package com.klzan.plugin.pay.ips.transfer.vo;

import com.klzan.plugin.pay.IDetailResponse;

import java.util.List;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayTransferResponse implements IDetailResponse {

    /**
     * 商户转账批次号
     */
    private String batchNo;
    /**
     * 项目 ID 号
     */
    private String projectNo;
    /**
     * 转账类型 1 放款、2 债券转让、3 还款、4 分红、5代偿、6 代偿还款、7 风险准备金、8 结算担保收益、9 红包
     */
    private String transferType;
    /**
     * 转账明细集合
     */
    private List<TransferAccDetailResponse> transferAccDetail;

    public String getBatchNo() {
        return batchNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public String getTransferType() {
        return transferType;
    }

    public List<TransferAccDetailResponse> getTransferAccDetail() {
        return transferAccDetail;
    }
}
