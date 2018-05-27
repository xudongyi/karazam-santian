package com.klzan.plugin.pay.common.module;

import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4252Request;

/**
 * 充值查询
 * Created by suhao Date: 2017/11/22 Time: 14:45
 *
 * @version: 1.0
 */
public class PayRechargeQueryModule extends PayModule {

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.recharge_query;
    }

    @Override
    public TxBaseRequest getReqParam() {
        SnRequest req = (SnRequest)getRequest();

        // 创建交易请求对象
        Tx4252Request request = new Tx4252Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setPaymentNo(req.getSn());
        return request;
    }

}
