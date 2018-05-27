package com.klzan.p2p.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Embeddable
public class RepaymentRecord implements Serializable {
    private static final long serialVersionUID = -1548061140232424815L;

    /**
     * 还款日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pay_date", nullable = false)
    private Date payDate;

    /**
     * 下期还款日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "next_pay_date")
    private Date nextPayDate;

    /**
     * 本金
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal capital;

    /**
     * 利息
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal interest;

    /**
     * 当前期数
     */
    @Column(nullable = false)
    private Integer period;

    @Transient
    private Integer investmentId;

    public RepaymentRecord() {
    }

    public RepaymentRecord(Date payDate, BigDecimal capital, BigDecimal interest, Integer period) {
        this.payDate = payDate;
        this.capital = capital;
        this.interest = interest;
        this.period = period;
    }

    public void setNextPayDate(Date nextPayDate) {
        this.nextPayDate = nextPayDate;
    }

    public Date getNextPayDate() {
        return this.nextPayDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getPayAmount() {
        return this.capital.add(interest);
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public boolean isCurrentPeriod(Integer period) {
        return this.period.equals(period);
    }
}
