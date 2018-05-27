/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.investment;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.vo.investment.InvestmentRecordVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 投资子记录
 * @author:
 */
@Repository
public class InvestmentRecordDao extends DaoSupport<InvestmentRecord> {

    /**
     * 分页查询投资记录
     * @param pageCriteria
     * @return
     */
    public PageResult<InvestmentRecordVo> findPageListPage(PageCriteria pageCriteria, Date startDate, Date endDate) {
        String sql = "select a.id as id,"
                + "a.operation_Method as operationMethodStr,"
                + "a.state as stateStr,"
                + "a.amount as amount,"
                + "a.preferential_Amount as preferentialAmount,"
                + "a.investor as investor,"
                + "b.id as borrowing ,"
                + "a.coupon_Code as couponCode,"
                + "a.investment as investment,"
                + "a.memo as memo,"
                + "a.operator as operator,"
                + "a.ip as ip,"
                + "a.is_Transfer as isTransfer,"
                + "a.is_Experience as isExperience,"
                + "a.order_No as orderNo,"
                + "b.title as title,"
                + "b.amount as borrowingAmount,"
                + "b.state as borrowingStateStr,"
                + "c.real_Name as realName, "
                + "a.create_date as createDate, "
                + "u.mobile as mobile, "
                + "a.device_type as deviceTypeStr "
                + "from karazam_investment_record a "
                + "left join karazam_borrowing b on a.borrowing = b.id "
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
        if (StringUtils.isNotBlank(pageCriteria.getSort())) {
            pageCriteria.setSort("a." + pageCriteria.getSort());
        } else {
            pageCriteria.setSort("a.id");
            pageCriteria.setOrder("desc");
        }
        return this.findPageBySQL(sql, pageCriteria, new ScalarAliasCallback<InvestmentRecordVo>() {
            @Override
            protected Class<InvestmentRecordVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("operationMethodStr", StandardBasicTypes.STRING);
                query.addScalar("stateStr", StandardBasicTypes.STRING);
                query.addScalar("amount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("preferentialAmount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("investor", StandardBasicTypes.INTEGER);
                query.addScalar("borrowing", StandardBasicTypes.INTEGER);
                query.addScalar("couponCode", StandardBasicTypes.INTEGER);
                query.addScalar("investment", StandardBasicTypes.INTEGER);
                query.addScalar("memo", StandardBasicTypes.STRING);
                query.addScalar("operator", StandardBasicTypes.STRING);
                query.addScalar("ip", StandardBasicTypes.STRING);
                query.addScalar("isTransfer", StandardBasicTypes.BOOLEAN);
                query.addScalar("isExperience", StandardBasicTypes.BOOLEAN);
                query.addScalar("orderNo", StandardBasicTypes.STRING);
                query.addScalar("title", StandardBasicTypes.STRING);
                query.addScalar("borrowingAmount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("borrowingStateStr", StandardBasicTypes.STRING);
                query.addScalar("realName", StandardBasicTypes.STRING);
                query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("mobile", StandardBasicTypes.STRING);
                query.addScalar("deviceTypeStr", StandardBasicTypes.STRING);
                return InvestmentRecordVo.class;
            }
        },pageCriteria.getParams(),params);
    }

    /**
     * 投资记录列表
     * @param borrowingId
     * @return
     */
    public List<InvestmentRecord> findList(Integer borrowingId) {
        StringBuffer hql = new StringBuffer("From InvestmentRecord i WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND i.borrowing = :borrowingId");
            params.put("borrowingId" , borrowingId);
        }
        hql.append(" ORDER BY i.createDate DESC ");
        return this.find(hql.toString(), params);
    }
    public List<InvestmentRecord> findListSuccess(Integer borrowingId) {
        StringBuffer hql = new StringBuffer("From InvestmentRecord i WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND i.borrowing = :borrowingId");
            params.put("borrowingId" , borrowingId);
            hql.append(" AND i.state <> 'FAILURE'  AND  i.state <> 'INVESTING' ");
        }
        hql.append(" ORDER BY i.createDate DESC ");
        return this.find(hql.toString(), params);
    }
    /**
     * 投资记录列表
     * @param userId
     * @return
     */
    public List<InvestmentRecord> findListByUserId(Integer userId) {
        StringBuffer hql = new StringBuffer("From InvestmentRecord i WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(userId != null){
            hql.append(" AND i.investor = :userId");
            params.put("userId" , userId);
        }
        hql.append(" ORDER BY i.createDate DESC ");
        return this.find(hql.toString(), params);
    }
    /**
     * 投资记录列表
     * @param investmentId
     * @return
     */
    public List<InvestmentRecord> findListByInvestment(Integer investmentId) {
        StringBuffer hql = new StringBuffer("From InvestmentRecord i WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(investmentId != null){
            hql.append(" AND i.investment = :investmentId");
            params.put("investmentId" , investmentId);
        }
        hql.append(" ORDER BY i.createDate DESC ");
        return this.find(hql.toString(), params);
    }

    /**
     * 投资记录列表
     * @param investmentId
     * @return
     */
    public List<InvestmentRecord> findListByInvestment(Integer investmentId, InvestmentState state) {
        StringBuffer hql = new StringBuffer("From InvestmentRecord i WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(investmentId != null){
            hql.append(" AND i.investment = :investmentId");
            params.put("investmentId" , investmentId);
        }
        if(state != null){
            hql.append(" AND i.state = :state");
            params.put("state" , state);
        }
        hql.append(" ORDER BY i.createDate DESC ");
        return this.find(hql.toString(), params);
    }

    /**
     * 投资记录
     * @param orderNo
     * @return
     */
    public InvestmentRecord findByOrderNo(String orderNo) {
        StringBuffer hql = new StringBuffer("From InvestmentRecord i WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(orderNo != null){
            hql.append(" AND i.orderNo = :orderNo");
            params.put("orderNo" , orderNo);
        }
        return this.findUnique(hql.toString(), params);
    }

    public List countInvest(String realName, String mobile, Date startDate, Date endDate) {
        String sql="select sum(i.amount),sum(b.amount) from karazam_investment_record i, karazam_user u, karazam_user_info ui, karazam_borrowing b where i.deleted=0 and i.investor=u.id and i.investor=ui.user_id and i.borrowing = b.id ";
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

    public InvestmentRecord getRecordByInvestment(Integer investmentId) {
        StringBuffer hql = new StringBuffer("From InvestmentRecord i WHERE deleted = 0 AND i.investment = ?0 ");
        return this.findUnique(hql.toString(), investmentId);
    }

    public List<InvestmentRecord> findList(Integer borrowingId, InvestmentState state) {
        StringBuffer hql = new StringBuffer("From InvestmentRecord i WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND i.borrowing = :borrowingId");
            params.put("borrowingId" , borrowingId);
        }
        if(state != null){
            hql.append(" AND i.state = :state");
            params.put("state" , state);
        }
        hql.append(" ORDER BY i.createDate DESC ");
        return this.find(hql.toString(), params);
    }

    public List<InvestmentRecord> findList(Integer borrowingId,Boolean isTransfer, InvestmentState... states) {
        StringBuffer hql = new StringBuffer("From InvestmentRecord i WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND i.borrowing = :borrowingId");
            params.put("borrowingId" , borrowingId);
        }
        if(isTransfer != null){
            hql.append(" AND i.isTransfer = :isTransfer");
            params.put("isTransfer" , isTransfer);
        }
        if(states != null && states.length > 0){
            hql.append(" AND ( ");
            int i = 0;
            for (InvestmentState state : states) {
                hql.append("i.state = :state" + i + " OR ");
                params.put("state" + i , state);
                i++;
            }
            hql = new StringBuffer(hql.substring(0, hql.length() - 3));
            hql.append(") ");
        }
        hql.append(" ORDER BY i.createDate DESC ");
        return this.find(hql.toString(), params);
    }

    public List<InvestmentRecord> findList(Integer borrowingId, Integer userId) {
        StringBuffer hql = new StringBuffer("From InvestmentRecord i WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(borrowingId != null){
            hql.append(" AND i.borrowing = :borrowingId");
            params.put("borrowingId" , borrowingId);
        }
        if(userId != null){
            hql.append(" AND i.investor = :userId");
            params.put("userId" , userId);
        }
        hql.append(" ORDER BY i.createDate DESC ");
        return this.find(hql.toString(), params);
    }

}