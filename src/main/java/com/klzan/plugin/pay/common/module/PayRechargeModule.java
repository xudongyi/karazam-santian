package com.klzan.plugin.pay.common.module;

import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.RechargeRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4251Request;
import payment.api.tx.paymentaccount.Tx4254Request;

import java.math.BigDecimal;

/**
 * 充值
 * Created by suhao Date: 2017/11/22 Time: 14:45
 *
 * @version: 1.0
 */
public class PayRechargeModule extends PayModule {

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.recharge;
    }

    @Override
    public TxBaseRequest getReqParam() {
        RechargeRequest rechargeRequest = (RechargeRequest) getRequest();
        CpcnPayAccountInfo cpcnPayAccountInfo = payUtils.getCpcnPayAccountInfo(rechargeRequest.getUserId());
        String paymentAccountNumber = cpcnPayAccountInfo.getAccountNumber();
        String paymentNo = getSn();
        String amount = rechargeRequest.getAmount().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).toString();

        // 创建交易请求对象
        if (rechargeRequest.isMobile()) {
            Tx4254Request request = new Tx4254Request();
            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
            request.setPaymentAccountNumber(paymentAccountNumber);
            request.setPaymentNo(paymentNo);
            request.setAmount(amount);
            request.setPageURL(ChinaClearingConfig.PAGE_URL + paymentNo);
            return request;
        } else {
            Tx4251Request request = new Tx4251Request();
            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
            request.setPaymentAccountNumber(paymentAccountNumber);
            request.setPaymentNo(paymentNo);
            request.setAmount(amount);
            request.setPageURL(ChinaClearingConfig.PAGE_URL + paymentNo);
            return request;
        }
    }

}
