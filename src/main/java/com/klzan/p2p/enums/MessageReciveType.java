package com.klzan.p2p.enums;

public enum MessageReciveType implements IEnum {
    all("所有用户"),

    android("安卓"),

    ios("ios"),

    user("单个用户"),

    ;

    private String displayName;

    MessageReciveType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}