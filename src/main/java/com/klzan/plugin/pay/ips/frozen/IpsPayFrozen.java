package com.klzan.plugin.pay.ips.frozen;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayPagePlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.frozen.vo.IpsPayFrozenRequest;
import com.klzan.plugin.pay.ips.frozen.vo.IpsPayFrozenResponse;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 冻结
 * Created by suhao Date: 2017/3/14 Time: 15:34
 *
 * @version: 1.0
 */
@Service
public class IpsPayFrozen extends AbscractIpsPayPlugin<IpsPayFrozenResponse> implements PayPagePlugin {
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
        IpsPayFrozenRequest ipsRequest = (IpsPayFrozenRequest) request;
        Map requestMap = new LinkedMap();
        requestMap.put("merBillNo", ipsRequest.getMerBillNo());
        requestMap.put("merDate", ipsRequest.getMerDate());
        requestMap.put("projectNo", ipsRequest.getProjectNo());
        requestMap.put("bizType", ipsRequest.getBizType());
        requestMap.put("regType", ipsRequest.getRegType());
        requestMap.put("contractNo", ipsRequest.getContractNo());
        requestMap.put("authNo", ipsRequest.getAuthNo());
        requestMap.put("trdAmt", ipsRequest.getTrdAmt());
        requestMap.put("merFee", ipsRequest.getMerFee());
        requestMap.put("freezeMerType", ipsRequest.getFreezeMerType());
        requestMap.put("ipsAcctNo", ipsRequest.getIpsAcctNo());
        requestMap.put("otherIpsAcctNo", ipsRequest.getOtherIpsAcctNo());
        requestMap.put("webUrl", IpsPayConfig.SYNC_URL + ipsRequest.getMerBillNo());
        requestMap.put("s2SUrl", IpsPayConfig.ASYNC_URL + ipsRequest.getMerBillNo());

        return this.doGenerateParams(requestMap);
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.FROZEN;
    }
}
