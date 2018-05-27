package com.klzan.p2p.enums;

public enum MessagePushType implements IEnum {
    new_project("新标通知"),

    repayment("还款通知"),

    system("系统通知"),

    userown("我的消息"),

    logout("下线通知"),
    ;

    private String displayName;

    MessagePushType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}