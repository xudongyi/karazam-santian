package com.klzan.p2p.enums;

/**
 * 平台订单支付方式
 */
public enum OrderMethod implements IEnum {

    BANLANCE("余额支付"),

    IPS("环迅支付"),

    CPCN("中金支付"),

    THIRD_PAYMENT("第三方支付"),

    BANK("银行卡支付");

    private String displayName;

    OrderMethod(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}