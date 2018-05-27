package com.klzan.p2p.vo.user;

import java.math.BigDecimal;

/**
 * Created by suhao Date: 2017/5/26 Time: 16:35
 *
 * @version: 1.0
 */
public class UserAutoInvestVo extends AbstractUserMeta {
    /**
     * 是否签约
     */
    private Boolean autoInvestSign;
    /**
     * 授权号
     */
    private String authNo;
    /**
     * 商户订单号
     */
    private String merBillNo;
    /**
     * 环迅订单号
     */
    private String ipsBillNo;
    /**
     * 有效期
     */
    private Integer validity;
    /**
     * 标的周期最小值
     */
    private Integer projectMinCyc;
    /**
     * 标的周期最大值
     */
    private Integer projectMaxCyc;
    /**
     * 投标限额最小值
     */
    private BigDecimal investMinAmount;
    /**
     * 投标限额最大值
     */
    private BigDecimal investMaxAmount;
    /**
     * 利率最小值
     */
    private BigDecimal interestRateMinRate;
    /**
     * 利率最大值
     */
    private BigDecimal interestRateMaxRate;
    /**
     * 签约是否成功
     */
    private Boolean status;

    // 签约平台内自定信息
    /**
     * 自动投票金额
     */
    private BigDecimal investmentAomunt;

    public Boolean getAutoInvestSign() {
        return autoInvestSign;
    }

    public void setAutoInvestSign(Boolean autoInvestSign) {
        this.autoInvestSign = autoInvestSign;
    }

    public String getAuthNo() {
        return authNo;
    }

    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }

    public String getMerBillNo() {
        return merBillNo;
    }

    public void setMerBillNo(String merBillNo) {
        this.merBillNo = merBillNo;
    }

    public String getIpsBillNo() {
        return ipsBillNo;
    }

    public void setIpsBillNo(String ipsBillNo) {
        this.ipsBillNo = ipsBillNo;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public Integer getProjectMinCyc() {
        return projectMinCyc;
    }

    public void setProjectMinCyc(Integer projectMinCyc) {
        this.projectMinCyc = projectMinCyc;
    }

    public Integer getProjectMaxCyc() {
        return projectMaxCyc;
    }

    public void setProjectMaxCyc(Integer projectMaxCyc) {
        this.projectMaxCyc = projectMaxCyc;
    }

    public BigDecimal getInvestMinAmount() {
        return investMinAmount;
    }

    public void setInvestMinAmount(BigDecimal investMinAmount) {
        this.investMinAmount = investMinAmount;
    }

    public BigDecimal getInvestMaxAmount() {
        return investMaxAmount;
    }

    public void setInvestMaxAmount(BigDecimal investMaxAmount) {
        this.investMaxAmount = investMaxAmount;
    }

    public BigDecimal getInterestRateMinRate() {
        return interestRateMinRate;
    }

    public void setInterestRateMinRate(BigDecimal interestRateMinRate) {
        this.interestRateMinRate = interestRateMinRate;
    }

    public BigDecimal getInterestRateMaxRate() {
        return interestRateMaxRate;
    }

    public void setInterestRateMaxRate(BigDecimal interestRateMaxRate) {
        this.interestRateMaxRate = interestRateMaxRate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BigDecimal getInvestmentAomunt() {
        return investmentAomunt;
    }

    public void setInvestmentAomunt(BigDecimal investmentAomunt) {
        this.investmentAomunt = investmentAomunt;
    }

    public boolean singInfoEquals(UserAutoInvestVo obj) {
        return null != obj && validity.equals(obj.getValidity())
                && projectMinCyc.compareTo(obj.getProjectMinCyc()) == 0
                && projectMaxCyc.compareTo(obj.getProjectMaxCyc()) == 0
                && investMinAmount.compareTo(obj.getInvestMinAmount()) == 0
                && investMaxAmount.compareTo(obj.getInvestMaxAmount()) == 0
                && interestRateMinRate.compareTo(obj.getInterestRateMinRate()) == 0
                && interestRateMaxRate.compareTo(obj.getInterestRateMaxRate()) == 0
                ;
    }

}
