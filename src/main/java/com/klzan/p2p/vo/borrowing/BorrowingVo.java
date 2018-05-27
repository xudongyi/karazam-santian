package com.klzan.p2p.vo.borrowing;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.vo.MaterialVo;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowingVo extends BaseVo {

    /**
     * 借款人ID
     */
    private Integer borrower;
    /**
     * 协议ID
     */
    private String agreementId;
    /**
     * 装让人转让协议ID
     */
    private Integer transferAgreementId;
    /**
     * 受让人转让协议ID
     */
    private Integer investTransferAgreementId;
    /**
     * 进度
     */
    private BorrowingProgress progress;

    /**
     * 进度
     */
    private String progressDes;

    /**
     * 状态
     */
    private BorrowingState state;

    /**
     * 状态
     */
    private String stateDes;

    /**
     * 类型
     */
    private BorrowingType type;

    /**
     * 类型
     */
    private String typeDes;

    /**
     * 标题
     */
    private String title;

    /**
     * 介绍
     */
    private String intro;

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
    private PeriodUnit periodUnit;

    /**
     * 计息方式
     */
    private InterestMethod interestMethod;

    /**
     * 利率
     */
    private BigDecimal interestRate;

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
    private GuaranteeMethod guaranteeMethod;

    /**
     * 担保公司
     */
    private Integer guaranteeCorp;

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
    private CreditRating creditRating;

    /**
     * 投资方式
     */
    private InvestmentMethod investmentMethod;

    /**
     * 最低投资
     */
    private BigDecimal investmentMinimum;

    /**
     * 加价幅度（投资时，最低投资额的增量）
     */
    private Integer multiple;

    /**
     * 最高投资
     */
    private BigDecimal investmentMaximum;

    /**
     * 投资返利率
     */
    private BigDecimal investmentRebateRate;

    /**
     * 投资密码
     */
    private String investmentPassword;

    /**
     * 投资开始时间
     */
    private Date investmentStartDate;

    /**
     * 投资结束时间
     */
    private Date investmentEndDate;

    /**
     * 出借时间
     */
    private LendingTime lendingTime;

    /**
     * 还款方式
     */
    private RepaymentMethod repaymentMethod;

    /**
     * 还款服务费收取方式
     */
    private RepaymentFeeMethod repaymentFeeMethod;

    /**
     * 服务费率
     */
    private BigDecimal feeRate;

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
     * 转入方服务费率
     */
    private BigDecimal inTransferFeeRate;

    /**
     * 转出方服务费率
     */
    private BigDecimal outTransferFeeRate;

    /**
     * 奖励利率
     */
    private BigDecimal rewardInterestRate;

    /**
     * 材料
     */
    private List<MaterialVo> materials = new ArrayList<>();

    /**
     * 是否支持优惠券
     */
    private Boolean surportCoupon;
    /**
     * 是否推荐（用于首页）
     */
    private Boolean isRecommend;

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

    private List<BorrowingExtraVo> extras;

    /** 协议签署地 */
    private String agreementPlace;

    /** 项目标签 */
    private String labels;
    /**
     * 是否支持自动投标
     */
    private Boolean autoInvest = Boolean.FALSE;
    /**
     * 是否通知借款人
     */
    private Boolean noticeBorrower = Boolean.FALSE;
    /**
     * 是否通知投资人
     */
    private Boolean noticeInvestor = Boolean.FALSE;


    /**
     * 银行账户开户行
     */
    @Column
    private String bankID;
    /**
     * 银行账户开户行
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

    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public List<MaterialVo> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialVo> materials) {
        this.materials = materials;
    }

    public Integer getBorrower() {
        return borrower;
    }

    public void setBorrower(Integer borrower) {
        this.borrower = borrower;
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

    public String getProgressDes() {
        return progressDes;
    }

    public void setProgressDes(String progressDes) {
        this.progressDes = progressDes;
    }

    public String getStateDes() {
        return stateDes;
    }

    public void setStateDes(String stateDes) {
        this.stateDes = stateDes;
    }

    public String getTypeDes() {
        return typeDes;
    }

    public void setTypeDes(String typeDes) {
        this.typeDes = typeDes;
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

    public InterestMethod getInterestMethod() {
        return interestMethod;
    }

    public void setInterestMethod(InterestMethod interestMethod) {
        this.interestMethod = interestMethod;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
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

    public Integer getGuaranteeCorp() {
        return guaranteeCorp;
    }

    public void setGuaranteeCorp(Integer guaranteeCorp) {
        this.guaranteeCorp = guaranteeCorp;
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

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public BigDecimal getInvestmentMaximum() {
        return investmentMaximum;
    }

    public void setInvestmentMaximum(BigDecimal investmentMaximum) {
        this.investmentMaximum = investmentMaximum;
    }
//
    public BigDecimal getInvestmentRebateRate() {
        return investmentRebateRate;
    }

    public void setInvestmentRebateRate(BigDecimal investmentRebateRate) {
        this.investmentRebateRate = investmentRebateRate;
    }

    public String getInvestmentPassword() {
        return investmentPassword;
    }

    public void setInvestmentPassword(String investmentPassword) {
        this.investmentPassword = investmentPassword;
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

    public BigDecimal getRewardInterestRate() {
        return rewardInterestRate;
    }

    public void setRewardInterestRate(BigDecimal rewardInterestRate) {
        this.rewardInterestRate = rewardInterestRate;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean recommend) {
        isRecommend = recommend;
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

    public List<BorrowingExtraVo> getExtras() {
        return extras;
    }

    public void setExtras(List<BorrowingExtraVo> extras) {
        this.extras = extras;
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

    public String getAgreementPlace() {
        return agreementPlace;
    }

    public void setAgreementPlace(String agreementPlace) {
        this.agreementPlace = agreementPlace;
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

    public Boolean getAutoInvest() {
        return autoInvest;
    }

    public void setAutoInvest(Boolean autoInvest) {
        this.autoInvest = autoInvest;
    }

    public Boolean getNoticeBorrower() {
        return noticeBorrower;
    }

    public void setNoticeBorrower(Boolean noticeBorrower) {
        this.noticeBorrower = noticeBorrower;
    }

    public Boolean getNoticeInvestor() {
        return noticeInvestor;
    }

    public void setNoticeInvestor(Boolean noticeInvestor) {
        this.noticeInvestor = noticeInvestor;
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
}
