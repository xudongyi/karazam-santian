package com.klzan.plugin.pay.ips.appRecharge.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.ips.AbstractRequest;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayAppRechargeRequest extends AbstractRequest implements IRequest {

    /**
     * 充值类型
     * */
    private String depositType;
    /**
     * IPS托管账户号
     * */
    private String ipsAcctNo;
    /**
     * 用户类型
     * */
    private String userType;
    /**
     * 充值金额
     * */
    private String trdAmt;
    /**
     * IPS手续费承担方
     * */
    private String ipsFeeType;
    /**
     * 平台手续费
     * */
    private String merFee;
    /**
     * 平台手续费收取方式
     * */
    private String merFeeType;
    /**
     * 银行卡号
     * */
    private String bankCard;

    public IpsPayAppRechargeRequest(String merBillNo, String merDate, String depositType, String ipsAcctNo, String userType, String trdAmt, String ipsFeeType, String merFee, String merFeeType, String bankCard) {
        this.merBillNo = merBillNo;
        this.merDate = merDate;
        this.depositType = depositType;
        this.ipsAcctNo = ipsAcctNo;
        this.userType = userType;
        this.trdAmt = trdAmt;
        this.ipsFeeType = ipsFeeType;
        this.merFee = merFee;
        this.merFeeType = merFeeType;
        this.bankCard = bankCard;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.MOBILE_RECHARGE;
    }

    @Override
    public Boolean isPageRequest() {
        return true;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public void setIpsAcctNo(String ipsAcctNo) {
        this.ipsAcctNo = ipsAcctNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTrdAmt() {
        return trdAmt;
    }

    public void setTrdAmt(String trdAmt) {
        this.trdAmt = trdAmt;
    }

    public String getIpsFeeType() {
        return ipsFeeType;
    }

    public void setIpsFeeType(String ipsFeeType) {
        this.ipsFeeType = ipsFeeType;
    }

    public String getMerFee() {
        return merFee;
    }

    public void setMerFee(String merFee) {
        this.merFee = merFee;
    }

    public String getMerFeeType() {
        return merFeeType;
    }

    public void setMerFeeType(String merFeeType) {
        this.merFeeType = merFeeType;
    }


    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }
}
