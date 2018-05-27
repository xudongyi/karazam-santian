/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.repayment.impl;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.SnUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.capital.CapitalDao;
import com.klzan.p2p.dao.capital.PaymentOrderDao;
import com.klzan.p2p.dao.capital.PlatformCapitalDao;
import com.klzan.p2p.dao.postloan.RepaymentDao;
import com.klzan.p2p.dao.postloan.RepaymentPlanDao;
import com.klzan.p2p.dao.transfer.TransferDao;
import com.klzan.p2p.dao.user.UserDao;
import com.klzan.p2p.dao.user.UserFinanceDao;
import com.klzan.p2p.dao.user.UserInfoDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingContactsService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.service.message.MessagePushService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.sms.SmsService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.setting.BasicSetting;
import com.klzan.p2p.setting.RepaymentNoticeSetting;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.util.AccountantUtils;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.ProjectSettlementBatchRequest;
import com.klzan.plugin.pay.common.dto.RepaymentRequest;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.module.PayModule;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.LockMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * 还款
 *
 * @author: chenxinglin  Date: 2016/11/3 Time: 11:52
 */
@Service
public class RepaymentServiceImpl extends BaseService<Repayment> implements RepaymentService {

    @Inject
    private BorrowingDao borrowingDao;

    @Inject
    private RepaymentDao repaymentDao;

    @Inject
    private RepaymentPlanDao repaymentPlanDao;

    @Inject
    private CapitalDao capitalDao;

    @Inject
    private PlatformCapitalDao platformCapitalDao;

    @Inject
    private PaymentOrderDao paymentOrderDao;

    @Inject
    private UserFinanceDao userFinanceDao;

    @Inject
    private UserDao userDao;

    @Inject
    private UserInfoDao userInfoDao;

    @Inject
    private BorrowingContactsService borrowingContactsService;

    @Inject
    private MessagePushService messagePushService;

    @Inject
    private RepaymentService repaymentService;

    @Inject
    private AccountantService accountantService;

    @Inject
    private TransferService transferService;

    @Inject
    private TransferDao transferDao;

    @Inject
    private BusinessService businessService;

    @Inject
    private OrderService orderService;

    @Inject
    private SmsService smsService;

    @Inject
    private SettingUtils setting;
    @Inject
    private CpcnSettlementService cpcnSettlementService;
    @Inject
    private PayUtils payUtils;

    @Override
    public PageResult<Repayment> findPage(PageCriteria criteria, RepaymentState state, Date payDate, Boolean overdue) {
        return repaymentDao.findPage(criteria, state, payDate, overdue);
    }

    @Override
    public List<Repayment> findList(RepaymentState state, Date payDate, Boolean overdue) {
        return repaymentDao.findList(state, payDate, overdue);
    }

    @Override
    public List<Repayment> findList(Integer borrowingId) {
        return repaymentDao.findList(borrowingId);
    }

    @Override
    public List<Repayment> findList(Date startDate, Date endDate) {
        return repaymentDao.findList(startDate, endDate);
    }

    @Override
    @Transactional
    public void repayment(Repayment repayment,String ipsBillNo) {

        if (!repayment.getState().equals(RepaymentState.REPAYING)) {
            logger.info("---------该期已还---------------");
            return;
        }

        //存在转让时，先撤销转让
        try {
            List<Transfer> transfers = transferService.findList(repayment.getBorrowing());
            for (Transfer transfer : transfers) {
                transferService.transferCancel(transfer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("撤销转让失败");
        }
        // TODO 还款计划
        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findListByRepayment(repayment.getId());

        // TODO 利息计算（逾期）
        repayment = accountantService.calOverdue(repayment);
        repaymentPlans = accountantService.calOverdue(repaymentPlans);
        // TODO 更新还款
        repaymentDao.update(repayment);
        BigDecimal recoveryTotalAmount = BigDecimal.ZERO;   // 投资人回款总金额
        for (RepaymentPlan repaymentPlan : repaymentPlans) {

            // TODO 更新还款计划
            repaymentPlanDao.update(repaymentPlan);

            // TODO 更新还款计划总金额
            recoveryTotalAmount = recoveryTotalAmount.add(repaymentPlan.getRecoveryAmount()).add(repaymentPlan.getRecoveryRecoveryFee());

        }
        BigDecimal amount = repayment.getRepaymentAmount().subtract(repayment.repaymentFee());
        if (recoveryTotalAmount.compareTo(amount) == 0) {
            businessService.repayment(repayment, repaymentPlans, ipsBillNo);

            /**
             * *  更新借款信息 (全部还款完成时)
             * */
            Boolean repaymentPlansIsOver = true;
            List<Repayment> repayments = repaymentDao.findList(repayment.getBorrowing());
            for (Repayment repay : repayments) {
                if (repay.getState().equals(RepaymentState.REPAYING)) {
                    repaymentPlansIsOver = false;
                    break;
                }
            }
            if (repaymentPlansIsOver) {
                Borrowing pBorrowing = borrowingDao.get(repayment.getBorrowing());
                pBorrowing.setRepaymentFinishDate(new Date());
                pBorrowing.setProgress(BorrowingProgress.COMPLETED);
                pBorrowing.setState(BorrowingState.SUCCESS);
                borrowingDao.update(pBorrowing);

            }
        } else {
            throw new RuntimeException("还款失败");
        }
    }


    @Override
    public void preRepayment(Borrowing borrowing,String ipsBillNo) {

        //存在转让时，先撤销转让
        try {
            List<Transfer> transfers = transferService.findList(borrowing.getId());
            for (Transfer transfer : transfers) {
                transferService.transferCancel(transfer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("撤销转让失败");
        }

        List<Repayment> repaymentsAll = repaymentService.findList(borrowing.getId());
        List<Repayment> repaymentsRepaying = new ArrayList<>(); // TODO 还款计划
        List<RepaymentPlan> repaymentPlansRepaying = new ArrayList<>(); // TODO 回款计划
        for(Repayment repayment : repaymentsAll){
            if(repayment.getState().equals(RepaymentState.REPAYING)){
                repaymentsRepaying.add(repayment);
                repaymentPlansRepaying.addAll(repaymentPlanDao.findListByRepayment(repayment.getId()));
            }
        }

//        // TODO 还款计划
//        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findListByRepayment(repayment.getId());

        // TODO 利息计算（提前）
        repaymentsRepaying = accountantService.calAhead(repaymentsRepaying);
        repaymentPlansRepaying = accountantService.calAhead(repaymentPlansRepaying);

        BigDecimal repaymentAmount = BigDecimal.ZERO;   // 借款人还款总金额
        BigDecimal recoveryTotalAmount = BigDecimal.ZERO;   // 投资人回款总金额
        // TODO 更新还款
        for(Repayment repayment : repaymentsRepaying) {
            repaymentAmount = repaymentAmount.add(repayment.getRepaymentAmount().subtract(repayment.repaymentFee()));
            repaymentDao.update(repayment);
        }
        for (RepaymentPlan repaymentPlan : repaymentPlansRepaying) {

            // TODO 更新还款计划
            repaymentPlanDao.update(repaymentPlan);

            // TODO 更新还款计划总金额
            recoveryTotalAmount = recoveryTotalAmount.add(repaymentPlan.getRecoveryAmount()).add(repaymentPlan.getRecoveryRecoveryFee());

        }
        if (recoveryTotalAmount.compareTo(repaymentAmount)==0){
            businessService.prerepayment(repaymentsRepaying, ipsBillNo);
        }else {
            throw new RuntimeException("提前还款失败");
        }
    }

    @Override
    public BigDecimal preRepaymentAmount(Borrowing borrowing) {

        BigDecimal repaymentTotalAmount = BigDecimal.ZERO;   // 借款人还款金额

        // TODO 还款 利息计算（提前）
        List<Repayment> repayments = accountantService.calAhead(repaymentDao.findList(borrowing.getId()));
        for (Repayment repayment : repayments) {

            if (!repayment.getState().equals(RepaymentState.REPAYING)) {
                continue;
            }

            if (repayment.getIsOverdue()) {
                throw new RuntimeException("存在逾期未还");
            }

            // TODO 还款计划
            List<RepaymentPlan> repaymentPlans = accountantService.calAhead(repaymentPlanDao.findListByRepayment(repayment.getId()));

            // TODO 遍历还款计划
            for (RepaymentPlan repaymentPlan : repaymentPlans) {
                repaymentTotalAmount = repaymentTotalAmount.add(repaymentPlan.getCapital().add(repaymentPlan.getAheadInterest()));
            }
            repaymentTotalAmount = repaymentTotalAmount.add(repayment.getRepaymentFee());
        }

        return repaymentTotalAmount;

    }

    @Override
    public Repayment findByOrderNo(String orderNo) {
        return repaymentDao.findByOrderNo(orderNo);
    }

    public Boolean repaidLastRepayment(Integer repaymentId) {
        Repayment repayment = repaymentDao.get(repaymentId);
        if (repayment.getPeriod() == 1) {
            return true;
        }
        Repayment lastRepayment = repaymentDao.findByPeriod(repayment.getBorrowing(), repayment.getPeriod() - 1);
        if (lastRepayment != null && lastRepayment.getState().equals(RepaymentState.REPAID)) {
            return true;
        }
        return false;
    }

    public Repayment getCurrentRepayment(Integer borrowingId) {
        Repayment repayment = repaymentDao.getCurrentRepayment(borrowingId);
        if (repayment != null) {
            return repayment;
        }
        return null;
    }

    @Override
    public BigDecimal countWaitPay() {
        return repaymentDao.countWaitPay();
    }

    @Override
    public List<Repayment> findByUser(Integer userId) {
        return repaymentDao.findByUser(userId);
    }

    @Override
    public void repaymentNotice() {
        try{

            RepaymentNoticeSetting repaymentNoticeSetting = setting.getRepaymentNoticeSetting();
            if(repaymentNoticeSetting == null){
                throw new RuntimeException("还款通知任务启动失败：还款通知设置不存在");
            }

            /** 开启短信提醒 */
            Boolean openSMS = repaymentNoticeSetting.getOpenSMS()==null?false:repaymentNoticeSetting.getOpenSMS();

            /** 还款前提醒时间 */
            Integer aheadDays = repaymentNoticeSetting.getAheadDays()==null?0:repaymentNoticeSetting.getAheadDays();

            /** 还款当天提醒 */
            Boolean currentDay = repaymentNoticeSetting.getCurrentDay()==null?false:repaymentNoticeSetting.getCurrentDay();

            /** 逾期提醒 */
            Integer overdueDays = repaymentNoticeSetting.getOverdueDays()==null?0:repaymentNoticeSetting.getOverdueDays();

            //是否开启
            if(!openSMS){
                return ;
            }

            //提前3天
            Date date = DateUtils.getZeroDate(new Date());
            Date startDate = null;
            Date endDate = null;

            if(aheadDays!=0){
                startDate = DateUtils.getZeroDate(DateUtils.addDays(date, aheadDays));
                endDate = DateUtils.getZeroDate(DateUtils.addDays(date, aheadDays+1));
                List<Repayment> repaymentsAhead = repaymentService.findList(startDate, endDate);
                for(Repayment repayment : repaymentsAhead){
                    if(repayment.getState().equals(RepaymentState.REPAYING)){
//                        User user = huaShanUserService.find(repayment.getBorrower());
//                        if(user==null || user.getMobile()==null){
//                            sMSService.send(user.getMobile(), "您好，您有一笔借款三日后到期，金额："+repayment.getRepaymentAmount().doubleValue()+"元", SMSType.REPAYMENT_NOTICE);
//                        }else{
//                            logger.info("还款提醒失败,还款ID:" + repayment.getId());
//                        }
                        List<BorrowingContacts> borrowingContacts = borrowingContactsService.findList(repayment.getBorrowing());
                        for(BorrowingContacts bc : borrowingContacts){
                            if(bc.getType().equals(BorrowingContactsType.SERVICE) || bc.getType().equals(BorrowingContactsType.RISK_CONTROL_PERSONNEL)){
                                smsService.send(bc.getMobile(), "您好，有一笔"+repayment.getPayDate()+"到期，金额："+repayment.getRepaymentAmount().doubleValue()+"元, 还款ID:" + repayment.getId() + "【智链金融】", SmsType.REPAYMENT_NOTICE);
                            }
                        }
                    }
                }
            }

            //当天
            if(currentDay) {
                startDate = DateUtils.getZeroDate(date);
                endDate = DateUtils.getZeroDate(DateUtils.addDays(date, 1));
                List<Repayment> repaymentsCurrent = repaymentService.findList(startDate, endDate);
                for (Repayment repayment : repaymentsCurrent) {
                    if (repayment.getState().equals(RepaymentState.REPAYING)) {
//                        User user = huaShanUserService.find(repayment.getBorrower());
//                        if (user == null || user.getMobile() == null) {
//                            sMSService.send(user.getMobile(), "您好，您有一笔借款今日到期，金额：" + repayment.getRepaymentAmount().doubleValue() + "元", SMSType.REPAYMENT_NOTICE);
//                        } else {
//                            logger.info("还款提醒失败,还款ID:" + repayment.getId());
//                        }
                        List<BorrowingContacts> borrowingContacts = borrowingContactsService.findList(repayment.getBorrowing());
                        for (BorrowingContacts bc : borrowingContacts) {
                            if (bc.getType().equals(BorrowingContactsType.SERVICE) || bc.getType().equals(BorrowingContactsType.RISK_CONTROL_PERSONNEL)) {
                                smsService.send(bc.getMobile(), "您好，有一笔"+repayment.getPayDate()+"到期，金额：" + repayment.getRepaymentAmount().doubleValue() + "元, 还款ID:" + repayment.getId() + "【智链金融】", SmsType.REPAYMENT_NOTICE);
                            }
                        }
                    }
                }
            }

            //逾期3天
            if(overdueDays!=0) {
                startDate = DateUtils.getZeroDate(DateUtils.addDays(date, -overdueDays));
                endDate = DateUtils.getZeroDate(DateUtils.addDays(date, -overdueDays+1));
                List<Repayment> repaymentsOverdue = repaymentService.findList(startDate, endDate);
                for (Repayment repayment : repaymentsOverdue) {
                    if (repayment.getState().equals(RepaymentState.REPAYING)) {
//                        User user = huaShanUserService.find(repayment.getBorrower());
//                        if (user == null || user.getMobile() == null) {
//                            sMSService.send(user.getMobile(), "您好，您有一笔借款已逾期三日，金额：" + repayment.getRepaymentAmount().doubleValue() + "元", SMSType.REPAYMENT_NOTICE);
//                        } else {
//                            logger.info("还款提醒失败,还款ID:" + repayment.getId());
//                        }
                        List<BorrowingContacts> borrowingContacts = borrowingContactsService.findList(repayment.getBorrowing());
                        for (BorrowingContacts bc : borrowingContacts) {
                            if (bc.getType().equals(BorrowingContactsType.SERVICE) || bc.getType().equals(BorrowingContactsType.RISK_CONTROL_PERSONNEL)) {
                                smsService.send(bc.getMobile(), "您好，有一笔已逾期"+overdueDays+"天，应还日期"+repayment.getPayDate()+"，金额：" + repayment.getRepaymentAmount().doubleValue() + "元, 还款ID:" + repayment.getId() + "【智链金融】", SmsType.REPAYMENT_NOTICE);
                            }
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public synchronized CpcnSettlement repayment(Repayment repayment, RepaymentOperator operator) {
        CpcnSettlement cpcnSettlement = cpcnSettlementService.find(PaymentOrderType.REPAYMENT, null, repayment.getId());
        if (cpcnSettlement == null){
            cpcnSettlement = new CpcnSettlement();
            cpcnSettlement.setType(PaymentOrderType.REPAYMENT);
            cpcnSettlement.setBorrowing(repayment.getBorrowing());
            cpcnSettlement.setRepayment(repayment.getId());
            cpcnSettlement.setrOrderNo(SnUtils.getOrderNo());
            cpcnSettlement.setsOrderNo(SnUtils.getOrderNo());
            cpcnSettlement = cpcnSettlementService.persist(cpcnSettlement);
        }
        if (operator != null){
            cpcnSettlement.setOperator(operator);
        }
        if(cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.success) && cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.success)){
//            Result.error("该期已还");
            throw new RuntimeException("该期已还");
        }
        if(cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.failure)){
            cpcnSettlement.setsOrderNo(SnUtils.getOrderNo());
        }
        cpcnSettlement = cpcnSettlementService.merge(cpcnSettlement);
        return cpcnSettlement;
    }

    @Override
    public synchronized CpcnSettlement repaymentEarly(Borrowing borrowing, RepaymentOperator operator) {
        CpcnSettlement cpcnSettlement = cpcnSettlementService.find(PaymentOrderType.REPAYMENT_EARLY, borrowing.getId(), null);
        if (cpcnSettlement == null){
            cpcnSettlement = new CpcnSettlement();
            cpcnSettlement.setType(PaymentOrderType.REPAYMENT_EARLY);
            cpcnSettlement.setBorrowing(borrowing.getId());
            cpcnSettlement.setrOrderNo(SnUtils.getOrderNo());
            cpcnSettlement.setsOrderNo(SnUtils.getOrderNo());
            cpcnSettlementService.persist(cpcnSettlement);
        }
        if (operator != null){
            cpcnSettlement.setOperator(operator);
        }
        if(cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.success) && cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.success)){
//            Result.error("该项目已还完");
            throw new RuntimeException("该项目已还完");
        }
        if(cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.failure)){
            cpcnSettlement.setsOrderNo(SnUtils.getOrderNo());
            cpcnSettlementService.merge(cpcnSettlement);
        }
        cpcnSettlement = cpcnSettlementService.merge(cpcnSettlement);
        return cpcnSettlement;
    }

//    @Override
//    public synchronized Result repayCarry(CpcnSettlement cpcnSettlement) {
//
//        if(cpcnSettlement == null || cpcnSettlement.getId() == null){
//            throw new RuntimeException();
//        }
//
//        cpcnSettlementService.flush();
//        cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
//
//        Response response = null;
//
//        if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT) || cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT_EARLY)){
//
//            if(cpcnSettlement.getrStatus().equals(CpcnRepaymentStatus.unpaid)){
//                if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT)){
//                    response = this.repay(cpcnSettlement);
//                }else {
//                    response = this.aheadRepay(cpcnSettlement);
//                }
//                if(response.isError()){
//                    cpcnSettlement.setrStatus(CpcnRepaymentStatus.failure);
//                    cpcnSettlementService.merge(cpcnSettlement);
//                    return Result.error("还款失败："+response.getMsg());
//                }
//                if(response.isProccessing()){
//                    cpcnSettlement.setrStatus(CpcnRepaymentStatus.paying);
//                    cpcnSettlementService.merge(cpcnSettlement);
//                }
//                if(response.isSuccess()){
//                    cpcnSettlement.setrStatus(CpcnRepaymentStatus.success);
//                    cpcnSettlementService.merge(cpcnSettlement);
//                }
//            }
//
//            cpcnSettlementService.flush();
//            cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
//            if(cpcnSettlement.getrStatus().equals(CpcnRepaymentStatus.paying)){
//                String sn = cpcnSettlement.getrOrderNo();
//                PayModule payModule = PayPortal.repayment_query.getModuleInstance();
//                SnRequest snRequest = new SnRequest();
//                snRequest.setSn(sn);
//                payModule.setRequest(snRequest);
//                response = payModule.invoking().getResponse();
//                if(response.isError()){
//                    return Result.error("还款处理中，还款查询结果："+response.getMsg());
//                }
//                if(response.isProccessing()){
//                    return Result.proccessing("还款处理中，还款查询结果："+response.getMsg());
//                }
//            }
//
//            cpcnSettlementService.flush();
//            cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
//            if(!cpcnSettlement.getrStatus().equals(CpcnRepaymentStatus.success)){
//                return Result.error();
//            }
//
//            if(cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.unsettled) || cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.failure)){
//                if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT)){
//                    response = this.repaySettlement(cpcnSettlement);
//                }else {
//                    response = this.aheadRepaySettlement(cpcnSettlement);
//                }
//                if(response.isError()){
//                    cpcnSettlement.setsStatus(CpcnSettlementStatus.failure);
//                    cpcnSettlementService.merge(cpcnSettlement);
//                    return Result.error("还款结算失败："+response.getMsg());
//                }
//                if(response.isProccessing()){
//                    cpcnSettlement.setsStatus(CpcnSettlementStatus.unsettling);
//                    cpcnSettlementService.merge(cpcnSettlement);
//                }
//                if(response.isSuccess()){
//                    return Result.error("还款结算异常："+response.getMsg());
//                }
//            }
//
//            cpcnSettlementService.flush();
//            cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
//            if(cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.unsettling)){
//                String sn = cpcnSettlement.getsOrderNo();
//                PayModule payModule = PayPortal.project_settlement_batch_query.getModuleInstance();
//                SnRequest snRequest = new SnRequest();
//                snRequest.setSn(sn);
//                payModule.setRequest(snRequest);
//                response = payModule.invoking().getResponse();
//                if(response.isError()){
//                    return Result.error("还款结算处理中，结算查询结果："+response.getMsg());
//                }
//                if(response.isProccessing()){
//                    return Result.proccessing("还款结算处理中，结算查询结果："+response.getMsg());
//                }
//            }
//        }
//
//        return Result.error();
//
//
//    }

    @Override
    public BigDecimal repaymentAmount(Integer repaymentId) {
        BigDecimal repaymentTotalAmount = BigDecimal.ZERO;

        Repayment repayment = accountantService.calOverdue(repaymentDao.get(repaymentId));

        if(!repayment.getState().equals(RepaymentState.REPAYING)){
            throw new RuntimeException("");
        }

        List<RepaymentPlan> repaymentPlans = accountantService.calOverdue(repaymentPlanDao.findListByRepayment(repayment.getId()));

        for(RepaymentPlan repaymentPlan : repaymentPlans){
            repaymentTotalAmount = repaymentTotalAmount.add(repaymentPlan.getRecoveryAmount()).add(repaymentPlan.getRecoveryRecoveryFee());
        }
        repaymentTotalAmount = repaymentTotalAmount.add(repayment.getRepaymentFee());
        return repaymentTotalAmount;
    }

    @Override
    public Response repay(CpcnSettlement cpcnSettlement) {

        Repayment repayment = repaymentDao.get(cpcnSettlement.getRepayment());

        if(!repayment.getState().equals(RepaymentState.REPAYING)){
            throw new RuntimeException("改期已还");
        }

        // TODO 存在转让时，先撤销转让
        try {
            List<Transfer> transfers = transferService.findList(repayment.getBorrowing());
            for(Transfer transfer : transfers){
                transferService.transferCancel(transfer);
            }
        }catch (Exception e){
            e.printStackTrace();
            Result.error("撤销转让失败");
        }

        // TODO 还款计划
        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findListByRepayment(repayment.getId());

        // TODO 利息计算（逾期）
        repayment = accountantService.calOverdue(repayment);
        repaymentPlans = accountantService.calOverdue(repaymentPlans);

        // TODO 校验还款金额
        BigDecimal repaymentCapitalInterest = BigDecimal.ZERO;
        BigDecimal repaymentAmount = BigDecimal.ZERO;
        for (RepaymentPlan repaymentPlan : repaymentPlans){
            System.out.println(repaymentPlan.getCapital() + " - " + repaymentPlan.getInterest());
            repaymentCapitalInterest = repaymentCapitalInterest.add(repaymentPlan.getCapitalInterest());
            repaymentAmount = repaymentAmount.add(repaymentPlan.getCapitalInterestOverdueAhead());
        }
        if(repayment.getCapitalInterest().compareTo(repaymentCapitalInterest) != 0){
            throw new RuntimeException("还款金额有误");
        }

        // TODO 还款进度
        String sn = SnUtils.getOrderNo();
        cpcnSettlement.setrOrderNo(sn);
        cpcnSettlement.setrStatus(CpcnRepaymentStatus.unpaid);
        cpcnSettlementService.merge(cpcnSettlement);

        // TODO 还款服务费支付订单
        BigDecimal repaymentFee = repayment.getRepaymentFee();
        if(repaymentFee !=null && repaymentFee.compareTo(BigDecimal.ZERO)>0){
            PaymentOrder paymentFee = new PaymentOrder();
            paymentFee.setParentOrderNo(sn);
            paymentFee.setOrderNo(SnUtils.getOrderNo());
            paymentFee.setStatus(PaymentOrderStatus.NEW_CREATE);
            paymentFee.setType(PaymentOrderType.REPAYMENT_FEE);
            paymentFee.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
            paymentFee.setAmount(repaymentFee);
            paymentFee.setMemo("还款服务费");
            paymentFee.setOperator(CommonUtils.getLoginName());
            paymentFee.setIp(CommonUtils.getRemoteIp());
            paymentFee.setUserId(null);
            paymentFee.setBorrowing(repayment.getBorrowing());
            paymentFee = paymentOrderDao.persist(paymentFee);
        }
        for(RepaymentPlan repaymentPlan : repaymentPlans){
            // TODO 更新还款计划
            repaymentPlanDao.update(repaymentPlan);

//            // TODO 回款积分
//            userPointService.repayment(repaymentPlan);

            if(repaymentPlan.getCapitalInterest().compareTo(BigDecimal.ZERO)==0){
                continue;
            }

            // TODO 回款支付订单
            PaymentOrder iPayment = new PaymentOrder();
            iPayment.setStatus(PaymentOrderStatus.NEW_CREATE);
            iPayment.setType(PaymentOrderType.RECOVERY);
            iPayment.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
            iPayment.setAmount(repaymentPlan.getRecoveryAmount().add(repaymentPlan.getRecoveryFee()));
            iPayment.setFee(repaymentPlan.getRecoveryFee());
            iPayment.setParentOrderNo(sn);
            iPayment.setOrderNo(SnUtils.getOrderNo());
            iPayment.setMemo(String.format("回款,金额￥[%s],服务费￥[%s]", repaymentPlan.getRecoveryAmount(), repaymentPlan.getRecoveryFee()));
            iPayment.setOperator(CommonUtils.getLoginName());
            iPayment.setIp(CommonUtils.getRemoteIp());
            iPayment.setUserId(repaymentPlan.getInvestor());
            iPayment.setBorrowing(repayment.getBorrowing());

            repaymentPlan.setOrderNo(iPayment.getOrderNo());
//            if(repaymentPlan.getTransferOver()){
//                iPayment.setTransferOver(true);
//                iPayment.setExtOrderNo(repaymentPlan.getTransferOrderNo());
//            }else {
//                iPayment.setExtOrderNo(payUtils.getPaymentNo(repayment.getBorrowing(), repaymentPlan.getInvestor()));
//            }
            iPayment = paymentOrderDao.persist(iPayment);
            if(repaymentPlan.getRecoveryFee()!=null && repaymentPlan.getRecoveryFee().compareTo(BigDecimal.ZERO)>0){
                // TODO 回款服务费支付订单
                PaymentOrder iPaymentFee = new PaymentOrder();
                iPaymentFee.setStatus(PaymentOrderStatus.NEW_CREATE);
                iPaymentFee.setType(PaymentOrderType.RECOVERY_FEE);
                iPaymentFee.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
                iPaymentFee.setAmount(repaymentPlan.getRecoveryFee());
                iPaymentFee.setParentOrderNo(sn);
                iPaymentFee.setOrderNo(SnUtils.getOrderNo());
                iPaymentFee.setMemo("回款服务费");
                iPaymentFee.setOperator(CommonUtils.getLoginName());
                iPaymentFee.setIp(CommonUtils.getRemoteIp());
                iPaymentFee.setUserId(null);
                iPaymentFee.setBorrowing(repayment.getBorrowing());
                iPaymentFee = paymentOrderDao.persist(iPaymentFee);
            }
        }

        // TODO 更新还款
        repayment.setPaidDate(new Date());
        repayment.setPaidAmount(repaymentAmount.add(repaymentFee));
        repayment.setPaidOverdueInterest(repayment.getOverdueInterest());
        repayment.setPaidSeriousOverdueInterest(repayment.getSeriousOverdueInterest());

        repaymentDao.merge(repayment);

        // TODO 更新还款计划
        for(RepaymentPlan repaymentPlan : repaymentPlans){
            if(!repaymentPlan.getState().equals(RepaymentState.REPAYING)){
                continue;
            }
            repaymentPlan.setPaidDate(new Date());
            repaymentPlan.setPaidAmount(repaymentPlan.getCapitalInterestOverdueAhead());
            repaymentPlan.setPaidCapital(repaymentPlan.getCapital());
            repaymentPlan.setPaidInterest(repaymentPlan.getInterest());
            repaymentPlan.setPaidOverdueInterest(repaymentPlan.getOverdueInterest());
            repaymentPlan.setPaidSeriousOverdueInterest(repaymentPlan.getSeriousOverdueInterest());
            repaymentPlanDao.update(repaymentPlan);
        }

        // TODO cpcn还款
        try {
            User borrower = userDao.get(repayment.getBorrower());
            UserFinance borrowerFinance = userFinanceDao.getByUserId(repayment.getBorrower());
            userFinanceDao.refresh(borrowerFinance);
            if (repayment.getRepaymentAmount().compareTo(borrowerFinance.getAvailable()) > 0) {
                throw new RuntimeException("借款人余额不足");
            }

            borrowerFinance.addFrozen(repaymentAmount.add(repaymentFee));
            userFinanceDao.update(borrowerFinance);
            //平台订单 : 还款
            Order order = new Order();
            order.setUserId(borrower.getId());
            order.setPayer(borrower.getId());
            order.setPayerName(borrower.getLoginName());
            order.setPayee(-4);
            order.setPayerName("投资人");
            order.setStatus(OrderStatus.LAUNCH);
            order.setType(OrderType.REPAYMENT);
            order.setMethod(OrderMethod.CPCN);
            order.setBusiness(repayment.getId());
            order.setOrderNo(SnUtils.getOrderNo());
            order.setThirdOrderNo(null);
            order.setPayerBalance(borrowerFinance.getAvailable());
            order.setAmount(repaymentAmount.add(repaymentFee));
            order.setAmountReceived(repaymentAmount);
            order.setPayerFee(repaymentFee);
            order.setPoints(0);
            order.setLaunchDate(new Date());
            order.setOperator(borrower.getLoginName());
            order.setIp(CommonUtils.getRemoteIp());
            orderService.persist(order);

            PayModule payModule = PayPortal.repayment.getModuleInstance();
            RepaymentRequest request = new RepaymentRequest();
            request.setBorrowing(repayment.getBorrowing());
            request.setRepayment(repayment.getId());
            request.setEarly(false);
            request.setBorrower(repayment.getBorrower());
            request.setAmount(repaymentAmount.add(repaymentFee));
            request.setFee(repaymentFee);
            request.setInstead(cpcnSettlement.getOperator().equals(RepaymentOperator.ADMIN_INSTEAD));
            payModule.setRequest(request);
            payModule.setSn(sn);
            Response response = payModule.invoking().getResponse();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("还款失败");
        }
    }

    @Override
    public void repaySucceed(CpcnSettlement cpcnSettlement, PaymentOrder paymentOrder) {
        Repayment repayment = repaymentService.get(cpcnSettlement.getRepayment());

        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findListByRepayment(repayment.getId());

        // TODO 更新还款
        repayment.setState(RepaymentState.REPAID);
        repaymentDao.merge(repayment);

        // TODO 更新借款人
        UserFinance pBorrowerFinance = userFinanceDao.getByUserId(repayment.getBorrower());
        userFinanceDao.refresh(pBorrowerFinance);

        //平台订单 : 还款
        Order order = orderService.updateOrderStatus(OrderType.REPAYMENT, repayment.getId(), OrderStatus.SUCCESS, paymentOrder.getOrderNo());

        // TODO 还款人资金记录  本金+利息  服务费  逾期利息+严重逾期利息
        pBorrowerFinance.subtractFrozen(paymentOrder.getAmount().subtract(paymentOrder.getFee()));
        if(!cpcnSettlement.getOperator().equals(RepaymentOperator.ADMIN_INSTEAD)){
            pBorrowerFinance.subtractBalance(paymentOrder.getAmount().subtract(paymentOrder.getFee()));
        }
        Capital capital = new Capital(repayment.getBorrower(), CapitalMethod.REPAYMENT, CapitalType.DEBIT, paymentOrder.getAmount().subtract(paymentOrder.getFee()), pBorrowerFinance, repayment.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "借款人还款", order.getId());
        capitalDao.persist(capital);
        if (repayment.getRepaymentFee().compareTo(BigDecimal.ZERO) > 0) {
            pBorrowerFinance.subtractFrozen(paymentOrder.getFee());
            if(!cpcnSettlement.getOperator().equals(RepaymentOperator.ADMIN_INSTEAD)){
                pBorrowerFinance.subtractBalance(paymentOrder.getFee());
            }
            capital = new Capital(repayment.getBorrower(), CapitalMethod.REPAYMENT_FEE, CapitalType.DEBIT, paymentOrder.getFee(), pBorrowerFinance, repayment.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "还款服务费", order.getId());
            capital = capitalDao.persist(capital);
            PlatformCapital platformCapital = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.REPAYMENT_FEE, repayment.getRepaymentFee(), BigDecimal.ZERO, capital.getId(), "还款服务费", paymentOrder.getOperator(), paymentOrder.getIp(), repayment.getOrderNo());
            platformCapitalDao.persist(platformCapital);
        }
        pBorrowerFinance.subtractDebit(repayment.getCapitalInterestFee());
        userFinanceDao.update(pBorrowerFinance);

        // TODO 还款计划
        for(RepaymentPlan repaymentPlan : repaymentPlans){
            if(!repaymentPlan.getState().equals(RepaymentState.REPAYING)){
                continue;
            }

            // TODO 更新还款计划
            repaymentPlan.setPayState(PayState.PAID);
            repaymentPlan.setState(RepaymentState.REPAID);
        }
        // TODO 更新借款 (全部还款完成时)
        Borrowing pBorrowing = borrowingDao.get(repayment.getBorrowing());
        repaymentPlanDao.flush();
        Boolean isRepaymentFull = repaymentPlanDao.countRepaying(pBorrowing.getId()) == 0;
        if (isRepaymentFull) {
            pBorrowing.setRepaymentFinishDate(new Date());
            pBorrowing.setProgress(BorrowingProgress.COMPLETED);
            pBorrowing.setState(BorrowingState.SUCCESS);
            borrowingDao.merge(pBorrowing);
        }
    }

    @Override
    public Response repaySettlement(CpcnSettlement cpcnSettlement) {
        List<PaymentOrder> paymentOrders = paymentOrderDao.findByParentOrderNo(cpcnSettlement.getrOrderNo());
        try {
//            for(PaymentOrder paymentOrder : paymentOrders){
//                paymentOrder.setOrderNo(SnUtils.getOrderNo());
//                paymentOrderDao.merge(paymentOrder);
//            }

            String sn = SnUtils.getOrderNo();
            cpcnSettlement.setsOrderNo(sn);
            cpcnSettlement.setsStatus(CpcnSettlementStatus.unsettled);
            cpcnSettlementService.merge(cpcnSettlement);

            PayModule payModule = PayPortal.project_settlement_batch.getModuleInstance();
            ProjectSettlementBatchRequest request = new ProjectSettlementBatchRequest();
            request.setSettlement(cpcnSettlement);
            request.setOrders(paymentOrders);
            payModule.setRequest(request);
            payModule.setSn(cpcnSettlement.getsOrderNo());
            Response response = payModule.invoking().getResponse();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error();
        }
    }

    @Override
    public void repaySettlementSucceed(CpcnSettlement cpcnSettlement, PaymentOrder paymentOrder, Boolean allSuccess)  {
        Repayment repayment = repaymentService.get(cpcnSettlement.getRepayment());
        Borrowing borrowing = borrowingDao.get(repayment.getBorrowing());

        if(allSuccess){
            repayment.setSettlement(true);
            repaymentService.merge(repayment);

            logger.info(String.format("还款ID[%s]全部订单结算成功", repayment.getId()));
            return;
        }

        if(paymentOrder != null){
            switch (paymentOrder.getType()){
                case REPAYMENT_FEE:{
                    break;
                }
                case RECOVERY:{
                    UserFinance userFinance = userFinanceDao.getByUserId(paymentOrder.getUserId());
                    userFinanceDao.lock(userFinance, LockMode.PESSIMISTIC_WRITE);

                    User borrower = userDao.get(repayment.getBorrower());  // 借款人
                    UserFinance borrowerFinance = userFinanceDao.getByUserId(repayment.getBorrower());
                    User investor = userDao.get(paymentOrder.getUserId());  // 投资人
                    UserFinance investorFinance = userFinanceDao.getByUserId(paymentOrder.getUserId());
                    RepaymentPlan repaymentPlan = repaymentPlanDao.findByOrderNo(paymentOrder.getOrderNo());
                    //平台订单 : 回款
                    Order orderRecoverry = new Order();
                    orderRecoverry.setId(null);
                    orderRecoverry.setUserId(investor.getId());
                    orderRecoverry.setPayer(borrower.getId());
                    orderRecoverry.setPayerName(borrower.getLoginName());
                    orderRecoverry.setPayee(investor.getId());
                    orderRecoverry.setPayerName(investor.getLoginName());
                    orderRecoverry.setStatus(OrderStatus.SUCCESS);
                    orderRecoverry.setType(OrderType.RECOVERY);
                    orderRecoverry.setMethod(OrderMethod.CPCN);
                    orderRecoverry.setBusiness(repaymentPlan.getId());
                    orderRecoverry.setParentOrderNo(paymentOrder.getOrderNo());
                    orderRecoverry.setOrderNo(SnUtils.getOrderNo());
                    orderRecoverry.setThirdOrderNo(null);
                    orderRecoverry.setPayeeBalance(investorFinance.getAvailable());
                    orderRecoverry.setPayeeFee(repaymentPlan.getRecoveryFee());
                    orderRecoverry.setPayerBalance(borrowerFinance.getAvailable());
                    orderRecoverry.setAmount(repaymentPlan.getRecoveryAmount().add(repaymentPlan.getRecoveryFee()));
                    orderRecoverry.setAmountReceived(repaymentPlan.getRecoveryAmount());
                    orderRecoverry.setPoints(0);
                    orderRecoverry.setLaunchDate(new Date());
                    orderRecoverry.setOperator(borrower.getLoginName());
                    orderRecoverry.setIp(CommonUtils.getRemoteIp());
                    orderRecoverry = orderService.persist(orderRecoverry);
                    orderService.flush();

                    userFinance.addBalance(paymentOrder.getAmount(), RechargeBusinessType.GENERAL);
                    Capital capital = new Capital(paymentOrder.getUserId(), CapitalMethod.RECOVERY, CapitalType.CREDIT, paymentOrder.getAmount(), userFinance, paymentOrder.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "回款", orderRecoverry.getId());
                    capitalDao.persist(capital);
                    if(paymentOrder.getFee().compareTo(BigDecimal.ZERO)>0){
                        userFinance.subtractBalance(paymentOrder.getFee());
                        capital = new Capital(paymentOrder.getUserId(), CapitalMethod.RECOVERY_FEE, CapitalType.DEBIT, paymentOrder.getFee(), userFinance, paymentOrder.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "回款服务费", orderRecoverry.getId());
                        capital = capitalDao.persist(capital);
                        PlatformCapital platformCapital = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.RECOVERY_FEE, paymentOrder.getFee(), BigDecimal.ZERO, capital.getId(), "回款服务费", paymentOrder.getOperator(), paymentOrder.getIp(), paymentOrder.getOrderNo());
                        platformCapitalDao.persist(platformCapital);
                    }
                    String sms="";
                    BasicSetting basic = setting.getBasic();
                    if (!repayment.getCapital().equals(BigDecimal.ZERO)){
                        sms="【%s】投资人您好，您投资的“%s”项目，于%s按时将本金及收益回款至您的余额账户。";
                        sms=String.format(sms,basic.getSiteName(),borrowing.getTitle(), DateUtils.format(new Date(), DateUtils.YYYY_MM_DD));
                    }else{
                        sms="【%S】投资人您好，您投资的“%s”第%s期收益已于%s按时回款至您的余额账户。";
                        sms=String.format(sms,basic.getSiteName(),borrowing.getTitle(),repayment.getPeriod(), DateUtils.format(new Date(), DateUtils.YYYY_MM_DD));

                    }
                    try {
                        smsService.send(userDao.get(paymentOrder.getUserId()).getMobile(), sms, SmsType.REPAYMENT_NOTICE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        messagePushService.recoverySuccPush(paymentOrder.getUserId(), paymentOrder.getAmount(), paymentOrder.getFee(), borrowing.getTitle(), repayment.getPeriod(), repayment.getPaidDate());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //todo sms
                    userFinanceDao.merge(userFinance);
                    break;
                }
                case RECOVERY_FEE:{
//                    userFinance.subtractCpcnBalance(paymentOrder.getAmount());
//                    Capital capital = new Capital(paymentOrder.getUserId(), CapitalMethod.recovery_fee, CapitalType.debit, paymentOrder.getAmount(), userFinance, paymentOrder.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "投资人回款服务费");
//                    capital = capitalDao.create(capital);
//                    HuashanCapital huashanCapital = new HuashanCapital(CapitalType.credit, CapitalMethod.recovery_fee, paymentOrder.getAmount(), BigDecimal.ZERO, capital.getId(), "平台收取投资人回款服务费", paymentOrder.getOperator(), paymentOrder.getIp(), paymentOrder.getOrderNo());
//                    huashanCapitalDao.create(huashanCapital);
                    break;
                }default:
                    throw new RuntimeException("订单状态错误");
            }
            logger.info(String.format("结算子订单[%s]结算成功", paymentOrder.getOrderNo()));
        }
    }

    @Override
    public BigDecimal aheadRepaymentAmount(Integer borrowingId) {
        BigDecimal repaymentTotalAmount = BigDecimal.ZERO;

        Borrowing borrowing = borrowingDao.get(borrowingId);
        List<Repayment> repayments = accountantService.calAhead(repaymentDao.findList(borrowing.getId()));
        for(Repayment repayment : repayments){
            if(!repayment.getState().equals(RepaymentState.REPAYING)){
                continue;
            }
            if(repayment.getIsOverdue()){
                throw new RuntimeException("存在逾期未还");
            }
            List<RepaymentPlan> repaymentPlans = accountantService.calAhead(repaymentPlanDao.findListByRepayment(repayment.getId()));
            for(RepaymentPlan repaymentPlan : repaymentPlans){
                repaymentTotalAmount = repaymentTotalAmount.add(repaymentPlan.getCapital().add(repaymentPlan.getAheadInterest()));
            }
            repaymentTotalAmount = repaymentTotalAmount.add(repayment.getRepaymentFee());
        }

        return repaymentTotalAmount;
    }

    @Override
    public Response aheadRepay(CpcnSettlement cpcnSettlement) {

        Borrowing borrowing = borrowingDao.get(cpcnSettlement.getBorrowing());

        //存在转让时，先撤销转让
        try {
            List<Transfer> transfers = transferService.findList(borrowing.getId());
            for(Transfer transfer : transfers){
                transferService.transferCancel(transfer);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("撤销转让失败");
        }

        // TODO 还款金额计算
        String sn = SnUtils.getOrderNo();
        BigDecimal totalRepaymentCapitalInterest = BigDecimal.ZERO;   // 借款人还款金额
        BigDecimal totalRepaymentFee = BigDecimal.ZERO;   // 借款人还款服务费
        List<Repayment> repayments = repaymentDao.findList(borrowing.getId()); // 还款
        repayments = accountantService.calAhead(repayments); //提前还款计算
        for(Repayment repayment : repayments){
            if(repayment.getState().equals(RepaymentState.REPAYING)){
                totalRepaymentCapitalInterest = totalRepaymentCapitalInterest.add(repayment.getCapital().add(repayment.getAheadInterest()));
                totalRepaymentFee = totalRepaymentFee.add(repayment.getRepaymentFee());

                /*更新还款*/
                repayment.setPaidDate(new Date());
                repayment.setPaidAmount(repayment.getRepaymentAmount());
                repaymentDao.update(repayment);
            }
        }

        logger.info(String.format("提前还款:借款ID[%s],用户ID[%s],订单号[%s]", borrowing.getId(), borrowing.getBorrower(), sn));
        logger.info(String.format("提前还款本息:[%s]", totalRepaymentCapitalInterest));
        logger.info(String.format("提前还款服务费:[%s]", totalRepaymentFee));

        // TODO 回款金额计算
        List<RepaymentPlan> repaymentPlans = accountantService.calAhead(repaymentPlanDao.findList(borrowing.getId())); // 还款计划
        // 校验还款本息
        BigDecimal repaymentPlansAll = BigDecimal.ZERO; //回款总本息
        BigDecimal recoveryAmountAll = BigDecimal.ZERO; //回款总金额
        BigDecimal recoveryFeeAll = BigDecimal.ZERO; //回款总服务费
        for (RepaymentPlan repaymentPlan : repaymentPlans){

            if(repaymentPlan.getState().equals(RepaymentState.REPAID)){
                continue;
            }

            System.out.println(repaymentPlan.getCapital() + " - " + repaymentPlan.getInterest());
//            repaymentPlansAll = repaymentPlansAll.add(repaymentPlan.getCapitalInterest());
            repaymentPlansAll = repaymentPlansAll.add(repaymentPlan.getCapital()).add(repaymentPlan.getAheadInterest());
            recoveryAmountAll = recoveryAmountAll.add(repaymentPlan.getRecoveryAmount());
            recoveryFeeAll = recoveryFeeAll.add(repaymentPlan.getRecoveryFee());
            logger.info(String.format("提前回款:用户ID[%s]", repaymentPlan.getInvestor()));
            logger.info(String.format("提前回款金额:[%s]", repaymentPlan.getRecoveryAmount()));
            logger.info(String.format("提前回款服务费:[%s]", repaymentPlan.getRecoveryFee()));
        }
        logger.info(String.format("提前回款总金额:[%s]", recoveryAmountAll));
        logger.info(String.format("提前回款总服务费:[%s]", recoveryFeeAll));

        // TODO 校验还款本息
        if(totalRepaymentCapitalInterest.compareTo(repaymentPlansAll) != 0){
            throw new RuntimeException("还款金额有误");
        }

        // TODO 还款进度
        cpcnSettlement.setrOrderNo(sn);
        cpcnSettlement.setrStatus(CpcnRepaymentStatus.unpaid);
        cpcnSettlement.setsStatus(CpcnSettlementStatus.unsettled);
        cpcnSettlementService.merge(cpcnSettlement);

        Set<Integer> investorIdSet = new HashSet();  //投资人ID
        for(RepaymentPlan repaymentPlan : repaymentPlans){
            investorIdSet.add(repaymentPlan.getInvestor());
        }
        for(Integer investor : investorIdSet){
            Boolean transferOver = Boolean.TRUE; //是否完全转让
            String transferOrderNo = ""; //完全转让的转让流水号

            List<RepaymentPlan> plans = new ArrayList<RepaymentPlan>();
            for(RepaymentPlan repaymentPlan : repaymentPlans){
                if(repaymentPlan.getState().equals(RepaymentState.REPAYING) && investor.equals(repaymentPlan.getInvestor())){
                    plans.add(repaymentPlan);
//                    if(!repaymentPlan.getTransferOver()){
//                        transferOver = Boolean.FALSE;
//                    }else {
//                        transferOrderNo = repaymentPlan.getTransferOrderNo();
//                    }
                }
            }
            BigDecimal totalRecoveryCapitalInterest = BigDecimal.ZERO; //投资人回款金额
            BigDecimal totalRecoveryFee = BigDecimal.ZERO; //投资人回款服务费
            String rsn = SnUtils.getOrderNo();


            for(RepaymentPlan repaymentPlan : plans){
                totalRecoveryCapitalInterest = totalRecoveryCapitalInterest.add(repaymentPlan.getCapital().add(repaymentPlan.getAheadInterest()));
                totalRecoveryFee = totalRecoveryFee.add(repaymentPlan.getRecoveryFee());

                // TODO 更新还款计划
                repaymentPlan.setPaidDate(new Date());
                repaymentPlan.setPaidAmount(repaymentPlan.getRecoveryAmount());
                repaymentPlan.setPaidCapital(repaymentPlan.getCapital());
                repaymentPlan.setPaidInterest(repaymentPlan.getAheadInterest());
                repaymentPlan.setOrderNo(rsn);
                repaymentPlanDao.merge(repaymentPlan);
//                // TODO 投资人回款积分
//                userPointService.repayment(repaymentPlan);
            }

            // TODO 提前回款支付订单
            PaymentOrder iPayment = new PaymentOrder();
            iPayment.setStatus(PaymentOrderStatus.NEW_CREATE);
            iPayment.setType(PaymentOrderType.RECOVERY_EARLY);
            iPayment.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
            iPayment.setAmount(totalRecoveryCapitalInterest);
            iPayment.setFee(totalRecoveryFee);
            iPayment.setParentOrderNo(sn);
            iPayment.setOrderNo(rsn);
            iPayment.setMemo(String.format("提前回款,金额￥[%s],服务费￥[%s]", totalRecoveryCapitalInterest, totalRecoveryFee));
            iPayment.setOperator(CommonUtils.getLoginName());
            iPayment.setIp(CommonUtils.getRemoteIp());
            iPayment.setUserId(investor);
            iPayment.setBorrowing(borrowing.getId());
//            if(transferOver){
//                iPayment.setTransferOver(true);
//                iPayment.setExtOrderNo(transferOrderNo);
//            }else {
                iPayment.setExtOrderNo(payUtils.getPaymentNo(borrowing.getId(), investor));
//            }
            paymentOrderDao.persist(iPayment);

            if(totalRecoveryFee!=null && totalRecoveryFee.compareTo(BigDecimal.ZERO)>0) {
                // TODO 提前回款服务费支付订单
                PaymentOrder iPaymentFee = new PaymentOrder();
                iPaymentFee.setStatus(PaymentOrderStatus.NEW_CREATE);
                iPaymentFee.setType(PaymentOrderType.RECOVERY_EARLY_FEE);
                iPaymentFee.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
                iPaymentFee.setAmount(totalRecoveryFee);
                iPaymentFee.setParentOrderNo(sn);
                iPaymentFee.setOrderNo(SnUtils.getOrderNo());
                iPaymentFee.setMemo("提前回款服务费");
                iPaymentFee.setOperator(CommonUtils.getLoginName());
                iPaymentFee.setIp(CommonUtils.getRemoteIp());
                iPaymentFee.setUserId(null);
                iPaymentFee.setBorrowing(borrowing.getId());
                paymentOrderDao.persist(iPaymentFee);
            }
        }

        // TODO 提前还款服务费支付订单
        if(totalRepaymentFee!=null && totalRepaymentFee.compareTo(BigDecimal.ZERO)>0){
            PaymentOrder paymentFee = new PaymentOrder();
            paymentFee.setParentOrderNo(sn);
            paymentFee.setOrderNo(SnUtils.getOrderNo());
            paymentFee.setStatus(PaymentOrderStatus.PROCESSING);
            paymentFee.setType(PaymentOrderType.REPAYMENT_EARLY_FEE);
            paymentFee.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
            paymentFee.setAmount(totalRepaymentFee);
            paymentFee.setMemo("提前还款服务费");
            paymentFee.setOperator(CommonUtils.getLoginName());
            paymentFee.setIp(CommonUtils.getRemoteIp());
            paymentFee.setUserId(null);
            paymentFee.setBorrowing(borrowing.getId());
            paymentOrderDao.persist(paymentFee);
        }

        // TODO 更新借款 (全部还款完成时)
        borrowing = borrowingDao.refresh(borrowing);
        borrowing.setRepaymentFinishDate(new Date());
        borrowingDao.update(borrowing);

        // TODO cpcn还款
        try {
            User borrower = userDao.get(borrowing.getBorrower());
            UserFinance borrowerFinance = userFinanceDao.getByUserId(borrowing.getBorrower());
            userFinanceDao.refresh(borrowerFinance);
            BigDecimal totalAmount = totalRepaymentCapitalInterest.add(totalRepaymentFee);
            if (totalAmount.compareTo(borrowerFinance.getAvailable()) > 0) {
                throw new RuntimeException("借款人余额不足");
            }

            borrowerFinance.addFrozen(totalAmount);
            userFinanceDao.update(borrowerFinance);

            //平台订单 : 还款
            Order order = new Order();
            order.setUserId(borrower.getId());
            order.setPayer(borrower.getId());
            order.setPayerName(borrower.getLoginName());
            order.setPayee(-4);
            order.setPayerName("投资人");
            order.setStatus(OrderStatus.LAUNCH);
            order.setType(OrderType.REPAYMENT_EARLY);
            order.setMethod(OrderMethod.CPCN);
            order.setBusiness(borrowing.getId());
            order.setOrderNo(SnUtils.getOrderNo());
            order.setThirdOrderNo(null);
            order.setPayerBalance(borrowerFinance.getAvailable());
            order.setAmount(totalAmount);
            order.setAmountReceived(totalAmount);
            order.setPayerFee(totalRepaymentFee);
            order.setPoints(0);
            order.setLaunchDate(new Date());
            order.setOperator(borrower.getLoginName());
            order.setIp(CommonUtils.getRemoteIp());
            order.setMemo(String.format("借款人[%s]提前还款", borrower.getId()));
            orderService.persist(order);

            PayModule payModule = PayPortal.repayment.getModuleInstance();
            RepaymentRequest request = new RepaymentRequest();
            request.setBorrowing(borrowing.getId());
            request.setEarly(true);
            request.setBorrower(borrowing.getBorrower());
            request.setAmount(totalRepaymentCapitalInterest);
            request.setFee(totalRepaymentFee);
            request.setInstead(cpcnSettlement.getOperator().equals(RepaymentOperator.ADMIN_INSTEAD));
            payModule.setRequest(request);
            payModule.setSn(sn);
            Response response = payModule.invoking().getResponse();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("提前还款失败");
        }
    }

    @Override
    public void aheadRepaySucceed(CpcnSettlement cpcnSettlement, PaymentOrder paymentOrder) {
        Borrowing borrowing = borrowingDao.get(cpcnSettlement.getBorrowing());

        List<Repayment> repayments = repaymentDao.findList(borrowing.getId()); // 还款
        List<Repayment> repaymentsInRepaying = new ArrayList<>(); // 还款
        for(Repayment repayment : repayments){
            if(repayment.getState().equals(RepaymentState.REPAYING)){
                /*更新还款*/
                repayment.setState(RepaymentState.REPAID);
                repaymentDao.update(repayment);
                repaymentsInRepaying.add(repayment);
            }
        }

        //平台订单 : 还款
        Order order = orderService.updateOrderStatus(OrderType.REPAYMENT_EARLY, borrowing.getId(), OrderStatus.SUCCESS, paymentOrder.getOrderNo());

        // TODO 借款人资金
        UserFinance borrowerFinance = userFinanceDao.getByUserId(borrowing.getBorrower()); //还款人
        /*还款资金记录*/
        borrowerFinance.subtractFrozen(paymentOrder.getAmount().subtract(paymentOrder.getFee()));
        if(!cpcnSettlement.getOperator().equals(RepaymentOperator.ADMIN_INSTEAD)){
            borrowerFinance.subtractBalance(paymentOrder.getAmount().subtract(paymentOrder.getFee()));
        }
        Capital capital = new Capital(borrowing.getBorrower(), CapitalMethod.REPAYMENT, CapitalType.DEBIT, paymentOrder.getAmount(), borrowerFinance, paymentOrder.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "提前还款", order.getId());
        capitalDao.persist(capital);
        /*还款服务费资金记录*/
        if (paymentOrder.getFee().compareTo(BigDecimal.ZERO) > 0) {
            borrowerFinance.subtractFrozen(paymentOrder.getFee());
            if(!cpcnSettlement.getOperator().equals(RepaymentOperator.ADMIN_INSTEAD)){
                borrowerFinance.subtractBalance(paymentOrder.getFee());
            }
            capital = new Capital(borrowing.getBorrower(), CapitalMethod.REPAYMENT_FEE, CapitalType.DEBIT, paymentOrder.getFee(), borrowerFinance, paymentOrder.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "提前还款服务费", order.getId());
            capital = capitalDao.persist(capital);
            PlatformCapital platformCapital = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.REPAYMENT_FEE, paymentOrder.getFee(), BigDecimal.ZERO, capital.getId(), "提前还款服务费", paymentOrder.getOperator(), paymentOrder.getIp(), paymentOrder.getOperator());
            platformCapitalDao.persist(platformCapital);
        }
        /*借款人资金更新*/
        borrowerFinance.subtractDebit(AccountantUtils.getRepaymentAmountInterestFee(repaymentsInRepaying));
        userFinanceDao.update(borrowerFinance);

        /*回款金额计算*/
        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findList(borrowing.getId()); // 还款计划
        for(RepaymentPlan repaymentPlan : repaymentPlans){
            // TODO 更新还款计划
            repaymentPlan.setPayState(PayState.PAID);
            repaymentPlan.setState(RepaymentState.REPAID);
            repaymentPlanDao.update(repaymentPlan);
        }

        // TODO 更新借款 (全部还款完成时)
        borrowing = borrowingDao.refresh(borrowing);
//        borrowing.setRepaymentFinishDate(new Date());
        borrowing.setProgress(BorrowingProgress.COMPLETED);
        borrowing.setState(BorrowingState.SUCCESS);
        borrowingDao.update(borrowing);
    }

    @Override
    public Response aheadRepaySettlement(CpcnSettlement cpcnSettlement) {
        List<PaymentOrder> paymentOrders = paymentOrderDao.findByParentOrderNo(cpcnSettlement.getrOrderNo());  // 回款订单、服务费订单
        try {
            String sn = SnUtils.getOrderNo();
            cpcnSettlement.setsOrderNo(sn);
            cpcnSettlement.setsStatus(CpcnSettlementStatus.unsettled);
            cpcnSettlementService.merge(cpcnSettlement);

            PayModule payModule = PayPortal.project_settlement_batch.getModuleInstance();
            ProjectSettlementBatchRequest request = new ProjectSettlementBatchRequest();
            request.setSettlement(cpcnSettlement);
            request.setOrders(paymentOrders);
            payModule.setRequest(request);
            payModule.setSn(cpcnSettlement.getsOrderNo());
            Response response = payModule.invoking().getResponse();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("结算失败");
        }
    }

    @Override
    public void aheadRepaySettlementSucceed(CpcnSettlement CpcnSettlementState, PaymentOrder paymentOrder)  {
        if(paymentOrder != null){
            switch (paymentOrder.getType()){
                case REPAYMENT_EARLY_FEE:{
                    break;
                }
                case RECOVERY_EARLY:{
                    Borrowing borrowing = borrowingDao.get(paymentOrder.getBorrowing());
                    UserFinance userFinance = userFinanceDao.getByUserId(paymentOrder.getUserId());
                    userFinanceDao.lock(userFinance, LockMode.PESSIMISTIC_WRITE);

                    User borrower = userDao.get(borrowing.getBorrower());  // 借款人
                    UserFinance borrowerFinance = userFinanceDao.getByUserId(borrowing.getBorrower());
                    User investor = userDao.get(paymentOrder.getUserId());  // 投资人
                    UserFinance investorFinance = userFinanceDao.getByUserId(paymentOrder.getUserId());
//                    RepaymentPlan repaymentPlan = repaymentPlanDao.findByOrderNo(paymentOrder.getOrderNo());
                    Order order = orderService.getByBusinessId(OrderType.REPAYMENT_EARLY, borrowing.getId());
                    //平台订单 : 提前回款
                    Order orderRecoverry = new Order();
                    orderRecoverry.setUserId(paymentOrder.getUserId());
                    orderRecoverry.setPayer(borrower.getId());
                    orderRecoverry.setPayerName(borrower.getLoginName());
                    orderRecoverry.setPayee(investor.getId());
                    orderRecoverry.setPayerName(investor.getLoginName());
                    orderRecoverry.setStatus(OrderStatus.SUCCESS);
                    orderRecoverry.setType(OrderType.RECOVERY_EARLY);
                    orderRecoverry.setMethod(OrderMethod.CPCN);
                    orderRecoverry.setBusiness(borrowing.getId());
                    orderRecoverry.setParentOrderNo(order.getOrderNo());
                    orderRecoverry.setOrderNo(SnUtils.getOrderNo());
                    orderRecoverry.setThirdOrderNo(null);
                    orderRecoverry.setPayeeBalance(investorFinance.getAvailable());
                    orderRecoverry.setPayerBalance(borrowerFinance.getAvailable());
                    orderRecoverry.setAmount(paymentOrder.getAmount());
                    orderRecoverry.setAmountReceived(paymentOrder.getAmount().subtract(paymentOrder.getFee()));
                    orderRecoverry.setPayeeFee(paymentOrder.getFee());
                    orderRecoverry.setPoints(0);
                    orderRecoverry.setLaunchDate(new Date());
                    orderRecoverry.setOperator(borrower.getLoginName());
                    orderRecoverry.setIp(CommonUtils.getRemoteIp());
                    orderRecoverry.setMemo(String.format("回款人[%s]提前回款", investor.getId()));
                    orderRecoverry = orderService.persist(orderRecoverry);
                    orderService.flush();

                    userFinance.addBalance(paymentOrder.getAmount(), RechargeBusinessType.GENERAL);
                    Capital capital = new Capital(paymentOrder.getUserId(), CapitalMethod.RECOVERY, CapitalType.CREDIT, paymentOrder.getAmount(), userFinance, paymentOrder.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "提前回款", orderRecoverry.getId());
                    capitalDao.persist(capital);
                    if(paymentOrder.getFee().compareTo(BigDecimal.ZERO)>0){
                        userFinance.subtractBalance(paymentOrder.getFee());
                        capital = new Capital(paymentOrder.getUserId(), CapitalMethod.RECOVERY_FEE, CapitalType.DEBIT, paymentOrder.getFee(), userFinance, paymentOrder.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "提前回款服务费", orderRecoverry.getId());
                        capital = capitalDao.persist(capital);
                        PlatformCapital platformCapital = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.RECOVERY_FEE, paymentOrder.getFee(), BigDecimal.ZERO, capital.getId(), "提前回款服务费", paymentOrder.getOperator(), paymentOrder.getIp(), paymentOrder.getOrderNo());
                        platformCapitalDao.persist(platformCapital);
                    }
                    BasicSetting basic = setting.getBasic();
                    String sms="【%s】投资人您好，您投资的“%s”项目，因借款人申请提前还款，经华善金融复核，于%s回款至您的余额账户。";
                    sms=String.format(basic.getSiteName(),borrowing.getTitle(), DateUtils.format(new Date(), DateUtils.YYYY_MM_DD));
                    try {
                        smsService.send(userDao.get(paymentOrder.getUserId()).getMobile(),sms,SmsType.REPAYMENT_NOTICE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        messagePushService.recoverySuccPush(paymentOrder.getUserId(), paymentOrder.getAmount(), paymentOrder.getFee(), borrowing.getTitle(), null, new Date());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    userFinanceDao.merge(userFinance);
                    logger.info(String.format("结算子订单[%s]结算成功", paymentOrder.getOrderNo()));
                    break;
                }
                case RECOVERY_EARLY_FEE:{
//                    userFinance.subtractCpcnBalance(paymentOrder.getAmount());
//                    Capital capital = new Capital(paymentOrder.getUserId(), CapitalMethod.recovery_fee, CapitalType.debit, paymentOrder.getAmount(), userFinance, paymentOrder.getOrderNo(), paymentOrder.getOperator(), paymentOrder.getIp(), "提前回款服务费");
//                    capital = capitalDao.create(capital);
//                    HuashanCapital huashanCapital = new HuashanCapital(CapitalType.credit, CapitalMethod.recovery_fee, paymentOrder.getAmount(), BigDecimal.ZERO, capital.getId(), "提前回款服务费", paymentOrder.getOperator(), paymentOrder.getIp(), paymentOrder.getOrderNo());
//                    huashanCapitalDao.create(huashanCapital);
                    break;
                }default:
                    throw new RuntimeException("订单状态错误");
            }
        }
    }
}
