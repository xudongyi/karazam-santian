package com.klzan.plugin.pay.ips.unfreeze;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayBgPlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.unfreeze.vo.IpsPayUnfreezeRequest;
import com.klzan.plugin.pay.ips.unfreeze.vo.IpsPayUnfreezeResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhutao on 2017/3/16.
 */
@Service
public class IpsPayUnfreeze extends AbscractIpsPayPlugin<IpsPayUnfreezeResponse> implements PayBgPlugin {
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
        IpsPayUnfreezeRequest ipsRequest = (IpsPayUnfreezeRequest) request;
        Map map = new HashMap();
        map.put("merBillNo", ipsRequest.getMerBillNo());
        map.put("merDate", ipsRequest.getMerDate());
        map.put("projectNo", ipsRequest.getProjectNo());
        map.put("freezeId", ipsRequest.getFreezeId());
        map.put("bizType", ipsRequest.getBizType());
        map.put("merFee", ipsRequest.getMerFee());
        map.put("ipsAcctNo", ipsRequest.getIpsAcctNo());
        map.put("trdAmt", ipsRequest.getTrdAmt());
        map.put("s2SUrl", IpsPayConfig.ASYNC_URL + ipsRequest.getMerBillNo());

        return doPost(doGenerateParams(map));
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.UNFREEZE;
    }
}
