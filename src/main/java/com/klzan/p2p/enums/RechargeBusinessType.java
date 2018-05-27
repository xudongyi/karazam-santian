package com.klzan.p2p.enums;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.StringUtils;

/**
 * 充值业务类型
 */
public enum RechargeBusinessType implements IEnum {
    GENERAL("普通充值", "1"),

    REAPYMENT("还款充值", "2");

    private String displayName;
    private String rechargeBusinessType;

    RechargeBusinessType(String displayName, String rechargeBusinessType) {
        this.displayName = displayName;
        this.rechargeBusinessType = rechargeBusinessType;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getRechargeBusinessType() {
        return rechargeBusinessType;
    }

    public static RechargeBusinessType convert(String rechargeBusinessType) {
        for (RechargeBusinessType businessType : values()) {
            if (StringUtils.equals(businessType.getRechargeBusinessType(), rechargeBusinessType)) {
                return businessType;
            }
        }
        throw new BusinessProcessException("充值业务类型转换错误");
    }
}