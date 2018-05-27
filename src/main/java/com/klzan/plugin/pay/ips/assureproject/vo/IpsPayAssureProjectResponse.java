package com.klzan.plugin.pay.ips.assureproject.vo;

import com.klzan.plugin.pay.ips.IpsPayDataResponse;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayAssureProjectResponse extends IpsPayDataResponse{

    /**
     * 项目 ID 号
     */
    private String projectNo;
    /**
     * 登记状态 0 失败 1 成功
     */
    private String status;

    public String getProjectNo() {
        return projectNo;
    }

    public String getStatus() {
        return status;
    }
}
