/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.goods;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.GoodsOrderStatus;
import com.klzan.p2p.enums.GoodsType;
import com.klzan.p2p.model.Goods;
import com.klzan.p2p.model.GoodsOrder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品订单
 * @author: chenxinglin
 */
@Repository
public class GoodsOrderDao extends DaoSupport<GoodsOrder> {

    /**
     * 分页列表
     * @param criteria
     * @return
     */
    public PageResult<GoodsOrder> findPage(PageCriteria criteria) {
        StringBuffer hql = new StringBuffer("From GoodsOrder go WHERE go.deleted = 0 ");
        criteria.setSort("go.createDate");
        criteria.setOrder("desc");
        return this.findPage(hql.toString(), criteria, criteria.getParams());
    }

    /**
     * 分页列表
     * @param criteria
     * @return
     */
    public PageResult<GoodsOrder> findPage(PageCriteria criteria, Integer userId, GoodsType type, GoodsOrderStatus status, Integer month) {
        StringBuffer hql = new StringBuffer("SELECT go From GoodsOrder go,Goods g WHERE go.deleted = 0 AND g.deleted = 0 AND g.id = go.goods ");
        Map param = new HashMap();
        if (null != userId) {
            hql.append("AND go.userId=:userId ");
            param.put("userId", userId);
        }
        if (null != type) {
            hql.append("AND g.type=:type ");
            param.put("type", type);
        }
        if (null != status) {
            hql.append("AND go.status=:status ");
            param.put("status", status);
        }
        if (null != month && month.intValue()>0) {
            Date date = DateUtils.addMonths(new Date(), -month.intValue());
            hql.append("AND go.createDate > :date ");
            param.put("date", date);
        }
        criteria.setSort("go.createDate");
        criteria.setOrder("desc");
        return this.findPage(hql.toString(), criteria, criteria.getParams(), param);
    }

    public List<GoodsOrder> findListByUserId(Integer userId) {
        StringBuffer hql = new StringBuffer("From GoodsOrder go WHERE go.deleted = 0  AND go.userId = ?0 ");
        return find(hql.toString(), userId);
    }

    public List<GoodsOrder> findListByGoods(Integer goodsId) {
        StringBuffer hql = new StringBuffer("From GoodsOrder go WHERE go.deleted = 0  AND go.goods = ?0 ");
        return find(hql.toString(), goodsId);
    }

}
