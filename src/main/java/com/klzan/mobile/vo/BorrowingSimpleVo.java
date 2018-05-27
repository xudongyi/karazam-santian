package com.klzan.mobile.vo;
import com.klzan.p2p.enums.PeriodUnit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Created by suhao Date: 2016/12/22 Time: 21:18
 *
 * @version: 1.0
 */
public class BorrowingSimpleVo implements Serializable {
    private  boolean allowInvest;//是否可投
    private  Integer period;//投资期限
    private String periodUnit;//期限单位
    private Integer borrowingId;
    private String title;
    private Date investmentStartDate; //投资开始时间
    private  BigDecimal allowInvestmentAmount; //允许投资金额
    private  BigDecimal alreadyInvestmentAmount; //已投资金额
    private BigDecimal amount;//总额
    private String  borrowingType; //借款类型
    private String repaymentMethod; //还款方式
    private String borrowingState;//标状态（）
    private BigDecimal InvestmentProgress;//投资进度
    private BigDecimal interestRate;//利率 投资利率+奖励利率

    public boolean isAllowInvest() {
        return allowInvest;
    }

    public void setAllowInvest(boolean allowInvest) {
        this.allowInvest = allowInvest;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(String periodUnit) {
        this.periodUnit = periodUnit;
    }

    public Integer getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(Integer borrowingId) {
        this.borrowingId = borrowingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Date getInvestmentStartDate() {
        return investmentStartDate;
    }

    public void setInvestmentStartDate(Date investmentStartDate) {
        this.investmentStartDate = investmentStartDate;
    }

    public BigDecimal getAllowInvestmentAmount() {
        return allowInvestmentAmount;
    }

    public void setAllowInvestmentAmount(BigDecimal allowInvestmentAmount) {
        this.allowInvestmentAmount = allowInvestmentAmount;
    }

    public BigDecimal getAlreadyInvestmentAmount() {
        return alreadyInvestmentAmount;
    }

    public void setAlreadyInvestmentAmount(BigDecimal alreadyInvestmentAmount) {
        this.alreadyInvestmentAmount = alreadyInvestmentAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBorrowingType() {
        return borrowingType;
    }

    public void setBorrowingType(String borrowingType) {
        this.borrowingType = borrowingType;
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public String getBorrowingState() {
        return borrowingState;
    }

    public void setBorrowingState(String borrowingState) {
        this.borrowingState = borrowingState;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public BigDecimal getInvestmentProgress() {
        return InvestmentProgress;
    }

    public void setInvestmentProgress(BigDecimal investmentProgress) {
        InvestmentProgress = investmentProgress;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
