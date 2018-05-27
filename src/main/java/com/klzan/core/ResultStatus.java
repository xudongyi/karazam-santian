package com.klzan.core;

/**
 * 消息状态
 */
public enum ResultStatus {

    SUCCESS("success", "操作成功"),
    PROCESSING("proccessing", "处理中"),
    ERROR("error", "操作失败");

    private String status;
    private String description;

    ResultStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
