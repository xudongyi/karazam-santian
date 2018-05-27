package com.klzan.p2p.model;

import com.klzan.p2p.enums.CapitalMethod;
import com.klzan.p2p.enums.CapitalType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 资金
 */
@Entity
@Table(name = "karazam_capital")
public class Capital extends BaseModel {

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
     * 冻结
     */
    @Column(precision = 16, scale = 2)
    private BigDecimal frozen;

    /**
     * 解冻
     */
    @Column(precision = 16, scale = 2)
    private BigDecimal unfrozen;

    /**
     * 余额
     */
    @Column(precision = 16, scale = 2)
    private BigDecimal balance;

    /**
     * 备注
     */
    private String memo;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 订单号
     */
    @Column(updatable = false, length = 100)
    private String orderNo;

    /**
     * 操作人
     */
    private String operator;

    /**
     * IP
     */
    private String ip;

    /**
     * 平台订单
     */
    private Integer orderId;

    public Capital() {
    }

    public Capital(Integer userId, CapitalMethod method, CapitalType type, BigDecimal amount, UserFinance userFinance, String orderNo, String operator, String ip, String memo, Integer orderId) {
        this.userId = userId;
        this.method = method;
        this.type = type;
        switch (type){
            case CREDIT :
                this.credit = amount;
                break;
            case DEBIT :
                this.debit = amount;
                break;
            case FROZEN :
                this.frozen = amount;
                break;
            case UNFROZEN :
                this.unfrozen = amount;
                break;
            default:
                throw new RuntimeException();
        }
        this.balance = userFinance.getBalance();
        this.userId = userFinance.getUserId();
        this.orderNo = orderNo;
        this.operator = operator;
        this.ip = ip;
        this.memo = memo;
        this.orderId = orderId;
    }

    /**
     * 金额
     */
    @Transient
    public BigDecimal getAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        switch (getType()){
            case CREDIT :
                amount = getCredit();
                break;
            case DEBIT :
                amount = getDebit();
                break;
            case FROZEN :
                amount = getFrozen();
                break;
            case UNFROZEN :
                amount = getUnfrozen();
                break;
            default:
                amount = BigDecimal.ZERO;
        }
        return amount;
    }

    /**
     * 类型
     */
    @Transient
    public String getTypeDes() {
        return getType().getDisplayName();
    }

    /**
     * 方式
     */
    @Transient
    public String getMethodDes() {
        return getMethod().getDisplayName();
    }

    public CapitalType getType() {
        return type;
    }

    public CapitalMethod getMethod() {
        return method;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public BigDecimal getUnfrozen() {
        return unfrozen;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getMemo() {
        return memo;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getOperator() {
        return operator;
    }

    public String getIp() {
        return ip;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setType(CapitalType type) {
        this.type = type;
    }

    public void setMethod(CapitalMethod method) {
        this.method = method;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public void setUnfrozen(BigDecimal unfrozen) {
        this.unfrozen = unfrozen;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}