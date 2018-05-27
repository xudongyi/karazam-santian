package com.klzan.p2p.service.business.ips;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by suhao on 2017/4/7.
 */
public abstract class AbscractIpsProcessSerivce implements IpsProcessSerivce {

    protected static Logger logger = LoggerFactory.getLogger(AbscractIpsProcessSerivce.class);

    @Autowired
    protected PaymentOrderService paymentOrderService;

    @Autowired
    protected DistributeLock lock;

    protected void checkOrder(String orderNo) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
        if (null == paymentOrder) {
            throw new BusinessProcessException(String.format("[%s]订单不存在", orderNo));
        }
        PaymentOrderStatus status = paymentOrder.getStatus();
        if (status == PaymentOrderStatus.SUCCESS) {
            throw new BusinessProcessException(String.format("[%s]订单已处理", orderNo));
        }
    }

    protected void processOrder(String orderNo, String extOrdedrNo, PaymentOrderStatus status) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
        if (null == paymentOrder) {
            throw new BusinessProcessException(String.format("[%s]订单不存在", orderNo));
        }
        if (!paymentOrder.getStatus().equals(PaymentOrderStatus.SUCCESS)) {
            paymentOrderService.flush();
            paymentOrder.setStatus(status);
            paymentOrder.setExtOrderNo(extOrdedrNo);
            paymentOrderService.merge(paymentOrder);
        } else {
            logger.warn("[{}]订单已处理", orderNo);
        }
    }
}
