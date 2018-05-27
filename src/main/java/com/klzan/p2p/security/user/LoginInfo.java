package com.klzan.p2p.security.user;

import java.io.Serializable;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:23
 *
 * @version:
 */
public class LoginInfo implements Serializable {
    private Integer userId;
    private String loginName;
    private String loginIp;
    private String loginSource;

    public LoginInfo() {
    }

    public LoginInfo(Integer userId, String loginName, String loginIp, String loginSource) {
        this.userId = userId;
        this.loginName = loginName;
        this.loginIp = loginIp;
        this.loginSource = loginSource;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(String loginSource) {
        this.loginSource = loginSource;
    }
}
