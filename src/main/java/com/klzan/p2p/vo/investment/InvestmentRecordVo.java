package com.klzan.p2p.vo.investment;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.*;

import java.math.BigDecimal;

public class InvestmentRecordVo extends BaseVo {
    /**
     * 操作方式
     */
    private OperationMethod operationMethod;
    private String operationMethodStr;
    /**
     * 投资状态
     */
    private InvestmentState state;
    private String stateStr;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 优惠金额
     */
    private BigDecimal preferentialAmount;
    /**
     * 借款
     */
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
    private Integer investor;
    /**
     * 投资人姓名
     */
    private String realName;
    /**
     * 投资人电话号码
     */
    private String mobile;
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
     * 是否转让
     */
    private Boolean isTransfer;
    /**
     * 是否为体验投资
     */
    private Boolean isExperience;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 借款标题
     */
    private String title;
    /**
     * 借款总金额
     */
    private BigDecimal borrowingAmount;
    private BigDecimal borrowingInterestRate;
    private Integer borrowingPeriod;
    private PeriodUnit borrowingPeriodUnit;
    private RepaymentMethod borrowingRepaymentMethod;
    /**
     * 借款状态
     */
    private BorrowingState borrowingState;
    private String borrowingStateStr;
    private DeviceType deviceType;
    private String deviceTypeStr;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public OperationMethod getOperationMethod() {
        return OperationMethod.valueOf(operationMethodStr);
    }

    public void setOperationMethod(OperationMethod operationMethod) {
        this.operationMethod = operationMethod;
    }

    public InvestmentState getState() {
        if (StringUtils.isBlank(stateStr)) {
            return null;
        }
        return InvestmentState.valueOf(stateStr);
    }

    public String getStateStr() {
        if (StringUtils.isBlank(stateStr)) {
            return null;
        }
        return InvestmentState.valueOf(stateStr).getDisplayName();
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
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

    public Integer getInvestment() {
        return investment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getBorrowingAmount() {
        return borrowingAmount;
    }

    public void setBorrowingAmount(BigDecimal borrowingAmount) {
        this.borrowingAmount = borrowingAmount;
    }

    public BigDecimal getBorrowingInterestRate() {
        return borrowingInterestRate;
    }

    public void setBorrowingInterestRate(BigDecimal borrowingInterestRate) {
        this.borrowingInterestRate = borrowingInterestRate;
    }

    public Integer getBorrowingPeriod() {
        return borrowingPeriod;
    }

    public void setBorrowingPeriod(Integer borrowingPeriod) {
        this.borrowingPeriod = borrowingPeriod;
    }

    public PeriodUnit getBorrowingPeriodUnit() {
        return borrowingPeriodUnit;
    }

    public void setBorrowingPeriodUnit(PeriodUnit borrowingPeriodUnit) {
        this.borrowingPeriodUnit = borrowingPeriodUnit;
    }

    public RepaymentMethod getBorrowingRepaymentMethod() {
        return borrowingRepaymentMethod;
    }

    public void setBorrowingRepaymentMethod(RepaymentMethod borrowingRepaymentMethod) {
        this.borrowingRepaymentMethod = borrowingRepaymentMethod;
    }

    public BorrowingState getBorrowingState() {
        if (StringUtils.isBlank(borrowingStateStr)) {
            return null;
        }
        return BorrowingState.valueOf(borrowingStateStr);
    }

    public String getBorrowingStateStr() {
        if (StringUtils.isBlank(borrowingStateStr)) {
            return null;
        }
        return BorrowingState.valueOf(borrowingStateStr).getDisplayName();
    }
    public void setBorrowingStateStr(String borrowingStateStr) {
        this.borrowingStateStr = borrowingStateStr;
    }

    public void setBorrowingState(BorrowingState borrowingState) {
        this.borrowingState = borrowingState;
    }

    public void setInvestment(Integer investment) {
        this.investment = investment;
    }

    public Integer getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(Integer couponCode) {
        this.couponCode = couponCode;
    }

    public Integer getInvestor() {
        return investor;
    }

    public void setInvestor(Integer investor) {
        this.investor = investor;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getTransfer() {
        return isTransfer;
    }

    public void setTransfer(Boolean transfer) {
        isTransfer = transfer;
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
    public String getOperationMethodStr() {
        return OperationMethod.valueOf(operationMethodStr).getDisplayName();
    }

    public void setOperationMethodStr(String operationMethodStr) {
        this.operationMethodStr = operationMethodStr;
    }

    public DeviceType getDeviceType() {
        if (StringUtils.isNotBlank(deviceTypeStr)) {
            return DeviceType.valueOf(deviceTypeStr);
        }
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceTypeStr() {
        if (StringUtils.isNotBlank(deviceTypeStr)) {
            return DeviceType.valueOf(deviceTypeStr).getDisplayName();
        }
        return deviceTypeStr;
    }

    public void setDeviceTypeStr(String deviceTypeStr) {
        this.deviceTypeStr = deviceTypeStr;
    }

    public String getBorrowingPeriodUnitDes() {
        if (null != borrowingPeriodUnit) {
            return borrowingPeriodUnit.getDisplayName();
        }
        return "";
    }

    public String getBorrowingRepaymentMethodDes() {
        if (null != borrowingRepaymentMethod) {
            return borrowingRepaymentMethod.getDisplayName();
        }
        return "";
    }
}
