package com.klzan.plugin.pay.ips.unfreeze.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.ips.AbstractRequest;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayUnfreezeRequest extends AbstractRequest implements IRequest {
    /**
     * 项目 ID 号
     */
    private String projectNo;
    /**
     * 原 IPS 冻结订单号
     */
    private String freezeId;
    /**
     * 业务类型 1:流标  ;2:撤标;3:解冻保证金;4:解冻红包
     */
    private String bizType;
    /**
     * 平台手续费 N(15,2)
     */
    private String merFee;
    /**
     * 解冻账号
     */
    private String ipsAcctNo ;
    /**
     * 解冻金额 N(15,2)
     */
    private String trdAmt;

    public IpsPayUnfreezeRequest(String merBillNo,String merDate,String projectNo, String freezeId, String bizType, String merFee, String ipsAcctNo, String trdAmt) {
        this.merBillNo = merBillNo;
        this.merDate = merDate;
        this.projectNo = projectNo;
        this.freezeId = freezeId;
        this.bizType = bizType;
        this.merFee = merFee;
        this.ipsAcctNo = ipsAcctNo;
        this.trdAmt = trdAmt;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.UNFREEZE;
    }

    @Override
    public Boolean isPageRequest() {
        return false;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public String getFreezeId() {
        return freezeId;
    }

    public String getBizType() {
        return bizType;
    }

    public String getMerFee() {
        return merFee;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public String getTrdAmt() {
        return trdAmt;
    }

}
