package com.klzan.plugin.pay.ips;

import com.klzan.plugin.pay.IDetailResponse;

/**
 * Created by suhao on 2017/3/14.
 */
public abstract class IpsPayDataResponse implements IDetailResponse {
    /**
     * 商户订单号
     */
    private String merBillNo;
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

    public String getIpsBillNo() {
        return ipsBillNo;
    }

    public String getIpsDoTime() {
        return ipsDoTime;
    }
}
