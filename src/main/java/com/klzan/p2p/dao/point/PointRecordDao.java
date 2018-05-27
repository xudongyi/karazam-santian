/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.point;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.PointMethod;
import com.klzan.p2p.enums.PointType;
import com.klzan.p2p.model.PointRecord;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分记录
 * @author: chenxinglin
 */
@Repository
public class PointRecordDao extends DaoSupport<PointRecord> {

    public PageResult<PointRecord> findPage(PageCriteria criteria, Integer userId, PointType type, Integer month) {
        StringBuffer hql = new StringBuffer("From PointRecord pr WHERE pr.deleted = 0 ");
        Map param = new HashMap();
        if (null != userId) {
            hql.append("AND pr.userId=:userId ");
            param.put("userId", userId);
        }
        if (null != type) {
            hql.append("AND pr.type=:type ");
            param.put("type", type);
        }
        if (null != month && month.intValue()>0) {
            Date date = DateUtils.addMonths(new Date(), -month.intValue());
            hql.append("AND pr.createDate > :date ");
            param.put("date", date);
        }
        criteria.setSort("pr.createDate");
        criteria.setOrder("desc");
        return this.findPage(hql.toString(), criteria, criteria.getParams(), param);
    }

    public List<PointRecord> findListByUserId(Integer userId) {
        StringBuffer hql = new StringBuffer("From PointRecord pr WHERE pr.deleted = 0  AND pr.userId = ?0 ");
        return find(hql.toString(), userId);
    }

    public List<PointRecord> findList(Integer userId, PointMethod method) {
        StringBuffer hql = new StringBuffer("From PointRecord pr WHERE pr.deleted = 0 ");
        Map param = new HashMap();
        if (null != userId) {
            hql.append("AND pr.userId=:userId ");
            param.put("userId", userId);
        }
        if (null != method) {
            hql.append("AND pr.method=:method ");
            param.put("method", method);
        }
        hql.append(" ORDER BY pr.createDate DESC");
        return find(hql.toString(), param);
    }

    public List<PointRecord> findList(Integer userId, PointMethod method, Date startDate, Date endDate) {
        StringBuffer hql = new StringBuffer("From PointRecord pr WHERE pr.deleted = 0 ");
        Map param = new HashMap();
        if (null != userId) {
            hql.append("AND pr.userId=:userId ");
            param.put("userId", userId);
        }
        if (null != method) {
            hql.append("AND pr.method=:method ");
            param.put("method", method);
        }
        if (null != startDate) {
            hql.append("AND pr.createDate>:startDate ");
            param.put("startDate", startDate);
        }
        if (null != endDate) {
            hql.append("AND pr.createDate<:endDate ");
            param.put("endDate", endDate);
        }
        hql.append(" ORDER BY pr.createDate DESC");
        return find(hql.toString(), param);
    }

//    public List<PointRecord> findListByGoods(Integer goodsId) {
//        StringBuffer hql = new StringBuffer("From PointRecord pr,GoodsOrder go WHERE pr.orderNo = go.orderNo AND pr.deleted = 0 AND go.deleted = 0 AND go.goods = ?0 ");
//        return find(hql.toString(), goodsId);
//    }

}
