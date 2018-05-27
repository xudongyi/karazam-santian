package com.klzan.p2p.model;

import com.klzan.core.util.DateUtils;
import com.klzan.core.util.SnowflakeUtils;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户自动投标排行信息
 * Created by suhao Date: 2017/5/26 Time: 11:40
 *
 * @version: 1.0
 */
@Entity
@Table(name = "karazam_user_auto_investment_rank")
public class UserAutoInvestmentRank extends BaseModel implements Comparable<UserAutoInvestmentRank> {
    /**
     * 关联用户
     */
    @Column(nullable = false)
    private Integer userId;
    /**
     * 开启状态 true-开启 false-关闭
     */
    private Boolean openStatus = Boolean.TRUE;
    /**
     * 投资序号
     */
    @Column(nullable = false, length = 20)
    private Long investNo;
    /**
     * 有效期
     */
    private Integer validity;
    /**
     * 过期时间
     */
    private Date expire;
    /**
     * 已投资金额
     */
    private BigDecimal investedAmount = BigDecimal.ZERO;
    /**
     * 剩余可投资金额
     */
    private BigDecimal residualAmount = BigDecimal.ZERO;
    /**
     * 投标限额最小值
     */
    private BigDecimal investMinAmount;
    /**
     * 投标限额最大值
     */
    private BigDecimal investMaxAmount;
    /**
     * 标的周期最小值
     */
    private Integer projectMinCyc;
    /**
     * 标的周期最大值
     */
    private Integer projectMaxCyc;
    /**
     * 利率最小值
     */
    private BigDecimal interestRateMinRate;
    /**
     * 利率最大值
     */
    private BigDecimal interestRateMaxRate;
    /**
     * 上次投资时间
     */
    private Date prevInvestDate;

    /**
     * 手机号
     */
    @Transient
    private String mobile;
    /**
     * 真实姓名
     */
    @Transient
    private String realName;
    @Transient
    private Integer rank;
    @Transient
    private Integer effectiveSign;
    @Transient
    private String rankDes;

    public UserAutoInvestmentRank() {
    }

    public UserAutoInvestmentRank(Integer userId, Integer validity, BigDecimal investMinAmount, BigDecimal investMaxAmount, Integer projectMinCyc, Integer projectMaxCyc, BigDecimal interestRateMinRate, BigDecimal interestRateMaxRate) {
        this.userId = userId;
        this.validity = validity;
        this.investMinAmount = investMinAmount;
        this.investMaxAmount = investMaxAmount;
        this.projectMinCyc = projectMinCyc;
        this.projectMaxCyc = projectMaxCyc;
        this.interestRateMinRate = interestRateMinRate;
        this.interestRateMaxRate = interestRateMaxRate;

        this.expire = DateUtils.addDays(new Date(), validity);
        this.openStatus = Boolean.TRUE;
        this.investNo = SnowflakeUtils.getNextNo();
        this.residualAmount = investMaxAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public Boolean getOpenStatus() {
        return openStatus;
    }

    public Long getInvestNo() {
        return investNo;
    }

    public Integer getValidity() {
        return validity;
    }

    public Date getExpire() {
        return expire;
    }

    public BigDecimal getInvestedAmount() {
        return investedAmount;
    }

    public BigDecimal getResidualAmount() {
        return residualAmount;
    }

    public BigDecimal getInvestMinAmount() {
        return investMinAmount;
    }

    public BigDecimal getInvestMaxAmount() {
        return investMaxAmount;
    }

    public Integer getProjectMinCyc() {
        return projectMinCyc;
    }

    public Integer getProjectMaxCyc() {
        return projectMaxCyc;
    }

    public BigDecimal getInterestRateMinRate() {
        return interestRateMinRate;
    }

    public BigDecimal getInterestRateMaxRate() {
        return interestRateMaxRate;
    }

    public Date getPrevInvestDate() {
        return prevInvestDate;
    }

    public String getMobile() {
        return mobile;
    }

    public String getRealName() {
        return realName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getEffectiveSign() {
        return effectiveSign;
    }

    public void setEffectiveSign(Integer effectiveSign) {
        this.effectiveSign = effectiveSign;
    }

    public String getRankDes() {
        return rankDes;
    }

    public void setRankDes(String rankDes) {
        this.rankDes = rankDes;
    }

    public void updateFullInvest() {
        this.investNo = SnowflakeUtils.getNextNo();
        this.residualAmount = getInvestMaxAmount();
        this.investedAmount = BigDecimal.ZERO;
        this.prevInvestDate = new Date();
    }

    public void updatePartInvest(BigDecimal investedAmount) {
        this.investNo = SnowflakeUtils.getNextNo();
        this.residualAmount = getResidualAmount().subtract(investedAmount);
        this.investedAmount = investedAmount;
        this.prevInvestDate = new Date();
    }

    public void updateReSign(Integer validity, BigDecimal investMinAmount, BigDecimal investMaxAmount, Integer projectMinCyc, Integer projectMaxCyc, BigDecimal interestRateMinRate, BigDecimal interestRateMaxRate) {
        this.validity = validity;
        this.investMinAmount = investMinAmount;
        this.investMaxAmount = investMaxAmount;
        this.projectMinCyc = projectMinCyc;
        this.projectMaxCyc = projectMaxCyc;
        this.interestRateMinRate = interestRateMinRate;
        this.interestRateMaxRate = interestRateMaxRate;

        this.expire = DateUtils.addDays(new Date(), validity);
        this.openStatus = Boolean.TRUE;
        this.investNo = SnowflakeUtils.getNextNo();
        this.residualAmount = investMaxAmount;
    }

    public void updateOpenStatus(Boolean openStatus) {
        this.openStatus = openStatus;
    }

    @Override
    public int compareTo(UserAutoInvestmentRank o) {
        if (this.investNo.compareTo(o.getInvestNo()) > 0) {
            return 1;
        }
        return 0;
    }
}
