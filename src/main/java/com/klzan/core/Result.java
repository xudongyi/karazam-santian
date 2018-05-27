package com.klzan.core;

import org.apache.commons.collections.map.HashedMap;

import java.io.Serializable;

public class Result implements Serializable {
    /**
     * serialVersionUID
     */
    protected static final long serialVersionUID = -3703154275817120085L;
    protected static final String DEFAULT_SUCCESS_CODE = ResultCode.SUCCESS.getCode();
    protected static final String DEFAULT_ERROR_CODE = ResultCode.ERROR.getCode();
    protected static final String DEFAULT_PROCCESSING_CODE = ResultCode.PROCCESSING.getCode();
    protected static final String DEFAULT_SUCCESS_MSG = "操作成功";
    protected static final String DEFAULT_ERROR_MSG = "操作失败";
    protected static final String DEFAULT_PROCCESSING_MSG = "处理中";

    protected boolean success = false;
    protected String code;
    protected String status;
    protected String message;
    protected Object data;

    public Result() {
        this.success();
    }

    private Result(ResultStatus status, String code, String message, Object data) {
        this.success = status == ResultStatus.SUCCESS ? true : false;
        this.code = code;
        this.status = status.getStatus();
        this.message = message;
        this.data = data == null ? new HashedMap() : data;
    }

    public static Result success() {
        return new Result(ResultStatus.SUCCESS, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG, null);
    }

    public static <T> Result success(T message) {
        if (message instanceof String) {
            return new Result(ResultStatus.SUCCESS, DEFAULT_SUCCESS_CODE, (String) message, null);
        }
        return new Result(ResultStatus.SUCCESS, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG, message);
    }

    public static Result success(String message, Object data) {
        return new Result(ResultStatus.SUCCESS, DEFAULT_SUCCESS_CODE, message, data);
    }

    public static Result proccessing() {
        return new Result(ResultStatus.PROCESSING, DEFAULT_PROCCESSING_CODE, DEFAULT_PROCCESSING_MSG, null);
    }

    public static <T> Result proccessing(T message) {
        if (message instanceof String) {
            return new Result(ResultStatus.PROCESSING, DEFAULT_PROCCESSING_CODE, (String) message, null);
        }
        return new Result(ResultStatus.PROCESSING, DEFAULT_PROCCESSING_CODE, DEFAULT_PROCCESSING_MSG, message);
    }

    public static Result proccessing(String message, Object data) {
        return new Result(ResultStatus.PROCESSING, DEFAULT_PROCCESSING_CODE, message, data);
    }

    public static Result error(ResultCode resultCode, String message) {
        return new Result(ResultStatus.ERROR, resultCode.getCode(), message, null);
    }

    public static Result error(String message, Object data) {
        return new Result(ResultStatus.ERROR, DEFAULT_ERROR_CODE, message, data);
    }

    public static Result error(String message) {
        return new Result(ResultStatus.ERROR, DEFAULT_ERROR_CODE, message, null);
    }

    public static Result error(ResultCode resultCode) {
        return new Result(ResultStatus.ERROR, resultCode.getCode(), resultCode.getDescription(), null);
    }

    public static Result error() {
        return new Result(ResultStatus.ERROR, DEFAULT_ERROR_CODE, DEFAULT_ERROR_MSG, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isProccessing() {
        return code.equals(DEFAULT_PROCCESSING_CODE);
    }

    public boolean isError() {
        return code.equals(DEFAULT_ERROR_CODE);
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

}
