package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.recharge.RechargeService;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.InvestmentRequest;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.tx.p2p.Tx3216Response;

import java.math.BigDecimal;

/**
 * 项目支付查询
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessInvestmentQueryService extends AbstractBusinessService {

    @Autowired
    private InvestmentService investmentService;
    @Autowired
    private PayUtils payUtils;
    @Autowired
    protected CpcnSettlementService cpcnSettlementService;
    @Autowired
    protected TransferService transferService;
    @Autowired
    protected UserService userService;
    @Autowired
    private InvestmentRecordService investmentRecordService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RechargeService rechargeService;

    @Override
    public Result before(PayModule module) {
        SnRequest request = (SnRequest)module.getRequest();
        PaymentOrder paymentOrder = createOrder(module, null, PaymentOrderType.INVESTMENT_QUERY);
        paymentOrder.setParentOrderNo(request.getSn());
        paymentOrderService.merge(paymentOrder);
        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        String sn = module.getSn();
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
        paymentOrder.setResParams(JsonUtils.toJson(module.getResponse().getTxResponse()));
        paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
        paymentOrderService.merge(paymentOrder);

        Tx3216Response response = (Tx3216Response)module.response.getTxResponse();

        // 10=未支付 20=已支付
        if (response.getStatus() == 10) {

        } else if (response.getStatus() == 20) {
            PaymentOrder order = paymentOrderService.findByOrderNo(paymentOrder.getParentOrderNo());
            if (!order.getStatus().equals(PaymentOrderStatus.PROCESSING)){
                return Result.error("已处理");
            }
            order.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderService.merge(order);
            InvestmentRequest busReqParams = JsonUtils.toObject(order.getBusReqParams(), InvestmentRequest.class);

            CpcnPayAccountInfo accountInfo = payUtils.getCpcnPayAccountInfo(order.getUserId());
            BigDecimal amount = PayUtils.convertToYuan(response.getAmount());
            String investorAccountNumber = response.getLoanerPaymentAccountNumber();
            BigDecimal orderAmount = order.getAmount();
            BigDecimal orderFee = paymentOrder.getFee();
            if (amount.compareTo(orderAmount.add(orderFee)) != 0) {
                LOGGER.error("项目支付查询处理异常[{}],投资金额与订单金额不一致", sn);
                throw new PayException("项目支付查询处理异常");
            }
            if (!StringUtils.equals(accountInfo.getAccountNumber(), investorAccountNumber)) {
                LOGGER.error("项目支付查询处理异常[{}],投资账号与订单账号不一致", sn);
                throw new PayException("项目支付查询处理异常");
            }

            if (response.getPaymentWay().equals("20") || response.getPaymentWay().equals("30")) {
                rechargeService.rechargeFromInvestSuccess(order.getOrderNo());
            }

//            OrderStatus orderStatus = OrderStatus.FROZEN;
            if(!busReqParams.getYesTransfer()){
//                InvestmentRecord investmentRecord = investmentRecordService.findByOrderNo(order.getOrderNo());
//                orderService.updateOrderStatus(OrderType.INVESTMENT, investmentRecord.getId(), orderStatus, order.getOrderNo());
                investmentService.invest(order.getOrderNo());
            }else {
                Transfer transfer = transferService.get(busReqParams.getTransfer());
                User currentUser = userService.get(busReqParams.getUserId());
                Integer parts = busReqParams.getParts();
                transferService.transferInSucceed(order.getOrderNo(), transfer, currentUser, parts);
            }

        }

        return Result.success();
    }

}
