package com.klzan.p2p.vo.withdraw;

import com.klzan.p2p.enums.FeeDeductionType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 提现手续费
 */
public class WithdrawalFeeVo implements Serializable {
    /**
     * 到账金额
     */
    private BigDecimal arrivalAmount = BigDecimal.ZERO;

    /**
     * 提现金额
     */
    private BigDecimal amount = BigDecimal.ZERO;

    /**
     * 平台费用
     */
    private BigDecimal platformFee = BigDecimal.ZERO;
    /**
     * 环迅费用
     */
    private BigDecimal ipsFee = BigDecimal.ZERO;
    /**
     * 平台是否承担环迅手续费
     */
    private Boolean platformAssumeIpsFee = true;
    /**
     * 总提现费用
     */
    private BigDecimal fee = BigDecimal.ZERO;

    /**
     * 手续费扣除类型
     */
    private FeeDeductionType deductionType;

    public WithdrawalFeeVo(BigDecimal arrivalAmount, BigDecimal amount, BigDecimal platformFee, BigDecimal ipsFee, FeeDeductionType deductionType, Boolean platformAssumeIpsFee, BigDecimal fee) {
        this.arrivalAmount = arrivalAmount;
        this.amount = amount;
        this.platformFee = platformFee;
        this.ipsFee = ipsFee;
        this.deductionType = deductionType;
        this.platformAssumeIpsFee = platformAssumeIpsFee;
        this.fee = fee;
    }

    public BigDecimal getArrivalAmount() {
        return arrivalAmount;
    }

    public void setArrivalAmount(BigDecimal arrivalAmount) {
        this.arrivalAmount = arrivalAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(BigDecimal platformFee) {
        this.platformFee = platformFee;
    }

    public BigDecimal getIpsFee() {
        return ipsFee;
    }

    public void setIpsFee(BigDecimal ipsFee) {
        this.ipsFee = ipsFee;
    }

    public Boolean getPlatformAssumeIpsFee() {
        return platformAssumeIpsFee;
    }

    public void setPlatformAssumeIpsFee(Boolean platformAssumeIpsFee) {
        this.platformAssumeIpsFee = platformAssumeIpsFee;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public FeeDeductionType getDeductionType() {
        return deductionType;
    }

    public void setDeductionType(FeeDeductionType deductionType) {
        this.deductionType = deductionType;
    }
}