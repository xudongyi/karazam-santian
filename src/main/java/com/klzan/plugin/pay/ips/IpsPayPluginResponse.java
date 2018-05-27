package com.klzan.plugin.pay.ips;

import com.klzan.core.util.StringUtils;
import com.klzan.plugin.pay.IResponse;

/**
 * Created by suhao Date: 2017/3/14 Time: 14:55
 *
 * @version: 1.0
 */
public class IpsPayPluginResponse implements IResponse {
    /**
     * 响应码 000000 成功 999999 失败
     */
    private String resultCode;
    /**
     * 响应信息
     */
    private String resultMsg;
    /**
     * 商户存管交易账号
     */
    private String merchantID;
    /**
     * 签名
     */
    private String sign;
    /**
     * 响应信息
     */
    private String response;

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public String getSign() {
        return sign;
    }

    public String getResponse() {
        return response;
    }

    public String getMsg() {
        if (StringUtils.contains(resultMsg, "|")) {
            return StringUtils.substringAfter(resultMsg, "|");
        }
        return resultMsg;
    }
}
