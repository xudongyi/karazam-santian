package com.klzan.plugin.pay.ips.frozen.vo;

import com.klzan.plugin.pay.ips.IpsPayDataResponse;

/**
 * Created by suhao Date: 2017/3/16 Time: 10:20
 *
 * @version: 1.0
 */
public class IpsPayFrozenResponse extends IpsPayDataResponse {
    /**
     * 项目ID号
     */
    private String projectNo;
    /**
     * IPS冻结金额
     */
    private String ipsTrdAmt;
    /**
     * 冻结账号
     */
    private String ipsAcctNo;
    /**
     * 它方账号
     */
    private String otherIpsAcctNo;
    /**
     * 冻结状态 0-失败 1-成功
     */
    private String trdStatus;

    public String getProjectNo() {
        return projectNo;
    }

    public String getIpsTrdAmt() {
        return ipsTrdAmt;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public String getOtherIpsAcctNo() {
        return otherIpsAcctNo;
    }

    public String getTrdStatus() {
        return trdStatus;
    }
}
