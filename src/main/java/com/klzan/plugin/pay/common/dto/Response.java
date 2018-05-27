package com.klzan.plugin.pay.common.dto;


import payment.api.tx.TxBaseResponse;

/**
 * 支付响应
 *
 * @author: chenxinglin
 */
public class Response {

    protected static final String BUI_SUCCESS_CODE = "000";
    protected static final String BUI_ERROR_CODE = "001";
    protected static final String BUI_PROCCESSING_CODE = "002";

    /**
     * 响应是否成功
     */
    private Boolean success = Boolean.FALSE;

    /**
     *
     */
    private String buiSuccess = BUI_ERROR_CODE;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应消息
     */
    private TxBaseResponse txResponse;

    public Response() {
    }

    public Response(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Response(Boolean success, String msg, String buiSuccess) {
        this.success = success;
        this.msg = msg;
        this.buiSuccess = buiSuccess;
    }

    public Boolean isSuccess() {
        return success && buiSuccess.equals(BUI_SUCCESS_CODE);
    }

    public Boolean isProccessing() {
        return success && buiSuccess.equals(BUI_PROCCESSING_CODE);
    }

    public Boolean isError() {
        return !isSuccess() && !isProccessing();
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setBuiSuccess(String buiSuccess) {
        this.buiSuccess = buiSuccess;
    }

    public String getMsg() {
        return msg;
    }

    public TxBaseResponse getTxResponse() {
        return txResponse;
    }

    public void setTxResponse(TxBaseResponse txResponse) {
        this.txResponse = txResponse;
    }

    public static Response success() {
        return new Response(true, "成功", BUI_SUCCESS_CODE);
    }

    public static Response success(String msg) {
        return new Response(true, msg, BUI_SUCCESS_CODE);
    }

    public static Response proccessing() {
        return new Response(true, "处理中", BUI_PROCCESSING_CODE);
    }

    public static Response proccessing(String msg) {
        return new Response(true, msg, BUI_PROCCESSING_CODE);
    }

    public static Response error() {
        return new Response(false, "失败", BUI_ERROR_CODE);
    }

    public static Response error(String msg) {
        return new Response(false, msg, BUI_ERROR_CODE);
    }

}