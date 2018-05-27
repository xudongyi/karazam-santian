package com.klzan.p2p.vo.withdraw;

import com.klzan.p2p.common.vo.BaseVo;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 提现
 */
public class WithdrawalVo extends BaseVo {

    private Integer userId;

    /** 金额 */
    private BigDecimal amount;

    /**
     * 订单号
     */
    private String orderNo;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @NotNull
    @Min(0)
    @Digits(integer = 19, fraction = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}