package com.klzan.plugin.pay;

/**
 * Created by suhao on 2017/3/14.
 */
public interface PayPlugin {
    /**
     * 是否支持
     * @return
     */
    Boolean isSupport(IRequest request);

    /**
     * 验证签名
     * @return
     */
    Boolean verifySign(String result);

    /**
     * 验证是否支持
     * @param type
     * @return
     */
    Boolean verifySupport(BusinessType type);

    /**
     * 获取响应结果
     * @param result
     * @return
     */
    IDetailResponse getResponseResult(String result);
}
