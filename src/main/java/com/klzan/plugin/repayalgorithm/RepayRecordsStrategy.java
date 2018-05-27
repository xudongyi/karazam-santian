package com.klzan.plugin.repayalgorithm;

import com.klzan.p2p.enums.RepaymentMethod;

import java.math.BigDecimal;

public interface RepayRecordsStrategy {

    /**
     * 生成还款计划对象
     *
     * @param capital
     * @param yearInterestRate
     * @param dateLength
     * @return
     */
    RepayRecords generateRepayRecords(BigDecimal capital, BigDecimal yearInterestRate, DateLength dateLength);

    /**
     * 判断是否支持当前还款方式+还款期限
     *
     * @param repaymentMethod
     * @param dateLength
     * @return
     */
    boolean support(RepaymentMethod repaymentMethod, DateLength dateLength);
}
