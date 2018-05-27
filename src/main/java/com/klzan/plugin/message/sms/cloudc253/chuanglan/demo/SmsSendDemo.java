package com.klzan.plugin.message.sms.cloudc253.chuanglan.demo;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.klzan.plugin.message.sms.cloudc253.chuanglan.sms.request.SmsSendRequest;
import com.klzan.plugin.message.sms.cloudc253.chuanglan.sms.response.SmsSendResponse;
import com.klzan.plugin.message.sms.cloudc253.chuanglan.sms.util.ChuangLanSmsUtil;

/**
 * 
 * @author tianyh 
 * @date 2017年4月15日 下午3:26:25
 * @Title: ChuangLanSmsDemo
 * @ClassName: ChuangLanSmsDemo
 * @Description:普通短信发送
 */
public class SmsSendDemo {

	public static final String charset = "utf-8";
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = "N5406617";
	// 用户平台API密码(非登录密码)
	public static String pswd = "P0zrGxfu1l5192";

	public static void main(String[] args) throws UnsupportedEncodingException {

		// 普通短信地址
		String smsSingleRequestServerUrl = "http://vsms.253.com/msg/send/json";
		// 短信内容
	    String msg = "省道11";
		//手机号码
		String phone = "18628188236";
		
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone);
		
		String requestJson = JSON.toJSONString(smsSingleRequest);
		
		System.out.println("before request string is: " + requestJson);
		
		String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
		
		System.out.println("response after request result is :" + response);
		
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		
		System.out.println("response  toString is :" + smsSingleResponse);
		
	
		
	
	}




}
