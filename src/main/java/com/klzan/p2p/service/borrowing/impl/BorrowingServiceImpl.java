/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing.impl;

import com.jcraft.jsch.SftpException;
import com.klzan.core.Result;
import com.klzan.core.SpringObjectFactory;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.exception.SystemException;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.*;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.borrowing.BorrowingOpinionDao;
import com.klzan.p2p.dao.capital.CapitalDao;
import com.klzan.p2p.dao.capital.PlatformCapitalDao;
import com.klzan.p2p.dao.common.MaterialDao;
import com.klzan.p2p.dao.investment.InvestmentDao;
import com.klzan.p2p.dao.investment.InvestmentRecordDao;
import com.klzan.p2p.dao.postloan.CpcnSettlementDao;
import com.klzan.p2p.dao.postloan.RepaymentDao;
import com.klzan.p2p.dao.postloan.RepaymentPlanDao;
import com.klzan.p2p.dao.user.ReferralDao;
import com.klzan.p2p.dao.user.ReferralFeeDao;
import com.klzan.p2p.dao.user.UserFinanceDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.event.ProjectPublishEvent;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingExtraService;
import com.klzan.p2p.service.borrowing.BorrowingNoticeService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.LendingRecordService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.coupon.UserCouponService;
import com.klzan.p2p.service.investment.AutoInvestmentService;
import com.klzan.p2p.service.message.MessagePushService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.schedule.ScheduleJobService;
import com.klzan.p2p.service.sms.SmsService;
import com.klzan.p2p.service.user.UserCommonService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.BasicSetting;
import com.klzan.p2p.setting.ReferralSetting;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.util.AccountantUtils;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.vo.MaterialVo;
import com.klzan.p2p.vo.borrowing.BorrowingSimpleVo;
import com.klzan.p2p.vo.borrowing.BorrowingVo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.ProjectSettlementBatchRequest;
import com.klzan.plugin.pay.common.dto.ProjectSettlementRequest;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.module.PayModule;
import com.klzan.plugin.repayalgorithm.RepayRecordsStrategyHolder;
import com.klzan.plugin.repayalgorithm.Repays;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import payment.api.vo.RongziProjectSettlementBatItem;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 借款
 */
@Service
@Transactional
public class BorrowingServiceImpl extends BaseService<Borrowing> implements BorrowingService {
    private static final String DEV_PREFIX = "1";
    private static final String TEST_PREFIX = "2";
    private static final String PROD_PREFIX = "3";

    @Inject
    private SettingUtils setting;

    @Inject
    private BorrowingDao borrowingDao;

    @Inject
    private BorrowingOpinionDao opinionDao;

    @Inject
    private CapitalDao capitalDao;

    @Inject
    private PlatformCapitalDao platformCapitalDao;

    @Inject
    private InvestmentDao investmentDao;

    @Inject
    private InvestmentRecordDao investmentRecordDao;

    @Inject
    private RepaymentDao repaymentDao;

    @Inject
    private RepaymentPlanDao repaymentPlanDao;

    @Inject
    private UserFinanceDao userFinanceDao;

    @Inject
    private MaterialDao materialDao;

    @Inject
    private ReferralDao referralDao;

    @Inject
    private ReferralFeeDao referralFeeDao;

    @Inject
    private UserService userService;

    @Inject
    private UserFinanceService userFinanceService;

    @Inject
    private MessagePushService messagePushService;

    @Inject
    private LendingRecordService lendingRecordService;

    @Inject
    private BusinessService businessService;

    @Inject
    private BorrowingExtraService borrowingExtraService;

    @Inject
    private OrderService orderService;

    @Inject
    private ScheduleJobService scheduleJobService;

    @Inject
    private ApplicationContext applicationContext;

    @Inject
    private UserCouponService userCouponService;

    @Inject
    private BorrowingNoticeService borrowingNoticeService;
    @Inject
    private CpcnSettlementDao cpcnSettlementDao;
    @Inject
    private PaymentOrderService paymentOrderService;
    @Inject
    private PayUtils payUtils;
    @Inject
    private SmsService smsService;

    @Override
    public List<Borrowing> findAll() {
        return borrowingDao.findValidAll();
    }

    @Override
    public PageResult<Borrowing> findBorrowingList(PageCriteria pageCriteria, BorrowingProgress progress, Boolean isFailure) {
        return borrowingDao.findList(pageCriteria, progress, isFailure);
    }

    @Override
    public PageResult<Borrowing> findList(PageCriteria pageCriteria, BorrowingProgress progress, PeriodScope scope, InterestRateScope rate, BorrowingType borrowingType, Boolean isRecommend) {
        Map map = new HashMap();
        map.put("type", borrowingType == null ? null : borrowingType.name());
        map.put("isRecommend", isRecommend);
        map.put("scope", scope == null ? null : scope.name());
        map.put("rate", rate == null ? null : rate.name());
        map.put("progress", progress == null ? null : progress.name());
        map.put("sort", pageCriteria.getSort());
        map.put("order", pageCriteria.getOrder());
        PageResult page = myDaoSupport.findPage("com.klzan.p2p.mapper.BorrowingMapper.findList", map, pageCriteria);

        return page;
    }

    @Override
    public PageResult<Borrowing> findList(PageCriteria pageCriteria,int borrower,String state) {
        return borrowingDao.findList(pageCriteria,borrower,state);
    }

    @Override
    public List<BorrowingVo> findSingleBorrowById(Integer id) {
        return null;
    }

    @Override
    public Integer findList(BorrowingType type) {
        return borrowingDao.findList(type);
    }

    @Override
    public List<BorrowingSimpleVo> findHotProjects() {
        Map<String, Object> map = new HashedMap();
        List<String> progresses = new ArrayList<>();
        progresses.add(BorrowingProgress.INVESTING.name());
        map.put("progress", progresses);
        return myDaoSupport.findList("com.klzan.p2p.mapper.BorrowingMapper.findHotProjects", map);
    }

    @Override
    public PageResult<BorrowingSimpleVo> findPage(PageCriteria criteria) {
        Map<String, Object> map = new HashedMap();
        List<String> progresses = new ArrayList<>();
        progresses.add(BorrowingProgress.PREVIEWING.name());
        progresses.add(BorrowingProgress.AUTOINVESTING.name());
        progresses.add(BorrowingProgress.INVESTING.name());
        progresses.add(BorrowingProgress.LENDING.name());
        progresses.add(BorrowingProgress.REPAYING.name());
        progresses.add(BorrowingProgress.COMPLETED.name());
        map.put("progress", progresses);
        return myDaoSupport.findPage("com.klzan.p2p.mapper.BorrowingMapper.findBorrowing", map, criteria);
    }

    @Override
    public List<Borrowing> findByBorrowerId(Integer userId) {
        return borrowingDao.findByBorrowerId(userId);
    }

    @Override
    public BigInteger countTargetByType(String type) {
        return borrowingDao.countTargetByType(type);
    }

    @Override
    public Borrowing findByProjectNo(String projectNo) {
        return borrowingDao.findByProjectNo(projectNo);
    }

    @Override
    public Borrowing apply(BorrowingVo vo, String prepareState, String opinion) throws Exception {
        Borrowing borrowing = new Borrowing();
        borrowing.setBorrower(vo.getBorrower());
        borrowing.setType(vo.getType());
        borrowing.setTitle(vo.getTitle());
        borrowing.setIntro(vo.getIntro());
        borrowing.setAmount(vo.getAmount());
        borrowing.setPeriod(vo.getPeriod());
        borrowing.setPeriodUnit(vo.getPeriodUnit());
        borrowing.setInterestRate(vo.getInterestRate());
        borrowing.setInterestMethod(vo.getInterestMethod());
        borrowing.setRewardInterestRate(vo.getRewardInterestRate() == null ? BigDecimal.ZERO : vo.getRewardInterestRate());
        borrowing.setDescription(vo.getDescription());
        borrowing.setPurpose(vo.getPurpose());
        borrowing.setFieldInquiry(vo.getFieldInquiry());
        borrowing.setCreditInquiry(vo.getCreditInquiry());
        borrowing.setRepaymentInquiry(vo.getRepaymentInquiry());
        borrowing.setGuaranteeMethod(vo.getGuaranteeMethod());
        borrowing.setGuaranteeCorp(vo.getGuaranteeCorp());
        borrowing.setGuarantee(vo.getGuarantee());
        borrowing.setCollateral(vo.getCollateral());
        borrowing.setInvestmentMethod(vo.getInvestmentMethod());
        borrowing.setInvestmentMinimum(vo.getInvestmentMinimum());
        borrowing.setInvestmentMaximum(vo.getInvestmentMaximum());
        borrowing.setInvestmentStartDate(vo.getInvestmentStartDate());
        borrowing.setInvestmentEndDate(vo.getInvestmentEndDate());
        borrowing.setLendingTime(vo.getLendingTime());
        borrowing.setRepaymentMethod(vo.getRepaymentMethod());
        borrowing.setFeeRate(vo.getFeeRate());
        borrowing.setRepaymentFeeMethod(vo.getRepaymentFeeMethod());
        borrowing.setRepaymentFeeRate(vo.getRepaymentFeeRate());
        borrowing.setRecoveryFeeRate(vo.getRecoveryFeeRate());
        borrowing.setInTransferFeeRate(vo.getInTransferFeeRate());
        borrowing.setOutTransferFeeRate(vo.getOutTransferFeeRate());
        borrowing.setOverdueInterestRate(vo.getOverdueInterestRate());
        borrowing.setSeriousOverdueStartPeriod(vo.getSeriousOverdueStartPeriod());
        borrowing.setSeriousOverdueInterestRate(vo.getSeriousOverdueInterestRate());
        borrowing.setRiskAnalysis(vo.getRiskAnalysis());
        borrowing.setInvestedAmount(BigDecimal.ZERO);
        borrowing.setOccupyAmount(BigDecimal.ZERO);
        borrowing.setOccupyCount(0);
        borrowing.setFee(BigDecimal.ZERO);
        borrowing.setPaidFee(BigDecimal.ZERO);
        borrowing.setIp(getRemoteIp());
        borrowing.setIsRecommend(vo.getIsRecommend());
        borrowing.setSubjectSituation(vo.getSubjectSituation());
        borrowing.setTradingContractNo(vo.getTradingContractNo());
        borrowing.setCommercialFactoringContractNo(vo.getCommercialFactoringContractNo());
        borrowing.setFactoringComBussinessNo(vo.getFactoringComBussinessNo());
        borrowing.setReceivables(vo.getReceivables());
        //TODO 增加协议字段
        borrowing.setAgreementId(new Integer(vo.getAgreementId()));
        borrowing.setTransferAgreementId(new Integer(vo.getTransferAgreementId()));
        borrowing.setInvestTransferAgreementId(new Integer(vo.getInvestTransferAgreementId()));
        borrowing.setSurportCoupon(vo.getSurportCoupon());
        borrowing.setAgreementPlace(vo.getAgreementPlace());
        borrowing.setLabels(vo.getLabels());
        borrowing.setAutoInvest(vo.getAutoInvest());
        //TODO 增加字段
        borrowing.setBankID(vo.getBankID());
        borrowing.setBankAccountName(vo.getBankAccountName());
        borrowing.setBankAccountNumber(vo.getBankAccountNumber());
        borrowing.setBankBranchName(vo.getBankBranchName());
        borrowing.setBankProvince(vo.getBankProvince());
        borrowing.setBankCity(vo.getBankCity());
        if (vo.getAutoInvest()) {
            if (vo.getInvestmentMinimum().compareTo(new BigDecimal(100)) != 0) {
                throw new BusinessProcessException("开启自动投标最低投资金额必须是100");
            }
        }
        //审批或调查
        if (null != prepareState && "INQUIRING".equals(prepareState)) {
            borrowing.setProgress(BorrowingProgress.INQUIRING);
        } else if (null != prepareState && "CONFIRMING".equals(prepareState)) {
            borrowing.setProgress(BorrowingProgress.APPROVAL);
        } else {
            throw new Exception("申请失败");
        }
        borrowing.setState(BorrowingState.WAIT);

        //保存借款
        borrowing = borrowingDao.persist(borrowing);

        //上传材料
        uploadMaterials(vo.getMaterials(), borrowing.getId());

        // 附加信息
        borrowingExtraService.addExtra(borrowing.getId(), vo.getExtras());

        //保存借款意见
        BorrowingOpinion bo = new BorrowingOpinion();
        bo.setType(BorrowingOpinionType.APPLY);
        bo.setCont(opinion);
        bo.setOperator(getLoginName());
        bo.setIp(getRemoteIp());
        bo.setBorrowing(borrowing.getId());
        opinionDao.persist(bo);

        return borrowing;
    }

    @Override
    public void update(BorrowingVo vo, String opinion) {

        //更新借款
        Borrowing borrowing = borrowingDao.get(vo.getId());
        borrowing.setBorrower(vo.getBorrower());
        borrowing.setType(vo.getType());
        borrowing.setTitle(vo.getTitle());
        borrowing.setIntro(vo.getIntro());
        borrowing.setAmount(vo.getAmount());
        borrowing.setPeriod(vo.getPeriod());
        borrowing.setPeriodUnit(vo.getPeriodUnit());
        borrowing.setInterestRate(vo.getInterestRate());
        borrowing.setInterestMethod(vo.getInterestMethod());
        borrowing.setRewardInterestRate(vo.getRewardInterestRate() == null ? BigDecimal.ZERO : vo.getRewardInterestRate());
        borrowing.setDescription(vo.getDescription());
        borrowing.setPurpose(vo.getPurpose());
        borrowing.setFieldInquiry(vo.getFieldInquiry());
        borrowing.setCreditInquiry(vo.getCreditInquiry());
        borrowing.setRepaymentInquiry(vo.getRepaymentInquiry());
        borrowing.setGuaranteeMethod(vo.getGuaranteeMethod());
        borrowing.setGuaranteeCorp(vo.getGuaranteeCorp());
        borrowing.setGuarantee(vo.getGuarantee());
        borrowing.setCollateral(vo.getCollateral());
        borrowing.setInvestmentMethod(vo.getInvestmentMethod());
        borrowing.setInvestmentMinimum(vo.getInvestmentMinimum());
        borrowing.setInvestmentMaximum(vo.getInvestmentMaximum());
        borrowing.setInvestmentStartDate(vo.getInvestmentStartDate());
        borrowing.setInvestmentEndDate(vo.getInvestmentEndDate());
        borrowing.setLendingTime(vo.getLendingTime());
        borrowing.setRepaymentMethod(vo.getRepaymentMethod());
        borrowing.setFeeRate(vo.getFeeRate());
        borrowing.setRepaymentFeeMethod(vo.getRepaymentFeeMethod());
        borrowing.setRepaymentFeeRate(vo.getRepaymentFeeRate());
        borrowing.setRecoveryFeeRate(vo.getRecoveryFeeRate());
        borrowing.setInTransferFeeRate(vo.getInTransferFeeRate());
        borrowing.setOutTransferFeeRate(vo.getOutTransferFeeRate());
        borrowing.setOverdueInterestRate(vo.getOverdueInterestRate());
        borrowing.setSeriousOverdueStartPeriod(vo.getSeriousOverdueStartPeriod());
        borrowing.setSeriousOverdueInterestRate(vo.getSeriousOverdueInterestRate());
        borrowing.setRiskAnalysis(vo.getRiskAnalysis());
        borrowing.setInvestedAmount(BigDecimal.ZERO);
        borrowing.setOccupyAmount(BigDecimal.ZERO);
        borrowing.setOccupyCount(0);
        borrowing.setFee(BigDecimal.ZERO);
        borrowing.setPaidFee(BigDecimal.ZERO);
        borrowing.setIp(getRemoteIp());
//        borrowing.setIsRecommend(vo.getIsRecommend());
        if (borrowing.getProgress().equals(BorrowingProgress.INQUIRING) || borrowing.getProgress().equals(BorrowingProgress.APPROVAL)) {
            borrowing.setIsRecommend(vo.getIsRecommend());
        }
        borrowing.setSubjectSituation(vo.getSubjectSituation());
        borrowing.setTradingContractNo(vo.getTradingContractNo());
        borrowing.setCommercialFactoringContractNo(vo.getCommercialFactoringContractNo());
        borrowing.setFactoringComBussinessNo(vo.getFactoringComBussinessNo());
        borrowing.setReceivables(vo.getReceivables());

//        borrowing.setRepaymentMethod(vo.getRepaymentMethod());
//        borrowing.setFeeRate(vo.getFeeRate());
//        borrowing.setRepaymentFeeRate(vo.getRepaymentFeeRate());
//        borrowing.setRecoveryFeeRate(vo.getRecoveryFeeRate());
//        borrowing.setOverdueInterestRate(vo.getOverdueInterestRate());
//        borrowing.setSeriousOverdueInterestRate(vo.getSeriousOverdueInterestRate());
//        borrowing.setSeriousOverdueStartPeriod(vo.getSeriousOverdueStartPeriod());
//        borrowing.setInTransferFeeRate(vo.getInTransferFeeRate());
//        borrowing.setOutTransferFeeRate(vo.getOutTransferFeeRate());
//        borrowing.setInvestmentEndDate(vo.getInvestmentEndDate());
//        borrowing.setInvestmentStartDate(vo.getInvestmentStartDate());
//        borrowing.setInvestmentMaximum(vo.getInvestmentMaximum());
//        borrowing.setInvestmentMinimum(vo.getInvestmentMinimum());
//        borrowing.setInterestRate(vo.getInterestRate());
//        borrowing.setInterestMethod(vo.getInterestMethod());
//        borrowing.setRewardInterestRate(vo.getRewardInterestRate());
//        borrowing.setPeriod(vo.getPeriod());
//        borrowing.setAmount(vo.getAmount());
//        borrowing.setBorrower(vo.getBorrower());
//        borrowing.setTitle(vo.getTitle());
//        borrowing.setType(vo.getType());
//        borrowing.setIntro(vo.getIntro());
//        borrowing.setDescription(vo.getDescription());
//        borrowing.setPurpose(vo.getPurpose());
//        borrowing.setFieldInquiry(vo.getFieldInquiry());
//        borrowing.setCreditInquiry(vo.getCreditInquiry());
//        borrowing.setRepaymentInquiry(vo.getRepaymentInquiry());
//        borrowing.setGuaranteeMethod(vo.getGuaranteeMethod());
//        borrowing.setGuaranteeCorp(vo.getGuaranteeCorp());
//        borrowing.setGuarantee(vo.getGuarantee());
//        borrowing.setCollateral(vo.getCollateral());
//        borrowing.setSubjectSituation(vo.getSubjectSituation());
//        borrowing.setTradingContractNo(vo.getTradingContractNo());
//        borrowing.setCommercialFactoringContractNo(vo.getCommercialFactoringContractNo());
//        borrowing.setFactoringComBussinessNo(vo.getFactoringComBussinessNo());
//        borrowing.setReceivables(vo.getReceivables());
//        if (borrowing.getProgress().equals(BorrowingProgress.INQUIRING) || borrowing.getProgress().equals(BorrowingProgress.APPROVAL)) {
//            borrowing.setIsRecommend(vo.getIsRecommend());
//        }
        //TODO 增加协议字段
        borrowing.setAgreementId(new Integer(vo.getAgreementId()));
        borrowing.setTransferAgreementId(new Integer(vo.getTransferAgreementId()));
        borrowing.setInvestTransferAgreementId(new Integer(vo.getInvestTransferAgreementId()));
        borrowing.setSurportCoupon(vo.getSurportCoupon());
        borrowing.setAgreementPlace(vo.getAgreementPlace());
        borrowing.setLabels(vo.getLabels());
        borrowing.setAutoInvest(vo.getAutoInvest());
        //TODO 增加字段
        borrowing.setBankID(vo.getBankID());
        borrowing.setBankAccountName(vo.getBankAccountName());
        borrowing.setBankAccountNumber(vo.getBankAccountNumber());
        borrowing.setBankBranchName(vo.getBankBranchName());
        borrowing.setBankProvince(vo.getBankProvince());
        borrowing.setBankCity(vo.getBankCity());
        if (vo.getAutoInvest()) {
            if (vo.getInvestmentMinimum().compareTo(new BigDecimal(100)) != 0) {
                throw new BusinessProcessException("开启自动投标最低投资金额必须是100");
            }
        }
        borrowingDao.update(borrowing);

        //上传材料
        uploadMaterials(vo.getMaterials(), borrowing.getId());
        // 附加信息
        borrowingExtraService.updateExtra(borrowing.getId(), vo.getExtras());

        //保存借款意见
        BorrowingOpinion bo = new BorrowingOpinion();
        bo.setType(BorrowingOpinionType.MODIFY);
        bo.setCont(opinion);
        bo.setOperator(getLoginName());
        bo.setIp(getRemoteIp());
        bo.setBorrowing(borrowing.getId());
        opinionDao.persist(bo);
    }

    @Override
    public void inquiry(BorrowingVo vo, Borrowing borrowing, BorrowingCheckState state, String opinion) {

        //更新借款
        borrowing.setDescription(vo.getDescription());
        borrowing.setTitle(vo.getTitle());
        borrowing.setIntro(vo.getIntro());
        borrowing.setAmount(vo.getAmount());
        borrowing.setRewardInterestRate(vo.getRewardInterestRate());
        borrowing.setInterestRate(vo.getInterestRate());
        borrowing.setRepaymentFeeMethod(vo.getRepaymentFeeMethod());
        borrowing.setInterestMethod(vo.getInterestMethod());
        borrowing.setPeriod(vo.getPeriod());
        borrowing.setType(vo.getType());
        borrowing.setInvestmentStartDate(vo.getInvestmentStartDate());
        borrowing.setInvestmentEndDate(vo.getInvestmentEndDate());
        borrowing.setPurpose(vo.getPurpose());
        borrowing.setFieldInquiry(vo.getFieldInquiry());
        borrowing.setCreditInquiry(vo.getCreditInquiry());
        borrowing.setRepaymentInquiry(vo.getRepaymentInquiry());
        borrowing.setGuaranteeMethod(vo.getGuaranteeMethod());
        borrowing.setGuaranteeCorp(vo.getGuaranteeCorp());
        borrowing.setGuarantee(vo.getGuarantee());
        borrowing.setCollateral(vo.getCollateral());
        borrowing.setIsRecommend(vo.getIsRecommend());
        borrowing.setSubjectSituation(vo.getSubjectSituation());
        borrowing.setTradingContractNo(vo.getTradingContractNo());
        borrowing.setCommercialFactoringContractNo(vo.getCommercialFactoringContractNo());
        borrowing.setFactoringComBussinessNo(vo.getFactoringComBussinessNo());
        borrowing.setReceivables(vo.getReceivables());
        borrowing.setIsRecommend(vo.getIsRecommend());
        borrowing.setAgreementPlace(vo.getAgreementPlace());
        borrowing.setLabels(vo.getLabels());
        borrowing.setAutoInvest(vo.getAutoInvest());
        if (vo.getAutoInvest()) {
            if (vo.getInvestmentMinimum().compareTo(new BigDecimal(100)) != 0) {
                throw new BusinessProcessException("开启自动投标最低投资金额必须是100");
            }
        }
        switch (state) {
            case SUCCESS://通过
                borrowing.setProgress(BorrowingProgress.APPROVAL);
                borrowing.setState(BorrowingState.WAIT);
                break;
            case FAILURE://未通过
                borrowing.setState(BorrowingState.FAILURE);
//                this.approveFailure(borrowing); //解冻
                break;
            default://待继续调查
                break;
        }
        borrowingDao.update(borrowing);

        //上传材料
        uploadMaterials(vo.getMaterials(), borrowing.getId());
        // 附加信息
        borrowingExtraService.updateExtra(borrowing.getId(), vo.getExtras());

        //保存借款意见
        BorrowingOpinion bo = new BorrowingOpinion();
        bo.setType(BorrowingOpinionType.INQUIRY);
        bo.setCont(opinion);
        bo.setOperator(getLoginName());
        bo.setIp(getRemoteIp());
        bo.setBorrowing(borrowing.getId());
        opinionDao.persist(bo);

    }

    @Override
    public void approval(BorrowingVo vo, Borrowing borrowing, BorrowingCheckState state, String opinion) {

        //更新借款
        borrowing.setIntro(vo.getIntro());
        borrowing.setDescription(vo.getDescription());
        borrowing.setPurpose(vo.getPurpose());
        borrowing.setFieldInquiry(vo.getFieldInquiry());
        borrowing.setCreditInquiry(vo.getCreditInquiry());
        borrowing.setRepaymentInquiry(vo.getRepaymentInquiry());
        borrowing.setGuaranteeMethod(vo.getGuaranteeMethod());
        borrowing.setGuaranteeCorp(vo.getGuaranteeCorp());
        borrowing.setGuarantee(vo.getGuarantee());
        borrowing.setCollateral(vo.getCollateral());
        borrowing.setIsRecommend(vo.getIsRecommend());
        borrowing.setSubjectSituation(vo.getSubjectSituation());
        borrowing.setTradingContractNo(vo.getTradingContractNo());
        borrowing.setCommercialFactoringContractNo(vo.getCommercialFactoringContractNo());
        borrowing.setFactoringComBussinessNo(vo.getFactoringComBussinessNo());
        borrowing.setReceivables(vo.getReceivables());
        borrowing.setAgreementPlace(vo.getAgreementPlace());
        borrowing.setLabels(vo.getLabels());
        borrowing.setAutoInvest(vo.getAutoInvest());
        if (vo.getAutoInvest()) {
            if (vo.getInvestmentMinimum().compareTo(new BigDecimal(100)) != 0) {
                throw new BusinessProcessException("开启自动投标最低投资金额必须是100");
            }
        }
        if (borrowing.getInvestmentStartDate() == null) {
            borrowing.setInvestmentStartDate(new Date());
        }

        switch (state) {
            case SUCCESS://通过
                borrowing.setPublishDate(new Date());
                borrowing.setProgress(BorrowingProgress.PREVIEWING);
                borrowing.setState(BorrowingState.WAIT);
                borrowing.setProjectNo(getEnv() + DateUtils.format(borrowing.getCreateDate(), DateUtils.DATE_PATTERN_NUMBER_S) + borrowing.getId());
                break;
            case FAILURE://未通过
                borrowing.setProgress(BorrowingProgress.INQUIRING);
                borrowing.setState(BorrowingState.WAIT);
//                this.approveFailure(borrowing); //解冻
                break;
            default://待继续审批
                break;
        }
        borrowingDao.update(borrowing);
        borrowingDao.flush();

        /* 消息推送 */
        messagePushService.newBorrowingSuccPush(borrowing);

        //上传材料
        uploadMaterials(vo.getMaterials(), borrowing.getId());
        // 附加信息
        borrowingExtraService.updateExtra(borrowing.getId(), vo.getExtras());

        //保存借款意见
        BorrowingOpinion bo = new BorrowingOpinion();
        bo.setType(BorrowingOpinionType.APPROVAL);
        bo.setCont(opinion);
        bo.setOperator(getLoginName());
        bo.setIp(getRemoteIp());
        bo.setBorrowing(borrowing.getId());
        opinionDao.persist(bo);

        applicationContext.publishEvent(new ProjectPublishEvent(applicationContext, borrowing.getId()));
    }

    @Override
    @Transactional
    public void lending(Borrowing borrowing, BorrowingCheckState state, String opinion, Boolean noticeBorrower, Boolean noticeInvestor) throws Exception {

        //参数校验
        if (borrowing == null || borrowing.getId() == null) {
            throw new Exception("出借失败");
        }

        //出借校验
        if (!borrowing.verifyLending()) {
            throw new Exception("出借失败");
        }

        /* 新增借款意见*/
        BorrowingOpinion bo = new BorrowingOpinion();
        bo.setType(BorrowingOpinionType.LEND);
        bo.setCont(opinion);
        bo.setOperator(getLoginName());
        bo.setIp(getRemoteIp());
        bo.setBorrowing(borrowing.getId());
        opinionDao.persist(bo);

        Set<RecordStatus> statusSet = lendingRecordService.findLendingStatus(borrowing.getId());
        boolean lendingSuccess = false;
        switch (state) {
            case SUCCESS://通过
                lendingSuccess = businessService.lending(borrowing);
                break;
            case WAIT://待下次出借
                return;
            case FAILURE://未通过
                if (statusSet.contains(RecordStatus.SUCCESS)) {
                    throw new BusinessProcessException("已有部分投资出借成功，不能撤销");
                }
                Boolean lendingFailure = businessService.lendingFailure(borrowing);
                if (!lendingFailure) {
                    throw new BusinessProcessException("撤销失败");
                }
                borrowing.setState(BorrowingState.FAILURE);
                borrowing.setProgress(BorrowingProgress.RESCIND);
                borrowingDao.update(borrowing);
                // 借款未通过解冻
                List<Investment> investments = investmentDao.findList(borrowing.getId());
                for (Investment investment : investments) {
                    UserFinance investorFinance = userFinanceDao.getByUserId(investment.getInvestor());
                    investorFinance.subtractFrozen(investment.getAmount());
                    userFinanceDao.update(investorFinance);
//                    Order order = orderService.addOrMergeOrder(new OrderVo());
                    // 更新投资
                    investment.setState(InvestmentState.FAILURE);
                    investmentDao.update(investment);
                    /* 更新投资记录*/
                    List<InvestmentRecord> investmentRecordList = investmentRecordDao.findListByInvestment(investment.getId());
                    for (InvestmentRecord investmentRecord : investmentRecordList) {
                        investmentRecord.setState(InvestmentState.FAILURE);
                        investmentRecordDao.update(investmentRecord);

                        // TODO 第三方支付解冻后，解冻平台订单
                        Order order = orderService.updateOrderStatus(OrderType.INVESTMENT, investmentRecord.getId(), OrderStatus.RESCIND, investmentRecord.getOrderNo());

//                        Integer couponCode = investmentRecord.getCouponCode();
//                        if(couponCode!=null){
//                            //1.如果使用了红包，将红包状态更新为已使用
//                            UserCoupon userCoupon = userCouponService.get(couponCode);
//                            userCoupon.setCouponState(CouponState.UNUSED);
//                            userCouponService.merge(userCoupon);
//                            UserFinance userFinance = userFinanceService.findByUserId(userCoupon.getUsedId());
//                            userFinance.setBalance(userFinance.getBalance().add(userCoupon.getAmount()));
//                            userFinanceService.merge(userFinance);
//                        }
                        // 解冻资金记录
                        Capital capital = new Capital(investment.getInvestor(),
                                CapitalMethod.INVESTMENT,
                                CapitalType.UNFROZEN,
                                investmentRecord.getAmount(),
                                investorFinance,
                                investmentRecord.getOrderNo(),
                                getLoginName(),
                                getRemoteIp(),
                                "投资人投资解冻",
                                order.getId());
                        capitalDao.persist(capital);
                    }
                    //短信提醒
                    messagePushService.investmentRefundSuccPush(investment.getInvestor(), investment.getAmount());
                }
                return;
            default:
                throw new RuntimeException();
        }

        //通知参数
        Map noticeBorrowerMap = new HashMap();
        List<Map> noticeInvestors = new ArrayList<>();
        if (lendingSuccess) {
            // 借款人
            Integer borrowerId = borrowing.getBorrower();
//            userFinanceDao.flush();
//            UserFinance borrowerFinance = userFinanceDao.getByUserId(borrowerId);
//
            // 金额
            BigDecimal amount = borrowing.getInvestedAmount().setScale(2, BigDecimal.ROUND_DOWN);
            // 借款服务费
            BigDecimal borrowingFee = BigDecimal.ZERO;
            // 投资总记录
            List<Investment> investmentList = investmentDao.findList(borrowing.getId(), InvestmentState.SUCCESS);

            /* 产生还款、还款计划*/
            Repays repays = RepayRecordsStrategyHolder.instanse().getRepays(borrowing, investmentList);
            List<Repayment> repayments = repays.getRepayments();
            List<RepaymentPlan> repaymentPlans = repays.getRepaymentPlen();

            /*服务费计算*/
            Boolean hasRepaymentFee = borrowing.getRepaymentFeeRate() != null && borrowing.getRepaymentFeeRate().compareTo(BigDecimal.ZERO) > 0 && borrowing.getRepaymentFeeMethod() != null;
            Boolean hasRecoveryFee = borrowing.getRecoveryFeeRate() != null && borrowing.getRecoveryFeeRate().compareTo(BigDecimal.ZERO) > 0;
            Integer period = borrowing.getRepaymentMethod().equals(RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST) ? 1 : borrowing.getPeriod();
            BigDecimal repaymentFee = BigDecimal.ZERO;

            for (RepaymentPlan repaymentPlan : repaymentPlans) {
                /* 回收服务费 = 每期利息 * 回收服务费利率  */
                if (hasRecoveryFee) {
                    BigDecimal fee = AccountantUtils.calFee(repaymentPlan.getRepaymentRecord().getInterest(), borrowing.getRecoveryFeeRate());
                    repaymentPlan.setRecoveryFee(fee);
                }
                repaymentPlan.setBorrower(borrowerId);
                if (hasRepaymentFee) {
                    repaymentFee = repaymentFee.add(repaymentPlan.getRepaymentRecord().getCapital()
                            .multiply(borrowing.getRepaymentFeeRate())
                            .divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP)
                    );
                }
            }
            for (Repayment repayment : repayments) {
                if (hasRepaymentFee) {
                    BigDecimal recidualRepaymentFee = repaymentFee;
                    if (borrowing.getRepaymentFeeMethod().equals(RepaymentFeeMethod.EACH_PERIOD)) { //每期支付
                        BigDecimal perPeriodRepaymentFee = BigDecimal.ZERO;
                        if (repaymentFee.compareTo(BigDecimal.ZERO) > 0) {
                            perPeriodRepaymentFee = repaymentFee.divide(new BigDecimal(repayments.size()), 2, BigDecimal.ROUND_UP);
                        }
                        repayment.setRepaymentFee(perPeriodRepaymentFee);
//                        if (repayment.getPeriod().intValue() == repayments.size()) {
//                            repayment.setRepaymentFee(recidualRepaymentFee);
//                        } else {
//                            repayment.setRepaymentFee(perPeriodRepaymentFee);
//                            recidualRepaymentFee = recidualRepaymentFee.subtract(perPeriodRepaymentFee);
//                        }
                    } else if (borrowing.getRepaymentFeeMethod().equals(RepaymentFeeMethod.LAST_PERIOD)) { //最后一期支付
                        if (repayment.getNextPayDate() == null && repayment.getPeriod().intValue() == repayments.size()) {
                            repayment.setRepaymentFee(recidualRepaymentFee);
                        }
                    }
                }
                repayment = repaymentDao.persist(repayment);

                //更新还款计划的还款ID
                for (RepaymentPlan repaymentPlan : repaymentPlans) {
                    if (repaymentPlan.getRepaymentRecord().getPeriod() == repayment.getPeriod()) {
                        repaymentPlan.setRepayment(repayment.getId());
                        repaymentPlanDao.persist(repaymentPlan);
                    }
                }
            }

            /* 投资遍历*/
            for (Investment investment : investmentList) {

//                /* 投资判断*/
//                if (investment.getState() != InvestmentState.SUCCESS) {
//                    throw new RuntimeException();
//                }

                borrowingFee = borrowingFee.add(investment.getAmount().multiply(borrowing.getFeeRate()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN));

                /* 更新投资人*/
                Integer investorId = investment.getInvestor();
                User investor = userService.get(investorId);
                UserFinance investorFinance = userFinanceService.getByUserId(investorId);
                investorFinance.addCredit(AccountantUtils.getRecoveryAmountInterestFee(repaymentPlans, investorId));

                Map noticeInvestorMap = new HashMap();
                noticeInvestorMap.put("investor", investment.getInvestor());
                noticeInvestorMap.put("amount", investment.getAmount());
                noticeInvestors.add(noticeInvestorMap);

//                /* 更新投资记录*/
//                List<InvestmentRecord> investmentRecordList = investmentRecordDao.findListByInvestment(investment.getId());
//                for (InvestmentRecord investmentRecord : investmentRecordList) {
//                    investmentRecord.setState(InvestmentState.SUCCESS);
//                    investmentRecordDao.update(investmentRecord);
//
//                    //平台订单更新
//                    Order order = orderService.updateOrderStatus(OrderType.INVESTMENT, investmentRecord.getId(), OrderStatus.SUCCESS);
//
//                    // 解冻资金记录
//                    Capital capital = new Capital(investment.getInvestor(),
//                            CapitalMethod.INVESTMENT,
//                            CapitalType.UNFROZEN,
//                            investmentRecord.getAmount(),
//                            investorFinance,
//                            investmentRecord.getOrderNo(),
//                            getLoginName(),
//                            getRemoteIp(),
//                            "投资人投资解冻",
//                            order.getId());
//                    capitalDao.persist(capital);
//                }

                /* 推荐费 */
                ReferralSetting referralSetting = setting.getReferral();
                if (setting != null && referralSetting != null) {
                    List<Referral> referrals = referralDao.findByReUserId(investor.getId());
                    if (referrals != null && referrals.size() == 1) {
                        Referral referral = referrals.get(0);
                        Date nowDate = new Date();
                        Date createDate = referral.getCreateDate();
                        String beginDateStr = referralSetting.getBeginDate() + " 00:00:00";
//                        Date beginDate = DateUtils.parse(beginDateStr, DateUtils.YYYY_MM_DD_HH_MM_SS);
                        Date beginDate = DateUtils.parseToDate(beginDateStr);
                        String endDateStr = referralSetting.getEndDate() + " 23:59:59";
//                        Date endDate = DateUtils.parse(endDateStr, DateUtils.YYYY_MM_DD_HH_MM_SS);
                        Date endDate = DateUtils.parseToDate(endDateStr);
                        boolean nowDateAvailable = beginDate.compareTo(nowDate) <= 0 && endDate.compareTo(nowDate) >= 0;
                        boolean referralCreateDateAvailable = beginDate.compareTo(createDate) <= 0 && endDate.compareTo(createDate) >= 0;
                        if (referral.getAvailable() && nowDateAvailable && referralCreateDateAvailable) {
                            ReferralFee referralFee = new ReferralFee();
                            referralFee.setReferralId(referral.getId());
                            referralFee.setState(ReferralFeeState.WAIT_APPLY);
                            referralFee.setReferralAmt(investment.getAmount());
                            BigDecimal referralFeeRate = referral.getReferralFeeRate() == null ? referralSetting.getReferralRate() : referral.getReferralFeeRate();//推荐费率
                            referralFee.setReferralFeeRate(referralFeeRate);
                            BigDecimal referralFeeAmt = referralFee.getReferralAmt().multiply(referral.getReferralFeeRate().divide(BigDecimal.TEN).divide(BigDecimal.TEN));
                            referralFee.setReferralFee(referralFeeAmt);
                            Integer settlementDays = 10; //推荐费结算时间（天）
                            if (referralSetting.getSettlementDays() != null && Integer.valueOf(referralSetting.getSettlementDays()) > 0) {
                                settlementDays = Integer.valueOf(referralSetting.getSettlementDays());
                            }
                            referralFee.setPlanPaymentDate(DateUtils.addDays(new Date(), settlementDays));
                            referralFee.setOperator(getLoginName());
                            referralFee.setIp(getRemoteIp());
                            referralFee.setBorrowing(borrowerId);
                            referralFee.setInvestment(investment.getId());
                            referralFee.setOrderNo(investment.getOrderNo());
                            referralFeeDao.persist(referralFee);

                            messagePushService.reffralSettleSuccPush(investment.getInvestor(), borrowing);
                        }
                    }
                }
                messagePushService.lendSuccPush(investment.getInvestor(), borrowing);
            }

            UserFinance borrowerFinance = userFinanceService.getByUserId(borrowerId);

            // 借款人资金更新
            borrowerFinance.addBalance(borrowing.getAmount(), RechargeBusinessType.GENERAL);
            borrowerFinance.addBorrowingAmts(borrowing.getAmount());

            Capital capitalFee = null;
            PlatformCapital platformCapital = null;
            Capital capital =  new Capital(borrowerId,
                    CapitalMethod.BORROWING,
                    CapitalType.CREDIT,
                    borrowing.getAmount(),
                    borrowerFinance,
                    "",
                    CommonUtils.getLoginName(),
                    CommonUtils.getRemoteIp(),
                    "借款人收款",
                    null);
            /* 借款人借款服务费资金记录 */
            if (borrowingFee.compareTo(BigDecimal.ZERO) > 0) {
                borrowerFinance.subtractBalance(borrowingFee, false);
                capitalFee = new Capital(borrowerId,
                        CapitalMethod.BORROWING_FEE,
                        CapitalType.DEBIT,
                        borrowingFee,
                        borrowerFinance,
                        "",
                        getLoginName(),
                        getRemoteIp(),
                        "借款人借款服务费",
                        null);
                platformCapital = new PlatformCapital(CapitalType.CREDIT,
                        CapitalMethod.BORROWING_FEE,
                        borrowingFee,
                        BigDecimal.ZERO,
                        capital.getId(),
                        "平台收取借款人借款服务费",
                        getLoginName(),
                        getRemoteIp(),
                        "");
            }
            /*  借款人资金更新 */
            borrowerFinance.addDebit(AccountantUtils.getRepaymentAmountInterestFee(repayments));
//            borrowerFinance.subtractFrozen(borrowing.getAmount());
            userFinanceDao.update(borrowerFinance);
            userFinanceDao.flush();

            User borrower = userService.get(borrowing.getBorrower());  // 借款人
            /* 平台订单 : 平台放款还款人收款 */
            Order order = new Order();
            order.setUserId(borrower.getId());
            order.setPayer(-4);
            order.setPayerName("投资人");
            order.setPayee(borrower.getId());
            order.setPayerName(borrower.getLoginName());
            order.setStatus(OrderStatus.SUCCESS);
            order.setType(OrderType.BORROWING);
            order.setMethod(OrderMethod.CPCN);
            order.setBusiness(borrowing.getId());
            order.setOrderNo(SnUtils.getOrderNo());
            order.setThirdOrderNo(null);
            order.setPayeeBalance(borrowerFinance.getBalance());
            order.setPayeeFee(borrowingFee);
            order.setAmount(borrowing.getAmount());
            order.setAmountReceived(borrowing.getAmount());
            order.setPoints(0);
            order.setLaunchDate(new Date());
            order.setFinishDate(new Date());
            order.setOperator(borrower.getLoginName());
            order.setIp(CommonUtils.getRemoteIp());
            orderService.persist(order);

            capital.setOrderNo(order.getOrderNo());
            capital.setOrderId(order.getId());
            capitalDao.persist(capital);
            if (borrowingFee.compareTo(BigDecimal.ZERO) > 0) {
                capitalFee.setOrderNo(order.getOrderNo());
                capitalFee.setOrderId(order.getId());
                capitalDao.persist(capitalFee);
                platformCapitalDao.persist(platformCapital);
            }

            try {
                /* 更新借款 */
                borrowing = borrowingDao.get(borrowing.getId());
                InterestMethod interestMethod = borrowing.getInterestMethod();
                Date interestBeginDate = DateUtils.getMinDateOfDay(DateUtils.addDays(new Date(), interestMethod.getDelayInterestCalDay()));
                borrowing.setInterestBeginDate(interestBeginDate);
                borrowing.setRepayPeriod(repayments.size());
                borrowing.setProgress(BorrowingProgress.REPAYING);
                borrowing.setState(BorrowingState.WAIT);
                borrowing.setFee(borrowing.getFee().add(borrowingFee));
                borrowing.setPaidFee(borrowing.getPaidFee().add(borrowingFee));
                borrowing.setLendingDate(new Date());
                borrowingDao.update(borrowing);
            } catch (Exception e) {
                logger.error("借款[{}]出借后更新失败", borrowing.getId());
            }

            //通知借款人
            if(BooleanUtils.isTrue(noticeBorrower)){
                noticeBorrowerMap.put("borrower", borrowing.getBorrower());
                noticeBorrowerMap.put("amount", borrowing.getAmount().subtract(borrowingFee));
                borrowingNoticeService.lendingToBorrower(borrowing, noticeBorrowerMap);
            }
            //通知投资人
            if(BooleanUtils.isTrue(noticeInvestor)){
                borrowingNoticeService.lendingToInvestors(borrowing, noticeInvestors);
            }

        }
    }

    @Override
    public Result lendingPay(Borrowing borrowing, BorrowingCheckState state, String opinion, Boolean noticeBorrower, Boolean noticeInvestor) {
        //参数校验
        if(borrowing == null || borrowing.getId() == null){
            throw new RuntimeException("出借失败");
        }

        //出借校验
        if(!borrowing.verifyLending()){
            throw new RuntimeException("出借失败");
        }

        CpcnSettlement settlement = cpcnSettlementDao.findSettlement(PaymentOrderType.LENDING, borrowing.getId());
        if (null != settlement && !settlement.getsStatus().equals(CpcnSettlementStatus.failure)) {
            throw new RuntimeException("请勿重复出借");
        }

        /* 新增借款意见*/
        BorrowingOpinion bo = new BorrowingOpinion();
        bo.setType(BorrowingOpinionType.LEND);
        bo.setCont(opinion);
        bo.setOperator(CommonUtils.getLoginName());
        bo.setIp(CommonUtils.getRemoteIp());
        bo.setBorrowing(borrowing.getId());
        opinionDao.persist(bo);

        switch (state){
            case SUCCESS://通过
                borrowing.setLendingDate(new Date());
                borrowingDao.update(borrowing);
                break;
            case WAIT://待下次出借
                return Result.success();
            case FAILURE://未通过
                return Result.success();
            default:
                throw new RuntimeException();
        }



        // 借款人
        Integer borrowerId = borrowing.getBorrower();
        User borrower = userService.get(borrowerId);
        UserFinance borrowerFinance = userFinanceDao.getByUserId(borrowerId);
        // 金额
        BigDecimal amount = borrowing.getInvestedAmount().setScale(2, BigDecimal.ROUND_DOWN);
        // 借款服务费
        BigDecimal borrowingFee = borrowing.getBorrowingFee();
        // 投资总记录
        List<Investment> investmentList = investmentDao.findList(borrowing.getId());

        /* 产生还款、还款计划*/
        Repays repays = RepayRecordsStrategyHolder.instanse().getRepays(borrowing, investmentList);
        List<Repayment> repayments = repays.getRepayments();
        List<RepaymentPlan> repaymentPlans = repays.getRepaymentPlen();

        /*服务费计算*/
        Boolean hasRepaymentFee = borrowing.getRepaymentFeeRate()!=null && borrowing.getRepaymentFeeRate().compareTo(BigDecimal.ZERO)>0 && borrowing.getRepaymentFeeMethod()!=null;
        Boolean hasRecoveryFee = borrowing.getRecoveryFeeRate()!=null && borrowing.getRecoveryFeeRate().compareTo(BigDecimal.ZERO)>0;
        Integer period = borrowing.getRepaymentMethod().equals(RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST)?1:borrowing.getPeriod();
        for(RepaymentPlan repaymentPlan : repaymentPlans){
            /* 回收服务费 = 每期本金 * 回收服务费利率  */
            if(hasRecoveryFee){
                BigDecimal fee = AccountantUtils.calFee(repaymentPlan.getRepaymentRecord().getCapital(), borrowing.getRecoveryFeeRate());
                repaymentPlan.setRecoveryFee(fee);
            }
            repaymentPlan.setBorrower(borrowerId);
        }
        for(Repayment repayment : repayments){
            /* 还款服务费 = 总本金 * 还款服务费利率  */
            BigDecimal fee = borrowing.getRepaymentFee();
            if(hasRepaymentFee){
                if(borrowing.getRepaymentFeeMethod().equals(RepaymentFeeMethod.EACH_PERIOD)){ //每期支付
                    if(fee.compareTo(BigDecimal.ZERO) > 0){
                        fee = fee.divide(new BigDecimal(period.intValue()), 2, BigDecimal.ROUND_UP);
                    }
                    repayment.setRepaymentFee(fee);
                }else if(borrowing.getRepaymentFeeMethod().equals(RepaymentFeeMethod.LAST_PERIOD)){ //最后一期支付
                    if(repayment.getNextPayDate()==null && repayment.getPeriod() == period){
                        repayment.setRepaymentFee(fee);
                    }
                }
            }
            repayment = repaymentDao.persist(repayment);

            //更新还款计划的还款ID
            for(RepaymentPlan repaymentPlan : repaymentPlans){
                if(repaymentPlan.getRepaymentRecord().getPeriod() == repayment.getPeriod()){
                    repaymentPlan.setRepayment(repayment.getId());
                    repaymentPlanDao.persist(repaymentPlan);
                }
            }
        }


        // TODO 结算进度
        String sn = SnUtils.getOrderNo();
        CpcnSettlement cpcnSettlement = new CpcnSettlement();
        cpcnSettlement.setBorrowing(borrowing.getId());
        cpcnSettlement.setsOrderNo(sn);
        cpcnSettlement.setsStatus(CpcnSettlementStatus.unsettled);
        cpcnSettlement.setNoticeBorrower(noticeBorrower);
        cpcnSettlement.setNoticeInvestor(noticeInvestor);
        cpcnSettlement.setType(PaymentOrderType.LENDING);
        cpcnSettlementDao.persist(cpcnSettlement);
        cpcnSettlementDao.flush();

//        ArrayList<RongziProjectSettlementBatItem> itemList = new ArrayList<>();
//        BigDecimal totalAmount = amount;
//        Integer totalCount = 1;
        List<PaymentOrder> orders = new ArrayList<>();

        // TODO  借款出借订单
        PaymentOrder payment = new PaymentOrder();
        payment.setParentOrderNo(sn);
        payment.setOrderNo(SnUtils.getOrderNo());
        payment.setStatus(PaymentOrderStatus.PROCESSING);
        payment.setType(PaymentOrderType.LENDING);
        payment.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
        payment.setAmount(amount);
        payment.setFee(borrowingFee);
        payment.setMemo(String.format("放款,金额￥[%s],服务费￥[%s]", amount, borrowingFee));
        payment.setOperator(CommonUtils.getLoginName());
        payment.setIp(CommonUtils.getRemoteIp());
        payment.setUserId(borrower.getId());
        payment.setBorrowing(borrowing.getId());
        paymentOrderService.persist(payment);
        orders.add(payment);

//        RongziProjectSettlementBatItem item = new RongziProjectSettlementBatItem();
//        item.setSettlementNo(payment.getOrderNo()); //结算交易流水号
////        item.setProjectNo(String.valueOf(payment.getBorrowing())); //项目编号
//        item.setProjectNo(borrowing.getProjectNo()); //项目编号
////      item.setPaymentNo(paymentNo); //支付投资交易流水号，还款给投资人时需要
//        item.setSettlementType(20); //结算类型 10=投资人 20=融资人 30=担保人 40=P2P平台方"
//        item.setPaymentAccountName(payUtils.getPaymentAccountUsername(payment.getUserId()));
//        item.setPaymentAccountNumber(payUtils.getPaymentAccountNumber(payment.getUserId()));
//        item.setAmount(PayUtils.convertToFen(amount.subtract(borrowingFee))); //结算金额，单位：分
//        item.setRemark(String.format("放款,金额￥[%s],服务费￥[%s]", amount, borrowingFee)); //备注
//        itemList.add(item);
        // TODO  借款服务费订单
        if (borrowingFee.compareTo(BigDecimal.ZERO) > 0) {
            PaymentOrder paymentFee = new PaymentOrder();
            paymentFee.setParentOrderNo(sn);
            paymentFee.setOrderNo(SnUtils.getOrderNo());
            paymentFee.setStatus(PaymentOrderStatus.PROCESSING);
            paymentFee.setType(PaymentOrderType.LENDING_FEE);
            paymentFee.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
            paymentFee.setAmount(borrowingFee);
            paymentFee.setMemo("放款服务费");
            paymentFee.setOperator(CommonUtils.getLoginName());
            paymentFee.setIp(CommonUtils.getRemoteIp());
            paymentFee.setUserId(null);
            paymentFee.setBorrowing(borrowing.getId());
            paymentOrderService.persist(paymentFee);
            orders.add(paymentFee);

//            RongziProjectSettlementBatItem itemFee = new RongziProjectSettlementBatItem();
//            itemFee.setSettlementNo(paymentFee.getOrderNo()); //结算交易流水号
////            itemFee.setProjectNo(String.valueOf(paymentFee.getBorrowing())); //项目编号
//            itemFee.setProjectNo(PayUtils.getProjectNo(paymentFee.getBorrowing())); //项目编号
////          itemFee.setPaymentNo(paymentNo); //支付投资交易流水号，还款给投资人时需要
//            itemFee.setSettlementType("40"); //结算类型 10=投资人 20=融资人 30=担保人 40=P2P平台方"
//            itemFee.setPaymentAccountName(ChinaClearingConfig.PLAT_PAYMENT_ACCOUNT_NAME);
//            itemFee.setPaymentAccountNumber(ChinaClearingConfig.PLAT_PAYMENT_ACCOUNT_NUMBER);
//            itemFee.setAmount(PayUtils.convertToFen(borrowingFee)); //结算金额，单位：分
//            itemFee.setRemark("放款服务费"); //备注
//            itemList.add(itemFee);
//
//            totalCount = 2;
        }

        try {
            // TODO cpcn放款
//            Result result = payService.projectTransferSettlementBatch_Loan(sn, itemList, totalAmount, totalCount, borrowing.getId());
            PayModule payModule = PayPortal.project_settlement_batch.getModuleInstance();
            ProjectSettlementBatchRequest request = new ProjectSettlementBatchRequest();
            request.setSettlement(cpcnSettlement);
            request.setOrders(orders);
            payModule.setRequest(request);
            payModule.setSn(sn);
            Response response = payModule.invoking().getResponse();
//            logger.info("cpcn放款sn:"+sn+","+JsonUtils.toJson(result));
            if(!response.isSuccess()){
                return Result.error(response.getMsg());
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("放款失败");
        }
    }

    @Override
    public void lendingSucceed(CpcnSettlement cpcnSettlement) throws Exception {

        Borrowing borrowing = borrowingDao.get(cpcnSettlement.getBorrowing());

        cpcnSettlement.setsStatus(CpcnSettlementStatus.success);
        cpcnSettlementDao.merge(cpcnSettlement);

        // 借款人
        Integer borrowerId = borrowing.getBorrower();
        User borrower = userService.get(borrowerId);
        UserFinance borrowerFinance = userFinanceDao.getByUserId(borrowerId);
        // 金额
        BigDecimal amount = borrowing.getInvestedAmount().setScale(2, BigDecimal.ROUND_DOWN);
        // 借款服务费
        BigDecimal borrowingFee = borrowing.getBorrowingFee();
        // 投资总记录
        List<Investment> investmentList = investmentDao.findList(borrowing.getId());

        List<Repayment> repayments = repaymentDao.findList(borrowing.getId());
        List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findList(borrowing.getId());

        /* 平台订单 : 平台放款还款人收款 */
        Order order = new Order();
        order.setUserId(borrower.getId());
        order.setPayer(-4);
        order.setPayerName("投资人");
        order.setPayee(borrower.getId());
        order.setPayerName(borrower.getLoginName());
        order.setStatus(OrderStatus.SUCCESS);
        order.setType(OrderType.BORROWING);
        order.setMethod(OrderMethod.CPCN);
        order.setBusiness(borrowing.getId());
        order.setOrderNo(SnUtils.getOrderNo());
        order.setThirdOrderNo(null);
        order.setPayeeBalance(borrowerFinance.getBalance().subtract(borrowingFee));
        order.setPayeeFee(borrowingFee);
        order.setAmount(borrowing.getAmount());
        order.setAmountReceived(borrowing.getAmount());
        order.setPoints(0);
        order.setLaunchDate(new Date());
        order.setFinishDate(new Date());
        order.setOperator(borrower.getLoginName());
        order.setIp(CommonUtils.getRemoteIp());
        orderService.persist(order);

        /*  借款人借款资金记录 */
        if(!borrowing.hasBankAccountNumber()){
            borrowerFinance.addBalance(amount, RechargeBusinessType.GENERAL);
        }
        Capital capital = new Capital(borrowerId, CapitalMethod.BORROWING, CapitalType.CREDIT, amount, borrowerFinance, "", CommonUtils.getLoginName(), CommonUtils.getRemoteIp(), "放款", order.getId());
        capital = capitalDao.persist(capital);

        /* 借款人借款服务费资金记录 */
        if (borrowingFee.compareTo(BigDecimal.ZERO) > 0) {
            if(!borrowing.hasBankAccountNumber()){
                borrowerFinance.subtractBalance(borrowingFee);
            }
            capital = new Capital(borrowerId, CapitalMethod.BORROWING_FEE, CapitalType.DEBIT, borrowingFee, borrowerFinance, "", CommonUtils.getLoginName(), CommonUtils.getRemoteIp(), "借款服务费", order.getId());
            capital = capitalDao.persist(capital);
            PlatformCapital platformCapital = new PlatformCapital(CapitalType.CREDIT, CapitalMethod.BORROWING_FEE, borrowingFee, BigDecimal.ZERO, capital.getId(), "借款服务费", CommonUtils.getLoginName(), CommonUtils.getRemoteIp(), "");
            platformCapitalDao.persist(platformCapital);
        }
        /*  借款人资金更新 */
        borrowerFinance.addDebit(AccountantUtils.getRepaymentAmountInterestFee(repayments));
        borrowerFinance.addBorrowingAmts(amount);
        userFinanceDao.update(borrowerFinance);

         /* 更新借款 */
//        borrowing.setProgress(BorrowingProgress.REPAYING);
//        borrowing.setState(BorrowingState.WAIT);
//        borrowing.setFee(borrowing.getFee().add(borrowingFee));
//        borrowing.setPaidFee(borrowing.getPaidFee().add(borrowingFee));
//        borrowingDao.update(borrowing);
        InterestMethod interestMethod = borrowing.getInterestMethod();
        Date interestBeginDate = DateUtils.getMinDateOfDay(DateUtils.addDays(new Date(), interestMethod.getDelayInterestCalDay()));
        borrowing.setInterestBeginDate(interestBeginDate);
        borrowing.setRepayPeriod(repayments.size());
        borrowing.setProgress(BorrowingProgress.REPAYING);
        borrowing.setState(BorrowingState.WAIT);
        borrowing.setFee(borrowing.getFee().add(borrowingFee));
        borrowing.setPaidFee(borrowing.getPaidFee().add(borrowingFee));
        borrowing.setLendingDate(new Date());
        borrowingDao.update(borrowing);

        /* 投资遍历*/
        for (Investment investment : investmentList) {

            /* 投资判断*/
            if (investment.getState() != InvestmentState.INVESTING) {
                throw new RuntimeException();
            }

            /* 更新投资*/
            investment.setState(InvestmentState.SUCCESS);
            investmentDao.update(investment);

//            /* 投资送积分*/
//            userPointService.invest(investment);

            /* 更新投资记录*/
            List<InvestmentRecord> investmentRecordList = investmentRecordDao.findListByInvestment(investment.getId());
            for (InvestmentRecord investmentRecord : investmentRecordList) {
                Order order_ = orderService.getByBusinessId(OrderType.INVESTMENT, investmentRecord.getId());
                order_.setStatus(OrderStatus.SUCCESS);
                orderService.merge(order_);

                investmentRecord.setState(InvestmentState.SUCCESS);
                investmentRecordDao.update(investmentRecord);
            }

            /* 更新投资人*/
            Integer investorId = investment.getInvestor();
            User investor = userService.get(investorId);
            UserFinance investorFinance = userFinanceService.getByUserId(investorId);
            investorFinance.addCredit(AccountantUtils.getRecoveryAmountInterestFee(repaymentPlans,investorId));
            investorFinance.addInvestmentAmts(investment.getAmount());
            investorFinance.subtractBalance(investment.getAmount());
            investorFinance.subtractFrozen(investment.getAmount());
            userFinanceDao.update(investorFinance);

            /* 投资人投资支出资金记录 */
            capital = new Capital(investorId, CapitalMethod.INVESTMENT, CapitalType.DEBIT, investment.getAmount(), investorFinance, investment.getOrderNo(), CommonUtils.getLoginName(), CommonUtils.getRemoteIp(), "投资人投资支出", null);
            capitalDao.persist(capital);

            /* 推荐费 */
            ReferralSetting referralSetting = setting.getReferral();
            if (setting != null && referralSetting != null) {
                List<Referral> referrals = referralDao.findByReUserId(investor.getId());
                if (referrals != null && referrals.size() == 1) {
                    Referral referral = referrals.get(0);
                    Date nowDate = new Date();
                    Date createDate = referral.getCreateDate();
                    String beginDateStr = referralSetting.getBeginDate();
                    Date beginDate = DateUtils.parseToDate(beginDateStr);
                    String endDateStr = referralSetting.getEndDate();
                    Date endDate = DateUtils.parseToDate(endDateStr);
                    boolean nowDateAvailable = beginDate.compareTo(nowDate) <= 0 && endDate.compareTo(nowDate) >= 0;
                    boolean referralCreateDateAvailable = beginDate.compareTo(createDate) <= 0 && endDate.compareTo(createDate) >= 0;
                    if (referral.getAvailable() && nowDateAvailable && referralCreateDateAvailable) {
                        ReferralFee referralFee = new ReferralFee();
                        referralFee.setReferralId(referral.getId());
                        referralFee.setState(ReferralFeeState.WAIT_APPLY);
                        referralFee.setReferralAmt(investment.getAmount());
                        BigDecimal referralFeeRate = referral.getReferralFeeRate() == null ? referralSetting.getReferralRate() : referral.getReferralFeeRate();//推荐费率
                        referralFee.setReferralFeeRate(referralFeeRate);
                        BigDecimal referralFeeAmt = referralFee.getReferralAmt().multiply(referral.getReferralFeeRate().divide(BigDecimal.TEN).divide(BigDecimal.TEN));
                        referralFee.setReferralFee(referralFeeAmt);
                        Integer settlementDays = 10; //推荐费结算时间（天）
                        if (referralSetting.getSettlementDays() != null && Integer.valueOf(referralSetting.getSettlementDays()) > 0) {
                            settlementDays = Integer.valueOf(referralSetting.getSettlementDays());
                        }
                        referralFee.setPlanPaymentDate(DateUtils.addDays(new Date(), settlementDays));
                        referralFee.setOperator(CommonUtils.getLoginName());
                        referralFee.setIp(CommonUtils.getRemoteIp());
                        referralFee.setBorrowing(borrowerId);
                        referralFee.setInvestment(investment.getId());
                        referralFee.setOrderNo(investment.getOrderNo());
                        referralFeeDao.persist(referralFee);

                        messagePushService.reffralSettleSuccPush(investment.getInvestor(), borrowing);
                    }
                }
            }
            String msg="【%s】投资人您好，%s 项目已满标出借，%s起计息";
            BasicSetting basic = setting.getBasic();
            msg=String.format(
                    msg,
                    basic.getSiteName(),
                    borrowing.getTitle(),
                    DateUtils.format(borrowing.getLendingDate(), DateUtils.YYYY_MM_DD));

            User user = userService.get(investment.getInvestor());
            smsService.send(user.getMobile(),msg,SmsType.OTHER);
            messagePushService.lendSuccPush(investment.getInvestor(), borrowing);
//            //通知借款人
//            if(BooleanUtils.isTrue(cpcnSettlement.getNoticeBorrower())){
//                noticeBorrowerMap.put("borrower", borrowing.getBorrower());
//                noticeBorrowerMap.put("amount", borrowing.getAmount().subtract(borrowingFee));
//                borrowingNoticeService.lendingToBorrower(borrowing, noticeBorrowerMap);
//            }
//            //通知投资人
//            if(BooleanUtils.isTrue(cpcnSettlement.getNoticeInvestor())){
//                borrowingNoticeService.lendingToInvestors(borrowing, noticeInvestors);
//            }
        }
    }

    @Override
    public Result failureBidSettlement(Borrowing borrowing, String opinion) {

//        if(borrowing.getState().equals(BorrowingState.FAILURE)){
//            return Result.error();
//        }
//
//        // TODO 项目已投金额
//        BigDecimal balance = BigDecimal.ZERO;
//        try {
//            Result result = payService.projectPublishQuery(borrowing.getId());
//            if(!result.isSuccess()){
//                return Result.error(result.getMessage());
//            }
//            Tx1212Response response = (Tx1212Response)result.getData();
//            balance = PayUtils.convertToYuan(response.getBalance());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error("获取项目信息失败");
//        }
//
//        // TODO 投资人已投金额
//        BigDecimal investAmount = BigDecimal.ZERO;
        List<Investment> investments = investmentDao.findList(borrowing.getId());
//        for(Investment investment : investments){
//            investAmount = investAmount.add(investment.getAmount().subtract(investment.getTransferedAmount()));
//        }

//        // TODO 金额校验
//        if(balance.compareTo(investAmount)!=0){
//            return Result.error("金额校验未通过");
//        }

        // TODO 流标退款进度
        String sn = SnUtils.getOrderNo();
        CpcnSettlement cpcnSettlement = cpcnSettlementDao.findSettlement(PaymentOrderType.REFUND, borrowing.getId());
        if(cpcnSettlement == null || cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.failure)){
            cpcnSettlement = new CpcnSettlement();
            cpcnSettlement.setType(PaymentOrderType.REFUND);
            cpcnSettlement.setBorrowing(borrowing.getId());
            cpcnSettlement.setsOrderNo(sn);
            cpcnSettlement.setsStatus(CpcnSettlementStatus.unsettled);
            cpcnSettlementDao.persist(cpcnSettlement);
            cpcnSettlementDao.flush();
            cpcnSettlement = cpcnSettlementDao.refresh(cpcnSettlement);
        }else {
            return Result.error("已在退款结算中");
        }

        // TODO 支付订单
        List<PaymentOrder> orders = new ArrayList<>();
        for(Investment investment : investments){
            // TODO 流标支付订单
            PaymentOrder iPayment = new PaymentOrder();
            iPayment.setStatus(PaymentOrderStatus.PROCESSING);
            iPayment.setType(PaymentOrderType.REFUND);
            iPayment.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
            iPayment.setAmount(investment.getAmount()/*.subtract(investment.getTransferedAmount())*/);
            iPayment.setParentOrderNo(sn);
            iPayment.setOrderNo(SnUtils.getOrderNo());
            iPayment.setMemo(String.format("流标退款,金额[%s]", investment.getAmount()/*CommonUtils.toRMB(investment.getAmount().subtract(investment.getTransferedAmount()))*/));
            iPayment.setOperator(CommonUtils.getLoginName());
            iPayment.setIp(CommonUtils.getRemoteIp());
            iPayment.setUserId(investment.getInvestor());
            iPayment.setBorrowing(borrowing.getId());
            iPayment.setExtOrderNo(investment.getOrderNo());
            paymentOrderService.persist(iPayment);
            orders.add(iPayment);
        }
        try {
//            Result result = payService.projectTransferSettlementBatch(sn, orders, borrowing.getId());
//            if(!result.isSuccess()){
//                return Result.success("退款失败："+result.getMessage(), result.getData());
//            }

            /* 新增借款意见*/
            BorrowingOpinion bo = new BorrowingOpinion();
            bo.setType(BorrowingOpinionType.LEND);
            bo.setCont(opinion);
            bo.setOperator(CommonUtils.getLoginName());
            bo.setIp(CommonUtils.getRemoteIp());
            bo.setBorrowing(borrowing.getId());
            opinionDao.persist(bo);

//            borrowing.setState(BorrowingState.FAILURE);
//            borrowingDao.update(borrowing);

            PayModule payModule = PayPortal.project_settlement_batch.getModuleInstance();
            ProjectSettlementBatchRequest request = new ProjectSettlementBatchRequest();
            request.setSettlement(cpcnSettlement);
            request.setOrders(orders);
            payModule.setRequest(request);
            payModule.setSn(sn);
            Response response = payModule.invoking().getResponse();
            if(!response.isSuccess()){
                return Result.success(response.getMsg());
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @Override
    public void failureBidSettlementSucceed(PaymentOrder order) {

        if(order == null){
            throw new RuntimeException("订单不存在");
        }

        UserFinance userFinance = userFinanceDao.getByUserId(order.getUserId());
        userFinanceDao.lock(userFinance, LockMode.PESSIMISTIC_WRITE);
        switch (order.getType()){
            case REFUND:{

                Borrowing borrowing = borrowingDao.get(order.getBorrowing());
                User user = userService.get(order.getUserId());

                 /* 平台订单 : 流标投资人收款 */
                Order orderFailureBid = new Order();
                orderFailureBid.setUserId(user.getId());
                orderFailureBid.setPayer(-2);
                orderFailureBid.setPayerName("中金存管");
                orderFailureBid.setPayee(order.getUserId());
                orderFailureBid.setPayerName(user.getLoginName());
                orderFailureBid.setStatus(OrderStatus.SUCCESS);
                orderFailureBid.setType(OrderType.REFUND);
                orderFailureBid.setMethod(OrderMethod.CPCN);
                orderFailureBid.setBusiness(borrowing.getId());
                orderFailureBid.setOrderNo(SnUtils.getOrderNo());
                orderFailureBid.setThirdOrderNo(null);
                orderFailureBid.setPayeeBalance(order.getAmount());
                orderFailureBid.setPayeeFee(BigDecimal.ZERO);
                orderFailureBid.setAmount(order.getAmount());
                orderFailureBid.setAmountReceived(order.getAmount());
                orderFailureBid.setPoints(0);
                orderFailureBid.setLaunchDate(new Date());
                orderFailureBid.setFinishDate(new Date());
                orderFailureBid.setOperator(CommonUtils.getLoginName());
                orderFailureBid.setIp(CommonUtils.getRemoteIp());
                orderFailureBid = orderService.persist(orderFailureBid);

//                userFinance.addBalance(order.getAmount(), RechargeBusinessType.GENERAL);
                userFinance.subtractFrozen(order.getAmount());
                Capital capital = new Capital(order.getUserId(), CapitalMethod.REFUND, CapitalType.UNFROZEN, order.getAmount(), userFinance, order.getOrderNo(), order.getOperator(), order.getIp(), "流标退款", orderFailureBid.getId());
                capitalDao.persist(capital);
                break;
            }
            default:
                throw new RuntimeException("订单类型错误");
        }
        userFinanceDao.merge(userFinance);
        logger.info(String.format("退款结算子订单[%s]结算成功", order.getOrderNo()));

        // 更新投资
        Investment investment = investmentDao.find(order.getBorrowing(), order.getUserId(), InvestmentState.INVESTING);
        investment.setState(InvestmentState.FAILURE);
        investmentDao.update(investment);
         /* 更新投资记录*/
        List<InvestmentRecord> investmentRecordList = investmentRecordDao.findListByInvestment(investment.getId());
        for (InvestmentRecord investmentRecord : investmentRecordList) {
            investmentRecord.setState(InvestmentState.FAILURE);
            investmentRecordDao.update(investmentRecord);
        }
        //短信提醒
        messagePushService.investmentRefundSuccPush(investment.getInvestor(), investment.getAmount());
    }

    @Override
    public void failureBidSettlementAllSucceed(CpcnSettlement cpcnSettlement) {
        Borrowing borrowing = borrowingDao.get(cpcnSettlement.getBorrowing());
        borrowing.setProgress(BorrowingProgress.REFUND);
        borrowingDao.update(borrowing);
    }

    public String getRemoteIp() {
        String remoteIp = WebUtils.getRemoteIp(WebUtils.getHttpRequest());
        remoteIp = remoteIp == null ? "-" : remoteIp;
        return remoteIp;
    }

    public String getLoginName() {
        String loginName = SecurityUtils.getSubject().getPrincipal().toString();
        loginName = loginName == null ? "-" : loginName;
        return loginName;
    }

    /**
     * 上传文件
     *
     * @param materials
     * @param borrowingId
     * @return
     */
    public void uploadMaterials(List<MaterialVo> materials, Integer borrowingId) {
        if (materials.isEmpty()) {
            materialDao.deleteList(borrowingId);
        }
        for (MaterialVo material : materials) {//保存材料
            MultipartFile file = material.getFile();
            String operator = material.getOperator();

            if (material.getId() != null) {
                Material pMaterial = materialDao.get(material.getId());
                pMaterial.setType(material.getType());
                pMaterial.setSort(material.getSort());
                pMaterial.setOperator(material.getOperator());
                pMaterial.setTitle(material.getTitle());
                materialDao.merge(pMaterial);
            } else {
                if (file != null && file.getSize() != 0) {
                    String filename = borrowingId + "_" + DateUtils.getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
                    SftpUtils sftp = new SftpUtils();
                    try {
                        sftp.connect().upload("borrowing", filename, file.getInputStream()).disconnect();
                        Material pMaterial = new Material(material.getType(), material.getTitle(), "/borrowing/" + filename, material.getSort(), borrowingId, operator);
                        pMaterial.setLinkType(LinkType.BORROWING);
                        materialDao.persist(pMaterial);
                    } catch (SftpException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        sftp.disconnect();
                    }
                }
            }
        }
    }

    private String getEnv() {
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