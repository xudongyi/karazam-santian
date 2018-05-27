package com.klzan.plugin.pay.ips.unfreeze.vo;

import com.klzan.plugin.pay.ips.IpsPayDataResponse;

import java.math.BigDecimal;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayUnfreezeResponse extends IpsPayDataResponse{

    /**
     * 项目 ID 号
     */
    private String projectNo;
    /**
     * 原 IPS 冻结订单号
     */
    private String freezeId;

    /**
     * 平台手续费 N(15,2)
     */
    private BigDecimal merFee;

    /**
     * 解冻账号
     */
    private String ipsAcctNo;

    /**
     * 解冻状态 1:成功 0:失败
     */
    private String trdStatus;

    public String getProjectNo() {
        return projectNo;
    }

    public String getFreezeId() {
        return freezeId;
    }

    public BigDecimal getMerFee() {
        return merFee;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public String getTrdStatus() {
        return trdStatus;
    }
}
