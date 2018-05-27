package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.exception.PayException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
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
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.tx.paymentaccount.Tx4256Response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现查询
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessWithdrawQueryService extends AbstractBusinessService {

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private CapitalService capitalService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFinanceService userFinanceService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessagePushService messagePushService;

    @Autowired
    private SmsService smsService;

    @Autowired
    protected DistributeLock lock;

    @Override
    public Result before(PayModule module) {
        createOrder(module, null, PaymentOrderType.WITHDRAWAL_QUERY);
        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(module.getResponse().getTxResponse()));
        paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
        paymentOrderService.merge(paymentOrder);

        String withdrawOrderNo = paymentOrder.getParentOrderNo();
        PaymentOrder order = paymentOrderService.findByOrderNo(withdrawOrderNo);
        Tx4256Response txResponse = (Tx4256Response)module.getResponse().getTxResponse();
        order.setResParams(JsonUtils.toJson(txResponse));
        if (StringUtils.isBlank(txResponse.getStatus())) {
            LOGGER.error("提现订单[{}]查询异常[{}]", order.getOrderNo(), JsonUtils.toJson(txResponse));
            return result;
        }
        WithdrawRecord withdrawRecord = withdrawService.findByOrderNo(withdrawOrderNo);
        try {
            lock.lock(LockStack.USER_LOCK, "WITHDRAW" + withdrawOrderNo);
            switch (Integer.parseInt(txResponse.getStatus())) {
                case 10 : {
                }
                case 30 : {
                    if (withdrawRecord.getStatus() == RecordStatus.PROCESSING) {
                        throw new BusinessProcessException(String.format("[%s]订单已经是处理中状态", withdrawOrderNo));
                    }
                    paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
                    Integer userId = paymentOrder.getUserId();
                    User user = userService.get(userId);
                    UserFinance userFinance = userFinanceService.findByUserId(userId);
                    BigDecimal frozenAmount = withdrawRecord.getAmount();
                    frozenAmount = frozenAmount.add(withdrawRecord.getFee());
                    // 更新用户可用余额
                    userFinance.addFrozen(frozenAmount);
                    userFinanceService.merge(userFinance);
                    Order _order = orderService.addOrMergeOrder(new OrderVo(userFinance.getAvailable(),
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
                            _order.getId());
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

                    break;
                }
                case 40 : {
                    withdrawService.withdrawSuccess(withdrawOrderNo);
                    break;
                }
                case 50 : {
                    withdrawService.withdrawFailure(withdrawOrderNo);
                    break;
                }
                default: {
                    throw new PayException("提现通知查询处理异常");
                }
            }
            paymentOrderService.merge(order);
        } finally {
            lock.unLock(LockStack.USER_LOCK, "WITHDRAW" + withdrawOrderNo);
        }

        return result;
    }

}
