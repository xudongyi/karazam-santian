/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.goods;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.GoodsType;
import com.klzan.p2p.model.Goods;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品
 * @author: chenxinglin
 */
@Repository
public class GoodsDao extends DaoSupport<Goods> {

    /**
     * 分页列表
     * @param criteria
     * @return
     */
    public PageResult<Goods> findPage(PageCriteria criteria) {
        StringBuffer hql = new StringBuffer("From Goods g WHERE g.deleted = 0");
        return this.findPage(hql.toString(), criteria, criteria.getParams());
    }

    /**
     * 分页列表
     * @param criteria
     * @return
     */
    public PageResult<Goods> findPage(PageCriteria criteria, Integer goodsCategory, GoodsType type, Integer point) {
        StringBuffer hql = new StringBuffer("From Goods g WHERE g.deleted = 0 AND g.stock > 0 AND g.putaway = 1 ");
        Map param = new HashMap();
        if (null != goodsCategory) {
            hql.append(" AND g.goodsCategory=:goodsCategory ");
            param.put("goodsCategory", goodsCategory);
        }
        if (null != type) {
            hql.append(" AND g.type=:type ");
            param.put("type", type);
        }
        if (null != point) {
            hql.append(" AND g.point<:point ");
            param.put("point", point);
        }
        return this.findPage(hql.toString(), criteria, criteria.getParams(), param);
    }

    /**
     * 商品
     * @param goodsCategory 商品分类
     * @return
     */
    public List<Goods> findAllList(Integer goodsCategory) {
        StringBuffer hql = new StringBuffer("From Goods g WHERE g.deleted = 0 and g.goodsCategory = ?0 ");
        return find(hql.toString(), goodsCategory);
    }

    /**
     * 商品
     * @param goodsCategory 商品分类
     * @return
     */
    public List<Goods> findList(Integer goodsCategory) {
        StringBuffer hql = new StringBuffer("From Goods g WHERE g.deleted = 0 AND g.stock > 0 AND g.putaway = 1 and g.goodsCategory = ?0 ");
        return find(hql.toString(), goodsCategory);
    }

    /**
     * 商品
     * @param goodsCategory 商品分类
     * @return
     */
    public List<Goods> findList(Integer goodsCategory, GoodsType type, Integer count, Boolean hot) {
        StringBuffer hql = new StringBuffer("From Goods g WHERE g.deleted = 0 AND g.stock > 0 AND g.putaway = 1 ");
        Map param = new HashMap();
        if (null != goodsCategory) {
            hql.append(" AND g.goodsCategory=:goodsCategory ");
            param.put("goodsCategory", goodsCategory);
        }
        if (null != type) {
            hql.append(" AND g.type=:type ");
            param.put("type", type);
        }
        if (null != hot) {
            hql.append(" AND g.hot=:hot ");
            param.put("hot", hot);
        }
        hql.append(" ORDER BY g.createDate desc");
        if(count == null){
            return find(hql.toString(), param);
        }
        return find(hql.toString(), 0, count, param);
    }

}