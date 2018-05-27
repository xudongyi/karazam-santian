package com.klzan.plugin.pay.ips.transfer.vo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/3/16.
 */
public class TransferAccDetailResponse {
    /**
     *商户订单号
     */
    private String merBillNo;
    /**
     *转出方IPS存管账户号
     */
    private String outIpsAcctNo;
    /**
     *转入方IPS存管账户号
     */
    private String inIpsAcctNo;
    /**
     *IPS 订单号
     */
    private String ipsBillNo;
    /**
     *IPS 处理时间 yyyy-MM-dd HH:mm:ss
     */
    private String ipsDoTime;
    /**
     * IPS转账金额(IPS实际转账) N(15,2)
     */
    private BigDecimal ipsTrdAmt;
    /**
     * 转账状态 0 失败 1 成功
     */
    private String trdStatus;

    public String getMerBillNo() {
        return merBillNo;
    }

    public String getOutIpsAcctNo() {
        return outIpsAcctNo;
    }

    public String getInIpsAcctNo() {
        return inIpsAcctNo;
    }

    public String getIpsBillNo() {
        return ipsBillNo;
    }

    public String getIpsDoTime() {
        return ipsDoTime;
    }

    public BigDecimal getIpsTrdAmt() {
        return ipsTrdAmt;
    }

    public String getTrdStatus() {
        return trdStatus;
    }
}
