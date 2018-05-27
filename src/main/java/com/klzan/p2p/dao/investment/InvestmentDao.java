/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.investment;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.BorrowingState;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.enums.MessagePushType;
import com.klzan.p2p.model.Investment;
import com.klzan.p2p.model.User;
import com.klzan.p2p.vo.investment.InvestmentVo;
import com.klzan.p2p.vo.investment.WaitingProfitInvestmentVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * 投资
 * @author: chenxinglin
 */
@Repository
public class InvestmentDao extends DaoSupport<Investment> {

    /**
     * 投资列表
     * @param pageCriteria
     * @return
     */
    public PageResult<Investment> findList(PageCriteria pageCriteria) {
        StringBuffer hql = new StringBuffer("From Investment i WHERE i.deleted = 0");
        return this.findPage(hql.toString(), pageCriteria, pageCriteria.getParams());
    }

    /**
     * 投资列表
     * @param borrowingId 借款ID
     * @return
     */
    public List<Investment> findList(Integer borrowingId) {

        StringBuffer hql = new StringBuffer("From Investment i WHERE i.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND i.borrowing = :borrowing ");
            params.put("borrowing", borrowingId);
        }
        return this.find(hql.toString(), params);
    }

    /**
     * 投资列表
     * @param transferId 借款ID
     * @return
     */
    public List<Investment> findListByTransfer(Integer transferId) {

        StringBuffer hql = new StringBuffer("From Investment i WHERE i.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(transferId != null){
            hql.append(" AND i.transfer = :transferId ");
            params.put("transferId", transferId);
        }
        return this.find(hql.toString(), params);
    }

    /**
     * 投资
     * @param borrowingId
     * @param userId
     * @param isTransfer
     * @param state
     * @return
     */
    public Investment find(Integer borrowingId, Integer userId, Boolean isTransfer, InvestmentState state) {
        if (borrowingId == null || userId == null) {
            throw new BusinessProcessException("参数错误");
        }

        StringBuffer hql = new StringBuffer("From  Investment i WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if (borrowingId != null) {
            hql.append(" AND i.borrowing = :borrowingId ");
            params.put("borrowingId", borrowingId);
        }
        if (userId != null) {
            hql.append(" AND i.investor = :investor ");
            params.put("investor", userId);
        }
        if (isTransfer != null) {
            if (isTransfer) {
                hql.append(" AND i.transfer != null ");
            } else {
                hql.append(" AND i.transfer = null ");
            }
        }
        if (state != null) {
            hql.append(" AND i.state = :state ");
            params.put("state", state);
        }
        return this.findUnique(hql.toString(), params);
    }
    /**
     * 投资
     * @param orderNo
     * @return
     */
    public Investment findByOrderNo(String orderNo) {

        Map<String, Object> params = new HashMap();
        params.put("orderNo", orderNo);
        StringBuffer hql = new StringBuffer("From  Investment i WHERE deleted = 0 AND i.orderNo = :orderNo");

        List<Investment> list = this.find(hql.toString(), params);
        return list!=null&&list.size()>0?list.get(0):null;
    }
    /**
     * 分页查询投资总记录
     * @param pageCriteria
     * @return
     */
    public PageResult<InvestmentVo> findPageListPage(PageCriteria pageCriteria, Boolean isTransfer, Date startDate, Date endDate) {
        String sql = "select a.id as id,"
                +"a.state as stateStr,"
                +"a.amount as amount,"
                +"a.preferential_Amount as preferentialAmount,"
                +"a.investor as investor,"
                +"a.is_Experience as isExperience,"
                +"a.borrowing as borrowing,"
                +"b.title as title,"
                +"b.amount as borrowingAmount,"
                +"b.state as borrowingStateStr,"
                +"c.real_name as realName, "
                +"b.interest_rate as interestRate, "
                +"b.interest_begin_date as interestBeginDate, "
                +"a.create_date as createDate, "
                +"b.lending_date as lendingDate, "
                +"b.progress      as borrowingProgressStr, "
                +"b.repayment_method  as repaymentMethodStr, "
                +"b.period        as borrowingPeriod, "
                +"b.period_unit   as borrowingPeriodUnitStr, "
                +"u.mobile as mobile, "
                +"b.reward_interest_rate as rewardInterestRate "
                + "from karazam_investment a left join karazam_borrowing b on a.borrowing = b.id "
                + "left join karazam_user_info c on a.investor=c.user_id "
                + "left join karazam_user u on a.investor=u.id "
                + "where a.deleted=false";
        Map<String, Object> params = new HashMap();
        if(isTransfer != null){
            if (isTransfer) {
                sql += " and a.transfer is not null ";
            } else {
                sql += " and a.transfer is null ";
            }
        }
        if(startDate != null){
            sql += " and a.create_date >= :startDate ";
            params.put("startDate", startDate);
        }
        if(endDate != null){
            sql += " and a.create_date <= :endDate ";
            params.put("endDate", endDate);
        }
        pageCriteria.setSortName("a.id");
        pageCriteria.setOrder("desc");
        return this.findPageBySQL(sql, pageCriteria, new ScalarAliasCallback<InvestmentVo>() {
            @Override
            protected Class<InvestmentVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("stateStr", StandardBasicTypes.STRING);
                query.addScalar("amount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("preferentialAmount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("investor", StandardBasicTypes.INTEGER);
                query.addScalar("isExperience", StandardBasicTypes.BOOLEAN);
                query.addScalar("borrowing", StandardBasicTypes.INTEGER);
                query.addScalar("title", StandardBasicTypes.STRING);
                query.addScalar("borrowingAmount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("borrowingStateStr", StandardBasicTypes.STRING);
                query.addScalar("realName", StandardBasicTypes.STRING);
                query.addScalar("interestRate", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("interestBeginDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("lendingDate",StandardBasicTypes.TIMESTAMP);
                query.addScalar("borrowingProgressStr",StandardBasicTypes.STRING);
                query.addScalar("repaymentMethodStr", StandardBasicTypes.STRING);
                query.addScalar("borrowingPeriod", StandardBasicTypes.INTEGER);
                query.addScalar("borrowingPeriodUnitStr", StandardBasicTypes.STRING);
                query.addScalar("mobile", StandardBasicTypes.STRING);
                query.addScalar("rewardInterestRate", StandardBasicTypes.BIG_DECIMAL);
                return InvestmentVo.class;
            }
        },pageCriteria.getParams(), params);
    }

    public PageResult<InvestmentVo> findPageListPageMobile(PageCriteria pageCriteria, Boolean isTransfer, Date startDate, Date endDate,BorrowingProgress ... progresses) {
        String sql = "select a.id as id,"
                +"a.state as stateStr,"
                +"a.amount as amount,"
                +"a.preferential_Amount as preferentialAmount,"
                +"a.investor as investor,"
                +"a.is_Experience as isExperience,"
                +"a.borrowing as borrowing,"
                +"b.title as title,"
                +"b.amount as borrowingAmount,"
                +"b.state as borrowingStateStr,"
                +"c.real_name as realName, "
                +"b.interest_rate as interestRate, "
                +"b.interest_begin_date as interestBeginDate, "
                +"a.create_date as createDate, "
                +"b.lending_date as lendingDate, "
                +"b.progress      as borrowingProgressStr, "
                +"b.repayment_method  as repaymentMethodStr, "
                +"b.period        as borrowingPeriod, "
                +"b.period_unit   as borrowingPeriodUnitStr, "
                +"u.mobile as mobile, "
                +"a.transfer as transfer, "
                +"b.reward_interest_rate as rewardInterestRate "
                + "from karazam_investment a left join karazam_borrowing b on a.borrowing = b.id "
                + "left join karazam_user_info c on a.investor=c.user_id "
                + "left join karazam_user u on a.investor=u.id "
                + "where a.deleted=false";
        Map<String, Object> params = new HashMap();

        if(startDate != null){
            sql += " and a.create_date >= :startDate ";
            params.put("startDate", startDate);
        }
        if(endDate != null){
            sql += " and a.create_date <= :endDate ";
            params.put("endDate", endDate);
        }
        if(progresses!=null&&progresses.length!=0){
            sql +=" and ( ";
            boolean sss = false ;
            for (BorrowingProgress  progress: progresses) {
                if(sss){
                    sql +=" OR ";
                }
                sss = true ;
                sql +=" b.progress =  '" + progress.name()+"'";
            }
            sql +=" ) ";
        }
        pageCriteria.setSortName("a.id");
        pageCriteria.setOrder("desc");
        return this.findPageBySQL(sql, pageCriteria, new ScalarAliasCallback<InvestmentVo>() {
            @Override
            protected Class<InvestmentVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("stateStr", StandardBasicTypes.STRING);
                query.addScalar("amount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("preferentialAmount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("investor", StandardBasicTypes.INTEGER);
                query.addScalar("isExperience", StandardBasicTypes.BOOLEAN);
                query.addScalar("borrowing", StandardBasicTypes.INTEGER);
                query.addScalar("title", StandardBasicTypes.STRING);
                query.addScalar("borrowingAmount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("borrowingStateStr", StandardBasicTypes.STRING);
                query.addScalar("realName", StandardBasicTypes.STRING);
                query.addScalar("interestRate", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("interestBeginDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("lendingDate",StandardBasicTypes.TIMESTAMP);
                query.addScalar("borrowingProgressStr",StandardBasicTypes.STRING);
                query.addScalar("repaymentMethodStr", StandardBasicTypes.STRING);
                query.addScalar("borrowingPeriod", StandardBasicTypes.INTEGER);
                query.addScalar("borrowingPeriodUnitStr", StandardBasicTypes.STRING);
                query.addScalar("mobile", StandardBasicTypes.STRING);
                query.addScalar("rewardInterestRate", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("transfer", StandardBasicTypes.INTEGER);
                return InvestmentVo.class;
            }
        },pageCriteria.getParams(), params);
    }

    public List countInvest(String realName, String mobile, Date startDate, Date endDate) { //and (i.state='success' or i.state='investing')
        String sql="select sum(i.amount),sum(b.amount) from karazam_investment i, karazam_user u, karazam_user_info ui, karazam_borrowing b where i.deleted=0 and i.investor=u.id and i.investor=ui.user_id and i.borrowing = b.id ";
        Map<String, Object> params = new HashMap();
        if(StringUtils.isNotBlank(realName)){
            sql += " and ui.real_name like '%:realName%' ";
            params.put("realName", realName);
        }
        if(StringUtils.isNotBlank(mobile)){
            sql += " and u.mobile like '%:mobile%' ";
            params.put("mobile", mobile);
        }
        if(startDate != null){
            sql += " and i.create_date >= :startDate ";
            params.put("startDate", startDate);
        }
        if(endDate != null){
            sql += " and i.create_date <= :endDate ";
            params.put("endDate", endDate);
        }
        return this.findBySQL(sql, params);
    }

    public BigDecimal countInvest() {
        String hql="select sum(amount) from karazam_investment where state='success' ";
        return this.findUniqueBySQL(hql);
    }

    public PageResult<InvestmentVo> findByUserId(Integer userId, PageCriteria criteria) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append("a.id            as id, ");
        sql.append("a.state         as stateStr, ");
        sql.append("a.amount        as amount, ");
        sql.append("a.preferential_amount as preferentialAmount, ");
        sql.append("a.investor      as investor, ");
        sql.append("a.is_experience as isExperience, ");
        sql.append("b.interest_rate + ifnull(b.reward_interest_rate, 0) as interestRate, ");
        sql.append("a.borrowing     as borrowing, ");
        sql.append("b.borrower      as borrower, ");
        sql.append("b.period        as borrowingPeriod, ");
        sql.append("b.period_unit   as borrowingPeriodUnitStr, ");
        sql.append("u.name          as borrowerName, ");
        sql.append("b.title         as title, ");
        sql.append("b.amount        as borrowingAmount, ");
        sql.append("b.state         as borrowingStateStr, ");
        sql.append("b.type          as borrowingTypeStr, ");
        sql.append("b.progress      as borrowingProgressStr, ");
        sql.append("c.real_name     as realName ");
        sql.append("from karazam_investment a ");
        sql.append("left join       karazam_borrowing b on a.borrowing = b.id ");
        sql.append("left join       karazam_user_info c on a.investor=c.user_id ");
        sql.append("left join       karazam_user u      on u.id=b.borrower ");
        sql.append("where           a.deleted=false ");
        sql.append("and             a.investor=?0 ");
        criteria.setOrder("desc");
        criteria.setSortName("a.create_date");
        return findPageBySQL(sql.toString(), criteria, new ScalarAliasCallback<InvestmentVo>() {
            @Override
            protected Class<InvestmentVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("stateStr", StandardBasicTypes.STRING);
                query.addScalar("amount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("preferentialAmount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("investor", StandardBasicTypes.INTEGER);
                query.addScalar("isExperience", StandardBasicTypes.BOOLEAN);
                query.addScalar("interestRate", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("borrowing", StandardBasicTypes.INTEGER);
                query.addScalar("title", StandardBasicTypes.STRING);
                query.addScalar("borrowingAmount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("borrowingStateStr", StandardBasicTypes.STRING);
                query.addScalar("borrowingTypeStr", StandardBasicTypes.STRING);
                query.addScalar("borrowingProgressStr", StandardBasicTypes.STRING);
                query.addScalar("realName", StandardBasicTypes.STRING);
                query.addScalar("borrower", StandardBasicTypes.INTEGER);
                query.addScalar("borrowerName", StandardBasicTypes.STRING);
                query.addScalar("borrowingPeriod", StandardBasicTypes.INTEGER);
                query.addScalar("borrowingPeriodUnitStr", StandardBasicTypes.STRING);
                return InvestmentVo.class;
            }
        }, userId);
    }

    public List<WaitingProfitInvestmentVo> findWaitingProfitInvestByUserId(Integer userId, Integer listSize) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append("rp.id               as id, ");
        sql.append("rp.borrowing        as borrowingId, ");
        sql.append("rp.investment       as investmentId, ");
        sql.append("i.amount            as investAmount, ");
        sql.append("b.period            as borrowingPeriod, ");
        sql.append("b.period_unit       as borrowingPeriodUnitStr, ");
        sql.append("b.interest_rate + ifnull(b.reward_interest_rate, 0)    as interestRate, ");
        sql.append("b.repayment_method  as repaymentMethodStr, ");
        sql.append("rp.capital+rp.interest+rp.overdue_interest+rp.serious_overdue_interest  as waitingCapitalProfit, ");
        sql.append("rp.recovery_fee     as recoveryFee, ");
        sql.append("rp.period           as recoveryPeriod, ");
        sql.append("rp.create_date      as investmentDate, ");
        sql.append("rp.pay_date         as recoveryDate ");
        sql.append("from karazam_repayment_plan rp ");
        sql.append("left join karazam_investment i on i.id=rp.investment ");
        sql.append("left join karazam_borrowing b on b.id=rp.borrowing ");
        sql.append("where rp.investor=?0 ");
        sql.append("and rp.state='repaying' ");
        sql.append("order by rp.pay_date asc ");
        if (null != listSize || listSize != 0) {
            sql.append("limit 0, " + listSize );
        }
        return this.findBySQL(sql.toString(), new ScalarAliasCallback<WaitingProfitInvestmentVo>() {
            @Override
            protected Class<WaitingProfitInvestmentVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("borrowingId", StandardBasicTypes.INTEGER);
                query.addScalar("investmentId", StandardBasicTypes.INTEGER);
                query.addScalar("investAmount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("borrowingPeriod", StandardBasicTypes.INTEGER);
                query.addScalar("borrowingPeriodUnitStr", StandardBasicTypes.STRING);
                query.addScalar("interestRate", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("repaymentMethodStr", StandardBasicTypes.STRING);
                query.addScalar("waitingCapitalProfit", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("recoveryFee", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("recoveryPeriod", StandardBasicTypes.INTEGER);
                query.addScalar("investmentDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("recoveryDate", StandardBasicTypes.TIMESTAMP);
                return WaitingProfitInvestmentVo.class;
            }
        }, userId);
    }

    public List<Investment> findListByUserId(Integer userId) {
        String hql = "FROM Investment WHERE deleted=0 AND state=?0 AND investor=?1 ";
        return this.find(hql, InvestmentState.SUCCESS, userId);
    }

    public List<Investment> findInvestingByUserId(Integer userId) {
        String hql = "FROM Investment WHERE deleted=0 AND state=?0 AND investor=?1 ";
        return this.find(hql, InvestmentState.INVESTING, userId);
    }

    /**
     * 投资分页列表
     * @param pageCriteria
     * @param currentUser
     * @return
     */
    public PageResult<Investment> findPage(PageCriteria pageCriteria, User currentUser, BorrowingProgress progress) {
        StringBuffer hql = new StringBuffer("SELECT i From Investment i,Borrowing b WHERE i.deleted = 0 and b.deleted = 0 and i.borrowing = b.id ");
        Map<String, Object> params = new HashMap();
        if(currentUser != null){
            hql.append(" AND i.investor = :investor ");
            params.put("investor" , currentUser.getId());
        }
        if(progress != null){
            if (progress.equals(BorrowingProgress.INVESTING)){
                hql.append(" AND (b.progress = :progress2 OR b.progress = :progress)");
                params.put("progress2" , BorrowingProgress.LENDING);
            }else{
                hql.append(" AND b.progress = :progress ");
            }
            params.put("progress" , progress);
        }
        hql.append(" AND b.state <> :failure and b.state <> :expiry");
        params.put("failure" , BorrowingState.FAILURE);
        params.put("expiry" , BorrowingState.EXPIRY);
        hql.append(" ORDER BY i.createDate desc ");
        return this.findPage(hql.toString(), pageCriteria, pageCriteria.getParams(), params);
    }

    public Integer countInvestmentDeadline(Integer begin,Integer end,String uint){
        StringBuffer sql = new StringBuffer("select count(1) from karazam_investment a left join karazam_borrowing b on a.borrowing=b.id " +
                "where a.state='success' and a.deleted=false and b.deleted=false and b.period >= :begin and b.period_unit=:uint ");
        Map map = new HashMap();
        map.put("begin",begin);
        if (end!=null){
            sql.append("AND b.period<:end");
            map.put("end",end);
        }
        map.put("uint",uint);

        return countWithSQL(sql.toString(),map);
    }

    public Integer countInvestmentAmount(BigDecimal begin,BigDecimal end){
        StringBuffer sql = new StringBuffer("select count(1) from karazam_investment a " +
                "where a.state='success' and a.deleted=false and a.amount >= :begin ");
        Map map = new HashMap();
        map.put("begin",begin);
        if (end!=null){
            sql.append("AND a.amount<:end");
            map.put("end",end);
        }
        return countWithSQL(sql.toString(),map);
    }

    public List<Investment> findList(Integer borrowingId, InvestmentState state) {
        StringBuffer hql = new StringBuffer("From Investment i WHERE i.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND i.borrowing = :borrowing ");
            params.put("borrowing", borrowingId);
        }
        if(state != null){
            hql.append(" AND i.state = :state ");
            params.put("state", state);
        }
        return this.find(hql.toString(), params);
    }

    public Investment find(Integer borrowingId, Integer investor, InvestmentState state) {
        StringBuffer hql = new StringBuffer("From Investment i WHERE i.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND i.borrowing = :borrowing ");
            params.put("borrowing", borrowingId);
        }
        if(investor != null){
            hql.append(" AND i.investor = :investor ");
            params.put("investor", investor);
        }
        if(state != null){
            hql.append(" AND i.state = :state ");
            params.put("state", state);
        }
        return this.findUnique(hql.toString(), params);
    }
}