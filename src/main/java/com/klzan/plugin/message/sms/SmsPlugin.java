/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.plugin.message.sms;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.List;

/**
 * 短信
 *
 * @author: chenxinglin  Date: 2016/11/3 Time: 15:48
 */
public abstract class SmsPlugin {

    /**
     * 短信发送
     *
     * @param mobile 手机号码
     * @param content 短信内容
     */
    public abstract String send(String mobile, String content)throws Exception;

    /**
     * POST请求
     *
     * @param url URL
     * @param params  请求参数
     * @return 返回结果
     */
    protected String post(String url, List<NameValuePair> params) throws Exception{

        // 结果
        String result = null;

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
        httpPost.setEntity(uefEntity);

        // 设置请求与数据处理的超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(20000).build();
        httpPost.setConfig(requestConfig);

        // 提交请求
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity, "UTF-8");
        } else {
            httpPost.releaseConnection();
            throw new Exception();
        }
        httpPost.releaseConnection();
        return result;

    }

}