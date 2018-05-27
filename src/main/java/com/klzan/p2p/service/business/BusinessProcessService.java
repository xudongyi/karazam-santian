package com.klzan.p2p.service.business;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.business.ips.IpsProcessSerivce;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by suhao Date: 2017/4/7 Time: 18:26
 *
 * @version: 1.0
 */
@Service
public class BusinessProcessService {
    @Autowired
    private List<IpsProcessSerivce> ipsProcessSerivces;
    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    protected DistributeLock lock;

    public <T> T process(String orderNo, IDetailResponse response, BusinessType businessType, PaymentOrderType type) {
        for (IpsProcessSerivce ipsProcessSerivce : ipsProcessSerivces) {
            if (ipsProcessSerivce.support(businessType, type)) {
                return ipsProcessSerivce.process(orderNo, response);
            }
        }
        throw new BusinessProcessException("[" + type.getDisplayName() + "]处理异常");
    }

    public void processOrder(String orderNo, String result) {
        try {
            lock.lock(LockStack.USER_LOCK, "ORDER_PROCESS" + orderNo);
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            if (null == paymentOrder) {
                throw new BusinessProcessException(String.format("[%s]订单不存在", orderNo));
            }
            PaymentOrderStatus status = paymentOrder.getStatus();
            if (status.equals(PaymentOrderStatus.SUCCESS)) {
                throw new BusinessProcessException(String.format("[%s]订单已处理", orderNo));
            }
            paymentOrder.setResParams(result);
            paymentOrderService.merge(paymentOrder);
        } finally {
            lock.unLock(LockStack.USER_LOCK, "ORDER_PROCESS" + orderNo);
        }
    }
}
