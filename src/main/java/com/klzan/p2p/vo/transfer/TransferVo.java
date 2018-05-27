package com.klzan.p2p.vo.transfer;

import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.BorrowingType;
import com.klzan.p2p.enums.RepaymentMethod;
import com.klzan.p2p.enums.TransferLoanState;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

public class TransferVo extends BaseVo {

    private String state;
    /**
     * 转让状态
     */
    private TransferLoanState enumState;
    private String enumStateStr;

    /**
     * 借款id
     */
    private Integer borrowingId;

    /**
     * 借款标题
     */
    private String title;

    /**
     * 剩余期数
     */
    private String surplusPeriod;

    /**
     * 下个还款日
     */
    private Date nextRepaymentDate;

    /**
     * 年利率
     */
    private BigDecimal interestRate;

    /**
     * 待收本息
     */
    private BigDecimal capitalInterest;

    /**
     * 待收利息
     */
    private BigDecimal interest;

    /**
     * 债权价值
     */
    private BigDecimal currentClaimTotalValue;

    /**
     * 是否可转让
     */
    private Boolean canTransfer;

    /**
     * 是否可转让
     */
    private Boolean transfering;

    /**
     * 已回收金额
     */
    private BigDecimal recoveriedAmount;

    /**
     * 剩余本金
     */
    private BigDecimal residualCapital;
    /**
     * 转让本金
     */
    private BigDecimal capital;
    /**
     * 总价值
     */
    private BigDecimal worth;

    /**
     * 剩余价值
     */
    private BigDecimal surplusWorth;
    /**
     * 已转让本金
     */
    private BigDecimal transferedCapital;
    /**
     * 转入服务费
     */
    private BigDecimal inFee;
    /**
     * 转出服务费
     */
    private BigDecimal outFee;
    /**
     * 上次转出时间
     */
    private Date lastDate;
    /**
     * 全部转出时间
     */
    private Date fullDate;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 类型
     */
    private BorrowingType type;
    private String typeStr;

    /**
     * 还款方式
     */
    private RepaymentMethod repaymentMethod;
    private String repaymentMethodStr;
    /*********************   转*让*人*用*户*新*息  *********************************************/
    private Integer transfer;
    private Integer transferUserId;
    private String transferMobile;
    private String transferRealName;
    private Integer investId;

    public Integer getInvestId() {
        return investId;
    }

    public void setInvestId(Integer investId) {
        this.investId = investId;
    }

    /**
     * 投资转让时间
     */
    private Date investmentDate;

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    public BigDecimal getSurplusWorth() {
        return surplusWorth;
    }

    public void setSurplusWorth(BigDecimal surplusWorth) {
        this.surplusWorth = surplusWorth;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BorrowingType getType() {
        if (StringUtils.isNotBlank(typeStr)){
            return BorrowingType.valueOf(typeStr);
        }
        return type;
    }
    public void setType(BorrowingType type) {
        this.type = type;
    }

    public String getTypeStr() {
        if (StringUtils.isNotBlank(typeStr)){
            return BorrowingType.valueOf(typeStr).getDisplayName();
        }
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public RepaymentMethod getRepaymentMethod() {
        if (StringUtils.isNotBlank(repaymentMethodStr)){
            return RepaymentMethod.valueOf(repaymentMethodStr);
        }
        return repaymentMethod;
    }

    public void setRepaymentMethod(RepaymentMethod repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public String getRepaymentMethodStr() {
        if (StringUtils.isNotBlank(repaymentMethodStr)){
            return RepaymentMethod.valueOf(repaymentMethodStr).getDisplayName();
        }
        return repaymentMethodStr;
    }

    public void setRepaymentMethodStr(String repaymentMethodStr) {
        this.repaymentMethodStr = repaymentMethodStr;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getWorth() {
        return worth;
    }

    public void setWorth(BigDecimal worth) {
        this.worth = worth;
    }

    public Integer getTransferUserId() {
        return transferUserId;
    }

    public void setTransferUserId(Integer transferUserId) {
        this.transferUserId = transferUserId;
    }

    public String getTransferMobile() {
        return transferMobile;
    }

    public void setTransferMobile(String transferMobile) {
        this.transferMobile = transferMobile;
    }

    public String getTransferRealName() {
        return transferRealName;
    }

    public void setTransferRealName(String transferRealName) {
        this.transferRealName = transferRealName;
    }

    public Integer getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(Integer borrowingId) {
        this.borrowingId = borrowingId;
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

    public Date getNextRepaymentDate() {
        return nextRepaymentDate;
    }

    public void setNextRepaymentDate(Date nextRepaymentDate) {
        this.nextRepaymentDate = nextRepaymentDate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getCapitalInterest() {
        return capitalInterest;
    }

    public void setCapitalInterest(BigDecimal capitalInterest) {
        this.capitalInterest = capitalInterest;
    }

    public BigDecimal getCurrentClaimTotalValue() {
        return currentClaimTotalValue;
    }

    public void setCurrentClaimTotalValue(BigDecimal currentClaimTotalValue) {
        this.currentClaimTotalValue = currentClaimTotalValue;
    }

    public Boolean getCanTransfer() {
        return canTransfer;
    }

    public void setCanTransfer(Boolean canTransfer) {
        this.canTransfer = canTransfer;
    }

    public Boolean getTransfering() {
        return transfering;
    }

    public void setTransfering(Boolean transfering) {
        this.transfering = transfering;
    }

    public TransferLoanState getEnumState() {
        if (StringUtils.isNotBlank(enumStateStr)){
            return TransferLoanState.valueOf(enumStateStr);
        }
        return enumState;
    }

    public void setEnumState(TransferLoanState enumState) {
        this.enumState = enumState;
    }

    public String getEnumStateStr() {
        if (StringUtils.isNotBlank(enumStateStr)){
            return TransferLoanState.valueOf(enumStateStr).getDisplayName();
        }
        return enumStateStr;
    }

    public void setEnumStateStr(String enumStateStr) {
        this.enumStateStr = enumStateStr;
    }

    public BigDecimal getRecoveriedAmount() {
        return recoveriedAmount;
    }

    public void setRecoveriedAmount(BigDecimal recoveriedAmount) {
        this.recoveriedAmount = recoveriedAmount;
    }

    public BigDecimal getResidualCapital() {
        return residualCapital;
    }

    public void setResidualCapital(BigDecimal residualCapital) {
        this.residualCapital = residualCapital;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(Date investmentDate) {
        this.investmentDate = investmentDate;
    }

    /**
     * 下次还款日
     * @return
     */
    @Transient
    public String getNextRepaymentDateDes() {
        if(getNextRepaymentDate() == null){
            return "-";
        }
        return DateUtils.format(getNextRepaymentDate(), DateUtils.YYYY_MM_DD);
    }


}
