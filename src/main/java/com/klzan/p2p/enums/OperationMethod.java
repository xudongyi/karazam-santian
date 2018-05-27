package com.klzan.p2p.enums;

/**
 * 操作类型
 */
public enum OperationMethod implements IEnum {
    AUTO("自动"),

    MANUAL("手动");

    private String displayName;

    OperationMethod(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}