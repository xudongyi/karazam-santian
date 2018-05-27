package com.klzan.p2p.service.transfer.impl;

import com.klzan.core.Result;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.SnUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.capital.CapitalDao;
import com.klzan.p2p.dao.capital.PaymentOrderDao;
import com.klzan.p2p.dao.capital.PlatformCapitalDao;
import com.klzan.p2p.dao.investment.InvestmentDao;
import com.klzan.p2p.dao.investment.InvestmentRecordDao;
import com.klzan.p2p.dao.postloan.RepaymentPlanDao;
import com.klzan.p2p.dao.transfer.TransferDao;
import com.klzan.p2p.dao.user.UserDao;
import com.klzan.p2p.dao.user.UserFinanceDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import com.klzan.p2p.service.schedule.ScheduleJobService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.setting.TransferSetting;
import com.klzan.p2p.util.AccountantUtils;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.util.CronExpressionUtils;
import com.klzan.p2p.vo.transfer.TransferVo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.ProjectSettlementBatchRequest;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.module.PayModule;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.LockMode;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 转让
 */
@Service
public class TransferServiceImpl extends BaseService<Transfer> implements TransferService {

    @Inject
    private TransferDao transferDao;

    @Inject
    private BorrowingDao borrowingDao;

    @Inject
    private RepaymentPlanDao repaymentPlanDao;

    @Inject
    private AccountantService accountantService;

    @Inject
    private OrderService orderService;

    @Inject
    private ScheduleJobService scheduleJobService;

    @Inject
    private InvestmentDao investmentDao;

    @Inject
    private InvestmentRecordDao investmentRecordDao;

    @Inject
    private PaymentOrderDao paymentOrderDao;

    @Inject
    private UserFinanceDao userFinanceDao;

    @Inject
    private CapitalDao capitalDao;

    @Inject
    private PlatformCapitalDao platformCapitalDao;

    @Inject
    private SettingUtils setting;
    @Inject
    private CpcnSettlementService cpcnSettlementService;
    @Inject
    private UserService userService;

    @Inject
    private DistributeLock lock;

    @Override
    public List<Transfer> findList(Integer borrowingId) {
        return transferDao.findList(borrowingId);
    }

    @Override
    public List<Transfer> findList(Integer borrowingId, Integer userId) {
        return transferDao.findList(borrowingId, userId);
    }

    @Override
    public PageResult<Transfer> findPage(PageCriteria pageCriteria) {
        return transferDao.findPage(pageCriteria);
    }

    @Override
    public PageResult<Transfer> findPage(PageCriteria pageCriteria, Map params) {
        PageResult<Transfer> page = myDaoSupport.findPage("com.klzan.p2p.mapper.TransferMapper.findList", params, pageCriteria);
        for (Transfer transfer : page.getRows()) {
            Date begin = transfer.getCreateDate();
            Date nextRepaymentDate = null;
            Date lastRepaymentDate = null;
            Borrowing borrowing = borrowingDao.get(transfer.getBorrowing());
            List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findList(transfer.getBorrowing(), transfer.getId());
            Integer residualPeriod = 0;
            for (RepaymentPlan repaymentPlan : repaymentPlans) {
                if (nextRepaymentDate == null || nextRepaymentDate.after(repaymentPlan.getRepaymentRecordPayDate())) {
                    nextRepaymentDate = repaymentPlan.getRepaymentRecordPayDate();
                }
                lastRepaymentDate = repaymentPlan.getRepaymentRecordPayDate();
                if (repaymentPlan.getState() == RepaymentState.REPAYING) {
                    residualPeriod++;
                }
                if (repaymentPlan.getPaidDate() != null
                        && DateUtils.compareTwoDate(repaymentPlan.getRepaymentRecordPayDate(), repaymentPlan.getPaidDate()) == 1) {
//                    begin = DateUtils.addDays(repaymentPlan.getRepaymentRecordPayDate(), 1);
                    begin = repaymentPlan.getRepaymentRecordPayDate();
                }
            }
            if (borrowing.getPeriodUnit() == PeriodUnit.DAY) {
                if (begin.getTime() < borrowing.getInterestBeginDate().getTime()) {
                    begin = borrowing.getInterestBeginDate();
                }
                residualPeriod = new Double(DateUtils.getDaysOfTwoDate(lastRepaymentDate, DateUtils.getMinDateOfDay(begin))).intValue();
            }

            transfer.setResidualPeriod(residualPeriod);
            transfer.setResidualUnit(borrowing.getPeriodUnitDes());
            transfer.setNextRepaymentDate(nextRepaymentDate);
            transfer.setRepaymentMethod(borrowing.getRepaymentMethod());
        }
        return page;
    }

    @Override
    public PageResult<Transfer> findPage(PageCriteria pageCriteria, TransferLoanState state, Integer userId) {
        return transferDao.findPage(pageCriteria, state, userId);
    }

    @Override
    public PageResult<TransferVo> findPageVo(PageCriteria pageCriteria, Integer userId) {

        PageResult<TransferVo> page = transferDao.findPage(pageCriteria, userId, true);

        for (TransferVo vo : page.getRows()) {
            Date nextRepaymentDate = null;
            int paidPeriod = 0;
            BigDecimal totalCapitalInterest = BigDecimal.ZERO;
            BigDecimal totalCurrentClaimTotalValue = BigDecimal.ZERO;
            BigDecimal recoveriedAmount = BigDecimal.ZERO;
            BigDecimal residualCapital = BigDecimal.ZERO;
            Boolean canTransfer = true;
            Boolean transfering = false;
            Borrowing borrowing = borrowingDao.get(vo.getBorrowingId());
            List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findListCanTransfer(vo.getBorrowingId(), userId);
            for (RepaymentPlan repaymentPlan : repaymentPlans) {
                if (repaymentPlan.getState().equals(RepaymentState.REPAID)) {
                    if (repaymentPlan.getRepaymentRecord().getPeriod() > paidPeriod) {
                        paidPeriod = repaymentPlan.getRepaymentRecord().getPeriod();
                    }
                    recoveriedAmount = recoveriedAmount.add(repaymentPlan.getRecoveryAmount());
                    continue;
                }
                if (nextRepaymentDate == null || nextRepaymentDate.after(repaymentPlan.getRepaymentRecordPayDate())) {
                    nextRepaymentDate = repaymentPlan.getRepaymentRecordPayDate();
                }
                residualCapital = residualCapital.add(repaymentPlan.getCapital());
                totalCapitalInterest = totalCapitalInterest.add(repaymentPlan.getCapitalInterest());
                totalCurrentClaimTotalValue = totalCurrentClaimTotalValue.add(accountantService.calCurrentSurplusValue(repaymentPlan, null).getTotalValue());
                if (repaymentPlan.getIsOverdue()) {
                    canTransfer = false;
                }
                if (repaymentPlan.getTransferState().equals(TransferState.TRANSFERING)) {
                    transfering = true;
                }
            }
            if (!borrowing.getProgress().equals(BorrowingProgress.REPAYING)) {
                canTransfer = false;
            }
            vo.setRecoveriedAmount(recoveriedAmount);
            vo.setResidualCapital(residualCapital);
            vo.setInterestRate(borrowing.getRealInterestRate());
            vo.setNextRepaymentDate(nextRepaymentDate);
            vo.setSurplusPeriod((borrowing.getRepayPeriod() - paidPeriod) + "/" + borrowing.getRepayPeriod());
            vo.setCapitalInterest(totalCapitalInterest);
            vo.setCurrentClaimTotalValue(totalCurrentClaimTotalValue);
            vo.setCanTransfer(canTransfer);
            vo.setTransfering(transfering);
            if (!canTransfer) {
                vo.setState("不可转让");
            } else {
                if (transfering) {
                    vo.setState("转让中");
                } else {
                    vo.setState("可转让");
                }
            }
        }
        return page;
    }

    @Override
    public PageResult<TransferVo> findBuyInPageVo(PageCriteria pageCriteria, Integer userId) {
        Map map = new HashMap();
        map.put("investor", userId);
        PageResult<TransferVo> page = myDaoSupport.findPage("com.klzan.p2p.mapper.TransferMapper.findBuyInList", map, pageCriteria);
        for (TransferVo vo : page.getRows()) {

        }
        return page;
    }

    @Override
    public void transferOut(Borrowing borrowing, User currentUser) {

        int paidPeriod = 0; //已还期次
        BigDecimal totalCurrentClaimTotalPrice = BigDecimal.ZERO;  //债权价格（剩余本金）
        BigDecimal totalCurrentClaimTotalValue = BigDecimal.ZERO;
        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findListCanTransfer(borrowing.getId(), currentUser.getId());//currentUser.getId());
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            if (repaymentPlan.getState().equals(RepaymentState.REPAID)) {
                if (repaymentPlan.getRepaymentRecord().getPeriod() > paidPeriod) {
                    paidPeriod = repaymentPlan.getRepaymentRecord().getPeriod();
                }
                continue;
            }
            totalCurrentClaimTotalPrice = totalCurrentClaimTotalPrice.add(repaymentPlan.getCapital());
            totalCurrentClaimTotalValue = totalCurrentClaimTotalValue.add(accountantService.calCurrentSurplusValue(repaymentPlan, null).getTotalValue());
        }

        if (totalCurrentClaimTotalPrice.intValue() % 100 != 0) {
            throw new BusinessProcessException("转让金额必须是100的整数倍");
        }

        TransferSetting transferSetting = setting.getTransferSetting();
        Integer purchaseDays = transferSetting.getPurchaseDays();

        //新增债权转让
        Transfer transfer = new Transfer();
        transfer.setState(TransferLoanState.TRANSFERING);
        transfer.setBorrowing(borrowing.getId());
        transfer.setExpireDate(DateUtils.addDays(new Date(), purchaseDays));
        transfer.setTransfer(currentUser.getId());
        transfer.setCapital(totalCurrentClaimTotalPrice);
        transfer.setOutFee(AccountantUtils.calFee(totalCurrentClaimTotalPrice, borrowing.getOutTransferFeeRate()));
        transfer.setType(borrowing.getType());
        transfer.setTitle(borrowing.getTitle());
        if (borrowing.getRepaymentMethod().equals(RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST)) {
            transfer.setSurplusPeriod((borrowing.getRepayPeriod() - paidPeriod) + "/1");
        } else {
            transfer.setSurplusPeriod((borrowing.getRepayPeriod() - paidPeriod) + "/" + borrowing.getRepayPeriod());
        }
        transfer.setInterestRate(borrowing.getRealInterestRate());
        transfer.setWorth(totalCurrentClaimTotalValue);
        transfer = transferDao.persist(transfer);

        //更新还款计划
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            if (repaymentPlan.getState().equals(RepaymentState.REPAID)) {
                continue;
            }
            repaymentPlan.setTransfer(transfer.getId());
            repaymentPlan.setTransferState(TransferState.TRANSFERING);
            if (repaymentPlan.getTransferEveryInterest() == null || repaymentPlan.getTransferEveryInterest().compareTo(BigDecimal.ZERO) <= 0) {
                repaymentPlan.setTransferEveryInterest(AccountantUtils.calAverage(repaymentPlan.getInterest(), totalCurrentClaimTotalPrice.intValue() / 100));
            }
        }

        ScheduleJob job = new ScheduleJob();
        job.setBeanName("transferExpireTask");
        job.setMethodName("expire");
        job.setParams(transfer.getId().toString());
        job.setPlanCount(1);
        job.setStatus(0);
        job.setCronExpression(CronExpressionUtils.getCron(transfer.getExpireDate()));
        job.setRemark(transfer.getId() + "债权转让定时任务");
        scheduleJobService.addJob(job);
    }

    @Override
    public void transferCancel(Transfer transfer) {

        //更新转让标
        transfer.setState(TransferLoanState.CANCEL);
        transfer = transferDao.update(transfer);

        //更新还款计划
        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findListCanTransfer(transfer.getBorrowing(), transfer.getTransfer());
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            if (repaymentPlan.getState().equals(RepaymentState.REPAID)) {
                continue;
            }
            repaymentPlan.setTransfer(transfer.getId());
            repaymentPlan.setTransferState(TransferState.RECOVER);
            repaymentPlanDao.update(repaymentPlan);
        }
        repaymentPlanDao.flush();

    }

    @Override
    public String transferIn(Transfer transfer, Investment investment, InvestmentRecord investmentRecord, User currentUser, Integer parts) {

        // 投资人余额校验
        transferDao.refresh(transfer);
        transferDao.lock(transfer, LockMode.PESSIMISTIC_WRITE);
        if (transfer.getState().equals(TransferLoanState.CANCEL)) {
            throw new BusinessProcessException("转让已撤销");
        }

        // 备注参数
        String remoteIp = "";
        String loginName = "";

        // 参数
        BigDecimal transferCapital = new BigDecimal(parts * 100);  //债权价格  原始本金
        BigDecimal totalFeeIn = BigDecimal.ZERO;  // 转入服务费
        BigDecimal totalFeeOut = BigDecimal.ZERO;  // 转出服务费
        String orderNo = SnUtils.getOrderNo();
        Borrowing borrowing = borrowingDao.get(transfer.getBorrowing());
        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findListCanTransfer(transfer.getBorrowing(), transfer.getTransfer());
        List<RepaymentPlan> newRpIns = new ArrayList<>();
//		List<RepaymentPlan> newRpOuts = new ArrayList<>();
//		List<RepaymentPlan> repaymentPlanOlds = new ArrayList<>();

        //更新投资
        investment.setState(InvestmentState.SUCCESS);
        investmentDao.merge(investment);
        investmentRecord.setState(InvestmentState.SUCCESS);
        investmentRecordDao.merge(investmentRecord);

        //更新原还款计划 新增还款计划
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            if (repaymentPlan.getState().equals(RepaymentState.REPAID)) {
                continue;
            }

            repaymentPlan = accountantService.calCurrentSurplusValue(repaymentPlan, parts, transfer.getSurplusParts(), transfer.getMaxParts());
            RepaymentPlan newRpIn = null;
            RepaymentPlan newRpOut = null;
            RepaymentRecord newRrIn = null;
            RepaymentRecord newRrOut = null;
            try {
                newRpIn = (RepaymentPlan) BeanUtils.cloneBean(repaymentPlan);
                newRpOut = (RepaymentPlan) BeanUtils.cloneBean(repaymentPlan);
                newRrIn = (RepaymentRecord) BeanUtils.cloneBean(repaymentPlan.getRepaymentRecord());
                newRrOut = (RepaymentRecord) BeanUtils.cloneBean(repaymentPlan.getRepaymentRecord());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("转让失败");
            }

//			if(parts.compareTo(transfer.getSurplusParts()) == -1 || (parts.compareTo(transfer.getSurplusParts()) == 0 && true)){ //部分转让 和 剩余部分全转让的当期非第一天     拆分
//				// 还款计划价值 = 已过部分价值 + （ 转让部分 + 未转让部分 ）
//				// ID 投资人 投资 本金 利息 服务费 订单号 转让状态 转让服务费（转出/转入）
//				newRpIn.setId(null);
//				newRpIn.setInvestor(currentUser.getId());
//				newRpIn.setInvestment(pInvestment.getId());
//				newRrIn.setCapital(repaymentPlan.getCapitalValue());
//				newRrIn.setInterest(repaymentPlan.getInterestValue());
//				newRpIn.setRepaymentRecord(newRrIn);
//				newRpIn.setRecoveryFee(AccountantUtils.calFee(newRpIn.getCapital(), borrowing.getRecoveryFeeRate()));
//				newRpIn.setOrderNo(orderNo);
//				newRpIn.setTransferState(TransferState.transferin);
//				newRpIn.setTransferFeeIn(AccountantUtils.calFee(newRpIn.getCapital(), borrowing.getInTransferFeeRate()));
//				newRpIn.setTransferFeeOut(BigDecimal.ZERO);
//				repaymentPlanDao.create(newRpIn);
//
//				// 有剩余金额时，为转让人产生新还款计划
//				newRpOut.setId(null);
//				newRrOut.setCapital(repaymentPlan.getCapital().subtract(repaymentPlan.getCapitalValue()));
//				newRrOut.setInterest(repaymentPlan.getInterest().subtract(repaymentPlan.getInterestValue()));
//				newRpOut.setRepaymentRecord(newRrOut);
//				newRpOut.setRecoveryFee(AccountantUtils.calFee(newRrOut.getCapital(), borrowing.getRecoveryFeeRate()));
//				newRpOut.setTransferState(TransferState.transfering);
//				BigDecimal transferFeeOut = repaymentPlan.getTransferFeeOut()==null?AccountantUtils.calFee(transferCapital, borrowing.getOutTransferFeeRate())
//						:repaymentPlan.getTransferFeeOut().add(AccountantUtils.calFee(transferCapital, borrowing.getOutTransferFeeRate()));
//				newRpOut.setTransferFeeOut(transferFeeOut);
//				repaymentPlanDao.create(newRpOut);
//			}else if(parts.compareTo(transfer.getSurplusParts()) == 0){ //剩余部分全转让    不拆分
//				// 还款计划价值 = 已过部分价值 + （ 转让部分 + 未转让部分 ）
//				// ID 投资人 投资 本金 利息 服务费 订单号 转让状态 转让服务费（转出/转入）
//				newRpIn.setId(null);
//				newRpIn.setInvestor(currentUser.getId());
//				newRpIn.setInvestment(pInvestment.getId());
//				newRrIn.setCapital(repaymentPlan.getCapital());
//				newRrIn.setInterest(repaymentPlan.getInterest());
//				newRpIn.setRepaymentRecord(newRrIn);
//				newRpIn.setRecoveryFee(AccountantUtils.calFee(newRpIn.getCapital(), borrowing.getRecoveryFeeRate()));
//				newRpIn.setOrderNo(orderNo);
//				newRpIn.setTransferState(TransferState.transferin);
//				newRpIn.setTransferFeeIn(AccountantUtils.calFee(newRpIn.getCapital(), borrowing.getInTransferFeeRate()));
//				newRpIn.setTransferFeeOut(BigDecimal.ZERO);
//				repaymentPlanDao.create(newRpIn);
//			}else {
//				throw new RuntimeException("系统错误");
//			}
//
//			totalFeeIn = totalFeeIn.add(newRpIn.getTransferFeeIn());
//			totalFeeOut = totalFeeOut.add(AccountantUtils.calFee(transferCapital, borrowing.getOutTransferFeeRate()));
//
//			repaymentPlan.setTransferState(TransferState.transferout);
//			repaymentPlanDao.update(repaymentPlan);


            if (parts.compareTo(transfer.getSurplusParts()) <= 0 /*repaymentPlan.getTotalValue().compareTo(repaymentPlan.getCapitalInterest()) <= 0*/) {// 未转让完 (当期已过部分 + 当期未过部分（转让部分 + 未转让部分）  /  转让完成
                // 还款计划价值 = 已过部分价值 + （ 转让部分 + 未转让部分 ）
                // ID 投资人 投资 本金 利息 服务费 订单号 转让状态 转让服务费（转出/转入）
                newRpIn.setId(null);
                newRpIn.setInvestor(currentUser.getId());
                newRpIn.setInvestment(investment.getId());
                newRrIn.setCapital(repaymentPlan.getCapitalValue());
                newRrIn.setInterest(repaymentPlan.getInterestValue());
                newRpIn.setRepaymentRecord(newRrIn);
                newRpIn.setRecoveryFee(AccountantUtils.calFee(newRpIn.getCapital(), borrowing.getRecoveryFeeRate()));
                newRpIn.setOrderNo(SnUtils.getOrderNo());
                newRpIn.setTransferState(TransferState.TRANSFER_IN);
                newRpIn.setTransferFeeIn(AccountantUtils.calFee(newRpIn.getCapital(), borrowing.getInTransferFeeRate()));
                newRpIn.setTransferFeeOut(BigDecimal.ZERO);
                repaymentPlanDao.persist(newRpIn);
                newRpIns.add(newRpIn);

                if (repaymentPlan.getTotalValue().compareTo(repaymentPlan.getCapitalInterest()) != 0) { // 有剩余金额时，为转让人产生新还款计划
                    newRpOut.setId(null);
                    newRrOut.setCapital(repaymentPlan.getCapital().subtract(repaymentPlan.getCapitalValue()));
                    newRrOut.setInterest(repaymentPlan.getInterest().subtract(repaymentPlan.getInterestValue()));
                    newRpOut.setRepaymentRecord(newRrOut);
                    newRpOut.setRecoveryFee(AccountantUtils.calFee(newRrOut.getCapital(), borrowing.getRecoveryFeeRate()));
                    newRpOut.setOrderNo(SnUtils.getOrderNo());
                    newRpOut.setTransferState(TransferState.TRANSFERING);
                    BigDecimal transferFeeOut = repaymentPlan.getTransferFeeOut() == null ? AccountantUtils.calFee(transferCapital, borrowing.getOutTransferFeeRate())
                            : repaymentPlan.getTransferFeeOut().add(AccountantUtils.calFee(transferCapital, borrowing.getOutTransferFeeRate()));
                    newRpOut.setTransferFeeOut(transferFeeOut);
                    repaymentPlanDao.persist(newRpOut);
//					newRpOuts.add(newRpOut);
                }
            } else {
                throw new RuntimeException("系统错误");
            }

            totalFeeIn = totalFeeIn.add(newRpIn.getTransferFeeIn());

            repaymentPlan.setTransferState(TransferState.TRANSFER_OUT);
            repaymentPlanDao.update(repaymentPlan);

//			repaymentPlanOlds.add(repaymentPlan);
        }

        totalFeeOut = totalFeeOut.add(AccountantUtils.calFee(transferCapital, borrowing.getOutTransferFeeRate()));
        // 更新转让标
        if (parts.equals(transfer.getSurplusParts())) {
            transfer.setTransferedCapital(transfer.getTransferedCapital().add(transferCapital));
            transfer.setLastDate(new Date());
            transfer.setFullDate(new Date());
            transfer.setState(TransferLoanState.TRANSFERED);
            transfer = transferDao.update(transfer);
        } else {
            transfer.setTransferedCapital(transfer.getTransferedCapital().add(transferCapital));
            transfer.setLastDate(new Date());
            transfer.setState(TransferLoanState.TRANSFERPART);
            transfer = transferDao.update(transfer);
        }

        //更新用户资金 产生转入转出资金 平台资金
        UserFinance transferFinance = userFinanceDao.getByUserId(transfer.getTransfer()); // 原投资人/转让人
        UserFinance currentFinance = userFinanceDao.getByUserId(currentUser.getId()); // 新投资人/受让人
        transferFinance.addBalance(transferCapital, RechargeBusinessType.GENERAL);
        transferFinance.subtractCredit(AccountantUtils.getRecoveryAmountInterestFee(newRpIns, currentUser.getId()));
        userFinanceDao.update(transferFinance);

        Capital capitalFee = null;
        PlatformCapital huashanCapital = null;
        Capital capital = new Capital(transfer.getTransfer(),
                CapitalMethod.TRANSFER,
                CapitalType.CREDIT,
                transferCapital,
                transferFinance,
                SnUtils.getOrderNo(),
                loginName,
                remoteIp,
                "转让人转出",
                null);
        if (totalFeeOut.compareTo(BigDecimal.ZERO) > 0) {
            transferFinance.subtractBalance(totalFeeOut, false);
            userFinanceDao.update(transferFinance);
            capitalFee = new Capital(transfer.getTransfer(),
                    CapitalMethod.TRANSFER_OUT_FEE,
                    CapitalType.DEBIT,
                    totalFeeOut,
                    transferFinance,
                    orderNo,
                    loginName,
                    remoteIp,
                    "转让人转出服务费",
                    null);
            huashanCapital = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.TRANSFER_OUT_FEE, totalFeeOut, BigDecimal.ZERO, capital.getId(), "平台收取转让人转出服务费", loginName, remoteIp, orderNo);
        }

        Order order = orderService.updateOrderStatus(OrderType.TRANSFER_OUT, investmentRecord.getId(), OrderStatus.SUCCESS, investmentRecord.getOrderNo());
        capital.setOrderId(order.getId());
        capitalDao.persist(capital);
        if (totalFeeOut.compareTo(BigDecimal.ZERO) > 0) {
            capitalFee.setOrderId(order.getId());
            capitalDao.persist(capitalFee);
            platformCapitalDao.persist(huashanCapital);
        }

        currentFinance.subtractBalance(transferCapital, false);
//		currentFinance.addCredit(transferCapital.add(AccountantUtils.calExpense(transferCapital, borrowing.getRealInterestRate())).subtract(AccountantUtils.calFee(transferCapital, borrowing.getRecoveryFeeRate())));
        currentFinance.addCredit(AccountantUtils.getRecoveryAmountInterestFee(newRpIns, currentUser.getId()));
        userFinanceDao.update(currentFinance);

        Capital capitalFeeIn = null;
        PlatformCapital huashanCapitalIn = null;
        Capital capitalIn = new Capital(currentUser.getId(),
                CapitalMethod.TRANSFEREE,
                CapitalType.DEBIT,
                transferCapital,
                currentFinance,
                orderNo,
                loginName,
                remoteIp,
                "受让人转入",
                null);
        if (totalFeeIn.compareTo(BigDecimal.ZERO) > 0) {
            currentFinance.subtractBalance(totalFeeIn, false);
            userFinanceDao.update(currentFinance);
            capitalFeeIn = new Capital(currentUser.getId(),
                    CapitalMethod.TRANSFER_IN_FEE,
                    CapitalType.DEBIT,
                    totalFeeIn,
                    currentFinance,
                    orderNo,
                    loginName,
                    remoteIp,
                    "受让人转入服务费",
                    null);
            huashanCapitalIn = new PlatformCapital(CapitalType.CREDIT,
                    CapitalMethod.TRANSFER_IN_FEE,
                    totalFeeIn,
                    BigDecimal.ZERO,
                    capital.getId(),
                    "平台收取受让人转入服务费",
                    loginName,
                    remoteIp,
                    orderNo);
        }
        order = orderService.updateOrderStatus(OrderType.TRANSFER_IN, investmentRecord.getId(), OrderStatus.SUCCESS, investmentRecord.getOrderNo());
        capitalIn.setOrderId(order.getId());
        capitalDao.persist(capitalIn);
        if (totalFeeIn.compareTo(BigDecimal.ZERO) > 0) {
            capitalFeeIn.setOrderId(order.getId());
            capitalDao.persist(capitalFeeIn);
            platformCapitalDao.persist(huashanCapitalIn);
        }

        return orderNo;
    }

    @Override
    public Result transferInSucceed(String sn, Transfer transfer, User currentUser, Integer parts) {
        try {
            lock.lock(LockStack.INVESTMENT_LOCK, sn);
            transferDao.refresh(transfer);
            transferDao.lock(transfer, LockMode.PESSIMISTIC_WRITE);
            if (transfer.getState().equals(TransferLoanState.CANCEL)) {
                throw new RuntimeException("转让已撤销");
            }

            Borrowing borrowing = borrowingDao.get(transfer.getBorrowing());
            if (borrowing == null) {
                throw new RuntimeException("转让已撤销");
            }

            PaymentOrder paymentOrder = paymentOrderDao.findByOrderNo(sn);

            // 参数
            BigDecimal transferCapital = new BigDecimal(parts * 100);  //债权价格  原始本金
            BigDecimal feeIn = AccountantUtils.calFee(transferCapital, borrowing.getInTransferFeeRate()); // 转入服务费
            BigDecimal feeOut = AccountantUtils.calFee(transferCapital, borrowing.getOutTransferFeeRate()); // 转出服务费
//        BigDecimal feeIn = transferCapital.multiply(borrowing.getInTransferFeeRate()).divide(new BigDecimal(100));
//        BigDecimal feeOut = transferCapital.multiply(borrowing.getOutTransferFeeRate()).divide(new BigDecimal(100));
            List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findListCanTransfer(transfer.getBorrowing(), transfer.getTransfer());
            List<RepaymentPlan> newRpIns = new ArrayList<>();

            //更新投资
            Investment investment = investmentDao.findByOrderNo(sn);
            investment.setState(InvestmentState.SUCCESS);
            investmentDao.merge(investment);
            InvestmentRecord investmentRecord = investmentRecordDao.findByOrderNo(sn);
            investmentRecord.setState(InvestmentState.SUCCESS);
            investmentRecordDao.merge(investmentRecord);

//        //新增投资
//        Investment pInvestment = new Investment();
//        pInvestment.setState(InvestmentState.PAID);
//        pInvestment.setAmount(transferCapital);
//        pInvestment.setPreferentialAmount(BigDecimal.ZERO);
//        pInvestment.setBorrowing(borrowing.getId());
//        pInvestment.setInvestor(currentUser.getId());
//        pInvestment.setOrderNo(sn);
//        pInvestment.setTransfer(transfer.getId());
//        pInvestment = investmentDao.persist(pInvestment);
//        // TODO 生成投资记录
//        InvestmentRecord pInvestmentRecord = new InvestmentRecord();
//        pInvestmentRecord.setOperationMethod(OperationMethod.MANUAL);
//        pInvestmentRecord.setMethod(paymentOrder.getMethod());
//        pInvestmentRecord.setAmount(transferCapital);
//        pInvestmentRecord.setPreferentialAmount(BigDecimal.ZERO);
//        pInvestmentRecord.setBorrowing(borrowing.getId());
//        pInvestmentRecord.setInvestor(currentUser.getId());
//        pInvestmentRecord.setOrderNo(paymentOrder.getOrderNo());
//        pInvestmentRecord.setOperator(CommonUtils.getLoginName());
//        pInvestmentRecord.setIp(CommonUtils.getRemoteIp());
//        pInvestmentRecord.setDeviceType(DeviceType.PC);
//        pInvestmentRecord.setState(InvestmentState.PAID);
//        pInvestmentRecord.setTransfer(true);
//        pInvestmentRecord.setTransferId(transfer.getId());
//        pInvestmentRecord.setInvestment(pInvestment.getId());
//        investmentRecordDao.persist(pInvestmentRecord);

//        InvestmentRecord investmentRecord = investmentRecordDao.find(transfer.getInvestmentRecord());
//        Investment investment = investmentDao.find(investmentRecord.getInvestment());
//        BigDecimal transferRatio = accountantService.calTransferRatio(investmentRecordDao.find(transfer.getInvestmentRecord()));

            //更新原还款计划 新增还款计划
            for (RepaymentPlan repaymentPlan : repaymentPlans) {
                if (repaymentPlan.getState().equals(RepaymentState.REPAID)) {
                    continue;
                }

                repaymentPlan = accountantService.calCurrentSurplusValue(repaymentPlan, parts, transfer.getSurplusParts(), transfer.getMaxParts());
                RepaymentPlan newRpIn = null;
                RepaymentPlan newRpOut = null;
                RepaymentRecord newRrIn = null;
                RepaymentRecord newRrOut = null;
                try {
                    newRpIn = (RepaymentPlan) BeanUtils.cloneBean(repaymentPlan);
                    newRpOut = (RepaymentPlan) BeanUtils.cloneBean(repaymentPlan);
                    newRrIn = (RepaymentRecord) BeanUtils.cloneBean(repaymentPlan.getRepaymentRecord());
                    newRrOut = (RepaymentRecord) BeanUtils.cloneBean(repaymentPlan.getRepaymentRecord());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("转让失败");
                }

                System.out.println("---------购买份数 、 剩余份数-------------");
                System.out.println(parts);
                System.out.println(transfer.getSurplusParts());
                if (parts.compareTo(transfer.getSurplusParts()) <= 0) {
                    if (parts.compareTo(transfer.getSurplusParts()) == 0 && repaymentPlan.getInterest().subtract(repaymentPlan.getInterestValue()).compareTo(BigDecimal.ONE) < 0) {
                        // TODO 转让完并且剩余利息小于1元
                        newRpIn.setId(null);
                        newRpIn.setInvestor(currentUser.getId());
                        newRpIn.setInvestment(investment.getId());
                        newRrIn.setCapital(repaymentPlan.getCapital());
                        newRrIn.setInterest(repaymentPlan.getInterest());
                        newRpIn.setRepaymentRecord(newRrIn);
                        newRpIn.setRecoveryFee(AccountantUtils.calFee(newRpIn.getCapital(), borrowing.getRecoveryFeeRate()));
                        newRpIn.setOrderNo(sn);
                        newRpIn.setTransferState(TransferState.TRANSFER_IN);
                        newRpIn.setTransferFeeIn(AccountantUtils.calFee(newRpIn.getCapital(), borrowing.getInTransferFeeRate()));
                        newRpIn.setTransferFeeOut(BigDecimal.ZERO);
                        repaymentPlanDao.persist(newRpIn);
                        newRpIns.add(newRpIn);
                    } else {
                        // TODO 未转让完或者 转让完后剩余利息大于等于1元
                        // TODO 受让人
                        newRpIn.setId(null);
                        newRpIn.setInvestor(currentUser.getId());
                        newRpIn.setInvestment(investment.getId());
                        newRrIn.setCapital(repaymentPlan.getCapitalValue());
                        newRrIn.setInterest(repaymentPlan.getInterestValue());
                        newRpIn.setRepaymentRecord(newRrIn);
                        newRpIn.setRecoveryFee(AccountantUtils.calFee(newRpIn.getCapital(), borrowing.getRecoveryFeeRate()));
                        newRpIn.setOrderNo(sn);
                        newRpIn.setTransferState(TransferState.TRANSFER_IN);
                        newRpIn.setTransferFeeIn(AccountantUtils.calFee(newRpIn.getCapital(), borrowing.getInTransferFeeRate()));
                        newRpIn.setTransferFeeOut(BigDecimal.ZERO);
                        repaymentPlanDao.persist(newRpIn);
                        newRpIns.add(newRpIn);
                        // TODO 转让人
                        newRpOut.setId(null);
                        newRrOut.setCapital(repaymentPlan.getCapital().subtract(repaymentPlan.getCapitalValue()));
                        newRrOut.setInterest(repaymentPlan.getInterest().subtract(repaymentPlan.getInterestValue()));
                        newRpOut.setRepaymentRecord(newRrOut);
                        newRpOut.setRecoveryFee(AccountantUtils.calFee(newRrOut.getCapital(), borrowing.getRecoveryFeeRate()));
                        newRpOut.setTransferState(TransferState.TRANSFERING);
                        if (parts.compareTo(transfer.getSurplusParts()) == 0) {
                            newRpOut.setTransferState(TransferState.RECOVER);
//                        newRpOut.setTransferOver(true);
//                        newRpOut.setTransferOrderNo(sn);
                        }
                        newRpOut.setTransferFeeIn(BigDecimal.ZERO);
                        newRpOut.setTransferFeeOut(repaymentPlan.getTransferFeeOut().add(AccountantUtils.calFee(newRpIn.getCapital(), borrowing.getOutTransferFeeRate())));
                        repaymentPlanDao.persist(newRpOut);
                    }
                } else {
                    throw new RuntimeException("系统错误");
                }
                repaymentPlan.setTransferState(TransferState.TRANSFER_OUT);
                repaymentPlanDao.merge(repaymentPlan);
            }

//        //更新原投资 投资记录
//        investmentRecord.setTransferedAmount(investmentRecord.getTransferedAmount().add(transferCapital));
//        if(parts.compareTo(transfer.getSurplusParts()) == 0){
//            investmentRecord.setTransferState(TransferState.general);
//        }
//        investmentRecordDao.merge(investmentRecord);
//        investment.setTransferedAmount(investment.getTransferedAmount().add(transferCapital));
//        investmentDao.merge(investment);

            // 更新转让标
            if (parts.equals(transfer.getSurplusParts())) {
                transfer.setTransferedCapital(transfer.getTransferedCapital().add(transferCapital));
                transfer.setLastDate(new Date());
                transfer.setFullDate(new Date());
                transfer.setState(TransferLoanState.TRANSFERED);
                transfer.setSurplusWorth(BigDecimal.ZERO);
                transfer = transferDao.update(transfer);
            } else {
                transfer.setTransferedCapital(transfer.getTransferedCapital().add(transferCapital));
                transfer.setLastDate(new Date());
                transfer.setState(TransferLoanState.TRANSFERPART);
                transfer.setSurplusWorth(transfer.getWorth().multiply(transfer.getCapital().subtract(transfer.getTransferedCapital()).divide(transfer.getCapital(), 6, BigDecimal.ROUND_HALF_EVEN)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                transfer = transferDao.update(transfer);
            }

            String sns = SnUtils.getOrderNo();
            List<PaymentOrder> orders = new ArrayList<>();

            CpcnSettlement cpcnSettlement = new CpcnSettlement();
            cpcnSettlement.setType(PaymentOrderType.TRANSFER);
            cpcnSettlement.setBorrowing(borrowing.getId());
            cpcnSettlement.setTransfer(transfer.getId());
            cpcnSettlement.setsOrderNo(sns);
            cpcnSettlement.setsStatus(CpcnSettlementStatus.unsettled);
            cpcnSettlement = cpcnSettlementService.persist(cpcnSettlement);

            // TODO 转出订单
            PaymentOrder paymentOut = new PaymentOrder();
            paymentOut.setParentOrderNo(sns);
            paymentOut.setOrderNo(SnUtils.getOrderNo());
            paymentOut.setStatus(PaymentOrderStatus.PROCESSING);
            paymentOut.setType(PaymentOrderType.TRANSFER);
            paymentOut.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
            paymentOut.setAmount(transferCapital);
            paymentOut.setFee(feeOut);
            paymentOut.setMemo(String.format("债权转出,金额￥[%s],服务费￥[%s]", transferCapital, feeOut));
            paymentOut.setOperator(CommonUtils.getLoginName());
            paymentOut.setIp(CommonUtils.getRemoteIp());
            paymentOut.setUserId(transfer.getTransfer());
            paymentOut.setBorrowing(borrowing.getId());
            paymentOut.setExtOrderNo(sn); //支付投资交易流水号，还款给投资人时需要(债转时用转让支付流水号)
            paymentOrderDao.persist(paymentOut);
            orders.add(paymentOut);

            // TODO 转出服务费订单
            if (feeOut.compareTo(BigDecimal.ZERO) > 0) {
                PaymentOrder paymentOutFee = new PaymentOrder();
                paymentOutFee.setParentOrderNo(sns);
                paymentOutFee.setOrderNo(SnUtils.getOrderNo());
                paymentOutFee.setStatus(PaymentOrderStatus.PROCESSING);
                paymentOutFee.setType(PaymentOrderType.TRANSFER_FEE);
                paymentOutFee.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
                paymentOutFee.setAmount(feeOut);
                paymentOutFee.setMemo("债权转出服务费");
                paymentOutFee.setOperator(CommonUtils.getLoginName());
                paymentOutFee.setIp(CommonUtils.getRemoteIp());
                paymentOutFee.setUserId(null);
                paymentOutFee.setBorrowing(borrowing.getId());
                paymentOrderDao.persist(paymentOutFee);
                orders.add(paymentOutFee);
            }

            // TODO 转入服务费订单
            if (feeIn.compareTo(BigDecimal.ZERO) > 0) {
                PaymentOrder paymentInFee = new PaymentOrder();
                paymentInFee.setParentOrderNo(sns);
                paymentInFee.setOrderNo(SnUtils.getOrderNo());
                paymentInFee.setStatus(PaymentOrderStatus.PROCESSING);
                paymentInFee.setType(PaymentOrderType.TRANSFER_FEE);
                paymentInFee.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
                paymentInFee.setAmount(feeIn);
                paymentInFee.setMemo("债权转入服务费");
                paymentInFee.setOperator(CommonUtils.getLoginName());
                paymentInFee.setIp(CommonUtils.getRemoteIp());
                paymentInFee.setUserId(null);
                paymentInFee.setBorrowing(borrowing.getId());
                paymentOrderDao.persist(paymentInFee);
                orders.add(paymentInFee);
            }

            Order order = orderService.updateOrderStatus(OrderType.TRANSFER_IN, investmentRecord.getId(), OrderStatus.SUCCESS, investmentRecord.getOrderNo());

            // TODO 受让人资金
            UserFinance currentFinance = userFinanceDao.getByUserId(currentUser.getId());
            userFinanceDao.lock(currentFinance, LockMode.PESSIMISTIC_WRITE);
            currentFinance.subtractBalance(transferCapital);
            currentFinance.addCredit(AccountantUtils.getRecoveryAmountInterestFee(newRpIns, currentUser.getId()));
            Capital capital = new Capital(currentUser.getId(), CapitalMethod.TRANSFEREE, CapitalType.DEBIT, transferCapital, currentFinance, sn, CommonUtils.getLoginName(), CommonUtils.getRemoteIp(), "债权转入", order.getId());
            capitalDao.persist(capital);
            if (feeIn.compareTo(BigDecimal.ZERO) > 0) {
                currentFinance.subtractBalance(feeIn);
                capital = new Capital(currentUser.getId(), CapitalMethod.TRANSFER_IN_FEE, CapitalType.DEBIT, feeIn, currentFinance, sn, CommonUtils.getLoginName(), CommonUtils.getRemoteIp(), "债权转入服务费", order.getId());
                capitalDao.persist(capital);

                PlatformCapital platformCapital = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.TRANSFER_IN_FEE, feeIn, BigDecimal.ZERO, capital.getId(), "债权转入服务费", paymentOrder.getOperator(), paymentOrder.getIp(), sn);
                platformCapitalDao.persist(platformCapital);
            }
            userFinanceDao.merge(currentFinance);

            try {
                Integer investor = investmentRecord.getInvestor();
                Integer transferOuterId = transfer.getTransfer();
                User transferIner = userService.get(investor);  // 受让人
                User transferOuter = userService.get(transferOuterId);  // 转让人
                UserFinance transferInFinance = userFinanceDao.getByUserId(investor);
                UserFinance transferOutFinance = userFinanceDao.getByUserId(transferOuterId);
                BigDecimal inTransferFee = transferCapital.multiply(borrowing.getInTransferFeeRate()).divide(new BigDecimal(100));
                BigDecimal outTransferFee = transferCapital.multiply(borrowing.getOutTransferFeeRate()).divide(new BigDecimal(100));
                //平台订单 : 转让人转让债权
                order = new Order();
                order.setUserId(transferOuterId);
                order.setPayer(investor);
                order.setPayerName(transferIner.getLoginName());
                order.setPayee(transferOuterId);
                order.setPayerName(transferOuter.getLoginName());
                order.setPayerBalance(transferInFinance.getAvailable());
                order.setPayerFee(inTransferFee);
                order.setPayeeBalance(transferOutFinance.getAvailable());
                order.setPayeeFee(outTransferFee);
                order.setStatus(OrderStatus.LAUNCH);
                order.setType(OrderType.TRANSFER_OUT);
                order.setMethod(OrderMethod.CPCN);
                order.setBusiness(investmentRecord.getId());
                order.setOrderNo(sns);
                order.setThirdOrderNo(null);
                order.setAmount(investmentRecord.getAmount().subtract(investmentRecord.getPreferentialAmount()));
                order.setAmountReceived(investmentRecord.getAmount());
                order.setPoints(0);
                order.setLaunchDate(new Date());
                order.setOperator(CommonUtils.getLoginName());
                order.setIp(CommonUtils.getRemoteIp());
                orderService.persist(order);

                PayModule payModule = PayPortal.project_settlement_batch.getModuleInstance();
                ProjectSettlementBatchRequest request = new ProjectSettlementBatchRequest();
                request.setSettlement(cpcnSettlement);
                request.setOrders(orders);
                payModule.setRequest(request);
                payModule.setSn(sns);
                Response response = payModule.invoking().getResponse();
                if (response.isError()) {
                    return Result.error("结算失败：" + response.getMsg());
                }
                if (response.isProccessing()) {
                    return Result.proccessing("结算中：" + response.getMsg());
                }
                return Result.error("结算异常：" + response.getMsg());
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error();
            }
        } finally {
            lock.unLock(LockStack.INVESTMENT_LOCK, sn);
        }
    }

    @Override
    public void transferInSettlementSucceed(PaymentOrder paymentOrder) {
        if (paymentOrder == null) {
            throw new RuntimeException("订单不存在");
        }
        switch (paymentOrder.getType()) {
            case TRANSFER: {
                UserFinance userFinance = userFinanceDao.getByUserId(paymentOrder.getUserId());
                userFinanceDao.lock(userFinance, LockMode.PESSIMISTIC_WRITE);
                userFinance.addBalance(paymentOrder.getAmount(), RechargeBusinessType.GENERAL);
                Order order = orderService.findOrder(OrderType.TRANSFER_OUT, null, OrderStatus.LAUNCH, paymentOrder.getParentOrderNo());
                order = orderService.updateOrderStatus(OrderType.TRANSFER_OUT, order.getBusiness(), OrderStatus.SUCCESS, paymentOrder.getParentOrderNo());
                Capital capital = new Capital(paymentOrder.getUserId(), CapitalMethod.TRANSFER, CapitalType.CREDIT, paymentOrder.getAmount(), userFinance, paymentOrder.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "债权转出回款", order.getId());
                capitalDao.persist(capital);
                if (paymentOrder.getFee().compareTo(BigDecimal.ZERO) > 0) {
                    userFinance.subtractBalance(paymentOrder.getFee());
                    capital = new Capital(paymentOrder.getUserId(), CapitalMethod.TRANSFER_OUT_FEE, CapitalType.DEBIT, paymentOrder.getFee(), userFinance, paymentOrder.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "债权转出服务费", order.getId());
                    capital = capitalDao.persist(capital);
                    PlatformCapital platformCapital = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.TRANSFER_OUT_FEE, paymentOrder.getFee(), BigDecimal.ZERO, capital.getId(), "债权转出服务费", paymentOrder.getOperator(), paymentOrder.getIp(), paymentOrder.getOrderNo());
                    platformCapitalDao.persist(platformCapital);
                }
                userFinanceDao.merge(userFinance);
                logger.info(String.format("结算子订单[%s]结算成功", paymentOrder.getOrderNo()));
                break;
            }
            case TRANSFER_FEE: {
                break;
            }
            default:
                throw new RuntimeException("订单类型错误");
        }
    }

//    @Override
//    public Request transferIn(Transfer transfer, Integer parts, User user, Borrowing borrowing, PaymentOrderType orderType) {
//
//        if (orderType != PaymentOrderType.TRANSFER_FROZEN) {
//            throw new BusinessProcessException("冻结业务类型不正确");
//        }
//        int userId = user.getId();
//        BigDecimal transferCapital = new BigDecimal(parts * 100);
//        PaymentOrder paymentOrder = new PaymentOrder(false,
//                userId,
//                orderType,
//                SnUtils.getOrderNo(),
//                null,
//                transferCapital,
//                String.format("用户[%s][%s]冻结", userId, orderType.getDisplayName()),
//                null);
//        String projectNo = "";
//        if (null != borrowing) {
//            projectNo = borrowing.getProjectNo();
//        }
//        BigDecimal inTransferFee = transferCapital.multiply(borrowing.getInTransferFeeRate()).divide(new BigDecimal(100));
//        BigDecimal outTransferFee = transferCapital.multiply(borrowing.getOutTransferFeeRate()).divide(new BigDecimal(100));
//        IpsPayFrozenRequest frozenRequest = new IpsPayFrozenRequest(paymentOrder.getOrderNo(),
//                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
//                projectNo,
//                "2",
//                "1",
//                "",
//                "",
//                paymentOrder.getAmountString(),
//                inTransferFee.toString(),
//                "1",
//                user.getPayAccountNo(),
//                user.getPayAccountNo());
//        Map map = (Map) payGeneratorContext.generateRequest(frozenRequest);
//        logger.info("frozen result:{}", map);
//        paymentOrder.setReqParams(JsonUtils.toJson(frozenRequest));
//        paymentOrderService.persist(paymentOrder);
//        paymentOrderService.flush();
//
//        // TODO 生成投资记录
//        InvestmentRecord investmentRecord = new InvestmentRecord();
//        investmentRecord.setOperationMethod(OperationMethod.MANUAL);
//        investmentRecord.setMethod(paymentOrder.getMethod());
//        investmentRecord.setAmount(transferCapital);
//        investmentRecord.setPreferentialAmount(BigDecimal.ZERO);
//        investmentRecord.setBorrowing(borrowing.getId());
//        investmentRecord.setInvestor(user.getId());
//        investmentRecord.setOrderNo(paymentOrder.getOrderNo());
//        investmentRecord.setOperator(CommonUtils.getLoginName());
//        investmentRecord.setIp(CommonUtils.getRemoteIp());
//        investmentRecord.setDeviceType(DeviceType.PC);
//        investmentRecord.setState(InvestmentState.INVESTING);
//        investmentRecord.setTransfer(true);
//        investmentRecord.setTransferId(transfer.getId());
//        investmentRecordService.persist(investmentRecord);
//
//        User transferIner = userDao.get(userId);  // 受让人
//        User transferOuter = userDao.get(transfer.getTransfer());  // 转让人
//        UserFinance transferInFinance = userFinanceService.getByUserId(userId);
//        UserFinance transferOutFinance = userFinanceService.getByUserId(transfer.getTransfer());
//        //平台订单 : 受让人购买债权
//        Order order = new Order();
//        Integer investor = investmentRecord.getInvestor();
//        Integer transferOuterId = transferOuter.getId();
//        order.setUserId(investor);
//        order.setPayer(investor);
//        order.setPayerName(transferIner.getLoginName());
//        order.setPayee(transferOuterId);
//        order.setPayerName(transferOuter.getLoginName());
//        order.setPayerBalance(transferInFinance.getAvailable());
//        order.setPayerFee(inTransferFee);
//        order.setPayeeBalance(transferOutFinance.getAvailable());
//        order.setPayeeFee(outTransferFee);
//        order.setStatus(OrderStatus.LAUNCH);
//        order.setType(OrderType.TRANSFER_IN);
//        order.setMethod(OrderMethod.IPS);
//        order.setBusiness(investmentRecord.getId());
//        order.setOrderNo(SnUtils.getOrderNo());
//        order.setThirdOrderNo(null);
//        order.setAmount(investmentRecord.getAmount().subtract(investmentRecord.getPreferentialAmount()));
//        order.setAmountReceived(investmentRecord.getAmount());
//        order.setPoints(0);
//        order.setLaunchDate(new Date());
//        order.setOperator(transferIner.getLoginName());
//        order.setIp(WebUtils.getRemoteIp(request));
//        orderDao.persist(order);
//
//        //平台订单 : 转让人转让债权
//        order = new Order();
//        order.setUserId(transferOuterId);
//        order.setPayer(investor);
//        order.setPayerName(transferIner.getLoginName());
//        order.setPayee(transferOuterId);
//        order.setPayerName(transferOuter.getLoginName());
//        order.setPayerBalance(transferInFinance.getAvailable());
//        order.setPayerFee(inTransferFee);
//        order.setPayeeBalance(transferOutFinance.getAvailable());
//        order.setPayeeFee(outTransferFee);
//        order.setStatus(OrderStatus.LAUNCH);
//        order.setType(OrderType.TRANSFER_OUT);
//        order.setMethod(OrderMethod.IPS);
//        order.setBusiness(investmentRecord.getId());
//        order.setOrderNo(SnUtils.getOrderNo());
//        order.setThirdOrderNo(null);
//        order.setAmount(investmentRecord.getAmount().subtract(investmentRecord.getPreferentialAmount()));
//        order.setAmountReceived(investmentRecord.getAmount());
//        order.setPoints(0);
//        order.setLaunchDate(new Date());
//        order.setOperator(transferIner.getLoginName());
//        order.setIp(WebUtils.getRemoteIp(request));
//        orderDao.persist(order);
//        return new Request(IpsPayConfig.REQUEST_URL, map);
//    }

    @Override
    public PageResult findPageByMybatis(PageCriteria criteria, HttpServletRequest request) {
        Map map = new HashMap();
        map.put("mobile", request.getParameter("mobile"));
        return myDaoSupport.findPage("com.klzan.p2p.mapper.TransferMapper.findTransferListOrDetail", map, criteria);
    }

    @Override
    public List<TransferVo> findTransferInvestment(Integer userId, Integer borrowingId) {
        Map map = new HashMap();
        map.put("transfer", userId);
        map.put("borrowingId", borrowingId);
        return myDaoSupport.findList("com.klzan.p2p.mapper.TransferMapper.findTransferInvestment", map);
    }

    @Override
    public List<TransferVo> countAmount(Integer investId) {
        Map map = new HashMap();
        map.put("investId", investId);
        return myDaoSupport.findList("com.klzan.p2p.mapper.TransferMapper.countAmount", map);
    }
}
