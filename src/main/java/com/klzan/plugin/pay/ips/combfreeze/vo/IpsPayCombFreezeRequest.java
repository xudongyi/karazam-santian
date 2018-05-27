package com.klzan.plugin.pay.ips.combfreeze.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;

/**
 * Created by suhao Date: 2017/3/16 Time: 10:20
 *
 * @version: 1.0
 */
public class IpsPayCombFreezeRequest implements IRequest {
    /**
     * 商户订单号
     */
    private String merBillNo;
    /**
     * 提交日期
     */
    private String merDate;
    /**
     * 项目ID号
     */
    private String projectNo;
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
     * 红包冻结
     */
    private RedPacketRequest redPacket;
    /**
     * 其他冻结
     */
    private RedPacketRequest bid;

    public IpsPayCombFreezeRequest(String merBillNo,String merDate, String projectNo, String regType, String contractNo, String authNo, RedPacketRequest redPacket, RedPacketRequest bid) {
        this.merBillNo = merBillNo;
        this.merDate = merDate;
        this.projectNo = projectNo;
        this.regType = regType;
        this.contractNo = contractNo;
        this.authNo = authNo;
        this.redPacket = redPacket;
        this.bid = bid;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.COMB_FREEZE;
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

    public String getProjectNo() {
        return projectNo;
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

    public RedPacketRequest getRedPacket() {
        return redPacket;
    }

    public RedPacketRequest getBid() {
        return bid;
    }

}
