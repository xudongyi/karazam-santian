package com.klzan.plugin.pay.common.module;

import com.klzan.core.util.SpringUtils;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.OrgTransferRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.bankcorp.Tx4540Request;

/**
 * 机构支付账户单笔转账 - 支付组件
 *
 * @author: chenxinglin
 */
public class PayOrgTransferModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.org_transfer;
    }

    @Override
    public TxBaseRequest getReqParam() {

        PayUtils payUtils = SpringUtils.getBean(PayUtils.class);
        OrgTransferRequest req = (OrgTransferRequest)getRequest();
        CpcnPayAccountInfo info = payUtils.getCpcnPayAccountInfo(req.getUserId());

        String txSN = getSn();
        String payerPaymentAccountName = ChinaClearingConfig.PLAT_PAYMENT_ACCOUNT_NAME;
        String payerPaymentAccountNumber = ChinaClearingConfig.PLAT_PAYMENT_ACCOUNT_NUMBER;
        String payeePaymentAccountName = info.getAccountUsername();
        String payeePaymentAccountNumber = info.getAccountNumber();
        long amount = PayUtils.convertToFen(req.getAmount());
        String remark = "";

        Tx4540Request request = new Tx4540Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setTxSN(txSN);
        request.setPayerPaymentAccountName(payerPaymentAccountName);
        request.setPayerPaymentAccountNumber(payerPaymentAccountNumber);
        request.setPayeePaymentAccountName(payeePaymentAccountName);
        request.setPayeePaymentAccountNumber(payeePaymentAccountNumber);
        request.setAmount(amount);
        request.setRemark(remark);
        return request;
    }

}