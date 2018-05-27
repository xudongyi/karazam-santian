package com.klzan.p2p.enums;

/**
 * 配置数据类型
 */
public enum OptionsDataType implements IEnum {
    JSON("JSON字符串"),
    DATE("日期"),
    DATETIME("日期时间"),
    IMAGE("图片"),
    EMAIL("邮箱"),
    STRING("字符串"),
    INTEGER("整数"),
    DOUBLE("浮点数"),
    BOOLEAN("布尔值");

    private String displayName;
    private String addonName;

    OptionsDataType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getAddonName() {
        return addonName;
    }
}
