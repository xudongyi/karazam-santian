/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.vo.user;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.Child;
import com.klzan.p2p.enums.Marriage;

import java.util.Date;

public class UserInfoVo extends BaseVo{
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号码
     */
    private String idNo;
    /**
     * 身份证签发日期
     */
    private Date idIssueDate;
    /**
     * 身份证到期日期
     */
    private Date idExpiryDate;
    /**
     * QQ号码
     */
    private String qq;
    /**
     * 最高学历
     */
    private String educ;
    /**
     * 毕业院校
     */
    private String univ;
    /**
     * 婚姻状况
     */
    private Marriage marriage;
    private String marriageStr;
    /**
     * 子女情况
     */
    private Child child;
    private String childStr;
    /**
     * 籍贯
     */
    private Integer birthplace;
    /**
     * 户籍
     */
    private Integer domicilePlace;
    /**
     * 居住地
     */
    private Integer abodePlace;
    /**
     * 地址
     */
    private String addr;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 职业状态
     */
    private String occup;
    /**
     * 工作邮箱
     */
    private String workEmail;
    /**
     * 工作手机
     */
    private String workMobile;
    /**
     * 工作电话/公司电话
     */
    private String workPhone;
    /**
     * 工作QQ
     */
    private String workQq;
    /**
     * 有无房产
     */
    private Boolean ownHouse;
    /**
     * 有无房贷
     */
    private Boolean withHouseLoan;

    private Boolean ownCar;
    /**
     * 有无车贷
     */
    private Boolean withCarLoan;
    /**
     * 每月信用卡账单
     */
    private String monthlyCreditCardStatement;
    /**
     * 学历编号
     */
    private String educId;
    /**
     * 职位
     */
    private String post;
    /**
     * 工作年限
     */
    private String workYears;
    /**
     * 月收入
     */
    private String income;
    /**
     * 简介（个人简介、会员简介）
     */
    private String intro;

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

    public Date getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(Date idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public Date getIdExpiryDate() {
        return idExpiryDate;
    }

    public void setIdExpiryDate(Date idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEduc() {
        return educ;
    }

    public void setEduc(String educ) {
        this.educ = educ;
    }

    public String getUniv() {
        return univ;
    }

    public void setUniv(String univ) {
        this.univ = univ;
    }

    public Marriage getMarriage() {
        return marriage;
    }

    public void setMarriage(Marriage marriage) {
        this.marriage = marriage;
    }

    public String getMarriageStr() {
        return marriageStr;
    }

    public void setMarriageStr(String marriageStr) {
        this.marriageStr = marriageStr;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public String getChildStr() {
        return childStr;
    }

    public void setChildStr(String childStr) {
        this.childStr = childStr;
    }

    public Integer getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(Integer birthplace) {
        this.birthplace = birthplace;
    }

    public Integer getDomicilePlace() {
        return domicilePlace;
    }

    public void setDomicilePlace(Integer domicilePlace) {
        this.domicilePlace = domicilePlace;
    }

    public Integer getAbodePlace() {
        return abodePlace;
    }

    public void setAbodePlace(Integer abodePlace) {
        this.abodePlace = abodePlace;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getOccup() {
        return occup;
    }

    public void setOccup(String occup) {
        this.occup = occup;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getWorkMobile() {
        return workMobile;
    }

    public void setWorkMobile(String workMobile) {
        this.workMobile = workMobile;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getWorkQq() {
        return workQq;
    }

    public void setWorkQq(String workQq) {
        this.workQq = workQq;
    }

    public Boolean getOwnHouse() {
        return ownHouse;
    }

    public void setOwnHouse(Boolean ownHouse) {
        this.ownHouse = ownHouse;
    }

    public Boolean getWithHouseLoan() {
        return withHouseLoan;
    }

    public void setWithHouseLoan(Boolean withHouseLoan) {
        this.withHouseLoan = withHouseLoan;
    }

    public Boolean getOwnCar() {
        return ownCar;
    }

    public void setOwnCar(Boolean ownCar) {
        this.ownCar = ownCar;
    }

    public Boolean getWithCarLoan() {
        return withCarLoan;
    }

    public void setWithCarLoan(Boolean withCarLoan) {
        this.withCarLoan = withCarLoan;
    }

    public String getMonthlyCreditCardStatement() {
        return monthlyCreditCardStatement;
    }

    public void setMonthlyCreditCardStatement(String monthlyCreditCardStatement) {
        this.monthlyCreditCardStatement = monthlyCreditCardStatement;
    }

    public String getEducId() {
        return educId;
    }

    public void setEducId(String educId) {
        this.educId = educId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
