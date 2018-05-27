package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.exception.PayException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.message.MessagePushService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.sms.SmsService;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.service.withdraw.WithdrawService;
import com.klzan.p2p.vo.order.OrderVo;
import com.klzan.p2p.vo.withdraw.WithdrawalVo;
import com.klzan.plugin.pay.common.dto.WithdrawRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.notice.Notice4257Request;
import payment.api.notice.NoticeRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessWithdrawService extends AbstractBusinessService {

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private CpcnAccService cpcnAccService;

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
        WithdrawRequest request = (WithdrawRequest) module.getRequest();
        createOrder(module, request.getUserId(), PaymentOrderType.WITHDRAWAL);
        WithdrawalVo withdrawalVo = new WithdrawalVo();
        withdrawalVo.setUserId(request.getUserId());
        withdrawalVo.setOrderNo(module.getSn());
        withdrawalVo.setAmount(request.getAmount());
        withdrawService.addRecord(withdrawalVo);
        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        String sn = module.getSn();
        WithdrawRecord withdrawRecord = withdrawService.findByOrderNo(sn);
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
        paymentOrder.setResParams(JsonUtils.toJson(module.getResponse().getTxResponse()));
        paymentOrder.setAmount(withdrawRecord.getAmount());
        paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
        paymentOrderService.merge(paymentOrder);
        return result;
    }

    @Override
    public Result notice(String sn, NoticeRequest notice) {
        try {
            Notice4257Request txNotice = new Notice4257Request(notice.getDocument());
            String paymentNo = txNotice.getTxSN();
            lock.lock(LockStack.USER_LOCK, "WITHDRAW" + sn);
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
            if (null == paymentOrder) {
                LOGGER.error("提现通知处理异常[{}],支付订单不存在", sn);
                throw new PayException("提现通知处理异常");
            }
            // 更新订单
            paymentOrder.setResParams(JsonUtils.toJson(txNotice));

            BigDecimal amount = new BigDecimal(txNotice.getAmount()).add(new BigDecimal(txNotice.getInstitutionFee()));
            String paymentAccountNumber = txNotice.getPaymentAccountNumber();

            CpcnPayAccountInfo payAcc = cpcnAccService.findByPayAcc(paymentAccountNumber);
            if (null == payAcc) {
                LOGGER.error("提现通知处理异常[{}],账户信息不存在", sn);
                throw new PayException("提现通知处理异常");
            }

            WithdrawRecord withdrawRecord = withdrawService.findByOrderNo(sn);
            if (null == withdrawRecord) {
                LOGGER.error("提现通知处理异常[{}],提现记录不存在", sn);
                throw new PayException("提现通知处理异常");
            }
            if (!payAcc.getUserId().equals(withdrawRecord.getUserId())) {
                LOGGER.error("提现通知处理异常[{}],账户信息不一致", sn);
                throw new PayException("提现通知处理异常");
            }
            BigDecimal recordAmount = withdrawRecord.getActualAmount().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
            if (recordAmount.compareTo(amount) != 0 ) {
                LOGGER.error("提现通知处理异常[{}],提现记录金额与通知金额不一致", sn);
                throw new PayException("提现通知处理异常");
            }

            paymentOrder.setResParams(JsonUtils.toJson(txNotice));
            LOGGER.info("提现订单[{}]通知状态[{}]", sn, txNotice.getStatus());
            switch (txNotice.getStatus()) {
                case 10 : {
                }
                case 30 : {
                    if (withdrawRecord.getStatus() == RecordStatus.PROCESSING) {
                        throw new BusinessProcessException(String.format("[%s]订单已经是处理中状态", paymentNo));
                    }
                    paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
                    Integer userId = paymentOrder.getUserId();
                    User user = userService.get(userId);
                    UserFinance userFinance = userFinanceService.findByUserId(userId);
                    BigDecimal frozenAmount = BigDecimal.ZERO;
                    if (withdrawRecord.getDeductionType() == FeeDeductionType.IN) {
                        frozenAmount = withdrawRecord.getAmount();
                    } else if (withdrawRecord.getDeductionType() == FeeDeductionType.OUT) {
                        frozenAmount = withdrawRecord.getAmount().add(withdrawRecord.getFee());
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

                    break;
                }
                case 40 : {
                    withdrawService.withdrawSuccess(paymentNo);
                    break;
                }
                case 50 : {
                    withdrawService.withdrawFailure(paymentNo);
                    break;
                }
                default: {
                    throw new PayException("提现通知处理异常");
                }
            }

            paymentOrderService.merge(paymentOrder);

            return Result.success();
        } catch (Exception e) {
            throw new PayException("提现通知处理异常");
        } finally {
            lock.unLock(LockStack.USER_LOCK, "WITHDRAW" + sn);
        }
    }
}
