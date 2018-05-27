package com.klzan.mobile.vo;

import com.klzan.p2p.enums.OrderMethod;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/5 0005.
 */
public class FundRecordVo {

    /** 状态（发起、审核中、平台处理中、环迅处理中、完成、失败、撤销） */
    private OrderStatus status;

    /** 业务类型（投资-借款出借、还款-回款、充值、提现、（推荐费、其他）） */
    private OrderType type;

    /** 支付方式（平台余额、第三方支付、银行卡） */
    private OrderMethod method;

    private String methodDes;
    /** 实际支付金额 */
    private BigDecimal amount = BigDecimal.ZERO;

    private long creatDate;

    private  String typeDes;

    private  String StatusDes;

    private String orderNo;
    private BigDecimal payeeFee;
    private BigDecimal payerThirdFee;
    private BigDecimal payeeThirdFee;
    private BigDecimal payerFee;

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

    public BigDecimal getPayerFee() {
        return payerFee;
    }

    public void setPayerFee(BigDecimal payerFee) {
        this.payerFee = payerFee;
    }

    public String getMethodDes() {
        return methodDes;
    }

    public void setMethodDes(String methodDes) {
        this.methodDes = methodDes;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTypeDes() {
        return typeDes;
    }

    public void setTypeDes(String typeDes) {
        this.typeDes = typeDes;
    }

    public String getStatusDes() {
        return StatusDes;
    }

    public void setStatusDes(String statusDes) {
        StatusDes = statusDes;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(long creatDate) {
        this.creatDate = creatDate;
    }
}
