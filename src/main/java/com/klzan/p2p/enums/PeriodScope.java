package com.klzan.p2p.enums;

public enum PeriodScope implements IEnum {
    BETWEEN_1MONTH_AND_3MONTH("1-3月"),

    BETWEEN_3MONTH_AND_6MONTH("3-6月"),

    BETWEEN_3MONTH_AND_12MONTH("3-12月"),

    BETWEEN_12MONTH_AND_24MONTH("12-24月"),

    OVER_12MONTH("12月以上"),

    BETWEEN_1DAY_AND_7DAY("1-7天"),

    BETWEEN_7DAY_AND_15DAY("7-15天"),

    BETWEEN_15DAY_AND_30DAY("15-30天"),

    OVER_30DAY("30天以上");

    private String displayName;

    PeriodScope(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}