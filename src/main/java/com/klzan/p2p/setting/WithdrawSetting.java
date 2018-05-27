package com.klzan.p2p.setting;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by suhao Date: 2017/2/8 Time: 16:51
 *
 * @version: 1.0
 */
public class WithdrawSetting implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 982827572983485140L;
    /**
     * 是否开启提现
     */
    private Boolean enable;

    /**
     * 环迅手续费
     */
    private BigDecimal ipsFee;

    /**
     * 平台是否承担环迅手续费
     */
    private Boolean platformAssumeIpsFee;

    /**
     * 起提金额
     */
    private BigDecimal beginAmount;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private BigDecimal feeRate;
    private BigDecimal minFee;
    private BigDecimal maxFee;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
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

    public BigDecimal getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(BigDecimal beginAmount) {
        this.beginAmount = beginAmount;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public BigDecimal getMinFee() {
        return minFee;
    }

    public void setMinFee(BigDecimal minFee) {
        this.minFee = minFee;
    }

    public BigDecimal getMaxFee() {
        return maxFee;
    }

    public void setMaxFee(BigDecimal maxFee) {
        this.maxFee = maxFee;
    }
}
