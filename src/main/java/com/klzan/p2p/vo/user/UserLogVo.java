package com.klzan.p2p.vo.user;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.UserLogType;

/**
 * Created by zhutao on 2017/2/16.
 */
public class UserLogVo extends BaseVo {
    /**
     * 类型
     */
    private UserLogType type;
    private String typeStr;
    /**
     * 内容
     */
    private String cont;
    /**
     * 是否通过
     */
    private Boolean approved;
    /**
     * 操作员
     */
    private String operator;
    /**
     * ip
     */
    private String ip;
    /**
     * 会员
     */
    private Integer userId;

    private String loginName;

    private String mobile;

    public UserLogType getType() {
        return type;
    }

    public void setType(UserLogType type) {
        this.type = type;
    }

    public String getTypeStr() {
        return type.getDisplayName();
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
