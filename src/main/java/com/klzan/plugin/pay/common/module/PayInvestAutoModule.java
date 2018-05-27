package com.klzan.plugin.pay.common.module;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.User;
import com.klzan.p2p.vo.investment.InvestVo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.InvestmentAutoRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.p2p.Tx3251Request;
import payment.api.vo.P2PLoanerItem;

import java.util.ArrayList;

/**
 * 项目自动支付
 * Created by suhao Date: 2017/11/22 Time: 14:45
 *
 * @version: 1.0
 */
public class PayInvestAutoModule extends PayModule {

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.invest_auto;
    }

    @Override
    public TxBaseRequest getReqParam() {
        InvestmentAutoRequest investAutoRequest = (InvestmentAutoRequest) getRequest();
        Integer projectId = investAutoRequest.getProjectId();
        Borrowing project = payUtils.getProject(projectId);
        User borrowerUser = payUtils.getUser(project.getBorrower());
        CpcnPayAccountInfo borrowerAccountInfo = payUtils.getCpcnPayAccountInfo(project.getBorrower());
        UserType borrowerUserType = borrowerUser.getType();
        String batchNo = investAutoRequest.getBatchNo();
        String projectNo = project.getProjectNo();
        String projectName = project.getTitle();
        String projectURL = ChinaClearingConfig.PROJECT_URL + projectId;
        String projectScale = PayUtils.convertToFenStr(project.getAmount());
        int returnRate = PayUtils.rateConvert(project.getRealInterestRate());
        int projectPeriod = 360;
        String startDate = DateUtils.format(project.getPublishDate(), "yyyyMMdd");
        String endDate = DateUtils.format(DateUtils.addDays(project.getPublishDate(), 360), "yyyyMMdd");
        String loaneeAccountType = "20";
        String loaneeBankID = "";
        String loaneeBankAccountName = "";
        String loaneeBankAccountNumber = "";
        String guaranteeAccountType = "0";
        String guaranteeBankID = "";
        String guaranteeBankAccountName = "";
        String guaranteeBankAccountNumber = "";
        String loaneePaymentAccountName = borrowerAccountInfo.getAccountUsername();
        String loaneePaymentAccountNumber = borrowerAccountInfo.getAccountNumber();
        String guaranteePaymentAccountName = "";
        String guaranteePaymentAccountNumber = "";

        ArrayList<P2PLoanerItem> P2PLoanerItemList = new ArrayList<>();
        for (InvestVo investVo : investAutoRequest.getInvests()) {
            P2PLoanerItem loaner = new P2PLoanerItem();
            CpcnPayAccountInfo investorAccountInfo = payUtils.getCpcnPayAccountInfo(investVo.getInvestor());
            loaner.setPaymentNo(investVo.getSn());
            loaner.setAmount(PayUtils.convertToFen(investVo.getAmount()));
            loaner.setLoanerPaymentAccountName(investorAccountInfo.getAccountUsername());
            loaner.setLoanerPaymentAccountNumber(investorAccountInfo.getAccountNumber());
            P2PLoanerItemList.add(loaner);
        }

        // 创建交易请求对象
        Tx3251Request request = new Tx3251Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setBatchNo(batchNo);
        request.setProjectNo(projectNo);
        request.setProjectName(projectName);
        request.setProjectURL(projectURL);
        request.setProjectScale(projectScale);
        request.setReturnRate(returnRate);
        request.setProjectPeriod(projectPeriod);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
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
        request.setP2PLoanerItemList(P2PLoanerItemList);

        return request;
    }

}
