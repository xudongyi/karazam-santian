package com.klzan.p2p.enums;

/**
 * 资金类型
 */
public enum CapitalType implements IEnum {
    CREDIT("收入"),

    DEBIT("支出"),

    FROZEN("冻结"),

    UNFROZEN("解冻");

    private String displayName;

    CapitalType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}