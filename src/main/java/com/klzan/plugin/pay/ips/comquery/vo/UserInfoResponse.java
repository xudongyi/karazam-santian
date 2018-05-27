package com.klzan.plugin.pay.ips.comquery.vo;

import java.io.Serializable;

/**
 * Created by suhao Date: 2017/3/16 Time: 14:27
 *
 * @version: 1.0
 */
public class UserInfoResponse implements Serializable {
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
    private String uCardStatus;
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
    private String signStatus;

    public String getAcctStatus() {
        return acctStatus;
    }

    public String getuCardStatus() {
        return uCardStatus;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public String getSignStatus() {
        return signStatus;
    }
}
