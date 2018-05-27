package com.klzan.plugin.pay.common.dto;

import java.math.BigDecimal;

/**
 * 项目结算
 * Created by suhao Date: 2017/11/22 Time: 14:51
 *
 * @version: 1.0
 */
public class ProjectSettlementRequest extends Request {

    /**
     * 充值金额
     */
    private BigDecimal amount;

    public ProjectSettlementRequest(Boolean isMobile, BigDecimal amount) {
        setMobile(isMobile);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
