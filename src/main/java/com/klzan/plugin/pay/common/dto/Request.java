package com.klzan.plugin.pay.common.dto;

/**
 * 支付请求
 *
 * @author: chenxinglin
 */
public class Request {
    /**
     * 是否移动端
     */
    private Boolean isMobile = Boolean.FALSE;

    public void setMobile(Boolean mobile) {
        isMobile = mobile;
    }

    public Boolean isMobile() {
        return isMobile;
    }
}