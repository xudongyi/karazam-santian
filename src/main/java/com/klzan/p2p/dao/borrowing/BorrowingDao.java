/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.borrowing;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.Sort;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.Borrowing;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 借款
 * @author: chenxinglin
 */
@Repository
public class BorrowingDao extends DaoSupport<Borrowing> {


    /**
     * 借款列表
     * @param criteria
     * @param progress 进度
     * @return
     */
    public PageResult<Borrowing> findList(PageCriteria criteria, BorrowingProgress progress, Boolean isFailure) {
        StringBuffer hql = new StringBuffer("From Borrowing b WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(progress != null){
            hql.append(" AND b.progress = :progress");
            params.put("progress" , progress);
        }
        if(isFailure != null){
            if(isFailure){
                hql.append(" AND ( b.state = :failure or b.state = :expiry )");
            }else{
                hql.append(" AND b.state <> :failure and b.state <> :expiry");
            }
            params.put("failure" , BorrowingState.FAILURE);
            params.put("expiry" , BorrowingState.EXPIRY);
        }
        if(criteria.getSort()==null){
            criteria.setOrder("desc");
            criteria.setSortName("id");
        }
        return this.findPage(hql.toString(), criteria, criteria.getParams(), params);
    }

    /**
     * 借款列表
     * @param pageCriteria
     * @param progress 进度
     * @param borrowingType
     * @return
     */
    public PageResult<Borrowing> findList(PageCriteria pageCriteria, BorrowingProgress progress, PeriodScope scope, InterestRateScope rate, BorrowingType borrowingType, Boolean isRecommend) {
        StringBuffer hql = new StringBuffer("SELECT b ");
        hql.append(", (b.interestRate+b.rewardInterestRate) as realInterestRate ");
        hql.append(" , (CASE b.progress " +
                "        WHEN 'investing' THEN 1 " +
                "        WHEN 'lending' THEN 2 " +
                "        WHEN 'repaying' THEN 3 " +
                "        WHEN 'completed' THEN 4 " +
                "        ELSE 5 " +
                "        END) as progress ");
        hql.append(" From Borrowing b WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        //过滤调查和审批中的借款
        hql.append(" AND b.progress <> :progressA AND b.progress <> :progressB");
        params.put("progressA" , BorrowingProgress.INQUIRING);
        params.put("progressB" , BorrowingProgress.APPROVAL);
        if(progress != null){
            hql.append(" AND b.progress = :progress");
            params.put("progress" , progress);
        }
        if(borrowingType != null){
            hql.append(" AND b.type = :type");
            params.put("type" , borrowingType);
        }
        if(isRecommend != null && isRecommend){
            hql.append(" AND b.isRecommend = :isRecommend");
            params.put("isRecommend" , isRecommend);
        }
        if(scope != null){
            //periodUnit month day
            switch (scope){
                case BETWEEN_1MONTH_AND_3MONTH: {
                    hql.append(" AND ((b.period >= :periodS AND b.period <= :periodE AND periodUnit = :periodUnitA) OR (b.period >= :periodSS AND b.period <= :periodEE AND periodUnit = :periodUnitB)) ");
                    params.put("periodS" , 1);
                    params.put("periodE" , 3);
                    params.put("periodUnitA" , PeriodUnit.MONTH);
                    params.put("periodSS" , 0);
                    params.put("periodEE" , 90);
                    params.put("periodUnitB" , PeriodUnit.DAY);
                    break;
                }
                case BETWEEN_3MONTH_AND_6MONTH: {
                    hql.append(" AND ((b.period >= :periodS AND b.period < :periodE AND periodUnit = :periodUnitA) OR (b.period >= :periodSS AND b.period < :periodEE AND periodUnit = :periodUnitB)) ");
                    params.put("periodS" , 3);
                    params.put("periodE" , 6);
                    params.put("periodUnitA" , PeriodUnit.MONTH);
                    params.put("periodSS" , 90);
                    params.put("periodEE" , 180);
                    params.put("periodUnitB" , PeriodUnit.DAY);
                    break;
                }
                case BETWEEN_3MONTH_AND_12MONTH: {
                    hql.append(" AND ((b.period > :periodS AND b.period <= :periodE AND periodUnit = :periodUnitA) OR (b.period > :periodSS AND b.period <= :periodEE AND periodUnit = :periodUnitB)) ");
                    params.put("periodS" , 3);
                    params.put("periodE" , 12);
                    params.put("periodUnitA" , PeriodUnit.MONTH);
                    params.put("periodSS" , 90);
                    params.put("periodEE" , 360);
                    params.put("periodUnitB" , PeriodUnit.DAY);
                    break;
                }
                case BETWEEN_12MONTH_AND_24MONTH: {
                    hql.append(" AND ((b.period >= :periodS AND b.period < :periodE AND periodUnit = :periodUnitA) OR (b.period >= :periodSS AND b.period < :periodEE AND periodUnit = :periodUnitB)) ");
                    params.put("periodS" , 12);
                    params.put("periodE" , 24);
                    params.put("periodUnitA" , PeriodUnit.MONTH);
                    params.put("periodSS" , 360);
                    params.put("periodEE" , 720);
                    params.put("periodUnitB" , PeriodUnit.DAY);
                    break;
                }
                case OVER_12MONTH: {
                    hql.append(" AND ((b.period > :periodS AND periodUnit = :periodUnitA) OR (b.period > :periodSS AND periodUnit = :periodUnitB)) ");
                    params.put("periodS" , 12);
                    params.put("periodUnitA" , PeriodUnit.MONTH);
                    params.put("periodSS" , 360);
                    params.put("periodUnitB" , PeriodUnit.DAY);
                    break;
                }
                default:
                    break;
            }
        }
        if(rate != null){
            switch (rate){
                case BETWEEN_0_AND_5: {
                    hql.append(" AND (b.interestRate + b.rewardInterestRate) >= :rateS AND (b.interestRate + b.rewardInterestRate) < :rateE ");
                    params.put("rateS" , new BigDecimal(0));
                    params.put("rateE" , new BigDecimal(5));
                    break;
                }
                case BETWEEN_5_AND_10: {
                    hql.append(" AND (b.interestRate + b.rewardInterestRate) >= :rateS AND (b.interestRate + b.rewardInterestRate) < :rateE ");
                    params.put("rateS" , new BigDecimal(5));
                    params.put("rateE" , new BigDecimal(10));
                    break;
                }
                case BETWEEN_10_AND_15: {
                    hql.append(" AND (b.interestRate + b.rewardInterestRate) >= :rateS AND (b.interestRate + b.rewardInterestRate) < :rateE ");
                    params.put("rateS" , new BigDecimal(10));
                    params.put("rateE" , new BigDecimal(15));
                    break;
                }
                case BETWEEN_15_AND_20: {
                    hql.append(" AND (b.interestRate + b.rewardInterestRate) >= :rateS AND (b.interestRate + b.rewardInterestRate) < :rateE ");
                    params.put("rateS" , new BigDecimal(15));
                    params.put("rateE" , new BigDecimal(20));
                    break;
                }
                case BETWEEN_20_AND_24: {
                    hql.append(" AND (b.interestRate + b.rewardInterestRate) >= :rateS AND (b.interestRate + b.rewardInterestRate) < :rateE ");
                    params.put("rateS" , new BigDecimal(20));
                    params.put("rateE" , new BigDecimal(24));
                    break;
                }
                case OVER_24: {
                    hql.append(" AND (b.interestRate + b.rewardInterestRate) >= :rateS ");
                    params.put("rateS" , new BigDecimal(24));
                    break;
                }
                default:
                    break;
            }
        }
        //排除流标(结束时间)
        hql.append(" AND ( b.investmentEndDate is null OR b.investmentEndDate = '' OR b.investmentEndDate > :investmentEndDate ) ");
        params.put("investmentEndDate" , new Date());
        //过滤失败项目
        hql.append(" AND b.state <> :failure and b.state <> :expiry ");
        params.put("failure" , BorrowingState.FAILURE);
        params.put("expiry" , BorrowingState.EXPIRY);
//        pageCriteria.setSortName("progress");
//        pageCriteria.setOrder("asc");
        List<Sort> sorts = new ArrayList<>();
        if (StringUtils.isNotBlank(pageCriteria.getSort())) {
            sorts.add(new Sort(pageCriteria.getSort(),pageCriteria.getOrder()));
            pageCriteria.setSort("");
            pageCriteria.setOrder("");
        } else {
            sorts.add(new Sort("progress","asc"));
            sorts.add(new Sort("createDate","desc"));
        }
        pageCriteria.setSorts(sorts);
        return this.findPage(hql.toString(), pageCriteria, pageCriteria.getParams(), params);
    }

    /**
     * 借款列表
     * @param pageCriteria
     * @return
     */
    public PageResult<Borrowing> findList(PageCriteria pageCriteria,Integer borrower,String stateStr) {
        StringBuffer hql = new StringBuffer("From Borrowing b WHERE deleted = 0 and borrower=:borrower ");
        Map map = new HashMap();
        map.put("borrower",borrower);
        BorrowingProgress progress = null;
        if ("NOLEND".equals(stateStr)){
            hql.append("and progress!='REPAYING' and progress!='COMPLETED' ");
        }else {
            hql.append("and progress=:progress ");
            progress = BorrowingProgress.valueOf(stateStr);
            map.put("progress",progress);
        }
        pageCriteria.setSortName("createDate");
        pageCriteria.setOrder("desc");
        if (progress==null){
            return this.findPage(hql.toString(),pageCriteria,pageCriteria.getParams(),map);
        }
        return this.findPage(hql.toString(),pageCriteria,pageCriteria.getParams(),map);
    }

    public Integer findList(BorrowingType type){
        String sql = "select count(1) From karazam_borrowing a where a.progress!='inquiring' AND a.progress!='approval' AND a.deleted=0 AND a.type = ?0 ";
        Map<String,BorrowingType> map = new HashMap();
        map.put("type",type);
        return ((BigInteger)this.findUniqueBySQL(sql, type.name())).intValue();
    }

    public List<Borrowing> findByBorrowerId(Integer userId) {
        StringBuffer hql = new StringBuffer("From Borrowing b WHERE deleted = 0 AND borrower=?0 ");
        return find(hql.toString(), userId);
    }

    public BigInteger countTargetByType(String type){
        String sql = "select count(1) from borrowing where deleted=false and type=?0";
        return findUniqueBySQL(sql,type);
    }

    public Borrowing findByProjectNo(String projectNo) {
        StringBuffer hql = new StringBuffer("From Borrowing b WHERE deleted = 0 AND projectNo=?0 ");
        return findUnique(hql.toString(), projectNo);
    }

//    /**
//     * 分页查询债权
//     * @param pageCriteria
//     * @return
//     */
//    public PageResult<Borrowing> findPage(PageCriteria pageCriteria) {
//        String hql = "select DISTINCT b.id "
//                + " FROM Borrowing b ,RepaymentPlan r "
//                + " WHERE b.deleted = false and r.borrowing = b.id "
//                + " AND r.investor = 8 ";
//        pageCriteria.setSortName("b.id");
//        pageCriteria.setOrder("desc");
//        return this.findPage(hql, pageCriteria);
//    }
}