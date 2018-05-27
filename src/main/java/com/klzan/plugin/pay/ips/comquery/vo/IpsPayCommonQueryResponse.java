package com.klzan.plugin.pay.ips.comquery.vo;

import com.klzan.plugin.pay.IDetailResponse;

/**
 * Created by suhao Date: 2017/3/16 Time: 14:20
 *
 * @version: 1.0
 */
public class IpsPayCommonQueryResponse implements IDetailResponse {
    /**
     * IPS存管账户号
     */
    private String ipsAcctNo;
    /**
     * 查询类型 01:账户查询、 02:交易查询、 03:余额查询
     */
    private String queryType;
    /**
     * 账户信息
     */
    private UserInfoResponse userInfo;
    /**
     * 交易信息
     */
    private TradeResponse trade;
    /**
     * 账户余额信息
     */
    private BalanceResponse balance;

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public String getQueryType() {
        return queryType;
    }

    public UserInfoResponse getUserInfo() {
        return userInfo;
    }

    public TradeResponse getTrade() {
        return trade;
    }

    public BalanceResponse getBalance() {
        return balance;
    }
}
