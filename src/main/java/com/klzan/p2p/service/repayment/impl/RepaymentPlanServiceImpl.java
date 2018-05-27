/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.repayment.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.postloan.RepaymentDao;
import com.klzan.p2p.dao.postloan.RepaymentPlanDao;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.model.RepaymentPlan;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.vo.repayment.RepaymentPlanVo;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: chenxinglin  Date: 2016/11/3 Time: 11:52
 */
@Service
public class RepaymentPlanServiceImpl extends BaseService<RepaymentPlan> implements RepaymentPlanService {

    @Inject
    private RepaymentPlanDao repaymentPlanDao;

    @Inject
    private BorrowingDao borrowingDao;

    @Inject
    private RepaymentDao repaymentDao;

    @Override
    public List<RepaymentPlan> findList(Integer borrowingId) {
        return repaymentPlanDao.findList(borrowingId);
    }

    @Override
    public List<RepaymentPlan> findList(Integer borrowingId, Integer transferId) {
        return repaymentPlanDao.findList(borrowingId, transferId);
    }

    @Override
    public List<RepaymentPlan> findList(Integer borrowingId, Integer userId, Integer investmentId) {
        return repaymentPlanDao.findList(borrowingId, userId, investmentId);
    }

    @Override
    public List<RepaymentPlan> findListCanTransfer(Integer borrowingId, Integer userId) {
        return repaymentPlanDao.findListCanTransfer(borrowingId, userId);
    }

    @Override
    public List<RepaymentPlan> findListByInvestment(Integer investmentId) {
        return repaymentPlanDao.findListByInvestment(investmentId);
    }

    @Override
    public BigDecimal countProfit(){
        return repaymentPlanDao.countProfit();
    }

    @Override
    public List<RepaymentPlan> countYesterdayWaitingProfit(Integer userId) {

        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.countYesterdayWaitingProfit(userId);

        return repaymentPlans;
    }

    @Override
    public BigDecimal countWillProfit(){
        return repaymentPlanDao.countWillProfit();
    }

    @Override
    public List<RepaymentPlan> waitingProfit(Integer userId, Integer days) {
        return repaymentPlanDao.waitingProfit(userId, days);
    }

    @Override
    public List<RepaymentPlan> waitingProfit(Integer userId) {
        return repaymentPlanDao.waitingProfit(userId);
    }

    @Override
    public List<RepaymentPlan> alreadyProfit(Integer userId) {
        return repaymentPlanDao.alreadyProfit(userId);
    }

    public RepaymentPlan findByOrderNo(String orderNo){
        return repaymentPlanDao.findByOrderNo(orderNo);
    }

    /**
     * 累计已还本金
     * @return
     */
    @Override
    public BigDecimal countPaidCapital(){
        return repaymentPlanDao.countPaidCapital();
    }
    /**
     * 昨日利息收益
     * @return
     */
    @Override
    public BigDecimal calyesterdayIncome(Integer userId){
       BigDecimal yesterdayIncome=BigDecimal.ZERO;
       if(userId==null){
           return yesterdayIncome;
       }

        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findRecoveries(userId, "");
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            if (repaymentPlan.getAdvance()) {
                continue;
            }
            Date yesterday = DateUtils.getMaxDateOfDay(DateUtils.addDays(new Date(), -1));
            Borrowing borrowing = borrowingDao.get(repaymentPlan.getBorrowing());
            Date interestBeginDate = DateUtils.getMinDateOfDay(borrowing.getInterestBeginDate());
            if(interestBeginDate.before(yesterday)){
                Date payDate = DateUtils.getMaxDateOfDay(repaymentPlan.getRepaymentRecord().getPayDate());
                Integer days = new Double(DateUtils.getDaysOfTwoDate(payDate, interestBeginDate)).intValue();
                BigDecimal profitEveryDay = (repaymentPlan.getRecoveryAmount().subtract(repaymentPlan.getCapital())).divide(new BigDecimal(days), 2, BigDecimal.ROUND_DOWN);
                yesterdayIncome = yesterdayIncome.add(profitEveryDay);
            }
        }

        return yesterdayIncome;
    }

    @Override
    public BigDecimal calTodayIncome(Integer userId) {

        BigDecimal todayIncome = BigDecimal.ZERO;

        if(userId == null){
            return todayIncome;
        }

        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findList(null, userId, null);
        for(RepaymentPlan repaymentPlan : repaymentPlans){
            if(repaymentPlan.getState().equals(RepaymentState.REPAYING)){

                // 今天日期、债权计划结束日期
                Date toDay = DateUtils.getZeroDate(new Date());
                Date endDay = DateUtils.getZeroDate(repaymentPlan.getRepaymentRecordPayDate());
                if (toDay.after(endDay)) {
                        continue;
                }
                // 债权计划开始日期
                Borrowing borrowing = borrowingDao.get(repaymentPlan.getBorrowing());
                Date startDay = null;
                switch (borrowing.getPeriodUnit()) {
                    case MONTH: { /** 月 */
                        if(repaymentPlan.getRepaymentRecord().getPeriod() == 1){//第一期
                            startDay = DateUtils.getZeroDate(borrowing.getLendingDate());
                        }else{
                            Repayment lastRepayment = repaymentDao.findByPeriod(borrowing.getId(), repaymentPlan.getRepaymentRecord().getPeriod()-1);
                            if(lastRepayment == null){
                                throw new RuntimeException("系统错误");
                            }
                            startDay = DateUtils.getZeroDate(lastRepayment.getPayDate());
                        }
                        break;
                    }
//            case day: { /** 天 */
//                startDay = DateUtils.addDays(DateUtils.getZeroDate(repaymentPlan.getCreateDate()), repaymentPlan.getRepaymentRecord().getPeriod() - 1);
//                break;
//            }
                    default: {
                        break;
                    }
                }
                // "债权计划开始日期"在"今天日期"之前时，"债权实际开始日期"为"今天日期"
                if (toDay.after(startDay)) {
                    // 总天数
                    BigDecimal distanceDays = new BigDecimal(String.valueOf(DateUtils.getDaysOfTwoDate(endDay, startDay)));

                    //当天利息 = 总利息 / 总天数
                    todayIncome = todayIncome.add(repaymentPlan.getInterest().divide(distanceDays, 2, BigDecimal.ROUND_HALF_EVEN));

                }




            }
        }



        return todayIncome;
    }

    @Override
    public List<RepaymentPlan> findRecoveries(Integer userId, String yearMonth) {
        return repaymentPlanDao.findRecoveries(userId, yearMonth);
    }

    @Override
    public List<RepaymentPlan> findRecoveries(Integer userId, RepaymentState state) {
        return repaymentPlanDao.findRecoveries(userId, state);
    }

    @Override
    public PageResult<RepaymentPlan> findRecoveries(Integer userId, RepaymentState state, PageCriteria criteria) {
        return repaymentPlanDao.findRecoveries(userId, state, criteria);
    }

    @Override
    public PageResult<RepaymentPlanVo> findPage(Integer userId, RepaymentState state, PageCriteria criteria) {
        Map map = new HashMap();
        map.put("userId", userId);
        if (null != state) {
            map.put("state", state.name());
            List<String> progress = new ArrayList<>();
            progress.add(BorrowingProgress.REPAYING.name());
            progress.add(BorrowingProgress.COMPLETED.name());
            map.put("borrowingProgress", progress);
        }
        if (!criteria.getParams().isEmpty()) {
            map.put("searchParamas", criteria.getParams());
        }
        return myDaoSupport.findPage("com.klzan.p2p.mapper.RepaymentPlanMapper.findPage", map ,criteria);
    }

    @Override
    public List<RepaymentPlanVo> findList(Integer userId, RepaymentState state, PageCriteria criteria) {
        Map map = new HashMap();
        map.put("userId", userId);
        if (null != state) {
            map.put("state", state.name());
            List<String> progress = new ArrayList<>();
            progress.add(BorrowingProgress.REPAYING.name());
            progress.add(BorrowingProgress.COMPLETED.name());
            map.put("borrowingProgress", progress);
        }
        if (!criteria.getParams().isEmpty()) {
            map.put("searchParamas", criteria.getParams());
        }
        return myDaoSupport.findList("com.klzan.p2p.mapper.RepaymentPlanMapper.findPage", map);
    }

    @Override
    public List<RepaymentPlan> findListByRepayment(Integer repaymentId) {
        return repaymentPlanDao.findListByRepayment(repaymentId);
    }
    @Override
    public List<RepaymentPlan> findListByTime(Integer userId, Date starDate,Date endDate){
        return repaymentPlanDao.findListByTime(userId,starDate,endDate);
    }
}
