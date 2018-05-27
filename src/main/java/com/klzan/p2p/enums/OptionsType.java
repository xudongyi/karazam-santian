package com.klzan.p2p.enums;

import com.klzan.p2p.setting.*;

/**
 * 配置类型
 */
public enum OptionsType implements IEnum {

    BASIC_SETTING("基本设置", BasicSetting.class),
    REFERRAL_SETTING("推荐设置", ReferralSetting.class),
    RECHARGE_SETTING("充值设置", RechargeSetting.class),
    WITHDRAW_SETTING("提现设置", WithdrawSetting.class),
    TRANSFER_SETTING("债权转让设置", TransferSetting.class),
    COUPON_SETTING("优惠券设置", CouponSetting.class),
    POSTLOAN_SETTING("还款通知设置", RepaymentNoticeSetting.class),
    POINT_SETTING("积分设置", PointSetting.class),
    AUTO_INVESTMENT_SETTING("自动投标设置", AutoInvestmentSetting.class),
    ;

    private String displayName;
    private Class setting;

    OptionsType(String displayName, Class setting) {
        this.displayName = displayName;
        this.setting = setting;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public Class getSetting() {
        return setting;
    }
}
