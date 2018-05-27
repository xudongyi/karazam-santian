package com.klzan.p2p.vo.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.0
 * Created by suhao Date: 2017/4/23 Time: 11:28
 *
 * @version:
 */
public class UserMonthProfitVo implements Serializable {
    /**
     * 本月到期应收
     */
    private BigDecimal recoverAmount = BigDecimal.ZERO;
    /**
     * 本月未收
     */
    private BigDecimal unRecoverAmount = BigDecimal.ZERO;
    /**
     * 详情
     */
    private List<UserMonthDetailProfitVo> detailProfits = new ArrayList<>();

    public BigDecimal getRecoverAmount() {
        return recoverAmount;
    }

    public BigDecimal getUnRecoverAmount() {
        return unRecoverAmount;
    }

    public List<UserMonthDetailProfitVo> getDetailProfits() {
        return detailProfits;
    }

    public void addRecoverAmount(BigDecimal amount) {
        this.recoverAmount = this.recoverAmount.add(amount);
    }

    public void addUnRecoverAmount(BigDecimal amount) {
        this.unRecoverAmount = unRecoverAmount.add(amount);
    }
    public void addDetailProfits(UserMonthDetailProfitVo detailProfit) {
        this.detailProfits.add(detailProfit);
    }
}
