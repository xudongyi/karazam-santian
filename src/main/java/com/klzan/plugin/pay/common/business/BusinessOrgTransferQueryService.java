package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.model.ReferralFee;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.p2p.service.user.ReferralFeeService;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 开户
 */
@Service
public class BusinessOrgTransferQueryService extends AbstractBusinessService{

    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private ReferralFeeService referralFeeService;
    @Autowired
    private OrderService orderService;

    @Override
    public Result before(PayModule module) {

        SnRequest request = (SnRequest) module.getRequest();

        createOrder(module, null, PaymentOrderType.ORG_TRANSFER_QUERY);

        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(result.getData()));
        if(!result.isSuccess()){
            paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
        }else {
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);

            PaymentOrder order = paymentOrderService.findByOrderNo(paymentOrder.getParentOrderNo());
            order.setStatus(PaymentOrderStatus.SUCCESS);
            ReferralFee referralFee = referralFeeService.findByOrderNo(order.getOrderNo());
            paymentOrderService.merge(order);
            orderService.updateOrderStatus(OrderType.REFERRAL, referralFee.getId(), OrderStatus.SUCCESS, order.getOrderNo());
            referralFeeService.transfer(referralFee.getId());
        }
        paymentOrderService.merge(paymentOrder);
        return result;
    }

}
