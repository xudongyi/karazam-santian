package com.klzan.plugin.pay.common.module;

import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.TerminationRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4273Request;

/**
 * 登录 - 支付组件
 *
 * @author: chenxinglin
 */
public class PayTerminationModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.termination;
    }

    @Override
    public TxBaseRequest getReqParam() {
        TerminationRequest req = (TerminationRequest)getRequest();
        String agreementNo = req.getAgreementNo();

        Tx4273Request request = new Tx4273Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setAgreementNo(agreementNo);
        return request;
    }

}