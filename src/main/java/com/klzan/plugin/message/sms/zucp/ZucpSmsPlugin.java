/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.plugin.message.sms.zucp;

import com.klzan.plugin.message.sms.SmsPlugin;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 漫道短信
 */
@Component("zucpSMSPlugin")
public class ZucpSmsPlugin extends SmsPlugin {

    /** 请求地址 */
    public static String url = "http://115.29.242.32:8888/sms.aspx";

    /** 企业号 */
    public static String userid = "713";

    /** 账号 */
    public static String account = "SHHS";

    /** 密匙 */
    public static String password = "huashanjinrong99";

    @Override
    public String send(String mobile, String content)throws Exception{

        // 参数赋值
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userid", userid)); //企业id
        params.add(new BasicNameValuePair("account", account));//帐号
        params.add(new BasicNameValuePair("password", password));//密码
        params.add(new BasicNameValuePair("mobile", mobile)); //发信发送的目的号码.多个号码之间用半角逗号隔开
        params.add(new BasicNameValuePair("content", content));//短信的内容，内容需要UTF-8编码
        params.add(new BasicNameValuePair("sendTime", null));//定时发送时间 为空表示立即发送，定时发送格式2010-10-24 09:08:10
        params.add(new BasicNameValuePair("action", "send"));//发送任务命令	设置为固定的:send
        params.add(new BasicNameValuePair("extno", null));//扩展子号	请先询问配置的通道是否支持扩展子号，如果不支持，请填空。子号只能为数字，且最多5位数。

        // 提交请求
        String result = post(url, params);
        System.out.println(result);
        return result;

    }

    /**
     * 查询余额与发送量
     * @return 请求返回值
     * @throws Exception
     */
    public String getBalance() throws Exception {

        // 参数赋值
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userid", userid));
        params.add(new BasicNameValuePair("account", account));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("action", "overage"));

        // 提交请求
        String result = post(url, params);
        return result;
    }

    /**
     * 非法关键词检查
     * @param content 待检查内容
     * @return 返回结果
     * @throws Exception
     */
    public String checkContent(String content) throws Exception {

        // 参数赋值
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userid", userid));
        params.add(new BasicNameValuePair("account", account));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("action", "checkkeyword"));
        params.add(new BasicNameValuePair("content", content));

        // 提交请求
        String result = post(url, params);
        return result;
    }

    /**
     * 获取返回报告数据
     * @return 返回报告数据
     * @throws Exception
     */
    public String getReport() throws Exception {

        String url = "http://118.244.214.125:8888/statusApi.aspx";
        // 参数赋值
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userid", userid));
        params.add(new BasicNameValuePair("account", account));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("action", "query"));

        // 提交请求
        String result = post(url, params);
        return result;
    }

    /**
     * 获取上行数据
     * @return 获取上行数据
     * @throws Exception
     */
    public String getMo() throws Exception {

        String url = "http://118.244.214.125:8888/callApi.aspx";
        // 参数赋值
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userid", userid));
        params.add(new BasicNameValuePair("account", account));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("action", "query"));

        // 提交请求
        String result = post(url, params);
        return result;
    }

}