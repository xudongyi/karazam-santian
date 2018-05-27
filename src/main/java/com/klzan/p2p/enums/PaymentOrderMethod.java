package com.klzan.p2p.enums;

import com.klzan.plugin.pay.IResponse;
import com.klzan.plugin.pay.ips.IpsPayPluginResponse;

/**
 * 订单支付方式
 */
public enum PaymentOrderMethod implements IEnum {

    IPS_PAY("环迅支付", new IpsPayPluginResponse()),
    CHINA_CLEAING_DEPOSIT_PAY("中金托管支付", new IpsPayPluginResponse());

    private String displayName;
    private IResponse response;

    PaymentOrderMethod(String displayName, IResponse response) {
        this.displayName = displayName;
        this.response = response;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public IResponse getResponse() {
        return response;
    }
}