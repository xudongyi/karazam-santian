package com.klzan.plugin.pay.common.module;

import com.klzan.core.util.SpringUtils;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4232Request;

/**
 * 开户查询 - 支付组件
 *
 * @author: chenxinglin
 */
public class PayOpenAccountQueryModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.open_account_query;
    }

    @Override
    public TxBaseRequest getReqParam() {

        PayUtils payUtils = SpringUtils.getBean(PayUtils.class);
        UserInfoRequest req = (UserInfoRequest)getRequest();

        String phoneNumber = payUtils.getMobile(req.getUserId());
        String userType = payUtils.getUserType(req.getUserId());

        Tx4232Request request = new Tx4232Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setPhoneNumber(phoneNumber);
        request.setUserType(userType);
        return request;
    }

}