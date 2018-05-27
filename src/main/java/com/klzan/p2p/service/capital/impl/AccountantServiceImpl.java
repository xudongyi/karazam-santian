package com.klzan.p2p.service.capital.impl;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.DateUtils;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.postloan.RepaymentDao;
import com.klzan.p2p.dao.postloan.RepaymentPlanDao;
import com.klzan.p2p.enums.InterestMethod;
import com.klzan.p2p.enums.InvestmentMethod;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.model.RepaymentPlan;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.util.AccountantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会计
 * @author: chenxinglin  Date: 2017-2-9
 */
@Service
public class AccountantServiceImpl implements AccountantService {
    protected Logger logger = LoggerFactory.getLogger(AccountantServiceImpl.class);

    @Inject
    private BorrowingDao borrowingDao;

    @Inject
    private RepaymentDao repaymentDao;

    @Inject
    private RepaymentPlanDao repaymentPlanDao;

    @Override
    public BigDecimal calInterest(BigDecimal amount, BigDecimal interestRate) {
        return AccountantUtils.calExpense(amount, interestRate);
    }

    @Override
    public BigDecimal calFee(BigDecimal amount, BigDecimal feeRate) {
        return AccountantUtils.calFee(amount, feeRate);
    }

    @Override
    public <T> List<T> calOverdue(List<T> repayments) {
        for(T obj: repayments){
            if(obj instanceof RepaymentPlan){
                this.calOverdue((RepaymentPlan)obj);
            }
            if(obj instanceof Repayment){
                this.calOverdue((Repayment)obj);
            }
        }
        return repayments;
    }

    @Override
    public Repayment calOverdue(Repayment repayment) {
        if(repayment == null){
            return null;
        }
        try{
            //已还款不计算
            if(!repayment.getState().equals(RepaymentState.REPAYING)){
                return repayment;
            }

            //逾期天数
            Borrowing borrowing = borrowingDao.get(repayment.getBorrowing());
            Integer seriousOverdueStartPeriod = borrowing.getSeriousOverdueStartPeriod();
            repayment.setOverduePeriod(AccountantUtils.calOverdueDays(repayment.getPayDate(), seriousOverdueStartPeriod));
            repayment.setSeriousOverduePeriod(AccountantUtils.calSeriousOverdueDays(repayment.getPayDate(), seriousOverdueStartPeriod));

            //逾期罚息
            List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findListByRepayment(repayment.getId());
            BigDecimal overdueInterest = BigDecimal.ZERO;
            BigDecimal seriousOverdueInterest = BigDecimal.ZERO;
            for(RepaymentPlan repaymentPlan : repaymentPlans){
                overdueInterest = overdueInterest.add(this.calOverdue(repaymentPlan).getOverdueInterest());
                seriousOverdueInterest = seriousOverdueInterest.add(this.calOverdue(repaymentPlan).getSeriousOverdueInterest());
            }
            repayment.setOverdueInterest(overdueInterest);
            repayment.setSeriousOverdueInterest(seriousOverdueInterest);

        }catch (Exception e){
            e.printStackTrace();
            return repayment;
        }
        return repayment;
    }

    @Override
    public RepaymentPlan calOverdue(RepaymentPlan repaymentPlan) {
        if(repaymentPlan == null){
            return null;
        }
        try{
            //已还款不计算
            if(!repaymentPlan.getState().equals(RepaymentState.REPAYING)){
                return repaymentPlan;
            }

            Borrowing borrowing = borrowingDao.get(repaymentPlan.getBorrowing());
            Integer seriousOverdueStartPeriod = borrowing.getSeriousOverdueStartPeriod(); //严重逾期开始时间
            BigDecimal overdueInterestRate = borrowing.getOverdueInterestRate();  //逾期利率
            BigDecimal seriousOverdueInterestRate = borrowing.getSeriousOverdueInterestRate();  //严重逾期利率

            //逾期天数、逾期罚息
            repaymentPlan.setOverduePeriod(AccountantUtils.calOverdueDays(repaymentPlan.getRepaymentRecord().getPayDate(), seriousOverdueStartPeriod));
            repaymentPlan.setOverdueInterest(AccountantUtils.calExpense(repaymentPlan.getCapitalInterest(), overdueInterestRate, repaymentPlan.getOverduePeriod())); /*逾期罚息 = 本息 * 逾期利率 * 逾期天数 */
            repaymentPlan.setSeriousOverduePeriod(AccountantUtils.calSeriousOverdueDays(repaymentPlan.getRepaymentRecord().getPayDate(), seriousOverdueStartPeriod));
            repaymentPlan.setSeriousOverdueInterest(AccountantUtils.calExpense(repaymentPlan.getCapitalInterest(), seriousOverdueInterestRate, repaymentPlan.getSeriousOverduePeriod())); /*严重逾期罚息 = 本息 * 严重逾期利率 * 严重逾期天数 */

        }catch (Exception e){
            e.printStackTrace();
            return repaymentPlan;
        }
        return repaymentPlan;
    }

    @Override
    public <T> List<T> calAhead(List<T> repayments) {
        for(T obj: repayments){
            if(obj instanceof RepaymentPlan){
                this.calAhead((RepaymentPlan)obj);
            }
            if(obj instanceof Repayment){
                this.calAhead((Repayment)obj);
            }
        }
        return repayments;
    }

    @Override
    public Repayment calAhead(Repayment repayment) {
        if(repayment == null){
            return null;
        }
        //已还款不计算
        if(!repayment.getState().equals(RepaymentState.REPAYING)){
            return repayment;
        }
        //逾期不计算
        if(repayment.getIsOverdue()){
            return repayment;
        }

        Borrowing borrowing = borrowingDao.get(repayment.getBorrowing());
        //上期还款日
        Date lastPayDate = null;
        if(repayment.getPeriod() == 1){//第一期
            lastPayDate = borrowing.getInterestBeginDate();
        }else{
            Repayment lastRepayment = repaymentDao.findByPeriod(borrowing.getId(), repayment.getPeriod()-1);
            if(lastRepayment == null){
                throw new RuntimeException("系统错误");
            }
            lastPayDate = lastRepayment.getPayDate();
        }
//        if (InterestMethod.isTPlusZero(borrowing.getInterestMethod())) {
//            lastPayDate = DateUtils.getMinDateOfDay(DateUtils.addDays(lastPayDate, -1));
//        }
        //时间计算
        Map<String, Object> map = AccountantUtils.calAheadDays(lastPayDate, repayment.getPayDate());
        if((Boolean) map.get("state") && (int)map.get("aheadDays") > 0){
            BigDecimal aheadInterest = BigDecimal.ZERO;
            List<RepaymentPlan> repaymentPlans = this.calAhead(repaymentPlanDao.findListByRepayment(repayment.getId()));
            for(RepaymentPlan repaymentPlan : repaymentPlans){
                aheadInterest = aheadInterest.add(repaymentPlan.getAheadInterest());
            }
            repayment.setAdvance(true);
            repayment.setAheadInterest(aheadInterest);
        }
        if(!(Boolean) map.get("state")){
            repayment.setAdvance(true);
            repayment.setAheadInterest(BigDecimal.ZERO);
        }
        return repayment;
    }

    @Override
    public RepaymentPlan calAhead(RepaymentPlan repaymentPlan) {
        if(repaymentPlan == null){
            return null;
        }
        //已还款不计算
        if(!repaymentPlan.getState().equals(RepaymentState.REPAYING)){
            return repaymentPlan;
        }
        //逾期不计算
        if(repaymentPlan.getIsOverdue()){
            return repaymentPlan;
        }

        Borrowing borrowing = borrowingDao.get(repaymentPlan.getBorrowing());
        Repayment repayment = repaymentDao.get(repaymentPlan.getRepayment());
        //上期还款日
        Date lastPayDate = null;
        if(repayment.getPeriod() == 1){//第一期
            lastPayDate = borrowing.getInterestBeginDate();
        }else{
            Repayment lastRepayment = repaymentDao.findByPeriod(borrowing.getId(), repayment.getPeriod()-1);
            if(lastRepayment == null){
                throw new RuntimeException("系统错误");
            }
            lastPayDate = lastRepayment.getPayDate();
        }
//        if (InterestMethod.isTPlusZero(borrowing.getInterestMethod())) {
//            lastPayDate = DateUtils.getMinDateOfDay(DateUtils.addDays(lastPayDate, -1));
//        }
        //时间计算
        Map<String, Object> map = AccountantUtils.calAheadDays(lastPayDate, repayment.getPayDate());
        if((Boolean) map.get("state") && (int)map.get("aheadDays") > 0){
            repaymentPlan.setAdvance(true);
            System.out.println("----------提前还款计算：利息、当期天数、提前天数------------");
            System.out.println(repaymentPlan.getInterest());
            System.out.println((int)map.get("total"));
            System.out.println((int)map.get("aheadDays"));
            repaymentPlan.setAheadInterest(AccountantUtils.calExpense(repaymentPlan.getInterest(), (int)map.get("total"), (int)map.get("aheadDays")));
        }
        if(!(Boolean) map.get("state")){
            repaymentPlan.setAdvance(true);
            repaymentPlan.setAheadInterest(BigDecimal.ZERO);
        }
        return repaymentPlan;
    }

    @Override
    public RepaymentPlan calCurrentSurplusValue(RepaymentPlan repaymentPlan, Integer totalParts) {
        if (repaymentPlan.getState() == RepaymentState.REPAID) {
            return repaymentPlan;
        }

        // 可转让本金
        BigDecimal transferCapital =  repaymentPlan.getCapital();
        // 可转让利息
        BigDecimal transferInterest = BigDecimal.ZERO;
        if(totalParts == null){
            transferInterest =  repaymentPlan.getInterest();
        }else {
            transferInterest = repaymentPlan.getTransferEveryInterest().multiply(new BigDecimal(totalParts));
        }

        // 今天日期、债权计划结束日期
        Date toDay = DateUtils.getZeroDate(new Date());
        Date endDay = DateUtils.getZeroDate(repaymentPlan.getRepaymentRecordPayDate());
        if (toDay.after(endDay)) {
            return repaymentPlan;
        }

        // 债权计划开始日期
        Borrowing borrowing = borrowingDao.get(repaymentPlan.getBorrowing());
        Date startDay = null;
        switch (borrowing.getPeriodUnit()) {
            case MONTH: { /** 月 */
                if(repaymentPlan.getRepaymentRecord().getPeriod() == 1){//第一期
//                    startDay = DateUtils.getZeroDate(borrowing.getLendingDate());
//                    if(borrowing.getInterestMethod().equals(InterestMethod.T_PLUS_ONE) || borrowing.getInterestMethod().equals(InterestMethod.T_PLUS_ONE_B)){
//                        startDay = DateUtils.getZeroDate(DateUtils.addDays(startDay, 1));
//                    }
                    startDay = borrowing.getInterestBeginDate();
                }else{
                    Repayment lastRepayment = repaymentDao.findByPeriod(borrowing.getId(), repaymentPlan.getRepaymentRecord().getPeriod()-1);
                    if(lastRepayment == null){
                        throw new BusinessProcessException("系统错误");
                    }
                    startDay = DateUtils.getZeroDate(lastRepayment.getPayDate());
                }
                break;
            }
            case DAY: { /** 天 */
//                startDay = DateUtils.addDays(DateUtils.getZeroDate(repaymentPlan.getCreateDate()), repaymentPlan.getRepaymentRecord().getPeriod() - 1);
//                break;
                if(repaymentPlan.getRepaymentRecord().getPeriod() == 1){//第一期
//                    startDay = DateUtils.getZeroDate(borrowing.getLendingDate());
//                    if(borrowing.getInterestMethod().equals(InterestMethod.T_PLUS_ONE) || borrowing.getInterestMethod().equals(InterestMethod.T_PLUS_ONE_B)){
//                        startDay = DateUtils.getZeroDate(DateUtils.addDays(startDay, 1));
//                    }
                    startDay = borrowing.getInterestBeginDate();
                }else{
                    Repayment lastRepayment = repaymentDao.findByPeriod(borrowing.getId(), repaymentPlan.getRepaymentRecord().getPeriod()-1);
                    if(lastRepayment == null){
                        throw new BusinessProcessException("系统错误");
                    }
                    startDay = DateUtils.getZeroDate(lastRepayment.getPayDate());
                }
                break;
            }
            default: {
                break;
            }
        }
        // "债权计划开始日期"在"今天日期"之前时，"债权实际开始日期"为"今天日期"
        if (toDay.after(startDay)) {
            // 总天数
            BigDecimal distanceDays = new BigDecimal(String.valueOf(DateUtils.getDaysOfTwoDate(endDay, startDay)));
            // 剩余天数
            BigDecimal surplusDays = new BigDecimal(String.valueOf(DateUtils.getDaysOfTwoDate(endDay, toDay)));
            //剩余利息 = 当期利息 * 剩余天数 / 总天数
            //剩余利息 = 当期每份利息 * 总份数 * 剩余天数 / 总天数
            // 注：当未购买时，当期利息 = 当期每份利息 * 总份数；当第一次购买后，当期利息 < 当期每份利息 * 总份数；
            if(totalParts == null){
                transferInterest = transferInterest.multiply(surplusDays).divide(distanceDays, 2, BigDecimal.ROUND_DOWN);
            }else {
                logger.info("当期总天数：" + distanceDays);
                logger.info("当期剩余天数：" + surplusDays);
                logger.info("每份利息：" + repaymentPlan.getTransferEveryInterest());
                logger.info("总份数：" + totalParts);
                transferInterest = repaymentPlan.getTransferEveryInterest().multiply(new BigDecimal(totalParts)).multiply(surplusDays).divide(distanceDays, 2, BigDecimal.ROUND_DOWN);
            }
        }
        repaymentPlan.setTotalValue(transferCapital.add(transferInterest).setScale( 2, BigDecimal.ROUND_DOWN));
        repaymentPlan.setCapitalValue(transferCapital);
        repaymentPlan.setInterestValue(transferInterest);

        logger.info("当期剩余利息价值："+repaymentPlan.getInterestValue());

        return repaymentPlan;
    }

    @Override
    public RepaymentPlan calCurrentSurplusValue(RepaymentPlan repaymentPlan, Integer parts, Integer surplusParts, Integer totalParts) {

        if (parts == null || surplusParts == null || totalParts == null || repaymentPlan.getState() == RepaymentState.REPAID) {
            return repaymentPlan;
        }

        repaymentPlan = this.calCurrentSurplusValue(repaymentPlan, totalParts);

        if(parts.equals(totalParts)){
            return repaymentPlan;
        }

        if(parts.compareTo(surplusParts) == 0){
            Integer hasBuyingParts = totalParts - parts;
            BigDecimal hasBuyingInterest = repaymentPlan.getInterestValue().multiply(new BigDecimal(hasBuyingParts)).divide(new BigDecimal(totalParts), 2, BigDecimal.ROUND_DOWN);
            BigDecimal surplusInterest = repaymentPlan.getInterestValue().subtract(hasBuyingInterest);
            repaymentPlan.setCapitalValue(repaymentPlan.getCapitalValue().multiply(new BigDecimal(parts)).divide(new BigDecimal(surplusParts), 2, BigDecimal.ROUND_DOWN));
            repaymentPlan.setInterestValue(surplusInterest);
            repaymentPlan.setTotalValue(repaymentPlan.getCapitalValue().add(repaymentPlan.getInterestValue()));
        }else{
            repaymentPlan.setCapitalValue(repaymentPlan.getCapitalValue().multiply(new BigDecimal(parts)).divide(new BigDecimal(surplusParts), 2, BigDecimal.ROUND_DOWN));
            repaymentPlan.setInterestValue(repaymentPlan.getInterestValue().multiply(new BigDecimal(parts)).divide(new BigDecimal(totalParts), 2, BigDecimal.ROUND_DOWN));
            repaymentPlan.setTotalValue(repaymentPlan.getCapitalValue().add(repaymentPlan.getInterestValue()));
        }

        logger.info("购买的利息价值："+repaymentPlan.getInterestValue());
        logger.info("---------------------");

        return repaymentPlan;
    }

}
