package com.klzan.p2p.enums;

/**
 * 平台订单业务类型符号
 */
public enum OrderSign implements IEnum {

    CREDIT("收入", "+"),

    DEBIT("支出", "-"),

    ;

    private String displayName;
    private String sign;

    OrderSign(String displayName, String sign) {
        this.displayName = displayName;
        this.sign = sign;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getSign() {
        return sign;
    }
}