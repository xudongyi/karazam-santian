/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.postloan;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.model.RepaymentPlan;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * 还款计划
 * @author: chenxinglin
 */
@Repository
public class RepaymentPlanDao extends DaoSupport<RepaymentPlan> {

    /**
     * 列表（有效）
     * @param borrowingId 借款ID
     * @return
     */
    public List<RepaymentPlan> findList(Integer borrowingId) {
        StringBuffer hql = new StringBuffer("From RepaymentPlan r WHERE r.deleted = 0 and r.transferState <> 'TRANSFER_OUT' ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND r.borrowing = :borrowing ");
            params.put("borrowing", borrowingId);
        }
        hql.append("ORDER BY repaymentRecord.period ASC ");
        return this.find(hql.toString(), params);
    }

    /**
     * 转让列表（有效）
     * @param borrowingId 借款ID
     * @return
     */
    public List<RepaymentPlan> findList(Integer borrowingId, Integer transferId) {
        StringBuffer hql = new StringBuffer("From RepaymentPlan r WHERE r.deleted = 0 and r.transferState <> 'TRANSFER_OUT' ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND r.borrowing = :borrowing ");
            params.put("borrowing", borrowingId);
        }
        if(transferId != null){
            hql.append(" AND r.transfer = :transfer ");
            params.put("transfer", transferId);
        }
        hql.append("ORDER BY repaymentRecord.period ASC ");
        return this.find(hql.toString(), params);
    }

    /**
     * 列表 (有效)
     * @param borrowingId 借款ID
     * @return
     */
    public List<RepaymentPlan> findList(Integer borrowingId, Integer investor, Integer investmentId) {
        StringBuffer hql = new StringBuffer("From RepaymentPlan r WHERE r.deleted = 0 and r.transferState <> 'TRANSFER_OUT' ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND r.borrowing = :borrowing");
            params.put("borrowing", borrowingId);
        }
        if(investor != null){
            hql.append(" AND r.investor = :investor");
            params.put("investor", investor);
        }
        if(investmentId != null){
            hql.append(" AND r.investment = :investmentId");
            params.put("investmentId", investmentId);
        }
        hql.append(" order by repaymentRecord.payDate asc ");
        return this.find(hql.toString(), params);
    }

    /**
     * 列表 (可转让)
     * @param borrowingId 借款ID
     * @return
     */
    public List<RepaymentPlan> findListCanTransfer(Integer borrowingId, Integer userId) {
        StringBuffer hql = new StringBuffer("From RepaymentPlan r WHERE r.deleted = 0 and r.transferState <> 'TRANSFER_OUT' and r.transferState <> 'TRANSFER_IN' ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND r.borrowing = :borrowing");
            params.put("borrowing", borrowingId);
        }
        if(userId != null){
            hql.append(" AND r.investor = :userId");
            params.put("userId", userId);
        }
        return this.find(hql.toString(), params);
    }

    /**
     * 列表
     * @param investmentId
     * @return
     */
    public List<RepaymentPlan> findListByInvestment(Integer investmentId) {
        StringBuffer hql = new StringBuffer("From RepaymentPlan r WHERE r.deleted = 0 and r.transferState <> 'TRANSFER_OUT' ");
        Map<String, Object> params = new HashMap();
        if(investmentId != null){
            hql.append(" AND r.investment = :investmentId");
            params.put("investmentId" , investmentId);
        }
        return this.find(hql.toString(), params);
    }

    /**
     * 列表
     * @param repaymentId 还款ID
     * @return
     */
    public List<RepaymentPlan> findListByRepayment(Integer repaymentId) {
        StringBuffer hql = new StringBuffer("From RepaymentPlan r WHERE deleted = 0 and r.transferState <> 'TRANSFER_OUT' ");
        Map<String, Object> params = new HashMap();
        if(repaymentId != null){
            hql.append(" AND r.repayment = :repaymentId");
            params.put("repaymentId", repaymentId);
        }
        return this.find(hql.toString(), params);
    }
    /**
     * 列表
     * @param repaymentId 还款ID
     * @return
     */
    public List<RepaymentPlan> findListByRepayment(Integer repaymentId,RepaymentState repaymentState) {
        StringBuffer hql = new StringBuffer("From RepaymentPlan r WHERE deleted = 0 and r.transferState <> 'TRANSFER_OUT'");
        Map<String, Object> params = new HashMap();
        if(repaymentId != null){
            hql.append(" AND r.repayment = :repaymentId");
            params.put("repaymentId", repaymentId);
        }
        if(repaymentState != null){
            hql.append("  AND r.state = :repaymentState");
            params.put("repaymentState", repaymentState);
        }
        return this.find(hql.toString(), params);
    }
    /**
     * 列表
     * @param borrowingId 借款ID
     * @return
     */
    public Integer countRepaying(Integer borrowingId) {
        StringBuffer sql = new StringBuffer("select count(1) from karazam_repayment_plan r WHERE deleted = 0 and r.transfer_state <> 'TRANSFER_OUT' and state = 'REPAYING' and r.borrowing = :borrowing");
        Map<String, Object> params = new HashMap();
        params.put("borrowing", borrowingId);
        return this.countWithSQL(sql.toString(),params);
    }

    /**
     * 平台累计为投资者带来收益
     * @return
     */
    public BigDecimal countProfit() {
        String hql="select sum(interest)+sum(paid_Overdue_Interest)+sum(paid_Serious_Overdue_Interest) from karazam_repayment_plan where deleted = 0 and transfer_state <> 'TRANSFER_OUT' and state='REPAID' ";
        return this.findUniqueBySQL(hql);
    }
    /**
     * 平台即将为投资者带来收益
     * @return
     */
    public BigDecimal countWillProfit() {
        String hql="select sum(interest) from karazam_repayment_plan where deleted = 0 and transfer_state <> 'TRANSFER_OUT' and state='REPAYING' ";
        return this.findUniqueBySQL(hql);
    }
    /**
     * 累计已还本金
     * @return
     */
    public BigDecimal countPaidCapital() {
        String hql="select sum(Paid_Capital) from karazam_repayment_plan where deleted = 0 and transfer_state <> 'TRANSFER_OUT' and state='REPAID' ";
        return this.findUniqueBySQL(hql);
    }

    public List<RepaymentPlan> waitingProfit(Integer userId, Integer days) {
        Date begin = DateUtils.getZeroDate();
        Date end = DateUtils.getMaxDateOfDay(DateUtils.addDays(begin, days - 1));
        String hql = "FROM RepaymentPlan WHERE deleted = 0 and transferState <> 'TRANSFER_OUT' and state = ?0 AND investor=?1 AND repaymentRecord.payDate >= ?2 AND repaymentRecord.payDate<=?3 ";
        return this.find(hql, RepaymentState.REPAYING, userId, begin, end);
    }

    public List<RepaymentPlan> countYesterdayWaitingProfit(Integer userId) {
      //  昨天未支付的还款计划;
        Date end = DateUtils.getMaxDateOfDay(DateUtils.addDays(new Date(),-1));
        String hql="FROM RepaymentPlan WHERE deleted = 0 and transferState <> 'TRANSFER_OUT' and (state = ?0 or state = ?1) AND repaymentRecord.payDate >= ?2  AND investor=?3 ";
        return this.find(hql, RepaymentState.REPAYING, RepaymentState.REPAID, end, userId);
    }

    public List<RepaymentPlan> waitingProfit(Integer userId) {
        String hql = "FROM RepaymentPlan WHERE deleted = 0 and transferState <> 'TRANSFER_OUT' and state = ?0 AND investor=?1 ";
        return this.find(hql, RepaymentState.REPAYING, userId);
    }

    public List<RepaymentPlan> alreadyProfit(Integer userId) {
        String hql = "FROM RepaymentPlan WHERE deleted = 0 and transferState <> 'TRANSFER_OUT' and state = ?0 AND investor=?1 ";
        return this.find(hql, RepaymentState.REPAID, userId);
    }
    public RepaymentPlan findByOrderNo(String orderNo) {
        StringBuffer hql = new StringBuffer("From RepaymentPlan c WHERE c.deleted = 0 and c.transferState <> 'TRANSFER_OUT' AND c.orderNo=?0");
        return findUnique(hql.toString(), orderNo);
    }

    /**
     * 累计已还本金
     * @return
     */
    public BigDecimal countPlanIncome(Integer borrowingId, Integer investorId) {
        String hql="SELECT SUM(r.capital+r.interest) FROM karazam_repayment_plan r WHERE r.deleted = 0 AND r.transfer_state <> 'TRANSFER_OUT' AND r.borrowing = ?0  AND r.investor = ?1 ";
        return (BigDecimal)this.findUniqueBySQL(hql,borrowingId ,investorId);
    }

    public List<RepaymentPlan> findRecoveries(Integer userId, String yearMonth) {
        StringBuffer hql = new StringBuffer("From RepaymentPlan r WHERE deleted = 0 and r.transferState <> 'TRANSFER_OUT'");
        Map<String, Object> params = new HashMap();
        if(userId != null){
            hql.append(" AND r.investor = :investor");
            params.put("investor", userId);
        }
        if(StringUtils.isNotBlank(yearMonth)){
            hql.append(" AND ( date_format(r.repaymentRecord.payDate, '%Y-%m') = :yearMonth1 OR date_format(r.paidDate, '%Y-%m') = :yearMonth2 )");
            params.put("yearMonth1", yearMonth);
            params.put("yearMonth2", yearMonth);
        }
        return this.find(hql.toString(), params);
    }

    public List<RepaymentPlan> findRecoveries(Integer userId, RepaymentState state) {
        StringBuffer hql = new StringBuffer("From RepaymentPlan r WHERE deleted = 0 and r.transferState <> 'TRANSFER_OUT'");
        Map<String, Object> params = new HashMap();
        if(userId != null){
            hql.append(" AND r.investor = :investor");
            params.put("investor", userId);
        }
        if(null != state){
            hql.append(" AND r.state = :state");
            params.put("state", state);
        }
        return this.find(hql.toString(), params);
    }

    public PageResult<RepaymentPlan> findRecoveries(Integer userId, RepaymentState state, PageCriteria criteria) {
        StringBuffer hql = new StringBuffer("From RepaymentPlan r WHERE deleted = 0 and r.transferState <> 'TRANSFER_OUT'");
        Map<String, Object> params = new HashMap();
        if(userId != null){
            hql.append(" AND r.investor = :investor");
            params.put("investor", userId);
        }
        if(null != state){
            hql.append(" AND r.state = :state");
            params.put("state", state);
        }
        return this.findPage(hql.toString(), criteria, criteria.getParams(), params);
    }
    public List<RepaymentPlan> findListByTime(Integer userId, Date starDate,Date endDate){
        StringBuffer hql=new StringBuffer("From RepaymentPlan r WHERE deleted = 0 and r.transferState <> 'TRANSFER_OUT'");
        Map<String,Object> params=new HashMap<>();
        if(userId !=null){
            hql.append(" AND r.investor = :investor");
            params.put("investor", userId);
        }

        if (starDate!=null&&endDate!=null){
            hql.append(" AND (( r.state='REPAYING'");
            hql.append(" AND r.repaymentRecord.payDate >= :starDate");
            hql.append(" AND r.repaymentRecord.payDate <= :endDate)");
            hql.append("  OR ( r.state='REPAID'");
            hql.append(" AND r.paidDate >= :starDate");
            hql.append(" AND r.paidDate <= :endDate))");
            params.put("starDate",starDate);
            params.put("endDate",endDate);
        }
        return this.find(hql.toString(), params);
    }
}