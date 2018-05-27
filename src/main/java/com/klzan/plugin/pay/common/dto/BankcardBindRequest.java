package com.klzan.plugin.pay.common.dto;

/**
 * 开户 - 支付响应
 *
 * @author: chenxinglin
 */
public class BankcardBindRequest extends Request{

    private Integer userId;

    private String bindingSystemNo;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBindingSystemNo() {
        return bindingSystemNo;
    }

    public void setBindingSystemNo(String bindingSystemNo) {
        this.bindingSystemNo = bindingSystemNo;
    }
}