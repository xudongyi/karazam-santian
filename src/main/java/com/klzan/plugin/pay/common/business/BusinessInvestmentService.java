package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.SnUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.recharge.RechargeService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.vo.investment.InvestVo;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.InvestmentRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.notice.Notice3218Request;
import payment.api.notice.NoticeRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投资
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessInvestmentService extends AbstractBusinessService {
    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private InvestmentRecordService investmentRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFinanceService userFinanceService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private PayUtils payUtils;
    @Autowired
    protected TransferService transferService;

    @Override
    public Result before(PayModule module) {
        InvestmentRequest request = (InvestmentRequest) module.getRequest();
        Borrowing borrowing = borrowingService.get(request.getProjectId());
        PaymentOrder paymentOrder = null;
        Integer userId = request.getUserId();
        if(request.getYesTransfer()){
            paymentOrder = createOrder(module, userId, PaymentOrderType.TRANSFER);

            Transfer transfer = transferService.get(request.getTransfer());
            User transferIner = userService.get(userId);  // 受让人
            User transferOuter = userService.get(transfer.getTransfer());  // 转让人
            UserFinance transferInFinance = userFinanceService.getByUserId(userId);
            UserFinance transferOutFinance = userFinanceService.getByUserId(transfer.getTransfer());
            BigDecimal transferCapital = new BigDecimal(request.getParts() * 100);
            BigDecimal inTransferFee = transferCapital.multiply(borrowing.getInTransferFeeRate()).divide(new BigDecimal(100));
            BigDecimal outTransferFee = transferCapital.multiply(borrowing.getOutTransferFeeRate()).divide(new BigDecimal(100));

            //新增投资
            Investment pInvestment = new Investment();
            pInvestment.setState(InvestmentState.INVESTING);
            pInvestment.setAmount(transferCapital);
            pInvestment.setPreferentialAmount(BigDecimal.ZERO);
            pInvestment.setBorrowing(borrowing.getId());
            pInvestment.setInvestor(userId);
            pInvestment.setOrderNo(module.getSn());
            pInvestment.setTransfer(transfer.getId());
            pInvestment = investmentService.persist(pInvestment);
            // TODO 生成投资记录
            InvestmentRecord investmentRecord = new InvestmentRecord();
            investmentRecord.setInvestment(pInvestment.getId());
            investmentRecord.setOperationMethod(OperationMethod.MANUAL);
            investmentRecord.setMethod(paymentOrder.getMethod());
            investmentRecord.setAmount(transferCapital);
            investmentRecord.setPreferentialAmount(BigDecimal.ZERO);
            investmentRecord.setBorrowing(borrowing.getId());
            investmentRecord.setInvestor(userId);
            investmentRecord.setOrderNo(module.getSn());
            investmentRecord.setOperator(CommonUtils.getLoginName());
            investmentRecord.setIp(CommonUtils.getRemoteIp());
            investmentRecord.setDeviceType(DeviceType.PC);
            investmentRecord.setState(InvestmentState.INVESTING);
            investmentRecord.setTransfer(true);
            investmentRecord.setTransferId(transfer.getId());
            investmentRecordService.persist(investmentRecord);

            //平台订单 : 受让人购买债权
            Order order = new Order();
            Integer investor = userId;
            Integer transferOuterId = transferOuter.getId();
            order.setUserId(investor);
            order.setPayer(investor);
            order.setPayerName(transferIner.getLoginName());
            order.setPayee(transferOuterId);
            order.setPayerName(transferOuter.getLoginName());
            order.setPayerBalance(transferInFinance.getAvailable());
            order.setPayerFee(inTransferFee);
            order.setPayeeBalance(transferOutFinance.getAvailable());
            order.setPayeeFee(outTransferFee);
            order.setStatus(OrderStatus.LAUNCH);
            order.setType(OrderType.TRANSFER_IN);
            order.setMethod(OrderMethod.CPCN);
            order.setBusiness(investmentRecord.getId());
            order.setOrderNo(paymentOrder.getOrderNo());
            order.setThirdOrderNo(null);
            order.setAmount(investmentRecord.getAmount().subtract(investmentRecord.getPreferentialAmount()));
            order.setAmountReceived(investmentRecord.getAmount());
            order.setPoints(0);
            order.setLaunchDate(new Date());
            order.setOperator(CommonUtils.getLoginName());
            order.setIp(CommonUtils.getRemoteIp());
            orderService.persist(order);
        }else {
            paymentOrder = createOrder(module, userId, PaymentOrderType.INVESTMENT);
            InvestVo investVo = new InvestVo();
            investVo.setInvestor(userId);
            investVo.setAmount(request.getAmount());
            investVo.setProjectId(request.getProjectId());
            investVo.setOperationMethod(request.getOperationMethod());
            investVo.setPaymentMethod(request.getPaymentMethod());
            investVo.setDeviceType(request.getDeviceType());
            investVo.setSn(paymentOrder.getOrderNo());
            // TODO 生成投资记录
            InvestmentRecord investmentRecord = investmentService.genInvestAndInvestmentRecord(investVo);

            User investor = userService.get(investVo.getInvestor());  // 投资人
            UserFinance investorFinance = userFinanceService.getByUserId(investVo.getInvestor());
            User borrower = userService.get(borrowing.getBorrower());  // 借款人
            UserFinance borrowerFinance = userFinanceService.getByUserId(borrowing.getBorrower());
            //平台订单 : 投资人投资
            Order order = new Order();
            order.setUserId(investmentRecord.getInvestor());
            order.setPayer(investmentRecord.getInvestor());
            order.setPayerName(investor.getLoginName());
            order.setPayee(borrower.getId());
            order.setPayerName(borrower.getLoginName());
            order.setStatus(OrderStatus.LAUNCH);
            order.setType(OrderType.INVESTMENT);
            order.setMethod(OrderMethod.CPCN);
            order.setBusiness(investmentRecord.getId());
            order.setOrderNo(paymentOrder.getOrderNo());
            order.setThirdOrderNo(null);
            order.setPayerBalance(investorFinance.getAvailable());
            order.setPayeeBalance(borrowerFinance.getAvailable());
            order.setAmount(investmentRecord.getAmount().subtract(investmentRecord.getPreferentialAmount()));
            order.setAmountReceived(investmentRecord.getAmount());
            order.setPoints(0);
            order.setLaunchDate(new Date());
            order.setOperator(investor.getLoginName());
            order.setIp(null == request ? "": CommonUtils.getRemoteIp());
            order.setMemo(investVo.getOperationMethod().getDisplayName() + paymentOrder.getType().getDisplayName());
            orderService.persist(order);
        }

        BigDecimal amount = request.getAmount();
        if (!borrowing.verifyInvest(amount) && !request.getYesTransfer()) {
            throw new BusinessProcessException("超过可投资金额");
        }

        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        String sn = module.getSn();
        InvestmentRecord investmentRecord = investmentRecordService.findByOrderNo(sn);
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
        paymentOrder.setAmount(investmentRecord.getAmount());
        InvestmentRequest request = (InvestmentRequest) module.getRequest();
        if (request.getYesTransfer()) {
            Borrowing borrowing = borrowingService.get(request.getProjectId());
            BigDecimal transferCapital = new BigDecimal(request.getParts() * 100);
            BigDecimal inTransferFee = transferCapital.multiply(borrowing.getInTransferFeeRate()).divide(new BigDecimal(100));
            paymentOrder.setFee(inTransferFee);
        }
        paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
        paymentOrderService.merge(paymentOrder);
        return result;
    }

    @Override
    public Result notice(String sn, NoticeRequest notice) {
        try {
            Notice3218Request txNotice = new Notice3218Request(notice.getDocument());
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
            if (null == paymentOrder) {
                LOGGER.error("项目支付通知处理异常[{}],支付订单不存在", sn);
                throw new PayException("项目支付通知处理异常");
            }
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderService.merge(paymentOrder);

            CpcnPayAccountInfo accountInfo = payUtils.getCpcnPayAccountInfo(paymentOrder.getUserId());
            BigDecimal amount = PayUtils.convertToYuan(txNotice.getAmount());
            String investorAccountNumber = txNotice.getLoanerPaymentAccountNumber();
            BigDecimal orderAmount = paymentOrder.getAmount();
            BigDecimal orderFee = paymentOrder.getFee();
            if (amount.compareTo(orderAmount.add(orderFee)) != 0) {
                LOGGER.error("项目支付通知处理异常[{}],投资金额与订单金额不一致", sn);
                throw new PayException("项目支付通知处理异常");
            }
            if (!StringUtils.equals(accountInfo.getAccountNumber(), investorAccountNumber)) {
                LOGGER.error("项目支付通知处理异常[{}],投资账号与订单账号不一致", sn);
                throw new PayException("项目支付通知处理异常");
            }
            if (txNotice.getPaymentWay().equals("20") || txNotice.getPaymentWay().equals("30")) {
                rechargeService.rechargeFromInvestSuccess(sn);
            }

            InvestmentRequest busReqParams = JsonUtils.toObject(paymentOrder.getBusReqParams(), InvestmentRequest.class);
            if(!busReqParams.getYesTransfer()){
                investmentService.invest(paymentOrder.getOrderNo());
            }else {
                Transfer transfer = transferService.get(busReqParams.getTransfer());
                User currentUser = userService.get(busReqParams.getUserId());
                Integer parts = busReqParams.getParts();
                transferService.transferInSucceed(paymentOrder.getOrderNo(), transfer, currentUser, parts);
            }

//            investmentService.invest(sn);
//            OrderStatus orderStatus = OrderStatus.FROZEN;
//            InvestmentRecord investmentRecord = investmentRecordService.findByOrderNo(sn);
//            orderService.updateOrderStatus(OrderType.INVESTMENT, investmentRecord.getId(), orderStatus, sn);

            return Result.success();
        } catch (Exception e) {
            throw new PayException("项目支付通知处理异常");
        }
    }
}
