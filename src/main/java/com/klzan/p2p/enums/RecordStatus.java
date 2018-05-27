package com.klzan.p2p.enums;

/**
 * 记录状态
 */
public enum RecordStatus implements IEnum {

    NEW_CREATE("交易申请"),

    PROCESSING("交易处理中"),

    SUCCESS("交易成功"),

    FAILURE("交易失败");

    private String displayName;

    RecordStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}