/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.util;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.model.RepaymentPlan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会计工具
 * 统一规则: 1.保留小数点两位 2.利息向下取整、服务费向上取整
 * @author: chenxinglin
 */
public class AccountantUtils {

    /**
     * 服务费 ( = 金额 * 比率 / 100 )
     * @param amount
     * @param rate
     * @return
     */
    public static BigDecimal calFee(BigDecimal amount, BigDecimal rate) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) == 0){
            amount = BigDecimal.ZERO;
        }
        if(rate == null || rate.compareTo(BigDecimal.ZERO) == 0){
            rate = BigDecimal.ZERO;
        }
        return amount.multiply(rate).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
    }

    /**
     * 利息 ( = 金额 * 比率 / 100 )
     * @param amount
     * @param rate
     * @return
     */
    public static BigDecimal calExpense(BigDecimal amount, BigDecimal rate) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) == 0){
            amount = BigDecimal.ZERO;
        }
        if(rate == null || rate.compareTo(BigDecimal.ZERO) == 0){
            rate = BigDecimal.ZERO;
        }
        return amount.multiply(rate).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 罚息 （ = 金额 * 利率 * 天数 / 100 ）
     * @param amount
     * @param rate
     * @return
     */
    public static BigDecimal calExpense(BigDecimal amount, BigDecimal rate, Integer days) {
        if(amount == null){
            amount = BigDecimal.ZERO;
        }
        if(rate == null){
            rate = BigDecimal.ZERO;
        }
        if(days == null){
            days = 0;
        }
        return amount.multiply(rate).multiply(new BigDecimal(days.intValue())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 提前利息 （ = 利息*（提前天数）/总天数 ）
     * @param amount
     * @param total 当期总天数
     * @param aheadDays 提前天数
     * @return
     */
    public static BigDecimal calExpense(BigDecimal amount, Integer total, Integer aheadDays) {
        if(amount == null){
            amount = BigDecimal.ZERO;
        }
        if(total == null){
            total = 0;
        }
        if(aheadDays == null){
            aheadDays = 0;
        }
        return amount.multiply(new BigDecimal(aheadDays)).divide(new BigDecimal(total), 2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 平均 ( = 金额 / 份数  )
     * @param amount
     * @param parts
     * @return
     */
    public static BigDecimal calAverage(BigDecimal amount, Integer parts) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) == 0){
            amount = BigDecimal.ZERO;
        }
        if(parts == null){
            parts = 0;
        }
        return amount.divide(new BigDecimal(parts.intValue()), 8, BigDecimal.ROUND_DOWN);
    }

    /**
     * 逾期天数
     * @param payDate
     * @param seriousOverdueStartPeriod
     * @return
     */
    public static int calOverdueDays(Date payDate, Integer seriousOverdueStartPeriod) {
        int days = 0;
        Date date = new Date();
        if(payDate.before(date)){
            days = (int) DateUtils.getDaysOfTwoDate(DateUtils.getZeroDate(date), DateUtils.getZeroDate(payDate));
        }
        if(days > seriousOverdueStartPeriod){
            days = 0;
        }
        return days;
    }

    /**
     * 严重逾期天数
     * @param payDate
     * @param seriousOverdueStartPeriod
     * @return
     */
    public static int calSeriousOverdueDays(Date payDate, Integer seriousOverdueStartPeriod) {
        int days = 0;
        Date date = new Date();
        if(payDate.before(date)){
            days = (int) DateUtils.getDaysOfTwoDate(DateUtils.getZeroDate(date), DateUtils.getZeroDate(payDate));
        }
        if(days <= seriousOverdueStartPeriod){
            days = 0;
        }
        return days;
    }

    /**
     * 提前天数（按月）
     * @param lastPayDate
     * @param payDate
     * @return days 当期总天数、提前天数
     */
    public static Map<String, Object> calAheadDays(Date lastPayDate, Date payDate) {
        Map map = new HashMap<String, Object>();
        Date date = DateUtils.getZeroDate(new Date());
        lastPayDate = DateUtils.getZeroDate(lastPayDate);
        payDate = DateUtils.getZeroDate(payDate);
        if(lastPayDate.before(date)  && (date.before(payDate) || date.equals(payDate))){
            map.put("state", true);  //是否当期
            map.put("total", (int) DateUtils.getDaysOfTwoDate(payDate, lastPayDate));  //当期总天数
            map.put("aheadDays", (int) DateUtils.getDaysOfTwoDate(date, lastPayDate));  //提前天数
        }else if(date.before(lastPayDate) || date.equals(lastPayDate)){
            map.put("state", false);  //是否当期
            map.put("total", 0);  //当期总天数
            map.put("aheadDays", 0);  //提前天数
        }else {
            throw new RuntimeException("参数错误");
        }
        return map;
    }

    /**
     * 借款人待还金额 （本金 + 利息 + 还款服务费）
     * @param repayments
     * @return
     */
    public static BigDecimal getRepaymentAmountInterestFee(List<Repayment> repayments) {
        BigDecimal amount = BigDecimal.ZERO;
        for(Repayment repayment : repayments){
            amount = amount.add(repayment.getCapitalInterestFee());
        }
        return amount;
    }

    /**
     * 投资人待收金额 （本金 + 利息 - 回收服务费）
     * @param repaymentPlans
     * @param investorId
     * @return
     */
    public static BigDecimal getRecoveryAmountInterestFee(List<RepaymentPlan> repaymentPlans, Integer investorId) {
        BigDecimal amount = BigDecimal.ZERO;
        for(RepaymentPlan RepaymentPlan : repaymentPlans){
            if(RepaymentPlan.getInvestor().equals(investorId)){
                amount = amount.add(RepaymentPlan.getCapitalInterestFee());
            }
        }
        return amount;
    }

}

///**
// * 会计
// * @author: chenxinglin
// */
//class Accountant {
//
//    private BigDecimal capital;
//
//    private BigDecimal interest;
//
//    private BigDecimal aheadInterest;
//
//    private BigDecimal overdueInterest;
//
//    private BigDecimal seriousOverdueInterest;
//
//    private BigDecimal fee;
//
//
//}
