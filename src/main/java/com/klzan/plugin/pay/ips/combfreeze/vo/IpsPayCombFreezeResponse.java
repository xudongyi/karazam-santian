package com.klzan.plugin.pay.ips.combfreeze.vo;

import com.klzan.plugin.pay.IDetailResponse;

/**
 * Created by suhao Date: 2017/3/16 Time: 10:20
 *
 * @version: 1.0
 */
public class IpsPayCombFreezeResponse implements IDetailResponse {
    /**
     * 项目ID号
     */
    private String projectNo;
    /**
     * 冻结状态
     */
    private String trdStatus;
    /**
     * 红包冻结
     */
    private RedPacketResponse redPacket;
    /**
     * 其他冻结
     */
    private RedPacketResponse bid;

    public String getProjectNo() {
        return projectNo;
    }

    public String getTrdStatus() {
        return trdStatus;
    }

    public RedPacketResponse getRedPacket() {
        return redPacket;
    }

    public RedPacketResponse getBid() {
        return bid;
    }
}
