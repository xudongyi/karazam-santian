package com.klzan.p2p.vo.user;

import com.klzan.p2p.enums.UserType;

import java.io.Serializable;

/**
 * Created by suhao Date: 2017/4/7 Time: 16:31
 *
 * @version: 1.0
 */
public class UserIdentityVo implements Serializable {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户类型
     */
    private UserType type;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号码
     */
    private String idNo;
    /**
     * 公司名称
     */
    private String corpName;
    /**
     * 公司营业执照
     */
    private String corpLicenseNo;
    /**
     * 法人手机号
     */
    private String ipsMobile;

    public String getIpsMobile() {
        return ipsMobile;
    }

    public void setIpsMobile(String ipsMobile) {
        this.ipsMobile = ipsMobile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpLicenseNo() {
        return corpLicenseNo;
    }

    public void setCorpLicenseNo(String corpLicenseNo) {
        this.corpLicenseNo = corpLicenseNo;
    }
}
