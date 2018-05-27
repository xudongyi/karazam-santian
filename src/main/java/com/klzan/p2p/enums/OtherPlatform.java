package com.klzan.p2p.enums;

/**
 * 第三方平台
 */
public enum OtherPlatform implements IEnum {
    QQ("QQ"),
    WECHAT("微信");

    private String displayName;

    OtherPlatform(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
