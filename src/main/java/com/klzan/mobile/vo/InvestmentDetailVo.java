package com.klzan.mobile.vo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/6/6 0006.
 */
public class InvestmentDetailVo {

    //原标期限
    private  Integer oldPeriod;
    //剩余期限
    private Integer surplusPeriod;
    //投资金额
    private BigDecimal amount;
    //预 计收益
    private BigDecimal expectfee;
    //投资期限
    private String period;
    //期限单位
    private  String periodUnit;
    //产品介绍
    private  String title;
    //购买日期
    private  long buyDate;
    //计息日期
    private  long interestDate;
    //到期日期
    private long maturityDate;
    //预期年化利率
    private String rate;
    //已获收益
    private  BigDecimal earnedIncome;
    //收益处理方式
    private String treatmentMode;

    public Integer getOldPeriod() {
        return oldPeriod;
    }

    public void setOldPeriod(Integer oldPeriod) {
        this.oldPeriod = oldPeriod;
    }

    public String getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(String periodUnit) {
        this.periodUnit = periodUnit;
    }

    public Integer getSurplusPeriod() {
        return surplusPeriod;
    }

    public void setSurplusPeriod(Integer surplusPeriod) {
        this.surplusPeriod = surplusPeriod;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getExpectfee() {
        return expectfee;
    }

    public void setExpectfee(BigDecimal expectfee) {
        this.expectfee = expectfee;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(long buyDate) {
        this.buyDate = buyDate;
    }

    public long getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(long interestDate) {
        this.interestDate = interestDate;
    }

    public long getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(long maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public BigDecimal getEarnedIncome() {
        return earnedIncome;
    }

    public void setEarnedIncome(BigDecimal earnedIncome) {
        this.earnedIncome = earnedIncome;
    }

    public String getTreatmentMode() {
        return treatmentMode;
    }

    public void setTreatmentMode(String treatmentMode) {
        this.treatmentMode = treatmentMode;
    }
}
