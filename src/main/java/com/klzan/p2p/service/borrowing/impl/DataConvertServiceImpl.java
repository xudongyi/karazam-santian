package com.klzan.p2p.service.borrowing.impl;

import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.postloan.RepaymentDao;
import com.klzan.p2p.dao.postloan.RepaymentPlanDao;
import com.klzan.p2p.dao.transfer.TransferDao;
import com.klzan.p2p.dao.user.CorporationDao;
import com.klzan.p2p.dao.user.UserInfoDao;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.PeriodUnit;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.util.AccountantUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据转换
 * @author: chenxinglin  Date: 2017-2-10
 */
@Service
public class DataConvertServiceImpl implements DataConvertService {

    @Inject
    private UserService userService;

    @Inject
    private UserInfoDao userInfoDao;

    @Inject
    private BorrowingDao borrowingDao;

    @Inject
    private RepaymentDao repaymentDao;

    @Inject
    private RepaymentPlanDao repaymentPlanDao;

    @Inject
    private CorporationDao corporationDao;

    @Inject
    private TransferDao transferDao;

    @Inject
    private Environment environment;

    @Override
    public Borrowing convertBorrowing(Borrowing borrowing) {
        if(borrowing==null || borrowing.getId()==null) {
            return borrowing;
        }
        if(borrowing.getProgress().equals(BorrowingProgress.REPAYING) || borrowing.getProgress().equals(BorrowingProgress.COMPLETED)){
//            Date nextPayDate = repaymentDao.getNextPayDate(borrowing.getId());
            Date finalPayDate = repaymentDao.getFinalPayDate(borrowing.getId());
            Boolean ahead = repaymentDao.hasAhead(borrowing.getId());
//            borrowing.setNextPayDate(nextPayDate);
            borrowing.setFinalPayDate(finalPayDate);
            borrowing.setAhead(ahead);
            Repayment repayment = repaymentDao.getCurrentRepayment(borrowing.getId());
            if(repayment != null){
                borrowing.setNextPayDate(repayment.getPayDate());
                borrowing.setRepaymentProgress(repayment.getPeriod()+"/"+borrowing.getRepayPeriod());
            }else{
                borrowing.setNextPayDate(null);
                borrowing.setRepaymentProgress("已完成");
            }
        }
        return borrowing;
    }

    @Override
    public List<Borrowing> convertBorrowings(List<Borrowing> borrowings) {
        if(borrowings==null || borrowings.size()==0) {
            return borrowings;
        }
        for(Borrowing borrowing : borrowings){
            this.convertBorrowing(borrowing);
        }
        return borrowings;
    }

    @Override
    public Investment convertInvestment(Investment investment) {
        if(investment==null) {
            return investment;
        }
        User user = userService.get(investment.getInvestor());

        Borrowing borrowing = borrowingDao.get(investment.getBorrowing());

        investment.setInvestorLoginName(user.getLoginName());
        if(user.getType() == UserType.ENTERPRISE){
            Corporation corporation = corporationDao.findCorporationByUserId(user.getId());

            investment.setInvestorCorp(Boolean.TRUE);
            if(corporation==null){
                investment.setInvestorCorporationName("-");
                investment.setInvestorCorporationIdCard("-");
            }else{
                investment.setInvestorCorporationName(corporation.getCorpName());
                investment.setInvestorCorporationIdCard(corporation!=null&&corporation.getCorpLicenseNo()!=null?corporation.getCorpLicenseNo():"-");
            }

            UserInfo userInfo = userInfoDao.get(investment.getInvestor());
            investment.setInvestorRealName(userInfo!=null&&userInfo.getRealName()!=null?userInfo.getRealName():"-");

        }else {
            UserInfo userInfo = userInfoDao.get(investment.getInvestor());
            investment.setInvestorRealName(userInfo!=null&&userInfo.getRealName()!=null?userInfo.getRealName():"-");
            investment.setInvestorCorp(false);
            investment.setInvestorIdNo(userInfo!=null&&userInfo.getIdNo()!=null?userInfo.getIdNo():"-");
        }
//        investment.setInvestorIdNo(userInfo!=null&&userInfo.getIdNo()!=null?userInfo.getIdNo():"-");
//        investment.setInvestorRealName(userInfo!=null&&userInfo.getRealName()!=null?userInfo.getRealName():"-");
//        investment.setInvestorCorp(user!=null?user.getCorp():false);
//        investment.setInvestorCorporationIdCard(user!=null&&user.getCorp()&&userInfo!=null&&userInfo.getIdNo()!=null?userInfo.getIdNo():"-");
        if(borrowing.getProgress().equals(BorrowingProgress.COMPLETED) || borrowing.getProgress().equals(BorrowingProgress.REPAYING)){
            BigDecimal countPlanIncome = repaymentPlanDao.countPlanIncome(borrowing.getId(),user.getId());
            investment.setPlanIncome(countPlanIncome==null?BigDecimal.ZERO:countPlanIncome);
        }else {
            if(borrowing.getPeriodUnit().equals(PeriodUnit.MONTH)){
                investment.setPlanIncome(AccountantUtils.calExpense(investment.getAmount(), borrowing.getRealInterestRate()).multiply(new BigDecimal(borrowing.getPeriod())).divide(new BigDecimal(12),2,BigDecimal.ROUND_DOWN));
            }else {
                investment.setPlanIncome(AccountantUtils.calExpense(investment.getAmount(), borrowing.getRealInterestRate()).multiply(new BigDecimal(borrowing.getPeriod())).divide(new BigDecimal(365),2,BigDecimal.ROUND_DOWN));
            }
        }
        if(investment.getTransfer()!=null){
            Transfer transfer = transferDao.get(investment.getTransfer());
            User userTransfer = userService.get(transfer.getTransfer());
            investment.setTransferLoginName(userTransfer.getLoginName());
        }


        return investment;
    }

    @Override
    public List<Investment> convertInvestments(List<Investment> investments) {
        if(investments==null || investments.size()==0) {
            return investments;
        }

        Borrowing borrowing = borrowingDao.get(investments.get(0).getBorrowing());

        for(Investment investment : investments){
            this.convertInvestment(investment);
//            User user = userService.find(investment.getInvestor());
//            UserInfo userInfo = userService.getUserInfo(investment.getInvestor());
//
//            investment.setInvestorLoginName(user!=null?user.getLoginName():"-");
//            investment.setInvestorIdNo(userInfo!=null&&userInfo.getIdNo()!=null?userInfo.getIdNo():"-");
//            investment.setInvestorRealName(userInfo!=null&&userInfo.getRealName()!=null?userInfo.getRealName():"-");
//            investment.setInvestorCorp(user!=null?user.getCorp():false);
//            investment.setInvestorCorporationIdCard(user!=null&&user.getCorp()&&userInfo!=null&&userInfo.getIdNo()!=null?userInfo.getIdNo():"-");
//            investment.setPlanIncome(AccountantUtils.calExpense(investment.getAmount(), borrowing.getRealInterestRate()));
//            if(investment.getTransfer()!=null){
//                Transfer transfer = transferDao.find(investment.getTransfer());
//                User userTransfer = userService.find(transfer.getTransfer());
//                investment.setTransferLoginName(userTransfer.getLoginName());
//            }
        }
        return investments;
    }

    @Override
    public List<InvestmentRecord> convertInvestmentRecords(List<InvestmentRecord> investmentRecords) {
        if(investmentRecords==null || investmentRecords.size()==0) {
            return investmentRecords;
        }

        Borrowing borrowing = borrowingDao.get(investmentRecords.get(0).getBorrowing());
        for(InvestmentRecord investmentRecord : investmentRecords){
            User user = userService.get(investmentRecord.getInvestor());
            UserInfo userInfo = userInfoDao.get(investmentRecord.getInvestor());

            investmentRecord.setInvestorAvatar(user.getAvatar());
            investmentRecord.setInvestorLoginName(user!=null?user.getLoginName():"-");
            investmentRecord.setInvestorIdNo(userInfo!=null&&userInfo.getIdNo()!=null?userInfo.getIdNo():"-");
            investmentRecord.setInvestorRealName(userInfo!=null&&userInfo.getRealName()!=null?userInfo.getRealName():"-");
            investmentRecord.setInvestorType(user.getType());
            investmentRecord.setInvestorCorporationIdCard(user!=null&&userInfo!=null&&userInfo.getIdNo()!=null?userInfo.getIdNo():"-");
            investmentRecord.setPlanIncome(AccountantUtils.calExpense(investmentRecord.getAmount(), borrowing.getRealInterestRate()));
        }
        return investmentRecords;
    }


    @Override
    public List<Repayment> convertRepayments(List<Repayment> repayments) {
        if(repayments == null) {
            return null;
        }
        for(Repayment repayment : repayments){
            User user = userService.get(repayment.getBorrower());
            repayment.setBorrowerName(user.getLoginName());
            repayment.setBorrowerMobile(user.getMobile());
            repayment.setBorrowerType(user.getType());

            Borrowing borrowing = borrowingDao.get(repayment.getBorrowing());
            repayment.setBorrowingTitle(borrowing.getTitle());
        }
        return repayments;
    }

    @Override
    public List<RepaymentPlan> convertRepaymentPlans(List<RepaymentPlan> repaymentPlans) {
        if(repaymentPlans == null) {
            return null;
        }
        for(RepaymentPlan repaymentPlan : repaymentPlans){
            User user = userService.get(repaymentPlan.getBorrower());
            repaymentPlan.setBorrowerName(user.getLoginName());
            repaymentPlan.setBorrowerMobile(user.getMobile());
            repaymentPlan.setBorrowerType(user.getType());
            User investor = userService.get(repaymentPlan.getInvestor());
            repaymentPlan.setInvestorName(investor.getLoginName());
            repaymentPlan.setInvestorMobile(investor.getMobile());
            repaymentPlan.setInvestorType(investor.getType());
        }
        return repaymentPlans;
    }

}
