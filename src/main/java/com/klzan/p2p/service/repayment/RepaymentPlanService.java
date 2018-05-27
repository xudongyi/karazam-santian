/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.repayment;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.model.RepaymentPlan;
import com.klzan.p2p.vo.repayment.RepaymentPlanVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 还款计划
 * @author: chenxinglin  Date: 2016/11/1 Time: 17:07
 */
public interface RepaymentPlanService extends IBaseService<RepaymentPlan> {

    /**
     * 列表
     * @param borrowingId 借款ID
     * @return
     */
    List<RepaymentPlan> findList(Integer borrowingId);

    List<RepaymentPlan> findList(Integer borrowingId, Integer transferId);

    /**
     * 列表
     * @param borrowingId 借款ID
     * @return
     */
    List<RepaymentPlan> findList(Integer borrowingId, Integer userId, Integer investmentId);

    /**
     * 列表 (可转让)
     * @param borrowingId 借款ID
     * @return
     */
    List<RepaymentPlan> findListCanTransfer(Integer borrowingId, Integer userId);

    /**
     * 列表
     */
    List<RepaymentPlan> findListByInvestment(Integer investmentId);

    /**
     * 平台累计为投资者带来利息
     * @return
     */
    BigDecimal countProfit();

    /**
     * 今日收益
     * @return
     * @param userId
     */
    List<RepaymentPlan> countYesterdayWaitingProfit(Integer userId);

    /**
     * 平台即将为投资者带来的利息
     * @return
     */
    BigDecimal countWillProfit();

    /**
     * 查询用户多少天内的收益
     * @param userId
     * @param days
     * @return
     */
    List<RepaymentPlan> waitingProfit(Integer userId, Integer days);

    List<RepaymentPlan> waitingProfit(Integer userId);

    List<RepaymentPlan> alreadyProfit(Integer userId);

    RepaymentPlan findByOrderNo(String orderNo);
    /**
     * 累计已还本金
     * @return
     */
    BigDecimal countPaidCapital();

    /**
     * 昨日收益(仅包含正常利息部分，不计算逾期)
     * @return
     */
    BigDecimal calyesterdayIncome(Integer userId);
    /**
     * 每日收益(仅包含正常利息部分，不计算逾期)
     * @return
     */

    BigDecimal calTodayIncome(Integer userId);

    /**
     * 查询用户回收
     * @param userId
     * @param yearMonth
     * @return
     */
    List<RepaymentPlan> findRecoveries(Integer userId, String yearMonth);

    /**
     * 查询用户回收计划
     * @param userId
     * @param state
     * @return
     */
    List<RepaymentPlan> findRecoveries(Integer userId, RepaymentState state);

    /**
     * 查询回收分页
     * @param userId
     * @param state
     * @param criteria
     * @return
     */
    PageResult<RepaymentPlan> findRecoveries(Integer userId, RepaymentState state, PageCriteria criteria);

    /**
     * 查询回收分页
     * @param userId
     * @param state
     * @param criteria
     * @return
     */
    PageResult<RepaymentPlanVo> findPage(Integer userId, RepaymentState state, PageCriteria criteria);

    /**
     * 查询回收list
     * @param userId
     * @param state
     * @param criteria
     * @return
     */
    List<RepaymentPlanVo> findList(Integer userId, RepaymentState state, PageCriteria criteria);

    /**
     * 根据还款查询还款计划
     * @param repaymentId
     * @return
     */
    List<RepaymentPlan> findListByRepayment(Integer repaymentId);
    /**
     * 根据日期查询还款计划
     *
     */
    List<RepaymentPlan> findListByTime(Integer userId, Date starDate,Date endDate);
}
