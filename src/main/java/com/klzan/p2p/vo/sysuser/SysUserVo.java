package com.klzan.p2p.vo.sysuser;

import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.GenderType;
import com.klzan.p2p.enums.UserStatus;

import java.sql.Timestamp;
import java.util.Date;

public class SysUserVo extends BaseVo {
     /** serialVersionUID */
    private static final long serialVersionUID = -3703154275817120085L;

    private String loginName;
    private String name;
    private String password;
    private String salt;
    private String birthday;
    private Date birthdayDate;
    private GenderType gender;
    private String genderDisplay;
    private String email;
    private String mobile;
    private String icon;
    private UserStatus status;
    private String statusStr;
    private String description;
    private Integer loginCount;
    private Timestamp previousVisit;
    private Timestamp lastVisit;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Date getBirthdayDate() {
        if (StringUtils.isNotBlank(birthday)) {
            birthdayDate = DateUtils.parseToDate(birthday);
        }
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getGenderDisplay() {
        return GenderType.valueOf(genderDisplay).getDisplayName();
    }

    public void setGenderDisplay(String genderDisplay) {
        this.genderDisplay = genderDisplay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getStatusStr() {
        if (StringUtils.isNotBlank(statusStr)) {
            return UserStatus.valueOf(statusStr).getDisplayName();
        }
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Timestamp getPreviousVisit() {
        return previousVisit;
    }

    public void setPreviousVisit(Timestamp previousVisit) {
        this.previousVisit = previousVisit;
    }

    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }
}
