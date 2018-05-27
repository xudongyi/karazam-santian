package com.klzan.p2p.enums;

public enum AgreementType implements IEnum {
    INVESTMENT("投资协议"),

    TRANSFER("转让人债权转让协议"),

    INVESTMENT_TRANSFER("受让人债权转让协议"),

    BORROWING("借款协议"),

    REGISTER("注册协议");

    private String displayName;

    AgreementType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}