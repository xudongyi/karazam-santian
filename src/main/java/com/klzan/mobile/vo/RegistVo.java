package com.klzan.mobile.vo;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.ClientType;
import com.klzan.p2p.enums.UserType;

import java.util.Date;

/**
 * Created by zhu on 2016/11/30.
 */
public class RegistVo extends BaseVo {

    private ClientType clientType;
    private UserType type;
    private String mobile;
    private String password;
    private String captcha;
    private String referrer;
    private String smsCode;

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

}
