package com.klzan.p2p.vo.coupon;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.PeriodUnit;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券使用规则
 */
public class CouponRule{

    /**
     * 使用起始金额 （投资金额大于等于beginAmount可用）
     */
    private BigDecimal beginAmount;

    /**
     * 使用标的类型 （标的类型为 天标or月标 可用）
     */
    private PeriodUnit periodUnit;

    /**
     * 使用起始期限 （标的期限大于等于beginPeriod可用）
     */
    private Integer beginPeriod;

    /**
     *有效期
     */
    private Integer term;

    public BigDecimal getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(BigDecimal beginAmount) {
        this.beginAmount = beginAmount;
    }

    public PeriodUnit getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(PeriodUnit periodUnit) {
        this.periodUnit = periodUnit;
    }

    public Integer getBeginPeriod() {
        return beginPeriod;
    }

    public void setBeginPeriod(Integer beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }
}
