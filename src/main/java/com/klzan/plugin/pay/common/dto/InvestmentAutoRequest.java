package com.klzan.plugin.pay.common.dto;

import com.klzan.p2p.vo.investment.InvestVo;

import java.util.List;

/**
 * 项目支付-自动
 * Created by suhao Date: 2017/11/22 Time: 14:51
 *
 * @version: 1.0
 */
public class InvestmentAutoRequest extends Request {

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 项目ID
     */
    private Integer projectId;

    /**
     * 自动投标集合
     */
    private List<InvestVo> invests;

    public InvestmentAutoRequest(String batchNo, Integer projectId, List<InvestVo> invests) {
        this.batchNo = batchNo;
        this.projectId = projectId;
        this.invests = invests;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public List<InvestVo> getInvests() {
        return invests;
    }
}
