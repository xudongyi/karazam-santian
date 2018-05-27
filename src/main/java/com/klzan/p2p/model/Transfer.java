package com.klzan.p2p.model;

import com.klzan.p2p.enums.BorrowingType;
import com.klzan.p2p.enums.RepaymentMethod;
import com.klzan.p2p.enums.TransferLoanState;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 转让
 */
@Entity
@Table(name = "karazam_transfer")
public class Transfer extends BaseModel {

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferLoanState state;

    /**
     * 借款
     */
    @Column(nullable = false, updatable = false)
    private Integer borrowing;

    /**
     * 转让人
     */
    @Column(nullable = false, updatable = false)
    private Integer transfer;

    /**
     * 转让本金
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal capital = BigDecimal.ZERO;

    /**
     * 总价值
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal worth = BigDecimal.ZERO;

    /**
     * 剩余价值
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal surplusWorth = BigDecimal.ZERO;

    /**
     * 已转让本金
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal transferedCapital = BigDecimal.ZERO;

    /**
     * 转入服务费
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal inFee = BigDecimal.ZERO;

    /**
     * 转出服务费
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal outFee = BigDecimal.ZERO;

    /**
     * 上次转出时间
     */
    @Column
    private Date lastDate;

    /**
     * 全部转出时间
     */
    @Column
    private Date fullDate;

    /**
     * 过期时间
     */
    @Column(nullable = false)
    private Date expireDate;

    /**
     * 订单号
     */
    @Column(unique = false, length = 100)
    private String orderNo;

    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BorrowingType type;

    /**
     * 标名
     */
    @Column
    private String title;

    /**
     * 剩余期限
     */
    private String surplusPeriod;

    /**
     * 原标年化
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal interestRate = BigDecimal.ZERO;

    /**
     * 下个还款日
     */
    @Transient
    private Date nextRepaymentDate;
    /**
     * 还款方式
     */
    @Transient
    private RepaymentMethod repaymentMethod;

    @Transient
    private Integer residualPeriod;

    @Transient
    private String residualUnit;

    public TransferLoanState getState() {
        return state;
    }

    public void setState(TransferLoanState state) {
        this.state = state;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getTransferedCapital() {
        return transferedCapital;
    }

    public void setTransferedCapital(BigDecimal transferedCapital) {
        this.transferedCapital = transferedCapital;
    }

    public BigDecimal getInFee() {
        return inFee;
    }

    public void setInFee(BigDecimal inFee) {
        this.inFee = inFee;
    }

    public BigDecimal getOutFee() {
        return outFee;
    }

    public void setOutFee(BigDecimal outFee) {
        this.outFee = outFee;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Date getFullDate() {
        return fullDate;
    }

    public void setFullDate(Date fullDate) {
        this.fullDate = fullDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public BorrowingType getType() {
        return type;
    }

    public void setType(BorrowingType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSurplusPeriod() {
        return surplusPeriod;
    }

    public void setSurplusPeriod(String surplusPeriod) {
        this.surplusPeriod = surplusPeriod;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getWorth() {
        return worth;
    }

    public void setWorth(BigDecimal worth) {
        this.worth = worth;
    }

    public BigDecimal getSurplusWorth() {
        return surplusWorth;
    }

    public void setSurplusWorth(BigDecimal surplusWorth) {
        this.surplusWorth = surplusWorth;
    }

    /**
     * 借款类型
     */
    @Transient
    public String getTypeDes() {
        return type.getAlias();
    }

    /**
     * 剩余金额
     */
    @Transient
    public BigDecimal getSurplusCapital() {
        if(getTransferedCapital()==null){
            return getCapital();
        }
        return getCapital().subtract(getTransferedCapital());
    }

    /**
     * 是否投满
     */
    @Transient
    public Boolean getIsFull() {
        if(getState().equals(TransferLoanState.TRANSFERED)){
            return true;
        }
        if(getTransferedCapital()!=null && getCapital().compareTo(getTransferedCapital())==0){
            return true;
        }
        return false;
    }

    /**
     * 最大份数
     */
    @Transient
    public Integer getMaxParts() {
        return getCapital().intValue()/100;
    }

    /**
     * 剩余份数
     */
    @Transient
    public Integer getSurplusParts() {
        if(getTransferedCapital()==null){
            return getCapital().intValue()/100;
        }
        return getCapital().subtract(getTransferedCapital()).intValue()/100;
    }

    @Transient
    public String getStateDes() {
        return getState().getDisplayName();
    }

    @Transient
    public BigDecimal getTransferProgress() {
        return getTransferedCapital().divide(getCapital(), 2, BigDecimal.ROUND_HALF_EVEN);
    }

    public Date getNextRepaymentDate() {
        return nextRepaymentDate;
    }

    public void setNextRepaymentDate(Date nextRepaymentDate) {
        this.nextRepaymentDate = nextRepaymentDate;
    }

    public RepaymentMethod getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(RepaymentMethod repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    @Transient
    public String getRepaymentMethodDes() {
        if (null == repaymentMethod) {
            return "";
        }
        return repaymentMethod.getDisplayName();
    }

    @Transient
    public Boolean isCanTransfer() {
        if(getState().equals(TransferLoanState.CANCEL) || getState().equals(TransferLoanState.TRANSFERED)){
            return false;
        }
        return true;
    }
//    /**
//     * 每份金额
//     */
//    @Transient
//    public BigDecimal getWorthOfPart() {
//        return getWorth().divide(new BigDecimal(getMaxParts()), 6, BigDecimal.ROUND_DOWN);
//    }

    public Integer getResidualPeriod() {
        return residualPeriod;
    }

    public void setResidualPeriod(Integer residualPeriod) {
        this.residualPeriod = residualPeriod;
    }

    public String getResidualUnit() {
        return residualUnit;
    }

    public void setResidualUnit(String residualUnit) {
        this.residualUnit = residualUnit;
    }
}