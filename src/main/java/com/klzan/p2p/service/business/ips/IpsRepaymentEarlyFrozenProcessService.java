package com.klzan.p2p.service.business.ips;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.Order;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.frozen.vo.IpsPayFrozenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhutao Date: 2017/4/10
 *
 * @version: 1.0
 */
@Service
public class IpsRepaymentEarlyFrozenProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private OrderService orderService;

    @Override
    public String process(String orderNo, IDetailResponse response) {
        try {
            System.out.println("-------------提前还款冻结----------------------");
            lock.lock(LockStack.USER_LOCK, "REPAYMENT_EARLY" + orderNo);
            checkOrder(orderNo);
            IpsPayFrozenResponse investResponse = (IpsPayFrozenResponse) response;
            logger.info(JsonUtils.toJson(investResponse));
            PaymentOrderStatus status = null;

            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            if(paymentOrder.getExt()==null || paymentOrder.getExt().equals("")){
                throw new RuntimeException("借款不存在");
            }
            Integer borrowingId = Integer.valueOf(paymentOrder.getExt());
            Borrowing borrowing = borrowingService.get(borrowingId);

            if ("1".equals(investResponse.getTrdStatus())){
                try {
                    repaymentService.preRepayment(borrowing,investResponse.getIpsBillNo());
                    status = PaymentOrderStatus.SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BusinessProcessException("还款异常");
                }
            }else {
                status = PaymentOrderStatus.FAILURE;
                orderService.updateOrderStatus(OrderType.REPAYMENT_EARLY, borrowingId, OrderStatus.FAILURE, orderNo);
            }
            processOrder(orderNo, investResponse.getIpsBillNo(),status);
            return orderNo;
        } finally {
            lock.unLock(LockStack.USER_LOCK, "REPAYMENT_EARLY" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.FROZEN&&type == PaymentOrderType.REPAYMENT_EARLY_FROZEN;
    }

}
