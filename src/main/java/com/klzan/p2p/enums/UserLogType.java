package com.klzan.p2p.enums;

public enum UserLogType implements IEnum {
    REGIST("注册"),

    LOGIN("登录"),

    MODIFY("修改"),

    RECHARGE("充值"),

    CHARGE("扣费"),

    EXPERIENCE_AMOUNT_RECHARGE("体验金充值");

    private String displayName;

    UserLogType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}