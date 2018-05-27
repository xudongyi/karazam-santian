package com.klzan.p2p.service.business.ips;

import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.SnUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingNoticeService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.capital.PlatformCapitalService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.repayment.RepaymentTransferRecordService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserPointService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.transfer.vo.IpsPayTransferResponse;
import com.klzan.plugin.pay.ips.transfer.vo.TransferAccDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 还款转账处理
 * Created by suhao Date: 2017/4/28 Time: 14:10
 *
 * @version: 1.0
 */
@Service
public class IpsRepaymentEarlyTransferProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private UserFinanceService userFinanceService;

    @Autowired
    private CapitalService capitalService;

    @Autowired
    private PlatformCapitalService platformCapitalService;

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private RepaymentPlanService repaymentPlanService;

    @Autowired
    private RepaymentTransferRecordService repaymentTransferRecordService;

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountantService accountantService;
    @Autowired
    private BorrowingNoticeService borrowingNoticeService;

    @Override
    public Set<Boolean> process(String orderNo, IDetailResponse response) {
        try {
            System.out.println("-------------提前还款解冻转账----------------------");
            lock.lock(LockStack.INVESTMENT_LOCK, "REPAYMENT_EARLY" + orderNo);
            checkOrder(orderNo);
            IpsPayTransferResponse responseResult = (IpsPayTransferResponse) response;
            logger.info(JsonUtils.toJson(responseResult));

            String batchOrderNo = orderNo;
            Set<Boolean> booleanResults = new HashSet<>();
            List<TransferAccDetailResponse> transferAccDetail1 = responseResult.getTransferAccDetail();
            //还款金额
            BigDecimal totalSuccessAmount = BigDecimal.ZERO;
            //还款服务费
            BigDecimal totalRepaymentServiceFee = BigDecimal.ZERO;

            List<RepaymentTransferRecord> repaymentTransferRecords = repaymentTransferRecordService.findByBatchOrderNo(batchOrderNo);

            Integer borrowingId = repaymentTransferRecords.get(0).getBorrowing();
            Borrowing borrowing = borrowingService.get(borrowingId);
            List<Repayment> repayments = accountantService.calAhead(repaymentService.findList(borrowingId));
            UserFinance pBorrowerFinance = userFinanceService.getByUserId(borrowing.getBorrower());
            userFinanceService.refresh(pBorrowerFinance);

             String loginName = userService.get(borrowing.getBorrower()).getLoginName();
             // 备注参数
             String remoteIp = WebUtils.getRemoteIp(WebUtils.getHttpRequest());

             /**
             * 1.将成功还款的还款计划存入list successRepaymentPlan待用，并根据
             * hasSuccessRepay判断该次请求中是否包含失败的转账
             */
            // TODO 投资人集合
            List<Integer> successInvestors = new ArrayList<>();
//            List<RepaymentPlan> successRepaymentPlan = new ArrayList<>();
            for (TransferAccDetailResponse detailResponse : transferAccDetail1) {
                // 成功
                RepaymentTransferRecord transferRecord = repaymentTransferRecordService.findByOrderNo(detailResponse.getMerBillNo());
//                RepaymentPlan repaymentPlan = repaymentPlanService.findByOrderNo(transferRecord.getPlanOrderNoOrderNo());
                if (StringUtils.equals(detailResponse.getTrdStatus(), "1")) {
                    transferRecord.success(detailResponse.getIpsBillNo());
                    repaymentTransferRecordService.merge(transferRecord);
                    //统计成功的金额
                    totalSuccessAmount = totalSuccessAmount.add(detailResponse.getIpsTrdAmt());
                    //统计还款服务费：borrowing中的费率剩以改还款计划中的还款金额
//                    totalRepaymentServiceFee = transferRecord.getFee();
//                    successRepaymentPlan.add(repaymentPlan);
                    successInvestors.add(transferRecord.getInvstor());
                    booleanResults.add(true);
                } else {
                    transferRecord.failure();
                    repaymentTransferRecordService.merge(transferRecord);
                    booleanResults.add(false);
                }
            }
            repaymentTransferRecordService.flush();

            /**
             * 2：更新还款计划，包括
             * 1).增加投资人相应资金记录 2).投资人账户信息
             */
//            BigDecimal totalInvestorsRecoveryAmount = BigDecimal.ZERO; //回款总金额
            List<Map> investors = new ArrayList<>();
            for (Integer successInvestor : successInvestors) {
                UserFinance pInvestorFinance = userFinanceService.getByUserId(successInvestor);
                userFinanceService.refresh(pInvestorFinance);

                BigDecimal totalRecoveryAmount = BigDecimal.ZERO; //回款总金额
                BigDecimal totalRecoveryFee = BigDecimal.ZERO; //回款总服务费
                List<RepaymentPlan> repaymentPlans = repaymentPlanService.findList(borrowingId, successInvestor, null);
                if(repaymentPlans==null || repaymentPlans.size()==0){
                    throw new RuntimeException("回款人错误");
                }
                for (RepaymentPlan repaymentPlan : repaymentPlans) {
                    if (!repaymentPlan.getState().equals(RepaymentState.REPAYING)) {
                        continue;
                    }
                    repaymentPlan = accountantService.calAhead(repaymentPlan);
                    // 更新成功转账部分对于的还款计划
                    repaymentPlan.setPayState(PayState.PAID);
                    repaymentPlan.setState(RepaymentState.REPAID);
                    repaymentPlan.setPaidDate(new Date());
                    repaymentPlan.setPaidAmount(repaymentPlan.getRecoveryAmount());
                    repaymentPlan.setPaidCapital(repaymentPlan.getCapital());
                    repaymentPlan.setPaidInterest(repaymentPlan.getInterest());
                    repaymentPlan.setPaidOverdueInterest(repaymentPlan.getOverdueInterest());
                    repaymentPlan.setPaidSeriousOverdueInterest(repaymentPlan.getSeriousOverdueInterest());
                    repaymentPlanService.update(repaymentPlan);
                    totalRecoveryAmount = totalRecoveryAmount.add(repaymentPlan.getRecoveryAmount());
                    totalRecoveryFee = totalRecoveryFee.add(repaymentPlan.getRecoveryFee());

                    // TODO 投资人回款积分
                    userPointService.repayment(repaymentPlan);
                }
//                totalInvestorsRecoveryAmount = totalInvestorsRecoveryAmount.add(totalRecoveryAmount);

                Map map = new HashMap();
                map.put("investor", successInvestor);
                map.put("amount", totalRecoveryAmount);
                investors.add(map);

                pInvestorFinance.addBalance(totalRecoveryAmount, RechargeBusinessType.GENERAL);
                Capital capital = new Capital(successInvestor,
                        CapitalMethod.RECOVERY,
                        CapitalType.CREDIT,
                        totalRecoveryAmount.subtract(totalRecoveryFee),
                        pInvestorFinance,
                        "",
                        loginName,
                        remoteIp,
                        "投资人提前回款",
                        null);
                Capital capitalFee = null;
                PlatformCapital platformCapital = null;
                if (totalRecoveryFee.compareTo(BigDecimal.ZERO) > 0) {
                    pInvestorFinance.subtractBalance(totalRecoveryFee, false);
                    capitalFee = new Capital(successInvestor,
                            CapitalMethod.RECOVERY_FEE,
                            CapitalType.DEBIT,
                            totalRecoveryFee,
                            pInvestorFinance,
                            "",
                            loginName,
                            remoteIp,
                            "投资人提前回款服务费",
                            null);
                    platformCapital = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.RECOVERY_FEE, totalRecoveryFee, BigDecimal.ZERO, capital.getId(), "平台收取投资人回款服务费", loginName, remoteIp, "");
                    platformCapitalService.persist(platformCapital);
                }

                userFinanceService.merge(pInvestorFinance);
                userFinanceService.flush();

                User borrower = userService.get(repaymentPlans.get(0).getBorrower());  // 借款人
                UserFinance borrowerFinance = userFinanceService.getByUserId(repaymentPlans.get(0).getBorrower());
                User investor = userService.get(successInvestor);  // 投资人

                Order order = orderService.getByBusinessId(OrderType.REPAYMENT_EARLY, borrowingId);
                //平台订单 : 提前回款
                Order orderRecoverry = new Order();
                orderRecoverry.setUserId(successInvestor);
                orderRecoverry.setPayer(borrower.getId());
                orderRecoverry.setPayerName(borrower.getLoginName());
                orderRecoverry.setPayee(investor.getId());
                orderRecoverry.setPayerName(investor.getLoginName());
                orderRecoverry.setStatus(OrderStatus.SUCCESS);
                orderRecoverry.setType(OrderType.RECOVERY_EARLY);
                orderRecoverry.setMethod(OrderMethod.IPS);
                orderRecoverry.setBusiness(borrowingId);
                orderRecoverry.setParentOrderNo(order.getOrderNo());
                orderRecoverry.setOrderNo(SnUtils.getOrderNo());
                orderRecoverry.setThirdOrderNo(null);
                orderRecoverry.setPayeeBalance(pInvestorFinance.getAvailable());
                orderRecoverry.setPayerBalance(borrowerFinance.getAvailable());
                orderRecoverry.setAmount(totalRecoveryAmount.add(totalRecoveryFee));
                orderRecoverry.setAmountReceived(totalRecoveryAmount);
                orderRecoverry.setPayeeFee(totalRecoveryFee);
                orderRecoverry.setPoints(0);
                orderRecoverry.setLaunchDate(new Date());
                orderRecoverry.setOperator(borrower.getLoginName());
                orderRecoverry.setIp(WebUtils.getRemoteIp(WebUtils.getHttpRequest()));
                orderRecoverry = orderService.persist(orderRecoverry);
                order.setMemo(String.format("回款人[%s]提前回款", investor.getId()));
                orderService.flush();

                capital.setOrderNo(orderRecoverry.getOrderNo());
                capital.setOrderId(orderRecoverry.getId());
                capitalService.persist(capital);
                if (totalRecoveryFee.compareTo(BigDecimal.ZERO) > 0) {
                    capitalFee.setOrderNo(orderRecoverry.getOrderNo());
                    capitalFee.setOrderId(orderRecoverry.getId());
                    capitalService.persist(capitalFee);
                    platformCapital.setOrderNo(orderRecoverry.getOrderNo());
                    platformCapitalService.persist(platformCapital);
                }
            }
            repaymentPlanService.flush();

            /**
             * 3：更新还款 (注：只要存在未成功转账，则所有还款都处于未成功状态)
             */
//            List<RepaymentTransferRecord> transferRecords = repaymentTransferRecordService.findByBorrowingStatus(borrowingId, RecordStatus.SUCCESS);
//            Set<Boolean> successTransferResults = new HashSet<>();
//            for (RepaymentTransferRecord transferRecord : transferRecords) {
//                if (transferRecord.getStatus() == RecordStatus.SUCCESS) {
//                    successTransferResults.add(true);
//                } else {
//                    successTransferResults.add(false);
//                }
//            }
            for(Repayment repayment : repayments){
                if(repayment.getState().equals(RepaymentState.REPAID)){
                    continue;
                }
                totalRepaymentServiceFee = totalRepaymentServiceFee.add(repayment.getRepaymentFee());
                Boolean isALLRecovery = true;
                BigDecimal totalRecovery = BigDecimal.ZERO;
                List<RepaymentPlan> repaymentPlans = accountantService.calAhead(repaymentPlanService.findListByRepayment(repayment.getId()));
                for(RepaymentPlan repaymentPlan : repaymentPlans){
                    if(repaymentPlan.getState().equals(RepaymentState.REPAYING)){
                        isALLRecovery = false;
                        continue;
                    }
                    totalRecovery = totalRecovery.add(repaymentPlan.getRecoveryAmount().add(repaymentPlan.getRecoveryFee()));
                }
                if(isALLRecovery){
                    //还款完成
                    repayment.setState(RepaymentState.REPAID);
                    repayment.setPaidDate(new Date());
                    repayment.setPaidAmount(repayment.getRepaymentAmount());

                    /** 更新借款信息 (全部还款完成时)  */
                    Borrowing pBorrowing = borrowing;
                    pBorrowing.setRepaymentFinishDate(new Date());
                    pBorrowing.setProgress(BorrowingProgress.COMPLETED);
                    pBorrowing.setState(BorrowingState.SUCCESS);
                    borrowingService.update(pBorrowing);
                }else {
                    repayment.setPaidDate(new Date());
                    repayment.setState(RepaymentState.REPAYING);
                    repayment.setPaidAmount(totalRecovery);
                }
//                if (!successTransferResults.contains(false)) {
//
//                } else {
//
//                }
                repaymentService.update(repayment);
            }

            pBorrowerFinance.subtractBalance(totalSuccessAmount, true);
            pBorrowerFinance.subtractDebit(totalSuccessAmount);

            Capital capitalFee = null;
            PlatformCapital paltFormCapital = null;
            Capital capital = new Capital(borrowing.getBorrower(),
                    CapitalMethod.REPAYMENT,
                    CapitalType.DEBIT,
                    totalSuccessAmount,
                    pBorrowerFinance,
                    "",
                    loginName,
                    remoteIp,
                    "借款人提前还款",
                    null);
            if (totalRepaymentServiceFee.compareTo(BigDecimal.ZERO) > 0) {
                pBorrowerFinance.subtractBalance(totalRepaymentServiceFee, true);
                capitalFee = new Capital(pBorrowerFinance.getUserId(),
                        CapitalMethod.REPAYMENT_FEE,
                        CapitalType.DEBIT,
                        totalRepaymentServiceFee,
                        pBorrowerFinance,
                        "",
                        loginName,
                        remoteIp,
                        "借款人提前还款服务费",
                        null);
                paltFormCapital = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.REPAYMENT_FEE, totalRepaymentServiceFee, BigDecimal.ZERO, capital.getId(), "平台收取投资人还款服务费", loginName, remoteIp, "");
            }

            userFinanceService.merge(pBorrowerFinance);
            userFinanceService.flush();

            //平台订单 : 提前还款
            Order order = orderService.updateOrderStatus(OrderType.REPAYMENT_EARLY, borrowingId, OrderStatus.SUCCESS, null);
            capital.setOrderNo(order.getOrderNo());
            capital.setOrderId(order.getId());
            capitalService.persist(capital);
            if (totalRepaymentServiceFee.compareTo(BigDecimal.ZERO) > 0) {
                capitalFee.setOrderNo(order.getOrderNo());
                capitalFee.setOrderId(order.getId());
                capitalFee = capitalService.persist(capitalFee);
                paltFormCapital.setOrderNo(order.getOrderNo());
                platformCapitalService.persist(paltFormCapital);
            }

            // 还款短信通知
//            List<RepaymentPlan> repaymentPlans = repaymentPlanService.findListByRepayment(repayment.getId());
            Map<Integer, BigDecimal> investorMap = repaymentTransferRecordService.getInvestorsByRepayments(repayments);
            borrowingNoticeService.repaymentAdvance(borrowingId, investorMap.size(), investors);

            PaymentOrderStatus status;
            if (booleanResults.contains(true) && !booleanResults.contains(false)) {
                status = PaymentOrderStatus.SUCCESS;
            } else {
                status = PaymentOrderStatus.PROCESSING;
            }
            processOrder(orderNo, "", status);
            return booleanResults;
        }catch(Exception e){
            //TODO 定时任务补救
            e.printStackTrace();
            throw new RuntimeException("提前还款失败");
        } finally {
            lock.unLock(LockStack.INVESTMENT_LOCK, "REPAYMENT_EARLY" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.TRANSFER && type == PaymentOrderType.REPAYMENT_EARLY;
    }

}
