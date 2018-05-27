package com.klzan.mobile.vo;

import com.klzan.p2p.enums.PeriodUnit;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/6/5 0005.
 */
public class InvestmentRecordVo {

    private String projectName;
    private String rate;
    private Integer borrowingPeriod;
    private PeriodUnit borrowingPeriodUnit;
    private BigDecimal amount;
    private String state;
    private  long endDate;
    private Integer projectId;
    private Boolean isTransfer;
    private Integer transferId;
    private Integer investmentId;
    private Boolean IsCancel=false;
    private Integer InvestmentPeriod;

    public Integer getInvestmentPeriod() {
        return InvestmentPeriod;
    }

    public void setInvestmentPeriod(Integer investmentPeriod) {
        InvestmentPeriod = investmentPeriod;
    }

    public Boolean getCancel() {
        return IsCancel;
    }

    public void setCancel(Boolean cancel) {
        IsCancel = cancel;
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public Boolean getTransfer() {
        return isTransfer;
    }

    public void setTransfer(Boolean transfer) {
        isTransfer = transfer;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Integer getBorrowingPeriod() {
        return borrowingPeriod;
    }

    public void setBorrowingPeriod(Integer borrowingPeriod) {
        this.borrowingPeriod = borrowingPeriod;
    }

    public PeriodUnit getBorrowingPeriodUnit() {
        return borrowingPeriodUnit;
    }

    public void setBorrowingPeriodUnit(PeriodUnit borrowingPeriodUnit) {
        this.borrowingPeriodUnit = borrowingPeriodUnit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
