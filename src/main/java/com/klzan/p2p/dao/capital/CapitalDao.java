/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.capital;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.CapitalMethod;
import com.klzan.p2p.enums.CapitalType;
import com.klzan.p2p.model.Capital;
import com.klzan.p2p.vo.capital.CapitalVo;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by suhao Date: 2016/11/17 Time: 19:07
 *
 * @version: 1.0
 */
@Repository
public class CapitalDao extends DaoSupport<Capital> {

    /**
     * 资金列表
     * @param pageCriteria
     * @param type
     * @param method
     * @return
     */
    public PageResult<Capital> findList(PageCriteria pageCriteria, CapitalType type, CapitalMethod method) {
        StringBuffer hql = new StringBuffer("From Capital c WHERE c.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(type != null && !"".equals(type.toString().trim())){
            hql.append(" AND c.type = '" + type.toString() + "'");
        }
        if(method != null && !"".equals(method.toString().trim())){
            hql.append(" AND c.method = '" + method.toString() + "'");
        }
        pageCriteria.setSortName("id");
        pageCriteria.setOrder("desc");
        return this.findPage(hql.toString(), pageCriteria, pageCriteria.getParams(), params);
    }

    public PageResult<CapitalVo> findPage(PageCriteria criteria,String createDateBegin,String createDateEnd) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append("c.id        id, ");
        sql.append("c.user_id   userId, ");
        sql.append("u.name      name, ");
        sql.append("c.type      typeStr, ");
        sql.append("c.method    methodStr, ");
        sql.append("c.create_date         createDate, ");
        sql.append("ifnull(c.credit,0)    credit, ");
        sql.append("ifnull(c.debit,0)     debit, ");
        sql.append("ifnull(c.frozen,0)    frozen, ");
        sql.append("ifnull(c.unfrozen,0)  unfrozen, ");
        sql.append("ifnull(c.balance,0)   balance, ");
        sql.append("c.order_no  orderNo, ");
        sql.append("c.operator  operator, ");
        sql.append("c.memo      memo, ");
        sql.append("c.ip        ip ");
        sql.append("from karazam_capital c ");
        sql.append("left join karazam_user u on u.id = c.user_id ");
        sql.append("where c.deleted=false ");
        Map<String, Object> params = new HashedMap();
        if(StringUtils.isNotBlank(createDateBegin)){
            createDateBegin = createDateBegin + " 00:00:00";
            sql.append("and c.create_date >= :createDateBegin ");
            params.put("createDateBegin", createDateBegin);
        }
        if(StringUtils.isNotBlank(createDateEnd)){
            createDateEnd = createDateEnd + " 23:59:59";
            sql.append("and c.create_date <= :createDateEnd");
            params.put("createDateEnd", createDateEnd);
        }
        return this.findPageBySQL(sql.toString(), criteria, new ScalarAliasCallback<CapitalVo>() {
            @Override
            protected Class<CapitalVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("userId", StandardBasicTypes.INTEGER);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("typeStr", StandardBasicTypes.STRING);
                query.addScalar("methodStr", StandardBasicTypes.STRING);
                query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("credit", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("debit", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("frozen", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("unfrozen", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("balance", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("orderNo", StandardBasicTypes.STRING);
                query.addScalar("operator", StandardBasicTypes.STRING);
                query.addScalar("memo", StandardBasicTypes.STRING);
                query.addScalar("ip", StandardBasicTypes.STRING);
                return CapitalVo.class;
            }
        }, criteria.getParams(), params);
    }

    public PageResult<CapitalVo> findPage(PageCriteria criteria, Integer userId) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append("c.id        id, ");
        sql.append("c.user_id   userId, ");
        sql.append("u.name      name, ");
        sql.append("c.type      typeStr, ");
        sql.append("c.method    methodStr, ");
        sql.append("ifnull(c.credit,0)    credit, ");
        sql.append("ifnull(c.debit,0)     debit, ");
        sql.append("ifnull(c.frozen,0)    frozen, ");
        sql.append("ifnull(c.unfrozen,0)  unfrozen, ");
        sql.append("ifnull(c.credits,0)   credits, ");
        sql.append("ifnull(c.debits,0)    debits, ");
        sql.append("ifnull(c.frozens,0)   frozens, ");
        sql.append("ifnull(c.balance,0)   balance, ");
        sql.append("c.order_no  orderNo, ");
        sql.append("c.operator  operator, ");
        sql.append("c.memo      memo, ");
        sql.append("c.ip        ip ");
        sql.append("from capital c ");
        sql.append("left join user u on u.id = c.user_id ");
        sql.append("where c.deleted=false ");
        Map<String, Object> params = new HashedMap();
        if (userId!=null) {
            sql.append(" and c.user_Id = :userId ");
            params.put("userId", userId);
        }
        return this.findPageBySQL(sql.toString(), criteria, new ScalarAliasCallback<CapitalVo>() {
            @Override
            protected Class<CapitalVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("userId", StandardBasicTypes.INTEGER);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("typeStr", StandardBasicTypes.STRING);
                query.addScalar("methodStr", StandardBasicTypes.STRING);
                query.addScalar("credit", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("debit", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("frozen", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("unfrozen", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("credits", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("debits", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("frozens", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("balance", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("orderNo", StandardBasicTypes.STRING);
                query.addScalar("operator", StandardBasicTypes.STRING);
                query.addScalar("memo", StandardBasicTypes.STRING);
                query.addScalar("ip", StandardBasicTypes.STRING);
                return CapitalVo.class;
            }
        }, params);
    }

    public Capital findByOrderNo(String orderNo) {
        StringBuffer hql = new StringBuffer("From Capital c WHERE c.deleted = 0 AND orderNo=?0");
        return findUnique(hql.toString(), orderNo);
    }
}
