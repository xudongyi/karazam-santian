package com.klzan.mobile.vo;

import com.klzan.p2p.enums.ClientType;
import com.klzan.p2p.enums.UserType;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public class modifyPasswordVo {

     private String newPassword;
     private String oldPassword;
     private ClientType clientType;
     private UserType type;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

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
}
