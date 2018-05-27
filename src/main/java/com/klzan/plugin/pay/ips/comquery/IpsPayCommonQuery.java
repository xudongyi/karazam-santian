package com.klzan.plugin.pay.ips.comquery;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayBgPlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.comquery.vo.IpsPayCommonQueryRequest;
import com.klzan.plugin.pay.ips.comquery.vo.IpsPayCommonQueryResponse;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 查询
 * Created by suhao Date: 2017/3/16 Time: 14:20
 *
 * @version: 1.0
 */
@Service
public class IpsPayCommonQuery extends AbscractIpsPayPlugin<IpsPayCommonQueryResponse> implements PayBgPlugin {
    @Override
    public Boolean isSupport(IRequest request) {
        return !request.isPageRequest() && request.getBusinessType() == getBusinessType();
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
    public String generateParamsAndPostRequest(IRequest request) {
        IpsPayCommonQueryRequest ipsRequest = (IpsPayCommonQueryRequest) request;
        Map requestMap = new LinkedMap();
        requestMap.put("ipsAcctNo", ipsRequest.getIpsAcctNo());
        requestMap.put("queryType", ipsRequest.getQueryType());
        requestMap.put("merBillNo", ipsRequest.getMerBillNo());

        return doPost(doGenerateParams(requestMap));
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.COMMON_QUERY;
    }
}
