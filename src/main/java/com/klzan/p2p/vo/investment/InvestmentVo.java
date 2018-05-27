package com.klzan.p2p.vo.investment;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.*;
import com.klzan.plugin.repayalgorithm.DateLength;
import com.klzan.plugin.repayalgorithm.RepayRecordsStrategyHolder;

import java.math.BigDecimal;
import java.util.Date;

public class InvestmentVo extends BaseVo {
    /**
     * 状态
     */
    private InvestmentState state;
    private String stateStr;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 优惠金额
     */
    private BigDecimal preferentialAmount;
    /**
     * 投资人
     */
    private Integer investor;
    /**
     * 投资人姓名
     */
    private String realName;
    /**
     * 投资人电话号码
     */
    private String mobile;
    /**
     * 是否为体验投资
     */
    private Boolean isExperience = false;
    /**
     * 借款id
     */
    private Integer borrowing;
    /**
     * 借款标题
     */
    private String title;
    /**
     * 借款总金额
     */
    private BigDecimal borrowingAmount;
    /**
     * 借款状态
     */
    private BorrowingState borrowingState;
    private String borrowingStateStr;

    /**
     * 借款类型
     */
    private BorrowingType borrowingType;
    private String borrowingTypeStr;

    /**
     * 借款进度
     */
    private BorrowingProgress borrowingProgress;
    private String borrowingProgressStr;
    private Integer borrower;
    private String borrowerName;
    private BigDecimal interestRate;
    private BigDecimal rewardInterestRate;
    private Integer transfer;

    public Boolean getExperience() {
        return isExperience;
    }

    public void setExperience(Boolean experience) {
        isExperience = experience;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    /**
     * 期限
     */
    private Integer borrowingPeriod;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 期限单位
     */
    private PeriodUnit borrowingPeriodUnit;
    private String borrowingPeriodUnitStr;

    /**
     * 出借日期
     */
    private Date lendingDate;
    /**
     * 计息时间
     */
    private Date interestBeginDate;
    /**
     * 预期收益
     */
    private BigDecimal expectedReturn;
    private RepaymentMethod repaymentMethod;
    private String repaymentMethodStr;

    public BigDecimal getExpectedReturn() {
        DateLength dateLength;
        if (PeriodUnit.MONTH.equals(getBorrowingPeriodUnit())) {
            dateLength = DateLength.month(getBorrowingPeriod(),InterestMethod.T_PLUS_ZERO);
        } else if (PeriodUnit.DAY.equals(getBorrowingPeriodUnit())) {
            dateLength = DateLength.days(getBorrowingPeriod(), InterestMethod.T_PLUS_ONE);
        } else {
            dateLength = DateLength.year(getBorrowingPeriod(), InterestMethod.T_PLUS_ZERO);
        }
        return RepayRecordsStrategyHolder.instanse().generateRepayRecords(getRepaymentMethod(),getAmount(),getRealInterestRate(),dateLength).getTotalPayRecord().getInterest();
    }
    public BigDecimal getRealInterestRate() {
        if(getRewardInterestRate()!=null){
            return getInterestRate().add(getRewardInterestRate());
        }
        return getInterestRate();
    }
    public RepaymentMethod getRepaymentMethod() {
        if (StringUtils.isBlank(repaymentMethodStr)) {
            return null;
        }
        return RepaymentMethod.valueOf(repaymentMethodStr);
    }

    public void setRepaymentMethod(RepaymentMethod repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public String getRepaymentMethodStr() {
        if (StringUtils.isBlank(repaymentMethodStr)) {
            return null;
        }
        return RepaymentMethod.valueOf(repaymentMethodStr).getDisplayName();
    }

    public void setRepaymentMethodStr(String repaymentMethodStr) {
        this.repaymentMethodStr = repaymentMethodStr;
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

    public InvestmentState getState() {
        if (StringUtils.isBlank(stateStr)) {
            return null;
        }
        return InvestmentState.valueOf(stateStr);
    }

    public String getStateStr() {
        if (StringUtils.isBlank(stateStr)) {
            return null;
        }
        return InvestmentState.valueOf(stateStr).getDisplayName();
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public void setState(InvestmentState state) {
        this.state = state;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(BigDecimal preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public Integer getInvestor() {
        return investor;
    }

    public void setInvestor(Integer investor) {
        this.investor = investor;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Boolean getIsExperience() {
        return isExperience;
    }

    public void setIsExperience(Boolean isExperience) {
        isExperience = isExperience;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getBorrowingAmount() {
        return borrowingAmount;
    }

    public void setBorrowingAmount(BigDecimal borrowingAmount) {
        this.borrowingAmount = borrowingAmount;
    }

    public BorrowingState getBorrowingState() {
        if (StringUtils.isBlank(borrowingStateStr)) {
            return null;
        }
        return BorrowingState.valueOf(borrowingStateStr);
    }

    public void setBorrowingState(BorrowingState borrowingState) {
        this.borrowingState = borrowingState;
    }

    public String getBorrowingStateStr() {
        if (StringUtils.isBlank(borrowingStateStr)) {
            return null;
        }
        return BorrowingState.valueOf(borrowingStateStr).getDisplayName();
    }

    public void setBorrowingStateStr(String borrowingStateStr) {
        this.borrowingStateStr = borrowingStateStr;
    }

    public BorrowingType getBorrowingType() {
        if (StringUtils.isBlank(borrowingTypeStr)) {
            return null;
        }
        return BorrowingType.valueOf(borrowingTypeStr);
    }

    public void setBorrowingType(BorrowingType borrowingType) {
        this.borrowingType = borrowingType;
    }

    public String getBorrowingTypeStr() {
        if (StringUtils.isBlank(borrowingTypeStr)) {
            return null;
        }
        return BorrowingType.valueOf(borrowingTypeStr).getAlias();
    }

    public void setBorrowingTypeStr(String borrowingTypeStr) {
        this.borrowingTypeStr = borrowingTypeStr;
    }

    public BorrowingProgress getBorrowingProgress() {
        if (StringUtils.isBlank(borrowingProgressStr)) {
            return null;
        }
        return BorrowingProgress.valueOf(borrowingProgressStr);
    }

    public void setBorrowingProgress(BorrowingProgress borrowingProgress) {
        this.borrowingProgress = borrowingProgress;
    }

    public String getBorrowingProgressStr() {
        if (StringUtils.isBlank(borrowingProgressStr)) {
            return null;
        }
        return BorrowingProgress.valueOf(borrowingProgressStr).getDisplayName();
    }

    public void setBorrowingProgressStr(String borrowingProgressStr) {
        this.borrowingProgressStr = borrowingProgressStr;
    }

    public Integer getBorrower() {
        return borrower;
    }

    public void setBorrower(Integer borrower) {
        this.borrower = borrower;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getRewardInterestRate() {
        return rewardInterestRate;
    }

    public void setRewardInterestRate(BigDecimal rewardInterestRate) {
        this.rewardInterestRate = rewardInterestRate;
    }

    public Integer getBorrowingPeriod() {
        return borrowingPeriod;
    }

    public void setBorrowingPeriod(Integer borrowingPeriod) {
        this.borrowingPeriod = borrowingPeriod;
    }

    public PeriodUnit getBorrowingPeriodUnit() {
        if (StringUtils.isBlank(borrowingPeriodUnitStr)) {
            return null;
        }
        return PeriodUnit.valueOf(borrowingPeriodUnitStr);
    }

    public void setBorrowingPeriodUnit(PeriodUnit borrowingPeriodUnit) {
        this.borrowingPeriodUnit = borrowingPeriodUnit;
    }

    public String getBorrowingPeriodUnitStr() {
        if (StringUtils.isBlank(borrowingPeriodUnitStr)) {
            return null;
        }
        return PeriodUnit.valueOf(borrowingPeriodUnitStr).getDisplayName();
    }

    public void setBorrowingPeriodUnitStr(String borrowingPeriodUnitStr) {
        this.borrowingPeriodUnitStr = borrowingPeriodUnitStr;
    }
}
