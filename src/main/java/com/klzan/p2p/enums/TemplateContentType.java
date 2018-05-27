package com.klzan.p2p.enums;

/**
 * 内容模板类型
 */
public enum TemplateContentType implements IEnum {
    SMS("短信"),
    EMAIL("邮件");

    private String displayName;

    TemplateContentType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}
