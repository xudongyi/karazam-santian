/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.goods;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.GoodsFollow;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品关注
 * @author: chenxinglin
 */
@Repository
public class GoodsFollowDao extends DaoSupport<GoodsFollow> {

    /**
     * 商品关注
     * @return
     */
    public PageResult<GoodsFollow> findPage(PageCriteria criteria, Integer userId) {
        StringBuffer hql = new StringBuffer("From GoodsFollow gf WHERE gf.deleted = 0 ");
        Map param = new HashMap();
        if (null != userId) {
            hql.append("AND gf.userId=:userId ");
            param.put("userId", userId);
        }
        criteria.setSort("createDate");
        criteria.setOrder("desc");
        return this.findPage(hql.toString(), criteria, criteria.getParams(), param);
    }

    /**
     * 商品关注
     * @param userId 用户
     * @param goods 商品
     * @return
     */
    public GoodsFollow find(Integer userId, Integer goods) {
        StringBuffer hql = new StringBuffer("From GoodsFollow gf WHERE gf.deleted = 0 and gf.userId = ?0 and gf.goods = ?1 ");
        return findUnique(hql.toString(), userId, goods);
    }

}