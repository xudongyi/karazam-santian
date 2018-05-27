package com.klzan.p2p.enums;

/**
 * 资金类型
 */
public enum BankCardStatus implements IEnum {
    BINDED("已绑定", "10"),

    VERIFYING("待验证", "30"),

    FAILURE("绑定失败", "40"),

    UNBIND("解绑", "50");

    private String displayName;
    private String status;

    BankCardStatus(String displayName, String status) {
        this.displayName = displayName;
        this.status = status;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getStatus() {
        return status;
    }
}