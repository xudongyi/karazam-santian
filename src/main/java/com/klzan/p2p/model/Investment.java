package com.klzan.p2p.model;

import com.klzan.core.util.SnUtils;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * 投资
 */
@Entity
@Table(name = "karazam_investment")
public class Investment extends BaseModel {
    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvestmentState state = InvestmentState.INVESTING;

    /**
     * 金额
     */
    @Min(0)
    @Digits(integer = 16, fraction = 2)
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;

    /**
     * 优惠金额
     */
    @Min(0)
    @Digits(integer = 16, fraction = 2)
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal preferentialAmount = BigDecimal.ZERO;

    /**
     * 借款
     */
    private Integer borrowing;

    /**
     * 投资人
     */
    private Integer investor;

    /**
     * 是否为体验投资
     */
    @Column(nullable = false)
    private Boolean isExperience = false;

    /**
     * 订单号
     */
    @Column(updatable = false, unique = true, length = 100)
    private String orderNo;

    /**
     * 转让记录ID
     */
    @Column(nullable = true)
    private Integer transfer;

    public Investment() {
    }

    public Investment(BigDecimal amount, BigDecimal preferentialAmount, Integer borrowing, Integer investor, Boolean isExperience) {
        this.amount = amount;
        this.preferentialAmount = preferentialAmount;
        this.borrowing = borrowing;
        this.investor = investor;
        this.isExperience = isExperience;
        this.orderNo = SnUtils.getOrderNo(investor,12, 30);
    }

    public void addInvestment(BigDecimal amount, BigDecimal preferentialAmount) {
        this.amount = getAmount().add(amount);
        this.preferentialAmount = getPreferentialAmount().add(preferentialAmount);
    }

    public InvestmentState getState() {
        return state;
    }

    public void setState(InvestmentState state) {
        this.state = state;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(BigDecimal preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public Integer getInvestor() {
        return investor;
    }

    public void setInvestor(Integer investor) {
        this.investor = investor;
    }

    public Boolean getExperience() {
        return isExperience;
    }

    public void setExperience(Boolean experience) {
        isExperience = experience;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    /**
     * 进度状态
     */
    @Transient
    public String getStateDes() {
        return state.getDisplayName();
    }

    /**
     * 添加额度
     *
     * @param amount
     *            额度
     */
    @Transient
    public void addAmount(BigDecimal amount) {
        setAmount(getAmount().add(amount));
    }

    /**
     * 添加优惠额度
     *
     * @param amount
     *            额度
     */
    @Transient
    public void addPreferentialAmount(BigDecimal amount) {
        setPreferentialAmount(getPreferentialAmount().add(amount));
    }

    /**
     * 创建日期
     */
    @Transient
    public String getCreateDateDes(){
        return com.klzan.core.util.DateUtils.format(getCreateDate(), com.klzan.core.util.DateUtils.YYYY_MM_DD_HH_MM_SS);
    }


    ///////////////  瞬时字段  /////////////////
    @Transient
    private String investorLoginName;

    @Transient
    private String investorIdNo; //投资人身份证号码

    @Transient
    private String investorRealName; //投资人姓名

    @Transient
    private Boolean investorCorp; //是否企业

    @Transient
    private String investorCorporationName; //投资企业名

    @Transient
    private String investorCorporationIdCard; //投资企业营业执照号

    @Transient
    private BigDecimal planIncome;

    @Transient
    private String transferLoginName;

    public String getInvestorLoginName() {
        return investorLoginName;
    }

    public void setInvestorLoginName(String investorLoginName) {
        this.investorLoginName = investorLoginName;
    }

    public String getInvestorIdNo() {
        return investorIdNo;
    }

    public void setInvestorIdNo(String investorIdNo) {
        this.investorIdNo = investorIdNo;
    }

    public String getInvestorRealName() {
        return investorRealName;
    }

    public void setInvestorRealName(String investorRealName) {
        this.investorRealName = investorRealName;
    }

    public Boolean getInvestorCorp() {
        return investorCorp;
    }

    public void setInvestorCorp(Boolean investorCorp) {
        this.investorCorp = investorCorp;
    }

    public String getInvestorCorporationName() {
        return investorCorporationName;
    }

    public void setInvestorCorporationName(String investorCorporationName) {
        this.investorCorporationName = investorCorporationName;
    }

    public String getInvestorCorporationIdCard() {
        return investorCorporationIdCard;
    }

    public void setInvestorCorporationIdCard(String investorCorporationIdCard) {
        this.investorCorporationIdCard = investorCorporationIdCard;
    }

    public BigDecimal getPlanIncome() {
        return planIncome;
    }

    public void setPlanIncome(BigDecimal planIncome) {
        this.planIncome = planIncome;
    }

    public String getTransferLoginName() {
        return transferLoginName;
    }

    public void setTransferLoginName(String transferLoginName) {
        this.transferLoginName = transferLoginName;
    }
}
