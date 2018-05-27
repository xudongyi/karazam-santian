/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户设备
 * Created by suhao Date: 2017/01/09
 *
 * @version: 1.0
 */
@Entity
@Table(name = "karazam_user_device")
public class UserDevice extends BaseModel {
    public static final String ANDROID = "ANDROID";
    public static final String IOS = "IOS";
    /**
     * 关联用户
     */
    private Integer userId;
    /**
     * 设备标识
     */
    private String registrationId;
    /**
     * 设备系统类型(android/ios)
     */
    private String osType = ANDROID;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }
}
