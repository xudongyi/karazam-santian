package com.klzan.p2p.model;

import com.klzan.core.util.SnUtils;
import com.klzan.p2p.enums.PayState;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.enums.TransferState;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.base.BaseModel;
import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款计划
 */
@Entity
@Table(name = "karazam_repayment_plan")
public class RepaymentPlan extends BaseModel implements Comparable<RepaymentPlan> {

    @Embedded
    private RepaymentRecord repaymentRecord;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepaymentState state = RepaymentState.REPAYING;

    /**
     * 已还本金
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal paidCapital = BigDecimal.ZERO;

    /**
     * 已还利息
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal paidInterest = BigDecimal.ZERO;

    /**
     * 实际还款日期
     */
    private Date paidDate;

    /**
     * 提前还款利息
     */
    @Column(precision = 16, scale = 2)
    private BigDecimal aheadInterest = BigDecimal.ZERO;

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
     * 已还逾期利息
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
     * 已还严重逾期利息
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal paidSeriousOverdueInterest = BigDecimal.ZERO;

    /**
     * 回收服务费
     */
    private BigDecimal recoveryFee = BigDecimal.ZERO;

    /**
     * 已付金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal paidAmount = BigDecimal.ZERO;
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
     * 还款
     */
    @Column(nullable = false)
    private Integer repayment;
    /**
     * 借款人
     */
    @Column(nullable = false)
    private Integer borrower;
    /**
     * 投资人
     */
    @Column(nullable = false)
    private Integer investor;
    /**
     * 是否提前还款
     */
    private Boolean advance = false;
    /**
     * 支付状态
     */
    @Enumerated(EnumType.STRING)
    private PayState payState = PayState.NOTPAY;
    /**
     * 订单号
     */
    @Column(unique = false, length = 100)
    private String orderNo;
    /**
     * 是否担保
     */
    private Boolean guarantee = false;

    /**
     * 转让状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferState transferState = TransferState.GENERAL;

    /**
     * 转让记录ID
     */
    @Column(nullable = true)
    private Integer transfer;

    /**
     * 转入转让服务费
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal transferFeeIn = BigDecimal.ZERO;

    /**
     * 转出转让服务费
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal transferFeeOut = BigDecimal.ZERO;

    /**
     * 每份转让利息
     */
    @Column(nullable = false, precision = 16, scale = 8)
    private BigDecimal transferEveryInterest = BigDecimal.ZERO;

    public RepaymentPlan() {
    }

    public RepaymentPlan(Investment investment, RepaymentRecord repaymentRecord) {
        this.repaymentRecord = repaymentRecord;
        this.borrowing = investment.getBorrowing();
        this.investor = investment.getInvestor();
        this.investment = investment.getId();
        this.orderNo = SnUtils.getOrderNo(getInvestor(), 16, 30);
    }

    public RepaymentRecord getRepaymentRecord() {
        return repaymentRecord;
    }

    public RepaymentState getState() {
        return state;
    }

    public BigDecimal getPaidCapital() {
        return paidCapital;
    }

    public BigDecimal getPaidInterest() {
        return paidInterest;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public Integer getOverduePeriod() {
        return overduePeriod;
    }

    public BigDecimal getOverdueInterest() {
        return overdueInterest;
    }

    public BigDecimal getPaidOverdueInterest() {
        return paidOverdueInterest;
    }

    public Integer getSeriousOverduePeriod() {
        return seriousOverduePeriod;
    }

    public BigDecimal getSeriousOverdueInterest() {
        return seriousOverdueInterest;
    }

    public BigDecimal getPaidSeriousOverdueInterest() {
        return paidSeriousOverdueInterest;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public Integer getRepayment() {
        return repayment;
    }

    public Integer getBorrower() {
        return borrower;
    }

    public Boolean getAdvance() {
        return advance;
    }

    public PayState getPayState() {
        return payState;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public Boolean getGuarantee() {
        return guarantee;
    }

    public Integer getInvestment() {
        return investment;
    }

    public Integer getInvestor() {
        return investor;
    }

    public void setRepaymentRecord(RepaymentRecord repaymentRecord) {
        this.repaymentRecord = repaymentRecord;
    }

    public void setState(RepaymentState state) {
        this.state = state;
    }

    public void setPaidCapital(BigDecimal paidCapital) {
        this.paidCapital = paidCapital;
    }

    public void setPaidInterest(BigDecimal paidInterest) {
        this.paidInterest = paidInterest;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public void setOverduePeriod(Integer overduePeriod) {
        this.overduePeriod = overduePeriod;
    }

    public void setOverdueInterest(BigDecimal overdueInterest) {
        this.overdueInterest = overdueInterest;
    }

    public void setPaidOverdueInterest(BigDecimal paidOverdueInterest) {
        this.paidOverdueInterest = paidOverdueInterest;
    }

    public void setSeriousOverduePeriod(Integer seriousOverduePeriod) {
        this.seriousOverduePeriod = seriousOverduePeriod;
    }

    public void setSeriousOverdueInterest(BigDecimal seriousOverdueInterest) {
        this.seriousOverdueInterest = seriousOverdueInterest;
    }

    public void setPaidSeriousOverdueInterest(BigDecimal paidSeriousOverdueInterest) {
        this.paidSeriousOverdueInterest = paidSeriousOverdueInterest;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public void setInvestment(Integer investment) {
        this.investment = investment;
    }

    public void setRepayment(Integer repayment) {
        this.repayment = repayment;
    }

    public void setBorrower(Integer borrower) {
        this.borrower = borrower;
    }

    public void setInvestor(Integer investor) {
        this.investor = investor;
    }

    public void setAdvance(Boolean advance) {
        this.advance = advance;
    }

    public void setPayState(PayState payState) {
        this.payState = payState;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setGuarantee(Boolean guarantee) {
        this.guarantee = guarantee;
    }

    public BigDecimal getAheadInterest() {
        return aheadInterest;
    }

    public void setAheadInterest(BigDecimal aheadInterest) {
        this.aheadInterest = aheadInterest;
    }

    public BigDecimal getRecoveryFee() {
        return recoveryFee;
    }

    public void setRecoveryFee(BigDecimal recoveryFee) {
        this.recoveryFee = recoveryFee;
    }

    public int compareTo(RepaymentPlan repaymentPlan) {
        return new CompareToBuilder().append(repaymentRecord.getPayDate(), repaymentPlan.getRepaymentRecord().getPayDate()).toComparison();
    }

    public TransferState getTransferState() {
        return transferState;
    }

    public void setTransferState(TransferState transferState) {
        this.transferState = transferState;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    public BigDecimal getTransferFeeIn() {
        return transferFeeIn;
    }

    public void setTransferFeeIn(BigDecimal transferFeeIn) {
        this.transferFeeIn = transferFeeIn;
    }

    public BigDecimal getTransferFeeOut() {
        return transferFeeOut;
    }

    public void setTransferFeeOut(BigDecimal transferFeeOut) {
        this.transferFeeOut = transferFeeOut;
    }

    public BigDecimal getTransferEveryInterest() {
        return transferEveryInterest;
    }

    public void setTransferEveryInterest(BigDecimal transferEveryInterest) {
        this.transferEveryInterest = transferEveryInterest;
    }

    /**
     * 进度状态
     */
    @Transient
    public String getStateDes() {
        return state.getDisplayName();
    }

    /**
     * 本金
     */
    @Transient
    public BigDecimal getRepaymentRecordCapital() {
        return getRepaymentRecord().getCapital();
    }

    /**
     * 利息
     */
    @Transient
    public BigDecimal getRepaymentRecordInterest() {
        return getRepaymentRecord().getInterest();
    }

    /**
     * 逾期利息
     * @return
     */
    @Transient
    public BigDecimal getRecoveryOverdueInterest(){
        return getOverdueInterest().add(getSeriousOverdueInterest());
    }

    /**
     * 期数
     */
    @Transient
    public Integer getRepaymentRecordPeriod() {
        return getRepaymentRecord().getPeriod();
    }

    /**
     * 计划还款日期
     */
    @Transient
    public Date getRepaymentRecordPayDate() {
        return getRepaymentRecord().getPayDate();
    }

    /**
      * 回款金额  正常/逾期：本金+利息+逾期+严重逾期-服务费   提前：本金+提前利息-服务费
      */
    @Transient
    public BigDecimal getRecoveryAmount(){
        if(advance!=null && advance){
            return getRepaymentRecord().getCapital().add(getAheadInterest()).subtract(getRecoveryRecoveryFee());
        }else{
            return getRepaymentRecord().getCapital().add(getRepaymentRecord().getInterest()).add(getOverdueInterest()).add(getSeriousOverdueInterest()).subtract(getRecoveryRecoveryFee());
        }
    }

    /**
     * 已收收益
     * @return
     */
    @Transient
    public BigDecimal getPayedProfits(){
        if(advance!=null && advance){
            return getAheadInterest().subtract(getRecoveryRecoveryFee()).subtract(getTransferFeeIn()).subtract(getTransferFeeOut());
        }else{
            return getRepaymentRecord().getInterest().add(getOverdueInterest()).add(getSeriousOverdueInterest()).subtract(getRecoveryRecoveryFee()).subtract(getTransferFeeIn()).subtract(getTransferFeeOut());
        }
    }

    /**
     * 待收金额 = 本金+利息-服务费
     */
    @Transient
    public BigDecimal getCapitalInterestFee(){
        return getRepaymentRecord().getCapital().add(getRepaymentRecord().getInterest()).subtract(getRecoveryRecoveryFee());
    }

    /**
     * 是否逾期
     */
    @Transient
    public Boolean getIsOverdue(){
        return com.klzan.core.util.DateUtils.getZeroDate(new Date()).after(com.klzan.core.util.DateUtils.getZeroDate(getRepaymentRecord().getPayDate()))
                && getState().equals(RepaymentState.REPAYING);
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
    public BigDecimal getRecoveryRecoveryFee(){
        return getRecoveryFee()==null?BigDecimal.ZERO:getRecoveryFee();
    }

    /**
     * 本金
     */
    @Transient
    public BigDecimal getCapital(){
        return getRepaymentRecord().getCapital();
    }

    /**
     * 利息
     */
    @Transient
    public BigDecimal getInterest(){
        return getRepaymentRecord().getInterest();
    }

    /**
     * 本息
     * @return
     */
    @Transient
    public BigDecimal getCapitalInterest() {
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
            return getCapital().add(getAheadInterest()).subtract(getRecoveryFee());
        }
        return getCapital().add(getInterest()).add(getOverdueInterest()).add(getSeriousOverdueInterest()).subtract(getRecoveryFee());
    }

    /**
     * 计划还款日期
     */
    @Transient
    public String getPayDateDes(){
        return com.klzan.core.util.DateUtils.format(getRepaymentRecord().getPayDate(), com.klzan.core.util.DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 实际还款日期
     */
    @Transient
    public String getPaidDateDes(){
        return com.klzan.core.util.DateUtils.format(getPaidDate(), com.klzan.core.util.DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 创建日期
     */
    @Transient
    public String getCreateDateDes(){
        return com.klzan.core.util.DateUtils.format(getCreateDate(), com.klzan.core.util.DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 是否提前还款
     */
    @Transient
    public String getAdvanceDes(){
        return getAdvance()?"是":"否";
    }


    ///////////////  瞬时字段1  /////////////////
    @Transient
    private String borrowerName;

    @Transient
    private String borrowerMobile;

    @Transient
    private UserType borrowerType;

    @Transient
    private String investorName;

    @Transient
    private String investorMobile;

    @Transient
    private UserType investorType;

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

    public String getInvestorName() {
        return investorName;
    }

    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }

    public String getInvestorMobile() {
        return investorMobile;
    }

    public void setInvestorMobile(String investorMobile) {
        this.investorMobile = investorMobile;
    }

    public UserType getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(UserType borrowerType) {
        this.borrowerType = borrowerType;
    }

    public UserType getInvestorType() {
        return investorType;
    }

    public void setInvestorType(UserType investorType) {
        this.investorType = investorType;
    }

    ///////////////  瞬时字段2  /////////////////
    @Transient
    private BigDecimal totalValue = BigDecimal.ZERO;

    @Transient
    private BigDecimal capitalValue = BigDecimal.ZERO;

    @Transient
    private BigDecimal interestValue = BigDecimal.ZERO;

    @Transient
    private BigDecimal recoveryFeeValue = BigDecimal.ZERO;

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getCapitalValue() {
        return capitalValue;
    }

    public void setCapitalValue(BigDecimal capitalValue) {
        this.capitalValue = capitalValue;
    }

    public BigDecimal getInterestValue() {
        return interestValue;
    }

    public void setInterestValue(BigDecimal interestValue) {
        this.interestValue = interestValue;
    }

    public BigDecimal getRecoveryFeeValue() {
        return recoveryFeeValue;
    }

    public void setRecoveryFeeValue(BigDecimal recoveryFeeValue) {
        this.recoveryFeeValue = recoveryFeeValue;
    }
}