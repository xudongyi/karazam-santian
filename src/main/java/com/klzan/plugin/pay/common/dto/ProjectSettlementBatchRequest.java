package com.klzan.plugin.pay.common.dto;

import com.klzan.p2p.model.CpcnSettlement;
import com.klzan.p2p.model.PaymentOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 项目批量结算
 * Created by suhao Date: 2017/11/22 Time: 14:51
 *
 * @version: 1.0
 */
public class ProjectSettlementBatchRequest extends Request {

    private CpcnSettlement settlement;

    private List<PaymentOrder> orders;

    public CpcnSettlement getSettlement() {
        return settlement;
    }

    public void setSettlement(CpcnSettlement settlement) {
        this.settlement = settlement;
    }

    public List<PaymentOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<PaymentOrder> orders) {
        this.orders = orders;
    }

    //    /**
//     * 充值金额
//     */
//    private BigDecimal amount;
//
//    public ProjectSettlementBatchRequest(Boolean isMobile, BigDecimal amount) {
//        setMobile(isMobile);
//        this.amount = amount;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
}
