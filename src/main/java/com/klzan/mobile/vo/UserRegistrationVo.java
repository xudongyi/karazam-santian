package com.klzan.mobile.vo;

import java.io.Serializable;

/**
 * Created by suhao Date: 2016/12/21 Time: 10:04
 *
 * @version: 1.0
 */
public class UserRegistrationVo implements Serializable {
    private Integer userId;
    // 设备标识
    private String registrationId;
    private String osType;

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
