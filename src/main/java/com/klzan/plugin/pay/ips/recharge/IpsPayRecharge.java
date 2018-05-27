package com.klzan.plugin.pay.ips.recharge;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayPagePlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.recharge.vo.IpsPayRechargeRequest;
import com.klzan.plugin.pay.ips.recharge.vo.IpsPayRechargeResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhutao on 2017/3/15.
 */
@Service
public class IpsPayRecharge extends AbscractIpsPayPlugin<IpsPayRechargeResponse> implements PayPagePlugin {

    @Override
    public Boolean isSupport(IRequest request) {
        return request.isPageRequest()&&request.getBusinessType() == getBusinessType();
    }

    @Override
    public Boolean verifySign(String result) {
        return doVerifySign(result);
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
        IpsPayRechargeRequest ipsRequest = (IpsPayRechargeRequest) request;
        Map requestMap = new LinkedHashMap();
        requestMap.put("merBillNo", ipsRequest.getMerBillNo());
        requestMap.put("merDate", ipsRequest.getMerDate());
        requestMap.put("depositType", ipsRequest.getDepositType());
        requestMap.put("channelType", ipsRequest.getChannelType());
        requestMap.put("bankCode", ipsRequest.getBankCode());
        requestMap.put("userType", ipsRequest.getUserType());
        requestMap.put("ipsAcctNo", ipsRequest.getIpsAcctNo());
        requestMap.put("trdAmt", ipsRequest.getTrdAmt());
        requestMap.put("ipsFeeType", ipsRequest.getIpsFeeType());
        requestMap.put("merFee", ipsRequest.getMerFee());
        requestMap.put("merFeeType", ipsRequest.getMerFeeType());
        requestMap.put("taker", ipsRequest.getTaker());
        requestMap.put("webUrl", IpsPayConfig.SYNC_URL + ipsRequest.getMerBillNo());
        requestMap.put("s2SUrl", IpsPayConfig.ASYNC_URL + ipsRequest.getMerBillNo());

        return doGenerateParams(requestMap);
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.RECHARGE;
    }
}
