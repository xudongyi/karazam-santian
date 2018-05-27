package com.klzan.p2p.model;

import com.klzan.p2p.enums.CapitalMethod;
import com.klzan.p2p.enums.CapitalType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 平台资金
 */
@Entity
@Table(name = "karazam_platform_capital")
public class PlatformCapital extends BaseModel {

    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CapitalType type;

    /**
     * 方式
     *
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CapitalMethod method;

    /**
     * 收入
     */
    @Column(precision = 16, scale = 2)
    private BigDecimal credit;

    /**
     * 支出
     */
    @Column(precision = 16, scale = 2)
    private BigDecimal debit;

    /**
     * 关联资金记录ID
     */
    private Integer userFinanceId;
    /**
     * 备注
     */
    private String memo;

    /**
     * 操作人
     */
    private String operator;

    /**
     * IP
     */
    private String ip;

    /**
     * 订单号
     */
    @Column(updatable = false, length = 100)
    private String orderNo;

    public PlatformCapital() {
    }

    public PlatformCapital(CapitalType type, CapitalMethod method, BigDecimal credit, BigDecimal debit, Integer userFinanceId, String memo, String operator, String ip, String orderNo) {
        this.type = type;
        this.method = method;
        this.credit = credit;
        this.debit = debit;
        this.userFinanceId = userFinanceId;
        this.memo = memo;
        this.operator = operator;
        this.ip = ip;
        this.orderNo = orderNo;
    }

    public CapitalType getType() {
        return type;
    }

    public void setType(CapitalType type) {
        this.type = type;
    }

    public CapitalMethod getMethod() {
        return method;
    }

    public void setMethod(CapitalMethod method) {
        this.method = method;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public Integer getUserFinanceId() {
        return userFinanceId;
    }

    public void setUserFinanceId(Integer userFinanceId) {
        this.userFinanceId = userFinanceId;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}