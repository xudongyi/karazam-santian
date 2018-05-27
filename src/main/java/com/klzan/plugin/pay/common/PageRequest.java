package com.klzan.plugin.pay.common;

import java.io.Serializable;
import java.util.Map;

/**
 * 接口请求
 */
public class PageRequest implements Serializable {
    /**
     * 请求地址
     */
    public String requestUrl;
    /**
     * 请求参数
     */
    public Map parameterMap;

    public PageRequest(String requestUrl, Map parameterMap) {
        this.requestUrl = requestUrl;
        this.parameterMap = parameterMap;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public Map getParameterMap() {
        return parameterMap;
    }
}
