package com.klzan.plugin.repayalgorithm;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.InterestMethod;
import com.klzan.p2p.enums.RepaymentMethod;
import com.klzan.p2p.model.RepaymentRecord;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 等额本息
 */
public class RepayRecordsAverageCapitalPlusInterest extends AbstractRepayRecordsStrategy {

    @Override
    public RepayRecords generateRepayRecords(BigDecimal capital, BigDecimal interestRate, DateLength dateLength) {
        // 期数（小数）
        int period = dateLength.length();
        BigDecimal periodDecimal = new BigDecimal(period);
        // 月利率/天利率
        BigDecimal interestRateInDateLength = interestRate(interestRate, dateLength);

        /** 月利率倍数 */
        /** 1+i = 1+月利率 */
        BigDecimal monthInterestRateMultiple = BigDecimal.ONE.add(interestRateInDateLength);

        /*******************************************************************/
        /** 每期本息（27位小数，四舍五入） */
        /*******************************************************************/
        /** BX = a*i*(1+i)^N/[(1+i)^N-1] */
        /** 每期本息 = [金额*月利率*(1+月利率)^总期数]/[(1+月利率)^总期数－1] */
        /*******************************************************************/
        BigDecimal periodPrincipalInterest = capital
                .multiply(interestRateInDateLength)
                .multiply(monthInterestRateMultiple.pow(period))
                .divide(monthInterestRateMultiple.pow(period).subtract(BigDecimal.ONE), 2, ROUNDING_MODE);

        // 利息
        BigDecimal interest = capital
                .multiply(interestRateInDateLength)
                .multiply(monthInterestRateMultiple.pow(period))
                .divide(monthInterestRateMultiple.pow(period).subtract(BigDecimal.ONE), 2, ROUNDING_MODE)
                .multiply(periodDecimal).subtract(capital).setScale(5, ROUNDING_MODE);

        periodPrincipalInterest = format(periodPrincipalInterest);
        interest = format(interest);
        // 剩余本金
        BigDecimal residuePrincipal = capital;
        // 剩余利息
        BigDecimal residueInterest = interest;
        RepayRecords repayRecords = new RepayRecords();
        for (Integer i = 1; i <= period; i++) {
            /*******************************************************************/
            /** 当期本金（27位小数，四舍五入） */
            /*******************************************************************/
            /** B = a*i*(1+i)^(n-1)/[(1+i)^N-1] */
            /** 当期本金 = 金额*月利率*(1+月利率)^(当期数-1)/[(1+月利率)^总期数-1] */
            /*******************************************************************/
            BigDecimal currentPeriodCapital = null;
            if (i == period) {
                currentPeriodCapital = residuePrincipal;
            } else {
                currentPeriodCapital = capital
                        .multiply(interestRateInDateLength)
                        .multiply(monthInterestRateMultiple.pow(i - 1))
                        .divide(monthInterestRateMultiple.pow(period).subtract(BigDecimal.ONE), 2, ROUNDING_MODE);
                // 剩余本金
                residuePrincipal = residuePrincipal.subtract(currentPeriodCapital);
            }
            /*******************************************************************/
            /** 当期利息 */
            /*******************************************************************/
            /**
             * X = BX-B = a*i*(1+i)^N/[(1+i)^N-1] -
             * a*i(1+i)^(n-1)/[(1+i)^N-1]
             */
            /** 当期利息 = 每期本息 - 当期本金 */
            /*******************************************************************/
            BigDecimal currentPeriodInterest;
            if (i == period) {
                currentPeriodInterest = residueInterest;
            } else {
                currentPeriodInterest = periodPrincipalInterest.subtract(currentPeriodCapital);
                // 剩余利息
                residueInterest = residueInterest.subtract(currentPeriodInterest);
            }

            RepaymentRecord repayRecord = new RepaymentRecord();

            InterestMethod interestMethod = dateLength.interestMethod();
            Date dueDate = DateUtils.addMonths(DateUtils.addDays(new Date(), interestMethod.getDelayInterestCalDay() + interestMethod.getDelayRepayDay()), i);
            if (dateLength.isOneMonth()) {
                dueDate = DateUtils.addDays(dueDate, -1);
            }
            repayRecord.setPayDate(dueDate);
            repayRecord.setPeriod(i);
            if (i == period) {
                repayRecord.setCapital(format(residuePrincipal));
                repayRecord.setInterest(format(residueInterest));
            } else {
                repayRecord.setCapital(format(currentPeriodCapital));
                repayRecord.setInterest(format(currentPeriodInterest));
            }
            repayRecords.addRecord(repayRecord);
        }
        repayRecords.fillNextDueDate();
        return repayRecords;
    }

    @Override
    public boolean support(RepaymentMethod repaymentMethod, DateLength dateLength) {
        return repaymentMethod == RepaymentMethod.EACH_PERIOD_AVG_CAPITAL_PLUS_INTEREST && dateLength.isMonth();
    }

}
