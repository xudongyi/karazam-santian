package com.klzan.plugin.pay.ips.combfreeze.vo;

import java.io.Serializable;

/**
 * Created by suhao Date: 2017/3/16 Time: 11:41
 *
 * @version: 1.0
 */
public class RedPacketResponse implements Serializable {
    /**
     * 商户订单号
     */
    private String merBillNo;
    /**
     * IPS冻结金额
     */
    private String ipsTrdAmt;
    /**
     * 冻结账号
     */
    private String ipsAcctNo;
    /**
     * 它方账号 债权转让时为受让方存管账号；代偿还款时为担保人的存管账号；
     */
    private String otherIpsAcctNo;
    /**
     * IPS订单号
     */
    private String ipsBillNo;
    /**
     * IPS处理时间
     */
    private String ipsDoTime;

    public String getMerBillNo() {
        return merBillNo;
    }

    public String getIpsTrdAmt() {
        return ipsTrdAmt;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public String getOtherIpsAcctNo() {
        return otherIpsAcctNo;
    }

    public String getIpsBillNo() {
        return ipsBillNo;
    }

    public String getIpsDoTime() {
        return ipsDoTime;
    }
}
