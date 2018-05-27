package com.klzan.p2p.service.business.ips;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.message.MessagePushService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.sms.SmsService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.service.withdraw.WithdrawService;
import com.klzan.p2p.vo.order.OrderVo;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.withdraw.vo.IpsPayWithdrawResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by suhao Date: 2017/4/7 Time: 18:31
 *
 * @version: 1.0
 */
@Service
public class IpsWithdrawProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFinanceService userFinanceService;

    @Autowired
    private CapitalService capitalService;

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private MessagePushService messagePushService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SmsService smsService;

    @Override
    public String process(String orderNo, IDetailResponse response) {
        try {
            lock.lock(LockStack.USER_LOCK, "WITHDRAW" + orderNo);
            checkOrder(orderNo);
            IpsPayWithdrawResponse withdrawResponse = (IpsPayWithdrawResponse) response;
            logger.info(JsonUtils.toJson(withdrawResponse));

            WithdrawRecord withdrawRecord = withdrawService.findByOrderNo(orderNo);
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            PaymentOrderStatus status = paymentOrder.getStatus();
            Integer userId = withdrawRecord.getUserId();
            BigDecimal amount = withdrawRecord.getAmount();
            FeeDeductionType deductionType = withdrawRecord.getDeductionType();

            // 提现状态-3 退票-不处理，交由提现退票接口处理
            // 失败
            if (withdrawResponse.getTrdStatus().equals(0)) {
                status = PaymentOrderStatus.FAILURE;
                withdrawService.withdrawFailure(orderNo);
            }
            // 成功
            else if (withdrawResponse.getTrdStatus().equals(1)) {
                status = PaymentOrderStatus.SUCCESS;
                withdrawService.withdrawSuccess(orderNo);
            }
            // 处理中
            else if (withdrawResponse.getTrdStatus().equals(2)) {
                if (withdrawRecord.getStatus() == RecordStatus.PROCESSING) {
                    throw new BusinessProcessException(String.format("[%s]订单已经是处理中状态", orderNo));
                }
                status = PaymentOrderStatus.PROCESSING;

                User user = userService.get(userId);
                UserFinance userFinance = userFinanceService.findByUserId(userId);
                BigDecimal frozenAmount = amount;
                if (deductionType == FeeDeductionType.OUT) {
                    frozenAmount = frozenAmount.add(withdrawRecord.getFee());
                    if (!withdrawRecord.getPlatformAssumeIpsFee()) {
                        frozenAmount = frozenAmount.add(withdrawRecord.getIpsFee());
                    }
                }
                // 更新用户可用余额
                userFinance.addFrozen(frozenAmount);
                userFinanceService.merge(userFinance);
                Order order = orderService.addOrMergeOrder(new OrderVo(userFinance.getAvailable(),
                        userFinance.getAvailable(),
                        OrderStatus.THIRD_PROCESSING,
                        OrderType.WITHDRAW,
                        withdrawRecord.getId(),
                        paymentOrder.getExtOrderNo()
                ));
                // 生成资金记录-冻结
                Capital capital = new Capital(userFinance.getUserId(), CapitalMethod.WITHDRAW,
                        CapitalType.FROZEN,
                        frozenAmount,
                        userFinance,
                        withdrawRecord.getOrderNo(),
                        user.getLoginName(),
                        WebUtils.getRemoteIp(WebUtils.getHttpRequest()),
                        "提现冻结",
                        order.getId());
                capitalService.addUserCapital(capital);

                // 更新充值记录状态
                withdrawRecord.updateStatus(RecordStatus.PROCESSING);
                withdrawRecord.setSubmitPayTime(new Date());
                withdrawService.merge(withdrawRecord);
                // 更新订单状态
                paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
                paymentOrderService.merge(paymentOrder);

                // 消息推送
                messagePushService.withdrawApplyPush(userId, frozenAmount);
                smsService.sendToWithdrawMng(user.getMobile(), frozenAmount);
            }
            processOrder(orderNo, withdrawResponse.getIpsBillNo(), status);
            return orderNo;
        } finally {
            lock.unLock(LockStack.USER_LOCK, "WITHDRAW" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.WITHDRAW && type == PaymentOrderType.WITHDRAWAL;
    }

}
