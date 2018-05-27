package com.klzan.plugin.repayalgorithm;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.InterestMethod;
import com.klzan.p2p.enums.RepaymentMethod;
import com.klzan.p2p.model.RepaymentRecord;

import java.math.BigDecimal;
import java.util.*;

/**
 * 固定日期付息，到期还本
 */
public class RepayRecordsFixedInterestLastCapital extends AbstractRepayRecordsStrategy {

    // 计算利息周期
    private static final Integer FIXED_CAL_PERIOD_DAYS = 30;
    // 一年天数
    private static final Integer FIXED_CAL_YEAR_DAYS = 360;

    @Override
    public RepayRecords generateRepayRecords(BigDecimal capital, BigDecimal yearInterestRate, DateLength dateLength) {
        Date today = DateUtils.getMinDateOfDay(new Date());
        InterestMethod interestMethod = dateLength.interestMethod();
        Date startCalInterestDate = DateUtils.addDays(today, interestMethod.getDelayInterestCalDay());
        int borrowingDays = dateLength.length();
        /** 借款天数 */
        int periodDays = dateLength.length();

        /** 一年天数 */
        BigDecimal daysOfYear = BigDecimal.valueOf(FIXED_CAL_YEAR_DAYS);
        /** 总收益 */
        BigDecimal totalProfit = capital.multiply(new BigDecimal(periodDays)).multiply(yearInterestRate).divide(PERCENT).divide(daysOfYear, 2, ROUNDING_MODE);
        /** 每天收益 */
        BigDecimal dayProfit = capital.multiply(yearInterestRate).divide(PERCENT).divide(daysOfYear, 5, ROUNDING_MODE);
        /** 每期收益 */
        BigDecimal periodProfit = format(dayProfit.multiply(new BigDecimal(FIXED_CAL_PERIOD_DAYS)));
        return interestMethod.getFrontFullMonth() ? getRepayRecordsAsFrontFullMonth(capital, today, interestMethod, startCalInterestDate, borrowingDays, totalProfit, dayProfit, periodProfit)
                : getRepayRecordsAsBehindFullMonth(capital, today, interestMethod, startCalInterestDate, borrowingDays, totalProfit, dayProfit, periodProfit);
    }

    public boolean support(RepaymentMethod repaymentMethod, DateLength dateLength) {
        return repaymentMethod == RepaymentMethod.FIXED_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL && dateLength.isDay();
    }

    /**
     * 前期整月
     * @param capital
     * @param today
     * @param interestMethod
     * @param startCalInterestDate
     * @param borrowingDays
     * @param totalProfit
     * @param dayProfit
     * @param periodProfit
     * @return
     */
    private RepayRecords getRepayRecordsAsFrontFullMonth(BigDecimal capital, Date today, InterestMethod interestMethod, Date startCalInterestDate, int borrowingDays, BigDecimal totalProfit, BigDecimal dayProfit, BigDecimal periodProfit) {
        // 最后还款日期
        Date lastRepayDate = DateUtils.addDays(today, borrowingDays + interestMethod.getDelayInterestCalDay());
        int monthsCount = DateUtils.getMonthsOfTwoDate(startCalInterestDate, lastRepayDate);

        // 期数-还款日期
        Map<Integer, Date> periods = new LinkedHashMap<>();
        for (int i = 1; i <= monthsCount; i++) {
            Date payDate = DateUtils.addMonths(startCalInterestDate, i);
            if (DateUtils.compareTwoDate(payDate, lastRepayDate) == -1) {
                periods.put(i, lastRepayDate);
                break;
            }
            periods.put(i, payDate);
        }

        /** 剩余利息 */
        BigDecimal residueInterest = totalProfit;
        RepayRecords repayRecords = new RepayRecords();
        for (int i = 1; i <= monthsCount; i++) {
            RepaymentRecord repayRecord = new RepaymentRecord();
            repayRecord.setPeriod(i);
            Date dueDate = periods.get(i);
            repayRecord.setPayDate(dueDate);
            if (i == monthsCount) { // 最后一期
                double scatterDays;
                if (monthsCount > 1) {
                    scatterDays = DateUtils.getDaysOfTwoDate(dueDate, periods.get(i - 1));
                } else {
                    scatterDays = DateUtils.getDaysOfTwoDate(dueDate, startCalInterestDate);
                }
                repayRecord.setCapital(capital);
                repayRecord.setInterest(format(new BigDecimal(scatterDays).multiply(dayProfit)));
            } else {
                repayRecord.setCapital(BigDecimal.ZERO);
                repayRecord.setInterest(format(periodProfit));
                residueInterest = residueInterest.subtract(periodProfit);
            }
            repayRecords.addRecord(repayRecord);
        }
        repayRecords.fillNextDueDate();
        List<RepaymentRecord> repaymentPlans = repayRecords.getRepaymentPlans();
        Collections.sort(repaymentPlans, new Comparator<RepaymentRecord>() {
            public int compare(RepaymentRecord o1, RepaymentRecord o2) {
                return o1.getPeriod().compareTo(o2.getPeriod());
            }
        });

        return repayRecords;
    }

    /**
     * 后期整月
     * @param capital
     * @param today
     * @param interestMethod
     * @param startCalInterestDate
     * @param borrowingDays
     * @param totalProfit
     * @param dayProfit
     * @param periodProfit
     * @return
     */
    private RepayRecords getRepayRecordsAsBehindFullMonth(BigDecimal capital, Date today, InterestMethod interestMethod, Date startCalInterestDate, int borrowingDays, BigDecimal totalProfit, BigDecimal dayProfit, BigDecimal periodProfit) {
        // 最后还款日期
        Date lastRepayDate = DateUtils.addDays(today, borrowingDays + interestMethod.getDelayInterestCalDay());
        int monthsCount = DateUtils.getMonthsOfTwoDate(startCalInterestDate, lastRepayDate);

        // 期数-还款日期
        Map<Integer, Date> periods = new LinkedHashMap<>();
        for (int i = 0, j = monthsCount; i <= monthsCount; i++, j--) {
            Date payDate = DateUtils.addMonths(lastRepayDate, -j);
            if (DateUtils.compareTwoDate(payDate, today) != -1) {
                continue;
            }
            periods.put(i, payDate);
        }

        /** 剩余利息 */
        BigDecimal residueInterest = totalProfit;
        RepayRecords repayRecords = new RepayRecords();
        for (int i = monthsCount; i >= 1; i--) {
            RepaymentRecord repayRecord = new RepaymentRecord();
            repayRecord.setPeriod(i);
            Date dueDate = periods.get(i);
            repayRecord.setPayDate(dueDate);
            if (i == 1) {//第一期
                double scatterDays = DateUtils.getDaysOfTwoDate(dueDate, startCalInterestDate);
                repayRecord.setCapital(BigDecimal.ZERO);
                repayRecord.setInterest(format(new BigDecimal(scatterDays).multiply(dayProfit)));
//                repayRecord.setInterest(format(residueInterest));
            } else if (i == monthsCount) { // 最后一期
                repayRecord.setCapital(capital);
                repayRecord.setInterest(format(periodProfit));
                residueInterest = residueInterest.subtract(periodProfit);
            } else {
                repayRecord.setCapital(BigDecimal.ZERO);
                repayRecord.setInterest(format(periodProfit));
                residueInterest = residueInterest.subtract(periodProfit);
            }
            repayRecords.addRecord(repayRecord);
        }
        repayRecords.fillNextDueDate();
        List<RepaymentRecord> repaymentPlans = repayRecords.getRepaymentPlans();
        Collections.sort(repaymentPlans, new Comparator<RepaymentRecord>() {
            public int compare(RepaymentRecord o1, RepaymentRecord o2) {
                return o1.getPeriod().compareTo(o2.getPeriod());
            }
        });

        return repayRecords;
    }
}
