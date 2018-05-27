package com.klzan.plugin.pay.common;

import com.klzan.core.exception.PayException;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.investment.InvestmentRecordDao;
import com.klzan.p2p.enums.BankCardStatus;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.p2p.service.user.CpcnBankCardService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.dto.SignedRequest;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import com.klzan.plugin.pay.cpcn.ChinaClearingConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PayUtils {

    protected static Logger logger = LoggerFactory.getLogger(PayUtils.class);

    private static Map<PaymentOrderType, PayPortal> QUERY_MAP = new HashMap<>();

    static {
        QUERY_MAP.put(PaymentOrderType.OPEN_ACCOUNT, PayPortal.open_account_query);
//        QUERY_MAP.put(PaymentOrderType.BINDCARD_BIND, PayPortal.bankcard_bind_query);
        QUERY_MAP.put(PaymentOrderType.RECHARGE, PayPortal.recharge_query);
        QUERY_MAP.put(PaymentOrderType.WITHDRAWAL, PayPortal.withdraw_query);
        QUERY_MAP.put(PaymentOrderType.SIGNED, PayPortal.payment_account_signed_query);
        QUERY_MAP.put(PaymentOrderType.INVESTMENT, PayPortal.invest_query);
        QUERY_MAP.put(PaymentOrderType.AUTO_INVESTMENT, PayPortal.invest_auto_query);
        QUERY_MAP.put(PaymentOrderType.TRANSFER, PayPortal.invest_query);
        QUERY_MAP.put(PaymentOrderType.REPAYMENT, PayPortal.repayment_query);
        QUERY_MAP.put(PaymentOrderType.SETTLEMENT_BATCH, PayPortal.project_settlement_batch_query);
        QUERY_MAP.put(PaymentOrderType.ORG_TRANSFER, PayPortal.org_transfer_query);
    }

    @Autowired
    private UserService userService;
    @Autowired
    private CpcnAccService cpcnAccService;
    @Autowired
    private CpcnBankCardService cpcnBankCardService;
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private RepaymentService repaymentService;
    @Autowired
    private InvestmentRecordDao investmentRecordDao;
    @Autowired
    private BorrowingDao borrowingDao;
    @Autowired
    private PaymentOrderService paymentOrderService;

    /**
     * 金额转换（元转分）
     */
    public static String convertToFenStr(BigDecimal amount){
        return String.valueOf(amount.multiply(new BigDecimal(100)).intValue());
    }

    /**
     * 金额转换（元转分）
     */
    public static long convertToFen(BigDecimal amount){
        return amount.multiply(new BigDecimal(100)).intValue();
    }

    /**
     * 金额转换（分转元）
     */
    public static BigDecimal convertToYuan(String amount){
        return new BigDecimal(amount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 金额转换（分转元）
     */
    public static BigDecimal convertToYuan(long amount){
        return new BigDecimal(amount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
    }

    public static int rateConvert(BigDecimal rate) {
        DecimalFormat format = new DecimalFormat("#.##");
        String rateStr = format.format(rate.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN));
        return Integer.parseInt(rateStr);

    }
//
//    /* 用户ID */
//    public Integer getUserId(){
//        return getUserId(null);
//    }
//    public Integer getUserId(Integer userId){
//        User user;
//        if(userId == null){
//            user = UserUtils.getCurrentUser();
//        }else {
//            user = huaShanUserService.find(userId);
//        }
//        return user.getId();
//    }
//
        /* 用户 */
    public UserVo getUserVo(Integer userId){
        UserVo userVo = userService.getUserById(userId);
        return userVo;
    }

    /* 用户 */
    public User getUser(Integer userId){
        User user = userService.get(userId);
        return user;
    }

    /* 用户ID */
    public String getMobile(){
        return getMobile(null);
    }
    public String getMobile(Integer userId){
        User user;
        if(userId == null){
            user = UserUtils.getCurrentUser();
        }else {
            user = userService.get(userId);
        }
        return user.getMobile();
    }
//
    /* 用户类型 */
    public String getUserType(){
        return getUserType(null);
    }
    public String getUserType(Integer userId){
        User user;
        if(userId == null){
            user = UserUtils.getCurrentUser();
        }else {
            user = userService.get(userId);
        }
        if(user.getType().equals(UserType.ENTERPRISE)){
            return ChinaClearingConstant.UserType.enterprise;
        }
        return ChinaClearingConstant.UserType.personal;
    }

    /* 用户 */
    public CpcnPayAccountInfo getCpcnPayAccountInfo(Integer userId){
        logger.info("用户{}支付账户信息查询", userId);
        return cpcnAccService.findByUserId(userId);
    }
    public CpcnPayAccountInfo getCpcnPayAccountInfo(String accountNumber){
        return cpcnAccService.findByPayAcc(accountNumber);
    }

    public List<CpcnBankCard> getUserBankCards(Integer userId) {
        return cpcnBankCardService.findByUserId(userId, BankCardStatus.BINDED);
    }

    public Borrowing getBorrowing(Integer borrowing){
        return borrowingService.get(borrowing);
    }

    public Repayment getRepayment(Integer repayment){
        return repaymentService.get(repayment);
    }

    public long getRepaymentAmount(Integer repayment){
        return repaymentService.repaymentAmount(repayment).multiply(new BigDecimal(100)).longValue();
    }

    public long getAheadRepaymentAmount(Integer borrowing){
        return repaymentService.aheadRepaymentAmount(borrowing).multiply(new BigDecimal(100)).longValue();
    }

//    /* 用户支付账户号码 */
//    public String getPaymentAccountNumber(){
//        return getPaymentAccountNumber(null);
//    }
    public String getPaymentAccountNumber(Integer userId){
        return getCpcnPayAccountInfo(userId).getAccountNumber();
    }
//
//    /**
//     * 用户姓名/法人姓名
//     */
//    public String getPaymentAccountUsername(){
//        return getPaymentAccountUsername(null);
//    }
    public String getPaymentAccountUsername(Integer userId){
        return getCpcnPayAccountInfo(userId).getAccountUsername();
    }
//
//    /**
//     * 用户身份证号/法人身份证
//     */
//    public String getIdNo(){
//        return getIdNo(null);
//    }
//    public String getIdNo(Integer userId){
//        User user = null;
//        if(userId == null){
//            user = UserUtils.getCurrentUser();
//        }else {
//            user = huaShanUserService.find(userId);
//        }
//        if(user.getCorp()){
//            CorporationLegal corporationLegal = corporationLegalService.findCorporationLegalByUserId(user.getId());
//            return corporationLegal.getCorporationIdCard();
//        }else {
//            UserInfo userInfo = huaShanUserService.getUserInfo(user.getId());
//            return userInfo.getIdNo();
//        }
//    }
//
//    /**
//     * 通过项目ID生成项目编号
//     */
//    public String generateProjectNo(Integer borrowingId){
//        String projectNo = "";
//        if(borrowingId != null){
//            Borrowing borrowing = borrowingDao.find(borrowingId);
//            if(borrowing != null){
//                projectNo = projectNo + ChinaClearingConfig.INSTITUTION_ID;
//                projectNo = projectNo + DateUtils.format(borrowing.getCreateDate(), DateUtils.DATE_PATTERN_NUMBER);
//                projectNo = projectNo + borrowing.getId().intValue();
//            }
//        }
//        System.out.println(projectNo);
//        return projectNo;
//    }
//
//    /**
//     * 通过项目ID获取项目编号
//     */
//    public String getProjectNo(Integer borrowingId){
//        String projectNo = "";
//        if(borrowingId != null){
//            Borrowing borrowing = borrowingDao.find(borrowingId);
//            if(borrowing != null){
//                projectNo = borrowing.getCpcnProjectNo();
//            }
//        }
//        return projectNo;
//    }

    /**
     * 通过项目ID获取项目
     */
    public Borrowing getProject(Integer borrowingId) {
        Borrowing borrowing = borrowingDao.get(borrowingId);
        if (null == borrowing) {
            throw new PayException("项目不存在");
        }
        return borrowing;
    }
//
//    /**
//     * 通过项目编号获取项目
//     */
//    public Borrowing getBorrowing(String projectNo){
//        Borrowing borrowing = null;
//        if(StringUtils.isNotBlank(projectNo)){
//            borrowing = borrowingDao.findByCpcnProjectNo(projectNo);
//        }
//        return borrowing;
//    }
//
    /**
     * 支付投资交易流水号，还款给投资人时需要
     */
    public String getPaymentNo(Integer borrowingId, Integer userId){
        investmentRecordDao.flush();
        List<InvestmentRecord> investmentRecords = investmentRecordDao.findList(borrowingId, userId);
        for(InvestmentRecord investmentRecord : investmentRecords){
            if(investmentRecord.getState().equals(InvestmentState.SUCCESS) || investmentRecord.getState().equals(InvestmentState.PAID)/*&& investmentRecord.getAmount().compareTo(investmentRecord.getTransferedAmount())>0*/){
                return investmentRecord.getOrderNo();
            }
        }
        throw new RuntimeException("不存在投资流水号");
    }

    public Response query(String sn) {
        Response response = null;
        try {
            if(sn == null){
                return Response.error("订单号不存在");
            }
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
            if(paymentOrder == null){
                return Response.error("订单不存在");
            }

            if (QUERY_MAP.containsKey(paymentOrder.getType())) {
                PayPortal type = QUERY_MAP.get(paymentOrder.getType());
                PayModule payModule = type.getModuleInstance();
                if (type.getRequestClazz().getSimpleName().equals(SnRequest.class.getSimpleName())) {
                    SnRequest request = new SnRequest();
                    request.setSn(sn);
                    payModule.setRequest(request);
                } else if (type.getRequestClazz().getSimpleName().equals(SignedRequest.class.getSimpleName())) {
                    SignedRequest request = new SignedRequest();
                    request.setUserId(paymentOrder.getUserId());
                    request.setOrderType(paymentOrder.getType());
                    request.setQueryOrderNo(sn);
                    request.setAgreementType("20");
                    payModule.setRequest(request);
                } else if (type.getRequestClazz().getSimpleName().equals(UserInfoRequest.class.getSimpleName())) {
                    UserInfoRequest request = new UserInfoRequest();
                    request.setUserId(paymentOrder.getUserId());
                    payModule.setRequest(request);
                } else {
                    throw new PayException();
                }
                return payModule.invoking().getResponse();
            } else {
                return Response.error("订单查询类型不正确");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = Response.error();
        }
        return response;
    }


}
