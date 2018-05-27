package com.klzan.p2p.enums;

/**
 * Created by suhao on 2017/5/10.
 */
public enum InterestMethod implements IEnum {

    T_PLUS_ZERO("T+0计息", "T+0后期整月", 0, 0, false),
    T_PLUS_ZERO_B("T+0计息", "T+0前期整月", 0, 0, true),
    T_PLUS_ONE("T+1计息", "T+1后期整月", 1, 1, false),
    T_PLUS_ONE_B("T+1计息", "T+1前期整月", 1, 1, true)

    ;

    private String displayName;
    private String displayBgName;
    private Integer delayInterestCalDay;
    private Integer delayRepayDay;
    private Boolean frontFullMonth;

    InterestMethod(String displayName, String displayBgName, Integer delayInterestCalDay, Integer delayRepayDay, Boolean frontFullMonth) {
        this.displayName = displayName;
        this.displayBgName = displayBgName;
        this.delayInterestCalDay = delayInterestCalDay;
        this.delayRepayDay = delayRepayDay;
        this.frontFullMonth = frontFullMonth;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayBgName() {
        return displayBgName;
    }

    public Boolean getFrontFullMonth() {
        return frontFullMonth;
    }

    public Integer getDelayInterestCalDay() {
        return delayInterestCalDay;
    }

    public Integer getDelayRepayDay() {
        return delayRepayDay;
    }

    public static Boolean isTPlusZero(InterestMethod interestMethod) {
        if (interestMethod == InterestMethod.T_PLUS_ZERO || interestMethod == InterestMethod.T_PLUS_ZERO_B) {
            return true;
        }
        return false;
    }
}

