/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.vo.capital;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.PaymentOrderMethod;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;

import java.math.BigDecimal;

/**
 * 支付订单VO
 * @version: 1.0
 */
public class PaymentOrderVo extends BaseVo {
    /**
     * 关联用户
     */
    private Integer userId;
    /**
     * 用户昵称
     */
    private String name;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 订单状态
     */
    private PaymentOrderStatus status;
    private String statusStr;
    /**
     * 订单类型
     */
    private PaymentOrderType type;
    private String typeStr;
    /**
     * 支付方式
     */
    private PaymentOrderMethod method;
    private String methodStr;
    /**
     * 内部订单号
     */
    private String orderNo;
    /**
     * 第三方订单号
     */
    private String extOrderNo;

    /**
     * 内部父订单号
     */
    private String parentOrderNo;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String memo;

    /**
     * 扩展备用
     */
    private String ext;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public PaymentOrderStatus getStatus() {
        return PaymentOrderStatus.valueOf(statusStr);
    }

    public void setStatus(PaymentOrderStatus status) {
        this.status = status;
    }

    public String getStatusStr() {
        return PaymentOrderStatus.valueOf(statusStr).getDisplayName();
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public PaymentOrderType getType() {
        return PaymentOrderType.valueOf(typeStr);
    }

    public void setType(PaymentOrderType type) {
        this.type = type;
    }

    public String getTypeStr() {
        return PaymentOrderType.valueOf(typeStr).getDisplayName();
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public PaymentOrderMethod getMethod() {
        return PaymentOrderMethod.valueOf(methodStr);
    }

    public void setMethod(PaymentOrderMethod method) {
        this.method = method;
    }

    public String getMethodStr() {
        return PaymentOrderMethod.valueOf(methodStr).getDisplayName();
    }

    public void setMethodStr(String methodStr) {
        this.methodStr = methodStr;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getExtOrderNo() {
        return extOrderNo;
    }

    public void setExtOrderNo(String extOrderNo) {
        this.extOrderNo = extOrderNo;
    }

    public String getParentOrderNo() {
        return parentOrderNo;
    }

    public void setParentOrderNo(String parentOrderNo) {
        this.parentOrderNo = parentOrderNo;
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
}
