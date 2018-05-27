/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.mobile.vo;

import com.klzan.p2p.enums.ClientType;
import com.klzan.p2p.enums.UserType;

import java.io.Serializable;

/**
 * VO用户基类
 * Created by suhao Date: 2016/10/20 Time: 14:52
 *
 * @version: 1.0
 */
public class UserBaseVo implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = -3703154275817120085L;
    /**
     * 用户Id
     */
    private Integer userId;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户加密标识
     */
    private String userKey;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户类型
     */
    private UserType type;
    /**
     * 类型
     */
    private ClientType clientType;

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

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}
