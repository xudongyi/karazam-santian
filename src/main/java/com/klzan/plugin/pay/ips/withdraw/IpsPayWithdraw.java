package com.klzan.plugin.pay.ips.withdraw;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayPagePlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.withdraw.vo.IpsPayWithdrawRequest;
import com.klzan.plugin.pay.ips.withdraw.vo.IpsPayWithdrawResponse;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户提现
 * Created by suhao Date: 2017/3/15 Time: 18:34
 *
 * @version: 1.0
 */
@Service
public class IpsPayWithdraw extends AbscractIpsPayPlugin<IpsPayWithdrawResponse> implements PayPagePlugin {
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
        IpsPayWithdrawRequest ipsRequest = (IpsPayWithdrawRequest) request;
        Map requestMap = new LinkedMap();
        requestMap.put("merBillNo", ipsRequest.getMerBillNo());
        requestMap.put("merDate", ipsRequest.getMerDate());
        requestMap.put("userType", ipsRequest.getUserType());
        requestMap.put("trdAmt", ipsRequest.getTrdAmt());
        requestMap.put("merFee", ipsRequest.getMerFee());
        requestMap.put("merFeeType", ipsRequest.getMerFeeType());
        requestMap.put("ipsFeeType", ipsRequest.getIpsFeeType());
        requestMap.put("ipsAcctNo", ipsRequest.getIpsAcctNo());
        requestMap.put("webUrl", IpsPayConfig.SYNC_URL + ipsRequest.getMerBillNo());
        requestMap.put("s2SUrl", IpsPayConfig.ASYNC_URL + ipsRequest.getMerBillNo());

        return this.doGenerateParams(requestMap);
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.WITHDRAW;
    }
}
