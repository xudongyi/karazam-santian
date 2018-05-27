package com.klzan.plugin.pay.ips.autosign;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayPagePlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.autosign.vo.IpsPayAutoSignRequest;
import com.klzan.plugin.pay.ips.autosign.vo.IpsPayAutoSignResponse;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 自动签约
 * Created by suhao Date: 2017/3/16 Time: 13:34
 *
 * @version: 1.0
 */
@Service
public class IpsPayAutoSign extends AbscractIpsPayPlugin<IpsPayAutoSignResponse> implements PayPagePlugin {
    @Override
    public Boolean isSupport(IRequest request) {
        return request.isPageRequest() && request.getBusinessType() == getBusinessType();
    }

    @Override
    public Boolean verifySign(String result) {
        return this.doVerifySign(result);
    }

    @Override
    public Boolean verifySupport(BusinessType type) {
        return type == getBusinessType();
    }

    @Override
    public IDetailResponse getResponseResult(String result) {
        return doGetResponseResult(result);
    }

    @Override
    public Map<String, Object> generateParams(IRequest request) {
        IpsPayAutoSignRequest ipsRequest = (IpsPayAutoSignRequest) request;
        Map requestMap = new LinkedMap();
        requestMap.put("merBillNo", ipsRequest.getMerBillNo());
        requestMap.put("merDate", ipsRequest.getMerDate());
        requestMap.put("userType", ipsRequest.getUserType());
        requestMap.put("ipsAcctNo", ipsRequest.getIpsAcctNo());
        requestMap.put("signedType", ipsRequest.getSignedType());
        requestMap.put("validity", ipsRequest.getValidity());
        requestMap.put("cycMinVal", ipsRequest.getCycMinVal());
        requestMap.put("cycMaxVal", ipsRequest.getCycMaxVal());
        requestMap.put("bidMin", ipsRequest.getBidMin());
        requestMap.put("bidMax", ipsRequest.getBidMax());
        requestMap.put("rateMin", ipsRequest.getRateMin());
        requestMap.put("rateMax", ipsRequest.getRateMax());
        requestMap.put("webUrl", IpsPayConfig.SYNC_URL + ipsRequest.getMerBillNo());
        requestMap.put("s2SUrl", IpsPayConfig.ASYNC_URL + ipsRequest.getMerBillNo());

        return this.doGenerateParams(requestMap);
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.AUTO_SIGN;
    }
}
