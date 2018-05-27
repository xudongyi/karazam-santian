package com.klzan.p2p.model;

import com.klzan.p2p.enums.ReferralFeeState;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 推荐费
 */
@Entity
@Table(name = "karazam_referral_fee")
public class ReferralFee extends BaseModel {
    /**
     * 推荐关系ID
     */
    @Column(nullable = false)
    private Integer referralId;
    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReferralFeeState state = ReferralFeeState.WAIT_APPLY;
    /**
     * 推荐金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal referralAmt;
    /**
     * 推荐费率
     */
    @Column(nullable = false, precision = 5, scale = 3)
    private BigDecimal referralFeeRate;
    /**
     * 推荐费
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal referralFee = BigDecimal.ZERO;

    /**
     * 计划结算日期
     */
    @Column(nullable = false)
    @Temporal(value=TemporalType.DATE)
    private Date planPaymentDate;
    /**
     * 实际结算日期
     */
    @Temporal(value=TemporalType.DATE)
    private Date paymentDate;
    /**
     * 备注
     */
    private String memo;
    /**
     * 操作员
     */
    private String operator;
    /**
     * IP
     */
    private String ip;
    /**
     * 借款
     */
    @Column(nullable = false)
    private Integer borrowing;
    /**
     * 投资
     */
    @Column(nullable = false)
    private Integer investment;

    /**
     * 订单号
     */
    @Column(unique = true, length = 100)
    private String orderNo;

    public Integer getReferralId() {
        return referralId;
    }

    public ReferralFeeState getState() {
        return state;
    }

    public BigDecimal getReferralAmt() {
        return referralAmt;
    }

    public BigDecimal getReferralFeeRate() {
        return referralFeeRate;
    }

    public BigDecimal getReferralFee() {
        return referralFee;
    }

    public Date getPlanPaymentDate() {
        return planPaymentDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getMemo() {
        return memo;
    }

    public String getOperator() {
        return operator;
    }

    public String getIp() {
        return ip;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public Integer getInvestment() {
        return investment;
    }

    public void setState(ReferralFeeState state) {
        this.state = state;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setReferralId(Integer referralId) {
        this.referralId = referralId;
    }

    public void setReferralAmt(BigDecimal referralAmt) {
        this.referralAmt = referralAmt;
    }

    public void setReferralFeeRate(BigDecimal referralFeeRate) {
        this.referralFeeRate = referralFeeRate;
    }

    public void setReferralFee(BigDecimal referralFee) {
        this.referralFee = referralFee;
    }

    public void setPlanPaymentDate(Date planPaymentDate) {
        this.planPaymentDate = planPaymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public void setInvestment(Integer investment) {
        this.investment = investment;
    }

    public String getOrderNo() {
        return orderNo;
    }

}