package com.klzan.plugin.pay.common.module;

import com.klzan.core.util.SpringUtils;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.BankcardBindRequest;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4242Request;
import payment.api.tx.paymentaccount.Tx4245Request;
import payment.api.tx.paymentaccount.Tx4246Request;

/**
 * 登录 - 支付组件
 *
 * @author: chenxinglin
 */
public class PayBankcardUnbindModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.bankcard_unbind;
    }

    @Override
    public TxBaseRequest getReqParam() {
        PayUtils payUtils = SpringUtils.getBean(PayUtils.class);
        BankcardBindRequest req = (BankcardBindRequest)getRequest();
        CpcnPayAccountInfo info = payUtils.getCpcnPayAccountInfo(req.getUserId());
        String paymentAccountNumber = info.getAccountNumber();

        if(getRequest().isMobile()){
            Tx4246Request request = new Tx4246Request();
            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
            request.setPaymentAccountNumber(paymentAccountNumber);
            request.setBindingSystemNo(req.getBindingSystemNo());
            request.setPageURL(ChinaClearingConfig.PAGE_URL + getSn());
            return request;
        }

        Tx4245Request request = new Tx4245Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setPaymentAccountNumber(paymentAccountNumber);
        request.setBindingSystemNo(req.getBindingSystemNo());
        request.setPageURL(ChinaClearingConfig.PAGE_URL + getSn());
        return request;
    }

}