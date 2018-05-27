package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.CpcnSettlement;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.plugin.pay.common.dto.RepaymentRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.tx.p2p.Tx3241Response;

import java.util.List;

/**
 * 还款
 */
@Service
public class BusinessRepaymentService extends AbstractBusinessService {

    @Autowired
    protected CpcnSettlementService cpcnSettlementService;
    @Autowired
    protected RepaymentService repaymentService;
    @Autowired
    protected OrderService orderService;

    @Override
    public Result before(PayModule module) {
        RepaymentRequest request = (RepaymentRequest) module.getRequest();

        PaymentOrder paymentOrder = createOrder(module, request.getBorrower(), PaymentOrderType.REPAYMENT);
        paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
        paymentOrder.setAmount(request.getAmount());
        paymentOrder.setFee(request.getFee());
        paymentOrderService.merge(paymentOrder);

        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(result.getData()));

        Tx3241Response response = (Tx3241Response)module.getResponse().getTxResponse();
        if(!paymentOrder.getStatus().equals(PaymentOrderStatus.PROCESSING)){
            return Result.error();
        }

        CpcnSettlement cpcnSettlement = cpcnSettlementService.findByOrderNoRepayment(paymentOrder.getOrderNo());

        if(response.getStatus() == 20){
            cpcnSettlement.setrStatus(CpcnRepaymentStatus.paying);
            cpcnSettlementService.merge(cpcnSettlement);
            return Result.proccessing();
        }else if(response.getStatus() == 30){
            cpcnSettlement.setrStatus(CpcnRepaymentStatus.success);
            cpcnSettlementService.merge(cpcnSettlement);
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderService.merge(paymentOrder);

            List<PaymentOrder> orders = paymentOrderService.findByParentOrderNo(cpcnSettlement.getrOrderNo());
            for(PaymentOrder order : orders){
                if(order.getStatus().equals(PaymentOrderStatus.NEW_CREATE)){
                    order.setStatus(PaymentOrderStatus.PROCESSING);
                    paymentOrderService.merge(order);
                }
            }

            //平台订单 : 还款
            if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT)){
                repaymentService.repaySucceed(cpcnSettlement, paymentOrder);
            }else {
                repaymentService.aheadRepaySucceed(cpcnSettlement, paymentOrder);
            }

            return Result.success();
        }else if(response.getStatus() == 40){
            cpcnSettlement.setrStatus(CpcnRepaymentStatus.failure);
            cpcnSettlementService.merge(cpcnSettlement);
            paymentOrder.setStatus(PaymentOrderStatus.FAILURE);
            paymentOrderService.merge(paymentOrder);
            //平台订单 : 还款
            if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT)){
                orderService.updateOrderStatus(OrderType.REPAYMENT, cpcnSettlement.getRepayment(), OrderStatus.FAILURE, paymentOrder.getOrderNo());
            }else {
                orderService.updateOrderStatus(OrderType.REPAYMENT_EARLY, cpcnSettlement.getBorrowing(), OrderStatus.FAILURE, paymentOrder.getOrderNo());
            }
            return Result.error();
        }else {
            throw new RuntimeException();
        }
    }

}
