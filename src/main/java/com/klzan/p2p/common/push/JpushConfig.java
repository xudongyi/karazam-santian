package com.klzan.p2p.common.push;

/**
 * 极光推送配置
 * Created by suhao Date: 2017/1/9 Time: 11:38
 *
 * @version: 1.0
 */
public class JpushConfig {

    public static String APPKEY;

    public static String MASTER_SECRET;

    public void setAppKey(String appKey) {
        JpushConfig.APPKEY = appKey;
    }

    public void setMasterSecret(String masterSecret) {
        JpushConfig.MASTER_SECRET = masterSecret;
    }
}