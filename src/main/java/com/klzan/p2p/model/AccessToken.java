package com.klzan.p2p.model;

import com.klzan.p2p.enums.ClientType;
import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.model.base.BaseModel;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

/**
 * 访问令牌
 */
@Entity
@Table(name = "karazam_access_token")
public class AccessToken extends BaseModel {

    /** 过期时间（分钟）  */
    public static final int COUNT = 24 * 60;

    /** 用户ID */
    private Integer userId;

    /** 类型 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientType type;

    /**  token */
    @Column(nullable = false,unique = true)
    private String token;

    /** 最后活跃时间 */
    @Column(nullable = false)
    private Date lastActiveDate;

    public AccessToken(){

    }

    public AccessToken(Integer userId, ClientType type, String token) {
        this.userId = userId;
        this.type = type;
        this.token = token;
        this.lastActiveDate = new Date();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(Date lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    /**
     * 验证
     * @param type
     * @param token
     * @return
     */
    @Transient
    public boolean verify(ClientType type, String token) {
        return type!=null && getType().equals(type) && StringUtils.equals(getToken(), token);
    }

}