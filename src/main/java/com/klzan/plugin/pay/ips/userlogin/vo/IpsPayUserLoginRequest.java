package com.klzan.plugin.pay.ips.userlogin.vo;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayUserLoginRequest implements IRequest {

    /**
     * 用户名
     */
    private String userName;
    /**
     * 商户存管交易账号
     */
    private String merchantId;

    public IpsPayUserLoginRequest(String userName) {
        this.userName = userName;
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.USER_LOGIN;
    }

    @Override
    public Boolean isPageRequest() {
        return true;
    }

    public String getUserName() {
        return userName;
    }

    public String getMerchantId() {
        return merchantId;
    }
}
