/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 中金托管个人信息表 - 中金托管
 *
 * @author: chenxinglin
 */
@Entity
@Table(name = "karazam_cpcn_trustee_info")
public class CpcnPayAccountInfo extends BaseModel {

    /**
     * 用户标识
     */
    @Column
    private Integer userId;

    /**
     * 支付账户号码
     */
    @Column
    private String accountNumber;

    /**
     * 支付账户名称
     */
    @Column
    private String accountUsername;

    /**
     * 支付账户开户状态
     */
    @Column
    private String accountState;

//    /**
//     * 支付账户余额
//     */
//    @Column
//    private BigDecimal accountBalance = BigDecimal.ZERO;
//
//    /**
//     * 绑定卡数量
//     */
//    @Column
//    private Integer bindCardCount = 0;

    /**
     * 支付账户扣款签约号
     */
    @Column
    private String chargeAgreementNo;

    /**
     * 自动投资签约号
     */
    @Column
    private String investAgreementNo;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public String getAccountState() {
        return accountState;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }

    public String getChargeAgreementNo() {
        return chargeAgreementNo;
    }

    public void setChargeAgreementNo(String chargeAgreementNo) {
        this.chargeAgreementNo = chargeAgreementNo;
    }

    public String getInvestAgreementNo() {
        return investAgreementNo;
    }

    public void setInvestAgreementNo(String investAgreementNo) {
        this.investAgreementNo = investAgreementNo;
    }

}