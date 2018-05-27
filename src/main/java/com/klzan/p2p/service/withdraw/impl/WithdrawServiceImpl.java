package com.klzan.p2p.service.withdraw.impl;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.withdraw.WithdrawDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.capital.PlatformCapitalService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.service.withdraw.WithdrawService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.setting.WithdrawSetting;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.vo.order.OrderVo;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.p2p.vo.withdraw.WithdrawalFeeVo;
import com.klzan.p2p.vo.withdraw.WithdrawalVo;
import com.klzan.plugin.pay.ips.withdrawrefticket.vo.IpsPayWithdrawRefundTicketResponse;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by suhao Date: 2016/12/15 Time: 14:45
 *
 * @version: 1.0
 */
@Service
public class WithdrawServiceImpl extends BaseService<WithdrawRecord> implements WithdrawService {
    @Inject
    private WithdrawDao withdrawDao;
    @Inject
    private UserFinanceService userFinanceService;
    @Inject
    private CapitalService capitalService;
    @Inject
    private UserService userService;
    @Inject
    private PlatformCapitalService platformCapitalService;
    @Inject
    private PaymentOrderService paymentOrderService;
    @Inject
    private OrderService orderService;
    @Inject
    private DistributeLock distributeLock;
    @Inject
    private SettingUtils settingUtils;
    @Override
    public WithdrawRecord addRecord(WithdrawalVo withdrawalVo) {
        Integer userId = withdrawalVo.getUserId();
//        User user = userService.get(userId);
        UserFinance userFinance = userFinanceService.findByUserId(userId);
        UserVo userVo = userService.getUserById(userId);
        BigDecimal amount = withdrawalVo.getAmount();
        WithdrawalFeeVo feeVo = calculateFee(withdrawalVo.getUserId(), amount);
        FeeDeductionType deductionType = feeVo.getDeductionType();
        // 生成提现记录
        WithdrawRecord record = new WithdrawRecord(userId,
                amount,
                feeVo.getPlatformFee(),
                feeVo.getIpsFee(),
                deductionType,
                feeVo.getPlatformAssumeIpsFee(),
                withdrawalVo.getOrderNo());
        withdrawDao.persist(record);
        orderService.addOrMergeOrder(new OrderVo(userId,
                userFinance.getAvailable(),
                BigDecimal.ZERO,
                userId,
                userVo.getRealName(),
                -1,
                userVo.getBankName() + ":" + userVo.getBankCard(),
                OrderStatus.LAUNCH,
                OrderType.WITHDRAW,
                OrderMethod.CPCN,
                record.getId(),
                null,
                record.getOrderNo(),
                null,
                record.getAmount(),
                record.getActualAmount(),
                record.getFee(),
                BigDecimal.ZERO,
                record.getActualIpsFee(),
                BigDecimal.ZERO,
                record.getCreateDate(),
                "提现",
                CommonUtils.getLoginName(),
                CommonUtils.getRemoteIp()
        ));
        // 更新用户可用余额
//        userFinance.addFrozen(amount);
//        userFinanceService.merge(userFinance);

        // 生成资金记录-冻结
//        Capital capital = new Capital(userFinance.getUserId(), CapitalMethod.WITHDRAW,
//                CapitalType.FROZEN,
//                amount,
//                userFinance,
//                record.getOrderNo(),
//                UserUtils.getCurrentUserPrincipal().getLoginName(),
//                CommonUtils.getRemoteIp(),
//                "提现冻结");
//        capitalService.addUserCapital(capital);
//
//        messagePushService.withdrawApplyPush(userId, withdrawalVo.getAmount());
//        smsService.sendToWithdrawMng(user.getMobile(), record.getAmount());
        return record;
    }

    @Override
    public List<WithdrawRecord> findWithdrawing(Integer userId) {
        return withdrawDao.findWithdrawing(userId);
    }
    @Override
    public List<WithdrawRecord> findWithdrawingMobile(Integer userId) {
        return withdrawDao.findWithdrawingMobile(userId);
    }
    @Override
    public List<WithdrawRecord> findList(RecordStatus status) {
        return withdrawDao.findList(status);
    }

    @Override
    public List<WithdrawRecord> findByUser(Integer userId, RecordStatus status) {
        return withdrawDao.findByUser(userId, status);
    }

    @Override
    public List<WithdrawRecord> findByUser(Integer userId) {
        return withdrawDao.findByUser(userId);
    }

    @Override
    public WithdrawRecord findByOrderNo(String orderNo) {
        return withdrawDao.findByOrderNo(orderNo);
    }

    @Override
    public void withdrawSuccess(String orderNo) {
        WithdrawRecord withdrawRecord = findByOrderNo(orderNo);
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
        if (null == withdrawRecord) {
            throw new BusinessProcessException(String.format("提现订单记录[%s]不存在", orderNo));
        }
        if (null == paymentOrder) {
            throw new BusinessProcessException(String.format("提现订单[%s]不存在", orderNo));
        }
        if (withdrawRecord.getStatus() == RecordStatus.SUCCESS || paymentOrder.getStatus() == PaymentOrderStatus.SUCCESS) {
            throw new BusinessProcessException(String.format("提现订单[%s]已处理", orderNo));
        }
        try {
            distributeLock.lock(LockStack.USER_LOCK, orderNo);
            User user = userService.get(withdrawRecord.getUserId());
            UserFinance userFinance = userFinanceService.findByUserId(withdrawRecord.getUserId());

            FeeDeductionType deductionType = withdrawRecord.getDeductionType();
            BigDecimal frozenAmount = withdrawRecord.getAmount();
            BigDecimal arrivalAmount = withdrawRecord.getActualAmount();
            BigDecimal allFee = withdrawRecord.getFee();
            if (deductionType == FeeDeductionType.OUT) {
                frozenAmount = frozenAmount.add(withdrawRecord.getFee());
                if (!withdrawRecord.getPlatformAssumeIpsFee()) {
                    frozenAmount = frozenAmount.add(withdrawRecord.getIpsFee());
                }
            }
            if (!withdrawRecord.getPlatformAssumeIpsFee()) {
                allFee = allFee.add(withdrawRecord.getIpsFee());
            }

            Order order = orderService.addOrMergeOrder(new OrderVo(userFinance.getAvailable(),
                    userFinance.getAvailable(),
                    OrderStatus.SUCCESS,
                    OrderType.WITHDRAW,
                    withdrawRecord.getId(),
                    paymentOrder.getExtOrderNo()
            ));
            // 生成资金记录
            // 解冻
            Capital capital = new Capital(userFinance.getUserId(), CapitalMethod.WITHDRAW,
                    CapitalType.UNFROZEN,
                    frozenAmount,
                    userFinance,
                    orderNo,
                    user.getLoginName(),
                    CommonUtils.getRemoteIp(),
                    "提现解冻",
                    order.getId());
            capitalService.addUserCapital(capital);
            // 支出提现金额
            userFinance.subtractBalance(arrivalAmount, false);
            capital = new Capital(userFinance.getUserId(), CapitalMethod.WITHDRAW,
                    CapitalType.DEBIT,
                    arrivalAmount,
                    userFinance,
                    orderNo,
                    user.getLoginName(),
                    CommonUtils.getRemoteIp(),
                    "提现支出",
                    order.getId());
            capitalService.addUserCapital(capital);

            if (allFee.compareTo(BigDecimal.ZERO) == 1) {
                // 支出提现手续费
                userFinance.subtractBalance(allFee, false);
                capital = new Capital(userFinance.getUserId(), CapitalMethod.WITHDRAW_FEE,
                        CapitalType.DEBIT,
                        allFee,
                        userFinance,
                        orderNo,
                        user.getLoginName(),
                        CommonUtils.getRemoteIp(),
                        "提现手续费支出",
                        order.getId());
                capitalService.addUserCapital(capital);

                if (withdrawRecord.getFee().compareTo(BigDecimal.ZERO) == 1) {
                    // 平台收入提现手续费
                    PlatformCapital platformCapital = new PlatformCapital(CapitalType.CREDIT,
                            CapitalMethod.WITHDRAW_FEE,
                            withdrawRecord.getFee(),
                            BigDecimal.ZERO,
                            capital.getId(),
                            "提现手续费收入",
                            user.getLoginName(),
                            CommonUtils.getRemoteIp(),
                            orderNo
                    );
                    platformCapitalService.persist(platformCapital);
                }
            }

            // 更新提现记录状态
            withdrawRecord.updateStatus(RecordStatus.SUCCESS);
            withdrawRecord.setFinishPayTime(new Date());
            withdrawDao.merge(withdrawRecord);
            // 更新订单状态
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderService.merge(paymentOrder);

            // 更新用户余额
            userFinance.addWithdrawalAmts(withdrawRecord.getAmount().add(withdrawRecord.getFee()));
            userFinance.subtractFrozen(withdrawRecord.getAmount().add(withdrawRecord.getFee()));
            userFinanceService.update(userFinance);

        } finally {
            distributeLock.unLock(LockStack.USER_LOCK, orderNo);
        }
    }

    @Override
    public void withdrawFailure(String orderNo) {
        WithdrawRecord withdrawRecord = findByOrderNo(orderNo);
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
        if (null == withdrawRecord) {
            throw new BusinessProcessException(String.format("提现订单记录[%s]不存在", orderNo));
        }
        if (null == paymentOrder) {
            throw new BusinessProcessException(String.format("提现订单[%s]不存在", orderNo));
        }
        if (withdrawRecord.getStatus() == RecordStatus.SUCCESS || paymentOrder.getStatus() == PaymentOrderStatus.SUCCESS) {
            throw new BusinessProcessException(String.format("提现订单[%s]已处理", orderNo));
        }
        try {
            distributeLock.lock(LockStack.USER_LOCK, orderNo);
            // 更新充值记录状态
            withdrawRecord.updateStatus(RecordStatus.FAILURE);
            withdrawRecord.setRemark("驳回" + withdrawRecord.getRemark());
            withdrawDao.update(withdrawRecord);
            // 更新订单状态
            paymentOrder.setStatus(PaymentOrderStatus.FAILURE);
            paymentOrder.setMemo("驳回" + paymentOrder.getMemo());
            paymentOrderService.update(paymentOrder);

            UserFinance userFinance = userFinanceService.findByUserId(withdrawRecord.getUserId());

            Order order = orderService.addOrMergeOrder(new OrderVo(userFinance.getAvailable(),
                    userFinance.getAvailable(),
                    OrderStatus.FAILURE,
                    OrderType.WITHDRAW,
                    withdrawRecord.getId(),
                    paymentOrder.getExtOrderNo()
            ));
            // 生成资金记录
            // 解冻
            Capital capital = new Capital(userFinance.getUserId(), CapitalMethod.WITHDRAW,
                    CapitalType.UNFROZEN,
                    withdrawRecord.getAmount().add(withdrawRecord.getFee()),
                    userFinance,
                    orderNo,
                    SecurityUtils.getSubject().getPrincipal().toString(),
                    CommonUtils.getRemoteIp(),
                    "提现解冻",
                    order.getId());
            capitalService.addUserCapital(capital);

            // 更新用户余额
            userFinance.subtractFrozen(withdrawRecord.getAmount().add(withdrawRecord.getFee()));
            userFinanceService.update(userFinance);

        } finally {
            distributeLock.unLock(LockStack.USER_LOCK, orderNo);
        }
    }

    @Override
    public void withdrawRefund(String orderNo, IpsPayWithdrawRefundTicketResponse refund) {
        WithdrawRecord withdrawRecord = findByOrderNo(orderNo);
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
        if (null == withdrawRecord) {
            throw new BusinessProcessException(String.format("提现订单记录[%s]不存在", orderNo));
        }
        if (null == paymentOrder) {
            throw new BusinessProcessException(String.format("提现订单[%s]不存在", orderNo));
        }
        if (withdrawRecord.getStatus() == RecordStatus.SUCCESS || paymentOrder.getStatus() == PaymentOrderStatus.SUCCESS) {
            throw new BusinessProcessException(String.format("提现订单[%s]已处理", orderNo));
        }
        if (withdrawRecord.getStatus() == RecordStatus.FAILURE || paymentOrder.getStatus() == PaymentOrderStatus.FAILURE) {
            throw new BusinessProcessException(String.format("提现订单[%s]已失败", orderNo));
        }
        try {
            distributeLock.lock(LockStack.USER_LOCK, orderNo);
            // 更新充值记录状态
            withdrawRecord.updateStatus(RecordStatus.FAILURE);
            withdrawRecord.setRemark("退票：" + refund.getReason());
            withdrawDao.update(withdrawRecord);
            // 更新订单状态
            paymentOrder.setStatus(PaymentOrderStatus.FAILURE);
            paymentOrder.setMemo("退票：" + refund.getReason());
            paymentOrderService.update(paymentOrder);

            UserFinance userFinance = userFinanceService.findByUserId(withdrawRecord.getUserId());

            Order order = orderService.addOrMergeOrder(new OrderVo(userFinance.getAvailable(),
                    userFinance.getAvailable(),
                    OrderStatus.FAILURE,
                    OrderType.WITHDRAW,
                    withdrawRecord.getId(),
                    paymentOrder.getExtOrderNo()
            ));
            // 生成资金记录
            // 解冻
            Capital capital = new Capital(userFinance.getUserId(), CapitalMethod.WITHDRAW,
                    CapitalType.UNFROZEN,
                    withdrawRecord.getAmount().add(withdrawRecord.getFee()),
                    userFinance,
                    orderNo,
                    SecurityUtils.getSubject().getPrincipal().toString(),
                    CommonUtils.getRemoteIp(),
                    "提现退票",
                    order.getId());
            capitalService.addUserCapital(capital);

            // 更新用户余额
            userFinance.subtractFrozen(withdrawRecord.getAmount().add(withdrawRecord.getFee()));
            userFinanceService.update(userFinance);

        } finally {
            distributeLock.unLock(LockStack.USER_LOCK, orderNo);
        }
    }

    @Override
    public WithdrawalFeeVo calculateFee(Integer userId, BigDecimal amount) {
        UserFinance userFinance = userFinanceService.findByUserId(userId);
        BigDecimal available = userFinance.getAvailable();

        WithdrawSetting withdrawSetting = settingUtils.getWithdrawSetting();
        BigDecimal ipsFee = withdrawSetting.getIpsFee();
        Boolean platformAssumeIpsFee = withdrawSetting.getPlatformAssumeIpsFee();
        BigDecimal feeRate = withdrawSetting.getFeeRate();
        BigDecimal maxAmount = withdrawSetting.getMaxAmount();
        BigDecimal minAmount = withdrawSetting.getMinAmount();
        BigDecimal maxFee = withdrawSetting.getMaxFee();
        BigDecimal minFee = withdrawSetting.getMinFee();
        FeeDeductionType deductionType = FeeDeductionType.IN;
        WithdrawalFeeVo withdrawal = new WithdrawalFeeVo(BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                deductionType,
                platformAssumeIpsFee,
                BigDecimal.ZERO);
        if (null == feeRate) {
            return withdrawal;
        }
        BigDecimal fee = amount.multiply(feeRate).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_EVEN);
        maxFee = null == maxFee ? BigDecimal.ZERO : maxFee;
        minFee = null == minFee ? BigDecimal.ZERO : minFee;
        maxAmount = null == maxAmount ? BigDecimal.ZERO : maxAmount;
        minAmount = null == minAmount ? BigDecimal.ZERO : minAmount;
        BigDecimal actualFee = platformAssumeIpsFee ? fee : fee.add(ipsFee);
        BigDecimal actualMaxFee = platformAssumeIpsFee ? maxFee : maxFee.add(ipsFee);
        BigDecimal actualMinFee = platformAssumeIpsFee ? minFee : minFee.add(ipsFee);
        withdrawal.setAmount(amount);
        withdrawal.setIpsFee(ipsFee);
        withdrawal.setPlatformAssumeIpsFee(platformAssumeIpsFee);
        BigDecimal platformFee = calculatePlatformFee(amount, feeRate, minAmount, maxAmount, minFee, maxFee);
        withdrawal.setPlatformFee(platformFee);
        if (available.compareTo(amount.add(platformFee)) >= 0) {
            withdrawal.setDeductionType(FeeDeductionType.OUT);
            withdrawal.setArrivalAmount(amount);
        } else {
            withdrawal.setDeductionType(FeeDeductionType.IN);
            withdrawal.setArrivalAmount(amount.subtract(platformFee));
        }

        if (platformAssumeIpsFee) {
            withdrawal.setFee(platformFee);
        } else {
            withdrawal.setFee(platformFee.add(ipsFee));
        }

        return withdrawal;
    }

    private BigDecimal calculatePlatformFee(BigDecimal amount, BigDecimal feeRate, BigDecimal minAmount, BigDecimal maxAmount, BigDecimal minFee, BigDecimal maxFee) {
        BigDecimal platformFee;
        if (null == minAmount || minAmount == BigDecimal.ZERO) {
            platformFee = amount.multiply(feeRate).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_EVEN);
        } else {
            if (amount.compareTo(minAmount) <= 0) {
                platformFee = minFee;
            } else {
                if (amount.compareTo(minAmount) > 0 && amount.compareTo(maxAmount) <= 0) {
                    platformFee = amount.multiply(feeRate).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_EVEN);
                } else {
                    platformFee = maxFee;
                }
            }
        }

        return platformFee;
    }

    @Override
    public PageResult<WithdrawRecord> findPageByUser(Integer userId, PageCriteria criteria, RecordStatus status) {
        return withdrawDao.findPageByUser(userId, criteria, status);
    }

}
