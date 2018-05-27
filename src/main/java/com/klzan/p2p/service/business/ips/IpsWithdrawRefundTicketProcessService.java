package com.klzan.p2p.service.business.ips;

import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.service.withdraw.WithdrawService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.withdrawrefticket.vo.IpsPayWithdrawRefundTicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 提现退票处理
 * Created by suhao Date: 2017/4/7 Time: 18:31
 *
 * @version: 1.0
 */
@Service
public class IpsWithdrawRefundTicketProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private WithdrawService withdrawService;

    @Override
    public String process(String orderNo, IDetailResponse response) {
        try {
            lock.lock(LockStack.USER_LOCK, "WITHDRAW_REFUND_TICKET" + orderNo);
            checkOrder(orderNo);
            IpsPayWithdrawRefundTicketResponse withdrawRefundResponse = (IpsPayWithdrawRefundTicketResponse) response;
            logger.info("WITHDRAW_REFUND_TICKET " + orderNo + " result:" + JsonUtils.toJson(withdrawRefundResponse));

            // 提现-退票-处理
            // 失败
            PaymentOrderStatus status = PaymentOrderStatus.FAILURE;
            withdrawService.withdrawRefund(orderNo, withdrawRefundResponse);
            processOrder(orderNo, "", status);
            return orderNo;
        } finally {
            lock.unLock(LockStack.USER_LOCK, "WITHDRAW_REFUND_TICKET" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.WITHDRAW_REFUND_TICKET && type == PaymentOrderType.WITHDRAWAL;
    }

}
