package com.klzan.plugin.pay.ips.combfreeze.vo;

import java.io.Serializable;

/**
 * Created by suhao Date: 2017/3/16 Time: 11:41
 *
 * @version: 1.0
 */
public class RedPacketRequest implements Serializable {
    /**
     * 商户订单号
     */
    private String merBillNo;
    /**
     * 业务类型 1-投标 2-债权转让 9-红包
     */
    private String bizType;
    /**
     * 冻结金额
     */
    private String trdAmt;
    /**
     * 平台手续费
     */
    private String merFee;
    /**
     * 冻结方类型 1-用户
     */
    private String freezeMerType;
    /**
     * 冻结账号
     */
    private String ipsAcctNo;
    /**
     * 它方账号 债权转让时为受让方存管账号；代偿还款时为担保人的存管账号
     */
    private String otherIpsAcctNo;

    public RedPacketRequest(String merBillNo, String bizType, String trdAmt, String merFee, String freezeMerType, String ipsAcctNo, String otherIpsAcctNo) {
        this.merBillNo = merBillNo;
        this.bizType = bizType;
        this.trdAmt = trdAmt;
        this.merFee = merFee;
        this.freezeMerType = freezeMerType;
        this.ipsAcctNo = ipsAcctNo;
        this.otherIpsAcctNo = otherIpsAcctNo;
    }

    public String getMerBillNo() {
        return merBillNo;
    }

    public String getBizType() {
        return bizType;
    }

    public String getTrdAmt() {
        return trdAmt;
    }

    public String getMerFee() {
        return merFee;
    }

    public String getFreezeMerType() {
        return freezeMerType;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public String getOtherIpsAcctNo() {
        return otherIpsAcctNo;
    }
}
