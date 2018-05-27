package com.klzan.plugin.pay.common.dto;

import java.math.BigDecimal;

/**
 * 开户 - 支付响应
 *
 * @author: chenxinglin
 */
public class OrgTransferRequest extends Request{

    private Integer userId;

    private BigDecimal amount;

    private String remark;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}