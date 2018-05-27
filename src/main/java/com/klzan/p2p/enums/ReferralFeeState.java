package com.klzan.p2p.enums;

/**
 * 推荐费状态
 */
public enum ReferralFeeState implements IEnum {
    WAIT_APPLY("待申请"),

    APPLYING("待审核"),

    WAIT_PAY("待结算"),

    PAYING("结算中"),

    PAID("已结算"),

    OFFLINE_PAID("线下已结算"),

    POSTPAID("置后结算"),

    FAILURE("结算失败"),;

    private String displayName;

    ReferralFeeState(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}