/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.setting;

import java.io.Serializable;

/**
 * Setting - 还款通知设置
 */
public class RepaymentNoticeSetting implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 982827572983485140L;

    /** 通知标的负责人 */
    private Boolean noticePrincipal;

    /** 通知投资人 */
    private Boolean noticeInvestor;

    /** 开启短信提醒 */
    private Boolean openSMS;

    /** 还款前提醒时间 */
    private Integer aheadDays;

    /** 还款当天提醒 */
    private Boolean currentDay;

    /** 逾期提醒 */
    private Integer overdueDays;

    public Boolean getOpenSMS() {
        return openSMS;
    }

    public void setOpenSMS(Boolean openSMS) {
        this.openSMS = openSMS;
    }

    public Integer getAheadDays() {
        return aheadDays;
    }

    public void setAheadDays(Integer aheadDays) {
        this.aheadDays = aheadDays;
    }

    public Boolean getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Boolean currentDay) {
        this.currentDay = currentDay;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public Boolean getNoticePrincipal() {
        return noticePrincipal;
    }

    public void setNoticePrincipal(Boolean noticePrincipal) {
        this.noticePrincipal = noticePrincipal;
    }

    public Boolean getNoticeInvestor() {
        return noticeInvestor;
    }

    public void setNoticeInvestor(Boolean noticeInvestor) {
        this.noticeInvestor = noticeInvestor;
    }
}