package com.klzan.p2p.service.recharge.impl;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.recharge.RechargeRecordDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.bank.BankService;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.message.MessagePushService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.recharge.RechargeService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.vo.order.OrderVo;
import com.klzan.p2p.vo.recharge.RechargeVo;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.pay.ips.appRecharge.vo.IpsPayAppRechargeResponse;
import com.klzan.plugin.pay.ips.recharge.vo.IpsPayRechargeResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by suhao Date: 2016/12/8 Time: 10:36
 *
 * @version: 1.0
 */
@Service
public class RechargeServiceImpl extends BaseService<RechargeRecord> implements RechargeService {

    private static Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

    @Inject
    private RechargeRecordDao rechargeRecordDao;
    @Inject
    private PaymentOrderService paymentOrderService;
    @Inject
    private CapitalService capitalService;
    @Inject
    private UserFinanceService userFinanceService;
    @Inject
    private UserService userService;
    @Inject
    private BankService bankService;
    @Inject
    private OrderService orderService;
    @Inject
    private InvestmentRecordService investmentRecordService;
    @Inject
    private DistributeLock distributeLock;
    @Inject
    private SettingUtils settingUtils;
    @Inject
    private MessagePushService messagePushService;

    @Override
    public RechargeRecord addRecord(RechargeVo rechargeVo) {
        Integer userId = rechargeVo.getUserId();
        UserVo userVo = userService.getUserById(userId);
        Bank bank= new Bank();
        if(rechargeVo.getBankId()!=null){
            bank = bankService.get(rechargeVo.getBankId());
        }
        UserFinance userFinance = userFinanceService.getByUserId(userId);

        String bankName = bank.getName();
        RechargeRecord rechargeRecord = new RechargeRecord(
                rechargeVo.getRechargeBusType(),
                userId,
                rechargeVo.getAmount(),
                rechargeVo.getFee(),
                BigDecimal.ZERO,
                true,
                FeeDeductionType.OUT,
                bankName,
                rechargeVo.getBankCode(),
                rechargeVo.getBankId(),
                rechargeVo.getCoupon(),
                rechargeVo.getOrderNo());
        this.persist(rechargeRecord);

        orderService.addOrMergeOrder(new OrderVo(
                userId,
                BigDecimal.ZERO,
                userFinance.getAvailable(),
                -1,
                bankName,
                userId,
                userVo.getRealName(),
                OrderStatus.LAUNCH,
                OrderType.RECHARGE,
                OrderMethod.CPCN,
                rechargeRecord.getId(),
                null,
                rechargeRecord.getOrderNo(),
                null,
                rechargeRecord.getAmount(),
                rechargeRecord.getActualAmount(),
                BigDecimal.ZERO,
                rechargeRecord.getFee(),
                BigDecimal.ZERO,
                rechargeRecord.getActualIpsFee(),
                rechargeRecord.getCreateDate(),
                "充值",
                CommonUtils.getLoginName(),
                CommonUtils.getRemoteIp()
        ));

        return rechargeRecord;
    }

    @Override
    public void rechargeSuccess(String orderNo, IpsPayRechargeResponse rechargeResponse) {
        try {
            distributeLock.lock(LockStack.USER_LOCK, orderNo);
            logger.info("begin to process recharhe order {}", orderNo);
            RechargeRecord rechargeRecord = rechargeRecordDao.findByOrderNo(orderNo);
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            if (null == rechargeRecord) {
                throw new BusinessProcessException(String.format("充值订单记录[%s]不存在", orderNo));
            }
            if (null == paymentOrder) {
                throw new BusinessProcessException(String.format("充值订单[%s]不存在", orderNo));
            }
            if (rechargeRecord.getStatus() == RecordStatus.SUCCESS || paymentOrder.getStatus() == PaymentOrderStatus.SUCCESS) {
                throw new BusinessProcessException(String.format("充值订单[%s]已处理", orderNo));
            }

            // 判断充值前和充值后的方式有没有变化
            // 1、普通充值 2、还款充值
            String depositType = rechargeResponse.getDepositType();
            RechargeBusinessType businessType = rechargeRecord.getBusinessType();
            String bankCode = rechargeRecord.getBankCode();
            String rechargeBusinessType = businessType.getRechargeBusinessType();
            String channelType = rechargeResponse.getChannelType();
            String ipsDoTime = rechargeResponse.getIpsDoTime();
            if (!StringUtils.equals(depositType, rechargeBusinessType)) {
                logger.warn("充值订单[{}]充值前充值类型为[{}]", orderNo, businessType.getDisplayName());
                logger.warn("充值订单[{}]充值后通知充值类型为[{}]", orderNo, RechargeBusinessType.convert(depositType).getDisplayName());
            }

            if (!StringUtils.equals(bankCode, rechargeResponse.getBankCode())) {
                logger.warn("充值订单[{}]充值前充值银行为[{}]", orderNo, bankCode);
                logger.warn("充值订单[{}]充值后充值银行为[{}]", orderNo, rechargeResponse.getBankCode());
                if (StringUtils.isNotBlank(rechargeResponse.getBankCode())) {
                    Bank bank = bankService.findByCodeAndBizType(rechargeResponse.getBankCode(), Integer.parseInt(channelType));
                    rechargeRecord.updateBanKInfo(bank);
                }
            }

            // 更新充值记录状态
            BigDecimal ipsFee = rechargeResponse.getIpsFee();
            rechargeRecord.updateIpsFee(ipsFee);
            rechargeRecord.updateIpsProcessTime(DateUtils.parse(ipsDoTime, DateUtils.DATE_PATTERN_DEFAULT));
            rechargeRecord.updateStatus(RecordStatus.SUCCESS);
            rechargeRecordDao.update(rechargeRecord);
            // 更新订单状态
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderService.update(paymentOrder);

            UserFinance userFinance = userFinanceService.findByUserId(rechargeRecord.getUserId());
            // 更新用户余额
            BigDecimal rechargeAmount = rechargeRecord.getAmount();
            if (rechargeRecord.getDeductionType() == FeeDeductionType.OUT) {
                rechargeAmount = rechargeRecord.getAmount().add(rechargeRecord.getAllFee());
            }
            userFinance.addBalance(rechargeAmount, rechargeRecord.getBusinessType());
            userFinance.addRechargeAmts(rechargeAmount);
            userFinanceService.update(userFinance);

            // 生成资金记录
            User user = userService.get(rechargeRecord.getUserId());
            Capital capitalFee = null;
            Capital capital = new Capital(userFinance.getUserId(), CapitalMethod.RECHARGE,
                    CapitalType.CREDIT,
                    rechargeAmount,
                    userFinance,
                    orderNo,
                    user.getLoginName(),
                    WebUtils.getRemoteIp(WebUtils.getHttpRequest()),
                    paymentOrder.getMethod().getDisplayName() + "充值",
                    null);
            if (rechargeRecord.getAllFee().compareTo(BigDecimal.ZERO) > 0) {
                userFinance.subtractBalance(rechargeRecord.getAllFee(), false);
                userFinanceService.update(userFinance);
                capitalFee = new Capital(userFinance.getUserId(), CapitalMethod.RECHARGE_FEE,
                        CapitalType.DEBIT,
                        rechargeRecord.getAllFee(),
                        userFinance,
                        orderNo,
                        user.getLoginName(),
                        WebUtils.getRemoteIp(WebUtils.getHttpRequest()),
                        "充值手续费",
                        null);
            }

            userFinanceService.flush();
            Order order = orderService.addOrMergeOrder(new OrderVo(userFinance.getAvailable(),
                    userFinance.getAvailable(),
                    OrderStatus.SUCCESS,
                    OrderType.RECHARGE,
                    rechargeRecord.getId(),
                    paymentOrder.getExtOrderNo()
            ));
            order.setAmountReceived(rechargeRecord.getActualAmount());
            order.setPayeeThirdFee(rechargeRecord.getActualIpsFee());
            orderService.merge(order);
            capital.setOrderId(order.getId());
            capitalService.addUserCapital(capital);
            if (rechargeRecord.getAllFee().compareTo(BigDecimal.ZERO) > 0) {
                capitalFee.setOrderId(order.getId());
                capitalService.addUserCapital(capitalFee);
            }
            messagePushService.rechargeSuccPush(paymentOrder.getUserId(), paymentOrder.getAmount());
        } catch (Exception e) {
            logger.error("recharge fail order {}, {}", orderNo, ExceptionUtils.getMessage(e));
        } finally {
            distributeLock.unLock(LockStack.USER_LOCK, orderNo);
        }
    }

    @Override
    public void rechargeSuccess(String orderNo) {
        try {
            distributeLock.lock(LockStack.USER_LOCK, orderNo);
            logger.info("begin to process recharhe order {}", orderNo);
            RechargeRecord rechargeRecord = rechargeRecordDao.findByOrderNo(orderNo);
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            if (null == rechargeRecord) {
                throw new BusinessProcessException(String.format("充值订单记录[%s]不存在", orderNo));
            }
            if (null == paymentOrder) {
                throw new BusinessProcessException(String.format("充值订单[%s]不存在", orderNo));
            }
            if (rechargeRecord.getStatus() == RecordStatus.SUCCESS || paymentOrder.getStatus() == PaymentOrderStatus.SUCCESS) {
                throw new BusinessProcessException(String.format("充值订单[%s]已处理", orderNo));
            }

            // 更新充值记录状态
            rechargeRecord.updateStatus(RecordStatus.SUCCESS);
            rechargeRecordDao.update(rechargeRecord);
            // 更新订单状态
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderService.update(paymentOrder);

            UserFinance userFinance = userFinanceService.findByUserId(rechargeRecord.getUserId());
            // 更新用户余额
            BigDecimal rechargeAmount = rechargeRecord.getAmount();
            if (rechargeRecord.getDeductionType() == FeeDeductionType.OUT) {
                rechargeAmount = rechargeRecord.getAmount().add(rechargeRecord.getAllFee());
            }
            userFinance.addBalance(rechargeAmount, rechargeRecord.getBusinessType());
            userFinance.addRechargeAmts(rechargeAmount);
            userFinanceService.update(userFinance);

            // 生成资金记录
            User user = userService.get(rechargeRecord.getUserId());
            Capital capitalFee = null;
            Capital capital = new Capital(userFinance.getUserId(), CapitalMethod.RECHARGE,
                    CapitalType.CREDIT,
                    rechargeAmount,
                    userFinance,
                    orderNo,
                    user.getLoginName(),
                    WebUtils.getRemoteIp(WebUtils.getHttpRequest()),
                    paymentOrder.getMethod().getDisplayName() + "充值",
                    null);
            if (rechargeRecord.getAllFee().compareTo(BigDecimal.ZERO) > 0) {
                userFinance.subtractBalance(rechargeRecord.getAllFee(), false);
                userFinanceService.update(userFinance);
                capitalFee = new Capital(userFinance.getUserId(), CapitalMethod.RECHARGE_FEE,
                        CapitalType.DEBIT,
                        rechargeRecord.getAllFee(),
                        userFinance,
                        orderNo,
                        user.getLoginName(),
                        WebUtils.getRemoteIp(WebUtils.getHttpRequest()),
                        "充值手续费",
                        null);
            }

            userFinanceService.flush();
            Order order = orderService.addOrMergeOrder(new OrderVo(userFinance.getAvailable(),
                    userFinance.getAvailable(),
                    OrderStatus.SUCCESS,
                    OrderType.RECHARGE,
                    rechargeRecord.getId(),
                    paymentOrder.getExtOrderNo()
            ));
            order.setAmountReceived(rechargeRecord.getActualAmount());
            order.setPayeeThirdFee(rechargeRecord.getActualIpsFee());
            orderService.merge(order);
            capital.setOrderId(order.getId());
            capitalService.addUserCapital(capital);
            if (rechargeRecord.getAllFee().compareTo(BigDecimal.ZERO) > 0) {
                capitalFee.setOrderId(order.getId());
                capitalService.addUserCapital(capitalFee);
            }
            messagePushService.rechargeSuccPush(paymentOrder.getUserId(), paymentOrder.getAmount());
        } catch (Exception e) {
            logger.error("recharge fail order {}, {}", orderNo, ExceptionUtils.getMessage(e));
        } finally {
            distributeLock.unLock(LockStack.USER_LOCK, orderNo);
        }
    }

    @Override
    public void rechargeFromInvestSuccess(String orderNo) {
        try {
            distributeLock.lock(LockStack.USER_LOCK, orderNo);
            logger.info("begin to process invest recharhe order {}", orderNo);
            InvestmentRecord investmentRecord = investmentRecordService.findByOrderNo(orderNo);
            if (null == investmentRecord) {
                throw new BusinessProcessException(String.format("投资记录[%s]不存在", orderNo));
            }
            if (investmentRecord.getState() != InvestmentState.INVESTING) {
                throw new BusinessProcessException(String.format("投资记录[%s]状态不是投资中", orderNo));
            }

            UserFinance userFinance = userFinanceService.findByUserId(investmentRecord.getInvestor());
            // 更新用户余额
            BigDecimal rechargeAmount = investmentRecord.getAmount();
            userFinance.addBalance(rechargeAmount, RechargeBusinessType.GENERAL);
            userFinance.addRechargeAmts(rechargeAmount);
            userFinanceService.update(userFinance);

            // 生成资金记录
            User user = userService.get(investmentRecord.getInvestor());
            Capital capital = new Capital(userFinance.getUserId(), CapitalMethod.RECHARGE,
                    CapitalType.CREDIT,
                    rechargeAmount,
                    userFinance,
                    orderNo,
                    user.getLoginName(),
                    WebUtils.getRemoteIp(WebUtils.getHttpRequest()),
                    "投资充值",
                    null);
            userFinanceService.flush();
            capitalService.addUserCapital(capital);
        } catch (Exception e) {
            logger.error("invest recharge fail order {}, {}", orderNo, ExceptionUtils.getMessage(e));
        } finally {
            distributeLock.unLock(LockStack.USER_LOCK, orderNo);
        }
    }

    @Override
    public void appRechargeSuccess(String orderNo, IpsPayAppRechargeResponse rechargeResponse) {
        try {
            distributeLock.lock(LockStack.USER_LOCK, orderNo);
            logger.info("begin to process recharhe order {}", orderNo);
            RechargeRecord rechargeRecord = rechargeRecordDao.findByOrderNo(orderNo);
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            if (null == rechargeRecord) {
                throw new BusinessProcessException(String.format("充值订单记录[%s]不存在", orderNo));
            }
            if (null == paymentOrder) {
                throw new BusinessProcessException(String.format("充值订单[%s]不存在", orderNo));
            }
            if (rechargeRecord.getStatus() == RecordStatus.SUCCESS || paymentOrder.getStatus() == PaymentOrderStatus.SUCCESS) {
                throw new BusinessProcessException(String.format("充值订单[%s]已处理", orderNo));
            }

            // 判断充值前和充值后的方式有没有变化
            // 1、普通充值 2、还款充值
//            String depositType = rechargeResponse.getDepositType();
//            RechargeBusinessType businessType = rechargeRecord.getBusinessType();
//            String bankCode = rechargeRecord.getBankCode();
//            String rechargeBusinessType = businessType.getRechargeBusinessType();
//            String channelType = rechargeResponse.getChannelType();
//            String ipsDoTime = rechargeResponse.getIpsDoTime();
//            if (!StringUtils.equals(depositType, rechargeBusinessType)) {
//                logger.warn("充值订单[{}]充值前充值类型为[{}]", orderNo, businessType.getDisplayName());
//                logger.warn("充值订单[{}]充值后通知充值类型为[{}]", orderNo, RechargeBusinessType.convert(depositType).getDisplayName());
//            }

//            if (!StringUtils.equals(bankCode, rechargeResponse.getBankCode())) {
//                logger.warn("充值订单[{}]充值前充值银行为[{}]", orderNo, bankCode);
//                logger.warn("充值订单[{}]充值后充值银行为[{}]", orderNo, rechargeResponse.getBankCode());
//                if (StringUtils.isNotBlank(rechargeResponse.getBankCode())) {
//                    Bank bank = bankService.findByCodeAndBizType(rechargeResponse.getBankCode(), Integer.parseInt(channelType));
//                    rechargeRecord.updateBanKInfo(bank);
//                }
//            }
            String ipsDoTime = rechargeResponse.getIpsDoTime();
            // 更新充值记录状态
            BigDecimal ipsFee = rechargeResponse.getIpsFee();
            rechargeRecord.updateIpsFee(ipsFee);
            rechargeRecord.updateIpsProcessTime(DateUtils.parse(ipsDoTime, DateUtils.DATE_PATTERN_DEFAULT));
            rechargeRecord.updateStatus(RecordStatus.SUCCESS);
            rechargeRecordDao.update(rechargeRecord);
            // 更新订单状态
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderService.update(paymentOrder);

            UserFinance userFinance = userFinanceService.findByUserId(rechargeRecord.getUserId());
            // 更新用户余额
            BigDecimal rechargeAmount = rechargeRecord.getAmount();
            if (rechargeRecord.getDeductionType() == FeeDeductionType.OUT) {
                rechargeAmount = rechargeRecord.getAmount().add(rechargeRecord.getAllFee());
            }
            userFinance.addBalance(rechargeAmount, rechargeRecord.getBusinessType());
            userFinance.addRechargeAmts(rechargeAmount);
            userFinanceService.update(userFinance);

            // 生成资金记录
            User user = userService.get(rechargeRecord.getUserId());
            Capital capitalFee = null;
            Capital capital = new Capital(userFinance.getUserId(), CapitalMethod.RECHARGE,
                    CapitalType.CREDIT,
                    rechargeAmount,
                    userFinance,
                    orderNo,
                    user.getLoginName(),
                    WebUtils.getRemoteIp(WebUtils.getHttpRequest()),
                    paymentOrder.getMethod().getDisplayName() + "充值",
                    null);
            if (rechargeRecord.getAllFee().compareTo(BigDecimal.ZERO) > 0) {
                userFinance.subtractBalance(rechargeRecord.getAllFee(), false);
                userFinanceService.update(userFinance);
                capitalFee = new Capital(userFinance.getUserId(), CapitalMethod.RECHARGE_FEE,
                        CapitalType.DEBIT,
                        rechargeRecord.getAllFee(),
                        userFinance,
                        orderNo,
                        user.getLoginName(),
                        WebUtils.getRemoteIp(WebUtils.getHttpRequest()),
                        "充值手续费",
                        null);
            }

            userFinanceService.flush();
            Order order = orderService.addOrMergeOrder(new OrderVo(userFinance.getAvailable(),
                    userFinance.getAvailable(),
                    OrderStatus.SUCCESS,
                    OrderType.RECHARGE,
                    rechargeRecord.getId(),
                    paymentOrder.getExtOrderNo()
            ));
            order.setAmountReceived(rechargeRecord.getActualAmount());
            order.setPayeeThirdFee(rechargeRecord.getActualIpsFee());
            orderService.merge(order);
            capital.setOrderId(order.getId());
            capitalService.addUserCapital(capital);
            if (rechargeRecord.getAllFee().compareTo(BigDecimal.ZERO) > 0) {
                capitalFee.setOrderId(order.getId());
                capitalService.addUserCapital(capitalFee);
            }

            messagePushService.rechargeSuccPush(paymentOrder.getUserId(), paymentOrder.getAmount());
        } catch (Exception e) {
            logger.error("recharge fail order {}, {}", orderNo, ExceptionUtils.getMessage(e));
        } finally {
            distributeLock.unLock(LockStack.USER_LOCK, orderNo);
        }

    }

    @Override
    public RechargeRecord findByOrderNo(String orderNo){
        return rechargeRecordDao.findByOrderNo(orderNo);
    }

    @Override
    public PageResult<RechargeRecord> findPageByUser(Integer userId, PageCriteria criteria, RecordStatus status) {
        return rechargeRecordDao.findPageByUser(userId, criteria, status);
    }

    @Override
    public List<RechargeRecord> findByUser(Integer userId) {
        return rechargeRecordDao.findByUser(userId);
    }

}
