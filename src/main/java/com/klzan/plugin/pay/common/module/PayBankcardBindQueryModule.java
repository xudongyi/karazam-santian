package com.klzan.plugin.pay.common.module;

import com.klzan.core.util.SpringUtils;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4244Request;

/**
 * 绑卡查询 - 支付组件
 *
 * @author: chenxinglin
 */
public class PayBankcardBindQueryModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.bankcard_bind_query;
    }

    @Override
    public TxBaseRequest getReqParam() {

        PayUtils payUtils = SpringUtils.getBean(PayUtils.class);
        UserInfoRequest req = (UserInfoRequest)getRequest();
        CpcnPayAccountInfo info = payUtils.getCpcnPayAccountInfo(req.getUserId());

        // 创建交易请求对象
        Tx4244Request request = new Tx4244Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setPaymentAccountNumber(info.getAccountNumber());
        return request;
    }

}