package com.klzan.p2p.model;

import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * 出借记录
 */
@Entity
@Table(name = "karazam_lending_record")
public class LendingRecord extends BaseModel {
    /**
     * 借款
     */
    @Column(nullable = false)
    private Integer borrowing;
    /**
     * 投资
     */
    @Column(nullable = false)
    private Integer investment;
    /**
     * 投资记录ID
     */
    @Column(nullable = false)
    private Integer investmentRecordId;
    /**
     * 金额
     */
    @Min(0)
    @Digits(integer = 16, fraction = 2)
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;
    /**
     * 批次订单号
     */
    @Column(length = 100)
    private String batchOrderNo;

    /**
     * 投资订单号
     */
    @Column(updatable = false, length = 100)
    private String investOrderNo;

    /**
     * 订单号
     */
    @Column(updatable = false, unique = true, length = 100)
    private String orderNo;
    /**
     * 第三方订单号
     */
    private String extOrderNo;
    /**
     * 记录状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordStatus status = RecordStatus.NEW_CREATE;
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

    public LendingRecord() {
    }

    public LendingRecord(Integer borrowing, Integer investment, Integer investmentRecordId, BigDecimal amount, String batchOrderNo, String investOrderNo, String orderNo, String operator, String ip) {
        this.borrowing = borrowing;
        this.investment = investment;
        this.investmentRecordId = investmentRecordId;
        this.amount = amount;
        this.batchOrderNo = batchOrderNo;
        this.investOrderNo = investOrderNo;
        this.orderNo = orderNo;
        this.operator = operator;
        this.ip = ip;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public Integer getInvestment() {
        return investment;
    }

    public Integer getInvestmentRecordId() {
        return investmentRecordId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getBatchOrderNo() {
        return batchOrderNo;
    }

    public String getInvestOrderNo() {
        return investOrderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getExtOrderNo() {
        return extOrderNo;
    }

    public RecordStatus getStatus() {
        return status;
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

    @Transient
    public void success(String extOrderNo) {
        this.extOrderNo = extOrderNo;
        this.status = RecordStatus.SUCCESS;
    }

    @Transient
    public void failure() {
        status = RecordStatus.FAILURE;
    }

    @Transient
    public void updateBatchOrderNo(String batchOrderNo) {
        this.batchOrderNo = batchOrderNo;
    }
}