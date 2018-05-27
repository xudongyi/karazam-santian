/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.vo.capital;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.CapitalMethod;
import com.klzan.p2p.enums.CapitalType;
import com.klzan.p2p.enums.UserType;

import java.math.BigDecimal;

/**
 * Created by suhao Date: 2016/11/17 Time: 18:58
 *
 * @version:
 */
public class CapitalVo extends BaseVo {
    /**
     * 类型
     */
    private CapitalType type;
    private String typeStr;

    /**
     * 方式
     *
     */
    private CapitalMethod method;
    private String methodStr;

    /**
     * 收入
     */
    private BigDecimal credit;

    /**
     * 支出
     */
    private BigDecimal debit;

    /**
     * 冻结
     */
    private BigDecimal frozen;

    /**
     * 解冻
     */
    private BigDecimal unfrozen;

    /**
     * 总收入
     */
    private BigDecimal credits;

    /**
     * 总支出
     */
    private BigDecimal debits;

    /**
     * 总冻结
     */
    private BigDecimal frozens;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 备注
     */
    private String memo;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 操作人
     */
    private String operator;

    /**
     * IP
     */
    private String ip;
    private UserType userType;

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
     *   格式化时间
    */
    private String strCreateDate;
    /**
     * 还款专用余额 账号为用户IPS存管账户时返回
     */
    private String repaymentBal;

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

    public CapitalType getType() {
        return CapitalType.valueOf(typeStr);
    }

    public void setType(CapitalType type) {
        this.type = type;
    }

    public String getTypeStr() {
        return CapitalType.valueOf(typeStr).getDisplayName();
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public CapitalMethod getMethod() {
        return CapitalMethod.valueOf(methodStr);
    }

    public void setMethod(CapitalMethod method) {
        this.method = method;
    }

    public String getMethodStr() {
        return CapitalMethod.valueOf(methodStr).getDisplayName();
    }

    public void setMethodStr(String methodStr) {
        this.methodStr = methodStr;
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

    public BigDecimal getUnfrozen() {
        return unfrozen;
    }

    public void setUnfrozen(BigDecimal unfrozen) {
        this.unfrozen = unfrozen;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public BigDecimal getDebits() {
        return debits;
    }

    public void setDebits(BigDecimal debits) {
        this.debits = debits;
    }

    public BigDecimal getFrozens() {
        return frozens;
    }

    public void setFrozens(BigDecimal frozens) {
        this.frozens = frozens;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getStrCreateDate() {
        return strCreateDate;
    }

    public void setStrCreateDate(String strCreateDate) {
        this.strCreateDate = strCreateDate;
    }
}
