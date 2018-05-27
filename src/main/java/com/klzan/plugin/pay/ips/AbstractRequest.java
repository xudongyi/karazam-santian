package com.klzan.plugin.pay.ips;

import com.klzan.plugin.pay.IRequest;

/**
 * Created by suhao Date: 2017/3/15 Time: 19:11
 *
 * @version: 1.0
 */
public abstract class AbstractRequest implements IRequest {
    /**
     * 商户订单号
     */
    protected String merBillNo;
    /**
     * 日期 yyyy-MM-DD
     */
    protected String merDate;

    public String getMerBillNo() {
        return merBillNo;
    }

    public String getMerDate() {
        return merDate;
    }
}
