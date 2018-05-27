package com.klzan.plugin.pay.ips.recharge.vo;

import com.klzan.plugin.pay.ips.IpsPayDataResponse;

import java.math.BigDecimal;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayRechargeResponse extends IpsPayDataResponse{

    /**
     * 充值类型：1、普通充值 2、还款充值
     */
    private String depositType;
    /**
     * 充值渠道 1、个人网银 2、企业网银
     */
    private String channelType;
    /**
     * 充值银行
     */
    private String bankCode;
    /**
     * IPS 充值金额
     */
    private BigDecimal ipsTrdAmt;
    /**
     * IPS 手续费金额
     */
    private BigDecimal ipsFee;
    /**
     * 平台手续费
     */
    private BigDecimal merFee;
    /**
     * 充值状态  0 失败,1 成功,2 处理中
     */
    private String trdStatus;

    public String getDepositType() {
        return depositType;
    }

    public String getChannelType() {
        return channelType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public BigDecimal getIpsTrdAmt() {
        return ipsTrdAmt;
    }

    public BigDecimal getIpsFee() {
        return ipsFee;
    }

    public BigDecimal getMerFee() {
        return merFee;
    }

    public String getTrdStatus() {
        return trdStatus;
    }
}
