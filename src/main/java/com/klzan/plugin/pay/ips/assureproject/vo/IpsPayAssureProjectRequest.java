package com.klzan.plugin.pay.ips.assureproject.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.ips.AbstractRequest;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayAssureProjectRequest extends AbstractRequest implements IRequest {

    /**
     * 项目 ID 号
     */
    private String projectNo;
    /**
     * 担保金额 N(15,2)  不能大于担保的项目金额
     */
    private String assureAmt;
    /**
     * 担保收益 N(15,2)  最多为担保金额的 10%
     */
    private String assureIncome;
    /**
     * 担保方类型 1:个人  2:企业
     */
    private String assureType;
    /**
     * 担保方的 IPS存管账户号
     */
    private String assureIpsAcctNo;

    public IpsPayAssureProjectRequest(String merBillNo, String merDate, String projectNo, String assureAmt, String assureIncome, String assureType, String assureIpsAcctNo) {
        this.merBillNo = merBillNo;
        this.merDate = merDate;
        this.projectNo = projectNo;
        this.assureAmt = assureAmt;
        this.assureIncome = assureIncome;
        this.assureType = assureType;
        this.assureIpsAcctNo = assureIpsAcctNo;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.ASSURE_PROJECT;
    }

    @Override
    public Boolean isPageRequest() {
        return false;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public String getAssureAmt() {
        return assureAmt;
    }

    public String getAssureIncome() {
        return assureIncome;
    }

    public String getAssureType() {
        return assureType;
    }

    public String getAssureIpsAcctNo() {
        return assureIpsAcctNo;
    }

}
