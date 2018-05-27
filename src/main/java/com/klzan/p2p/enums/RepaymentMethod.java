package com.klzan.p2p.enums;

/**
 * 还款方式
 */
public enum RepaymentMethod implements IEnum {
    EACH_PERIOD_AVG_CAPITAL_PLUS_INTEREST("按月还款、等额本息"),

    EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL("每月付息、到期还本"),

    FIXED_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL("按期付息、到期还本"),

    CURRENT_AND_EACH_PERIOD_INTEREST_AND_LAST_PERIOD_CAPITAL("当日付息、每月付息、到期还本"),

    LAST_PERIOD_CAPITAL_PLUS_INTEREST("到期还本付息");

    private String displayName;

    RepaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}