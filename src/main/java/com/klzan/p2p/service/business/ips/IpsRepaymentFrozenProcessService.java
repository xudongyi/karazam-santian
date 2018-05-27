package com.klzan.p2p.service.business.ips;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.Order;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.frozen.vo.IpsPayFrozenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by zhutao Date: 2017/4/10
 *
 * @version: 1.0
 */
@Service
public class IpsRepaymentFrozenProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountantService accountantService;

    @Override
    @Transactional
    public String process(String orderNo, IDetailResponse response) {
        try {
            System.out.println("-------------还款冻结----------------------");
            lock.lock(LockStack.USER_LOCK, "REPAYMENT" + orderNo);
            checkOrder(orderNo);
            IpsPayFrozenResponse investResponse = (IpsPayFrozenResponse) response;
            logger.info(JsonUtils.toJson(investResponse));
            PaymentOrderStatus status = null;

            //还款
            Repayment repayment = accountantService.calOverdue(repaymentService.findByOrderNo(orderNo));

            if ("1".equals(investResponse.getTrdStatus())){
                try {
                    repaymentService.repayment(repayment,investResponse.getIpsBillNo());
                    status = PaymentOrderStatus.SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BusinessProcessException("还款异常");
                }
            }else {
                status = PaymentOrderStatus.FAILURE;
                orderService.updateOrderStatus(OrderType.REPAYMENT, repayment.getId(), OrderStatus.FAILURE, orderNo);
            }
            processOrder(orderNo, investResponse.getIpsBillNo(),status);
            return orderNo;
        } finally {
            lock.unLock(LockStack.USER_LOCK, "REPAYMENT" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.FROZEN&&type == PaymentOrderType.REPAYMENT_FROZEN;
    }

}
