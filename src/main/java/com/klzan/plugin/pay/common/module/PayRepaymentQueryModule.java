package com.klzan.plugin.pay.common.module;

import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.p2p.Tx3246Request;

/**
 * 项目还款查询
 * Created by suhao Date: 2017/11/22 Time: 14:45
 *
 * @version: 1.0
 */
public class PayRepaymentQueryModule extends PayModule {

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.repayment_query;
    }

    @Override
    public TxBaseRequest getReqParam() {

        SnRequest snRequest = (SnRequest)getRequest();
        String serialNumber = snRequest.getSn();

        // 创建交易请求对象
        Tx3246Request request = new Tx3246Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setSerialNumber(serialNumber);

        return request;
    }

}
