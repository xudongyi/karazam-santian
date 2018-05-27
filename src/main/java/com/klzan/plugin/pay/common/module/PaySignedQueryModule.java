package com.klzan.plugin.pay.common.module;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.SignedRequest;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4276Request;

/**
 * 开户查询 - 支付组件
 *
 * @author: chenxinglin
 */
public class PaySignedQueryModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.payment_account_signed_query;
    }

    @Override
    public TxBaseRequest getReqParam() {

        SignedRequest req = (SignedRequest)getRequest();

        CpcnPayAccountInfo cpcnPayAccountInfo = payUtils.getCpcnPayAccountInfo(req.getUserId());
        String agreementType = req.getAgreementType();
        String agreementNo = "";
        if (agreementType.equals("20")) {
            agreementNo = cpcnPayAccountInfo.getChargeAgreementNo();
        } else if (agreementType.equals("60")) {
            agreementNo = cpcnPayAccountInfo.getInvestAgreementNo();
        }

        if (StringUtils.isBlank(agreementNo)) {
            throw new PayException("签约号为空");
        }

        // 创建交易请求对象
        Tx4276Request request = new Tx4276Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setAgreementNo(agreementNo);
        return request;
    }

}