package com.klzan.p2p.enums;

import com.klzan.core.util.StringUtils;

public enum TreeNodeState implements IEnum {
    OPEN("展开"),

    CLOSED("关闭");

    private String displayName;

    TreeNodeState(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public static Boolean hasChildren(String state) {
        if (StringUtils.equals(state, CLOSED.name().toLowerCase())) {
            return true;
        }
        return false;
    }
}