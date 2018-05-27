package com.klzan.p2p.model;

import com.klzan.core.util.SnUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * 投资记录
 */
@Entity
@Table(name = "karazam_investment_record")
public class InvestmentRecord extends BaseModel {
    /**
     * 操作方式
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationMethod operationMethod = OperationMethod.MANUAL;
    /**
     * 投资状态
     */
    @Enumerated(EnumType.STRING)
    private InvestmentState state = InvestmentState.INVESTING;

    /**
     * 支付方式
     */
    @Enumerated(EnumType.STRING)
    private PaymentOrderMethod method;

    /**
     * 金额
     */
    @Min(0)
    @Digits(integer = 16, fraction = 2)
    @Column(nullable = false, updatable = false, precision = 16, scale = 2)
    private BigDecimal amount;
    /**
     * 优惠金额
     */
    @Min(0)
    @Digits(integer = 16, fraction = 2)
    @Column(nullable = false, updatable = false, precision = 16, scale = 2)
    private BigDecimal preferentialAmount;
    /**
     * 借款
     */
    @Column(nullable = false)
    private Integer borrowing;
    /**
     * 投资
     */
    private Integer investment;
    /**
     * 优惠码
     */
    private Integer couponCode;
    /**
     * 投资人
     */
    @Column(nullable = false)
    private Integer investor;
    /**
     * 备注
     */
    @Length(max = 200)
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
     * 是否转让
     */
    private Boolean isTransfer = false;
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
     * 批次号(仅自动投标使用)
     */
    @Column(length = 100)
    private String batchNo;

    /**
     * 投资来源
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceType deviceType = DeviceType.PC;

    /**
     * 转让记录ID
     */
    @Column(nullable = true)
    private Integer transferId;

    public InvestmentRecord() {
    }

    public InvestmentRecord(OperationMethod operationMethod, BigDecimal amount, BigDecimal preferentialAmount, Integer borrowing, Integer investment, Integer couponCode, Integer investor, String memo, String operator, String ip, Boolean isTransfer, Boolean isExperience) {
        this.operationMethod = operationMethod;
        this.amount = amount;
        this.preferentialAmount = preferentialAmount;
        this.borrowing = borrowing;
        this.investment = investment;
        this.couponCode = couponCode;
        this.investor = investor;
        this.memo = memo;
        this.operator = operator;
        this.ip = ip;
        this.isTransfer = isTransfer;
        this.isExperience = isExperience;
        this.orderNo = SnUtils.getOrderNo(investor, 13, 30);
    }

    /**
     * 操作方式
     */
    @Transient
    public String getOperationMethodDes() {
        return operationMethod.getDisplayName();
    }

    /**
     * 状态
     */
    @Transient
    public String getStateDes() {
        return state.getDisplayName();
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public OperationMethod getOperationMethod() {
        return operationMethod;
    }

    public InvestmentState getState() {
        return state;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPreferentialAmount() {
        return preferentialAmount;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public Integer getInvestment() {
        return investment;
    }

    public Integer getCouponCode() {
        return couponCode;
    }

    public Integer getInvestor() {
        return investor;
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

    public Boolean getTransfer() {
        return isTransfer;
    }

    public Boolean getExperience() {
        return isExperience;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public void setOperationMethod(OperationMethod operationMethod) {
        this.operationMethod = operationMethod;
    }

    public void setState(InvestmentState state) {
        this.state = state;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPreferentialAmount(BigDecimal preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public void setInvestment(Integer investment) {
        this.investment = investment;
    }

    public void setCouponCode(Integer couponCode) {
        this.couponCode = couponCode;
    }

    public void setInvestor(Integer investor) {
        this.investor = investor;
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

    public void setTransfer(Boolean transfer) {
        isTransfer = transfer;
    }

    public void setExperience(Boolean experience) {
        isExperience = experience;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public PaymentOrderMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentOrderMethod method) {
        this.method = method;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
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
    private String investorAvatar;

    @Transient
    private String investorIdNo;

    @Transient
    private String investorRealName;

    @Transient
    private UserType investorType;

    @Transient
    private String investorCorporationIdCard;

    @Transient
    private BigDecimal planIncome;

    public String getInvestorLoginName() {
        return investorLoginName;
    }

    public void setInvestorLoginName(String investorLoginName) {
        this.investorLoginName = investorLoginName;
    }

    public String getInvestorAvatar() {
        return investorAvatar;
    }

    public void setInvestorAvatar(String investorAvatar) {
        this.investorAvatar = investorAvatar;
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

    public UserType getInvestorType() {
        return investorType;
    }

    public void setInvestorType(UserType investorType) {
        this.investorType = investorType;
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
}