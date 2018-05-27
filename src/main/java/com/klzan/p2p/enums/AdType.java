package com.klzan.p2p.enums;

public enum AdType implements IEnum {
    TEXT("文本"),

    IMAGE("图片"),

    FLASH("Flash");

    private String displayName;

    AdType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}