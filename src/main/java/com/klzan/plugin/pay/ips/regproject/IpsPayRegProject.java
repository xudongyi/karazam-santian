package com.klzan.plugin.pay.ips.regproject;

import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayBgPlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.regproject.vo.IpsPayRegProjectRequest;
import com.klzan.plugin.pay.ips.regproject.vo.IpsPayRegProjectResponse;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 项目登记
 * Created by suhao Date: 2017/3/15 Time: 18:34
 *
 * @version: 1.0
 */
@Service
public class IpsPayRegProject extends AbscractIpsPayPlugin<IpsPayRegProjectResponse> implements PayBgPlugin {
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
        IpsPayRegProjectRequest regProjectRequest = (IpsPayRegProjectRequest) request;
        Map requestMap = new LinkedMap();
        requestMap.put("merBillNo", regProjectRequest.getMerBillNo());
        requestMap.put("merDate", regProjectRequest.getMerDate());
        requestMap.put("projectNo", regProjectRequest.getProjectNo());
        requestMap.put("projectName", regProjectRequest.getProjectName());
        requestMap.put("projectType", regProjectRequest.getProjectType());
        requestMap.put("projectAmt", regProjectRequest.getProjectAmt());
        requestMap.put("rateType", regProjectRequest.getRateType());
        requestMap.put("rateVal", regProjectRequest.getRateVal());
        requestMap.put("rateMinVal", regProjectRequest.getRateMinVal());
        requestMap.put("rateMaxVal", regProjectRequest.getRateMaxVal());
        requestMap.put("cycVal", regProjectRequest.getCycVal());
        requestMap.put("projectUse", regProjectRequest.getProjectUse());
        requestMap.put("finaAccType", regProjectRequest.getFinaAccType());
        requestMap.put("finaCertNo", regProjectRequest.getFinaCertNo());
        requestMap.put("finaName", regProjectRequest.getFinaName());
        requestMap.put("finaIpsAcctNo", regProjectRequest.getFinaIpsAcctNo());
        requestMap.put("isExcess", regProjectRequest.getIsExcess());
        requestMap.put("s2SUrl", IpsPayConfig.ASYNC_URL + regProjectRequest.getMerBillNo());

        return doPost(doGenerateParams(requestMap));
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.REG_PROJECT;
    }
}
