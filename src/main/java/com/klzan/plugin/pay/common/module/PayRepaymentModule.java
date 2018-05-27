package com.klzan.plugin.pay.common.module;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.Repayment;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.RepaymentRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.BooleanUtils;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.p2p.Tx3241Request;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * 项目还款
 * Created by suhao Date: 2017/11/22 Time: 14:45
 *
 * @version: 1.0
 */
public class PayRepaymentModule extends PayModule {

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.repayment;
    }

    @Override
    public TxBaseRequest getReqParam() {

        RepaymentRequest repaymentRequest = (RepaymentRequest)getRequest();
        Borrowing borrowing = null;
        Repayment repayment = null;
        CpcnPayAccountInfo info = null;
        if(repaymentRequest.getRepayment()!=null){
            repayment = payUtils.getRepayment(repaymentRequest.getRepayment());
        }
        if(repaymentRequest.getBorrowing()!=null){
            borrowing = payUtils.getBorrowing(repaymentRequest.getBorrowing());
        }else {
            borrowing = payUtils.getBorrowing(repayment.getBorrowing());
        }
        info = payUtils.getCpcnPayAccountInfo(borrowing.getBorrower());

        String serialNumber = getSn();
        String projectNo = borrowing.getProjectNo();
        int accountType = 20;
//        String bankID = "";
//        String bankAccountName = "";
//        String bankAccountNumber = "";
//        String bankProvince = "";
//        String bankCity = "";
//        String identificationType = "";
//        String identificationNumber = "";
        int repaymentType = 20;
        String paymentAccountName = info.getAccountUsername();
        String paymentAccountNumber = info.getAccountNumber();
        if(BooleanUtils.isTrue(repaymentRequest.getInstead())){
            repaymentType = 40;
            paymentAccountName = ChinaClearingConfig.PLAT_PAYMENT_ACCOUNT_NAME;
            paymentAccountNumber = ChinaClearingConfig.PLAT_PAYMENT_ACCOUNT_NUMBER;
        }
        long amount = 0;
        String remark = null;
        if(BooleanUtils.isTrue(repaymentRequest.getEarly())){
//            amount = payUtils.getAheadRepaymentAmount(borrowing.getId());
            amount = payUtils.convertToFen(repaymentRequest.getAmount().add(repaymentRequest.getFee()));
            remark = "提前还款";
        }else {
//            amount = payUtils.getRepaymentAmount(repayment.getId());
            amount = payUtils.convertToFen(repaymentRequest.getAmount().add(repaymentRequest.getFee()));
            remark = "还款";
        }

        // 创建交易请求对象
        Tx3241Request request = new Tx3241Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setSerialNumber(serialNumber);
        request.setProjectNo(projectNo);
        request.setRepaymentType(repaymentType);
        request.setAccountType(accountType);
//        request.setBankID(bankID);
//        request.setBankAccountName(bankAccountName);
//        request.setBankAccountNumber(bankAccountNumber);
//        request.setBankProvince(bankProvince);
//        request.setBankCity(bankCity);
//        request.setIdentificationNumber(identificationNumber);
//        request.setIdentificationType(identificationType);
        request.setPaymentAccountName(paymentAccountName);
        request.setPaymentAccountNumber(paymentAccountNumber);
        request.setAmount(amount);
        request.setRemark(remark);

        return request;
    }

}
