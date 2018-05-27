package com.klzan.plugin.pay.common.module;

import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.p2p.Tx3216Request;

/**
 * 项目支付查询
 * Created by suhao Date: 2017/11/22 Time: 14:45
 *
 * @version: 1.0
 */
public class PayInvestQueryModule extends PayModule {

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.invest_query;
    }

    @Override
    public TxBaseRequest getReqParam() {
        SnRequest snRequest = (SnRequest)getRequest();

        String paymentNo = snRequest.getSn();

        // 创建交易请求对象
        Tx3216Request request = new Tx3216Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setPaymentNo(paymentNo);

        return request;
    }

}
