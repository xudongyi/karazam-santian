package com.klzan.p2p.vo.borrowing;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.BorrowingApplyProgress;
import com.klzan.p2p.enums.BorrowingApplyType;
import com.klzan.p2p.enums.GenderType;

/**
 * Created by zhutao on 2017/5/3.
 */
public class BorrowingApplyVo extends BaseVo{

    private String type;

    private String userName;

    private String mobile;

    private GenderType genderTypeEnum;
    private String genderType;

    private String imgCode;

    private BorrowingApplyType borrowingApplyTypeEnum;
    private String borrowType;

    private BorrowingApplyProgress borrowingApplyProgressEnum;
    private String borrowingApplyProgress;

    private Boolean isPlatFormUser;

    private String suggestion;

    private String remark;

    /*短信验证码*/
    private String captcha;

    /**
     * 用户ID，期望金额，期望贷款期限，期望利率。在登陆状态申请贷款时可用
     */
    private String userId;
    private String amount;
    private Integer deadline;
    private Integer rate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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

    public BorrowingApplyType getBorrowingApplyTypeEnum() {
        if (StringUtils.isNotBlank(borrowType)){
            return BorrowingApplyType.valueOf(borrowType);
        }
        return borrowingApplyTypeEnum;
    }

    public void setBorrowingApplyTypeEnum(BorrowingApplyType borrowingApplyTypeEnum) {
        this.borrowingApplyTypeEnum = borrowingApplyTypeEnum;
    }
    public String getBorrowType() {
        if (StringUtils.isNotBlank(borrowType)){
            return BorrowingApplyType.valueOf(borrowType).getDisplayName();
        }
        return borrowType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public Boolean getIsPlatFormUser() {
        return isPlatFormUser;
    }

    public void setIsPlatFormUser(Boolean isPlatFormUser) {
        this.isPlatFormUser = isPlatFormUser;
    }

    public GenderType getGenderTypeEnum() {
        if (StringUtils.isNotBlank(genderType)){
            return GenderType.valueOf(genderType);
        }
        return genderTypeEnum;
    }
    public void setGenderTypeEnum(GenderType genderTypeEnum) {
        this.genderTypeEnum = genderTypeEnum;
    }
    public String getGenderType() {
        if (StringUtils.isNotBlank(genderType)){
            return GenderType.valueOf(genderType).getDisplayName();
        }
        return genderType;
    }
    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    public BorrowingApplyProgress getBorrowingApplyProgressEnum() {
        if (StringUtils.isNotBlank(borrowingApplyProgress)){
            return BorrowingApplyProgress.valueOf(borrowingApplyProgress);
        }
        return borrowingApplyProgressEnum;
    }

    public void setBorrowingApplyProgressEnum(BorrowingApplyProgress borrowingApplyProgressEnum) {
        this.borrowingApplyProgressEnum = borrowingApplyProgressEnum;
    }

    public String getBorrowingApplyProgress() {
        if (StringUtils.isNotBlank(borrowingApplyProgress)){
            return BorrowingApplyProgress.valueOf(borrowingApplyProgress).getDisplayName();
        }
        return borrowingApplyProgress;
    }

    public void setBorrowingApplyProgress(String borrowingApplyProgress) {
        this.borrowingApplyProgress = borrowingApplyProgress;
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

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

}
