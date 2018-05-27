/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.vo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.*;

import java.sql.Timestamp;
import java.util.Date;

public class UserVo extends BaseVo{
    //==========以下为User相关vo===========
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 昵称
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 性别
     */
    private GenderType gender;
    private String genderDisplay;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 图标
     */
    private String icon;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 状态
     */
    private UserStatus status;
    private String statusStr;
    /**
     * 支付密码
     */
    private String payPassword;
    /**
     * 描述
     */
    private String description;
    /**
     * 合资次数
     */
    private Integer loginCount;
    /**
     * 前一次登录时间
     */
    private Timestamp previousVisit;
    /**
     * 上次登录时间
     */
    private Timestamp lastVisit;
    /**
     * 类别
     */
    private UserType type;
    private String typeStr;
    /**
     * 支付账号
     */
    private String payAccountNo;
    /**
     * 企业法人手机号
     */
    private String legalMobile;
    /**
     * 账户开通状态 1 正常、 2 异常
     */
    private String acctStatus;
    /**
     * 身份证审核状态
     * 0 未上传身份证（默认）
     * 1 审核成功、
     * 2 审核拒绝、
     * 3 审核中（已经上传身份证，但是未审核）、
     * 4 未推送审核(已上传,但未发往运管审核)
     */
    private String userCardStatus;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行卡后四位
     */
    private String bankCard;
    /**
     * 代扣签约状态 1 未申请、 2 成功、 3 失败
     */
    private Date queryDate;
    private String repaySignStatus;
    //==========以下为UserInfo相关vo===========
    /**
     * userinfo主键
     */
    private Integer userInfoId;
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
    /**
     * 企业名称
     */
    private String corpName;
    /**
     * 营业执照
     */
    private String corpLicenseNo;
    private String chargeAgreementNo;

    public String getLegalMobile() {
        return legalMobile;
    }

    public void setLegalMobile(String legalMobile) {
        this.legalMobile = legalMobile;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public String getUserCardStatus() {
        return userCardStatus;
    }

    public void setUserCardStatus(String userCardStatus) {
        this.userCardStatus = userCardStatus;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getRepaySignStatus() {
        return repaySignStatus;
    }

    public void setRepaySignStatus(String repaySignStatus) {
        this.repaySignStatus = repaySignStatus;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public GenderType getGender() {
        if (StringUtils.isNotBlank(genderDisplay)){
            return GenderType.valueOf(genderDisplay);
        }
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getGenderDisplay() {
        if (StringUtils.isNotBlank(genderDisplay)){
            return GenderType.valueOf(genderDisplay).getDisplayName();
        }
        return genderDisplay;
    }

    public void setGenderDisplay(String genderDisplay) {
        this.genderDisplay = genderDisplay;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public UserStatus getStatus() {
        if (StringUtils.isNotBlank(statusStr)) {
            return UserStatus.valueOf(statusStr);
        }
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getStatusStr() {
        if (StringUtils.isNotBlank(statusStr)) {
            return UserStatus.valueOf(statusStr).getDisplayName();
        }
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Timestamp getPreviousVisit() {
        return previousVisit;
    }

    public void setPreviousVisit(Timestamp previousVisit) {
        this.previousVisit = previousVisit;
    }

    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
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

    public String getTypeStr() {
        if (StringUtils.isNotBlank(typeStr)) {
            return UserType.valueOf(typeStr).getDisplayName();
        }
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getPayAccountNo() {
        return payAccountNo;
    }

    public void setPayAccountNo(String payAccountNo) {
        this.payAccountNo = payAccountNo;
    }

    public Integer getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Integer userInfoId) {
        this.userInfoId = userInfoId;
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

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpLicenseNo() {
        return corpLicenseNo;
    }

    public void setCorpLicenseNo(String corpLicenseNo) {
        this.corpLicenseNo = corpLicenseNo;
    }

    public String getChargeAgreementNo() {
        return chargeAgreementNo;
    }

    public void setChargeAgreementNo(String chargeAgreementNo) {
        this.chargeAgreementNo = chargeAgreementNo;
    }

    @JsonIgnore
    public float getSecurityLevel() {
        int sum = 0;
        int current = 0;

        // 设置身份证号码
        if (StringUtils.isNotBlank(getIdNo())) {
            current++;
        }
        sum++;

        // 设置手机号码
        if (StringUtils.isNotBlank(getMobile())) {
            current++;
        }
        sum++;

        if (StringUtils.isNotBlank(getPayAccountNo())) {
            current++;
        }
        sum++;

        return (float) current / sum;
    }

    public Boolean hasPayAccount() {
        return StringUtils.isNotBlank(getPayAccountNo());
    }
}
