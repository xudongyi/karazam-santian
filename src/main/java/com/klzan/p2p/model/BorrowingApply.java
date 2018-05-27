package com.klzan.p2p.model;

import com.klzan.p2p.enums.BorrowingApplyProgress;
import com.klzan.p2p.enums.BorrowingApplyType;
import com.klzan.p2p.enums.GenderType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 借款申请
 */
@Entity
@Table(name = "karazam_borrowing_apply")
public class BorrowingApply extends BaseModel {

    /**
     * 姓名
     */
    @Column(nullable = false)
    private String userName;
    /**
     * 联系电话
     */
    @Column(nullable = false)
    private String mobile;
    /**
     * 性别
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderType genderType;

    /**
     * 是否为平台客户
     */
    private Boolean isPlatFormUser;

    /**
     * 申请进度
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BorrowingApplyProgress borrowingApplyProgress;

    /**
     * 申请借款类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BorrowingApplyType borrowingApplyType;

    /**
     * 用户反馈信息
     */
    private String suggestion;

    /**
     * 备注
     * @return
     */
    private String remark;

    /**
     * 期望金额，期望贷款期限，期望利率。登陆状态申请借款时填写
     */
    private BigDecimal amount;
    private Integer deadline;
    private Integer rate;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public BorrowingApplyType getBorrowingApplyType() {
        return borrowingApplyType;
    }

    public void setBorrowingApplyType(BorrowingApplyType borrowingApplyType) {
        this.borrowingApplyType = borrowingApplyType;
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

    public GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
    }

    public Boolean getIsPlatFormUser() {
        return isPlatFormUser;
    }

    public void setIsPlatFormUser(Boolean isPlatFormUser) {
        this.isPlatFormUser = isPlatFormUser;
    }

    public BorrowingApplyProgress getBorrowingApplyProgress() {
        return borrowingApplyProgress;
    }

    public void setBorrowingApplyProgress(BorrowingApplyProgress borrowingApplyProgress) {
        this.borrowingApplyProgress = borrowingApplyProgress;
    }
}
