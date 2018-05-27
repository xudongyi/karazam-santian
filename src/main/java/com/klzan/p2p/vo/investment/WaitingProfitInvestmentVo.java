package com.klzan.p2p.vo.investment;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.PeriodUnit;
import com.klzan.p2p.enums.RepaymentMethod;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 待收益投资记录
 * Created by suhao Date: 2016/12/7 Time: 11:29
 *
 * @version: 1.0
 */
public class WaitingProfitInvestmentVo extends BaseVo {
    /**
     * 借款ID
     */
    private Integer borrowingId;
    /**
     * 投资ID
     */
    private Integer investmentId;
    /**
     * 投资金额
     */
    private BigDecimal investAmount;
    /**
     * 期限
     */
    private Integer borrowingPeriod;

    /**
     * 期限单位
     */
    private PeriodUnit borrowingPeriodUnit;
    private String borrowingPeriodUnitStr;
    /**
     * 借款利率
     */
    private BigDecimal interestRate;
    /**
     * 还款方式
     */
    private RepaymentMethod repaymentMethod;
    private String repaymentMethodStr;
    /**
     * 待收本息
     */
    private BigDecimal waitingCapitalProfit;
    /**
     * 待收服务费
     */
    private BigDecimal recoveryFee;
    /**
     * 期数
     */
    private Integer recoveryPeriod;
    /**
     * 投资时间
     */
    private Date investmentDate;
    /**
     * 回收时间
     */
    private Date recoveryDate;

    public Integer getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(Integer borrowingId) {
        this.borrowingId = borrowingId;
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
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

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
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

    public BigDecimal getWaitingCapitalProfit() {
        return waitingCapitalProfit;
    }

    public void setWaitingCapitalProfit(BigDecimal waitingCapitalProfit) {
        this.waitingCapitalProfit = waitingCapitalProfit;
    }

    public BigDecimal getRecoveryFee() {
        return recoveryFee;
    }

    public void setRecoveryFee(BigDecimal recoveryFee) {
        this.recoveryFee = recoveryFee;
    }

    public Integer getRecoveryPeriod() {
        return recoveryPeriod;
    }

    public void setRecoveryPeriod(Integer recoveryPeriod) {
        this.recoveryPeriod = recoveryPeriod;
    }

    public Date getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(Date investmentDate) {
        this.investmentDate = investmentDate;
    }

    public Date getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(Date recoveryDate) {
        this.recoveryDate = recoveryDate;
    }
}
