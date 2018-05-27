package com.klzan.p2p.enums;

/**
 * 配置数据类型单位
 */
public enum OptionsDataUnit implements IEnum {
    NOUNIT(),
    PERCENT("百分比", "%"),
    DAYS("天数", "天");

    private String displayName;
    private String addonName;

    OptionsDataUnit(String displayName, String addonName) {
        this.displayName = displayName;
        this.addonName = addonName;
    }

    OptionsDataUnit() {

    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getAddonName() {
        return addonName;
    }
}
