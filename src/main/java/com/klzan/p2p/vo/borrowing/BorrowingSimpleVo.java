package com.klzan.p2p.vo.borrowing;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.BorrowingType;
import com.klzan.p2p.enums.PeriodUnit;
import com.klzan.p2p.enums.RepaymentMethod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by suhao Date: 2016/12/22 Time: 21:18
 *
 * @version: 1.0
 */
public class BorrowingSimpleVo implements Serializable {
    private Integer borrowingId;
    private String title;
    private BigDecimal interestRate;
    private String interestRateStr;
    private BigDecimal residualAmount;
    private String residualAmountStr;
    private Integer period;
    private String periodUnit;
    private String periodUnitStr;
    private String progress;
    private String progressDes;
    private Boolean allowInvest;
    private String progressDesReal;
    private Boolean oldProject;
    private BorrowingType borrowingType;
    private RepaymentMethod repaymentMethod;
    private String repaymentMethodStr;
    private BigDecimal amount;
    private BigDecimal percent;
    private Date investmentStartDate;
    private long heraldTime;
    private Map detail;

    public Map getDetail() {
        return detail;
    }

    public void setDetail(Map detail) {
        this.detail = detail;
    }

    public long getHeraldTime() {
        return heraldTime;
    }

    public void setHeraldTime(long heraldTime) {
        this.heraldTime = heraldTime;
    }

    public Date getInvestmentStartDate() {
        return investmentStartDate;
    }

    public void setInvestmentStartDate(Date investmentStartDate) {
        this.investmentStartDate = investmentStartDate;
    }

    public BigDecimal getPercent() {
        return residualAmount.divide(amount,6,BigDecimal.ROUND_HALF_UP).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public String getRepaymentMethodStr() {
        if (repaymentMethod!=null){
            return repaymentMethod.getDisplayName();
        }
        return null;
    }

    public void setRepaymentMethodStr(String repaymentMethodStr) {
        this.repaymentMethodStr = repaymentMethodStr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public RepaymentMethod getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(RepaymentMethod repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public BorrowingType getBorrowingType() {
        return borrowingType;
    }

    public void setBorrowingType(BorrowingType borrowingType) {
        this.borrowingType = borrowingType;
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

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getResidualAmount() {
        return residualAmount;
    }

    public void setResidualAmount(BigDecimal residualAmount) {
        this.residualAmount = residualAmount;
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

    public String getInterestRateStr() {
        return new DecimalFormat(",###.##").format(interestRate);
    }

    public String getResidualAmountStr() {
        return new DecimalFormat(",###.##万元").format(residualAmount.divide(new BigDecimal("10000"), RoundingMode.HALF_EVEN));
    }

    public String getPeriodUnitStr() {
        return period + PeriodUnit.valueOf(periodUnit).getDisplayName();
    }

    public void setInterestRateStr(String interestRateStr) {
        this.interestRateStr = interestRateStr;
    }

    public void setResidualAmountStr(String residualAmountStr) {
        this.residualAmountStr = residualAmountStr;
    }

    public void setPeriodUnitStr(String periodUnitStr) {
        this.periodUnitStr = periodUnitStr;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getProgressDes() {
        if (StringUtils.isNotBlank(progress)) {
            return BorrowingProgress.valueOf(progress).getDisplayName();
        }
        return progressDes;
    }

    public void setProgressDes(String progressDes) {
        this.progressDes = progressDes;
    }

    public Boolean getAllowInvest() {
        return allowInvest;
    }

    public void setAllowInvest(Boolean allowInvest) {
        this.allowInvest = allowInvest;
    }

    public String getProgressDesReal() {
        return progressDesReal;
    }

    public void setProgressDesReal(String progressDesReal) {
        this.progressDesReal = progressDesReal;
    }

    public Boolean getOldProject() {
        return oldProject;
    }

    public void setOldProject(Boolean oldProject) {
        this.oldProject = oldProject;
    }
}
