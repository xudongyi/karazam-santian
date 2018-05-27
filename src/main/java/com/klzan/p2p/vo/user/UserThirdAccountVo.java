/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.vo.user;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.GenderType;
import com.klzan.p2p.enums.OtherPlatform;
import com.klzan.p2p.enums.UserType;

/**
 * 用户第三方信息VO
 */
public class UserThirdAccountVo extends BaseVo {
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
     * 第三方平台
     */
    private OtherPlatform platform;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户类型
     */
    private UserType type;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public GenderType getSex() {
        return sex;
    }

    public void setSex(GenderType sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public OtherPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(OtherPlatform platform) {
        this.platform = platform;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
