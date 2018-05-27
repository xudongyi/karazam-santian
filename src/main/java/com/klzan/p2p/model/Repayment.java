package com.klzan.p2p.model;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "karazam_repayment")
public class Repayment extends BaseModel {
    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepaymentState state = RepaymentState.REPAYING;
    /**
     * 本金
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal capital = BigDecimal.ZERO;
    /**
     * 期数
     */
    @Column(nullable = false)
    private Integer period;
    /**
     * 利息
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal interest = BigDecimal.ZERO;
    /**
     * 还款日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date payDate;

    /**
     * 下期还款日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextPayDate;

    /**
     * 实际还款日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date paidDate;
    /**
     * 提前还款利息
     */
    @Column(precision = 16, scale = 2)
    private BigDecimal aheadInterest = BigDecimal.ZERO;
//    /**
//     * 提前还款费
//     */
//    @Column(nullable = false, precision = 16, scale = 2)
//    private BigDecimal prepayFee = BigDecimal.ZERO;
    /**
     * 逾期期限
     */
    @Column(nullable = false)
    private Integer overduePeriod = 0;
    /**
     * 逾期利息
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal overdueInterest = BigDecimal.ZERO;
    /**
     * 已付逾期利息
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal paidOverdueInterest = BigDecimal.ZERO;
    /**
     * 严重逾期期限
     */
    @Column(nullable = false)
    private Integer seriousOverduePeriod = 0;
    /**
     * 严重逾期利息
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal seriousOverdueInterest = BigDecimal.ZERO;
    /**
     * 已付严重逾期利息
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal paidSeriousOverdueInterest = BigDecimal.ZERO;
//    /**
//     * 服务费
//     */
//    @Column(nullable = false, precision = 16, scale = 2)
//    private BigDecimal fee = BigDecimal.ZERO;
//
//    /**
//     * 已付服务费
//     */
//    @Column(nullable = false, precision = 16, scale = 2)
//    private BigDecimal paidFee = BigDecimal.ZERO;

    /**
     * 还款服务费
     */
    private BigDecimal repaymentFee = BigDecimal.ZERO;

    /**
     * 是否提前还款
     */
    private Boolean advance = false;

    /**
     * 已付金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal paidAmount = BigDecimal.ZERO;
    /**
     * 借款
     */
    @Column(nullable = false, updatable = false)
    private Integer borrowing;
    /**
     * 借款人
     */
    @Column(nullable = false, updatable = false)
    private Integer borrower;
    /**
     * 订单号
     */
    @Column(unique = false, length = 100)
    private String orderNo;

    /**
     * 是否结算
     */
    private Boolean settlement = Boolean.FALSE;

    public Integer getOverdueTime(){
        return new Double(DateUtils.getDaysOfTwoDate(new Date(),this.payDate)).intValue();
    }
    public RepaymentState getState() {
        return state;
    }

    public void setState(RepaymentState state) {
        this.state = state;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getNextPayDate() {
        return nextPayDate;
    }

    public void setNextPayDate(Date nextPayDate) {
        this.nextPayDate = nextPayDate;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

//    public BigDecimal getPrepayFee() {
//        return prepayFee;
//    }
//
//    public void setPrepayFee(BigDecimal prepayFee) {
//        this.prepayFee = prepayFee;
//    }

    public Integer getOverduePeriod() {
        return overduePeriod;
    }

    public void setOverduePeriod(Integer overduePeriod) {
        this.overduePeriod = overduePeriod;
    }

    public BigDecimal getOverdueInterest() {
        return overdueInterest;
    }

    public void setOverdueInterest(BigDecimal overdueInterest) {
        this.overdueInterest = overdueInterest;
    }

    public BigDecimal getPaidOverdueInterest() {
        return paidOverdueInterest;
    }

    public void setPaidOverdueInterest(BigDecimal paidOverdueInterest) {
        this.paidOverdueInterest = paidOverdueInterest;
    }

    public Integer getSeriousOverduePeriod() {
        return seriousOverduePeriod;
    }

    public void setSeriousOverduePeriod(Integer seriousOverduePeriod) {
        this.seriousOverduePeriod = seriousOverduePeriod;
    }

    public BigDecimal getSeriousOverdueInterest() {
        return seriousOverdueInterest;
    }

    public void setSeriousOverdueInterest(BigDecimal seriousOverdueInterest) {
        this.seriousOverdueInterest = seriousOverdueInterest;
    }

    public BigDecimal getPaidSeriousOverdueInterest() {
        return paidSeriousOverdueInterest;
    }

    public void setPaidSeriousOverdueInterest(BigDecimal paidSeriousOverdueInterest) {
        this.paidSeriousOverdueInterest = paidSeriousOverdueInterest;
    }
//
//    public BigDecimal getFee() {
//        return fee;
//    }
//
//    public void setFee(BigDecimal fee) {
//        this.fee = fee;
//    }
//
//    public BigDecimal getPaidFee() {
//        return paidFee;
//    }
//
//    public void setPaidFee(BigDecimal paidFee) {
//        this.paidFee = paidFee;
//    }


    public BigDecimal getRepaymentFee() {
        return repaymentFee;
    }

    public void setRepaymentFee(BigDecimal repaymentFee) {
        this.repaymentFee = repaymentFee;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public Integer getBorrower() {
        return borrower;
    }

    public void setBorrower(Integer borrower) {
        this.borrower = borrower;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAheadInterest() {
        return aheadInterest;
    }

    public void setAheadInterest(BigDecimal aheadInterest) {
        this.aheadInterest = aheadInterest;
    }

    public Boolean getAdvance() {
        return advance;
    }

    public void setAdvance(Boolean advance) {
        this.advance = advance;
    }

    public Boolean getSettlement() {
        return settlement;
    }

    public void setSettlement(Boolean settlement) {
        this.settlement = settlement;
    }

    ///////////////  瞬时方法  /////////////////
    @Transient
    public String getStateDes() {
        return state.getDisplayName();
    }

    /**
     * 还款金额  正常/逾期：本金+利息+逾期+严重逾期-服务费   提前：本金+提前利息+服务费
     * @return
     */
    @Transient
    public BigDecimal getRepaymentAmount(){
        if(advance!=null && advance){
            return getCapital().add(getAheadInterest()).add(repaymentFee());
        }else {
            return getCapital().add(getInterest()).add(getOverdueInterest()).add(getSeriousOverdueInterest()).add(repaymentFee());
        }
    }

    /**
     * 待还金额 = 本金+利息+服务费
     */
    @Transient
    public BigDecimal getCapitalInterestFee(){
        return getCapital().add(getInterest()).add(repaymentFee());
    }

    /**
     * 本息
     * @return
     */
    @Transient
    public BigDecimal getCapitalInterest(){
        return getCapital().add(getInterest());
    }

    /**
     * 本息逾期提前
     * @return
     */
    @Transient
    public BigDecimal getCapitalInterestOverdueAhead(){
        if(getAdvance()){
            return getCapital().add(getAheadInterest());
        }
        return getCapital().add(getInterest()).add(getOverdueInterest()).add(getSeriousOverdueInterest());
    }

    /**
     * 本息逾期提前服务费
     * @return
     */
    @Transient
    public BigDecimal getCapitalInterestOverdueAheadFee(){
        if(getAdvance()){
            return getCapital().add(getAheadInterest()).add(getRepaymentFee());
        }
        return getCapital().add(getInterest()).add(getOverdueInterest()).add(getSeriousOverdueInterest()).add(getRepaymentFee());
    }

    /**
     * 逾期利息
     * @return
     */
    @Transient
    public BigDecimal repaymentOverdueInterest(){
        return getOverdueInterest().add(getSeriousOverdueInterest());
    }

    /**
     * 逾期总利息
     * @return
     */
    @Transient
    public BigDecimal getOverdueInterestTotal(){
        return getOverdueInterest().add(getSeriousOverdueInterest());
    }


    /**
     * 服务费
     * @return
     */
    @Transient
    public BigDecimal repaymentFee(){
        return getRepaymentFee()==null?BigDecimal.ZERO:getRepaymentFee();
    }

    /**
     * 是否逾期
     */
    @Transient
    public Boolean getIsOverdue(){
        return com.klzan.core.util.DateUtils.getZeroDate(new Date()).after(com.klzan.core.util.DateUtils.getZeroDate(getPayDate()))
                && getState().equals(RepaymentState.REPAYING);
    }

    /**
     * 计划还款日期
     */
    @Transient
    public String getPayDateDes(){
        return DateUtils.format(getPayDate(), DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 实际还款日期
     */
    @Transient
    public String getPaidDateDes(){
        return DateUtils.format(getPaidDate(), DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 创建日期
     */
    @Transient
    public String getCreateDateDes(){
        return DateUtils.format(getCreateDate(), DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 是否提前还款
     */
    @Transient
    public String getAdvanceDes(){
        return getAdvance()?"是":"否";
    }



    ///////////////  瞬时字段  /////////////////
    @Transient
    private String borrowerName;

    @Transient
    private String borrowerMobile;

    @Transient
    private UserType borrowerType;

    @Transient
    private String borrowingTitle;

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBorrowerMobile() {
        return borrowerMobile;
    }

    public void setBorrowerMobile(String borrowerMobile) {
        this.borrowerMobile = borrowerMobile;
    }

    public UserType getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(UserType borrowerType) {
        this.borrowerType = borrowerType;
    }

    public String getBorrowingTitle() {
        return borrowingTitle;
    }

    public void setBorrowingTitle(String borrowingTitle) {
        this.borrowingTitle = borrowingTitle;
    }
}