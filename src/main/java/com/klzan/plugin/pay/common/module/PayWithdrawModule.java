package com.klzan.plugin.pay.common.module;

import com.klzan.core.util.SpringUtils;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.service.withdraw.WithdrawService;
import com.klzan.p2p.vo.withdraw.WithdrawalFeeVo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.WithdrawRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4255Request;
import payment.api.tx.paymentaccount.Tx4258Request;

import java.math.BigDecimal;

/**
 * 提现
 * Created by suhao Date: 2017/11/22 Time: 14:45
 *
 * @version: 1.0
 */
public class PayWithdrawModule extends PayModule {

    private WithdrawService withdrawService = SpringUtils.getBean(WithdrawService.class);

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.withdraw;
    }

    @Override
    public TxBaseRequest getReqParam() {
        WithdrawRequest withdrawRequest = (WithdrawRequest) getRequest();
        BigDecimal withdrawAmount = withdrawRequest.getAmount();
        Integer userId = withdrawRequest.getUserId();
        WithdrawalFeeVo withdrawalFeeVo = withdrawService.calculateFee(userId, withdrawAmount);
        CpcnPayAccountInfo cpcnPayAccountInfo = payUtils.getCpcnPayAccountInfo(userId);
        String paymentAccountNumber = cpcnPayAccountInfo.getAccountNumber();
        String paymentNo = getSn();
        String institutionFee = PayUtils.convertToFenStr(withdrawalFeeVo.getFee());
        String amount = PayUtils.convertToFenStr(withdrawalFeeVo.getArrivalAmount());
        String switchFlag = "0";

        // 创建交易请求对象
        if (withdrawRequest.isMobile()) {
            Tx4258Request request = new Tx4258Request();
            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
            request.setPaymentAccountNumber(paymentAccountNumber);
            request.setTxSN(paymentNo);
            request.setAmount(amount);
            request.setInstitutionFee(institutionFee);
            request.setPageURL(ChinaClearingConfig.PAGE_URL + paymentNo);
            request.setSwitchFlag(switchFlag);
            return request;
        } else {
            Tx4255Request request = new Tx4255Request();
            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
            request.setPaymentAccountNumber(paymentAccountNumber);
            request.setTxSN(paymentNo);
            request.setAmount(amount);
            request.setInstitutionFee(institutionFee);
            request.setPageURL(ChinaClearingConfig.PAGE_URL + paymentNo);
            request.setSwitchFlag(switchFlag);
            return request;
        }
    }

}
