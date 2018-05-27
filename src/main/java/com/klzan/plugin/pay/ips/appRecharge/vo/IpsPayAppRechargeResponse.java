package com.klzan.plugin.pay.ips.appRecharge.vo;


import com.klzan.plugin.pay.ips.IpsPayDataResponse;

import java.math.BigDecimal;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayAppRechargeResponse extends IpsPayDataResponse {

    private String  depositType;
    private BigDecimal  ipsFee;
    private BigDecimal  merFee;
    private String  banktn;
    private String  trdStatus;
    private BigDecimal  ipsTrdAmt;

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public BigDecimal getIpsFee() {
        return ipsFee;
    }

    public void setIpsFee(BigDecimal ipsFee) {
        this.ipsFee = ipsFee;
    }

    public BigDecimal getMerFee() {
        return merFee;
    }

    public void setMerFee(BigDecimal merFee) {
        this.merFee = merFee;
    }

    public String getBanktn() {
        return banktn;
    }

    public void setBanktn(String banktn) {
        this.banktn = banktn;
    }

    public String getTrdStatus() {
        return trdStatus;
    }

    public void setTrdStatus(String trdStatus) {
        this.trdStatus = trdStatus;
    }

    public BigDecimal getIpsTrdAmt() {
        return ipsTrdAmt;
    }

    public void setIpsTrdAmt(BigDecimal ipsTrdAmt) {
        this.ipsTrdAmt = ipsTrdAmt;
    }
}
