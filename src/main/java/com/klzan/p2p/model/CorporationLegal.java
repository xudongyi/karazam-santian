package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 公司法人
 */
@Entity
@Table(name = "karazam_corporation_legal")
public class CorporationLegal extends BaseModel {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 法人手机号
     */
    private String corporationMobile;

    /**
     * 法人姓名
     */
    private String corporationName;

    /**
     * 法人身份证
     */
    private String corporationIdCard;

    public CorporationLegal(){}

    public CorporationLegal(String corporationMobile,String corporationName, String corporationIdCard) {
        this.corporationMobile = corporationMobile;
        this.corporationName = corporationName;
        this.corporationIdCard = corporationIdCard;
    }
    public void update(String corporationMobile,String corporationName, String corporationIdCard) {
        this.corporationMobile = corporationMobile;
        this.corporationName = corporationName;
        this.corporationIdCard = corporationIdCard;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCorporationMobile() {
        return corporationMobile;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public String getCorporationIdCard() {
        return corporationIdCard;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCorporationMobile(String corporationMobile) {
        this.corporationMobile = corporationMobile;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public void setCorporationIdCard(String corporationIdCard) {
        this.corporationIdCard = corporationIdCard;
    }
}
