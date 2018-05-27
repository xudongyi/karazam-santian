package com.klzan.plugin.pay.common.module;

import com.klzan.core.util.SpringUtils;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4235Request;

/**
 * 登录 - 支付组件
 *
 * @author: chenxinglin
 */
public class PayLoginModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.login;
    }

    @Override
    public TxBaseRequest getReqParam() {

        PayUtils payUtils = SpringUtils.getBean(PayUtils.class);
        UserInfoRequest req = (UserInfoRequest)getRequest();
        CpcnPayAccountInfo info = payUtils.getCpcnPayAccountInfo(req.getUserId());
        String paymentAccountNumber = info.getAccountNumber();

        Tx4235Request request = new Tx4235Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setPaymentAccountNumber(paymentAccountNumber);
        return request;
    }

}