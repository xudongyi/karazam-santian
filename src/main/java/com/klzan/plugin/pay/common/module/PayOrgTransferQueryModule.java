package com.klzan.plugin.pay.common.module;

import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.bankcorp.Tx4542Request;

/**
 * 机构支付账户单笔转账查询 - 支付组件
 *
 * @author: chenxinglin
 */
public class PayOrgTransferQueryModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.org_transfer_query;
    }

    @Override
    public TxBaseRequest getReqParam() {
        SnRequest req = (SnRequest)getRequest();

        Tx4542Request request = new Tx4542Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setTxSN(req.getSn());
        return request;
    }

}