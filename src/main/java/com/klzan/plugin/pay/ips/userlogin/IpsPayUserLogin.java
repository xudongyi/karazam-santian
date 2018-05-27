package com.klzan.plugin.pay.ips.userlogin;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.PayPagePlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.userlogin.vo.IpsPayUserLoginRequest;
import com.klzan.plugin.pay.ips.userlogin.vo.IpsPayUserLoginResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhutao on 2017/3/16.
 */
@Service
public class IpsPayUserLogin extends AbscractIpsPayPlugin<IpsPayUserLoginResponse> implements PayPagePlugin {
    @Override
    public Boolean isSupport(IRequest request) {
        return request.isPageRequest() && request.getBusinessType() == getBusinessType();
    }

    @Override
    public Boolean verifySign(String result) {
        return doVerifySign(result);
    }

    @Override
    public Boolean verifySupport(BusinessType type) {
        return getBusinessType() == type;
    }

    @Override
    public IDetailResponse getResponseResult(String result) {
        return doGetResponseResult(result);
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.USER_LOGIN;
    }

    @Override
    public Map<String, Object> generateParams(IRequest request) {
        IpsPayUserLoginRequest ipsRequest = (IpsPayUserLoginRequest) request;
        Map map = new HashMap();
        map.put("userName", ipsRequest.getUserName());
        map.put("merchantId", IpsPayConfig.MERCHANT_ID);
        return map;
    }
}
