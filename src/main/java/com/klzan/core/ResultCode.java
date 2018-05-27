package com.klzan.core;

public enum ResultCode {
    SUCCESS("000", "请求成功"),
    ERROR("001", "请求失败"),
    PROCCESSING("002", "处理中"),
    UNAUTHORIZED("401", "身份认证错误"),
    FORBIDDEN("403", "拒绝执行此请求"),
    NOT_FOUND("404", "请求找不到资源"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误"),
    BAD_GATEWAY("502", "无效请求"),
    SERVICE_UNAVAILABLE("503", "服务器暂时的无法处理请求"),
    GATEWAY_TIME_OUT("504", "网关超时"),
    SMS_SEND_ERROR("601", "短信发送失败"),
    PROJECT_UNINVEST("602", "项目不可投"),
    NO_PAY_PWD("603", "未设置支付密码"),
    PAY_PWD_ERROR("604", "支付密码不正确"),
    BANLANCE_NOT_SUFFICIENT("605", "余额不足"),
    REALNAME_NOT_AUTH("606", "未实名认证"),
    APP_NOT_LOGIN("666", "未登录"),
    ;

    private String code;
    private String description;

    ResultCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
