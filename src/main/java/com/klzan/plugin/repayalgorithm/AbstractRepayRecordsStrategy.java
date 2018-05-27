package com.klzan.plugin.repayalgorithm;

import java.math.BigDecimal;

public abstract class AbstractRepayRecordsStrategy implements RepayRecordsStrategy {

    /**
     * 百分数
     */
    public static final BigDecimal PERCENT = new BigDecimal("100");

    /**
     * 小数位数
     */
    public static final int DECIMAL_DIGITS = 27;

    /**
     * ROUNDING_MODE
     */
    public static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

    /**
     * 格式化BigDecimal
     *
     * @param amount
     * @return
     */
    protected BigDecimal format(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }

    /**
     * 月利率/天利率
     *
     * @param interestRate
     * @param dateLength
     * @return
     */
    protected BigDecimal interestRate(BigDecimal interestRate, DateLength dateLength) {
        BigDecimal lengthInYear = new BigDecimal(dateLength.unit().lengthInYear);
        return interestRate.divide(lengthInYear.multiply(PERCENT), DECIMAL_DIGITS, ROUNDING_MODE);
    }

}
