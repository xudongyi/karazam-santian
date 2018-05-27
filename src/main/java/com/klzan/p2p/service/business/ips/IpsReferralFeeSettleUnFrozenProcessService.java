package com.klzan.p2p.service.business.ips;

import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.enums.ReferralFeeState;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.model.ReferralFee;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.user.ReferralFeeService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.unfreeze.vo.IpsPayUnfreezeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 推荐费结算-解冻
 * Created by suhao Date: 2017/4/7 Time: 18:31
 *
 * @version: 1.0
 */
@Service
public class IpsReferralFeeSettleUnFrozenProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReferralFeeService referralFeeService;

    @Override
    public Boolean process(String orderNo, IDetailResponse response) {
        try {
            lock.lock(LockStack.USER_LOCK, "REFERRAL_FEE_TRANSFER" + orderNo);
            checkOrder(orderNo);
            IpsPayUnfreezeResponse responseResult = (IpsPayUnfreezeResponse) response;
            logger.info(JsonUtils.toJson(responseResult));
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);

            Boolean isSucc = false;
            ReferralFee referralFee = referralFeeService.findByOrderNo(paymentOrder.getParentOrderNo());
            OrderStatus orderStatus;
            // 成功
            if (StringUtils.equals(responseResult.getTrdStatus(), "1")) {
                isSucc = true;
                orderStatus = OrderStatus.SUCCESS;
                referralFeeService.transfer(referralFee.getId());
            } else {
                //更新
                isSucc = false;
                orderStatus = OrderStatus.FAILURE;
                referralFee.setState(ReferralFeeState.FAILURE);
                referralFeeService.merge(referralFee);
            }
            orderService.updateOrderStatus(OrderType.REFERRAL, referralFee.getId(), orderStatus, orderNo);
            return isSucc;
        } finally {
            lock.unLock(LockStack.USER_LOCK, "REFERRAL_FEE_TRANSFER" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.UNFREEZE && type == PaymentOrderType.REFERRAL_FEE_SETTLEMENT;
    }

}
