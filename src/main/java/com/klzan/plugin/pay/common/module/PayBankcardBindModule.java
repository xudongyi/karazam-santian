package com.klzan.plugin.pay.common.module;

import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.BankcardBindRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4241Request;
import payment.api.tx.paymentaccount.Tx4242Request;

/**
 * 用户支付账户银行卡绑定 - 支付组件
 *
 * @author: chenxinglin
 */
public class PayBankcardBindModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.bankcard_bind;
    }

    @Override
    public TxBaseRequest getReqParam() {
        BankcardBindRequest req = (BankcardBindRequest)getRequest();
        CpcnPayAccountInfo info = payUtils.getCpcnPayAccountInfo(req.getUserId());
        String paymentAccountNumber = info.getAccountNumber();

        if(getRequest().isMobile()){
            Tx4242Request request = new Tx4242Request();
            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
            request.setPaymentAccountNumber(paymentAccountNumber);
            request.setPageURL(ChinaClearingConfig.PAGE_URL + getSn());
            return request;
        }

        Tx4241Request request = new Tx4241Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setPaymentAccountNumber(paymentAccountNumber);
        request.setBindingSystemNo(req.getBindingSystemNo());
        request.setPageURL(ChinaClearingConfig.PAGE_URL + getSn());
        return request;
    }

}