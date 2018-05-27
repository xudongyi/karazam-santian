package com.klzan.plugin.pay.common.dto;

/**
 * 开户 - 支付响应
 *
 * @author: chenxinglin
 */
public class UserInfoRequest extends Request{

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}