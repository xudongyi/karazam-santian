package com.klzan.plugin.pay.ips;

import com.klzan.core.util.BeanUtils;
import org.apache.commons.collections.map.HashedMap;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by suhao Date: 2017/3/14 Time: 14:55
 *
 * @version: 1.0
 */
public class IpsPayPluginRequest implements Serializable {
    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 商户存管交易账号
     */
    private String merchantID;
    /**
     * 签名
     */
    private String sign;
    /**
     * 请求信息
     */
    private String request;

    public Map<String, Object> getRequestParams() {
        Map<String, Object> params = new HashedMap();
        BeanUtils.copyBean2Map(params, this);
        return params;
    }
}
