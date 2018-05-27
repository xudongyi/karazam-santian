package com.klzan.plugin.repayalgorithm;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.InterestMethod;
import com.klzan.p2p.enums.RepaymentMethod;
import com.klzan.p2p.model.RepaymentRecord;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 按月付息，到期还本
 */
public class RepayRecordsMonthlyInterestLastCapital extends AbstractRepayRecordsStrategy {

    @Override
    public RepayRecords generateRepayRecords(BigDecimal capital, BigDecimal yearInterestRate, DateLength dateLength) {

        /** 月份 */
        int monthAsInteger = dateLength.length();
        BigDecimal monthAsDecimal = new BigDecimal(monthAsInteger);
        /** 一年月份是12个月 */
        BigDecimal monthOfYear = BigDecimal.valueOf(DateUnit.MONTH.lengthInYear);

        /** 总收益 */
        BigDecimal totalProfit = capital.multiply(monthAsDecimal).multiply(yearInterestRate).divide(PERCENT).divide(monthOfYear, 2, ROUNDING_MODE);

        /** 每期收益 */
        BigDecimal phaseProfit = capital.multiply(yearInterestRate).divide(PERCENT).divide(monthOfYear, 2, ROUNDING_MODE);

        /** 剩余利息 */
        BigDecimal residueInterest = totalProfit;
        RepayRecords repayRecords = new RepayRecords();
        Date today = new Date();
        for (Integer i = 1; i <= monthAsInteger; i++) {
            RepaymentRecord repayRecord = new RepaymentRecord();
            repayRecord.setPeriod(i);
            InterestMethod interestMethod = dateLength.interestMethod();
            Date dueDate = DateUtils.addMonths(DateUtils.addDays(today, interestMethod.getDelayInterestCalDay() + interestMethod.getDelayInterestCalDay()), i);
            repayRecord.setPayDate(dueDate);
            if (i == monthAsInteger) {//最后一期
                repayRecord.setCapital(capital);
                repayRecord.setInterest(format(residueInterest));
            } else {
                repayRecord.setCapital(BigDecimal.ZERO);
                repayRecord.setInterest(format(phaseProfit));
                residueInterest = residueInterest.subtract(phaseProfit);
            }
            repayRecords.addRecord(repayRecord);
        }
        repayRecords.fillNextDueDate();
        return repayRecords;
    }

    public boolean support(RepaymentMethod repaymentMethod, DateLength dateLength) {
        return repaymentMethod == RepaymentMethod.EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL && dateLength.isMonth();
    }
}
