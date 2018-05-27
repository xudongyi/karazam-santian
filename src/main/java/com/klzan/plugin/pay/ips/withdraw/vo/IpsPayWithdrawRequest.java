package com.klzan.plugin.pay.ips.withdraw.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.ips.AbstractRequest;

/**
 * Created by suhao Date: 2017/3/15 Time: 18:20
 *
 * @version: 1.0
 */
public class IpsPayWithdrawRequest extends AbstractRequest {
    /**
     * 用户类型 1-个人 2-企业
     */
    private String userType;
    /**
     * 提现金额
     */
    private String trdAmt;
    /**
     * 平台手续费
     */
    private String merFee;
    /**
     * 平台手续费类型
     */
    private String merFeeType;
    /**
     * IPS 手续费承担方
     */
    private String ipsFeeType;
    /**
     * IPS 存管账户号
     */
    private String ipsAcctNo;

    public IpsPayWithdrawRequest(String merBillNo, String merDate, String userType, String trdAmt, String merFee, String merFeeType, String ipsFeeType, String ipsAcctNo) {
        this.merBillNo = merBillNo;
        this.merDate = merDate;
        this.userType = userType;
        this.trdAmt = trdAmt;
        this.merFee = merFee;
        this.merFeeType = merFeeType;
        this.ipsFeeType = ipsFeeType;
        this.ipsAcctNo = ipsAcctNo;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.WITHDRAW;
    }

    @Override
    public Boolean isPageRequest() {
        return true;
    }

    public String getUserType() {
        return userType;
    }

    public String getTrdAmt() {
        return trdAmt;
    }

    public String getMerFee() {
        return merFee;
    }

    public String getMerFeeType() {
        return merFeeType;
    }

    public String getIpsFeeType() {
        return ipsFeeType;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

}
