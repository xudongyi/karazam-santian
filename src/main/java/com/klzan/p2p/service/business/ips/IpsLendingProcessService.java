package com.klzan.p2p.service.business.ips;

import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.LendingRecordService;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.coupon.UserCouponService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserPointService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.vo.order.OrderVo;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.transfer.vo.IpsPayTransferResponse;
import com.klzan.plugin.pay.ips.transfer.vo.TransferAccDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by suhao Date: 2017/4/7 Time: 18:31
 *
 * @version: 1.0
 */
@Service
public class IpsLendingProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFinanceService userFinanceService;

    @Autowired
    private CapitalService capitalService;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private InvestmentRecordService investmentRecordService;

    @Autowired
    private LendingRecordService lendingRecordService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserPointService userPointService;

    @Resource
    private UserCouponService userCouponService;

    @Override
    @Transactional
    public Set<Boolean> process(String orderNo, IDetailResponse response) {
        try {
            System.out.println("---------还款出借----------");
            lock.lock(LockStack.USER_LOCK, "LENDING" + orderNo);
            checkOrder(orderNo);
            IpsPayTransferResponse responseResult = (IpsPayTransferResponse) response;
            logger.info(JsonUtils.toJson(responseResult));
            List<TransferAccDetailResponse> transferAccDetail1 = responseResult.getTransferAccDetail();
            BigDecimal successBorrowingAmount = BigDecimal.ZERO;
            BigDecimal successBorrowingingFee = BigDecimal.ZERO;
            String batchOrderNo = orderNo;
            List<LendingRecord> lendingRecords = lendingRecordService.findByBatchOrderNo(batchOrderNo);
            Borrowing borrowing = borrowingService.get(lendingRecords.get(0).getBorrowing());
            Integer borrowerId = borrowing.getBorrower();
            UserVo borrower = userService.getUserById(borrowerId);
            Set<Boolean> booleanResults = new HashSet<>();
            for (TransferAccDetailResponse detailResponse : transferAccDetail1) {
                System.out.println("-------------出借转账记录次数1-----------------");
                LendingRecord lendingRecord = lendingRecordService.findByOrderNo(detailResponse.getMerBillNo());
                // 成功
                if (StringUtils.equals(detailResponse.getTrdStatus(), "1")) {
                    lendingRecord.success(detailResponse.getIpsBillNo());
                    lendingRecordService.merge(lendingRecord);
                    booleanResults.add(true);

//                    Investment investment = investmentService.get(lendingRecord.getInvestment());
                    InvestmentRecord investmentRecord = investmentRecordService.get(lendingRecord.getInvestmentRecordId());
                    // 更新投资
//                    investment.setState(InvestmentState.SUCCESS);
                    investmentRecord.setState(InvestmentState.SUCCESS);
//                    // 投资送积分
//                    userPointService.invest(investment);

                    // 更新投资人
                    Integer investorId = investmentRecord.getInvestor();
                    UserVo investor = userService.getUserById(investorId);
                    UserFinance investorFinance = userFinanceService.getByUserId(investorId);
                    investorFinance.addInvestmentAmts(investmentRecord.getAmount());
                    investorFinance.subtractBalance(investmentRecord.getAmount(), false);
                    investorFinance.subtractFrozen(investmentRecord.getAmount());
                    userFinanceService.merge(investorFinance);

                    //平台订单更新
                    Order order = orderService.updateOrderStatus(OrderType.INVESTMENT, investmentRecord.getId(), OrderStatus.SUCCESS, orderNo);
                    // 投资人投资解冻资金记录
                    Capital capital = new Capital(investorId,
                            CapitalMethod.INVESTMENT,
                            CapitalType.UNFROZEN,
                            investmentRecord.getAmount(),
                            investorFinance,
                            investmentRecord.getOrderNo(),
                            CommonUtils.getLoginName(),
                            CommonUtils.getRemoteIp(),
                            "投资人投资解冻",
                            order.getId());
                    capitalService.persist(capital);

                    // 投资人投资支出资金记录
                    capital = new Capital(investorId,
                            CapitalMethod.INVESTMENT,
                            CapitalType.DEBIT,
                            investmentRecord.getAmount(),
                            investorFinance,
                            investmentRecord.getOrderNo(),
                            CommonUtils.getLoginName(),
                            CommonUtils.getRemoteIp(),
                            "投资人投资支出",
                            order.getId());
                    capitalService.persist(capital);
                    BigDecimal borrowingFee = lendingRecord.getAmount().multiply(borrowing.getFeeRate()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
                    successBorrowingAmount = successBorrowingAmount.add(lendingRecord.getAmount());
                    successBorrowingingFee = successBorrowingingFee.add(borrowingFee);
                } else {
                    lendingRecord.failure();
                    lendingRecordService.merge(lendingRecord);
                    booleanResults.add(false);
                }
            }

            // 更新投资
            investmentRecordService.flush();
            List<Investment> investments = investmentService.findList(borrowing.getId());
            origin : for(Investment investment : investments){
                List<InvestmentRecord> tnvestmentRecords = investmentRecordService.findListByInvestment(investment.getId());
                for(InvestmentRecord tnvestmentRecord : tnvestmentRecords){
                    if(!tnvestmentRecord.getState().equals(InvestmentState.SUCCESS)){
                        continue origin;
                    }
                }
                investment.setState(InvestmentState.SUCCESS);
                investmentService.merge(investment);
                userPointService.invest(investment); // 投资送积分
                userCouponService.createUserCoupon(CouponSource.INVESTMENT,investment.getInvestor());

            }

//            UserFinance borrowerFinance = userFinanceService.getByUserId(borrowerId);//
//            if (successBorrowingAmount.compareTo(BigDecimal.ZERO) > 0) {
//                // 借款人资金更新
//                borrowerFinance.addBalance(successBorrowingAmount, RechargeBusinessType.GENERAL);
//                borrowerFinance.addFrozen(successBorrowingAmount);
//                borrowerFinance.addBorrowingAmts(successBorrowingAmount);
//                userFinanceService.merge(borrowerFinance);
//                
//                Capital capital = new Capital(borrowerId,
//                        CapitalMethod.BORROWING,
//                        CapitalType.FROZEN,
//                        successBorrowingAmount,
//                        borrowerFinance,
//                        orderNo,
//                        CommonUtils.getLoginName(),
//                        CommonUtils.getRemoteIp(),
//                        "借款人收款",
//                        null);
//                capitalService.persist(capital);
//            }

            PaymentOrderStatus status;
            if (booleanResults.contains(true) && !booleanResults.contains(false)) {
                status = PaymentOrderStatus.SUCCESS;
            } else {
                status = PaymentOrderStatus.PROCESSING;
            }
            processOrder(orderNo, "", status);
            return booleanResults;
        } finally {
            lock.unLock(LockStack.USER_LOCK, "LENDING" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.TRANSFER && type == PaymentOrderType.LENDING;
    }

}
