package com.klzan.plugin.pay.ips.frozen.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.ips.AbstractRequest;

/**
 * Created by suhao Date: 2017/3/16 Time: 10:20
 *
 * @version: 1.0
 */
public class IpsPayFrozenRequest extends AbstractRequest {
    /**
     * 项目ID号
     */
    private String projectNo;
    /**
     * 业务类型 1:投标 2:债券转让 3:还款 4:分红 5:代偿 6:代偿还款 7:风险准备金 8:结算担保收益 9:红包 10:融资方保证金 11:投资人保证金 12:担保人保证金
     */
    private String bizType;
    /**
     * 登记方式 1:手动 2:自动
     */
    private String regType;
    /**
     * 合同号
     */
    private String contractNo;
    /**
     * 授权号
     */
    private String authNo;
    /**
     * 冻结金额
     */
    private String trdAmt;
    /**
     * 平台手续费
     */
    private String merFee;
    /**
     * 冻结方类型 1：用户 2：商户
     */
    private String freezeMerType;
    /**
     * 冻结账号
     */
    private String ipsAcctNo;
    /**
     * 它方账号
     */
    private String otherIpsAcctNo;

    public IpsPayFrozenRequest(String merBillNo, String merDate, String projectNo, String bizType, String regType, String contractNo, String authNo, String trdAmt, String merFee, String freezeMerType, String ipsAcctNo, String otherIpsAcctNo) {
        this.merBillNo = merBillNo;
        this.merDate = merDate;
        this.projectNo = projectNo;
        this.bizType = bizType;
        this.regType = regType;
        this.contractNo = contractNo;
        this.authNo = authNo;
        this.trdAmt = trdAmt;
        this.merFee = merFee;
        this.freezeMerType = freezeMerType;
        this.ipsAcctNo = ipsAcctNo;
        this.otherIpsAcctNo = otherIpsAcctNo;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.FROZEN;
    }

    @Override
    public Boolean isPageRequest() {
        return true;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public String getBizType() {
        return bizType;
    }

    public String getRegType() {
        return regType;
    }

    public String getContractNo() {
        return contractNo;
    }

    public String getAuthNo() {
        return authNo;
    }

    public String getTrdAmt() {
        return trdAmt;
    }

    public String getMerFee() {
        return merFee;
    }

    public String getFreezeMerType() {
        return freezeMerType;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public String getOtherIpsAcctNo() {
        return otherIpsAcctNo;
    }

}
