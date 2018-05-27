package com.klzan.p2p.vo.repayment;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.PayState;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.enums.TransferState;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.RepaymentPlan;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by suhao Date: 2017/4/24 Time: 9:30
 *
 * @version: 1.0
 */
public class RepaymentPlanVo extends BaseVo {
    /**
     * 还款日期
     */
    private Date payDate;

    /**
     * 下期还款日期
     */
    private Date nextPayDate;

    /**
     * 本金
     */
    private BigDecimal capital;

    /**
     * 利息
     */
    private BigDecimal interest;

    /**
     * 当前期数
     */
    private Integer period;

    /**
     * 状态
     */
    private RepaymentState state = RepaymentState.REPAYING;

    /**
     * 已还本金
     */
    private BigDecimal paidCapital = BigDecimal.ZERO;

    /**
     * 已还利息
     */
    private BigDecimal paidInterest = BigDecimal.ZERO;

    /**
     * 实际还款日期
     */
    private Date paidDate;

    /**
     * 提前还款利息
     */
    private BigDecimal aheadInterest = BigDecimal.ZERO;

    /**
     * 逾期期限
     */
    private Integer overduePeriod = 0;
    /**
     * 逾期利息
     */
    private BigDecimal overdueInterest = BigDecimal.ZERO;
    /**
     * 已还逾期利息
     */
    private BigDecimal paidOverdueInterest = BigDecimal.ZERO;
    /**
     * 严重逾期期限
     */
    private Integer seriousOverduePeriod = 0;
    /**
     * 严重逾期利息
     */
    private BigDecimal seriousOverdueInterest = BigDecimal.ZERO;
    /**
     * 已还严重逾期利息
     */
    private BigDecimal paidSeriousOverdueInterest = BigDecimal.ZERO;

    /**
     * 回收服务费
     */
    private BigDecimal recoveryFee = BigDecimal.ZERO;

    /**
     * 已付金额
     */
    private BigDecimal paidAmount = BigDecimal.ZERO;
    /**
     * 借款
     */
    private Integer borrowing;
    /**
     * 投资
     */
    private Integer investment;
    /**
     * 还款
     */
    private Integer repayment;
    /**
     * 借款人
     */
    private Integer borrower;
    /**
     * 投资人
     */
    private Integer investor;
    /**
     * 是否提前还款
     */
    private Boolean advance = false;
    /**
     * 支付状态
     */
    private PayState payState = PayState.NOTPAY;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 是否担保
     */
    private Boolean guarantee = false;

    /**
     * 转让状态
     */
    private TransferState transferState = TransferState.GENERAL;

    /**
     * 转让记录ID
     */
    private Integer transfer;

    /**
     * 转入转让服务费
     */
    private BigDecimal transferFeeIn = BigDecimal.ZERO;

    /**
     * 转出转让服务费
     */
    private BigDecimal transferFeeOut = BigDecimal.ZERO;

    /**
     * 每份转让利息
     */
    private BigDecimal transferEveryInterest = BigDecimal.ZERO;

    private String projectName;

    private String projectPeriod;

    private String borrowerName;

    private String borrowerMobile;

    private UserType borrowerType;

    private String investorName;

    private String investorMobile;

    private UserType investorType;

    ///////////////  瞬时字段2  /////////////////
    private BigDecimal totalValue = BigDecimal.ZERO;

    private BigDecimal capitalValue = BigDecimal.ZERO;

    private BigDecimal interestValue = BigDecimal.ZERO;

    private BigDecimal recoveryFeeValue = BigDecimal.ZERO;

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

    public boolean isCurrentPeriod(Integer period) {
        return this.period.equals(period);
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
        return new CompareToBuilder().append(getPayDate(), repaymentPlan.getRepaymentRecord().getPayDate()).toComparison();
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
    public String getStateDes() {
        return state.getDisplayName();
    }

    /**
     * 逾期利息
     * @return
     */
    public BigDecimal getRecoveryOverdueInterest(){
        return getOverdueInterest().add(getSeriousOverdueInterest());
    }

    /**
     * 回款金额  正常/逾期：本金+利息+逾期+严重逾期-服务费   提前：本金+提前利息-服务费
     */
    public BigDecimal getRecoveryAmount(){
        if(advance!=null && advance){
            return getCapital().add(getAheadInterest()).subtract(getRecoveryRecoveryFee());
        }else{
            return getCapital().add(getInterest()).add(getOverdueInterest()).add(getSeriousOverdueInterest()).subtract(getRecoveryRecoveryFee());
        }
    }

    /**
     * 待收金额 = 本金+利息-服务费
     */
    public BigDecimal getCapitalInterestFee(){
        return getCapital().add(getInterest()).subtract(getRecoveryRecoveryFee());
    }

    /**
     * 是否逾期
     */
    public Boolean getIsOverdue(){
        return com.klzan.core.util.DateUtils.getZeroDate(new Date()).after(com.klzan.core.util.DateUtils.getZeroDate(getPayDate()))
                && getState().equals(RepaymentState.REPAYING);
    }

    /**
     * 逾期总利息
     * @return
     */
    public BigDecimal getOverdueInterestTotal(){
        return getOverdueInterest().add(getSeriousOverdueInterest());
    }

    /**
     * 服务费
     * @return
     */
    public BigDecimal getRecoveryRecoveryFee(){
        return getRecoveryFee()==null?BigDecimal.ZERO:getRecoveryFee();
    }

    /**
     * 本息
     * @return
     */
    public BigDecimal getCapitalInterest() {
        return getCapital().add(getInterest());
    }

    /**
     * 本息逾期提前
     * @return
     */
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
    public BigDecimal getCapitalInterestOverdueAheadFee(){
        if(getAdvance()){
            return getCapital().add(getAheadInterest()).subtract(getRecoveryFee());
        }
        return getCapital().add(getInterest()).add(getOverdueInterest()).add(getSeriousOverdueInterest()).subtract(getRecoveryFee());
    }

    /**
     * 计划还款日期
     */
    public String getPayDateDes(){
        return com.klzan.core.util.DateUtils.format(getPayDate(), com.klzan.core.util.DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 实际还款日期
     */
    public String getPaidDateDes(){
        return com.klzan.core.util.DateUtils.format(getPaidDate(), com.klzan.core.util.DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 创建日期
     */
    public String getCreateDateDes(){
        return com.klzan.core.util.DateUtils.format(getCreateDate(), com.klzan.core.util.DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    public String getAdvanceDes(){
        return getAdvance()?"是":"否";
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPeriod() {
        return projectPeriod;
    }

    public void setProjectPeriod(String projectPeriod) {
        this.projectPeriod = projectPeriod;
    }

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
