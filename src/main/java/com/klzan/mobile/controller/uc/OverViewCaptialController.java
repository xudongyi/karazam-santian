package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.enums.CapitalMethod;
import com.klzan.p2p.enums.CapitalType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.user.ReferralFeeService;
import com.klzan.p2p.service.user.UserCommonService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.withdraw.WithdrawService;
import com.klzan.p2p.vo.capital.CapitalVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/5/15 0015.
 * 资金总览
 */
@Controller("mobileUCOverViewCaptialController")
@RequestMapping("/mobile/uc/")
public class OverViewCaptialController {

    @Inject
    private UserFinanceService userFinanceService;

    @Inject
    private RepaymentPlanService repaymentPlanService;

    @Inject
    private ReferralFeeService referralFeeService;
    @Inject
    private  CapitalService capitalService;
    @Inject
    private UserCommonService userCommonService;
    @Inject
    private WithdrawService withdrawService;
        // 收益详情
    @RequestMapping(value = "overViewFunds",method = RequestMethod.GET)
    @ResponseBody
    public Result overViewFunds(@CurrentUser User user){

        if(user==null)
        {
            Result.error("请先登录");
        }
        //累计收益=利息收益+其它收入（如：推荐费、红包、积分兑换等）
        BigDecimal accumulateIncome =BigDecimal.ZERO;
        BigDecimal todayIncome=BigDecimal.ZERO;//今日收益
        Date todayMax=DateUtils.getMaxDateOfDay(new Date());
        Date todayMix=DateUtils.getMinDateOfDay(new Date());
        List<RepaymentPlan> repaymentPlans = repaymentPlanService.alreadyProfit(user.getId());
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            accumulateIncome=accumulateIncome.add(repaymentPlan.getRecoveryAmount().subtract(repaymentPlan.getCapital()).subtract(repaymentPlan.getTransferFeeIn()));
            if(repaymentPlan.getPaidDate().getTime()>=todayMix.getTime()&&repaymentPlan.getPaidDate().getTime()<=todayMax.getTime()){
                todayIncome= todayIncome.add(repaymentPlan.getRecoveryAmount()).subtract(repaymentPlan.getCapital().subtract(repaymentPlan.getTransferFeeIn()));
            }
        }
        List<ReferralFee> ReferralFeeList =referralFeeService.alreadySettlement(user.getId());//推荐费
        for (ReferralFee fee : ReferralFeeList) {

            if(fee.getPaymentDate().getTime()>=todayMix.getTime()&&fee.getPaymentDate().getTime()<=todayMax.getTime()) {
                todayIncome= todayIncome.add(fee.getReferralFee());
            }
            accumulateIncome = accumulateIncome.add(fee.getReferralFee());
        }
        Map<String,Object> map=new HashMap<>();
        map.put("accumulateIncome",accumulateIncome);
        map.put("todayIncome",todayIncome);
//        String assets = userCommonService.getUserAssets(user.getId()).get("allCapitalSum").toString();
//        map.put("allCapitalSum",assets);
        return Result.success("success",map);
    }
    /**
     * 时间段内每天收益列表
     * */
    @RequestMapping(value = "time",method = RequestMethod.GET)
    @ResponseBody
    public Result timeProfit(@CurrentUser User user){
        if(user==null) {
            Result.error("未登录");
        }
        Date endDate=DateUtils.getMaxDateOfDay(new Date());
        Date startDate=DateUtils.addDays(endDate,-7);
        Integer days = new Double(DateUtils.getDaysOfTwoDate(endDate,startDate)).intValue();

        List<Object> profitlist=new ArrayList<>();
        List<RepaymentPlan> repaymentPlans = repaymentPlanService.alreadyProfit(user.getId());
        List<ReferralFee> ReferralFeeList =referralFeeService.alreadySettlement(user.getId());
       for(int i=1;i<=days;i++) {
           Map map=new HashMap();
           Date temp=DateUtils.addDays(startDate,i);
           Date tempStart=DateUtils.getMinDateOfDay(temp);
           Date tempEnd=DateUtils.getMaxDateOfDay(temp);
           BigDecimal dayProfit= BigDecimal.ZERO;
           BigDecimal dayRerral=BigDecimal.ZERO;
           BigDecimal dayAccumulate=BigDecimal.ZERO;
           for (RepaymentPlan repaymentPlan : repaymentPlans) {
               if(repaymentPlan.getPaidDate().getTime()<=tempEnd.getTime()&&repaymentPlan.getPaidDate().getTime()>=tempStart.getTime()){
                   dayProfit= dayProfit.add(repaymentPlan.getRecoveryAmount().subtract(repaymentPlan.getCapital()).subtract(repaymentPlan.getTransferFeeIn()));
               }
               if(repaymentPlan.getPaidDate().before(tempEnd)){
                   dayAccumulate= dayAccumulate.add(repaymentPlan.getRecoveryAmount().subtract(repaymentPlan.getCapital()).subtract(repaymentPlan.getTransferFeeIn()));
               }
           }
           for (ReferralFee fee : ReferralFeeList) {
               if(fee.getPaymentDate().before(tempEnd)&&fee.getPaymentDate().after(tempStart)){
                   dayRerral=dayRerral.add(fee.getReferralFee());
               }
               if(fee.getPaymentDate().before(tempEnd)){
                   dayAccumulate= dayAccumulate.add(fee.getReferralFee());
               }
           }
           map.put("dayAccumulate",dayAccumulate);
           map.put("dayProfit",dayProfit.add(dayRerral));
           map.put("time",tempStart);
           profitlist.add(map);
       }
       Map map1=new HashMap();
       map1.put("profitlist",profitlist);
       return Result.success("success",map1);

    }
    @RequestMapping(value = "waitcapital" ,method = RequestMethod.GET)
    @ResponseBody
    public Result assetDetails(@CurrentUser User user){
        UserFinance userFinance = userFinanceService.findByUserId(user.getId());

        Map<String,Object> map= new HashMap<>();
        String  waitingInterest= userCommonService.getUserAssets(user.getId()).get("watingProfits").toString();
        String  waitingCaptial=userCommonService.getUserAssets(user.getId()).get("watingCapital").toString();
        List<RepaymentPlan> waitingProfitList=repaymentPlanService.waitingProfit(user.getId());

        //查提现冻结金额
        if(StringUtils.isBlank(user.getMobile())){
           return Result.error("未绑定手机号");
        }
        List<WithdrawRecord>  userCapticals =withdrawService.findWithdrawing(user.getId());
//        List<CapitalVo> userCapticals=capitalService.findAllCapital(user.getMobile(),CapitalType.FROZEN.toString(),CapitalMethod.WITHDRAW.toString(),null,null);
        BigDecimal withdrawFrozen =BigDecimal.ZERO;
        for (WithdrawRecord userCaptical : userCapticals) {
            withdrawFrozen= withdrawFrozen.add(userCaptical.getAmount());
        }
        map.put("waitingInterest",waitingInterest);
        map.put("waitingCaptial",waitingCaptial);
        map.put("availableBalance",userFinance.getAvailable());
        map.put("frozenfee",userFinance.getFrozen());
        map.put("withdrawFrozen",withdrawFrozen);//提现在途，提现冻结
        String assets = userCommonService.getUserAssets(user.getId()).get("allCapitalSum").toString();
        map.put("allCapitalSum",assets);
        return Result.success("success",map);
    }
}
