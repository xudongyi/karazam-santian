package com.klzan.core.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http工具类
 * 
 * @author suhao
 * @version 1.0
 */
public class HttpUtils {
    /**
     * GET请求
     * 
     * @param url
     *            URL
     * @return 返回结果
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }
    
    /**
     * GET请求
     * 
     * @param url
     *            URL
     * @param parameterMap
     *            请求参数
     * @return 返回结果
     */
    public static String doGet(String url, Map<String, Object> parameterMap) {
        Assert.hasText(url);
        String result = null;
        HttpClient httpClient = HttpClients.createDefault();
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            if (parameterMap != null) {
                for (Entry<String, Object> entry : parameterMap.entrySet()) {
                    String name = entry.getKey();
                    String value = ConvertUtils.convert(entry.getValue());
                    if (StringUtils.isNotBlank(name)) {
                        nameValuePairs.add(new BasicNameValuePair(name, value));
                    }
                }
            }
            HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?")
                    + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
            EntityUtils.consume(httpEntity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }
    
    /**
     * POST请求
     * 
     * @param url
     *            URL
     * @return 返回结果
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }
    
    /**
     * POST请求
     * 
     * @param url
     *            URL
     * @param parameterMap
     *            请求参数
     * @return 返回结果
     */
    public static String doPost(String url, Map<String, Object> parameterMap) {
        Assert.hasText(url);
        String result = null;
        HttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            if (parameterMap != null) {
                for (Entry<String, Object> entry : parameterMap.entrySet()) {
                    String name = entry.getKey();
                    String value = ConvertUtils.convert(entry.getValue());
                    if (StringUtils.isNotBlank(name)) {
                        nameValuePairs.add(new BasicNameValuePair(name, value));
                    }
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
            EntityUtils.consume(httpEntity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }
}
