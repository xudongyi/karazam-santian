package com.klzan.p2p.vo.business;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * 环迅接口请求数据封装
 * Created by suhao Date: 2017/4/6 Time: 9:57
 *
 * @version: 1.0
 */
public class Request implements Serializable {
    /**
     * 请求地址
     */
    public String requestUrl;
    /**
     * 请求参数
     */
    public Map parameterMap;

    public Request(String requestUrl, Map parameterMap) {
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
