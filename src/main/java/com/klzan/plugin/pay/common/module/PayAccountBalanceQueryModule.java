package com.klzan.plugin.pay.common.module;

import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4237Request;

/**
 * 余额查询 - 支付组件
 *
 * @author: chenxinglin
 */
public class PayAccountBalanceQueryModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.account_balance_query;
    }

    @Override
    public TxBaseRequest getReqParam() {
        UserInfoRequest req = (UserInfoRequest)getRequest();
        CpcnPayAccountInfo cpcnPayAccountInfo = payUtils.getCpcnPayAccountInfo(req.getUserId());
        String accountNumber = cpcnPayAccountInfo.getAccountNumber();

        // 创建交易请求对象
        Tx4237Request request = new Tx4237Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setPaymentAccountNumber(accountNumber);
        return request;
    }

}