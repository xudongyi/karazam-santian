package com.klzan.plugin.pay.common.dto;

import java.math.BigDecimal;

/**
 * 充值
 * Created by suhao Date: 2017/11/22 Time: 14:51
 *
 * @version: 1.0
 */
public class RechargeRequest extends Request {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 充值金额
     */
    private BigDecimal amount;

    public RechargeRequest(Boolean isMobile, Integer userId, BigDecimal amount) {
        setMobile(isMobile);
        this.userId = userId;
        this.amount = amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
