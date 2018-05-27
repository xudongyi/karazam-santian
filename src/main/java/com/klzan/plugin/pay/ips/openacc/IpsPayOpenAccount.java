package com.klzan.plugin.pay.ips.openacc;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayPagePlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.openacc.vo.IpsPayOpenAccountRequest;
import com.klzan.plugin.pay.ips.openacc.vo.IpsPayOpenAccountResponse;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户开户
 * Created by suhao Date: 2017/3/14 Time: 15:34
 *
 * @version: 1.0
 */
@Service
public class IpsPayOpenAccount extends AbscractIpsPayPlugin<IpsPayOpenAccountResponse> implements PayPagePlugin {
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
        IpsPayOpenAccountRequest ipsRequest = (IpsPayOpenAccountRequest) request;
        Map requestMap = new LinkedMap();
        requestMap.put("merBillNo", ipsRequest.getMerBillNo());
        requestMap.put("merDate", ipsRequest.getMerDate());
        requestMap.put("userType", ipsRequest.getUserType());
        requestMap.put("userName", getUserName(ipsRequest.getUserName()));
        requestMap.put("mobileNo", ipsRequest.getMobileNo());
        requestMap.put("identNo", ipsRequest.getIdentNo());
        requestMap.put("bizType", "1");
        requestMap.put("realName", ipsRequest.getRealName());
        requestMap.put("enterName", ipsRequest.getEnterName());
        requestMap.put("orgCode", ipsRequest.getOrgCode());
        requestMap.put("isAssureCom", ipsRequest.getIsAssureCom());
        requestMap.put("webUrl", IpsPayConfig.SYNC_URL + ipsRequest.getMerBillNo());
        requestMap.put("s2SUrl", IpsPayConfig.ASYNC_URL + ipsRequest.getMerBillNo());

        return this.doGenerateParams(requestMap);
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.OPEN_ACCOUNT;
    }

    /**
     * 组装用户名
     * @param username
     * @return
     */
    private String getUserName(String username) {
        return new StringBuilder(IpsPayConfig.USER_NAME_PREFIX).append(username).toString();
    }
}
