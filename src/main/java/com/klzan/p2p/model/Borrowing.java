package com.klzan.p2p.model;

import com.klzan.core.util.SpringUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.dao.user.UserDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.base.BaseModel;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.util.AccountantUtils;
import com.klzan.p2p.vo.user.UserVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 借款
 */
@Entity
@Table(name = "karazam_borrowing")
public class Borrowing extends BaseModel {

    /**
     * 进度
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BorrowingProgress progress;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BorrowingState state;

    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BorrowingType type;

    /**
     * 标题
     */
    private String title;

    /**
     * 介绍
     */
    @Lob
    private String intro;

    /**
     * LOGO
     */
    private String logo;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 期限
     */
    private Integer period;

    /**
     * 期限单位
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PeriodUnit periodUnit;

    /**
     * 还款期数
     */
    private Integer repayPeriod;

    /**
     * 基本利率
     */
    private BigDecimal interestRate;

    /**
     * 利息
     */
    private BigDecimal interest;

    /**
     * 描述
     */
    private String description;

    /**
     * 借款用途
     */
    private String purpose;

    /**
     * 实地调查
     */
    private String fieldInquiry;

    /**
     * 信用调查
     */
    private String creditInquiry;

    /**
     * 还款调查
     */
    private String repaymentInquiry;

    /**
     * 担保方式
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private GuaranteeMethod guaranteeMethod;

    /**
     * 担保金额
     */
    private BigDecimal guaranteeCapital;

    /**
     * 担保公司
     */
    private Integer guaranteeCorp;

    /**
     * 担保证明
     */
    private String guaranteeCertification;

    /**
     * 担保措施
     */
    private String guarantee;

    /**
     * 抵押物
     */
    private String collateral;

    /**
     * 风险分析
     */
    private String riskAnalysis;

    /**
     * 风险度
     */
    private BigDecimal riskDegree;

    /**
     * 信用评级
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 50)
    private CreditRating creditRating;

    /**
     * 投资方式
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private InvestmentMethod investmentMethod;

    /**
     * 最低投资
     */
    @Column(precision = 19, scale = 2)
    private BigDecimal investmentMinimum;

    /**
     * 最高投资
     */
    private BigDecimal investmentMaximum;

    /**
     * 投资返利率
     */
    private BigDecimal investmentRebateRate;

    /**
     * 已投金额
     */
    private BigDecimal investedAmount;

    /**
     * 投资开始时间
     */
    private Date investmentStartDate;

    /**
     * 投资结束时间
     */
    private Date investmentEndDate;

    /**
     * 投资完成时间
     */
    private Date investmentFinishDate;

    /**
     * 借款发布时间
     */
    private Date publishDate;

    /**
     * 借款出借时间
     */
    private Date lendingDate;

    /**
     * 计息开始时间
     */
    private Date interestBeginDate;

    /**
     * 计息方式
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private InterestMethod interestMethod = InterestMethod.T_PLUS_ZERO;

    /**
     * 借款完成时间
     */
    private Date repaymentFinishDate;

    /**
     * 出借时间类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private LendingTime lendingTime;

    /**
     * 还款方式
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private RepaymentMethod repaymentMethod;

    /**
     * 还款服务费收取方式
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private RepaymentFeeMethod repaymentFeeMethod;

    /**
     * 借款服务费率
     */
    private BigDecimal feeRate;

    /**
     * 借款服务费
     */
    private BigDecimal fee;

    /**
     * 已付服务费
     */
    private BigDecimal paidFee;

    /**
     * 还款服务费率
     */
    private BigDecimal repaymentFeeRate;

    /**
     * 回收服务费率
     */
    private BigDecimal recoveryFeeRate;

    /**
     * 逾期利率
     */
    private BigDecimal overdueInterestRate;

    /**
     * 严重逾期开始时间
     */
    private Integer seriousOverdueStartPeriod;

    /**
     * 严重逾期利率
     */
    private BigDecimal seriousOverdueInterestRate;

    /**
     * 借款人ID
     */
    private Integer borrower;

    /**
     * IP
     */
    private String ip;

    /**
     * SN
     */
    private String sn;

    /**
     * 转入方服务费率
     */
    private BigDecimal inTransferFeeRate;

    /**
     * 转出方服务费率
     */
    private BigDecimal outTransferFeeRate;

    /**
     * 是否推荐（用于首页）
     */
    private Boolean isRecommend;

    /**
     * 是否体验标
     */
    private Boolean isExperience = false;

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 加价幅度（投资时，最低投资额的增量）
     */
    private Integer multiple;

    /**
     * 奖励利率
     */
    private BigDecimal rewardInterestRate;

    /**
     * 支付占位次数
     */
    private Integer occupyCount;

    /**
     * 支付占位金额
     */
    private BigDecimal occupyAmount;

    /** 标的概况 */
    private String subjectSituation;

    /** 交易合同编号 */
    private String tradingContractNo;

    /** 商业保理合同编号 */
    private String commercialFactoringContractNo;

    /** 商业保理公司营业执照号 */
    private String factoringComBussinessNo;

    /** 商业保理合同中应收账款 */
    private BigDecimal receivables;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 项目登记编号
     */
    private String projectNo;

    /**
     * 协议ID
     */
    @Column(nullable = false)
    private Integer agreementId;

    /**
     * 装让人转让协议ID
     */
    @Column(nullable = false)
    private Integer transferAgreementId;
    /**
     * 受让人转让协议ID
     */
    @Column(nullable = false)
    private Integer investTransferAgreementId;
    /**
     * 协议签署地
     */
    @Column
    private String agreementPlace;
    /**
     * 项目标签
     */
    @Column
    private String labels;
    /**
     * 优惠券是否可用
     */
    @Column
    private Boolean surportCoupon= Boolean.FALSE;
    /**
     * 是否支持自动投标
     */
    private Boolean autoInvest = Boolean.FALSE;


    /**
     * 银行账户开户行
     */
    @Column
    private String bankID;
    /**
     * 银行账户名称
     */
    @Column
    private String bankAccountName;
    /**
     * 银行账户号码
     */
    @Column
    private String bankAccountNumber;
    /**
     * 银行账户分支行
     */
    @Column
    private String bankBranchName;
    /**
     * 省份
     */
    @Column
    private String bankProvince;
    /**
     * 城市
     */
    @Column
    private String bankCity;

    public Borrowing() {
    }

    public Integer getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Integer agreementId) {
        this.agreementId = agreementId;
    }

    public BorrowingProgress getProgress() {
        return progress;
    }

    public void setProgress(BorrowingProgress progress) {
        this.progress = progress;
    }

    public BorrowingState getState() {
        return state;
    }

    public void setState(BorrowingState state) {
        this.state = state;
    }

    public BorrowingType getType() {
        return type;
    }

    public void setType(BorrowingType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public PeriodUnit getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(PeriodUnit periodUnit) {
        this.periodUnit = periodUnit;
    }

    public Integer getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(Integer repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getFieldInquiry() {
        return fieldInquiry;
    }

    public void setFieldInquiry(String fieldInquiry) {
        this.fieldInquiry = fieldInquiry;
    }

    public String getCreditInquiry() {
        return creditInquiry;
    }

    public void setCreditInquiry(String creditInquiry) {
        this.creditInquiry = creditInquiry;
    }

    public String getRepaymentInquiry() {
        return repaymentInquiry;
    }

    public void setRepaymentInquiry(String repaymentInquiry) {
        this.repaymentInquiry = repaymentInquiry;
    }

    public GuaranteeMethod getGuaranteeMethod() {
        return guaranteeMethod;
    }

    public void setGuaranteeMethod(GuaranteeMethod guaranteeMethod) {
        this.guaranteeMethod = guaranteeMethod;
    }

    public BigDecimal getGuaranteeCapital() {
        return guaranteeCapital;
    }

    public void setGuaranteeCapital(BigDecimal guaranteeCapital) {
        this.guaranteeCapital = guaranteeCapital;
    }

    public Integer getGuaranteeCorp() {
        return guaranteeCorp;
    }

    public void setGuaranteeCorp(Integer guaranteeCorp) {
        this.guaranteeCorp = guaranteeCorp;
    }

    public String getGuaranteeCertification() {
        return guaranteeCertification;
    }

    public void setGuaranteeCertification(String guaranteeCertification) {
        this.guaranteeCertification = guaranteeCertification;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getCollateral() {
        return collateral;
    }

    public void setCollateral(String collateral) {
        this.collateral = collateral;
    }

    public String getRiskAnalysis() {
        return riskAnalysis;
    }

    public void setRiskAnalysis(String riskAnalysis) {
        this.riskAnalysis = riskAnalysis;
    }

    public BigDecimal getRiskDegree() {
        return riskDegree;
    }

    public void setRiskDegree(BigDecimal riskDegree) {
        this.riskDegree = riskDegree;
    }

    public CreditRating getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(CreditRating creditRating) {
        this.creditRating = creditRating;
    }

    public InvestmentMethod getInvestmentMethod() {
        return investmentMethod;
    }

    public void setInvestmentMethod(InvestmentMethod investmentMethod) {
        this.investmentMethod = investmentMethod;
    }

    public BigDecimal getInvestmentMinimum() {
        return investmentMinimum;
    }

    public void setInvestmentMinimum(BigDecimal investmentMinimum) {
        this.investmentMinimum = investmentMinimum;
    }

    public BigDecimal getInvestmentMaximum() {
        return investmentMaximum;
    }

    public void setInvestmentMaximum(BigDecimal investmentMaximum) {
        this.investmentMaximum = investmentMaximum;
    }

    public BigDecimal getInvestmentRebateRate() {
        return investmentRebateRate;
    }

    public void setInvestmentRebateRate(BigDecimal investmentRebateRate) {
        this.investmentRebateRate = investmentRebateRate;
    }

    public BigDecimal getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(BigDecimal investedAmount) {
        this.investedAmount = investedAmount;
    }

    public Date getInvestmentStartDate() {
        return investmentStartDate;
    }

    public void setInvestmentStartDate(Date investmentStartDate) {
        this.investmentStartDate = investmentStartDate;
    }

    public Date getInvestmentEndDate() {
        return investmentEndDate;
    }

    public void setInvestmentEndDate(Date investmentEndDate) {
        this.investmentEndDate = investmentEndDate;
    }

    public Date getInvestmentFinishDate() {
        return investmentFinishDate;
    }

    public void setInvestmentFinishDate(Date investmentFinishDate) {
        this.investmentFinishDate = investmentFinishDate;
    }

    public LendingTime getLendingTime() {
        return lendingTime;
    }

    public void setLendingTime(LendingTime lendingTime) {
        this.lendingTime = lendingTime;
    }

    public RepaymentMethod getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(RepaymentMethod repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public RepaymentFeeMethod getRepaymentFeeMethod() {
        return repaymentFeeMethod;
    }

    public void setRepaymentFeeMethod(RepaymentFeeMethod repaymentFeeMethod) {
        this.repaymentFeeMethod = repaymentFeeMethod;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(BigDecimal paidFee) {
        this.paidFee = paidFee;
    }

    public BigDecimal getRepaymentFeeRate() {
        return repaymentFeeRate;
    }

    public void setRepaymentFeeRate(BigDecimal repaymentFeeRate) {
        this.repaymentFeeRate = repaymentFeeRate;
    }

    public BigDecimal getRecoveryFeeRate() {
        return recoveryFeeRate;
    }

    public void setRecoveryFeeRate(BigDecimal recoveryFeeRate) {
        this.recoveryFeeRate = recoveryFeeRate;
    }

    public BigDecimal getOverdueInterestRate() {
        return overdueInterestRate;
    }

    public void setOverdueInterestRate(BigDecimal overdueInterestRate) {
        this.overdueInterestRate = overdueInterestRate;
    }

    public Integer getSeriousOverdueStartPeriod() {
        return seriousOverdueStartPeriod;
    }

    public void setSeriousOverdueStartPeriod(Integer seriousOverdueStartPeriod) {
        this.seriousOverdueStartPeriod = seriousOverdueStartPeriod;
    }

    public BigDecimal getSeriousOverdueInterestRate() {
        return seriousOverdueInterestRate;
    }

    public void setSeriousOverdueInterestRate(BigDecimal seriousOverdueInterestRate) {
        this.seriousOverdueInterestRate = seriousOverdueInterestRate;
    }

    public Integer getBorrower() {
        return borrower;
    }

    public void setBorrower(Integer borrower) {
        this.borrower = borrower;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public BigDecimal getInTransferFeeRate() {
        return inTransferFeeRate;
    }

    public void setInTransferFeeRate(BigDecimal inTransferFeeRate) {
        this.inTransferFeeRate = inTransferFeeRate;
    }

    public BigDecimal getOutTransferFeeRate() {
        return outTransferFeeRate;
    }

    public void setOutTransferFeeRate(BigDecimal outTransferFeeRate) {
        this.outTransferFeeRate = outTransferFeeRate;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    public Boolean getExperience() {
        return isExperience;
    }

    public void setExperience(Boolean experience) {
        isExperience = experience;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public BigDecimal getRewardInterestRate() {
        return rewardInterestRate;
    }

    public void setRewardInterestRate(BigDecimal rewardInterestRate) {
        this.rewardInterestRate = rewardInterestRate;
    }

    public Integer getOccupyCount() {
        return occupyCount;
    }

    public void setOccupyCount(Integer occupyCount) {
        this.occupyCount = occupyCount;
    }

    public BigDecimal getOccupyAmount() {
        return occupyAmount;
    }

    public void setOccupyAmount(BigDecimal occupyAmount) {
        this.occupyAmount = occupyAmount;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getRepaymentFinishDate() {
        return repaymentFinishDate;
    }

    public void setRepaymentFinishDate(Date repaymentFinishDate) {
        this.repaymentFinishDate = repaymentFinishDate;
    }

    public Date getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(Date lendingDate) {
        this.lendingDate = lendingDate;
    }

    public Date getInterestBeginDate() {
        return interestBeginDate;
    }

    public void setInterestBeginDate(Date interestBeginDate) {
        this.interestBeginDate = interestBeginDate;
    }

    public InterestMethod getInterestMethod() {
        return interestMethod;
    }

    public void setInterestMethod(InterestMethod interestMethod) {
        this.interestMethod = interestMethod;
    }

    public String getSubjectSituation() {
        return subjectSituation;
    }

    public void setSubjectSituation(String subjectSituation) {
        this.subjectSituation = subjectSituation;
    }

    public String getTradingContractNo() {
        return tradingContractNo;
    }

    public void setTradingContractNo(String tradingContractNo) {
        this.tradingContractNo = tradingContractNo;
    }

    public String getCommercialFactoringContractNo() {
        return commercialFactoringContractNo;
    }

    public void setCommercialFactoringContractNo(String commercialFactoringContractNo) {
        this.commercialFactoringContractNo = commercialFactoringContractNo;
    }

    public String getFactoringComBussinessNo() {
        return factoringComBussinessNo;
    }

    public void setFactoringComBussinessNo(String factoringComBussinessNo) {
        this.factoringComBussinessNo = factoringComBussinessNo;
    }

    public BigDecimal getReceivables() {
        return receivables;
    }

    public void setReceivables(BigDecimal receivables) {
        this.receivables = receivables;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    /**
     * 借款进度
     */
    @Transient
    public String getProgressDes() {
        if(getProgress().equals(BorrowingProgress.INQUIRING) && getState().equals(BorrowingState.FAILURE)){
            return "调查失败";
        }
        if(getProgress().equals(BorrowingProgress.APPROVAL) && getState().equals(BorrowingState.FAILURE)){
            return "审批失败";
        }
        if(getProgress().equals(BorrowingProgress.INVESTING)){
            if(getState().equals(BorrowingState.FAILURE) || getState().equals(BorrowingState.EXPIRY)){
                return "投资" + getState().getDisplayName();
            }
            if(getIsInvestFull()){
                return "融资完成";
            }
            if(getInvestmentStartDate()!=null && new Date().before(getInvestmentStartDate())){
                return "预告中";
            }
            if(getInvestmentEndDate()!=null && new Date().after(getInvestmentEndDate())){
                return "已流标";
            }
        }
//        if(getProgress().equals(BorrowingProgress.LENDING)){
//            return "融资完成";
//        }
        return progress.getDisplayName();
    }

    /**
     * 借款进度
     */
    @Transient
    public String getProgressMobileDes() {
        if(getProgress().equals(BorrowingProgress.INQUIRING) && getState().equals(BorrowingState.FAILURE)){
            return "调查失败";
        }
        if(getProgress().equals(BorrowingProgress.APPROVAL) && getState().equals(BorrowingState.FAILURE)){
            return "审批失败";
        }
        if(getProgress().equals(BorrowingProgress.INVESTING)){
            if(getState().equals(BorrowingState.FAILURE) || getState().equals(BorrowingState.EXPIRY)){
                return "投资" + getState().getDisplayName();
            }
            if(getIsInvestFull()){
                return "已满额";
            }
            if(getInvestmentStartDate()!=null && new Date().before(getInvestmentStartDate())){
                return "预告中";
            }
            if(getInvestmentEndDate()!=null && new Date().after(getInvestmentEndDate())){
                return "已流标";
            }
        }
        if(getProgress().equals(BorrowingProgress.LENDING)){
            return "已满额";
        }
        return progress.getDisplayName();
    }

    /**
     * 投资进度
     */
    @Transient
    public BigDecimal getInvestmentProgress() {
        return getInvestedAmount().divide(getAmount(), 4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100));
    }

    /**
     * 是否预告中
     */
    @Transient
    public Boolean getIsForecasting() {
        return getProgress() == BorrowingProgress.INVESTING && getInvestmentStartDate()!=null && new Date().before(getInvestmentStartDate());
    }

    /**
     * 剩余投资金额
     */
    @Transient
    public BigDecimal getSurplusInvestmentAmount() {
        return getAmount().subtract(getInvestedAmount().add(getOccupyAmount()));
    }

    /**
     * 借款期限
     */
    @Transient
    public String getPeriodDes() {
        return getPeriod().intValue() + getPeriodUnitDes();
    }

    /**
     * 期限单位
     */
    @Transient
    public String getPeriodUnitDes() {
        return getPeriodUnit().getDisplayName();
    }

    /**
     * 进度状态
     */
    @Transient
    public String getStateDes() {
        return state.getDisplayName();
    }

    /**
     * 借款类型
     */
    @Transient
    public String getTypeDes() {
        return type.getAlias();
    }

    /**
     * 还款方式
     */
    @Transient
    public String getRepaymentMethodDes() {
        return getRepaymentMethod().getDisplayName();
    }

    /**
     * 还款服务费收取方式
     */
    @Transient
    public String getRepaymentFeeMethodDes() {
        return getRepaymentFeeMethod().getDisplayName();
    }

    /**
     * 投资开始时间
     */
    @Transient
    public Long getInvestmentStartDateDes() {
        return getInvestmentStartDate()==null?0:getInvestmentStartDate().getTime();
    }

    /**
     * 验证出借
     */
    @Transient
    public boolean verifyLending() {
        return getProgress() == BorrowingProgress.LENDING && getState() == BorrowingState.WAIT && getIsInvestFull(); //投满且状态正确
    }

    /**
     * 验证投资
     *
     * @return 验证是否通过
     */
    @Transient
    public boolean verifyInvest() {
        boolean isRichStartDate = true;
        if (null != getInvestmentStartDate()) {
            isRichStartDate = getInvestmentStartDate().before(new Date());
        }
        return getCanInvest() && getState() == BorrowingState.WAIT
                && (getInvestmentEndDate() == null || getInvestmentEndDate().after(new Date()))
                && isRichStartDate
                && (getAmount().compareTo(getInvestedAmount().add(getOccupyAmount())) >= 0);
    }

    /**
     * 验证投资
     *
     * @param amount 投资额度
     * @return 验证是否通过
     */
    @Transient
    public boolean verifyInvest(BigDecimal amount) {
        return getCanInvest() && getState() == BorrowingState.WAIT
                && (getInvestmentMinimum().compareTo(amount) <= 0 || getSurplusInvestmentAmount().compareTo(amount) == 0)
                && (getInvestmentMaximum() == null || getInvestmentMaximum().compareTo(amount) >= 0)
                && (getInvestmentEndDate() == null || getInvestmentEndDate().after(new Date()))
                && (getAmount().compareTo(getInvestedAmount().add(getOccupyAmount()).add(amount)) >= 0);
    }

    /**
     * 是否投满
     */
    @Transient
    public boolean getIsInvestFull() {
        return getAmount().compareTo(getInvestedAmount()) == 0 && getOccupyCount() == 0;  //投满且无占位
    }

    /**
     * 判断是否失败
     *
     * @return 是否失败
     */
    @Transient
    public boolean getIsFailure() {
        if (getState() == BorrowingState.FAILURE || getState() == BorrowingState.EXPIRY
            || getProgress() == BorrowingProgress.EXPIRE || getProgress() == BorrowingProgress.RESCIND) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否投资流标
     *
     * @return 是否投资流标
     */
    @Transient
    public boolean getIsFailureBid() {
        if (getState() == BorrowingState.EXPIRY && getProgress() == BorrowingProgress.EXPIRE) {
            return true;
        }
        return false;
    }

    /**
     * 实际利率/年华收益率 ( = 利率 + 奖励利率 )
     * @return
     */
    @Transient
    public BigDecimal getRealInterestRate() {
        if(getRewardInterestRate()!=null){
            return getInterestRate().add(getRewardInterestRate());
        }
        return getInterestRate();
    }

    /**
     * 实际利率/年华收益率 ( = 利率 + 奖励利率 )
     */
    @Transient
    public String getInterestRateByYear() {
        if(getRewardInterestRate()!=null){
            return getInterestRate().add(getRewardInterestRate()) + "%";
        }else{
            return getInterestRate() + "%";
        }
    }

    /**
     * 还款金额（ = 本金 + （本金 * 实际利率 / 100 ））
     * @return
     */
    @Transient
    public BigDecimal getRepaymentAmount() {
        return getAmount().add(AccountantUtils.calExpense(getAmount(), getRealInterestRate()));
    }

    /**
     * 还款服务费（ = 本金 + 还款服务费率 / 100 ）
     * @return
     */
    @Transient
    public BigDecimal getRepaymentFee() {
        return AccountantUtils.calFee(getAmount(), getRepaymentFeeRate());
    }

    /**
     * 借款服务费（ = 本金 + 借款服务费率 / 100 ）
     * @return
     */
    @Transient
    public BigDecimal getBorrowingFee() {
        return AccountantUtils.calFee(getAmount(), getFeeRate());
    }

//    /**
//     * 计算借款服务费
//     * @param amount 金额
//     * @return 借款服务费
//     */
//    @Transient
//    public BigDecimal computeFee(BigDecimal amount) {
//        return getFeeRate().compareTo(BigDecimal.ZERO) > 0 ? getFeeRate().divide(BigDecimal.TEN).divide(BigDecimal.TEN)
//                .multiply(amount).setScale(2, BigDecimal.ROUND_DOWN) : BigDecimal.ZERO;
//    }

    /**
     * 添加投资额度
     *
     * @param amount
     *            投资额度
     * @return 是否有效
     */
    @Transient
    public void addInvestedAmount(BigDecimal amount) {
        setInvestedAmount(getInvestedAmount().add(amount));
    }

    /**
     * 添加占位额度
     *
     * @param amount
     *            占位额度
     * @return 是否有效
     */
    @Transient
    public void addOccupyAmount(BigDecimal amount) {
        setOccupyAmount(getOccupyAmount().add(amount));
    }

    /**
     * 减少占位额度
     *
     * @param amount
     *            占位额度
     * @return 是否有效
     */
    @Transient
    public void subtractOccupyAmount(BigDecimal amount) {
        setOccupyAmount(getOccupyAmount().subtract(amount));
    }

    /**
     * 增加占位
     *
     */
    @Transient
    public void addOccupyCount() {
        setOccupyCount(getOccupyCount() + 1);
    }

    /**
     * 减少占位
     *
     */
    @Transient
    public void subtractOccupyCount() {
        setOccupyCount(getOccupyCount() - 1);
    }

    /**
     * 还款完成时间（计划/实际）
     */
    @Transient
    public Date getRepaymentFinishDateDes() {
        return getRepaymentFinishDate()!=null?getRepaymentFinishDate():getFinalPayDate();
    }

    /**
     * 是否提前还款
     */
    @Transient
    public String getAheadDes() {
        return getAhead()!=null&&getAhead()?"是":"否";
    }

    /**
     * 是否有三方银行账户
     */
    @Transient
    public Boolean hasBankAccountNumber() {
        return StringUtils.isNotBlank(getBankAccountNumber());
    }

    /**
     * 下期还款日
     */
    @Transient
    private Date nextPayDate;

    /**
     * 最后一期还款日
     */
    @Transient
    private Date finalPayDate;

    /**
     * 是否提前还款
     */
    @Transient
    private Boolean ahead;

    /**
     * 还款进度
     */
    @Transient
    private String repaymentProgress;

    public Date getNextPayDate() {
        return nextPayDate;
    }

    public void setNextPayDate(Date nextPayDate) {
        this.nextPayDate = nextPayDate;
    }

    public Date getFinalPayDate() {
        return finalPayDate;
    }

    public void setFinalPayDate(Date finalPayDate) {
        this.finalPayDate = finalPayDate;
    }

    public Boolean getAhead() {
        return ahead;
    }

    public void setAhead(Boolean ahead) {
        this.ahead = ahead;
    }

    public String getRepaymentProgress() {
        return repaymentProgress;
    }

    public void setRepaymentProgress(String repaymentProgress) {
        this.repaymentProgress = repaymentProgress;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public Boolean getSurportCoupon() {
        return surportCoupon;
    }

    public void setSurportCoupon(Boolean surportCoupon) {
        this.surportCoupon = surportCoupon;
    }

    public String getAgreementPlace() {
        return agreementPlace;
    }

    public void setAgreementPlace(String agreementPlace) {
        this.agreementPlace = agreementPlace;
    }

    public Integer getTransferAgreementId() {
        return transferAgreementId;
    }

    public void setTransferAgreementId(Integer transferAgreementId) {
        this.transferAgreementId = transferAgreementId;
    }

    public Integer getInvestTransferAgreementId() {
        return investTransferAgreementId;
    }

    public void setInvestTransferAgreementId(Integer investTransferAgreementId) {
        this.investTransferAgreementId = investTransferAgreementId;
    }

    public Boolean getAutoInvest() {
        return autoInvest;
    }

    public void setAutoInvest(Boolean autoInvest) {
        this.autoInvest = autoInvest;
    }

    @Transient
    public boolean getShow() {
        if (progress == BorrowingProgress.PREVIEWING
                || progress == BorrowingProgress.AUTOINVESTING
                || progress == BorrowingProgress.INVESTING
                || progress == BorrowingProgress.LENDING
                || progress == BorrowingProgress.REPAYING
                || progress == BorrowingProgress.COMPLETED) {
            return true;
        }
        return false;
    }

    @Transient
    public boolean getShowInvesting() {
        return progress == BorrowingProgress.PREVIEWING || progress == BorrowingProgress.AUTOINVESTING || progress == BorrowingProgress.INVESTING;
    }

    @Transient
    public boolean getCanInvest() {
        return progress == BorrowingProgress.AUTOINVESTING || progress == BorrowingProgress.INVESTING;
    }

    @Transient
    public boolean getCanManualInvest() {
        return progress == BorrowingProgress.INVESTING;
    }

    @Transient
    public boolean getCanAutoInvest() {
        return progress == BorrowingProgress.AUTOINVESTING;
    }

    @Transient
    public String getInterestMethodDes() {
        if (null != interestMethod) {
            return interestMethod.getDisplayBgName();
        }
        return "";
    }

    @Transient
    public String getBorrowerName() {
        if (getBorrower()!=null) {
//            UserDao userDao = SpringUtils.getBean(UserDao.class);
//            User user = userDao.get(getBorrower());
//            return user.getLoginName();
            UserService userService = SpringUtils.getBean(UserService.class);
            UserVo borrowerInfo = userService.getUserById(getBorrower());
            if(borrowerInfo.getType().equals(UserType.ENTERPRISE)){
                return borrowerInfo.getCorpName();
            }
            return borrowerInfo.getRealName();

        }
        return "";
    }

    @Transient
    public String getBorrowerMobile() {
        if (getBorrower()!=null) {
            UserDao userDao = SpringUtils.getBean(UserDao.class);
            User user = userDao.get(getBorrower());
            return user.getMobile();
        }
        return "";
    }

//    @Transient
//    public Map getUserInfo() {
//        Map map = new HashMap();
//        map.put("loginName", "");
//        map.put("mobile", "");
//        map.put("userType", "");
//        if (getBorrower()!=null) {
//            UserDao userDao = SpringUtils.getBean(UserDao.class);
//            User user = userDao.get(getBorrower());
//            map.put("loginName", user.getLoginName());
//            map.put("mobile", user.getMobile());
//            map.put("userType", user.getType());
//        }
//        return map;
//    }
}
