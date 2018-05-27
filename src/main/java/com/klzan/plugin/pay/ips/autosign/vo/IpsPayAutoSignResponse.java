package com.klzan.plugin.pay.ips.autosign.vo;

import com.klzan.plugin.pay.ips.IpsPayDataResponse;

/**
 * Created by suhao Date: 2017/3/16 Time: 13:34
 *
 * @version: 1.0
 */
public class IpsPayAutoSignResponse extends IpsPayDataResponse {
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 日期
     */
    private String merDate;
    /**
     * IPS存管账户号
     */
    private String ipsAcctNo;
    /**
     * 签约类型
     */
    private String signedType;
    /**
     * 有效期
     */
    private String validity;
    /**
     * 标的周期最小值
     */
    private String cycMinVal;
    /**
     * 标的周期最大值
     */
    private String cycMaxVal;
    /**
     * 投标限额最小值
     */
    private String bidMin;
    /**
     * 投标限额最大值
     */
    private String bidMax;
    /**
     * 利率最小值
     */
    private String rateMin;
    /**
     * 利率最大值
     */
    private String rateMax;
    /**
     * IPS 授权号
     */
    private String ipsAuthNo;
    /**
     * 签约结果 0 失败 1 成功
     */
    private String status;

    public String getUserType() {
        return userType;
    }

    public String getMerDate() {
        return merDate;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public String getSignedType() {
        return signedType;
    }

    public String getValidity() {
        return validity;
    }

    public String getCycMinVal() {
        return cycMinVal;
    }

    public String getCycMaxVal() {
        return cycMaxVal;
    }

    public String getBidMin() {
        return bidMin;
    }

    public String getBidMax() {
        return bidMax;
    }

    public String getRateMin() {
        return rateMin;
    }

    public String getRateMax() {
        return rateMax;
    }

    public String getIpsAuthNo() {
        return ipsAuthNo;
    }

    public String getStatus() {
        return status;
    }
}
