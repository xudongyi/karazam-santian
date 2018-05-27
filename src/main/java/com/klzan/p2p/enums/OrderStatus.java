package com.klzan.p2p.enums;

/**
 * 平台订单状态
 */
public enum OrderStatus implements IEnum {

    LAUNCH("发起"),

    FROZEN("冻结"),

    AUDITING("审核中"),

    PROCESSING("平台处理中"),

    THIRD_PROCESSING("第三方处理中"),

    SUCCESS("成功"),

    FAILURE("失败"),

    RESCIND("撤销"),
    ;

    private String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}