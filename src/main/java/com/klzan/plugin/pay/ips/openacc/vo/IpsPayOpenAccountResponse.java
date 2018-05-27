package com.klzan.plugin.pay.ips.openacc.vo;

import com.klzan.plugin.pay.ips.IpsPayDataResponse;

/**
 * Created by suhao Date: 2017/3/14 Time: 16:20
 *
 * @version: 1.0
 */
public class IpsPayOpenAccountResponse extends IpsPayDataResponse {
    /**
     * IPS虚拟账号
     */
    private String ipsAcctNo;
    /**
     * 注册状态 0-失败 1-成功 2-待审核
     */
    private String status;

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public String getStatus() {
        return status;
    }
}
