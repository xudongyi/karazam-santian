package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.mobile.vo.*;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.ReferralFeeService;
import com.klzan.p2p.service.user.ReferralService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.investment.InvestmentVo;
import com.klzan.p2p.vo.repayment.RepaymentPlanVo;
import com.klzan.p2p.vo.user.ReferralFeeVo;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.repayalgorithm.DateLength;
import com.klzan.plugin.repayalgorithm.RepayRecords;
import com.klzan.plugin.repayalgorithm.RepayRecordsStrategyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

import static com.klzan.plugin.repayalgorithm.DateUnit.DAY;
import static com.klzan.plugin.repayalgorithm.DateUnit.MONTH;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
@Controller("mobileUCAccountCenterController")
@RequestMapping("/mobile/uc/account/")
public class AccountCenterController extends BaseController {

    @Inject
    private CapitalService capitalService;
    @Inject
    private UserFinanceService userFinanceService;

    @Inject
    private RepaymentPlanService repaymentPlanService;
    @Inject
    private ReferralService referralService;
    @Inject
    private BorrowingService borrowingService;
    @Inject
    private BusinessService businessService;
    @Inject
    private OrderService orderService;
    @Inject
    private ReferralFeeService referralFeeService;
    @Resource
    private InvestmentService investmentService;
    @Inject
    private InvestmentRecordService investmentRecordService;
    @Inject
    private UserService userService;
    @Inject
    private TransferService transferService;
    @Inject
    private DataConvertService dataConvertService;
    @Inject
    private RepaymentService repaymentService;

    //投资记录
    @RequestMapping(value = "/investmentRecord",method = RequestMethod.GET)
    @ResponseBody
    public Result investmentrecord(@CurrentUser User user , PageCriteria criteria,String state){

        criteria.getParams().add(ParamsFilter.addFilter("investor", ParamsFilter.MatchType.EQ, user.getId()));
        PageResult<InvestmentVo> investmentVoPageResult=new PageResult<>();
        if(state.equals("HOLD")){
            investmentVoPageResult= investmentService.findInvestmentListMobile(criteria, false,null,null, BorrowingProgress.REPAYING);
        }
        if (state.equals("DOING")){
            investmentVoPageResult= investmentService.findInvestmentListMobile(criteria, false,null,null,BorrowingProgress.LENDING,BorrowingProgress.INVESTING);
        }
        if (state.equals("COMPLETED")){
            investmentVoPageResult= investmentService.findInvestmentListMobile(criteria, false,null,null,BorrowingProgress.COMPLETED);
        }

        List<InvestmentRecordVo> InvestmentRecordVos=new ArrayList<>();
        Map map= new HashMap();
        if(investmentVoPageResult.getRows().size()==0){
          return Result.success("暂时没有投资记录");
        }
        for (InvestmentVo investmentVo : investmentVoPageResult.getRows()) {
            Borrowing borrowing=borrowingService.get(investmentVo.getBorrowing());
            borrowing= dataConvertService.convertBorrowing(borrowing);
            Integer residualPeriod=0;

           switch (state){
               //持有中
               case "HOLD":
                   if( investmentVo.getBorrowingProgress().name().equals("REPAYING")){
                       InvestmentRecordVo investmentRecordVo=new InvestmentRecordVo();
//                       if (borrowing.getAhead()!=null&&borrowing.getAhead()){
//                           investmentRecordVo.setEndDate(borrowing.getFinalPayDate().getTime());
//                       }
//                       else{
                           investmentRecordVo.setEndDate(borrowing.getFinalPayDate().getTime());
//                       }
                       investmentRecordVo.setAmount(investmentVo.getAmount());

                       investmentRecordVo.setBorrowingPeriod(investmentVo.getBorrowingPeriod());
                       if (investmentVo.getTransfer()!=null){//如果是债转

                          Transfer transfer= transferService.get(investmentVo.getTransfer());
                          Date beginDate=transferSetjixitime(transfer,borrowing,investmentVo.getId());
                          Integer days=new Double(DateUtils.getDaysOfTwoDate(DateUtils.getMinDateOfDay(borrowing.getFinalPayDate()),DateUtils.getMinDateOfDay(beginDate))).intValue();

                           investmentRecordVo.setBorrowingPeriod(Integer.parseInt(transfer.getSurplusPeriod().substring(0,transfer.getSurplusPeriod().indexOf("/"))));
                            if(borrowing.getPeriodUnit()==PeriodUnit.DAY){
                                investmentRecordVo.setBorrowingPeriod(days);
                            }
                       }
                       investmentRecordVo.setBorrowingPeriodUnit(investmentVo.getBorrowingPeriodUnit());
                       investmentRecordVo.setProjectName(investmentVo.getTitle());
                       investmentRecordVo.setRate(investmentVo.getInterestRate().toString()+'+'+investmentVo.getRewardInterestRate().toString());
                       investmentRecordVo.setProjectId(investmentVo.getBorrowing());
                       List<InvestmentRecord> investmentRecords = investmentRecordService.findListByInvestment(investmentVo.getId());
                       investmentRecordVo.setTransfer(false);
                       if(investmentRecords!=null && investmentRecords.size()>0){
                           investmentRecordVo.setTransfer(investmentRecords.get(0).getTransfer());
                           investmentRecordVo.setTransferId(investmentRecords.get(0).getTransferId());
                       }
                       investmentRecordVo.setInvestmentId(investmentVo.getId());
                       InvestmentRecordVos.add(investmentRecordVo);
                   }
                   break;
                   //进行中
               case "DOING":
                   if(investmentVo.getBorrowingProgress().name().equals("LENDING")||investmentVo.getBorrowingProgress().name().equals("INVESTING")){
                       InvestmentRecordVo investmentRecordVo = new InvestmentRecordVo();
                       investmentRecordVo.setAmount(investmentVo.getAmount());
                       investmentRecordVo.setBorrowingPeriod(investmentVo.getBorrowingPeriod());
                       investmentRecordVo.setBorrowingPeriodUnit(investmentVo.getBorrowingPeriodUnit());
                       investmentRecordVo.setProjectName(investmentVo.getTitle());
                       investmentRecordVo.setRate(investmentVo.getInterestRate().toString()+'+'+investmentVo.getRewardInterestRate().toString());
                       investmentRecordVo.setProjectId(investmentVo.getBorrowing());
                       List<InvestmentRecord> investmentRecords = investmentRecordService.findListByInvestment(investmentVo.getId());
                       investmentRecordVo.setTransfer(false);
                       if(investmentRecords!=null && investmentRecords.size()>0){
                           investmentRecordVo.setTransfer(investmentRecords.get(0).getTransfer());
                           investmentRecordVo.setTransferId(investmentRecords.get(0).getTransferId());
                       }
                       investmentRecordVo.setInvestmentId(investmentVo.getId());
                       if (investmentVo.getTransfer()!=null){
                           Transfer transfer= transferService.get(investmentVo.getTransfer());

                           investmentRecordVo.setBorrowingPeriod(Integer.parseInt(transfer.getSurplusPeriod().substring(0,transfer.getSurplusPeriod().indexOf("/"))));
                       }

                       InvestmentRecordVos.add(investmentRecordVo);
                   }

                   break;
                   //已完成
               case "COMPLETED":
                   if(investmentVo.getBorrowingProgress().name().equals("COMPLETED")){
                       InvestmentRecordVo investmentRecordVo=new InvestmentRecordVo();
                          if (borrowing.getAhead()!=null&&borrowing.getAhead()){
                              investmentRecordVo.setEndDate(borrowing.getRepaymentFinishDate().getTime());
                          }
                          else{
                              investmentRecordVo.setEndDate(borrowing.getFinalPayDate().getTime());
                          }

                       investmentRecordVo.setAmount(investmentVo.getAmount());
                       investmentRecordVo.setBorrowingPeriod(investmentVo.getBorrowingPeriod());
                       investmentRecordVo.setBorrowingPeriodUnit(investmentVo.getBorrowingPeriodUnit());
                       investmentRecordVo.setProjectName(investmentVo.getTitle());
                       investmentRecordVo.setRate(investmentVo.getInterestRate().toString()+'+'+investmentVo.getRewardInterestRate().toString());
                       investmentRecordVo.setProjectId(investmentVo.getBorrowing());
                       List<InvestmentRecord> investmentRecords=investmentRecordService.findListByInvestment(investmentVo.getId());
                       investmentRecordVo.setTransfer(false);
                       if(investmentRecords!=null && investmentRecords.size()>0){
                           investmentRecordVo.setTransfer(investmentRecords.get(0).getTransfer());
                           investmentRecordVo.setTransferId(investmentRecords.get(0).getTransferId());
                       }
                       investmentRecordVo.setInvestmentId(investmentVo.getId());
                       if (investmentVo.getTransfer()!=null){

                           Transfer transfer= transferService.get(investmentVo.getTransfer());
                           Date beginDate=transferSetjixitime(transfer,borrowing,investmentVo.getId());
                           Integer days=0;
                          if (borrowing.getFinalPayDate()!=null){
                              days=new Double(DateUtils.getDaysOfTwoDate(DateUtils.getMinDateOfDay(borrowing.getFinalPayDate()),DateUtils.getMinDateOfDay(beginDate))).intValue();

                          }
                           investmentRecordVo.setBorrowingPeriod(Integer.parseInt(transfer.getSurplusPeriod().substring(0,transfer.getSurplusPeriod().indexOf("/"))));
                           if(borrowing.getPeriodUnit()==PeriodUnit.DAY){
                               investmentRecordVo.setBorrowingPeriod(days);
                           }
                       }
                       InvestmentRecordVos.add(investmentRecordVo);
                   }
                   break;
               default:break;
           }

        }

        map.put("InvestmentRecordVos",InvestmentRecordVos);
        map.put("pages",investmentVoPageResult.getPages());
        return Result.success("成功",map);
    }
    //投资详情
    @RequestMapping(value = "/investmentDetail/{projectId}",method = RequestMethod.GET)
    @ResponseBody
    public  Result investmentDetail(@CurrentUser User user,@PathVariable Integer projectId,Boolean isTransfer,Integer investmentId){
        InvestmentDetailVo investmentDetailVo=new InvestmentDetailVo();
        Borrowing borrowing=new Borrowing();
        Transfer transfer=new Transfer();
        BigDecimal earnedIncome=BigDecimal.ZERO;
        BigDecimal expectFee=BigDecimal.ZERO;
        if (isTransfer!=null&&isTransfer){
            transfer= transferService.get(projectId);
            borrowing=borrowingService.get(transfer.getBorrowing());
        }
        else{
            borrowing= borrowingService.get(projectId);
        }
        borrowing= dataConvertService.convertBorrowing(borrowing);
        if(borrowing==null){
            return  Result.error("参数错误");
        }
        //-------------------------------
        Investment investment= investmentService.get(investmentId);
        if (borrowing.getAhead()!=null&&borrowing.getAhead()){
            //提前还款设置到期日期
            investmentDetailVo.setMaturityDate(borrowing.getRepaymentFinishDateDes().getTime());
        }else {
            //正常还款设置到期日期
            if (borrowing.getFinalPayDate()!=null) {//生成还款计划的标
                investmentDetailVo.setMaturityDate(borrowing.getFinalPayDate().getTime());
            }
        }
        if (isTransfer!=null&&isTransfer){ //债转标
            investmentDetailVo.setTreatmentMode(borrowing.getRepaymentMethodDes());

            investmentDetailVo.setAmount(investment.getAmount());//投资金额
            investmentDetailVo.setBuyDate(investment.getCreateDate().getTime());//购买日期


            Date beginDate=transferSetjixitime(transfer,borrowing,investment.getId());
            Integer days=0;
            if (borrowing.getFinalPayDate()!=null){
                days=new Double(DateUtils.getDaysOfTwoDate(DateUtils.getMinDateOfDay(borrowing.getFinalPayDate()),DateUtils.getMinDateOfDay(beginDate))).intValue();
            }
            investmentDetailVo.setInterestDate(beginDate.getTime());
            Integer residualPeriod = 0;
            investmentDetailVo.setTitle(borrowing.getTitle());
           if (borrowing.getPeriodUnit()==PeriodUnit.MONTH) {
               residualPeriod = Integer.parseInt(transfer.getSurplusPeriod().substring(0, transfer.getSurplusPeriod().indexOf("/")));
               investmentDetailVo.setPeriodUnit(borrowing.getPeriodUnit().name());
               investmentDetailVo.setPeriod(residualPeriod.toString());
               investmentDetailVo.setOldPeriod(borrowing.getPeriod());
           }else{//天标债转

               investmentDetailVo.setOldPeriod(borrowing.getPeriod());
               investmentDetailVo.setSurplusPeriod(days);
               investmentDetailVo.setPeriodUnit(borrowing.getPeriodUnit().name());
               investmentDetailVo.setPeriod(days.toString());
           }
            List<RepaymentPlan> repaymentPlanList=repaymentPlanService.findList(borrowing.getId(),user.getId(),investmentId);
            investmentDetailVo.setRate(borrowing.getRealInterestRate().toString());
            if (repaymentPlanList!=null&&repaymentPlanList.size()!=0) {//出借的标的预期收益
                expectFee=BigDecimal.ZERO;
                for (RepaymentPlan repaymentPlan : repaymentPlanList) {
                    if (repaymentPlan.getState().name().equals("REPAID")) {
                        earnedIncome = earnedIncome.add(repaymentPlan.getRecoveryAmount().subtract(repaymentPlan.getCapital()).subtract(repaymentPlan.getTransferFeeIn()));
                    }
                    expectFee = expectFee.add(repaymentPlan.getRecoveryAmount().subtract(repaymentPlan.getCapital()).subtract(repaymentPlan.getTransferFeeIn()));
                }
            }else{//未出借的标预期收益

            }
            investmentDetailVo.setEarnedIncome(earnedIncome);
            investmentDetailVo.setExpectfee(expectFee);

        }else{//  普通标
            investmentDetailVo.setOldPeriod(borrowing.getPeriod());
            investmentDetailVo.setTreatmentMode(borrowing.getRepaymentMethodDes());
            investmentDetailVo.setRate(borrowing.getRealInterestRate().toString());
            investmentDetailVo.setAmount(investment.getAmount());//投资金额
            investmentDetailVo.setBuyDate(investment.getCreateDate().getTime());//购买日期
            investmentDetailVo.setTitle(borrowing.getTitle());
            investmentDetailVo.setPeriodUnit(borrowing.getPeriodUnit().name());
            investmentDetailVo.setPeriod(borrowing.getPeriod().toString());
            List<RepaymentPlan> repaymentPlanList=repaymentPlanService.findList(borrowing.getId(),user.getId(),investmentId);
            if (borrowing.getProgress()==BorrowingProgress.REPAYING||borrowing.getProgress()==BorrowingProgress.COMPLETED){
                investmentDetailVo.setInterestDate(borrowing.getInterestBeginDate().getTime());
            }

            if (repaymentPlanList!=null&&repaymentPlanList.size()!=0) {
                expectFee=BigDecimal.ZERO;
                for (RepaymentPlan repaymentPlan : repaymentPlanList) {
                    if (repaymentPlan.getState().name().equals("REPAID")) {
                        earnedIncome = earnedIncome.add(repaymentPlan.getRecoveryAmount().subtract(repaymentPlan.getCapital()).subtract(repaymentPlan.getTransferFeeIn()));
                    }
                    expectFee = expectFee.add(repaymentPlan.getRecoveryAmount().subtract(repaymentPlan.getCapital()).subtract(repaymentPlan.getTransferFeeIn()));
                }
            } else{//未出借的标预期收益持有中
                expectFee=calculator(investment.getAmount(),borrowing.getRealInterestRate(),borrowing.getRepaymentMethod(),borrowing.getPeriod(),borrowing.getPeriodUnit());
                }
            investmentDetailVo.setEarnedIncome(earnedIncome);
            investmentDetailVo.setExpectfee(expectFee);
        }

        return Result.success("success",investmentDetailVo);
    }
    //收款明细
    @RequestMapping(value = "/recovery",method = RequestMethod.GET)
    @ResponseBody
    public Result recoverydata(@CurrentUser User user,String monthDate, HttpServletRequest request) {

        Date time= DateUtils.format(monthDate);
        Date starDate=DateUtils.getDateByFirstDayOfMonth(time);
        Date endDate=DateUtils.getMaxDateOfMonth(time);
        List<RepaymentPlan> list=repaymentPlanService.findListByTime(user.getId(),starDate,endDate);
        //本月已收本息
        BigDecimal yAmount = BigDecimal.ZERO;
        //本月待收本息
        BigDecimal nAmount = BigDecimal.ZERO;
        List<RecoveryVo> recoveryVos=new ArrayList<>();

        for (RepaymentPlan plan : list) {
            RecoveryVo recoveryVo=new RecoveryVo();
            if(plan.getState().name().equals("REPAYING")){
                recoveryVo.setPayDate(plan.getRepaymentRecord().getPayDate().getTime());
                nAmount = nAmount.add(plan.getCapital()).add(plan.getInterest());
            }
            if(plan.getState().name().equals("REPAID"))
            {
                recoveryVo.setPayDate(plan.getPaidDate().getTime());
                yAmount = yAmount.add(plan.getRecoveryAmount());
            }

            recoveryVo.setCapital(plan.getCapital());
            recoveryVo.setInterest(plan.getRecoveryAmount().subtract(plan.getCapital()));
            Borrowing borrowing=borrowingService.get(plan.getBorrowing());
            recoveryVo.setProjectName(borrowing.getTitle());
            recoveryVo.setQishu(plan.getRepaymentRecord().getPeriod().toString()+'/'+borrowing.getRepayPeriod().toString());
            if(plan.getIsOverdue()){
                recoveryVo.setState("逾期");
            }else{
               if(plan.getStateDes().equals("还款中")) {
                   recoveryVo.setState("待回收");
               }
               if(plan.getStateDes().equals("已还款")) {
                   recoveryVo.setState("已回收");
               }
            }
            recoveryVo.setProjectId(plan.getBorrowing());
            recoveryVos.add(recoveryVo);
        }
        Map map= new HashMap();
        map.put("nAmount",nAmount);
        map.put("yAmount",yAmount);
        map.put("recoveryVos",recoveryVos);
        return Result.success("success",map);
    }
    //交易记录
    @RequestMapping(value = "/fundRecord",method = RequestMethod.GET)
    @ResponseBody
    public Result data(@CurrentUser User currentUser, PageCriteria criteria, OrderType method, HttpServletRequest request) {
        PageResult<Order> transactionRecord=orderService.AppfindPage(buildQueryCriteria(criteria, request), currentUser.getId(), method);
        Map map= new HashMap();
        List<Order>orderList=transactionRecord.getRows();
        List<FundRecordVo> fundRecordVoList=new ArrayList<>();
        for (Order order : orderList) {
            FundRecordVo fundRecordVo =new FundRecordVo();
            fundRecordVo.setAmount(order.getAmount());
            fundRecordVo.setCreatDate(order.getCreateDate().getTime());
            fundRecordVo.setMethod(order.getMethod());
            fundRecordVo.setMethodDes(order.getMethod().getDisplayName());
            fundRecordVo.setStatus(order.getStatus());
            fundRecordVo.setType(order.getType());
            fundRecordVo.setTypeDes(order.getTypeDes());
            fundRecordVo.setStatusDes(order.getStatusDes());
            fundRecordVo.setOrderNo(order.getOrderNo());
            fundRecordVo.setPayeeFee(order.getPayeeFee());
            fundRecordVo.setPayeeThirdFee(order.getPayeeThirdFee());
            fundRecordVo.setPayerFee(order.getPayerFee());
            fundRecordVo.setPayerThirdFee(order.getPayerThirdFee());
            fundRecordVoList.add(fundRecordVo);

        }
        map.put("rows",fundRecordVoList);
        map.put("pages",transactionRecord.getPages());
        return Result.success("交易记录",map);
    }
    //推荐记录
    @RequestMapping(value = "/referralRecord",method = RequestMethod.GET)
    @ResponseBody
    public Result ReferralRecord(@CurrentUser User user,PageCriteria criteria) {

        PageResult<ReferralFeeVo> voPageResult= referralFeeService.findReferralFeeAndReferralList(criteria,user.getId());
        Map map=new HashMap();
        BigDecimal sumReferralFee=BigDecimal.ZERO;
        List<ReferralVo> referralVoList = new ArrayList<>();
        for (ReferralFeeVo referralFeeVo : voPageResult.getRows()) {
            ReferralVo referralVo=new ReferralVo();
            referralVo.setCreatDate(referralFeeVo.getCreateDate().getTime());
            referralVo.setReUserMobile(referralFeeVo.getReUserMobile());
            if(referralFeeVo.getReferralFee()==null){
                referralVo.setReferralFee(BigDecimal.ZERO);
                referralFeeVo.setReferralFee(BigDecimal.ZERO);
            }else{
                referralVo.setReferralFee(referralFeeVo.getReferralFee());
            }
            referralVoList.add(referralVo);
        }
        List<ReferralFee> alreadySettlement=referralFeeService.alreadySettlement(user.getId());
        logger.info("alreadySettlement {}", JsonUtils.toJson(alreadySettlement));
        if (alreadySettlement!=null&&alreadySettlement.size()!=0){
            for (ReferralFee fee : alreadySettlement) {
                sumReferralFee = sumReferralFee.add(fee.getReferralFee());
            }
        }
        map.put("sumReferralFee",sumReferralFee);
        map.put("pages",voPageResult.getPages());
        map.put("rows",referralVoList);

        return Result.success("success",map);
    }
    //托管账号信息
    @RequestMapping(value = "/ipsBalance",method = RequestMethod.GET)
    @ResponseBody
    public Result ipsBalanceInfo(@CurrentUser User user){
        return businessService.balanceQuery(user);
    }

    public BigDecimal calculator(BigDecimal amount, BigDecimal rate, RepaymentMethod repaymentMethod, Integer borrowingPeriod, PeriodUnit periodUnit) {

        BigDecimal sum_invests = new BigDecimal(0);
        DateLength dateLength = new DateLength(borrowingPeriod, MONTH, InterestMethod.T_PLUS_ZERO);
        if (periodUnit.name().equals("MONTH")) {
            dateLength = new DateLength(borrowingPeriod, MONTH, InterestMethod.T_PLUS_ZERO);
        } else if (periodUnit.name().equals("DAY")) {
            dateLength = new DateLength(borrowingPeriod, DAY, InterestMethod.T_PLUS_ONE);
        }
        RepayRecords repayRecords = RepayRecordsStrategyHolder.instanse().generateRepayRecords(repaymentMethod, amount, rate, dateLength);
        List<RepaymentRecord> repaymentPlans = repayRecords.getRepaymentPlans();
        for (RepaymentRecord record : repaymentPlans) {
            sum_invests = record.getInterest().add(sum_invests);
        }
        return sum_invests;
    }

    //天标债转计息开始时间
    public Date transferSetjixitime(Transfer transfer,Borrowing borrowing,Integer investmentId) {

        List<Repayment> repayments= repaymentService.findList(borrowing.getId());
        Integer surplusperiod = Integer.parseInt(transfer.getSurplusPeriod().substring(0,transfer.getSurplusPeriod().indexOf("/")));
        Integer totalperiod=Integer.parseInt(transfer.getSurplusPeriod().substring(transfer.getSurplusPeriod().indexOf("/")+1,transfer.getSurplusPeriod().length()));
        Integer period=totalperiod-surplusperiod+1;
        Date  begin = investmentService.get(investmentId).getCreateDate();
        for (Repayment repayment : repayments) {
            if (repayment.getPeriod()==period-1&&repayment.getState()==RepaymentState.REPAID&&transfer.getCreateDate().getTime()>repayment.getPaidDate().getTime()){
                begin= repayment.getPayDate();
            }
        }

        if (borrowing.getInterestBeginDate()!=null&&borrowing.getInterestBeginDate().getTime()>begin.getTime()){
            begin=borrowing.getInterestBeginDate();
        }

        return begin;
    }
}
