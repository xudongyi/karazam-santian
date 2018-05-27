package com.klzan.plugin.repayalgorithm;

import com.klzan.p2p.enums.InterestMethod;
import com.klzan.p2p.enums.PeriodUnit;
import com.klzan.p2p.enums.RepaymentMethod;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.Investment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RepayRecordsStrategyHolder {
    private static RepayRecordsStrategyHolder repayRecordsStrategyHolder;
    private List<RepayRecordsStrategy> repayRecordsStrategies;

    public static RepayRecordsStrategyHolder instanse() {
        if (repayRecordsStrategyHolder != null) {
            return repayRecordsStrategyHolder;
        }
        repayRecordsStrategyHolder = new RepayRecordsStrategyHolder();
        repayRecordsStrategyHolder.addStrategy(new RepayRecordsAverageCapitalPlusInterest());
        repayRecordsStrategyHolder.addStrategy(new RepayRecordsCapitalPlusInterestOneTime());
        repayRecordsStrategyHolder.addStrategy(new RepayRecordsMonthlyInterestLastCapital());
        repayRecordsStrategyHolder.addStrategy(new RepayRecordsFixedInterestLastCapital());
        repayRecordsStrategyHolder.addStrategy(new RepayRecordsCapitalPlusInterestOneTimeDay());
        return repayRecordsStrategyHolder;
    }

    /**
     * 生成还款和计划
     * @param borrowing
     * @param investments
     * @return
     */
    public Repays getRepays(Borrowing borrowing, List<Investment> investments) {
        RepaymentMethod repaymentMethod = borrowing.getRepaymentMethod();
        BigDecimal yearInterestRate = borrowing.getRealInterestRate();
        InterestMethod interestMethod = borrowing.getInterestMethod();
        DateLength dateLength;
        if (borrowing.getPeriodUnit().equals(PeriodUnit.MONTH)) {
            dateLength = DateLength.month(borrowing.getPeriod(), interestMethod);
        } else if (borrowing.getPeriodUnit().equals(PeriodUnit.DAY)) {
            dateLength = DateLength.days(borrowing.getPeriod(), interestMethod);
        } else {
            dateLength = DateLength.year(borrowing.getPeriod(), interestMethod);
        }
        RepayRecords records = generateRepayRecords(repaymentMethod, yearInterestRate, dateLength, investments);
        return records.convertRepayment(borrowing.getId(), borrowing.getBorrower());
    }

    /**
     * 生成还款计划对象-单条投资记录
     *
     * @param repaymentMethod
     * @param capital
     * @param yearInterestRate
     * @param dateLength
     * @return
     */
    public RepayRecords generateRepayRecords(RepaymentMethod repaymentMethod, BigDecimal capital, BigDecimal yearInterestRate, DateLength dateLength) {
        for (RepayRecordsStrategy repayRecordsStrategy : repayRecordsStrategies) {
            if (repayRecordsStrategy.support(repaymentMethod, dateLength)) {
                return repayRecordsStrategy.generateRepayRecords(capital, yearInterestRate, dateLength);
            }
        }
        throw new IllegalArgumentException(repaymentMethod.name() + "," + dateLength.unit().name() + " is not supported");
    }

    /**
     * 生成还款计划对象-多条投资记录
     *
     * @param repaymentMethod
     * @param yearInterestRate
     * @param dateLength
     * @param investments
     * @return
     */
    private RepayRecords generateRepayRecords(RepaymentMethod repaymentMethod, BigDecimal yearInterestRate, DateLength dateLength, List<Investment> investments) {
        RepayRecords records = new RepayRecords();
        for (Investment investment : investments) {
            RepayRecords repayRecords = generateRepayRecords(repaymentMethod, investment.getAmount(), yearInterestRate, dateLength);
            records.addRecordsByInvestment(investment, repayRecords.getRepaymentPlans());
        }
        return records;
    }

    private void addStrategy(RepayRecordsStrategy repayRecordsStrategy) {
        if (repayRecordsStrategies == null) {
            repayRecordsStrategies = new ArrayList<>();
        }
        repayRecordsStrategies.add(repayRecordsStrategy);
    }
}
