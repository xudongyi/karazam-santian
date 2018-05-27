/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.sms;

import com.klzan.p2p.enums.SmsType;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 短信
 */
public interface SmsService {

    /**
     * 发送短信
     * @param mobile 手机号码，多个手机号使用“,”分割
     * @param model 模板参数
     * @param type 短信类型
     */
    void send(String mobile, Map<String, Object> model, SmsType type) throws Exception ;

    /**
     * 发送短信
     * @param mobile 手机号码，多个手机号使用“,”分割
     * @param content 短信内容
     * @param type 短信类型
     */
    void send(String mobile, String content, SmsType type) throws Exception;

    /**
     * 发送短信验证码
     * @param mobile 手机号码
     * @param model 模板参数
     * @param type 短信类型
     */
    void sendValidate(String mobile, Map<String, Object> model, SmsType type) throws Exception ;

    /**
     * 验证短信验证码
     * @param mobile 手机号码，多个手机号使用“,”分割
     * @param validateCode 验证码
     * @param type 短信类型
     */
    Boolean validate(String mobile, String validateCode, SmsType type) throws Exception;

    /**
     * 发送提现通知短信给管理员
     * @param userMobile
     * @param amount
     */
    void sendToWithdrawMng(String userMobile, BigDecimal amount);

}
