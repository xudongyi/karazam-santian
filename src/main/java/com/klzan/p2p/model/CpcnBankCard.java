/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 银行卡 - 中金托管
 *
 * @author: chenxinglin
 */
@Entity
@Table(name = "karazam_cpcn_bank_card")
public class CpcnBankCard extends BaseModel {

    /**
     * 用户标识
     */
    @Column
    private Integer userId;

    /**
     * 订单号
     */
    @Column
    private String orderNo;

    /**
     * 银行ID
     */
    @Column
    private String bankID;

    /**
     * 银行账户号码
     */
    @Column
    private String bankAccountNumber;

    /**
     * 银行绑定流水号
     */
    @Column
    private String bindingSystemNo;

    /**
     * 支付账户开户状态
     */
    @Column
    private String status;

    @Transient
    private String bankLogo;

    @Transient
    private String bankName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBindingSystemNo() {
        return bindingSystemNo;
    }

    public void setBindingSystemNo(String bindingSystemNo) {
        this.bindingSystemNo = bindingSystemNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}