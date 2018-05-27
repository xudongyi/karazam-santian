package com.klzan.plugin.pay.ips.withdraw.vo;

import com.klzan.plugin.pay.ips.IpsPayDataResponse;

import java.math.BigDecimal;

/**
 * Created by suhao Date: 2017/3/15 Time: 18:20
 *
 * @version: 1.0
 */
public class IpsPayWithdrawResponse extends IpsPayDataResponse {
    /**
     * 平台手续费
     */
    private BigDecimal ipsFee;
    /**
     * IPS 手续费
     */
    private BigDecimal merFee;
    /**
     * IPS 存管账户号
     */
    private String ipsAcctNo;
    /**
     * IPS 提现金额
     */
    private BigDecimal ipsTrdAmt;
    /**
     * 提现状态 0-失败 1-成功 2-处理中 3-退票
     */
    private Integer trdStatus;

    public BigDecimal getIpsFee() {
        return ipsFee;
    }

    public BigDecimal getMerFee() {
        return merFee;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public BigDecimal getIpsTrdAmt() {
        return ipsTrdAmt;
    }

    public Integer getTrdStatus() {
        return trdStatus;
    }
}
