package com.klzan.p2p.setting;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by suhao Date: 2017/5/3 Time: 09:10
 *
 * @version: 1.0
 */
public class RechargeSetting implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 982827572983485140L;
    /**
     * 是否开启充值
     */
    private Boolean enable;

    /**
     * 环迅充值个人手续费率
     */
    private BigDecimal ipsGeneralFeeRate;

    /**
     * 平台是否承担个人充值环迅手续费
     */
    private Boolean platformAssumeGeneralIpsFee;

    /**
     * 个人充值环迅手续费扣除类型
     */
    private Integer generalDeductionType;

    /**
     * 环迅充值企业手续费
     */
    private BigDecimal ipsEnterpriseFee;

    /**
     * 平台是否承担企业充值环迅手续费
     */
    private Boolean platformAssumeEnterpriseIpsFee;

    /**
     * 企业充值环迅手续费扣除类型
     */
    private Integer enterpriseDeductionType;

    /**
     * 是否开启还款充值
     */
    private Boolean enableRepay;

    /**
     * 环迅还款充值费率
     */
    private BigDecimal ipsRepayRechargeFeeRate;

    /**
     * 环迅还款充值最低收取
     */
    private BigDecimal ipsRepayRechargeMinFee;

    /**
     * 环迅还款充值最高收取
     */
    private BigDecimal ipsRepayRechargeMaxFee;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public BigDecimal getIpsGeneralFeeRate() {
        return ipsGeneralFeeRate;
    }

    public void setIpsGeneralFeeRate(BigDecimal ipsGeneralFeeRate) {
        this.ipsGeneralFeeRate = ipsGeneralFeeRate;
    }

    public Boolean getPlatformAssumeGeneralIpsFee() {
        return platformAssumeGeneralIpsFee;
    }

    public void setPlatformAssumeGeneralIpsFee(Boolean platformAssumeGeneralIpsFee) {
        this.platformAssumeGeneralIpsFee = platformAssumeGeneralIpsFee;
    }

    public Integer getGeneralDeductionType() {
        return generalDeductionType;
    }

    public void setGeneralDeductionType(Integer generalDeductionType) {
        this.generalDeductionType = generalDeductionType;
    }

    public BigDecimal getIpsEnterpriseFee() {
        return ipsEnterpriseFee;
    }

    public void setIpsEnterpriseFee(BigDecimal ipsEnterpriseFee) {
        this.ipsEnterpriseFee = ipsEnterpriseFee;
    }

    public Boolean getPlatformAssumeEnterpriseIpsFee() {
        return platformAssumeEnterpriseIpsFee;
    }

    public void setPlatformAssumeEnterpriseIpsFee(Boolean platformAssumeEnterpriseIpsFee) {
        this.platformAssumeEnterpriseIpsFee = platformAssumeEnterpriseIpsFee;
    }

    public Integer getEnterpriseDeductionType() {
        return enterpriseDeductionType;
    }

    public void setEnterpriseDeductionType(Integer enterpriseDeductionType) {
        this.enterpriseDeductionType = enterpriseDeductionType;
    }

    public Boolean getEnableRepay() {
        return enableRepay;
    }

    public void setEnableRepay(Boolean enableRepay) {
        this.enableRepay = enableRepay;
    }

    public BigDecimal getIpsRepayRechargeFeeRate() {
        return ipsRepayRechargeFeeRate;
    }

    public void setIpsRepayRechargeFeeRate(BigDecimal ipsRepayRechargeFeeRate) {
        this.ipsRepayRechargeFeeRate = ipsRepayRechargeFeeRate;
    }

    public BigDecimal getIpsRepayRechargeMinFee() {
        return ipsRepayRechargeMinFee;
    }

    public void setIpsRepayRechargeMinFee(BigDecimal ipsRepayRechargeMinFee) {
        this.ipsRepayRechargeMinFee = ipsRepayRechargeMinFee;
    }

    public BigDecimal getIpsRepayRechargeMaxFee() {
        return ipsRepayRechargeMaxFee;
    }

    public void setIpsRepayRechargeMaxFee(BigDecimal ipsRepayRechargeMaxFee) {
        this.ipsRepayRechargeMaxFee = ipsRepayRechargeMaxFee;
    }
}
