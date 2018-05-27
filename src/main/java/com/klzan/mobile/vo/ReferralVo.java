package com.klzan.mobile.vo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/6/5 0005.
 */
public class ReferralVo {

    private long creatDate;
    private String reUserMobile;
    private BigDecimal referralFee;


    public long getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(long creatDate) {
        this.creatDate = creatDate;
    }

    public String getReUserMobile() {
        return reUserMobile;
    }

    public void setReUserMobile(String reUserMobile) {
        this.reUserMobile = reUserMobile;
    }

    public BigDecimal getReferralFee() {
        return referralFee;
    }

    public void setReferralFee(BigDecimal referralFee) {
        this.referralFee = referralFee;
    }
}
