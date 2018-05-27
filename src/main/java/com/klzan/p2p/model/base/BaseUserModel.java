package com.klzan.p2p.model.base;

import com.klzan.p2p.enums.GenderType;
import com.klzan.p2p.enums.UserStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by suhao on 2016/10/23.
 */
@MappedSuperclass
public abstract class BaseUserModel extends BaseModel {
    /**
     * 登录名
     */
    @Column(nullable = false, length = 20)
    protected String loginName;

    /**
     * 昵称
     */
    @Column(nullable = false, length = 20)
    protected String name;

    /**
     * 密码
     */
    @Column(nullable = false)
    protected String password;

    /**
     * 盐值
     */
    @Column(nullable = false)
    protected String salt;

    /**
     * 生日
     */
    @Column(length = 19)
    protected Date birthday;

    /**
     * 性别
     */
    @Column(length = 20)
    @Enumerated(value = EnumType.STRING)
    protected GenderType gender;

    /**
     * 邮件
     */
    @Column(length = 50)
    protected String email;

    /**
     * 手机号码
     */
    @Column(length = 20)
    protected String mobile;

    /**
     * 图标
     */
    @Column(length = 50)
    protected String icon;

    /**
     * 头像
     */
    @Column(length = 500)
    protected String avatar;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    protected UserStatus status = UserStatus.ENABLE;

    /**
     * 描述
     */
    protected String description;

    /**
     * 登录次数
     */
    protected Integer loginCount;

    /**
     * 前一次次登录时间
     */
    @Column(length = 19)
    protected Timestamp previousVisit;

    /**
     * 上次登录时间
     */
    @Column(length = 19)
    protected Timestamp lastVisit;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public Date getBirthday() {
        return birthday;
    }

    public GenderType getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getIcon() {
        return icon;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public Timestamp getPreviousVisit() {
        return previousVisit;
    }

    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public String getCredentialsSalt() {
        return salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public void setPreviousVisit(Timestamp previousVisit) {
        this.previousVisit = previousVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }

    public void updateSaltAndPassword(String salt, String password) {
        this.salt = salt;
        this.password = password;
    }

    public void updateLoginInfo(Integer loginCount, Timestamp previousVisit, Timestamp lastVisit) {
        this.loginCount = loginCount;
        this.previousVisit = previousVisit;
        this.lastVisit = lastVisit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
