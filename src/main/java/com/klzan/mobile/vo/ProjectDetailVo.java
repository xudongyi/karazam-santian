package com.klzan.mobile.vo;

import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.vo.borrowing.BorrowingExtraVo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7 0007.
 */
public class ProjectDetailVo {
    //介绍
    private  String introDes;

    //项目介绍
    private  String intro;
    //用户姓名
    private  String userRealName;
    //借款userType
    private  UserType userType;
    //性别
    private String genderDisplay;
    //年龄
    private  Integer age;
    //身份证号码
    private String idNo;
    //企业名称
    private String corpName;
    //营业执照
    private String corpLicenseNo;
    //历史还清期数
    private  Integer repaidPeriodCount;
    //历史待还期数
    private  Integer repayingPeriodCount;
    //历史逾期期数
    private  Integer overduePeriodCount;
    //借款信息
    private List<BorrowingInfoExtrasVo> borrowingExtraVoList;

    public String getIntroDes() {
        return introDes;
    }

    public void setIntroDes(String introDes) {
        this.introDes = introDes;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<BorrowingInfoExtrasVo> getBorrowingExtraVoList() {
        return borrowingExtraVoList;
    }

    public void setBorrowingExtraVoList(List<BorrowingInfoExtrasVo> borrowingExtraVoList) {
        this.borrowingExtraVoList = borrowingExtraVoList;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getGenderDisplay() {
        return genderDisplay;
    }

    public void setGenderDisplay(String genderDisplay) {
        this.genderDisplay = genderDisplay;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
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

    public Integer getRepaidPeriodCount() {
        return repaidPeriodCount;
    }

    public void setRepaidPeriodCount(Integer repaidPeriodCount) {
        this.repaidPeriodCount = repaidPeriodCount;
    }

    public Integer getRepayingPeriodCount() {
        return repayingPeriodCount;
    }

    public void setRepayingPeriodCount(Integer repayingPeriodCount) {
        this.repayingPeriodCount = repayingPeriodCount;
    }

    public Integer getOverduePeriodCount() {
        return overduePeriodCount;
    }

    public void setOverduePeriodCount(Integer overduePeriodCount) {
        this.overduePeriodCount = overduePeriodCount;
    }
}
