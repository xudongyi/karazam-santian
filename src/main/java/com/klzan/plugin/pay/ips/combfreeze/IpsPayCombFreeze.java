package com.klzan.plugin.pay.ips.combfreeze;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayPagePlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.combfreeze.vo.IpsPayCombFreezeRequest;
import com.klzan.plugin.pay.ips.combfreeze.vo.IpsPayCombFreezeResponse;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 红包组合冻结
 * Created by suhao Date: 2017/3/14 Time: 15:34
 *
 * @version: 1.0
 */
@Service
public class IpsPayCombFreeze extends AbscractIpsPayPlugin<IpsPayCombFreezeResponse> implements PayPagePlugin {
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
        IpsPayCombFreezeRequest ipsRequest = (IpsPayCombFreezeRequest) request;
        Map requestMap = new LinkedMap();
        requestMap.put("projectNo", ipsRequest.getProjectNo());
        requestMap.put("merDate", ipsRequest.getMerDate());
        requestMap.put("regType", ipsRequest.getRegType());
        requestMap.put("contractNo", ipsRequest.getContractNo());
        requestMap.put("authNo", ipsRequest.getAuthNo());
        requestMap.put("webUrl", IpsPayConfig.SYNC_URL + ipsRequest.getMerBillNo());
        requestMap.put("s2SUrl", IpsPayConfig.ASYNC_URL + ipsRequest.getMerBillNo());
        requestMap.put("redPacket", ipsRequest.getRedPacket());
        requestMap.put("bid", ipsRequest.getBid());

        return this.doGenerateParams(requestMap);
    }

    @Override
    public BusinessType getBusinessType() {
        return BusinessType.COMB_FREEZE;
    }
}
