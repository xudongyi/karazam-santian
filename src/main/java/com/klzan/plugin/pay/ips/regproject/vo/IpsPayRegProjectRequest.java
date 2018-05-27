package com.klzan.plugin.pay.ips.regproject.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.ips.AbstractRequest;

/**
 * Created by suhao Date: 2017/3/15 Time: 18:20
 *
 * @version: 1.0
 */
public class IpsPayRegProjectRequest extends AbstractRequest {
    /**
     * 项目ID号
     */
    private String projectNo;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目类型 1-P2P 2-众筹
     */
    private String projectType;
    /**
     * 项目金额
     */
    private String projectAmt;
    /**
     * 利率类型 1-固定 2-浮动
     */
    private String rateType;
    /**
     * 利率值
     */
    private String rateVal;
    /**
     * 最小利率值
     */
    private String rateMinVal;
    /**
     * 最大利率值
     */
    private String rateMaxVal;
    /**
     * 周期值 周期为天 0 表示无限大
     */
    private String cycVal;
    /**
     * 项目用途
     */
    private String projectUse;
    /**
     * 融资方账户类型 1-个人 2-企业
     */
    private String finaAccType;
    /**
     * 融资方证件号
     */
    private String finaCertNo;
    /**
     * 融资方姓名
     */
    private String finaName;
    /**
     * 融资方IPS账号
     */
    private String finaIpsAcctNo;
    /**
     * 是否超额 0：否 1：是
     */
    private String isExcess;

    public IpsPayRegProjectRequest(String merBillNo, String merDate, String projectNo, String projectName, String projectType, String projectAmt, String rateType, String rateVal, String rateMinVal, String rateMaxVal, String cycVal, String projectUse, String finaAccType, String finaCertNo, String finaName, String finaIpsAcctNo, String isExcess) {
        this.merBillNo = merBillNo;
        this.merDate = merDate;
        this.projectNo = projectNo;
        this.projectName = projectName;
        this.projectType = projectType;
        this.projectAmt = projectAmt;
        this.rateType = rateType;
        this.rateVal = rateVal;
        this.rateMinVal = rateMinVal;
        this.rateMaxVal = rateMaxVal;
        this.cycVal = cycVal;
        this.projectUse = projectUse;
        this.finaAccType = finaAccType;
        this.finaCertNo = finaCertNo;
        this.finaName = finaName;
        this.finaIpsAcctNo = finaIpsAcctNo;
        this.isExcess = isExcess;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.REG_PROJECT;
    }

    @Override
    public Boolean isPageRequest() {
        return false;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public String getProjectAmt() {
        return projectAmt;
    }

    public String getRateType() {
        return rateType;
    }

    public String getRateVal() {
        return rateVal;
    }

    public String getRateMinVal() {
        return rateMinVal;
    }

    public String getRateMaxVal() {
        return rateMaxVal;
    }

    public String getCycVal() {
        return cycVal;
    }

    public String getProjectUse() {
        return projectUse;
    }

    public String getFinaAccType() {
        return finaAccType;
    }

    public String getFinaCertNo() {
        return finaCertNo;
    }

    public String getFinaName() {
        return finaName;
    }

    public String getFinaIpsAcctNo() {
        return finaIpsAcctNo;
    }

    public String getIsExcess() {
        return isExcess;
    }
}
