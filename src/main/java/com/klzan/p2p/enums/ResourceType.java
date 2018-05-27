package com.klzan.p2p.enums;

/**
 * 资源类型
 */
public enum ResourceType {
    MENU("菜单"), BUTTON("按钮");

    private final String info;

    ResourceType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
