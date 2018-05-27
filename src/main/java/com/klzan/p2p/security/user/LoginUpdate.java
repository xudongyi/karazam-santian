package com.klzan.p2p.security.user;

import java.io.Serializable;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:23
 *
 * @version:
 */
public class LoginUpdate implements Serializable {
    private Integer userId;
    private String password;

    public LoginUpdate() {
    }

    public LoginUpdate(Integer userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
