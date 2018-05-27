package com.klzan.p2p.enums;

/**
 * 订单状态
 */
public enum PaymentOrderStatus implements IEnum {

    NEW_CREATE("交易申请"),

    WAITING_PROCESS("待处理"),

    PROCESSING("处理中"),

    EXPIRED("已超时"),

    CANCEL("已取消"),

    FAILURE("已失败"),

    SUCCESS("已成功");

    private String displayName;

    PaymentOrderStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}