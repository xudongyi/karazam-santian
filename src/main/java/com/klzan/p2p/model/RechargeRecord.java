package com.klzan.p2p.model;

import com.klzan.core.util.SnUtils;
import com.klzan.p2p.enums.FeeDeductionType;
import com.klzan.p2p.enums.RechargeBusinessType;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.base.BaseModel;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值记录
 */
@Entity
@Table(name = "karazam_recharge_record")
public class RechargeRecord extends BaseModel {
    /**
     * 用户id
     */
    @Column(nullable = false)
    private Integer userId;

    /**
     * 金额
     */
    @Column(precision = 16, scale = 2)
    private BigDecimal amount;

    /**
     * 实际支付金额=amount-优惠金额
     */
    @Column(precision = 16, scale = 2)
    private BigDecimal actualAmount;

    /**
     * 服务费
     */
    @Column(precision = 16, scale = 2)
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
     * 银行ID
     */
    private Integer bankId;
    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 优惠券ID
     */
    private Integer coupon;

    /**
     * 记录状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordStatus status = RecordStatus.NEW_CREATE;

    /**
     * 充值类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RechargeBusinessType businessType = RechargeBusinessType.GENERAL;

    /**
     * 订单号
     */
    @Column(updatable = false, unique = true, length = 100)
    private String orderNo;

    /**
     * IPS处理时间
     */
    @Column(length = 19)
    private Date processTime;

    public RechargeRecord() {
    }

    /**
     * 网银支付
     *
     * @param businessType
     * @param userId
     * @param amount
     * @param fee
     * @param bankCode
     * @param bankId
     * @param coupon
     */
    public RechargeRecord(RechargeBusinessType businessType, Integer userId, BigDecimal amount, BigDecimal fee, BigDecimal ipsFee, Boolean platformAssumeIpsFee, FeeDeductionType deductionType, String bankName, String bankCode, Integer bankId, Integer coupon, String orderNo) {
        this.businessType = businessType;
        this.userId = userId;
        this.amount = amount;
        this.actualAmount = amount;
        this.fee = fee == null ? BigDecimal.ZERO : fee;
        this.ipsFee = ipsFee == null ? BigDecimal.ZERO : ipsFee;
        this.platformAssumeIpsFee = platformAssumeIpsFee;
        this.deductionType = deductionType;
        if (deductionType == FeeDeductionType.IN) {
            if (platformAssumeIpsFee) {
                this.actualAmount = amount.subtract(getFee());
            } else {
                this.actualAmount = amount.subtract(getFee()).subtract(getIpsFee());
            }
        }
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.bankId = bankId;
        this.coupon = coupon;
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public Integer getBankId() {
        return bankId;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getBankName() {
        return bankName;
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

    public RechargeBusinessType getBusinessType() {
        return businessType;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void updateStatus(RecordStatus status) {
        this.status = status;
    }

    @Transient
    public String getStatusDes() {
        if (this.status != null) {
            return this.status.getDisplayName();
        }
        return null;
    }


    @Transient
    public String getBusinessTypeDes() {
        if (this.businessType != null) {
            return this.businessType.getDisplayName();
        }
        return null;
    }

    @Transient
    public BigDecimal getAllFee() {
        if(getPlatformAssumeIpsFee()){
            return getFee();
        }
        return getFee().add(getIpsFee());
    }

    @Transient
    public BigDecimal getActualIpsFee() {
        if(getPlatformAssumeIpsFee()){
            return BigDecimal.ZERO;
        }
        return getIpsFee();
    }

    @Transient
    public void updateIpsFee(BigDecimal ipsFee) {
        this.ipsFee = ipsFee;
        if (getDeductionType() == FeeDeductionType.IN) {
            if (platformAssumeIpsFee) {
                this.actualAmount = amount.subtract(getFee());
            } else {
                this.actualAmount = amount.subtract(getFee()).subtract(getIpsFee());
            }
        }
    }

    @Transient
    public void updateIpsProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    @Transient
    public void updateBanKInfo(Bank bank) {
        this.bankCode = bank.getCode();
        this.bankId = bank.getId();
        this.bankName = bank.getName();
    }
}
