package com.klzan.plugin.pay.ips.querybank;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayBgPlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.querybank.vo.IpsPayQueryBankResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by zhutao on 2017/3/15.
 */
@Service
public class IpsPayQueryBank extends AbscractIpsPayPlugin<IpsPayQueryBankResponse> implements PayBgPlugin {

    @Override
    public Boolean isSupport(IRequest request) {
        return !request.isPageRequest()&&request.getBusinessType() == getBusinessType();
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
    public String generateParamsAndPostRequest(IRequest request) {
        return doPost(doGenerateParams(new HashMap()));
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.QUERY_BANK;
    }
}
