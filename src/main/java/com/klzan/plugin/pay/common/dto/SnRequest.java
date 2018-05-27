package com.klzan.plugin.pay.common.dto;

/**
 * 开户 - 支付响应
 *
 * @author: chenxinglin
 */
public class SnRequest extends Request{

    private String sn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}