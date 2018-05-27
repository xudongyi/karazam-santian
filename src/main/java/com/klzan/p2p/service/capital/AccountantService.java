/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.capital;

import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.model.RepaymentPlan;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会计
 * @author: chenxinglin  Date: 2017-2-9
 */
public interface AccountantService {

    /**
     * 利息计算
     * @param amount
     * @param interestRate
     * @return
     */
    BigDecimal calInterest(BigDecimal amount, BigDecimal interestRate);

    /**
     * 服务费计算
     * 借款服务费、还款服务费、回收服务费、转入服务费、转出服务费、其他服务费
     * @param amount
     * @param feeRate
     * @return
     */
    BigDecimal calFee(BigDecimal amount, BigDecimal feeRate);

    /**
     * 逾期计算
     * 计算对象 ：逾期天数、逾期利息、严重逾期天数、严重逾期利息
     * @param repayments 还款/还款计划
     * @return
     */
    <T> List<T> calOverdue(List<T> repayments);

    /**
     * 逾期计算
     * 计算对象 ：逾期天数、逾期利息、严重逾期天数、严重逾期利息
     * @param repayment 还款
     * @return
     */
    Repayment calOverdue(Repayment repayment);

    /**
     * 逾期计算
     * 计算对象 ：逾期天数、逾期利息、严重逾期天数、严重逾期利息
     * @param repaymentPlan 还款计划
     * @return
     */
    RepaymentPlan calOverdue(RepaymentPlan repaymentPlan);

    /**
     * 提前还款计算
     * @param repayments 还款/还款计划
     * @return
     */
    <T> List<T> calAhead(List<T> repayments);

    /**
     * 提前还款计算
     * @param repayment 还款
     * @return
     */
    Repayment calAhead(Repayment repayment);

    /**
     * 提前还款计算
     * @param repaymentPlan 还款计划
     * @return
     */
    RepaymentPlan calAhead(RepaymentPlan repaymentPlan);

    /**
     * 当期剩余债权价值
     * @param repaymentPlan 还款计划
     * @return
     */
    RepaymentPlan calCurrentSurplusValue(RepaymentPlan repaymentPlan, Integer totalParts);

    /**
     * 当期剩余债权价值 * 份数比例
     * @param repaymentPlan 还款计划
     * @param parts 购买份数
     * @param totalParts 总份数
     * @return
     */
    RepaymentPlan calCurrentSurplusValue(RepaymentPlan repaymentPlan, Integer parts, Integer surplusParts, Integer totalParts);


}
