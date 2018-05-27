package com.klzan.p2p.enums;

public enum InterestRateScope implements IEnum {
    BETWEEN_0_AND_5("0-5%"),

    BETWEEN_5_AND_10("5-10%"),

    BETWEEN_10_AND_15("10-15%"),

    BETWEEN_15_AND_20("15-20%"),

    BETWEEN_20_AND_24("20-24%"),

    OVER_24("24%以上");

    private String displayName;

    InterestRateScope(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}