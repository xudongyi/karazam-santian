package com.klzan.p2p.service.business.ips;

import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.dao.investment.InvestmentRecordDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.model.UserCoupon;
import com.klzan.p2p.service.coupon.UserCouponService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.combfreeze.vo.IpsPayCombFreezeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zhutao Date: 2017/4/10
 *
 * @version: 1.0
 */
@Service
public class IpsCouponInvestProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvestmentRecordDao investmentRecordDao;
    @Resource
    private UserCouponService userCouponService;

    @Override
    public PaymentOrderStatus process(String orderNo, IDetailResponse response) {
        try {
            lock.lock(LockStack.INVESTMENT_LOCK, "COUPON_INVESTMENT" + orderNo);
            checkOrder(orderNo);
            IpsPayCombFreezeResponse investResponse = (IpsPayCombFreezeResponse) response;
            logger.info(JsonUtils.toJson(investResponse));
            try {
                investmentService.invest(orderNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            PaymentOrderStatus status;
            OrderStatus orderStatus;

            if ("1".equals(investResponse.getTrdStatus())) {
                status = PaymentOrderStatus.SUCCESS;
                orderStatus = OrderStatus.FROZEN;
            } else {
                status = PaymentOrderStatus.FAILURE;
                orderStatus = OrderStatus.FAILURE;
            }
            InvestmentRecord investmentRecord = investmentRecordDao.findByOrderNo(orderNo);
            UserCoupon userCoupon = userCouponService.get(investmentRecord.getCouponCode());
            userCoupon.setCouponState(CouponState.USED);
            userCoupon.setUsedId(investmentRecord.getBorrowing());
            userCoupon.setUsedDate(new Date());
            userCouponService.merge(userCoupon);
            orderService.updateOrderStatus(OrderType.INVESTMENT, investmentRecord.getId(), orderStatus, orderNo);
//            PaymentOrder investPaymentOrder = paymentOrderService.findByOrderNo(investResponse.getBid().getMerBillNo());
            PaymentOrder couponPaymentOrder = paymentOrderService.findByOrderNo(investResponse.getRedPacket().getMerBillNo());
            processOrder(orderNo, investResponse.getBid().getIpsBillNo(), status);
            processOrder(couponPaymentOrder.getOrderNo(), investResponse.getRedPacket().getIpsBillNo(), status);
            return status;
        } finally {
            lock.unLock(LockStack.INVESTMENT_LOCK, "COUPON_INVEST" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.COMB_FREEZE && type == PaymentOrderType.COMB_FREEZE;
    }

}
