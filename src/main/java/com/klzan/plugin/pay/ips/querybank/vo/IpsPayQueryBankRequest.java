package com.klzan.plugin.pay.ips.querybank.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayQueryBankRequest implements IRequest {

    public IpsPayQueryBankRequest() {
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.QUERY_BANK;
    }

    @Override
    public Boolean isPageRequest() {
        return false;
    }
}
