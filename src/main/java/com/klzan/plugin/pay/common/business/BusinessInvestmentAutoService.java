package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.SnUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.vo.investment.InvestVo;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.InvestmentAutoRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.tx.TxBaseResponse;
import payment.api.tx.p2p.Tx3251Response;
import payment.api.vo.P2PLoanerItem;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目支付-自动
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessInvestmentAutoService extends AbstractBusinessService {
    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private InvestmentRecordService investmentRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFinanceService userFinanceService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayUtils payUtils;
    @Override
    public Result before(PayModule module) {
        InvestmentAutoRequest request = (InvestmentAutoRequest) module.getRequest();
        for (InvestVo investVo : request.getInvests()) {
            PaymentOrder paymentOrder = createOrder(module, investVo.getInvestor(), PaymentOrderType.AUTO_INVESTMENT);
            Borrowing borrowing = borrowingService.get(request.getProjectId());
            BigDecimal amount = investVo.getAmount();
            if (!borrowing.verifyInvest(amount)) {
                throw new BusinessProcessException("超过可投资金额");
            }

            // TODO 生成投资记录
            InvestmentRecord investmentRecord = investmentService.genInvestAndInvestmentRecord(investVo);

            User investor = userService.get(investVo.getInvestor());  // 投资人
            UserFinance investorFinance = userFinanceService.getByUserId(investVo.getInvestor());
            User borrower = userService.get(borrowing.getBorrower());  // 借款人
            UserFinance borrowerFinance = userFinanceService.getByUserId(borrowing.getBorrower());
            //平台订单 : 投资人投资
            Order order = new Order();
            order.setUserId(investmentRecord.getInvestor());
            order.setPayer(investmentRecord.getInvestor());
            order.setPayerName(investor.getLoginName());
            order.setPayee(borrower.getId());
            order.setPayerName(borrower.getLoginName());
            order.setStatus(OrderStatus.LAUNCH);
            order.setType(OrderType.INVESTMENT);
            order.setMethod(OrderMethod.CPCN);
            order.setBusiness(investmentRecord.getId());
            order.setOrderNo(SnUtils.getOrderNo());
            order.setThirdOrderNo(null);
            order.setPayerBalance(investorFinance.getAvailable());
            order.setPayeeBalance(borrowerFinance.getAvailable());
            order.setAmount(investmentRecord.getAmount().subtract(investmentRecord.getPreferentialAmount()));
            order.setAmountReceived(investmentRecord.getAmount());
            order.setPoints(0);
            order.setLaunchDate(new Date());
            order.setOperator(investor.getLoginName());
            order.setIp(null == request ? "": CommonUtils.getRemoteIp());
            order.setMemo(investVo.getOperationMethod().getDisplayName() + paymentOrder.getType().getDisplayName());
            orderService.persist(order);
        }

        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        try {
            InvestmentAutoRequest request = (InvestmentAutoRequest) module.getRequest();
            Tx3251Response txResponse = (Tx3251Response) module.getResponse().getTxResponse();
            for (P2PLoanerItem item : txResponse.getLoaners()) {
                String sn = item.getPaymentNo();
                int status = item.getStatus();
                InvestmentRecord investmentRecord = investmentRecordService.findByOrderNo(sn);
                PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
                paymentOrder.setAmount(investmentRecord.getAmount());
                if (status == 20) {
                    investmentRecord.setState(InvestmentState.PAID);
                } else {
                    investmentRecord.setState(InvestmentState.FAILURE);
                }
                paymentOrderService.merge(paymentOrder);
                investmentRecordService.merge(investmentRecord);
            }
        } catch (Exception e) {
            LOGGER.error("自动投标失败");
        }
        return result;
    }

}
