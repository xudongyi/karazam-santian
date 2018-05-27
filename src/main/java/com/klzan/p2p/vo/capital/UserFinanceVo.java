/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.vo.capital;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.UserType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户财务信息VO
 * Created by suhao Date: 2016/11/15 Time: 17:03
 *
 * @version: 1.0
 */
public class UserFinanceVo extends BaseVo {
    // 用户基本信息
    /**
     * 关联用户id
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号
     */
    private String idNo;
    /**
     * 注册日期
     */
    private Date registDate;

    // 用户财务信息
    /**
     * 借款总金额
     */
    private BigDecimal borrowingAmts;
    /**
     * 投资总金额
     */
    private BigDecimal investmentAmts;
    /**
     * 充值总金额
     */
    private BigDecimal rechargeAmts;
    /**
     * 提现总金额
     */
    private BigDecimal withdrawalAmts;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 待收金额
     */
    private BigDecimal credit;
    /**
     * 待还金额
     */
    private BigDecimal debit;
    /**
     * 冻结金额
     */
    private BigDecimal frozen;
    /**
     * 体验金
     */
    private BigDecimal experience;
    /**
     * 是否是借款人
     */
    private Boolean isBorrower;

    private UserType type;
    private String typeStr;

    /**
     * 账户余额
     */
    private String curBal;
    /**
     * 可用余额
     */
    private String availBal;
    /**
     * 冻结余额
     */
    private String freezeBal;
    /**
     * 还款专用余额 账号为用户IPS存管账户时返回
     */
    private String repaymentBal;

    private Date queryDate;

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public String getCurBal() {
        return curBal;
    }

    public void setCurBal(String curBal) {
        this.curBal = curBal;
    }

    public String getAvailBal() {
        return availBal;
    }

    public void setAvailBal(String availBal) {
        this.availBal = availBal;
    }

    public String getFreezeBal() {
        return freezeBal;
    }

    public void setFreezeBal(String freezeBal) {
        this.freezeBal = freezeBal;
    }

    public String getRepaymentBal() {
        return repaymentBal;
    }

    public void setRepaymentBal(String repaymentBal) {
        this.repaymentBal = repaymentBal;
    }

    public String getTypeStr() {
        if (StringUtils.isNotBlank(typeStr)) {
            return UserType.valueOf(typeStr).getDisplayName();
        }
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public UserType getType() {
        if (StringUtils.isNotBlank(typeStr)) {
            return UserType.valueOf(typeStr);
        }
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public BigDecimal getBorrowingAmts() {
        return borrowingAmts;
    }

    public void setBorrowingAmts(BigDecimal borrowingAmts) {
        this.borrowingAmts = borrowingAmts;
    }

    public BigDecimal getInvestmentAmts() {
        return investmentAmts;
    }

    public void setInvestmentAmts(BigDecimal investmentAmts) {
        this.investmentAmts = investmentAmts;
    }

    public BigDecimal getRechargeAmts() {
        return rechargeAmts;
    }

    public void setRechargeAmts(BigDecimal rechargeAmts) {
        this.rechargeAmts = rechargeAmts;
    }

    public BigDecimal getWithdrawalAmts() {
        return withdrawalAmts;
    }

    public void setWithdrawalAmts(BigDecimal withdrawalAmts) {
        this.withdrawalAmts = withdrawalAmts;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public BigDecimal getExperience() {
        return experience;
    }

    public void setExperience(BigDecimal experience) {
        this.experience = experience;
    }

    public Boolean getIsBorrower() {
        return isBorrower;
    }

    public void setIsBorrower(Boolean borrower) {
        isBorrower = borrower;
    }
}
