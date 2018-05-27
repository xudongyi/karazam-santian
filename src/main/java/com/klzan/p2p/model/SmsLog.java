package com.klzan.p2p.model;

import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;

/**
 * 短信日志
 */
@Entity
@Table(name = "karazam_sms_log")
public class SmsLog extends BaseModel {

    /**
     * 手机号
     */
    @Column(nullable = false, length = 1200)
    private String mobile;
    /**
     * 内容
     */
    @Column(nullable = false, length = 1000)
    private String cont;
    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SmsType type;
    /**
     * 备注
     */
    @Column(nullable = false, length = 1000)
    private String memo;

    public SmsLog(){}

    public SmsLog(String mobile, String cont, SmsType type, String memo) {
        this.mobile = mobile;
        this.cont = cont;
        this.type = type;
        this.memo = memo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public SmsType getType() {
        return type;
    }

    public void setType(SmsType type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}