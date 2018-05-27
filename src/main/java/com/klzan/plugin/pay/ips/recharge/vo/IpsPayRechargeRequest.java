package com.klzan.plugin.pay.ips.recharge.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.ips.AbstractRequest;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayRechargeRequest extends AbstractRequest implements IRequest {


    /**
     * 充值类型 1,普通充值  2，还款充值
     */
    private String depositType;
    /**
     * 渠道种类 充值渠道 1、个人网银 2、企业网银（用户发起且为普通充值时必填）
     */
    private String channelType;
    /**
     * 充值银行号 充值类型为 1、普通充值且用户为非移动端发起时必填
     */
    private String bankCode;
    /**
     * 用户类型 1、个人 2、企业
     */
    private String userType;
    /**
     * IPS 存管账户号
     */
    private String ipsAcctNo;
    /**
     * 充值金额 N(15,2)
     */
    private String trdAmt;
    /**
     * ips 手续费承担方:1、平台商户2、平台用户
     */
    private String ipsFeeType;
    /**
     * 平台手续费
     */
    private String merFee;
    /**
     * 平台手续费收取方式：1、内扣，2、外扣
     */
    private String merFeeType;
    /**
     * 发起方：1、商户发起，2、用户发起（商户发起时充值类型只能为还款充值）
     */
    private String taker;

    public IpsPayRechargeRequest(String merBillNo, String merDate, String depositType,
                                 String channelType, String bankCode,
                             String userType, String ipsAcctNo, String trdAmt,
                                 String ipsFeeType, String merFee,
                         String merFeeType, String taker) {
        this.merBillNo = merBillNo;
        this.merDate = merDate;
        this.depositType = depositType;
        this.channelType = channelType;
        this.bankCode = bankCode;
        this.userType = userType;
        this.ipsAcctNo = ipsAcctNo;
        this.trdAmt = trdAmt;
        this.ipsFeeType = ipsFeeType;
        this.merFee = merFee;
        this.merFeeType = merFeeType;
        this.taker = taker;
    }
    public IpsPayRechargeRequest(String merBillNo,String merDate,String depositType,
                                 String userType,String ipsAcctNo,String trdAmt,String ipsFeeType,
                                 String merFee,String merFeeType){
        this.merBillNo = merBillNo;
        this.merDate = merDate;
        this.depositType = depositType;
        this.ipsAcctNo = ipsAcctNo;
        this.trdAmt = trdAmt;
        this.ipsFeeType = ipsFeeType;
        this.merFee = merFee;
        this.merFeeType = merFeeType;
        this.userType=userType;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.RECHARGE;
    }

    @Override
    public Boolean isPageRequest() {
        return true;
    }

    public String getDepositType() {
        return depositType;
    }

    public String getChannelType() {
        return channelType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getUserType() {
        return userType;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public String getTrdAmt() {
        return trdAmt;
    }

    public String getIpsFeeType() {
        return ipsFeeType;
    }

    public String getMerFee() {
        return merFee;
    }

    public String getMerFeeType() {
        return merFeeType;
    }

    public String getTaker() {
        return taker;
    }

}
