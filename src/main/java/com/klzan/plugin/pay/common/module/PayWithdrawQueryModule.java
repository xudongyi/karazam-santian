package com.klzan.plugin.pay.common.module;

import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.RechargeRequest;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4252Request;
import payment.api.tx.paymentaccount.Tx4255Request;
import payment.api.tx.paymentaccount.Tx4256Request;
import payment.api.tx.paymentaccount.Tx4258Request;

import java.math.BigDecimal;

/**
 * 提现查询
 * Created by suhao Date: 2017/11/22 Time: 14:45
 *
 * @version: 1.0
 */
public class PayWithdrawQueryModule extends PayModule {

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.withdraw_query;
    }

    @Override
    public TxBaseRequest getReqParam() {
        SnRequest req = (SnRequest)getRequest();

        // 创建交易请求对象
        Tx4256Request request = new Tx4256Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setTxSN(req.getSn());
        return request;
    }

}
