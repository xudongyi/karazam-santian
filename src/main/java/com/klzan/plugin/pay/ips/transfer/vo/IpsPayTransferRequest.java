package com.klzan.plugin.pay.ips.transfer.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;

import java.util.List;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayTransferRequest implements IRequest {

    /**
     * 商户转账批次号
     */
    private String batchNo;
    /**
     * 转账日期
     */
    private String merDate;
    /**
     * 项目 ID 号
     */
    private String projectNo;
    /**
     * 转账类型 1 放款、2 债券转让、3 还款、4 分红、5代偿、6 代偿还款、7 风险准备金、8 结算担保收益、9 红包
     */
    private String transferType;
    /**
     * 是否自动还款  当转账类型为还款和代偿还款时，该字段为必选。1 为是、2 为否，转账类型为其它类型的时默认为 3
     */
    private String isAutoRepayment ;
    /**
     * 转账方式 1 为逐笔转账、2 为批量转账
     */
    private String transferMode;

    /**
     * 转账明细集合
     */
    private List<TransferAccDetailRequest> transferAccDetailRequest;

    public IpsPayTransferRequest(String batchNo, String merDate, String projectNo, String transferType, String isAutoRepayment, String transferMode, List<TransferAccDetailRequest> transferAccDetailRequest) {
        this.batchNo = batchNo;
        this.merDate = merDate;
        this.projectNo = projectNo;
        this.transferType = transferType;
        this.isAutoRepayment = isAutoRepayment;
        this.transferMode = transferMode;
        this.transferAccDetailRequest = transferAccDetailRequest;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.TRANSFER;
    }

    @Override
    public Boolean isPageRequest() {
        return false;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public String getMerDate() {
        return merDate;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public String getTransferType() {
        return transferType;
    }

    public String getIsAutoRepayment() {
        return isAutoRepayment;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public List<TransferAccDetailRequest> getTransferAccDetailRequest() {
        return transferAccDetailRequest;
    }

}
