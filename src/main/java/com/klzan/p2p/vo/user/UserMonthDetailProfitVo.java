package com.klzan.p2p.vo.user;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 1.0
 * Created by suhao Date: 2017/4/23 Time: 11:28
 *
 * @version:
 */
public class UserMonthDetailProfitVo implements Serializable {
    /**
     * 日期
     */
    private Integer recoverDay = 1;
    /**
     * 应收金额
     */
    private BigDecimal recoverAmount = BigDecimal.ZERO;
    /**
     * 实收金额
     */
    private BigDecimal recoveredAmount = BigDecimal.ZERO;
    /**
     * 到期实收
     */
    private BigDecimal todayRecoveredAmount = BigDecimal.ZERO;
    /**
     * 今日提前还款
     */
    private BigDecimal todayAdvanceAmount = BigDecimal.ZERO;
    /**
     * 到期应收
     */
    private BigDecimal expireRecoverAmount = BigDecimal.ZERO;
    /**
     * 历史提前还款
     */
    private BigDecimal historyRecoveredAmount = BigDecimal.ZERO;

    public UserMonthDetailProfitVo(Integer recoverDay, BigDecimal recoverAmount, BigDecimal recoveredAmount, BigDecimal todayRecoveredAmount, BigDecimal todayAdvanceAmount, BigDecimal expireRecoverAmount, BigDecimal historyRecoveredAmount) {
        this.recoverDay = recoverDay;
        this.recoverAmount = recoverAmount;
        this.recoveredAmount = recoveredAmount;
        this.todayRecoveredAmount = todayRecoveredAmount;
        this.todayAdvanceAmount = todayAdvanceAmount;
        this.expireRecoverAmount = expireRecoverAmount;
        this.historyRecoveredAmount = historyRecoveredAmount;
    }

    public Integer getRecoverDay() {
        return recoverDay;
    }

    public BigDecimal getRecoverAmount() {
        return recoverAmount;
    }

    public BigDecimal getRecoveredAmount() {
        return recoveredAmount;
    }

    public BigDecimal getTodayRecoveredAmount() {
        return todayRecoveredAmount;
    }

    public BigDecimal getTodayAdvanceAmount() {
        return todayAdvanceAmount;
    }

    public BigDecimal getExpireRecoverAmount() {
        return expireRecoverAmount;
    }

    public BigDecimal getHistoryRecoveredAmount() {
        return historyRecoveredAmount;
    }

    public void updateDetail(BigDecimal recoverAmount, BigDecimal recoveredAmount, BigDecimal todayRecoveredAmount, BigDecimal todayAdvanceAmount, BigDecimal expireRecoverAmount, BigDecimal historyRecoveredAmount) {
        this.recoverAmount = this.recoverAmount.add(recoverAmount);
        this.recoveredAmount = this.recoveredAmount.add(recoveredAmount);
        this.todayRecoveredAmount = this.todayRecoveredAmount.add(todayRecoveredAmount);
        this.todayAdvanceAmount = this.todayAdvanceAmount.add(todayAdvanceAmount);
        this.expireRecoverAmount = this.expireRecoverAmount.add(expireRecoverAmount);
        this.historyRecoveredAmount = this.historyRecoveredAmount.add(historyRecoveredAmount);
    }
}
