package com.klzan.plugin.pay.ips.autosign.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.ips.AbstractRequest;

/**
 * Created by suhao Date: 2017/3/16 Time: 13:34
 *
 * @version: 1.0
 */
public class IpsPayAutoSignRequest extends AbstractRequest {
    /**
     * 用户类型 1-个人 2-企业
     */
    private String userType;
    /**
     * IPS 存管账户号
     */
    private String ipsAcctNo;
    /**
     * 签约类型 0 自动投标,1 自动还款
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

    public IpsPayAutoSignRequest(String merBillNo, String merDate, String userType, String ipsAcctNo, String signedType, String validity, String cycMinVal, String cycMaxVal, String bidMin, String bidMax, String rateMin, String rateMax) {
        this.merBillNo = merBillNo;
        this.merDate = merDate;
        this.userType = userType;
        this.ipsAcctNo = ipsAcctNo;
        this.signedType = signedType;
        this.validity = validity;
        this.cycMinVal = cycMinVal;
        this.cycMaxVal = cycMaxVal;
        this.bidMin = bidMin;
        this.bidMax = bidMax;
        this.rateMin = rateMin;
        this.rateMax = rateMax;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.AUTO_SIGN;
    }

    @Override
    public Boolean isPageRequest() {
        return true;
    }

    public String getUserType() {
        return userType;
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

}
