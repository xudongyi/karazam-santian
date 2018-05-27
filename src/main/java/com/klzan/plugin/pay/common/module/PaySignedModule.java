package com.klzan.plugin.pay.common.module;

import com.klzan.core.util.SpringUtils;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.SignedRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4242Request;
import payment.api.tx.paymentaccount.Tx4271Request;
import payment.api.tx.paymentaccount.Tx4272Request;

/**
 * 登录 - 支付组件
 *
 * @author: chenxinglin
 */
public class PaySignedModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.signed;
    }

    @Override
    public TxBaseRequest getReqParam() {

        PayUtils payUtils = SpringUtils.getBean(PayUtils.class);
        SignedRequest req = (SignedRequest)getRequest();
        CpcnPayAccountInfo info = payUtils.getCpcnPayAccountInfo(req.getUserId());
        String agreementType = req.getAgreementType();
        String paymentAccountNumber = info.getAccountNumber();

        if(getRequest().isMobile()){
            Tx4272Request request = new Tx4272Request();
            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
            request.setAgreementNo(getSn());
            request.setAgreementType(Integer.valueOf(agreementType));
            request.setPaymentAccountNumber(paymentAccountNumber);
            request.setPageURL(ChinaClearingConfig.PAGE_URL + getSn());
            return request;
        }

        Tx4271Request request = new Tx4271Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setAgreementNo(getSn());
        request.setAgreementType(agreementType);
        request.setPaymentAccountNumber(paymentAccountNumber);
        request.setPageURL(ChinaClearingConfig.PAGE_URL + getSn());
        return request;
    }

}