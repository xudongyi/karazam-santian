package com.klzan.plugin.pay.ips.transfer;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayBgPlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.transfer.vo.IpsPayTransferRequest;
import com.klzan.plugin.pay.ips.transfer.vo.IpsPayTransferResponse;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zhutao on 2017/3/16.
 */
@Service
public class IpsPayTransfer extends AbscractIpsPayPlugin<IpsPayTransferResponse> implements PayBgPlugin {
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
        IpsPayTransferRequest ipsRequest = (IpsPayTransferRequest) request;
        Map map = new LinkedMap();
        map.put("batchNo", ipsRequest.getBatchNo());
        map.put("merDate", ipsRequest.getMerDate());
        map.put("projectNo", ipsRequest.getProjectNo());
        map.put("transferType", ipsRequest.getTransferType());
        map.put("isAutoRepayment", ipsRequest.getIsAutoRepayment());
        map.put("transferMode", ipsRequest.getTransferMode());
        map.put("s2SUrl", IpsPayConfig.ASYNC_URL + ipsRequest.getBatchNo());
        map.put("transferAccDetail", ipsRequest.getTransferAccDetailRequest());

        return doPost(doGenerateParams(map));
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.TRANSFER;
    }
}
