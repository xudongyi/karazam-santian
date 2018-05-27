package com.klzan.p2p.enums;

/**
 * 用户状态
 */
public enum UserStatus implements IEnum {
    ENABLE("正常"),
    DISABLE("禁用"),
    LOCKED("锁定");

    private String displayName;

    UserStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
