package com.klzan.plugin.pay.ips.regproject.vo;

import com.klzan.plugin.pay.ips.IpsPayDataResponse;

/**
 * Created by suhao Date: 2017/3/15 Time: 18:20
 *
 * @version: 1.0
 */
public class IpsPayRegProjectResponse extends IpsPayDataResponse {
    /**
     * 项目ID号
     */
    private String projectNo;
    /**
     * 登记状态
     */
    private String status;

    public String getProjectNo() {
        return projectNo;
    }

    public String getStatus() {
        return status;
    }
}
