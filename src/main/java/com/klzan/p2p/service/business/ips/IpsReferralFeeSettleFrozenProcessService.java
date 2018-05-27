package com.klzan.p2p.service.business.ips;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.ReferralFee;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.user.ReferralFeeService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.frozen.vo.IpsPayFrozenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 推荐费结算冻结处理
 *
 * @version: 1.0
 */
@Service
public class IpsReferralFeeSettleFrozenProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private ReferralFeeService referralFeeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BusinessService businessService;

    @Override
    public String process(String orderNo, IDetailResponse response) {
        try {
            lock.lock(LockStack.USER_LOCK, "REFERRAL_FEE_FROZEN" + orderNo);
            checkOrder(orderNo);
            IpsPayFrozenResponse referralFeeFrozenResponse = (IpsPayFrozenResponse) response;
            logger.info(JsonUtils.toJson(referralFeeFrozenResponse));
            PaymentOrderStatus status = null;
            OrderStatus orderStatus;

            ReferralFee referralFee = referralFeeService.findByOrderNo(orderNo);

            if ("1".equals(referralFeeFrozenResponse.getTrdStatus())){
                try {
                    status = PaymentOrderStatus.SUCCESS;
                    orderStatus = OrderStatus.FROZEN;
                    orderService.updateOrderStatus(OrderType.REFERRAL, referralFee.getId(), orderStatus, orderNo);
                    businessService.referralSettleUnFrozen(orderNo, referralFeeFrozenResponse.getIpsBillNo());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BusinessProcessException("推荐费结算异常");
                }
            }else {
                status = PaymentOrderStatus.FAILURE;
                orderStatus = OrderStatus.FAILURE;
                orderService.updateOrderStatus(OrderType.REFERRAL, referralFee.getId(), orderStatus, orderNo);
            }

            processOrder(orderNo, referralFeeFrozenResponse.getIpsBillNo(),status);
            return orderNo;
        } finally {
            lock.unLock(LockStack.USER_LOCK, "REFERRAL_FEE_FROZEN" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.FROZEN &&type == PaymentOrderType.REFERRAL_FEE_SETTLEMENT_FROZEN;
    }

}
