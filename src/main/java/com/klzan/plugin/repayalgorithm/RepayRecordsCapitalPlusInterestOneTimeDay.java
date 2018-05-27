package com.klzan.plugin.repayalgorithm;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.InterestMethod;
import com.klzan.p2p.enums.RepaymentMethod;
import com.klzan.p2p.model.RepaymentRecord;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 一次性还本付息(天)
 */
public class RepayRecordsCapitalPlusInterestOneTimeDay extends AbstractRepayRecordsStrategy {
    // 一年天数
    private static final Integer FIXED_CAL_YEAR_DAYS = 360;

    @Override
    public RepayRecords generateRepayRecords(BigDecimal capital, BigDecimal yearInterestRate, DateLength dateLength) {
        Date today = DateUtils.getMinDateOfDay(new Date());
        InterestMethod interestMethod = dateLength.interestMethod();
        int period = dateLength.length();
        BigDecimal lengthInYear = BigDecimal.valueOf(FIXED_CAL_YEAR_DAYS);
        BigDecimal periodDecimal = new BigDecimal(period);

        // 总利息
        BigDecimal totalProfit = capital.multiply(yearInterestRate).multiply(periodDecimal).divide(PERCENT).divide(lengthInYear, 2, ROUNDING_MODE);

        RepayRecords repayRecords = new RepayRecords();
//        Date dueDate = DateUtils.addDays(DateUtils.addDays(today, interestMethod.getDelayInterestCalDay() + interestMethod.getDelayRepayDay()), period);
        Date dueDate = DateUtils.addDays(DateUtils.addDays(today, interestMethod.getDelayInterestCalDay() /*+ interestMethod.getDelayRepayDay()*/), period);
        repayRecords.addRecord(new RepaymentRecord(dueDate, capital, format(totalProfit), 1));
        repayRecords.fillNextDueDate();
        return repayRecords;
    }

    public boolean support(RepaymentMethod repaymentMethod, DateLength dateLength) {
        return repaymentMethod == RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST && dateLength.isDay();
    }
}
