package com.klzan.p2p.enums;

/**
 * 用户类型
 */
public enum UserType implements IEnum {
    GENERAL("个人", "1"),
    ENTERPRISE("企业", "2");

    private String displayName;
    private String tag;

    UserType(String displayName, String tag) {
        this.displayName = displayName;
        this.tag = tag;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getTag() {
        return tag;
    }

    public Boolean isPerson() {
        if (this == GENERAL) {
            return true;
        }
        return false;
    }
}
