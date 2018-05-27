package com.klzan.p2p.model;

import com.klzan.p2p.enums.UserLogType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "karazam_user_log")
public class UserLog extends BaseModel {
    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private UserLogType type;
    /**
     * 内容
     */
    @Column(nullable = false)
    private String cont;
    /**
     * 是否通过
     */
    @Column(updatable = false)
    private Boolean approved;
    /**
     * 操作员
     */
    @Column(updatable = false)
    private String operator;
    /**
     * 操作员
     */
    @Column(updatable = false)
    private String ip;
    /**
     * 会员
     */
    @Column(nullable = false, updatable = false)
    private Integer userId;

    public UserLog(){

    }

    public UserLog(UserLogType type, String cont, String operator, String ip, Integer userId) {
        this.type = type;
        this.cont = cont;
        this.operator = operator;
        this.ip = ip;
        this.userId = userId;
    }

    public UserLogType getType() {
        return type;
    }

    public String getCont() {
        return cont;
    }

    public Boolean getApproved() {
        return approved;
    }

    public String getOperator() {
        return operator;
    }

    public String getIp() {
        return ip;
    }

    public Integer getUserId() {
        return userId;
    }
}