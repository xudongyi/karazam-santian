package com.klzan.p2p.enums;

/**
 * 客户端类型
 */
public enum ClientType implements IEnum {
    PC("pc"),
    MOBILEWEB("mobileweb"),
    ANDROID("android"),
    IOS("ios");

    private String displayName;

    ClientType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
