package com.klzan.p2p.model;

import com.klzan.core.util.SnUtils;
import com.klzan.p2p.enums.FeeDeductionType;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现记录
 */
@Entity
@Table(name = "karazam_withdraw_record")
public class WithdrawRecord extends BaseModel {
    /**
     * 用户id
     */
    @Column(nullable = false)
    private Integer userId;

    /** 金额 */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal amount;

    /**
     * 提现费用
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal fee = BigDecimal.ZERO;

    /**
     * 环迅费用
     */
    @Column(precision = 16, scale = 2)
    private BigDecimal ipsFee = BigDecimal.ZERO;

    /**
     * 平台是否承担环迅手续费
     */
    private Boolean platformAssumeIpsFee = true;

    /**
     * 手续费扣除类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeeDeductionType deductionType = FeeDeductionType.IN;

    /**
     * 实际到账金额
     */
    @Column(precision = 16, scale = 2)
    private BigDecimal actualAmount;

    /**
     * 记录状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordStatus status = RecordStatus.NEW_CREATE;

    /**
     * 订单号
     */
    @Column(updatable = false, unique = true, length = 100)
    private String orderNo;

    private String remark;
    private Date submitPayTime;
    private Date finishPayTime;

    public WithdrawRecord() {

    }

    public WithdrawRecord(Integer userId, BigDecimal amount, BigDecimal fee, BigDecimal ipsFee, FeeDeductionType deductionType, Boolean platformAssumeIpsFee, String orderNo) {
        this.userId = userId;
        this.amount = amount;
        this.actualAmount = amount;
        this.fee = fee == null ? BigDecimal.ZERO : fee;
        this.ipsFee = ipsFee;
        if (deductionType == FeeDeductionType.IN) {
            if (platformAssumeIpsFee) {
                this.actualAmount = amount.subtract(getFee());
            } else {
                this.actualAmount = amount.subtract(getFee()).subtract(getIpsFee());
            }
        }
        this.deductionType = deductionType;
        this.platformAssumeIpsFee = platformAssumeIpsFee;
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public BigDecimal getIpsFee() {
        return ipsFee;
    }

    public Boolean getPlatformAssumeIpsFee() {
        return platformAssumeIpsFee;
    }

    public FeeDeductionType getDeductionType() {
        return deductionType;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getSubmitPayTime() {
        return submitPayTime;
    }

    public void setSubmitPayTime(Date submitPayTime) {
        this.submitPayTime = submitPayTime;
    }

    public Date getFinishPayTime() {
        return finishPayTime;
    }

    public void setFinishPayTime(Date finishPayTime) {
        this.finishPayTime = finishPayTime;
    }

    public void updateStatus(RecordStatus status) {
        this.status = status;
    }

    @Transient
    public BigDecimal getTotalAmount() {
        return getActualAmount().add(getAllFee());
    }

    @Transient
    public String getStatusDes() {
        if (this.status != null) {
            return this.status.getDisplayName();
        }
        return null;
    }

    @Transient
    public BigDecimal getActualIpsFee() {
        if(getPlatformAssumeIpsFee()){
            return BigDecimal.ZERO;
        }
        return getIpsFee();
    }

    @Transient
    public BigDecimal getAllFee() {
        if (platformAssumeIpsFee) {
            return getFee();
        } else {
            return getFee().add(getIpsFee());
        }
    }
}
