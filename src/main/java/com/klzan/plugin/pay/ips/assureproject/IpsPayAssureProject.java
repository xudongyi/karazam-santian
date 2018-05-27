package com.klzan.plugin.pay.ips.assureproject;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayBgPlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.assureproject.vo.IpsPayAssureProjectRequest;
import com.klzan.plugin.pay.ips.assureproject.vo.IpsPayAssureProjectResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhutao on 2017/3/16.
 */
@Service
public class IpsPayAssureProject extends AbscractIpsPayPlugin<IpsPayAssureProjectResponse> implements PayBgPlugin {
    @Override
    public Boolean isSupport(IRequest request) {
        return !request.isPageRequest() && request.getBusinessType() == getBusinessType();
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
    public String generateParamsAndPostRequest(IRequest request) {
        IpsPayAssureProjectRequest ipsRequest = (IpsPayAssureProjectRequest) request;
        Map map = new HashMap();
        map.put("merBillNo", ipsRequest.getMerBillNo());
        map.put("merDate", ipsRequest.getMerDate());
        map.put("projectNo", ipsRequest.getProjectNo());
        map.put("assureAmt", ipsRequest.getAssureAmt());
        map.put("assureIncome", ipsRequest.getAssureIncome());
        map.put("assureType", ipsRequest.getAssureType());
        map.put("assureIpsAcctNo", ipsRequest.getAssureIpsAcctNo());
        map.put("s2SUrl", IpsPayConfig.ASYNC_URL + ipsRequest.getMerBillNo());

        return doPost(doGenerateParams(map));
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.ASSURE_PROJECT;
    }
}
