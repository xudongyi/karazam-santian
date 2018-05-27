package com.klzan.plugin.pay.common.dto;

import com.klzan.p2p.enums.PaymentOrderType;

/**
 * 签约 - 支付响应
 *
 * @author: chenxinglin
 */
public class SignedRequest extends Request{

    private Integer userId;

    private String agreementType;

    // 查询时设值
    private PaymentOrderType orderType;
    private String queryOrderNo;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }

    public PaymentOrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(PaymentOrderType orderType) {
        this.orderType = orderType;
    }

    public String getQueryOrderNo() {
        return queryOrderNo;
    }

    public void setQueryOrderNo(String queryOrderNo) {
        this.queryOrderNo = queryOrderNo;
    }
}