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
import com.klzan.p2p.service.message.MessagePushService;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 还款转账处理
 * Created by suhao Date: 2017/4/28 Time: 14:10
 *
 * @version: 1.0
 */
@Service
public class IpsRepaymentTransferProcessService extends AbscractIpsProcessSerivce {

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
    private OrderService orderService;

    @Autowired
    private AccountantService accountantService;
    @Autowired
    private MessagePushService messagePushService;
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private BorrowingNoticeService borrowingNoticeService;

    @Override
    @Transactional
    public Set<Boolean> process(String orderNo, IDetailResponse response) {
        try {
            System.out.println("-------------还款解冻转账----------------------");
            lock.lock(LockStack.INVESTMENT_LOCK, "REPAYMENT" + orderNo);
            checkOrder(orderNo);
            IpsPayTransferResponse responseResult = (IpsPayTransferResponse) response;
            logger.info(JsonUtils.toJson(responseResult));

            String batchOrderNo = orderNo;
            Set<Boolean> booleanResults = new HashSet<>();
            List<TransferAccDetailResponse> transferAccDetail1 = responseResult.getTransferAccDetail();
            //还款金额 总本息
            BigDecimal totalSuccessAmount = BigDecimal.ZERO;
            //逾期金额
            BigDecimal totalOverdueInterestSuccessAmout = BigDecimal.ZERO;
            //严重逾期金额
            BigDecimal totalSeriousOverdueInterestSuccessAmout = BigDecimal.ZERO;
            //还款服务费
            BigDecimal totalRepaymentServiceFee = BigDecimal.ZERO;
            List<RepaymentTransferRecord> repaymentTransferRecords = repaymentTransferRecordService.findByBatchOrderNo(batchOrderNo);
            Repayment repayment = repaymentService.get(repaymentTransferRecords.get(0).getRepayment());
            String loginName = userService.get(repayment.getBorrower()).getLoginName();
            // 备注参数
            String remoteIp = WebUtils.getRemoteIp(WebUtils.getHttpRequest());

            /**
             * 1.将成功还款的还款计划存入list successRepaymentPlan待用，并根据
             * hasSuccessRepay判断该次请求中是否包含失败的转账
             */
            List<RepaymentPlan> successRepaymentPlan = new ArrayList<>();
            for (TransferAccDetailResponse detailResponse : transferAccDetail1) {
                // 成功
                RepaymentTransferRecord transferRecord = repaymentTransferRecordService.findByOrderNo(detailResponse.getMerBillNo());
                RepaymentPlan repaymentPlan = accountantService.calOverdue(repaymentPlanService.findByOrderNo(transferRecord.getPlanOrderNoOrderNo()));
                if (StringUtils.equals(detailResponse.getTrdStatus(), "1")) {
                    transferRecord.success(detailResponse.getIpsBillNo());
                    repaymentTransferRecordService.merge(transferRecord);
                    //统计成功的金额
                    totalSuccessAmount = totalSuccessAmount.add(detailResponse.getIpsTrdAmt());
                    totalOverdueInterestSuccessAmout = totalOverdueInterestSuccessAmout.add(repaymentPlan.getOverdueInterest());
                    totalSeriousOverdueInterestSuccessAmout = totalSeriousOverdueInterestSuccessAmout.add(repaymentPlan.getSeriousOverdueInterest());
                    //统计还款服务费：borrowing中的费率剩以改还款计划中的还款金额
//                    totalRepaymentServiceFee = transferRecord.getFee();
                    successRepaymentPlan.add(repaymentPlan);
                    booleanResults.add(true);
                } else {
                    transferRecord.failure();
                    repaymentTransferRecordService.merge(transferRecord);
                    booleanResults.add(false);
                }
            }

            /**
             * 2：更新还款
             */
            List<RepaymentTransferRecord> transferRecords = repaymentTransferRecordService.findByRepaymentStatus(repayment.getId(), RecordStatus.SUCCESS);
            Set<Boolean> successTransferResults = new HashSet<>();
            for (RepaymentTransferRecord transferRecord : transferRecords) {
                if (transferRecord.getStatus() == RecordStatus.SUCCESS) {
                    successTransferResults.add(true);
                } else {
                    successTransferResults.add(false);
                }
            }
            if (!successTransferResults.contains(false)) {
                /**
                 * 最后一次
                 */
                repayment.setState(RepaymentState.REPAID);
                repayment.setPaidDate(new Date());
                repayment.setPaidAmount(repayment.getRepaymentAmount());
                repayment.setPaidOverdueInterest(repayment.getOverdueInterest());
                repayment.setPaidSeriousOverdueInterest(repayment.getSeriousOverdueInterest());
            } else {
                repayment.setPaidDate(new Date());
                repayment.setState(RepaymentState.REPAYING);
                //如果存在失败，已还金额应将前一次已经成功的部分加上
                repayment.setPaidAmount(totalSuccessAmount.add(repayment.getPaidAmount()));
                repayment.setPaidOverdueInterest(totalOverdueInterestSuccessAmout.add(repayment.getPaidOverdueInterest()));
                repayment.setPaidSeriousOverdueInterest(totalSeriousOverdueInterestSuccessAmout.add(repayment.getSeriousOverdueInterest()));
            }
            repaymentService.update(repayment);
            /**
             * 3：更新还款人信息包括：1).增加还款人相应资金记录 2).还款人账户信息
             */
            UserFinance pBorrowerFinance = userFinanceService.getByUserId(repayment.getBorrower());
            userFinanceService.refresh(pBorrowerFinance);
            if (repayment.getRepaymentAmount().compareTo(pBorrowerFinance.getAvailable()) > 0) {
                throw new RuntimeException("借款人余额不足");
            }

            pBorrowerFinance.subtractBalance(totalSuccessAmount.subtract(totalOverdueInterestSuccessAmout.add(totalSeriousOverdueInterestSuccessAmout)), true);

            Capital capitalFee = null;
            PlatformCapital paltFormCapital = null;
            Capital capitalOverdue = null;
            Capital capital = new Capital(repayment.getBorrower(),
                    CapitalMethod.REPAYMENT,
                    CapitalType.DEBIT,
                    totalSuccessAmount.subtract(totalOverdueInterestSuccessAmout.add(totalSeriousOverdueInterestSuccessAmout)),
                    pBorrowerFinance,
                    repayment.getOrderNo(),
                    loginName,
                    remoteIp,
                    "借款人还款",
                    null);
            totalRepaymentServiceFee = repayment.getRepaymentFee();
            if (totalRepaymentServiceFee.compareTo(BigDecimal.ZERO) > 0) {
                pBorrowerFinance.subtractBalance(totalRepaymentServiceFee, true);
                capitalFee = new Capital(repayment.getBorrower(),
                        CapitalMethod.REPAYMENT_FEE,
                        CapitalType.DEBIT,
                        totalRepaymentServiceFee,
                        pBorrowerFinance,
                        repayment.getOrderNo(),
                        loginName,
                        remoteIp,
                        "借款人还款服务费",
                        null);
                paltFormCapital = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.REPAYMENT_FEE, totalRepaymentServiceFee, BigDecimal.ZERO, capital.getId(), "平台收取投资人还款服务费", loginName, remoteIp, repayment.getOrderNo());

            }
            if (repayment.getOverduePeriod() > 0 || repayment.getSeriousOverduePeriod() > 0) {
                pBorrowerFinance.subtractBalance(totalOverdueInterestSuccessAmout.add(totalSeriousOverdueInterestSuccessAmout), true);
                capitalOverdue = new Capital(repayment.getBorrower(),
                        CapitalMethod.REPAYMENT,
                        CapitalType.DEBIT,
                        totalOverdueInterestSuccessAmout.add(totalSeriousOverdueInterestSuccessAmout),
                        pBorrowerFinance,
                        repayment.getOrderNo(),
                        loginName,
                        remoteIp,
                        "逾期罚息",
                        null);
            }
            pBorrowerFinance.subtractDebit(repayment.getCapitalInterestFee());
            userFinanceService.update(pBorrowerFinance);
            userFinanceService.flush();

            //平台订单 : 还款
            Order order = orderService.updateOrderStatus(OrderType.REPAYMENT, repayment.getId(), OrderStatus.SUCCESS, null);
            capital.setOrderId(order.getId());
            capitalService.persist(capital);
            if (totalRepaymentServiceFee.compareTo(BigDecimal.ZERO) > 0) {
                capitalFee.setOrderId(order.getId());
                capitalFee = capitalService.persist(capitalFee);
                platformCapitalService.persist(paltFormCapital);
            }
            if (repayment.getOverduePeriod() > 0 || repayment.getSeriousOverduePeriod() > 0) {
                capitalOverdue.setOrderId(order.getId());
                capitalService.persist(capital);
            }

            /**
             * 4：根据还款成功部分的还款计划，跟新投资人信息，包括
             * 1).增加投资人相应资金记录 2).投资人账户信息
             */
            // TODO 更新成功部分： 还款计划，投资人资金记录，投资人
            Map<Integer, UserFinance> investorMap = new HashMap();
            UserFinance pInvestorFinance = null;
            List<Map> investors = new ArrayList<>();
            for (RepaymentPlan repaymentPlan : successRepaymentPlan) {
                if (!repaymentPlan.getState().equals(RepaymentState.REPAYING)) {
                    continue;
                }

                // 添加投资人PO并锁定（修复投资未合并、投资人投资多次的情况）
                if (investorMap.containsKey(repaymentPlan.getInvestor())) {
                    pInvestorFinance = investorMap.get(repaymentPlan.getInvestor());
                } else {
                    pInvestorFinance = userFinanceService.getByUserId(repaymentPlan.getInvestor());
                    userFinanceService.refresh(pInvestorFinance);
                    investorMap.put(pInvestorFinance.getId(), pInvestorFinance);
                }

                /**
                 * 5：更新成功转账部分对于的还款计划
                 */
                repaymentPlan.setPayState(PayState.PAID);
                repaymentPlan.setState(RepaymentState.REPAID);
                repaymentPlan.setPaidDate(new Date());
                repaymentPlan.setPaidAmount(repaymentPlan.getRecoveryAmount());
                repaymentPlan.setPaidCapital(repaymentPlan.getCapital());
                repaymentPlan.setPaidInterest(repaymentPlan.getInterest());
                repaymentPlan.setPaidOverdueInterest(repaymentPlan.getOverdueInterest());
                repaymentPlan.setPaidSeriousOverdueInterest(repaymentPlan.getSeriousOverdueInterest());
                repaymentPlanService.update(repaymentPlan);

                Map map = new HashMap();
                map.put("investor", repaymentPlan.getInvestor());
                map.put("amount", repaymentPlan.getRecoveryAmount());
                investors.add(map);

                // TODO 投资人回款积分
                userPointService.repayment(repaymentPlan);

                // TODO 投资人资金记录  本金+利息  服务费  逾期利息+严重逾期利息
                pInvestorFinance.addBalance(repaymentPlan.getCapitalInterest(), RechargeBusinessType.GENERAL);

                User borrower = userService.get(repayment.getBorrower());  // 借款人
                UserFinance borrowerFinance = userFinanceService.getByUserId(repayment.getBorrower());
                User investor = userService.get(repaymentPlan.getInvestor());  // 投资人
                UserFinance investorFinance = userFinanceService.getByUserId(repaymentPlan.getInvestor());

                Capital capitalFeeRecoverry = null;
                PlatformCapital paltFormCapitalRecoverry = null;
                Capital capitalOverdueRecoverry = null;
                Capital capitalRecoverry = new Capital(repaymentPlan.getInvestor(),
                        CapitalMethod.RECOVERY,
                        CapitalType.CREDIT,
                        repaymentPlan.getCapitalInterest(),
                        pInvestorFinance,
                        repaymentPlan.getOrderNo(),
                        loginName,
                        remoteIp,
                        "投资人回款",
                        null);
                if (repaymentPlan.getRecoveryFee().compareTo(BigDecimal.ZERO) > 0) {
                    pInvestorFinance.subtractBalance(repaymentPlan.getRecoveryFee(), false);
                    capitalFeeRecoverry = new Capital(repaymentPlan.getInvestor(),
                            CapitalMethod.RECOVERY_FEE,
                            CapitalType.DEBIT,
                            repaymentPlan.getRecoveryFee(),
                            pInvestorFinance,
                            repaymentPlan.getOrderNo(),
                            loginName,
                            remoteIp,
                            "投资人回款服务费",
                            null);
                    paltFormCapitalRecoverry = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.RECOVERY_FEE, repaymentPlan.getRecoveryFee(), BigDecimal.ZERO, capital.getId(), "平台收取投资人回款服务费", loginName, remoteIp, repaymentPlan.getOrderNo());
                }
                if (repaymentPlan.getOverduePeriod() > 0 || repaymentPlan.getSeriousOverduePeriod() > 0) {
                    pInvestorFinance.addBalance(repaymentPlan.getOverdueInterestTotal(), RechargeBusinessType.GENERAL);
                    capitalOverdueRecoverry = new Capital(repaymentPlan.getInvestor(),
                            CapitalMethod.RECOVERY,
                            CapitalType.CREDIT,
                            repaymentPlan.getOverdueInterestTotal(),
                            pInvestorFinance,
                            repaymentPlan.getOrderNo(),
                            loginName,
                            remoteIp,
                            "逾期罚息",
                            null);
                }
                pInvestorFinance.subtractCredit(repaymentPlan.getCapitalInterestFee());
                userFinanceService.update(pInvestorFinance);
                userFinanceService.flush();

                //平台订单 : 回款
                Order orderRecoverry = new Order();
                orderRecoverry.setId(null);
                orderRecoverry.setUserId(investor.getId());
                orderRecoverry.setPayer(borrower.getId());
                orderRecoverry.setPayerName(borrower.getLoginName());
                orderRecoverry.setPayee(investor.getId());
                orderRecoverry.setPayerName(investor.getLoginName());
                orderRecoverry.setStatus(OrderStatus.SUCCESS);
                orderRecoverry.setType(OrderType.RECOVERY);
                orderRecoverry.setMethod(OrderMethod.IPS);
                orderRecoverry.setBusiness(repaymentPlan.getId());
                orderRecoverry.setParentOrderNo(order.getOrderNo());
                orderRecoverry.setOrderNo(SnUtils.getOrderNo());
                orderRecoverry.setThirdOrderNo(null);
                orderRecoverry.setPayeeBalance(investorFinance.getAvailable());
                orderRecoverry.setPayeeFee(repaymentPlan.getRecoveryFee());
                orderRecoverry.setPayerBalance(borrowerFinance.getAvailable());
                orderRecoverry.setAmount(repaymentPlan.getRecoveryAmount());
                orderRecoverry.setAmountReceived(repaymentPlan.getRecoveryAmount());
                orderRecoverry.setPoints(0);
                orderRecoverry.setLaunchDate(new Date());
                orderRecoverry.setOperator(borrower.getLoginName());
                orderRecoverry.setIp(WebUtils.getRemoteIp(WebUtils.getHttpRequest()));
                orderRecoverry = orderService.persist(orderRecoverry);
                orderService.flush();

                capitalRecoverry.setOrderId(orderRecoverry.getId());
                capitalService.persist(capitalRecoverry);
                if (repaymentPlan.getRecoveryFee().compareTo(BigDecimal.ZERO) > 0) {
                    capitalFeeRecoverry.setOrderId(orderRecoverry.getId());
                    capitalFeeRecoverry = capitalService.persist(capitalFeeRecoverry);
                    platformCapitalService.persist(paltFormCapitalRecoverry);
                }
                if (repaymentPlan.getOverduePeriod() > 0 || repaymentPlan.getSeriousOverduePeriod() > 0) {
                    capitalOverdueRecoverry.setOrderId(orderRecoverry.getId());
                    capitalService.persist(capitalOverdueRecoverry);
                }
                messagePushService.recoverySuccPush(investor.getId(),repaymentPlan.getRecoveryAmount(),borrowingService.get(repaymentPlan.getBorrowing()).getTitle(),repaymentPlan.getRepaymentRecordPeriod(),repaymentPlan.getPaidDate());
            }
            // TODO 更新投资人
            for (Iterator<Map.Entry<Integer, UserFinance>> iterator = investorMap.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<Integer, UserFinance> entry = iterator.next();
                userFinanceService.update(entry.getValue());
            }
            // 还款短信通知
            List<RepaymentPlan> repaymentPlans = repaymentPlanService.findListByRepayment(repayment.getId());
            borrowingNoticeService.repayment(repayment.getBorrowing(), repayment.getId(), repaymentPlans.size(), investors);

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
            throw new RuntimeException("还款失败");
        } finally {
            lock.unLock(LockStack.INVESTMENT_LOCK, "REPAYMENT" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.TRANSFER && type == PaymentOrderType.REPAYMENT;
    }

}
