package com.klzan.p2p.model;

import com.klzan.p2p.enums.OrderMethod;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 */
@Entity
@Table(name = "karazam_order")
public class Order extends BaseModel {

    /** 当前用户 */
    private Integer userId;

    /** 付款人 -1银行 -2第三方支付 -3平台 -4投资人（多人） */
    private Integer payer;

    /** 付款人名称 */
    private String payerName;

    /** 收款人 */
    private Integer payee;

    /** 收款人名称 */
    private String payeeName;

    /** 状态（发起、审核中、平台处理中、环迅处理中、完成、失败、撤销） */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    /** 业务类型（投资-借款出借、还款-回款、充值、提现、（推荐费、其他）） */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType type;

    /** 支付方式（平台余额、第三方支付、银行卡） */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderMethod method;

    /** 业务编号  */
    private Integer business;

    /** 平台父订单号 */
    @Column(updatable = false, length = 100)
    private String parentOrderNo;

    /** 平台订单号 */
    @Column(nullable = false, updatable = false, unique = true, length = 100)
    private String orderNo;

    /** 第三方订单号 */
    @Column(length = 100)
    private String thirdOrderNo;

    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal payerBalance = BigDecimal.ZERO;

    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal payeeBalance = BigDecimal.ZERO;

    /** 实际支付金额 - 支付人服务费 - 支付人服务费(环迅) = 实际收款金额 + 收款人服务费 + 收款人服务费(环迅)  */

    /** 实际支付金额 */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;

    /** 实际收款金额 */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal amountReceived = BigDecimal.ZERO;

    /** 支付人服务费 */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal payerFee = BigDecimal.ZERO;

    /** 收款人服务费 */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal payeeFee = BigDecimal.ZERO;

    /** 支付人服务费(环迅) */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal payerThirdFee = BigDecimal.ZERO;

    /** 收款人服务费(环迅) */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal payeeThirdFee = BigDecimal.ZERO;

    /** 三方手续费是否平台承担 */
    private Boolean platformPay = false;

    /** 积分 */
    private Integer points;

    /** 支付时间（发起） */
    private Date launchDate;

    /** 审核时间（审核） */
    private Date auditDate;

    /** 处理中时间（处理中） */
    private Date processingDate;

    /** 完成时间（成功、失败、撤销） */
    private Date finishDate;

    /** 备注  */
    private String memo;

    /** 操作员  */
    private String operator;

    /** IP  */
    private String ip;

//    /** 第三方名称：XX银行、XX支付  */
//    private String thirdName;
//
//    /** 第三方LOGO  */
//    private String thirdLogo;
//
//    /** 银行卡号  */
//    private String cardNo;
//
//    /** 银行卡类型  */
//    private String cardType;
//
//    /** 第三方支付方式  */
//    private String thirdPayMethod;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPayer() {
        return payer;
    }

    public void setPayer(Integer payer) {
        this.payer = payer;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public Integer getPayee() {
        return payee;
    }

    public void setPayee(Integer payee) {
        this.payee = payee;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderMethod getMethod() {
        return method;
    }

    public void setMethod(OrderMethod method) {
        this.method = method;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public String getParentOrderNo() {
        return parentOrderNo;
    }

    public void setParentOrderNo(String parentOrderNo) {
        this.parentOrderNo = parentOrderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    public BigDecimal getPayerBalance() {
        return payerBalance;
    }

    public void setPayerBalance(BigDecimal payerBalance) {
        this.payerBalance = payerBalance;
    }

    public BigDecimal getPayeeBalance() {
        return payeeBalance;
    }

    public void setPayeeBalance(BigDecimal payeeBalance) {
        this.payeeBalance = payeeBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(BigDecimal amountReceived) {
        this.amountReceived = amountReceived;
    }

    public BigDecimal getPayerFee() {
        return payerFee;
    }

    public void setPayerFee(BigDecimal payerFee) {
        this.payerFee = payerFee;
    }

    public BigDecimal getPayeeFee() {
        return payeeFee;
    }

    public void setPayeeFee(BigDecimal payeeFee) {
        this.payeeFee = payeeFee;
    }

    public BigDecimal getPayerThirdFee() {
        return payerThirdFee;
    }

    public void setPayerThirdFee(BigDecimal payerThirdFee) {
        this.payerThirdFee = payerThirdFee;
    }

    public BigDecimal getPayeeThirdFee() {
        return payeeThirdFee;
    }

    public void setPayeeThirdFee(BigDecimal payeeThirdFee) {
        this.payeeThirdFee = payeeThirdFee;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
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

    public Date getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    @Transient
    public String getTypeDes() {
        return type.getDisplayName();
    }

    @Transient
    public String getStatusDes() {
        return status.getDisplayName();
    }

    @Transient
    public String getOrderSignDes() {
        return type.getSign().getDisplayName();
    }

    @Transient
    public String getOrderSign() {
        return type.getSign().getSign();
    }

    public Boolean getPlatformPay() {
        return platformPay;
    }

    public void setPlatformPay(Boolean platformPay) {
        this.platformPay = platformPay;
    }

    @Transient
    public BigDecimal getBalance() {
        if (userId.equals(payee)) {
            return payeeBalance;
        }
        if (userId.equals(payer)) {
            return payerBalance;
        }
        return BigDecimal.ZERO;
    }

    @Transient
    private BigDecimal totalRechargeFee = BigDecimal.ZERO;

    @Transient
    private BigDecimal totalWithdrawFee = BigDecimal.ZERO;

    /**
     * 充值服务费 = 收款人服务费 + 收款人服务费(环迅)
     * 充值服务费 = 收款人服务费   //平台承担第三方服务费
     * @return
     */
    @Transient
    public BigDecimal getTotalRechargeFee() {
        if (getPlatformPay()) {
            return getPayeeFee();
        }
        return getPayeeFee().add(getPayeeThirdFee());
    }

    /**
     * 提现服务费 = 支付人服务费 + 支付人服务费(环迅)
     * 提现服务费 = 支付人服务费   //平台承担第三方服务费
     * @return
     */
    @Transient
    public BigDecimal getTotalWithdrawFee() {
        if (getPlatformPay()) {
            return getPayerFee();
        }
        return getPayerFee().add(getPayerThirdFee());
    }

    /**
     * @return
     */
    @Transient
    public BigDecimal getTotalFee() {
        if (getUserId().equals(getPayer())) {
            return getPayerFee().add(getPayerThirdFee());
        }
        return getPayeeFee().add(getPayeeThirdFee());
    }

    /**
     * 付款方userId
     * 付款方名称
     * 收款方userId
     * 收款方名称
     * 状态（发起、审核中、平台处理中、第三方处理中、完成、失败、撤销）     *
     * 平台订单号
     * 第三方订单号
     * 金额
     * 服务费（平台）
     * 服务费（第三方）
     * 积分（奖励）
     * 支付时间（发起）
     * 审核时间（审核）
     * 到账时间（完成）
     * 支付方式（平台余额、第三方支付、银行卡）
     * 第三方名称：XX银行、XX支付     *
     * 银行卡号
     * 银行卡类型
     * 第三方支付方式：？
     * 备注
     * 操作员
     * IP
     * 平台业务类型（借款、投资、还款、回款、充值、提现、推荐费、其他）
     * 平台业务编号
     *
     */
}