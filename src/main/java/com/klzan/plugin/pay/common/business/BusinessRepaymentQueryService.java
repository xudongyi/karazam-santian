package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.CpcnSettlement;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.notice.NoticeRequest;
import payment.api.tx.p2p.Tx3241Response;
import payment.api.tx.p2p.Tx3246Response;

import java.util.List;

/**
 * 还款查询
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessRepaymentQueryService extends AbstractBusinessService {

    @Autowired
    protected CpcnSettlementService cpcnSettlementService;
    @Autowired
    protected RepaymentService repaymentService;
    @Autowired
    protected OrderService orderService;

    @Override
    public Result before(PayModule module) {
        SnRequest request = (SnRequest)module.getRequest();
        PaymentOrder paymentOrder = createOrder(module, null, PaymentOrderType.REPAYMENT_QUERY);
        paymentOrder.setParentOrderNo(request.getSn());
//        paymentOrder.setStatus(PaymentOrderStatus.NEW_CREATE);
        paymentOrderService.merge(paymentOrder);

        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(result.getData()));
        if(!result.isSuccess()){
            paymentOrder.setStatus(PaymentOrderStatus.FAILURE);
            paymentOrderService.merge(paymentOrder);
            return Result.error();
        }
        paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
        paymentOrderService.merge(paymentOrder);

        PaymentOrder order = paymentOrderService.findByOrderNo(paymentOrder.getParentOrderNo());
        Tx3246Response response = (Tx3246Response)module.getResponse().getTxResponse();
        if(!order.getStatus().equals(PaymentOrderStatus.PROCESSING)){
            return Result.error();
        }

        CpcnSettlement cpcnSettlement = cpcnSettlementService.findByOrderNoRepayment(order.getOrderNo());

        if(response.getStatus() == 20){
            cpcnSettlement.setrStatus(CpcnRepaymentStatus.paying);
            cpcnSettlementService.merge(cpcnSettlement);
            return Result.proccessing();
        }else if(response.getStatus() == 30){
            cpcnSettlement.setrStatus(CpcnRepaymentStatus.success);
            cpcnSettlementService.merge(cpcnSettlement);
            order.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderService.merge(order);

            List<PaymentOrder> orders = paymentOrderService.findByParentOrderNo(cpcnSettlement.getrOrderNo());
            for(PaymentOrder order_ : orders){
                if(order_.getStatus().equals(PaymentOrderStatus.NEW_CREATE)){
                    order_.setStatus(PaymentOrderStatus.PROCESSING);
                    paymentOrderService.merge(order_);
                }
            }
            repaymentService.repaySucceed(cpcnSettlement, order);

            return Result.success();
        }else if(response.getStatus() == 40){
            cpcnSettlement.setrStatus(CpcnRepaymentStatus.failure);
            cpcnSettlementService.merge(cpcnSettlement);
            order.setStatus(PaymentOrderStatus.FAILURE);
            paymentOrderService.merge(order);
            //平台订单 : 还款
            if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT)){
                orderService.updateOrderStatus(OrderType.REPAYMENT, cpcnSettlement.getRepayment(), OrderStatus.FAILURE, paymentOrder.getOrderNo());
            }else {
                orderService.updateOrderStatus(OrderType.REPAYMENT_EARLY, cpcnSettlement.getBorrowing(), OrderStatus.FAILURE, paymentOrder.getOrderNo());
            }
            return Result.error();
        }else {
            return Result.error();
        }
    }
}
