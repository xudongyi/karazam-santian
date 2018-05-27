package com.klzan.p2p.enums;

/**
 * 关系类型
 */
public enum RelationType implements IEnum {
    PARENTS("父母"),
    SPOUSE("配偶"),
    BROTHERSANDSISTERS("兄弟姐妹"),
    SONSANDDAUGHTERS("子女"),
    FRIENDS("朋友"),
    OTHER("其他");

    private String displayName;

    private RelationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
