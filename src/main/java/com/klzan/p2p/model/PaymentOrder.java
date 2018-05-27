package com.klzan.p2p.model;

import com.klzan.p2p.enums.PaymentOrderMethod;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 支付订单
 */
@Entity
@Table(name = "karazam_payment_order")
public class PaymentOrder extends BaseModel {
    /**
     * 关联用户
     */
    private Integer userId;
    /**
     * 订单状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentOrderStatus status=PaymentOrderStatus.NEW_CREATE;
    /**
     * 订单类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentOrderType type;
    /**
     * 支付方式
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentOrderMethod method = PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY;
    /**
     * 内部订单号
     */
    @Column(nullable = false, updatable = false, unique = true, length = 100)
    private String orderNo;
    /**
     * 内部父订单号
     */
    @Column(length = 100)
    private String parentOrderNo;
    /**
     * 是否有子订单
     */
    private Boolean hasChildren = false;
    /**
     * 第三订单号
     */
    @Column(length = 100)
    private String extOrderNo;
    /**
     * 订单金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;
    /**
     * 订单金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal fee = BigDecimal.ZERO;
    /**
     * 备注
     */
    private String memo;

    /**
     * 扩展备用
     */
    @Column(length = 16777216)
    private String ext;

    /**
     * 请求参数
     */
    @Column(length = 16777216)
    private String reqParams;

    /**
     * 响应参数
     */
    @Column(length = 16777216)
    private String resParams;

    /**
     * 请求业务参数
     */
    @Column(length = 16777216)
    private String busReqParams;

    /** IP */
    private String ip;

    /** 操作员 */
    private String operator;

    /**
     * 借款
     */
    @Column
    private Integer borrowing;

    /**
     * 是否移动端
     */
    private Boolean isMobile;

    public PaymentOrder() {
    }

    public PaymentOrder(Boolean hasChildren, Integer userId, PaymentOrderType type, String orderNo, String parentOrderNo, BigDecimal amount, String memo, String ext) {
        this.hasChildren = hasChildren;
        this.userId = userId;
        this.type = type;
        this.orderNo = orderNo;
        this.parentOrderNo = parentOrderNo;
        this.amount = amount;
        this.memo = memo;
        this.ext = ext;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public PaymentOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentOrderStatus status) {
        this.status = status;
    }

    public PaymentOrderType getType() {
        return type;
    }

    public void setType(PaymentOrderType type) {
        this.type = type;
    }

    public PaymentOrderMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentOrderMethod method) {
        this.method = method;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getParentOrderNo() {
        return parentOrderNo;
    }

    public void setParentOrderNo(String parentOrderNo) {
        this.parentOrderNo = parentOrderNo;
    }

    public String getExtOrderNo() {
        return extOrderNo;
    }

    public void setExtOrderNo(String extOrderNo) {
        this.extOrderNo = extOrderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams;
    }

    public String getResParams() {
        return resParams;
    }

    public void setResParams(String resParams) {
        this.resParams = resParams;
    }

    public String getBusReqParams() {
        return busReqParams;
    }

    public void setBusReqParams(String busReqParams) {
        this.busReqParams = busReqParams;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public Boolean getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Boolean isMobile) {
        this.isMobile = isMobile;
    }

    public String getAmountString() {
        DecimalFormat format = new DecimalFormat("##0.00");
        return format.format(amount);
    }

}