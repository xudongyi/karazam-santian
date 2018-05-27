/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.plugin.message.sms.cloudc253;

import com.alibaba.fastjson.JSON;
import com.klzan.plugin.message.sms.SmsPlugin;
import com.klzan.plugin.message.sms.cloudc253.chuanglan.sms.request.SmsSendRequest;
import com.klzan.plugin.message.sms.cloudc253.chuanglan.sms.response.SmsSendResponse;
import com.klzan.plugin.message.sms.cloudc253.chuanglan.sms.util.ChuangLanSmsUtil;
import org.springframework.stereotype.Component;

/**
 * 漫道短信
 */
@Component("cloudC253SmsPlugin")
public class CloudC253SmsPlugin extends SmsPlugin {

    public static final String charset = "utf-8";

    /** 请求地址 */
    public static String url = "http://smssh1.253.com/msg/send/json";

    // 用户平台API账号(非登录账号,示例:N1234567)
    public static String account = "CN1624131";

    // 用户平台API密码(非登录密码)
    public static String password = "bjA5Bh2i8E7865";

    @Override
    public String send(String mobile, String content) throws Exception{

        // 短信内容
        String msg = content;
        //手机号码
        String phone = mobile;

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone);

        String requestJson = JSON.toJSONString(smsSingleRequest);

        System.out.println("before request string is: " + requestJson);

        String response = ChuangLanSmsUtil.sendSmsByPost(url, requestJson);

        System.out.println("response after request result is :" + response);

        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);

        System.out.println("response  toString is :" + smsSingleResponse);


        return "";

    }

}