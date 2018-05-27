package com.klzan.p2p.enums;

/**
 * 期限单位
 */
public enum PeriodUnit implements IEnum {
    MONTH("个月"),

    DAY("天");

    private String displayName;

    PeriodUnit(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}