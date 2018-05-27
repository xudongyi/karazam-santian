package com.klzan.plugin.repayalgorithm;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.InterestMethod;
import com.klzan.p2p.enums.RepaymentMethod;
import com.klzan.p2p.model.RepaymentRecord;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 一次性还本付息(月)
 */
public class RepayRecordsCapitalPlusInterestOneTime extends AbstractRepayRecordsStrategy {

    @Override
    public RepayRecords generateRepayRecords(BigDecimal capital, BigDecimal yearInterestRate, DateLength dateLength) {
        int period = dateLength.length();
        BigDecimal lengthInYear = BigDecimal.valueOf(dateLength.unit().lengthInYear);
        BigDecimal periodDecimal = new BigDecimal(period);

        // 总利息
        BigDecimal totalProfit = capital.multiply(yearInterestRate).multiply(periodDecimal).divide(PERCENT).divide(lengthInYear, 2, ROUNDING_MODE);

        RepayRecords repayRecords = new RepayRecords();
        InterestMethod interestMethod = dateLength.interestMethod();
        Date dueDate = DateUtils.addMonths(DateUtils.addDays(new Date(), interestMethod.getDelayInterestCalDay() + interestMethod.getDelayRepayDay()), period);
        repayRecords.addRecord(new RepaymentRecord(dueDate, capital, format(totalProfit), 1));
        repayRecords.fillNextDueDate();
        return repayRecords;
    }

    public boolean support(RepaymentMethod repaymentMethod, DateLength dateLength) {
        return repaymentMethod == RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST && dateLength.isMonth();
    }
}
