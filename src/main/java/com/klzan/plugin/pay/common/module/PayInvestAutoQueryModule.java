package com.klzan.plugin.pay.common.module;

import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.p2p.Tx3256Request;

/**
 * 项目自动支付查询
 * Created by suhao Date: 2017/11/22 Time: 14:45
 *
 * @version: 1.0
 */
public class PayInvestAutoQueryModule extends PayModule {

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.invest_auto_query;
    }

    @Override
    public TxBaseRequest getReqParam() {
        SnRequest req = (SnRequest)getRequest();
        String batchNo = req.getSn();

        // 创建交易请求对象
        Tx3256Request request = new Tx3256Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setBatchNo(batchNo);

        return request;
    }

}
