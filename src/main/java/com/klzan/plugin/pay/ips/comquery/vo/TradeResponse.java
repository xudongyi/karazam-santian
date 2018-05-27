package com.klzan.plugin.pay.ips.comquery.vo;

import java.io.Serializable;

/**
 * Created by suhao Date: 2017/3/16 Time: 14:27
 *
 * @version: 1.0
 */
public class TradeResponse implements Serializable {
    /**
     * 商户订单号
     */
    private String merBillNo;
    /**
     * 交易日期
     */
    private String merDate;
    /**
     * 交易状态
     */
    private String trdStatus;
    /**
     * IPS订单号
     */
    private String ipsBillNo;
    /**
     * IPS 处理时间
     */
    private String ipsDoTime;

    public String getMerBillNo() {
        return merBillNo;
    }

    public String getMerDate() {
        return merDate;
    }

    public String getTrdStatus() {
        return trdStatus;
    }

    public String getIpsBillNo() {
        return ipsBillNo;
    }

    public String getIpsDoTime() {
        return ipsDoTime;
    }
}
