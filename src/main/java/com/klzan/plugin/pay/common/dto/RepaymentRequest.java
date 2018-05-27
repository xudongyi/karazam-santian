package com.klzan.plugin.pay.common.dto;

import java.math.BigDecimal;

/**
 * 项目还款
 * Created by suhao Date: 2017/11/22 Time: 14:51
 *
 * @version: 1.0
 */
public class RepaymentRequest extends Request {

    /**
     *
     */
    private Integer borrowing;

    /**
     *
     */
    private Integer repayment;

    /**
     *
     */
    private Boolean early = Boolean.FALSE;

    /**
     *
     */
    private Integer borrower;

    /**
     *
     */
    private BigDecimal amount;

    /**
     *
     */
    private BigDecimal fee;

    /**
     *
     */
    private Boolean instead = Boolean.FALSE;

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public Integer getRepayment() {
        return repayment;
    }

    public void setRepayment(Integer repayment) {
        this.repayment = repayment;
    }

    public Boolean getEarly() {
        return early;
    }

    public void setEarly(Boolean early) {
        this.early = early;
    }

    public Integer getBorrower() {
        return borrower;
    }

    public void setBorrower(Integer borrower) {
        this.borrower = borrower;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Boolean getInstead() {
        return instead;
    }

    public void setInstead(Boolean instead) {
        this.instead = instead;
    }
}
