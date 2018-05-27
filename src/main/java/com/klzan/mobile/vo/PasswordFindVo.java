package com.klzan.mobile.vo;

import com.klzan.p2p.enums.UserType;

import java.io.Serializable;

/**
 * Created by suhao Date: 2016/12/22 Time: 20:59
 *
 * @version: 1.0
 */
public class PasswordFindVo implements Serializable {
    private String mobile;
    private String password;
    private String smsCode;
    private UserType type;

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

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
