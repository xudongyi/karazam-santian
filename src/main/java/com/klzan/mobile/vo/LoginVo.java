package com.klzan.mobile.vo;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.ClientType;
import com.klzan.p2p.enums.UserType;

/**
 * Created by zhu on 2016/11/30.
 */
public class LoginVo extends BaseVo {

    private ClientType clientType;
    private UserType type;
    private String loginName;
    private String password;
    private boolean isGesPwd;

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGesPwd() {
        return isGesPwd;
    }

    public void setGesPwd(boolean gesPwd) {
        isGesPwd = gesPwd;
    }
}
