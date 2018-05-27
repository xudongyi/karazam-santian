package com.klzan.mobile.vo;

import com.klzan.p2p.enums.SmsType;

import java.io.Serializable;

/**
 * Created by suhao Date: 2016/12/22 Time: 19:39
 *
 * @version: 1.0
 */
public class SendSmsVo implements Serializable {
    private String mobile;
    private SmsType smsType;
    private String smsCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public SmsType getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsType smsType) {
        this.smsType = smsType;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
