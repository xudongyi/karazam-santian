package com.klzan.plugin.pay.ips.transfer.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/16.
 */
public class TransferAccDetailRequest implements Serializable {
    /**
     * 商户订单号
     */
    private String merBillNo;
    /**
     * IPS 原冻结订单号
     */
    private String freezeId;
    /**
     * 转出方IPS存管账户号
     */
    private String outIpsAcctNo;
    /**
     * 转出方平台手续费 N(15,2)
     */
    private String outMerFee;
    /**
     * 转入方IPS存管账户号
     */
    private String inIpsAcctNo;
    /**
     * 转入方平台手续费 N(15,2)
     */
    private String inMerFee;
    /**
     * 转账金额 N(15,2)
     */
    private String trdAmt;

    public TransferAccDetailRequest(String merBillNo, String freezeId, String outIpsAcctNo, String outMerFee, String inIpsAcctNo, String inMerFee, String trdAmt) {
        this.merBillNo = merBillNo;
        this.freezeId = freezeId;
        this.outIpsAcctNo = outIpsAcctNo;
        this.outMerFee = outMerFee;
        this.inIpsAcctNo = inIpsAcctNo;
        this.inMerFee = inMerFee;
        this.trdAmt = trdAmt;
    }

    public String getMerBillNo() {
        return merBillNo;
    }

    public String getFreezeId() {
        return freezeId;
    }

    public String getOutIpsAcctNo() {
        return outIpsAcctNo;
    }

    public String getOutMerFee() {
        return outMerFee;
    }

    public String getInIpsAcctNo() {
        return inIpsAcctNo;
    }

    public String getInMerFee() {
        return inMerFee;
    }

    public String getTrdAmt() {
        return trdAmt;
    }
}
