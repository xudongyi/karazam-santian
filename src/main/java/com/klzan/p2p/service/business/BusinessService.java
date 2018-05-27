package com.klzan.p2p.service.business;

import com.klzan.core.Result;
import com.klzan.core.SpringObjectFactory;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.exception.SystemException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.*;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.order.OrderDao;
import com.klzan.p2p.dao.postloan.RepaymentDao;
import com.klzan.p2p.dao.user.UserDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingNoticeService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.LendingRecordService;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.coupon.UserCouponService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.recharge.RechargeService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.repayment.RepaymentTransferRecordService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.*;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.vo.business.Request;
import com.klzan.p2p.vo.investment.InvestVo;
import com.klzan.p2p.vo.recharge.RechargeVo;
import com.klzan.p2p.vo.user.*;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayGeneratorContext;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.appRecharge.vo.IpsPayAppRechargeRequest;
import com.klzan.plugin.pay.ips.assureproject.vo.IpsPayAssureProjectRequest;
import com.klzan.plugin.pay.ips.assureproject.vo.IpsPayAssureProjectResponse;
import com.klzan.plugin.pay.ips.autosign.vo.IpsPayAutoSignRequest;
import com.klzan.plugin.pay.ips.combfreeze.vo.IpsPayCombFreezeRequest;
import com.klzan.plugin.pay.ips.combfreeze.vo.RedPacketRequest;
import com.klzan.plugin.pay.ips.comquery.vo.IpsPayCommonQueryRequest;
import com.klzan.plugin.pay.ips.comquery.vo.IpsPayCommonQueryResponse;
import com.klzan.plugin.pay.ips.frozen.vo.IpsPayFrozenRequest;
import com.klzan.plugin.pay.ips.openacc.vo.IpsPayOpenAccountRequest;
import com.klzan.plugin.pay.ips.querybank.vo.IpsPayQueryBankRequest;
import com.klzan.plugin.pay.ips.querybank.vo.IpsPayQueryBankResponse;
import com.klzan.plugin.pay.ips.recharge.vo.IpsPayRechargeRequest;
import com.klzan.plugin.pay.ips.regproject.vo.IpsPayRegProjectRequest;
import com.klzan.plugin.pay.ips.regproject.vo.IpsPayRegProjectResponse;
import com.klzan.plugin.pay.ips.transfer.vo.IpsPayTransferRequest;
import com.klzan.plugin.pay.ips.transfer.vo.IpsPayTransferResponse;
import com.klzan.plugin.pay.ips.transfer.vo.TransferAccDetailRequest;
import com.klzan.plugin.pay.ips.unfreeze.vo.IpsPayUnfreezeRequest;
import com.klzan.plugin.pay.ips.unfreeze.vo.IpsPayUnfreezeResponse;
import com.klzan.plugin.pay.ips.userlogin.vo.IpsPayUserLoginRequest;
import com.klzan.plugin.pay.ips.withdraw.vo.IpsPayWithdrawRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by suhao Date: 2017/4/1 Time: 17:38
 *
 * @version: 1.0
 */
@Service
public class BusinessService {
    protected static Logger logger = LoggerFactory.getLogger(BusinessService.class);

    public static final String DEV_PREFIX = "1";
    public static final String TEST_PREFIX = "2";
    public static final String PROD_PREFIX = "3";

    @Autowired
    private BusinessProcessService businessProcessService;

    @Autowired
    private PayGeneratorContext payGeneratorContext;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFinanceService userFinanceService;

    @Autowired
    private UserMetaService userMetaService;

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private CorporationService corporationService;

    @Autowired
    private CorporationLegalService corporationLegalService;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private InvestmentRecordService investmentRecordService;

    @Autowired
    private LendingRecordService lendingRecordService;

    @Autowired
    private RepaymentTransferRecordService repaymentTransferRecordService;

    @Autowired
    private ReferralService referralService;

    @Autowired
    private ReferralFeeService referralFeeService;

    @Autowired
    private RepaymentDao repaymentDao;

    @Autowired
    private BorrowingDao borrowingDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private RepaymentPlanService repaymentPlanService;

    @Autowired
    private AccountantService accountantService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private DistributeLock lock;
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private BorrowingNoticeService borrowingNoticeService;
    /**
     * 用户开户
     *
     * @param user
     * @return
     */
    public Request openAccount(User user) {
        Integer userId = user.getId();
        UserVo userVo = userService.getUserById(userId);
        if(StringUtils.isBlank(userVo.getRealName())){
            throw new BusinessProcessException("未实名认证");
        }
        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                PaymentOrderType.OPEN_ACCOUNT,
                SnUtils.getOrderNo(),
                null,
                BigDecimal.ZERO,
                String.format("用户[%s]开户", userId),
                null);
        String enterName = "";
        String orgCode = "";
        String isAssureCom = "";
        boolean isPerson = user.getType().isPerson();
        if (!isPerson) {
            CorporationLegal corporationLegal = corporationLegalService.findCorporationLegalByUserId(userId);
            if (null == corporationLegal) {
                throw new BusinessProcessException("法人信息不存在");
            }
            Corporation corporation = corporationService.findCorporationByUserId(userId);
            if (null == corporation) {
                throw new BusinessProcessException("公司信息不存在");
            }
            enterName = corporation.getCorpName();
            orgCode = corporation.getCorpLicenseNo();
            if (StringUtils.isBlank(enterName)) {
                throw new BusinessProcessException("公司名称不能为空");
            }
            if (StringUtils.isBlank(orgCode)) {
                throw new BusinessProcessException("公司执照编号不能为空");
            }
            isAssureCom = corporation.getEnterpriseGuaranteeAbilityState() == EnterpriseGuaranteeAbility.SUCCESS ? "1" : "0";
        }
        String userTag = user.getType().getTag();

        Map map = (Map) payGeneratorContext.generateRequest(new IpsPayOpenAccountRequest(
                paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                isPerson ? "1" : "2",
                userTag + user.getMobile(),
                isPerson ? user.getMobile() : user.getLegalMobile(),
                userVo.getIdNo(),
                userVo.getRealName(),
                enterName,
                orgCode,
                isAssureCom));
        paymentOrder.setReqParams(JsonUtils.toJson(map));
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        return new Request(IpsPayConfig.REQUEST_URL, map);
    }

    /**
     * 托管登录
     * @param user
     * @return
     */
    public Request login(User user) {
        Integer userId = user.getId();
        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                PaymentOrderType.USER_LOGIN,
                SnUtils.getOrderNo(),
                null,
                BigDecimal.ZERO,
                String.format("用户[%s]托管登录", userId),
                null);
        UserOpenIpsAcctVo ipsAcctVo = userMetaService.convertToBean(UserMetaType.IPS_OPEN_ACCT, userId, UserOpenIpsAcctVo.class);
        String ipsLoginName = new StringBuilder(IpsPayConfig.USER_NAME_PREFIX).append(user.getType().getTag()).append(user.getMobile()).toString();
        if (null != ipsAcctVo && StringUtils.isNotBlank(ipsAcctVo.getIpsLoginName())) {
            ipsLoginName = ipsAcctVo.getIpsLoginName();
        }
        Map map = (Map) payGeneratorContext.generateRequest(new IpsPayUserLoginRequest(ipsLoginName));
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        return new Request(IpsPayConfig.LOGIN_URL, map);
    }

    /**
     * 托管账户余额查询
     * @param user
     * @return
     */
    public Result balanceQuery(User user) {
        Integer userId = user.getId();
        UserVo userVo = userService.getUserById(userId);
        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                PaymentOrderType.USER_INFO_QUERY,
                SnUtils.getOrderNo(),
                null,
                BigDecimal.ZERO,
                String.format("用户[%s]查询余额", userId),
                null);
        String result = (String) payGeneratorContext.generateRequest(new IpsPayCommonQueryRequest(
                "03",
                userVo.getPayAccountNo()
        ));
        logger.info("result:{}", result);
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
            IpsPayCommonQueryResponse responseResult = (IpsPayCommonQueryResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
            logger.info(JsonUtils.toJson(responseResult));
            return Result.success("查询成功", responseResult);
        }
        return Result.error();
    }

    /**
     * 获取银行列表
     *
     * @return
     */
    public IDetailResponse getBanks() {
        String result = (String) payGeneratorContext.generateRequest(new IpsPayQueryBankRequest());
        logger.info("get banks result:{}", result);
        if (payGeneratorContext.verifySign(result, BusinessType.QUERY_BANK)) {
            IpsPayQueryBankResponse responseResult = (IpsPayQueryBankResponse) payGeneratorContext.getResponseResult(result, BusinessType.QUERY_BANK);
            logger.info(JsonUtils.toJson(responseResult));
            return responseResult;
        }
        throw new BusinessProcessException("获取银行列表失败");
    }

    /**
     * 用户充值
     *
     * @param user
     * @param rechargeVo
     * @return
     */
    public Request recharge(User user, RechargeVo rechargeVo) {
        Integer userId = user.getId();
        RechargeRecord rechargeRecord  = rechargeService.addRecord(rechargeVo);
        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                PaymentOrderType.RECHARGE,
                rechargeRecord.getOrderNo(),
                null,
                rechargeVo.getAmount(),
                String.format("用户[%s]充值", userId),
                null);
        String channelType = "1";
        String userType = "1";
        boolean isPerson = user.getType().isPerson();
        if (!isPerson) {
            channelType = "2";
            userType = "2";
        }
        String depositType = rechargeVo.getRechargeBusType().getRechargeBusinessType();
        Map map = (Map) payGeneratorContext.generateRequest(new IpsPayRechargeRequest(
                paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                depositType,
                channelType,
                rechargeVo.getBankCode(),
                userType,
                user.getPayAccountNo(),
                paymentOrder.getAmountString(),
                rechargeRecord.getPlatformAssumeIpsFee() ? "1" : "2",
                "0.00",
                rechargeRecord.getDeductionType().getDeduction().toString(),
                "2"));
        paymentOrder.setReqParams(JsonUtils.toJson(map));
        rechargeService.persist(rechargeRecord);
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        return new Request(IpsPayConfig.REQUEST_URL, map);
    }

    /**
     * 移动用户充值
     */
    public Request appRecharge(User user, RechargeVo rechargeVo) {
        Integer userId = user.getId();
        RechargeRecord rechargeRecord  =rechargeService.addRecord(rechargeVo);
        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                PaymentOrderType.MOBILE_RECHARGE,
                rechargeRecord.getOrderNo(),
                null,
                rechargeVo.getAmount(),
                String.format("用户[%s]充值", userId),
                null);
        String channelType = "1";
        String userType = "1";
        boolean isPerson = user.getType().isPerson();
        if (!isPerson) {
            channelType = "2";
            userType = "2";
        }
        String depositType = rechargeVo.getRechargeBusType().getRechargeBusinessType();
        Map map = (Map) payGeneratorContext.generateRequest(new IpsPayAppRechargeRequest(
                paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                "1",
                user.getPayAccountNo(),
                userType,
                paymentOrder.getAmountString(),
                rechargeRecord.getPlatformAssumeIpsFee() ? "1" : "2",
                "0.00",
                rechargeRecord.getDeductionType().getDeduction().toString(),
                null
               )

        );
        paymentOrder.setReqParams(JsonUtils.toJson(map));
        rechargeService.persist(rechargeRecord);
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        return new Request(IpsPayConfig.REQUEST_URL, map);
    }
    /**
     * 用户提现
     *
     * @param user
     * @param record
     * @return
     */
    public Request withdraw(User user, WithdrawRecord record) {
        Integer userId = user.getId();
        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                PaymentOrderType.WITHDRAWAL,
                record.getOrderNo(),
                null,
                record.getAmount(),
                String.format("用户[%s]提现", userId),
                null);
        boolean isPerson = user.getType().isPerson();
        Map map = (Map) payGeneratorContext.generateRequest(new IpsPayWithdrawRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                isPerson ? "1" : "2",
                paymentOrder.getAmountString(),
                record.getFee().toString(),
                record.getDeductionType().getDeduction().toString(),
                record.getPlatformAssumeIpsFee() ? "1" : "2",
                user.getPayAccountNo()));
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();

        // 添加定时任务查询订单
//        ScheduleJob scheduleJob = new ScheduleJob();
//        scheduleJob.setBeanName("withdrawOrderQueryTask");
//        scheduleJob.setMethodName("orderQuery");
//        scheduleJob.setParams(withdrawalVo.getOrderNo());
//        scheduleJob.setCronExpression("0/30 * * * * ? ");
//        scheduleJob.setRemark("提现订单[" + withdrawalVo.getOrderNo() + "]查询任务");
//        scheduleJobService.addJob(scheduleJob);

        return new Request(IpsPayConfig.REQUEST_URL, map);
    }

    /**
     * 项目登记
     *
     * @param borrowing
     * @return
     */
    public boolean addRegProject(Borrowing borrowing) {
        Integer borrower = borrowing.getBorrower();
        UserVo user = userService.getUserById(borrower);
        PaymentOrder paymentOrder = new PaymentOrder(false,
                borrower,
                PaymentOrderType.REGIST_PROJECT,
                SnUtils.getOrderNo(),
                null,
                borrowing.getAmount(),
                String.format("标的[%s]登记", borrowing.getId()),
                null);
        String projectNo = getEnv() + DateUtils.format(paymentOrder.getCreateDate(), DateUtils.DATE_PATTERN_NUMBER_S) + borrowing.getId();
        Integer projectCyc = 0;
        switch (borrowing.getPeriodUnit()) {
            case DAY: {
                projectCyc = borrowing.getPeriod();
                break;
            }
            case MONTH: {
                projectCyc = borrowing.getPeriod() * 30;
                break;
            }
            default: {
                throw new BusinessProcessException("项目期限单位错误");
            }
        }
        if (null == projectCyc || projectCyc <= 0) {
            throw new BusinessProcessException("项目期限单位错误");
        }
        IpsPayRegProjectRequest request = new IpsPayRegProjectRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                projectNo,
                borrowing.getTitle(),
                "1",
                paymentOrder.getAmountString(),
                "1",
                borrowing.getRealInterestRate().toString(),
                "",
                "",
                projectCyc.toString(),
                borrowing.getPurpose(),
                user.getType() == UserType.GENERAL ? "1" : "2",
                user.getIdNo(),
                user.getRealName(),
                user.getPayAccountNo(),
                "0");
        String result = (String) payGeneratorContext.generateRequest(request);
        logger.info("result:{}", result);
        paymentOrder.setReqParams(JsonUtils.toJson(request));
        paymentOrder.setResParams(result);
        paymentOrder.setIp(WebUtils.getRemoteIp(WebUtils.getHttpRequest()));
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
            IpsPayRegProjectResponse responseResult = (IpsPayRegProjectResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
            logger.info(JsonUtils.toJson(responseResult));
            paymentOrder.setExtOrderNo(responseResult.getIpsBillNo());
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderService.merge(paymentOrder);

            borrowing.setOrderId(paymentOrder.getId());
            borrowing.setOrderNo(paymentOrder.getOrderNo());
            borrowing.setProjectNo(projectNo);
            borrowingService.update(borrowing);
            return true;
        }
        return false;
    }

    /**
     * 项目追加登记
     *
     * @param borrowing
     * @return
     */
    public boolean addAssureProject(Borrowing borrowing) {
        Integer borrower = borrowing.getBorrower();
        UserVo user = userService.getUserById(borrower);
        PaymentOrder paymentOrder = new PaymentOrder(false,
                borrower,
                PaymentOrderType.ASSURE_PROJECT,
                SnUtils.getOrderNo(),
                null,
                BigDecimal.ZERO,
                String.format("标的[%s]追加登记", borrowing.getId()),
                null);
        String result = (String) payGeneratorContext.generateRequest(new IpsPayAssureProjectRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                borrowing.getProjectNo(),
                "500",
                "5",
                "1",
                "100000123990"));
        logger.info("assure project result:{}", result);
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
            IpsPayAssureProjectResponse responseResult = (IpsPayAssureProjectResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
            logger.info(JsonUtils.toJson(responseResult));
            return true;
        }
        return false;
    }

    /**
     * 投资冻结
     * @param user
     * @param borrowing
     * @param orderType
     * @return
     */
    public Request investmentFrozen(HttpServletRequest request, InvestVo vo, User user, Borrowing borrowing, PaymentOrderType orderType) {
        return frozen(request, vo, user, borrowing, orderType);
    }

    /**
     * 债权转让-购买冻结
     * @param request
     * @param transfer
     * @param parts
     * @param user
     * @param borrowing
     * @param orderType
     * @return
     */
    public Request transferFrozen(HttpServletRequest request, Transfer transfer, Integer parts, User user, Borrowing borrowing, PaymentOrderType orderType) {
        if (orderType != PaymentOrderType.TRANSFER_FROZEN) {
            throw new BusinessProcessException("冻结业务类型不正确");
        }
        int userId = user.getId();
        BigDecimal transferCapital = new BigDecimal(parts * 100);
        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                orderType,
                SnUtils.getOrderNo(),
                null,
                transferCapital,
                String.format("用户[%s][%s]冻结", userId, orderType.getDisplayName()),
                null);
        String projectNo = "";
        if (null != borrowing) {
            projectNo = borrowing.getProjectNo();
        }
        BigDecimal inTransferFee = transferCapital.multiply(borrowing.getInTransferFeeRate()).divide(new BigDecimal(100));
        BigDecimal outTransferFee = transferCapital.multiply(borrowing.getOutTransferFeeRate()).divide(new BigDecimal(100));
        IpsPayFrozenRequest frozenRequest = new IpsPayFrozenRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                projectNo,
                "2",
                "1",
                "",
                "",
                paymentOrder.getAmountString(),
                inTransferFee.toString(),
                "1",
                user.getPayAccountNo(),
                user.getPayAccountNo());
        Map map = (Map) payGeneratorContext.generateRequest(frozenRequest);
        logger.info("frozen result:{}", map);
        paymentOrder.setReqParams(JsonUtils.toJson(frozenRequest));
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();

        // TODO 生成投资记录
        InvestmentRecord investmentRecord = new InvestmentRecord();
        investmentRecord.setOperationMethod(OperationMethod.MANUAL);
        investmentRecord.setMethod(paymentOrder.getMethod());
        investmentRecord.setAmount(transferCapital);
        investmentRecord.setPreferentialAmount(BigDecimal.ZERO);
        investmentRecord.setBorrowing(borrowing.getId());
        investmentRecord.setInvestor(user.getId());
        investmentRecord.setOrderNo(paymentOrder.getOrderNo());
        investmentRecord.setOperator(CommonUtils.getLoginName());
        investmentRecord.setIp(CommonUtils.getRemoteIp());
        investmentRecord.setDeviceType(DeviceType.PC);
        investmentRecord.setState(InvestmentState.INVESTING);
        investmentRecord.setTransfer(true);
        investmentRecord.setTransferId(transfer.getId());
        investmentRecordService.persist(investmentRecord);

        User transferIner = userDao.get(userId);  // 受让人
        User transferOuter = userDao.get(transfer.getTransfer());  // 转让人
        UserFinance transferInFinance = userFinanceService.getByUserId(userId);
        UserFinance transferOutFinance = userFinanceService.getByUserId(transfer.getTransfer());
        //平台订单 : 受让人购买债权
        Order order = new Order();
        Integer investor = investmentRecord.getInvestor();
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
        order.setMethod(OrderMethod.IPS);
        order.setBusiness(investmentRecord.getId());
        order.setOrderNo(SnUtils.getOrderNo());
        order.setThirdOrderNo(null);
        order.setAmount(investmentRecord.getAmount().subtract(investmentRecord.getPreferentialAmount()));
        order.setAmountReceived(investmentRecord.getAmount());
        order.setPoints(0);
        order.setLaunchDate(new Date());
        order.setOperator(transferIner.getLoginName());
        order.setIp(WebUtils.getRemoteIp(request));
        orderDao.persist(order);

        //平台订单 : 转让人转让债权
        order = new Order();
        order.setUserId(transferOuterId);
        order.setPayer(investor);
        order.setPayerName(transferIner.getLoginName());
        order.setPayee(transferOuterId);
        order.setPayerName(transferOuter.getLoginName());
        order.setPayerBalance(transferInFinance.getAvailable());
        order.setPayerFee(inTransferFee);
        order.setPayeeBalance(transferOutFinance.getAvailable());
        order.setPayeeFee(outTransferFee);
        order.setStatus(OrderStatus.LAUNCH);
        order.setType(OrderType.TRANSFER_OUT);
        order.setMethod(OrderMethod.IPS);
        order.setBusiness(investmentRecord.getId());
        order.setOrderNo(SnUtils.getOrderNo());
        order.setThirdOrderNo(null);
        order.setAmount(investmentRecord.getAmount().subtract(investmentRecord.getPreferentialAmount()));
        order.setAmountReceived(investmentRecord.getAmount());
        order.setPoints(0);
        order.setLaunchDate(new Date());
        order.setOperator(transferIner.getLoginName());
        order.setIp(WebUtils.getRemoteIp(request));
        orderDao.persist(order);
        return new Request(IpsPayConfig.REQUEST_URL, map);
    }

    /**
     * 债权转让-购买转账
     * @return
     */
    public boolean transferDo(Transfer transfer, Investment investment, InvestmentRecord investmentRecord, String ipsBillNo) {
        String batchOrderNo = SnUtils.getOrderNo();
        try {
            lock.lock(LockStack.ORDER_NO_LOCK, batchOrderNo);
            Borrowing borrowing = borrowingService.get(investment.getBorrowing());
            User transferOuter = userService.get(transfer.getTransfer()); // 转出人
            User transferIner = userService.get(investment.getInvestor()); // 受让人

            List<LendingRecord> lendingRecordList = lendingRecordService.addRecord(investment, batchOrderNo);
            List<TransferAccDetailRequest> transferAccDetails = new ArrayList<>();
            for (LendingRecord lendingRecord : lendingRecordList) {
                batchOrderNo = lendingRecord.getBatchOrderNo();
                String investOrderNo = lendingRecord.getInvestOrderNo();
                String orderNo = lendingRecord.getOrderNo();
                PaymentOrder investOrder = paymentOrderService.findByOrderNo(investOrderNo);
                BigDecimal inMerFee = lendingRecord.getAmount().multiply(borrowing.getOutTransferFeeRate()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
                BigDecimal outMerFee = lendingRecord.getAmount().multiply(borrowing.getInTransferFeeRate()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
                TransferAccDetailRequest transferAccDetail = new TransferAccDetailRequest(orderNo,
                        ipsBillNo,
                        transferIner.getPayAccountNo(),
                        outMerFee.toString(),
                        transferOuter.getPayAccountNo(),
                        inMerFee.toString(),
                        investOrder.getAmountString());
                transferAccDetails.add(transferAccDetail);
            }

            PaymentOrder paymentOrder = new PaymentOrder(false,
                    transferIner.getId(),
                    PaymentOrderType.TRANSFER,
                    batchOrderNo,
                    null,
                    investmentRecord.getAmount(),
                    String.format("债权[%s]转让", transfer.getId()),
                    null);
            IpsPayTransferRequest request = new IpsPayTransferRequest(
                    batchOrderNo,
                    DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                    borrowing.getProjectNo(),
                    "2",
                    "2",
                    "1",
                    transferAccDetails
            );
            String result = (String) payGeneratorContext.generateRequest(request);
            logger.info("transfer do result:{}", result);
            paymentOrder.setReqParams(JsonUtils.toJson(request));
            Boolean transferSuccess = false;
            paymentOrderService.persist(paymentOrder);
            paymentOrderService.flush();
            if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
                IpsPayTransferResponse responseResult = (IpsPayTransferResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
                transferSuccess = businessProcessService.process(batchOrderNo, responseResult, BusinessType.TRANSFER, PaymentOrderType.TRANSFER);
            }
            return transferSuccess;
        } finally {
            lock.unLock(LockStack.ORDER_NO_LOCK, batchOrderNo);
        }
    }

    /**
     * 出借
     * @param borrowing
     * @return
     */
    public boolean lending(Borrowing borrowing) {
        // 生成出借记录
        Integer borrowingId = borrowing.getId();
        List<LendingRecord> lendingRecords = lendingRecordService.findByBorrowing(borrowingId);
        String batchOrderNo;
        if (lendingRecords.isEmpty()) {
            List<Investment> investments = investmentService.findList(borrowingId, InvestmentState.INVESTING);
            batchOrderNo = SnUtils.getOrderNo();
            for (Investment investment : investments) {
                List<LendingRecord> lendingRecordList = lendingRecordService.addRecord(investment, batchOrderNo);
                lendingRecords.addAll(lendingRecordList);
            }
        } else {
            List<LendingRecord> newRecords = lendingRecordService.findByBorrowingStatus(borrowingId, RecordStatus.NEW_CREATE);
            List<LendingRecord> failureRecords = lendingRecordService.findByBorrowingStatus(borrowingId, RecordStatus.FAILURE);
            lendingRecords.clear();
            lendingRecords.addAll(newRecords);
            lendingRecords.addAll(failureRecords);
            batchOrderNo = SnUtils.getOrderNo();
            for (LendingRecord lendingRecord : lendingRecords) {
                lendingRecord.updateBatchOrderNo(batchOrderNo);
                lendingRecordService.merge(lendingRecord);
            }
        }
        Set<Boolean> booleanResults = new HashSet<>();
        User borrower = userService.get(borrowing.getBorrower());
        Integer borrowerId = borrower.getId();
        // 环迅转账接口调用次数，每次出借最多200比投资转账
        int lendingItemSize = lendingRecords.size();
        // 每次组装数据条数
        int lendingPerItems = 200;
        SpringObjectFactory.Profile profile = SpringObjectFactory.getActiveProfile();
        if (profile != SpringObjectFactory.Profile.PROD) {
            lendingPerItems = 5;
        }

        int requestInterfaceCount = new BigDecimal(lendingItemSize).divide(new BigDecimal(lendingPerItems), 0, BigDecimal.ROUND_UP).intValue();
        logger.info("借款[{}]出借调用转账接口次数：{}", borrowingId, requestInterfaceCount);
        for (int i = 1; i <= requestInterfaceCount; i++) {
            if (i > 1) {
                batchOrderNo = SnUtils.getOrderNo();
            }
            logger.info("借款[{}]出借第{}次调用转账接口开始", borrowingId, i);
            List<TransferAccDetailRequest> transferAccDetails = new ArrayList<>();
            BigDecimal lendingAmount = BigDecimal.ZERO;
            for (int j = (i - 1) * lendingPerItems; j + 1 <= lendingItemSize && j < i * lendingPerItems; j++) {
                LendingRecord lendingRecord = lendingRecords.get(j);
                if (i > 1) {
                    lendingRecord.updateBatchOrderNo(batchOrderNo);
                    lendingRecordService.merge(lendingRecord);
                }
                batchOrderNo = lendingRecord.getBatchOrderNo();
                String investOrderNo = lendingRecord.getInvestOrderNo();
                String orderNo = lendingRecord.getOrderNo();
                PaymentOrder investOrder = paymentOrderService.findByOrderNo(investOrderNo);
                User investor = userService.get(investOrder.getUserId());
                BigDecimal inMerFee = lendingRecord.getAmount().multiply(borrowing.getFeeRate()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
                TransferAccDetailRequest transferAccDetail = new TransferAccDetailRequest(orderNo,
                        investOrder.getExtOrderNo(),
                        investor.getPayAccountNo(),
                        "0.00",
                        borrower.getPayAccountNo(),
                        inMerFee.toString(),
                        investOrder.getAmountString());
                transferAccDetails.add(transferAccDetail);
                lendingAmount = lendingAmount.add(investOrder.getAmount());
            }
            if (StringUtils.isBlank(batchOrderNo)) {
                throw new BusinessProcessException("出借批次订单号出错");
            }

            if (transferAccDetails.isEmpty()) {
                throw new BusinessProcessException("无出借明细");
            }

            PaymentOrder paymentOrder = new PaymentOrder(false,
                    borrowingId,
                    PaymentOrderType.LENDING,
                    batchOrderNo,
                    null,
                    lendingAmount,
                    String.format("标的[%s]出借", borrowingId),
                    null);
            IpsPayTransferRequest request = new IpsPayTransferRequest(
                    batchOrderNo,
                    DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                    borrowing.getProjectNo(),
                    "1",
                    "2",
                    "2",
                    transferAccDetails
            );
            String result = (String) payGeneratorContext.generateRequest(request);
            logger.info("lending result:{}", result);
            paymentOrder.setReqParams(JsonUtils.toJson(request));
            paymentOrderService.persist(paymentOrder);
            paymentOrderService.flush();
            if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
                IpsPayTransferResponse responseResult = (IpsPayTransferResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
                booleanResults = businessProcessService.process(batchOrderNo, responseResult, BusinessType.TRANSFER, PaymentOrderType.LENDING);
            }
            logger.info("借款[{}]出借第{}次调用转账接口结束", borrowingId, i);
        }

        boolean failure = booleanResults.contains(Boolean.FALSE);
        boolean success = booleanResults.contains(Boolean.TRUE);
        List<LendingRecord> successLendingRecords = lendingRecordService.findByBorrowingStatus(borrowingId, RecordStatus.SUCCESS);
        List<InvestmentRecord> investmentRecords = investmentRecordService.findList(borrowingId, InvestmentState.SUCCESS);
        if (requestInterfaceCount == 0) {
            return successLendingRecords.size() == investmentRecords.size();
        }
        return success && !failure && successLendingRecords.size() == investmentRecords.size();
    }

    /**
     * 出借失败
     * @param borrowing
     * @return
     */
    public Boolean lendingFailure(Borrowing borrowing) {
        List<Investment> investments = investmentService.findList(borrowing.getId());
        Set<Boolean> rets = new HashSet<>();
        for (Investment investment : investments) {
            List<InvestmentRecord> records = investmentRecordService.findListByInvestment(investment.getId(), InvestmentState.PAID);
            for (InvestmentRecord record : records) {

                PaymentOrder investOrder = paymentOrderService.findByOrderNo(record.getOrderNo());
                User investor = userService.get(record.getInvestor());
                String orderNo = SnUtils.getOrderNo();
                PaymentOrder paymentOrder = new PaymentOrder(false,
                        record.getInvestor(),
                        PaymentOrderType.LENDING_CANCEL,
                        orderNo,
                        record.getOrderNo(),
                        record.getAmount().subtract(record.getPreferentialAmount()),
                        String.format("投资[%s]撤销解冻", record.getId()),
                        null);
                IpsPayUnfreezeRequest request = new IpsPayUnfreezeRequest(paymentOrder.getOrderNo(),
                        DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                        borrowing.getProjectNo(),
                        investOrder.getExtOrderNo(),
                        "2",
                        "0.00",
                        investor.getPayAccountNo(),
                        investOrder.getAmountString());
                String result = (String) payGeneratorContext.generateRequest(request);
                logger.info("borrowing [{}] lending failure result:{}", borrowing.getId(), result);
                paymentOrder.setReqParams(JsonUtils.toJson(request));
                Boolean investmentRescind;
                paymentOrderService.persist(paymentOrder);
                paymentOrderService.flush();
                if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
                    IpsPayUnfreezeResponse responseResult = (IpsPayUnfreezeResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
                    investmentRescind = businessProcessService.process(orderNo, responseResult, BusinessType.UNFREEZE, PaymentOrderType.LENDING_CANCEL);
                    rets.add(investmentRescind);
                }
            }
        }

        return !rets.isEmpty() && rets.contains(true) && !rets.contains(false);
    }
    private Request frozen(HttpServletRequest request, InvestVo vo, User user, Borrowing borrowing, PaymentOrderType orderType) {
        if (orderType != PaymentOrderType.INVESTMENT) {
            throw new BusinessProcessException("冻结业务类型不正确");
        }
        int userId = user.getId();
        borrowing = borrowingDao.get(borrowing.getId());
        BigDecimal amount = vo.getAmount();
        if (!borrowing.verifyInvest(amount)) {
            throw new BusinessProcessException("超过可投资金额");
        }
//        borrowing.addOccupyAmount(amount);
//        borrowing.addOccupyCount();
//        borrowingDao.flush();

        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                orderType,
                SnUtils.getOrderNo(),
                null,
                vo.getAmount(),
                String.format("用户[%s][%s][%s]冻结", userId, vo.getOperationMethod().getDisplayName(), orderType.getDisplayName()),
                null);
        String projectNo = "";
        if (null != borrowing) {
            projectNo = borrowing.getProjectNo();
        }
        String regType = "1";
        String contractNo = "";
        String authNo = "";
        if (vo.getOperationMethod() == OperationMethod.AUTO) {
            regType = "2";
            UserAutoInvestVo autoInvestVo = userMetaService.convertToBean(SignType.AUTO_INVESTMENT_SIGN, userId, UserAutoInvestVo.class);
            contractNo = autoInvestVo.getMerBillNo();
            authNo = autoInvestVo.getAuthNo();
        }
        IpsPayFrozenRequest frozenRequest = new IpsPayFrozenRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                projectNo,
                "1",
                regType,
                contractNo,
                authNo,
                paymentOrder.getAmountString(),
                "0",
                "1",
                user.getPayAccountNo(),
                "");
        Map map = (Map) payGeneratorContext.generateRequest(frozenRequest);
        logger.info("frozen result:{}", map);
        paymentOrder.setReqParams(JsonUtils.toJson(frozenRequest));
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        // TODO 生成投资记录
        vo.setSn(paymentOrder.getOrderNo());
        InvestmentRecord investmentRecord = investmentService.genInvestAndInvestmentRecord(vo);

        User investor = userDao.get(vo.getInvestor());  // 投资人
        UserFinance investorFinance = userFinanceService.getByUserId(vo.getInvestor());
        User borrower = userDao.get(borrowing.getBorrower());  // 借款人
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
        order.setMethod(OrderMethod.IPS);
        order.setBusiness(investmentRecord.getId());
        order.setOrderNo(SnUtils.getOrderNo());
        order.setThirdOrderNo(null);
        order.setPayerBalance(investorFinance.getAvailable());
        order.setPayeeBalance(borrowerFinance.getAvailable());
        order.setAmount(investmentRecord.getAmount().subtract(investmentRecord.getPreferentialAmount()));
        order.setAmountReceived(investmentRecord.getAmount());
        order.setPoints(0);
        order.setLaunchDate(new Date());
        order.setOperator(investor.getLoginName());
        order.setIp(null == request ? "": WebUtils.getRemoteIp(request));
        order.setMemo(vo.getOperationMethod().getDisplayName() + orderType.getDisplayName());
        orderDao.persist(order);
        return new Request(IpsPayConfig.REQUEST_URL, map);
    }

    /**
     * 红包冻结
     * @param request
     * @param vo
     * @param user
     * @param borrowing
     * @param orderType
     * @return
     */
    public Request combFrozen(HttpServletRequest request,InvestVo vo,User user, Borrowing borrowing, PaymentOrderType orderType,UserCoupon userCoupon) {
        if (orderType != PaymentOrderType.COMB_FREEZE) {
            throw new BusinessProcessException("冻结业务类型不正确");
        }
        int userId = user.getId();
        PaymentOrder paymentOrder = new PaymentOrder(true,
                userId,
                orderType,
                SnUtils.getOrderNo(),
                null,
                vo.getAmount(),
                String.format("用户[%s][%s]组合冻结", userId, orderType.getDisplayName()),
                null);
        String couponOrderNo = SnUtils.getOrderNo();
        PaymentOrder couponOrder = new PaymentOrder(false,
                userId,
                orderType,
                couponOrderNo,
                paymentOrder.getOrderNo(),
                vo.getAmount(),
                String.format("用户[%s][%s]组合冻结优惠券子订单", userId, orderType.getDisplayName()),
                null);
        userCoupon.setOrderNo(couponOrderNo);
        userCouponService.merge(userCoupon);
        String investOrderNo = SnUtils.getOrderNo();
        PaymentOrder investOrder = new PaymentOrder(false,
                userId,
                orderType,
                investOrderNo,
                paymentOrder.getOrderNo(),
                vo.getAmount(),
                String.format("用户[%s][%s]组合冻结投资子订单", userId, orderType.getDisplayName()),
                null);
        String projectNo = "";
        if (null != borrowing) {
            projectNo = borrowing.getProjectNo();
        }
        userCouponService.merge(userCoupon);
        IpsPayCombFreezeRequest combFreezeRequest = new IpsPayCombFreezeRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                projectNo,
                "1",
                "",
                "",
               new RedPacketRequest(couponOrderNo,"9",userCoupon.getAmount().toString(),"0","1",user.getPayAccountNo(),""),
               new RedPacketRequest(investOrderNo,"1",vo.getAmount().subtract(userCoupon.getAmount()).toString(),"0","1",user.getPayAccountNo(),"")
        );
        Map map = (Map) payGeneratorContext.generateRequest(combFreezeRequest);
        logger.info("frozen result:{}", map);
        paymentOrder.setReqParams(JsonUtils.toJson(combFreezeRequest));
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.persist(couponOrder);
        paymentOrderService.persist(investOrder);
        paymentOrderService.flush();
        // TODO 生成投资记录
        vo.setSn(paymentOrder.getOrderNo());
        vo.setPreferentialAmount(userCoupon.getAmount());
        vo.setCoupon(userCoupon.getId());
        InvestmentRecord investmentRecord = investmentService.genInvestAndInvestmentRecord(vo);

        User investor = userDao.get(vo.getInvestor());  // 投资人
        UserFinance investorFinance = userFinanceService.getByUserId(vo.getInvestor());
        User borrower = userDao.get(borrowing.getBorrower());  // 借款人
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
        order.setMethod(OrderMethod.IPS);
        order.setBusiness(investmentRecord.getId());
        order.setOrderNo(SnUtils.getOrderNo());
        order.setThirdOrderNo(null);
        order.setPayerBalance(investorFinance.getAvailable());
        order.setPayeeBalance(borrowerFinance.getAvailable());
        order.setAmount(investmentRecord.getAmount().subtract(investmentRecord.getPreferentialAmount()));
        order.setAmountReceived(investmentRecord.getAmount());
        order.setPoints(0);
        order.setLaunchDate(new Date());
        order.setOperator(investor.getLoginName());
        order.setIp(WebUtils.getRemoteIp(request));
        orderDao.persist(order);
        return new Request(IpsPayConfig.REQUEST_URL, map);
    }
    /**
     * 还款冻结
     * @param user
     * @param repayment
     * @param orderType
     * @return
     */
    public Request repaymentFrozen(User user, Repayment repayment, PaymentOrderType orderType) {
        return frozen(user, repayment, orderType);
    }
    private Request frozen(User user,Repayment repayment, PaymentOrderType orderType) {
        if (orderType != PaymentOrderType.REPAYMENT_FROZEN) {
            throw new BusinessProcessException("冻结业务类型不正确");
        }

        //存在转让时，先撤销转让
        try {
            List<Transfer> transfers = transferService.findList(repayment.getBorrowing());
            for (Transfer transfer : transfers) {
                transferService.transferCancel(transfer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("撤销转让失败");
        }

        int userId = user.getId();
        repayment.setOrderNo(SnUtils.getOrderNo());
        repaymentDao.merge(repayment);
        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                orderType,
                repayment.getOrderNo(),
                null,
                repayment.getRepaymentAmount(),//冻结金额+服务费
                String.format("用户[%s][%s]", userId, orderType.getDisplayName()),
                null);
        String projectNo = "";
        Borrowing borrowing = borrowingDao.get(repayment.getBorrowing());
        if (null != borrowing) {
            projectNo = borrowing.getProjectNo();
        }

        User borrower = userService.get(repayment.getBorrower());  // 借款人
        UserFinance borrowerFinance = userFinanceService.getByUserId(repayment.getBorrower());
        //平台订单 : 还款
        Order order = new Order();
        order.setUserId(borrower.getId());
        order.setPayer(borrower.getId());
        order.setPayerName(borrower.getLoginName());
        order.setPayee(-4);
        order.setPayerName("投资人");
        order.setStatus(OrderStatus.LAUNCH);
        order.setType(OrderType.REPAYMENT);
        order.setMethod(OrderMethod.IPS);
        order.setBusiness(repayment.getId());
        order.setOrderNo(SnUtils.getOrderNo());
        order.setThirdOrderNo(null);
        order.setPayerBalance(borrowerFinance.getAvailable());
        order.setAmount(repayment.getRepaymentAmount());
        order.setAmountReceived(repayment.getRepaymentAmount().subtract(repayment.getRepaymentFee()));
        order.setPayerFee(repayment.getRepaymentFee());
        order.setPoints(0);
        order.setLaunchDate(new Date());
        order.setOperator(borrower.getLoginName());
        order.setIp(WebUtils.getRemoteIp(WebUtils.getHttpRequest()));
        orderDao.persist(order);

        IpsPayFrozenRequest request = new IpsPayFrozenRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                projectNo,
                "3",
                "1",
                "",
                "",
                repayment.getRepaymentAmount().subtract(repayment.getRepaymentFee()).toString(),
                repayment.getRepaymentFee().toString(),
                "1",
                user.getPayAccountNo(),
                "");
        logger.info(repayment.getRepaymentAmount().subtract(repayment.getRepaymentFee()).toString());
        logger.info(repayment.getRepaymentFee().toString());
        Map map = (Map) payGeneratorContext.generateRequest(request);
        logger.info("frozen result:{}", map);
        paymentOrder.setReqParams(JsonUtils.toJson(request));
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        return new Request(IpsPayConfig.REQUEST_URL, map);
    }

    /**
     * 还款
     *
     * @param repayment
     * @return
     */
    public void repayment(Repayment repayment,List<RepaymentPlan> repaymentPlans,String ipsBillNo) {
        /**
         * 根据还款生成转账记录
         */
        List<RepaymentTransferRecord> transferRecords = repaymentTransferRecordService.findByRepayment(repayment.getId());
        String batchOrderNo;
        if (transferRecords.isEmpty()) {
            batchOrderNo = SnUtils.getOrderNo();
            repaymentTransferRecordService.addRecord(repayment, batchOrderNo);
        } else {
            List<RepaymentTransferRecord> newRecords = repaymentTransferRecordService.findByRepaymentStatus(repayment.getId(), RecordStatus.NEW_CREATE);
            List<RepaymentTransferRecord> failureRecords = repaymentTransferRecordService.findByRepaymentStatus(repayment.getId(), RecordStatus.FAILURE);
            transferRecords.clear();
            transferRecords.addAll(newRecords);
            transferRecords.addAll(failureRecords);
            batchOrderNo = SnUtils.getOrderNo();
            for (RepaymentTransferRecord transferRecord : transferRecords) {
                transferRecord.updateBatchOrderNo(batchOrderNo);
                repaymentTransferRecordService.merge(transferRecord);
            }
        }

        List<RepaymentPlan> newRepaymentPlans = new ArrayList<>();
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            if(repaymentPlan.getRecoveryAmount().compareTo(BigDecimal.ZERO)>0){
                newRepaymentPlans.add(repaymentPlan);
            }
        }
        repaymentPlans = newRepaymentPlans;

        /**
         * 注：单次请求最大转账数控制在200，大于200后的转账另外发起请求
         */
        int maxDetailCount = 200;
        if (SpringObjectFactory.getActiveProfile() != SpringObjectFactory.Profile.PROD) {
            maxDetailCount = 5;//单次最多转账
        }
        int transforCount = 0;//调用转账次数
        if (repaymentPlans.size() % maxDetailCount == 0) {
            transforCount = Math.floorDiv(repaymentPlans.size(), maxDetailCount);
        } else {
            transforCount = Math.floorDiv(repaymentPlans.size(), maxDetailCount) + 1;
        }
        List transferAccDetails = new ArrayList();
        BigDecimal thisRepaymentAmount = BigDecimal.ZERO;
        BigDecimal totalRepaymentAmount = BigDecimal.ZERO;
        Boolean isRepayOver = true;
        /**
         * 1：组装需要参数 transferAccDetails
         */
        for (int requestCount = 0; requestCount < transforCount; requestCount++) {
            logger.info("开始第{}次调用开始", requestCount);
            if (requestCount > 0) {
                batchOrderNo = SnUtils.getOrderNo();
            }
            try {
                lock.lock(LockStack.ORDER_NO_LOCK, batchOrderNo);
                transferAccDetails.clear();
                for (int index = requestCount * maxDetailCount; index < repaymentPlans.size() && index < (requestCount + 1) * maxDetailCount; index++) {
                    logger.info("获取第{}个repaymentPlans", index);
                    logger.info("{}", repaymentPlans.get(index));
                    RepaymentPlan repaymentPlan = repaymentPlans.get(index);
                    RepaymentTransferRecord transferRecord = repaymentTransferRecordService.findByRepaymentOrderNo(repaymentPlan.getOrderNo());
                    if (requestCount > 0) {
                        transferRecord.updateBatchOrderNo(batchOrderNo);
                        repaymentTransferRecordService.merge(transferRecord);
                    }
                    BigDecimal repaymentServiceFee = BigDecimal.ZERO;
                    if (requestCount == 0 && index == 0) {
                        repaymentServiceFee = repayment.getRepaymentFee();
                    }else {
                        repaymentServiceFee = BigDecimal.ZERO;
                    }
                    if (repaymentPlan.getState() == RepaymentState.REPAYING) {
                        thisRepaymentAmount = repaymentPlan.getRecoveryAmount().add(repaymentPlan.getRecoveryRecoveryFee());
                        TransferAccDetailRequest transferAccDetail = new TransferAccDetailRequest(
                                transferRecord.getOrderNo(),
                                ipsBillNo,
                                userService.get(repayment.getBorrower()).getPayAccountNo(),
                                MathUtils.format(repaymentServiceFee, 2, 2),
                                userService.get(repaymentPlan.getInvestor()).getPayAccountNo(),
                                repaymentPlan.getRecoveryFee().toString(),
                                thisRepaymentAmount.toString());
                        transferAccDetails.add(transferAccDetail);
                        totalRepaymentAmount = totalRepaymentAmount.add(thisRepaymentAmount);
                        isRepayOver = false;
                        logger.info(MathUtils.format(repaymentServiceFee, 2, 2));
                        logger.info(repaymentPlan.getRecoveryFee().toString());
                        logger.info(thisRepaymentAmount.toString());
                    }
                }
                if (isRepayOver) {
                    throw new BusinessProcessException("该期还款已完成");
                }

                if (transferAccDetails.isEmpty()) {
                    throw new BusinessProcessException("无还款明细");
                }
                /**
                 * 2：生成支付订单
                 */
                PaymentOrder paymentOrder = new PaymentOrder(false,
                        repayment.getBorrower(),
                        PaymentOrderType.REPAYMENT,
                        batchOrderNo,
                        null,
                        repayment.getRepaymentAmount(),
                        String.format("还款ID[%s]还款解冻", repayment.getId()),
                        null);
                /**
                 * 3：组装接口请求参数
                 */
                IpsPayTransferRequest request = new IpsPayTransferRequest(
                        batchOrderNo,
                        DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                        borrowingService.get(repayment.getBorrowing()).getProjectNo(),
                        "3",
                        "2",
                        "2",
                        transferAccDetails
                );
                paymentOrder.setReqParams(JsonUtils.toJson(request));
                paymentOrderService.persist(paymentOrder);
                paymentOrderService.flush();
                String result = (String) payGeneratorContext.generateRequest(request);
                logger.info("repayment transfer result:{}", result);

                //平台订单 : 还款
                orderService.updateOrderStatus(OrderType.REPAYMENT, repayment.getId(), OrderStatus.SUCCESS, paymentOrder.getOrderNo());

                if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
                    IpsPayTransferResponse responseResult = (IpsPayTransferResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
                    logger.info("还款ID[{}]转账结果:{}", repayment.getId(), JsonUtils.toJson(responseResult));
                    businessProcessService.process(batchOrderNo, responseResult, BusinessType.TRANSFER, PaymentOrderType.REPAYMENT);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("开始第{}次调用异常：{}", requestCount, ExceptionUtils.getStackTrace(e));
            } finally {
                lock.unLock(LockStack.ORDER_NO_LOCK, batchOrderNo);
            }
            logger.info("开始第{}次调用结束", requestCount);
        }
    }

    /**
     * 提前还款冻结
     * @param user
     * @param borrowing
     * @param orderType
     * @return
     */
    public Request preRepaymentFrozen(User user, Borrowing borrowing, PaymentOrderType orderType) {
        return prefrozen(user, borrowing, orderType);
    }
    private Request prefrozen(User user,Borrowing borrowing, PaymentOrderType orderType) {
        if (orderType != PaymentOrderType.REPAYMENT_EARLY_FROZEN) {
            throw new BusinessProcessException("冻结业务类型不正确");
        }

        //存在转让时，先撤销转让
        try {
            List<Transfer> transfers = transferService.findList(borrowing.getId());
            for (Transfer transfer : transfers) {
                transferService.transferCancel(transfer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("撤销转让失败");
        }


        BigDecimal preRepaymentAmount = BigDecimal.ZERO;  // 借款人还款金额
        BigDecimal repaymentTotalFee = BigDecimal.ZERO;   // 借款人还款服务费
        try {
//            preRepaymentAmount = repaymentService.preRepaymentAmount(borrowing);
            List<Repayment> repayments = accountantService.calAhead(repaymentService.findList(borrowing.getId()));
            for(Repayment repayment : repayments){
                if(repayment.getState().equals(RepaymentState.REPAID)){
                    continue;
                }
                preRepaymentAmount = preRepaymentAmount.add(repayment.getRepaymentAmount());
                repaymentTotalFee = repaymentTotalFee.add(repayment.getRepaymentFee());
            }
        }catch (Exception e){
            throw new RuntimeException("还款金额计算错误");
        }
        int userId = user.getId();
        String orderNo = SnUtils.getOrderNo();
        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                orderType,
                orderNo,
                null,
                preRepaymentAmount,//冻结金额+服务费
                String.format("用户[%s][%s]", userId, orderType.getDisplayName()),
                borrowing.getId().toString());
        String projectNo = "";
        if (null != borrowing) {
            projectNo = borrowing.getProjectNo();
        }
//
        User borrower = userService.get(borrowing.getBorrower());  // 借款人
        UserFinance borrowerFinance = userFinanceService.getByUserId(borrowing.getBorrower());
        //平台订单 : 还款
        Order order = new Order();
        order.setUserId(borrower.getId());
        order.setPayer(borrower.getId());
        order.setPayerName(borrower.getLoginName());
        order.setPayee(-4);
        order.setPayerName("投资人");
        order.setStatus(OrderStatus.LAUNCH);
        order.setType(OrderType.REPAYMENT_EARLY);
        order.setMethod(OrderMethod.IPS);
        order.setBusiness(borrowing.getId());
        order.setOrderNo(SnUtils.getOrderNo());
        order.setThirdOrderNo(null);
        order.setPayerBalance(borrowerFinance.getAvailable());
        order.setAmount(preRepaymentAmount);
        order.setAmountReceived(preRepaymentAmount.subtract(repaymentTotalFee));
        order.setPayerFee(repaymentTotalFee);
        order.setPoints(0);
        order.setLaunchDate(new Date());
        order.setOperator(borrower.getLoginName());
        order.setIp(WebUtils.getRemoteIp(WebUtils.getHttpRequest()));
        order.setMemo(String.format("借款人[%s]提前还款", borrower.getId()));
        orderDao.persist(order);

        IpsPayFrozenRequest request = new IpsPayFrozenRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                projectNo,
                "3",
                "1",
                "",
                "",
                preRepaymentAmount.subtract(repaymentTotalFee).toString(),
                repaymentTotalFee.toString(),
                "1",
                user.getPayAccountNo(),
                "");
        logger.info("------提前还款冻结------");
        logger.info(preRepaymentAmount.toString());
        logger.info(repaymentTotalFee.toString());
        Map map = (Map) payGeneratorContext.generateRequest(request);
        logger.info("frozen result:{}", map);
        paymentOrder.setReqParams(JsonUtils.toJson(request));
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        return new Request(IpsPayConfig.REQUEST_URL, map);
    }

    /**
     * 提前还款
     *
     * @param repayments
     * @return
     */
    public void prerepayment(List<Repayment> repayments, String ipsBillNo) {
        /**
         * 根据还款生成转账记录
         */
        Integer borrowingId = repayments.get(0).getBorrowing();
        Integer borrower = repayments.get(0).getBorrower();
        BigDecimal repaymentAmount = BigDecimal.ZERO;   // 借款人还款总金额
        BigDecimal repaymentFee = BigDecimal.ZERO;   // 投资人还款总服务费
        // TODO 更新还款
        for(Repayment repayment : repayments) {
            repaymentAmount = repaymentAmount.add(repayment.getRepaymentAmount());
            repaymentFee = repaymentFee.add(repayment.repaymentFee());
        }

        List<RepaymentTransferRecord> transferRecords = repaymentTransferRecordService.findByBorrowing(borrowingId);
        String batchOrderNo;
        if (transferRecords.isEmpty()) {
            batchOrderNo = SnUtils.getOrderNo();
            transferRecords = repaymentTransferRecordService.addRecord(repayments, batchOrderNo);
        } else {
            List<RepaymentTransferRecord> newRecords = repaymentTransferRecordService.findByBorrowingStatus(borrowingId, RecordStatus.NEW_CREATE);
            List<RepaymentTransferRecord> failureRecords = repaymentTransferRecordService.findByBorrowingStatus(borrowingId, RecordStatus.FAILURE);
            transferRecords.clear();
            transferRecords.addAll(newRecords);
            transferRecords.addAll(failureRecords);
            batchOrderNo = SnUtils.getOrderNo();
            for (RepaymentTransferRecord transferRecord : transferRecords) {
                transferRecord.updateBatchOrderNo(batchOrderNo);
                repaymentTransferRecordService.merge(transferRecord);
            }
        }

        // TODO 投资人集合
        Map<Integer, BigDecimal> investorMap = repaymentTransferRecordService.getInvestorsByRepayments(repayments);

        List listKey = new ArrayList();
        List listValue = new ArrayList();
        Iterator it = investorMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            listKey.add(key);
            listValue.add(investorMap.get(key));
        }

        /**
         * 注：单次请求最大转账数控制在200，大于200后的转账另外发起请求
         */
        int maxDetailCount = 200;
        if (SpringObjectFactory.getActiveProfile() != SpringObjectFactory.Profile.PROD) {
            maxDetailCount = 5;//单次最多转账
        }
        int transforCount = 0;//调用转账次数
        if (listKey.size() % maxDetailCount == 0) {
            transforCount = Math.floorDiv(listKey.size(), maxDetailCount);
        } else {
            transforCount = Math.floorDiv(listKey.size(), maxDetailCount) + 1;
        }
        List<TransferAccDetailRequest> transferAccDetails = new ArrayList();
//        BigDecimal totalRepaymentAmount = BigDecimal.ZERO;
        Boolean isRepayOver = true;
        /**
         * 1：组装需要参数 transferAccDetails
         */
        for (int requestCount = 0; requestCount < transforCount; requestCount++) {
            logger.info("开始第{}次调用开始", requestCount);
            if (requestCount > 0) {
                batchOrderNo = SnUtils.getOrderNo();
            }
            try {
                lock.lock(LockStack.ORDER_NO_LOCK, batchOrderNo);
                transferAccDetails.clear();
                for (int index = requestCount * maxDetailCount; index < listKey.size() && index < (requestCount + 1) * maxDetailCount; index++) {

                    BigDecimal thisRepaymentAmount = BigDecimal.ZERO;
                    BigDecimal thisRepaymentFee = BigDecimal.ZERO;

                    logger.info("获取第{}个repaymentPlans", index);
                    logger.info("{}", listKey.get(index));
//                    RepaymentPlan repaymentPlan = repaymentPlans.get(index);
                    Integer key = Integer.valueOf(String.valueOf(listKey.get(index)));
                    RepaymentTransferRecord transferRecord = repaymentTransferRecordService.findByBorrowingOrderNo(borrowingId, key);
                    if (requestCount > 0) {
                        transferRecord.updateBatchOrderNo(batchOrderNo);
                        repaymentTransferRecordService.merge(transferRecord);
                    }
                    BigDecimal repaymentServiceFee = BigDecimal.ZERO;
                    if (requestCount == 0 && index == 0) {
                        repaymentServiceFee = repaymentFee;
                    }

                    List<RepaymentPlan> repaymentPlans = repaymentPlanService.findList(borrowingId, key, null);

                    if(repaymentPlans!=null && repaymentPlans.size()>0){
                        for(RepaymentPlan repaymentPlan : repaymentPlans){
                            if (repaymentPlan.getState().equals(RepaymentState.REPAYING)) {
                                thisRepaymentAmount = thisRepaymentAmount.add(repaymentPlan.getRecoveryAmount());
                                thisRepaymentFee = thisRepaymentFee.add(repaymentPlan.getRecoveryRecoveryFee());
                            }
                        }
                        if(thisRepaymentAmount.compareTo(BigDecimal.ZERO)>0 || thisRepaymentFee.compareTo(BigDecimal.ZERO)>0){
                            TransferAccDetailRequest transferAccDetail = new TransferAccDetailRequest(
                                    transferRecord.getOrderNo(),
                                    ipsBillNo,
                                    userService.get(borrower).getPayAccountNo(),
                                    repaymentServiceFee.toString(),
                                    userService.get(key).getPayAccountNo(),
                                    thisRepaymentFee.toString(),
                                    thisRepaymentAmount.add(thisRepaymentFee).toString());
                            transferAccDetails.add(transferAccDetail);
                            logger.info("------提前还款 解冻转账------");
                            logger.info(repaymentServiceFee.toString());
                            logger.info(thisRepaymentFee.toString());
                            logger.info(thisRepaymentAmount.toString());
                        }
                        isRepayOver = false;
                    }
                }
                if (isRepayOver) {
                    throw new BusinessProcessException("还款已完成");
                }

                if (transferAccDetails.isEmpty()) {
                    throw new BusinessProcessException("无还款明细");
                }
                /**
                 * 2：生成支付订单
                 */
                PaymentOrder paymentOrder = new PaymentOrder(false,
                        borrower,
                        PaymentOrderType.REPAYMENT_EARLY,
                        batchOrderNo,
                        null,
                        repaymentAmount,
                        String.format("借款ID[%s]提前还款解冻", borrowingId),
                        borrowingId.toString());
                /**
                 * 3：组装接口请求参数
                 */
                IpsPayTransferRequest request = new IpsPayTransferRequest(
                        batchOrderNo,
                        DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                        borrowingService.get(borrowingId).getProjectNo(),
                        "3",
                        "2",
                        "2",
                        transferAccDetails
                );
                paymentOrder.setReqParams(JsonUtils.toJson(request));
                paymentOrderService.persist(paymentOrder);
                paymentOrderService.flush();
                String result = (String) payGeneratorContext.generateRequest(request);
                logger.info("repayment transfer result:{}", result);

                if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
                    IpsPayTransferResponse responseResult = (IpsPayTransferResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
                    logger.info("借款ID[{}]转账结果:{}", borrowingId, JsonUtils.toJson(responseResult));
                    businessProcessService.process(batchOrderNo, responseResult, BusinessType.TRANSFER, PaymentOrderType.REPAYMENT_EARLY);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("开始第{}次调用异常：{}", requestCount, ExceptionUtils.getStackTrace(e));
            } finally {
                lock.unLock(LockStack.ORDER_NO_LOCK, batchOrderNo);
            }
            logger.info("开始第{}次调用结束", requestCount);
        }
    }

    public IDetailResponse query(String queryType, String ipsAcctNoOrMerBillNo) {
        String result = (String) payGeneratorContext.generateRequest(new IpsPayCommonQueryRequest(
                queryType,
                ipsAcctNoOrMerBillNo
        ));
        logger.info("query result:{}", result);
        if (payGeneratorContext.verifySign(result, BusinessType.COMMON_QUERY)) {
            return payGeneratorContext.getResponseResult(result, BusinessType.COMMON_QUERY);
        }else {
            return null;
        }
//        throw new BusinessProcessException("query error ");
    }

    /**
     * 推荐费冻结
     * @param referralFee
     * @param orderType
     * @return
     */
    public Request referralSettleFrozen(ReferralFee referralFee, PaymentOrderType orderType) {
        if (orderType != PaymentOrderType.REFERRAL_FEE_SETTLEMENT_FROZEN) {
            throw new BusinessProcessException("冻结业务类型不正确");
        }
        Referral referral = referralService.get(referralFee.getReferralId());
        int userId = referral.getUserId();
        User user = userService.get(userId);
        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                orderType,
                referralFee.getOrderNo(),
                null,
                referralFee.getReferralFee(),
                String.format("用户[%s][%s]推荐费结算冻结", userId, orderType.getDisplayName()),
                null);
        String projectNo = "";
        IpsPayFrozenRequest frozenRequest = new IpsPayFrozenRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                projectNo,
                "9",
                "1",
                "",
                "",
                paymentOrder.getAmountString(),
                "0",
                "1",
                user.getPayAccountNo(),
                "");
        Map map = (Map) payGeneratorContext.generateRequest(frozenRequest);
        logger.info("referral fee settle frozen result:{}", map);
        paymentOrder.setReqParams(JsonUtils.toJson(frozenRequest));
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        return new Request(IpsPayConfig.REQUEST_URL, map);
    }

    /**
     * 推荐费解冻
     * @param orderNo
     * @param ipsBillNo
     * @return
     */
    public Boolean referralSettleUnFrozen(String orderNo, String ipsBillNo) {
        ReferralFee referralFee = referralFeeService.findByOrderNo(orderNo);
        Referral referral = referralService.get(referralFee.getReferralId());
        User transferIner = userService.get(referral.getUserId()); // 推荐人

        String unFrozenOrderNo = SnUtils.getOrderNo();
        PaymentOrder investOrder = paymentOrderService.findByOrderNo(orderNo);

        PaymentOrder paymentOrder = new PaymentOrder(false,
                transferIner.getId(),
                PaymentOrderType.REFERRAL_FEE_SETTLEMENT,
                unFrozenOrderNo,
                orderNo,
                referralFee.getReferralFee(),
                String.format("推荐费[%s]结算", referralFee.getId()),
                null);
        IpsPayUnfreezeRequest request = new IpsPayUnfreezeRequest(unFrozenOrderNo,
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                "",
                ipsBillNo,
                "4",
                "0.00",
                transferIner.getPayAccountNo(),
                investOrder.getAmountString());
        String result = (String) payGeneratorContext.generateRequest(request);
        logger.info("referral fee settle unfrozen result:{}", result);
        paymentOrder.setReqParams(JsonUtils.toJson(request));
        Boolean referralFeeSettleSuccess = false;
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.flush();
        if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
            IpsPayUnfreezeResponse responseResult = (IpsPayUnfreezeResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
            referralFeeSettleSuccess = businessProcessService.process(unFrozenOrderNo, responseResult, BusinessType.UNFREEZE, PaymentOrderType.REFERRAL_FEE_SETTLEMENT);
        }
        return referralFeeSettleSuccess;
    }

    /**
     * 自动投标/还款签约
     * @param user
     * @param signType
     * @param autoSign
     * @return
     */
    public Request autoSign(User user, SignType signType, AbstractUserMeta autoSign) {
        Integer userId = user.getId();
        boolean isPerson = user.getType().isPerson();
        PaymentOrderType paymentOrderType = signType == SignType.AUTO_INVESTMENT_SIGN ? PaymentOrderType.AUTO_INVESTMENT_SIGN : PaymentOrderType.AUTO_REPAYMENT_SIGN;
        PaymentOrder paymentOrder = new PaymentOrder(false,
                userId,
                paymentOrderType,
                SnUtils.getOrderNo(),
                null,
                BigDecimal.ZERO,
                String.format("用户[%s]%s", userId, signType.getDisplayName()),
                null);
        String signedType;
        String validity = "";
        String cycMinVal = "";
        String cycMaxVal = "";
        String bidMin = "";
        String bidMax = "";
        String rateMin = "";
        String rateMax = "";
        if (signType == SignType.AUTO_INVESTMENT_SIGN) {
            UserAutoInvestVo autoInvest = (UserAutoInvestVo)autoSign;
            UserAutoInvestVo _autoInvest = new UserAutoInvestVo();

            List<UserMetaVo> userMetaVos = userMetaService.findMetasByType(SignType.AUTO_INVESTMENT_SIGN, userId);
            for (UserMetaVo userMetaVo : userMetaVos) {
                try {
                    BeanUtils.setProperty(_autoInvest, userMetaVo.getMetaKey(), userMetaVo.getMetaVal());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            if (autoInvest.singInfoEquals(_autoInvest)) {
                throw new BusinessProcessException("签约信息没有改变");
            }
            BigDecimal[] minResults = autoInvest.getInvestMinAmount().divideAndRemainder(BigDecimal.valueOf(100));
            if (minResults[1].intValue() != 0) {
                throw new BusinessProcessException("投标限额最小值不是100的整数倍");
            }
            BigDecimal[] maxResults = autoInvest.getInvestMaxAmount().divideAndRemainder(BigDecimal.valueOf(100));
            if (maxResults[1].intValue() != 0) {
                throw new BusinessProcessException("投标限额最大值不是100的整数倍");
            }

            signedType = "0";
            validity = autoInvest.getValidity().toString();
            cycMinVal = autoInvest.getProjectMinCyc().toString();
            cycMaxVal = autoInvest.getProjectMaxCyc().toString();
            bidMin = autoInvest.getInvestMinAmount().toString();
            bidMax = autoInvest.getInvestMaxAmount().toString();
            rateMin = autoInvest.getInterestRateMinRate() + "%";
            rateMax = autoInvest.getInterestRateMaxRate() + "%";
        } else {
            UserAutoRepayVo autoRepay = (UserAutoRepayVo)autoSign;
            signedType = "1";
            validity = autoRepay.getValidity().toString();
        }
        Map map = (Map) payGeneratorContext.generateRequest(new IpsPayAutoSignRequest(
                paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                isPerson ? "1" : "2",
                user.getPayAccountNo(),
                signedType,
                validity,
                cycMinVal,
                cycMaxVal,
                bidMin,
                bidMax,
                rateMin,
                rateMax
        ));

        paymentOrder.setReqParams(JsonUtils.toJson(map));
        paymentOrder.setExt(JsonUtils.toJson(autoSign));
        paymentOrderService.persist(paymentOrder);
        return new Request(IpsPayConfig.REQUEST_URL, map);
    }

    /**
     * 自动投标/还款解约
     * @param user
     * @return
     */
    public void unSign(User user, SignType signType) {
        userMetaService.deleteMetas(signType, user.getId());
    }

    public String getEnv() {
        String prefix = SpringObjectFactory.getActiveProfile().name();
        if (StringUtils.isBlank(prefix)) {
            throw new SystemException("获取环境参数异常");
        }
        if (SpringObjectFactory.Profile.DEV.name().equals(prefix)) {
            return DEV_PREFIX;
        } else if (SpringObjectFactory.Profile.TEST.name().equals(prefix)) {
            return TEST_PREFIX;
        } else if (SpringObjectFactory.Profile.PROD.name().equals(prefix)) {
            return PROD_PREFIX;
        }
        throw new SystemException("获取环境参数异常");
    }

}
