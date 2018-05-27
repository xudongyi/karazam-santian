/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.setting;

import java.io.Serializable;

/**
 * Setting - 基本设置
 */
public class BasicSetting implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 982827572983485140L;

    /** 网站名称 */
    private String siteName;

    /** 网站网址 */
    private String siteUrl;

    /** 网站LOGO */
    private String siteLogo;

    /** 备案编号 */
    private String siteCertifate;

    /** 网站是否开启 */
    private Boolean siteEnabled;

    /** 联系地址 */
    private String serviceContactAddr;

    /** 联系电话 */
    private String servicePhone;

    /** 邮箱地址 */
    private String serviceEmail;
    
    /** 服务时间 */
    private String serviceTime;

    /** 占位时间 */
    private Integer investOccupyTime;

    /** 提现通知管理员 */
    private String siteWithdrawNotice;

    /** 公司名称 */
    private String siteCorpName;

    /** 上线时间 */
    private String siteOnlineDay;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSiteLogo() {
        return siteLogo;
    }

    public void setSiteLogo(String siteLogo) {
        this.siteLogo = siteLogo;
    }

    public String getSiteCertifate() {
        return siteCertifate;
    }

    public void setSiteCertifate(String siteCertifate) {
        this.siteCertifate = siteCertifate;
    }

    public Boolean getSiteEnabled() {
        return siteEnabled;
    }

    public void setSiteEnabled(Boolean siteEnabled) {
        this.siteEnabled = siteEnabled;
    }

    public String getServiceContactAddr() {
        return serviceContactAddr;
    }

    public void setServiceContactAddr(String serviceContactAddr) {
        this.serviceContactAddr = serviceContactAddr;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getServiceEmail() {
        return serviceEmail;
    }

    public void setServiceEmail(String serviceEmail) {
        this.serviceEmail = serviceEmail;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Integer getInvestOccupyTime() {
        return investOccupyTime;
    }

    public void setInvestOccupyTime(Integer investOccupyTime) {
        this.investOccupyTime = investOccupyTime;
    }

    public String getSiteWithdrawNotice() {
        return siteWithdrawNotice;
    }

    public void setSiteWithdrawNotice(String siteWithdrawNotice) {
        this.siteWithdrawNotice = siteWithdrawNotice;
    }

    public String getSiteCorpName() {
        return siteCorpName;
    }

    public void setSiteCorpName(String siteCorpName) {
        this.siteCorpName = siteCorpName;
    }

    public String getSiteOnlineDay() {
        return siteOnlineDay;
    }

    public void setSiteOnlineDay(String siteOnlineDay) {
        this.siteOnlineDay = siteOnlineDay;
    }
}