package com.klzan.plugin.pay.ips;

/**
 * 环迅支付配置
 * Created by suhao Date: 2017/3/14 Time: 15:03
 *
 * @version: 1.0
 */
public class IpsPayConfig {
    /**
     * 商户存管交易账号
     */
    public static String MERCHANT_ID;
    /**
     * MD5证书
     */
    public static String CERT_MD5;
    /**
     * 密钥
     */
    public static String DES_KEY;
    /**
     * 向量
     */
    public static String DES_VCTOR;
    /**
     * 接口地址
     */
    public static String REQUEST_URL;
    /**
     * 登录地址
     */
    public static String LOGIN_URL;
    /**
     * 同步通知地址
     */
    public static String SYNC_URL;
    /**
     * 异步通知地址
     */
    public static String ASYNC_URL;
    /**
     * 用户名前缀
     */
    public static String USER_NAME_PREFIX;

    public void setMerchantId(String merchantId) {
        IpsPayConfig.MERCHANT_ID = merchantId;
    }

    public void setCertMd5(String certMd5) {
        IpsPayConfig.CERT_MD5 = certMd5;
    }

    public void setDesKey(String desKey) {
        IpsPayConfig.DES_KEY = desKey;
    }

    public void setDesVctor(String desVctor) {
        IpsPayConfig.DES_VCTOR = desVctor;
    }

    public void setRequestUrl(String requestUrl) {
        IpsPayConfig.REQUEST_URL = requestUrl;
    }

    public void setLoginUrl(String loginUrl) {
        IpsPayConfig.LOGIN_URL = loginUrl;
    }

    public void setSyncUrl(String syncUrl) {
        IpsPayConfig.SYNC_URL = syncUrl;
    }

    public void setAsyncUrl(String asyncUrl) {
        IpsPayConfig.ASYNC_URL = asyncUrl;
    }

    public void setUserNamePrefix(String userNamePrefix) {
        IpsPayConfig.USER_NAME_PREFIX = userNamePrefix;
    }
}
