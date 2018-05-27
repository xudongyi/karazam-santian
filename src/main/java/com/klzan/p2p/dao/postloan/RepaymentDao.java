/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.postloan;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.model.Repayment;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 还款
 * @author: chenxinglin
 */
@Repository
public class RepaymentDao extends DaoSupport<Repayment> {
    /**
     * 还款分页列表
     * @param criteria
     * @return
     */
    public PageResult<Repayment> findPage(PageCriteria criteria, RepaymentState state, Date payDate, Boolean overdue) {
        StringBuffer hql = new StringBuffer("From Repayment r WHERE r.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(state != null){
            hql.append(" AND r.state = :state");
            params.put("state" , state);
        }
        if(payDate != null){
            java.sql.Date payDateStart = new java.sql.Date(DateUtils.getZeroDate(payDate).getTime());
            java.sql.Date payDateEnd = new java.sql.Date(DateUtils.getZeroDate(DateUtils.addDays(payDate,1)).getTime());
            hql.append(" AND r.payDate > :payDateStart AND r.payDate < :payDateEnd ");
            params.put("payDateStart", payDateStart);
            params.put("payDateEnd", payDateEnd);
        }
        if(overdue!=null && overdue){
            if(state == null){
                hql.append(" AND r.payDate < :todayStart AND r.state = :state ");
                params.put("todayStart", new java.sql.Date(DateUtils.getZeroDate(new Date()).getTime()));
                params.put("state" , RepaymentState.REPAYING);
            }else if(state.equals(RepaymentState.REPAYING)){
                hql.append(" AND r.payDate < :todayStart ");
                params.put("todayStart", new java.sql.Date(DateUtils.getZeroDate(new Date()).getTime()));
            }
        }
        if(criteria.getSort()==null){
            criteria.setSortName("payDate");
        }
        return this.findPage(hql.toString(), criteria, criteria.getParams(), params);
    }

    /**
     * 还款列表
     * @return
     */
    public List<Repayment> findList(RepaymentState state, Date payDate, Boolean overdue) {
        StringBuffer hql = new StringBuffer("From Repayment r WHERE r.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(state != null){
            hql.append(" AND r.state = :state");
            params.put("state" , state);
        }
        if(payDate != null){
            java.sql.Date payDateStart = new java.sql.Date(DateUtils.getZeroDate(payDate).getTime());
            java.sql.Date payDateEnd = new java.sql.Date(DateUtils.getZeroDate(DateUtils.addDays(payDate,1)).getTime());
            hql.append(" AND r.payDate > :payDateStart AND r.payDate < :payDateEnd ");
            params.put("payDateStart", payDateStart);
            params.put("payDateEnd", payDateEnd);
        }
        if(overdue!=null && overdue){
            if(state == null){
                hql.append(" AND r.payDate < :todayStart AND r.state = :state ");
                params.put("todayStart", new java.sql.Date(DateUtils.getZeroDate(new Date()).getTime()));
                params.put("state" , RepaymentState.REPAYING);
            }else if(state.equals(RepaymentState.REPAYING)){
                hql.append(" AND r.payDate < :todayStart ");
                params.put("todayStart", new java.sql.Date(DateUtils.getZeroDate(new Date()).getTime()));
            }
        }
        hql.append(" ORDER BY r.payDate ASC ");
        return this.find(hql.toString(), params);
    }

    /**
     * 还款列表
     * @param borrowingId 借款ID
     * @return
     */
    public List<Repayment> findList(Integer borrowingId) {
        StringBuffer hql = new StringBuffer("From Repayment r WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND r.borrowing = :borrowing");
            params.put("borrowing", borrowingId);
        }
        return this.find(hql.toString(), params);
    }

    /**
     * 还款列表
     * @return
     */
    public List<Repayment> findList(Date startDate, Date endDate) {
        if(startDate == null || endDate == null){
            throw new RuntimeException("参数错误");
        }
        StringBuffer hql = new StringBuffer("From Repayment r WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        hql.append(" AND r.payDate > :startDate  AND r.payDate < :endDate ");
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        return this.find(hql.toString(), params);
    }

    /**
     * 还款还款
     * @param borrowingId 借款ID
     * @param period
     * @return
     */
    public Repayment findByPeriod(Integer borrowingId, Integer period) {
        if(borrowingId == null || period == null){
            throw new RuntimeException("参数错误");
        }
        StringBuffer hql = new StringBuffer("From Repayment r WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND r.borrowing = :borrowing");
            params.put("borrowing", borrowingId);
        }
        if(borrowingId != null){
            hql.append(" AND r.period = :period");
            params.put("period", period);
        }
        List<Repayment> repayments = this.find(hql.toString(), params);
        if(repayments!=null && repayments.size()==1){
            return repayments.get(0);
        }
        return null;
    }

    public Repayment findByOrderNo(String orderNo) {
        StringBuffer hql = new StringBuffer("From Repayment c WHERE c.deleted = 0 AND c.orderNo=?0");
        return findUnique(hql.toString(), orderNo);
    }

    public Repayment getCurrentRepayment(Integer borrowingId) {
        StringBuffer hql = new StringBuffer("From Repayment r WHERE r.deleted = 0 AND r.state='repaying' ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND r.borrowing = :borrowing");
            params.put("borrowing", borrowingId);
        }
        hql.append(" ORDER BY r.period ASC ");
        List<Repayment> repayments = this.find(hql.toString(), params);
        if(repayments!=null && repayments.size()>0){
            return repayments.get(0);
        }
        return null;
    }

//    public Repayment findEnd(Integer borrowingId) {
//        StringBuffer hql = new StringBuffer("From Repayment c WHERE c.deleted = 0 AND c.borrowing=?0");
//        return findUnique(hql.toString(), borrowingId);
//    }
    /**
     * 累计待还
     * @return
     */
    public BigDecimal countWaitPay() {
        String hql="select sum(capital)+sum(interest)+sum(overdue_interest)+sum(serious_overdue_interest)+sum(repayment_fee) from karazam_repayment where deleted = 0 and state='repaying' ";
        return this.findUniqueBySQL(hql);
    }

    /**
     * 下期还款日
     * @return
     */
    public Date getNextPayDate(Integer borrowingId) {
        String sql="SELECT r.pay_date FROM karazam_borrowing b, karazam_repayment r where b.deleted = 0 and r.deleted = 0 and r.borrowing = b.id and b.id = ?0 and r.state = 'repaying' ORDER BY r.period asc LIMIT 1 ";
        return this.findUniqueBySQL(sql, borrowingId);
    }

    /**
     * 最后一期还款日(计划)
     * @return
     */
    public Date getFinalPayDate(Integer borrowingId) {
        String sql="SELECT r.pay_date FROM karazam_borrowing b, karazam_repayment r where b.deleted = 0 and r.deleted = 0 and r.borrowing = b.id and b.id = ?0  ORDER BY r.period desc LIMIT 1 ";
        return this.findUniqueBySQL(sql, borrowingId);
    }

    /**
     * 存在提前还款
     * @return
     */
    public Boolean hasAhead(Integer borrowingId) {
        String sql="SELECT count(1) FROM karazam_borrowing b, karazam_repayment r where b.deleted = 0 and r.deleted = 0 and r.borrowing = b.id and b.id = ?0 and r.advance = 1 ORDER BY r.period asc LIMIT 1";
        BigInteger count = this.findUniqueBySQL(sql, borrowingId);
        return count!=null && count.intValue() > 0;
    }

    public List<Repayment> findByUser(Integer userId) {
        StringBuffer hql = new StringBuffer("From Repayment r WHERE deleted = 0 ");
        hql.append(" AND r.borrower = :borrower ");
        Map<String, Object> params = new HashMap();
        params.put("borrower", userId);
        return this.find(hql.toString(), params);
    }
}