/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.enums.GenderType;
import com.klzan.p2p.enums.OtherPlatform;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 用户第三方平台账号
 * Created by suhao Date: 2016/11/3 Time: 14:53
 *
 * @version: 1.0
 */
@Entity
@Table(name = "karazam_user_third_account")
public class UserThirdAccount extends BaseModel {
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    @Enumerated(EnumType.STRING)
    private GenderType sex;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的
     */
    private String unionid;
    /**
     * 授权用户唯一标识
     */
    private String openid;
    /**
     * 接口调用凭证
     */
    private String accessToken;
    /**
     * access_token接口调用凭证超时时间，单位（秒）
     */
    private String expiresIn;
    /**
     * 用户刷新access_token
     */
    private String refreshToken;
    /**
     * 第三方平台
     */
    @Enumerated(EnumType.STRING)
    private OtherPlatform platform;

    /**
     * 平台内用户ID
     */
    private Integer userId;

    public UserThirdAccount() {
    }

    public UserThirdAccount(String nickname, String avatar, GenderType sex, String country, String province, String city, String unionid, String openid, String accessToken, OtherPlatform platform) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.sex = sex;
        this.country = country;
        this.province = province;
        this.city = city;
        this.unionid = unionid;
        this.openid = openid;
        this.accessToken = accessToken;
        this.platform = platform;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public GenderType getSex() {
        return sex;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getUnionid() {
        return unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public OtherPlatform getPlatform() {
        return platform;
    }

    public Integer getUserId() {
        return userId;
    }

    public void updateUserId(Integer userId) {
        this.userId = userId;
    }
}
