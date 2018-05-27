package com.klzan.mobile.vo;

import com.klzan.p2p.enums.ClientType;

import java.io.Serializable;

/**
 * Created by suhao Date: 2016/12/21 Time: 10:04
 *
 * @version: 1.0
 */
public class UserGesPasswordVo implements Serializable {
    private String gesPassword;
    private Integer userId;
    private ClientType clientType;
    public UserGesPasswordVo() {
    }

    public UserGesPasswordVo(String gesPassword, Integer userId) {
        this.gesPassword = gesPassword;
        this.userId = userId;
    }

    public String getGesPassword() {
        return gesPassword;
    }

    public void setGesPassword(String gesPassword) {
        this.gesPassword = gesPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}
