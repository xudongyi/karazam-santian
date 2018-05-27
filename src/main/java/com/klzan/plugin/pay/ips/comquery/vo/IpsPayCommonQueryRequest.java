package com.klzan.plugin.pay.ips.comquery.vo;

import com.klzan.core.util.StringUtils;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;

/**
 * Created by suhao Date: 2017/3/16 Time: 14:20
 *
 * @version: 1.0
 */
public class IpsPayCommonQueryRequest implements IRequest {
    /**
     * IPS存管账户号 查询类型为“ 02:交易查询”可为空
     */
    private String ipsAcctNo;
    /**
     * 查询类型 01:账户查询、 02:交易查询、 03:余额查询
     */
    private String queryType;
    /**
     * 商户订单号 查询类型为交易查询时必填
     */
    private String merBillNo;

    public IpsPayCommonQueryRequest(String queryType, String ipsAcctNoOrMerBillNo) {
        this.queryType = queryType;
        if (StringUtils.equals(queryType, "02")) {
            this.merBillNo = ipsAcctNoOrMerBillNo;
        } else {
            this.ipsAcctNo = ipsAcctNoOrMerBillNo;
        }
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.COMMON_QUERY;
    }

    @Override
    public Boolean isPageRequest() {
        return false;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public String getQueryType() {
        return queryType;
    }

    public String getMerBillNo() {
        return merBillNo;
    }
}
