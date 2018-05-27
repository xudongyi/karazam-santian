package com.klzan.plugin.pay.ips.withdrawrefticket.vo;

import com.klzan.plugin.pay.IDetailResponse;

import java.math.BigDecimal;

/**
 * Created by suhao Date: 2017/3/16 Time: 14:58
 *
 * @version: 1.0
 */
public class IpsPayWithdrawRefundTicketResponse implements IDetailResponse {
    /**
     * 商户订单号
     */
    private String merBillNo;
    /**
     * 退票金额
     */
    private BigDecimal refundAmt;
    /**
     * 退票日期
     */
    private String trdDate;
    /**
     * 退票原因
     */
    private String reason;

    public String getMerBillNo() {
        return merBillNo;
    }

    public BigDecimal getRefundAmt() {
        return refundAmt;
    }

    public String getTrdDate() {
        return trdDate;
    }

    public String getReason() {
        return reason;
    }
}
