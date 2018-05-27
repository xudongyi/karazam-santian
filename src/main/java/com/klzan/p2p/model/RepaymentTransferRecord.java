package com.klzan.p2p.model;

import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * 还款转账记录
 */
@Entity
@Table(name = "karazam_repayment_transfer_record")
public class RepaymentTransferRecord extends BaseModel {
    /**
     * 借款
     */
    @Column(nullable = false)
    private Integer borrowing;
    /**
     * 还款
     */
    @Column(nullable = true)
    private Integer repayment;
    /**
     * 还款计划ID
     */
    @Column(nullable = true)
    private Integer repaymentPlan;
    /**
     * 投资人ID
     */
    @Column(nullable = true)
    private Integer invstor;
    /**
     * 金额
     */
    @Min(0)
    @Digits(integer = 16, fraction = 2)
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;
    /**
     * 还款手续费
     */
    @Min(0)
    @Digits(integer = 16, fraction = 2)
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal fee = BigDecimal.ZERO;
    /**
     * 还款批次订单号
     */
    @Column(length = 100)
    private String batchOrderNo;
    /**
     * 还款计划订单号
     */
    @Column(updatable = false, unique = true, length = 100)
    private String planOrderNo;

    /**
     * 还款计划转账订单号
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
    /**
     * 是否提前还款
     */
    private Boolean ahead;

    public RepaymentTransferRecord() {
    }

    public RepaymentTransferRecord(Integer borrowing, Integer repayment, Integer repaymentPlan, Integer invstor, BigDecimal amount, BigDecimal fee, String batchOrderNo, String planOrderNo, String orderNo, String operator, String ip, Boolean ahead) {
        this.borrowing = borrowing;
        this.repayment = repayment;
        this.repaymentPlan = repaymentPlan;
        this.invstor = invstor;
        this.amount = amount;
        this.fee = fee;
        this.batchOrderNo = batchOrderNo;
        this.planOrderNo = planOrderNo;
        this.orderNo = orderNo;
        this.operator = operator;
        this.ip = ip;
        this.ahead = ahead;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public Integer getRepayment() {
        return repayment;
    }

    public Integer getRepaymentPlan() {
        return repaymentPlan;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public String getBatchOrderNo() {
        return batchOrderNo;
    }

    public String getPlanOrderNoOrderNo() {
        return planOrderNo;
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

    public Integer getInvstor() {
        return invstor;
    }

    public void setInvstor(Integer invstor) {
        this.invstor = invstor;
    }

    public Boolean getAhead() {
        return ahead;
    }

    public void setAhead(Boolean ahead) {
        this.ahead = ahead;
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