package com.klzan.plugin.pay.common.module;

import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.User;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.InvestmentRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.p2p.Tx3211Request;
import payment.api.tx.p2p.Tx3212Request;

import java.math.BigDecimal;

/**
 * 项目支付
 * Created by suhao Date: 2017/11/22 Time: 14:45
 *
 * @version: 1.0
 */
public class PayInvestModule extends PayModule {

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.invest;
    }

    @Override
    public TxBaseRequest getReqParam() {
        InvestmentRequest investRequest = (InvestmentRequest) getRequest();
        Boolean isTransfer = investRequest.getYesTransfer();
        Integer projectId = investRequest.getProjectId();
        Integer investorUserId = investRequest.getUserId();
        Borrowing project = payUtils.getProject(projectId);
        Integer borrowerUserId = project.getBorrower();
        User investorUser = payUtils.getUser(investorUserId);
        User borrowerUser = payUtils.getUser(borrowerUserId);
        UserType investorUserType = investorUser.getType();
        UserType borrowerUserType = borrowerUser.getType();
        CpcnPayAccountInfo investorAccountInfo = payUtils.getCpcnPayAccountInfo(investorUserId);
        CpcnPayAccountInfo borrowerAccountInfo = payUtils.getCpcnPayAccountInfo(borrowerUserId);
        String paymentNo = getSn();
        String projectNo = project.getProjectNo();
        String projectName = project.getTitle();
        String projectURL = ChinaClearingConfig.PROJECT_URL + projectId;
        String projectScale = PayUtils.convertToFenStr(project.getAmount());
        int returnRate = PayUtils.rateConvert(project.getRealInterestRate());
        int projectPeriod = 360;
        String startDate = DateUtils.format(project.getPublishDate(), "yyyyMMdd");
        String endDate = DateUtils.format(DateUtils.addDays(project.getPublishDate(), 360), "yyyyMMdd");
        String loanerPaymentAccountNumber = investorAccountInfo.getAccountNumber();
        int loaneeAccountType = 20;
        String loaneeBankID = "";
        String loaneeBankAccountName = "";
        String loaneeBankAccountNumber = "";
        String loaneePaymentAccountName = borrowerAccountInfo.getAccountUsername();
        String loaneePaymentAccountNumber = borrowerAccountInfo.getAccountNumber();
        if(StringUtils.isNotBlank(project.getBankAccountNumber())){
            loaneeAccountType = Integer.valueOf(payUtils.getUserType(borrowerUserId));
            loaneeBankID = project.getBankID();
            loaneeBankAccountName = project.getBankAccountName();
            loaneeBankAccountNumber = project.getBankAccountNumber();
        }
        String guaranteeAccountType = "0";
        String guaranteeBankID = "";
        String guaranteeBankAccountName = "";
        String guaranteeBankAccountNumber = "";
        String guaranteePaymentAccountName = "";
        String guaranteePaymentAccountNumber = "";
        String investmentType = isTransfer ? "20" : "10";
        String paymentWay = "0";
        long amount = investRequest.getAmount().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).longValue();

        // 创建交易请求对象
        if (investRequest.isMobile()) {
            Tx3212Request request = new Tx3212Request();
            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
            request.setProjectNo(projectNo);
            request.setPaymentNo(paymentNo);
            request.setAmount(amount);
            request.setProjectName(projectName);
            request.setProjectURL(projectURL);
            request.setProjectScale(projectScale);
            request.setReturnRate(returnRate);
            request.setProjectPeriod(projectPeriod);
            request.setStartDate(startDate);
            request.setEndDate(endDate);
            request.setLoanerPaymentAccountNumber(loanerPaymentAccountNumber);
            request.setLoaneeAccountType(loaneeAccountType);
            request.setLoaneeBankID(loaneeBankID);
            request.setLoaneeBankAccountName(loaneeBankAccountName);
            request.setLoaneeBankAccountNumber(loaneeBankAccountNumber);
            request.setLoaneePaymentAccountName(loaneePaymentAccountName);
            request.setLoaneePaymentAccountNumber(loaneePaymentAccountNumber);
            request.setGuaranteeAccountType(guaranteeAccountType);
            request.setGuaranteeBankID(guaranteeBankID);
            request.setGuaranteeBankAccountName(guaranteeBankAccountName);
            request.setGuaranteeBankAccountNumber(guaranteeBankAccountNumber);
            request.setGuaranteePaymentAccountName(guaranteePaymentAccountName);
            request.setGuaranteePaymentAccountNumber(guaranteePaymentAccountNumber);
            request.setPaymentWay(paymentWay);
            request.setPageURL(ChinaClearingConfig.PAGE_URL + paymentNo);
            return request;
        } else {
            Tx3211Request request = new Tx3211Request();
            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
            request.setProjectNo(projectNo);
            request.setPaymentNo(paymentNo);
            request.setAmount(amount);
            request.setProjectName(projectName);
            request.setProjectURL(projectURL);
            request.setProjectScale(projectScale);
            request.setReturnRate(returnRate);
            request.setProjectPeriod(projectPeriod);
            request.setStartDate(startDate);
            request.setEndDate(endDate);
            request.setLoanerPaymentAccountNumber(loanerPaymentAccountNumber);
            request.setLoaneeAccountType(loaneeAccountType);
            request.setLoaneeBankID(loaneeBankID);
            request.setLoaneeBankAccountName(loaneeBankAccountName);
            request.setLoaneeBankAccountNumber(loaneeBankAccountNumber);
            request.setLoaneePaymentAccountName(loaneePaymentAccountName);
            request.setLoaneePaymentAccountNumber(loaneePaymentAccountNumber);
            request.setGuaranteeAccountType(guaranteeAccountType);
            request.setGuaranteeBankID(guaranteeBankID);
            request.setGuaranteeBankAccountName(guaranteeBankAccountName);
            request.setGuaranteeBankAccountNumber(guaranteeBankAccountNumber);
            request.setGuaranteePaymentAccountName(guaranteePaymentAccountName);
            request.setGuaranteePaymentAccountNumber(guaranteePaymentAccountNumber);
            request.setPageURL(ChinaClearingConfig.PAGE_URL + paymentNo);
            request.setInvestmentType(investmentType);
            request.setPaymentWay(paymentWay);
            return request;
        }
    }

}
