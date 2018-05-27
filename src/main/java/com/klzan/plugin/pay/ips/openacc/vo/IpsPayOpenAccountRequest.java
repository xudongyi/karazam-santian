package com.klzan.plugin.pay.ips.openacc.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.ips.AbstractRequest;

/**
 * Created by suhao Date: 2017/3/14 Time: 16:20
 *
 * @version: 1.0
 */
public class IpsPayOpenAccountRequest extends AbstractRequest {
    /**
     * 用户类型 1-个人 2-企业
     */
    private String userType;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 手机号
     */
    private String mobileNo;
    /**
     * 身份证号
     */
    private String identNo;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 企业名称 用户类型为企业必填
     */
    private String enterName;
    /**
     * 营业执照编码 用户类型为企业必填
     */
    private String orgCode;
    /**
     * 是否为担保企业 1-是 0-否
     */
    private String isAssureCom;

    public IpsPayOpenAccountRequest(String merBillNo, String merDate, String userType, String userName, String mobileNo, String identNo, String realName, String enterName, String orgCode, String isAssureCom) {
        this.merBillNo = merBillNo;
        this.merDate = merDate;
        this.userType = userType;
        this.userName = userName;
        this.mobileNo = mobileNo;
        this.identNo = identNo;
        this.realName = realName;
        this.enterName = enterName;
        this.orgCode = orgCode;
        this.isAssureCom = isAssureCom;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.OPEN_ACCOUNT;
    }

    @Override
    public Boolean isPageRequest() {
        return true;
    }

    public String getMerBillNo() {
        return merBillNo;
    }

    public String getMerDate() {
        return merDate;
    }

    public String getUserType() {
        return userType;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getIdentNo() {
        return identNo;
    }

    public String getRealName() {
        return realName;
    }

    public String getEnterName() {
        return enterName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public String getIsAssureCom() {
        return isAssureCom;
    }

}
