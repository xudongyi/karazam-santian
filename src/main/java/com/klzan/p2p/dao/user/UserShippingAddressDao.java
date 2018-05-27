/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.PointType;
import com.klzan.p2p.model.PointRecord;
import com.klzan.p2p.model.UserShippingAddress;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户收货地址
 * @author: chenxinglin
 */
@Repository
public class UserShippingAddressDao extends DaoSupport<UserShippingAddress> {

    public PageResult<UserShippingAddress> findPage(PageCriteria criteria, Integer userId) {
        StringBuffer hql = new StringBuffer("From UserShippingAddress usa WHERE usa.deleted = 0 ");
        Map param = new HashMap();
        if (null != userId) {
            hql.append("AND usa.userId=:userId ");
            param.put("userId", userId);
        }
        criteria.setSort("createDate");
        criteria.setOrder("desc");
        return this.findPage(hql.toString(), criteria, criteria.getParams(), param);
    }

    public List<UserShippingAddress> findListByUserId(Integer userId) {
        StringBuffer hql = new StringBuffer("From UserShippingAddress usa WHERE usa.deleted = 0  AND usa.userId = ?0 ");
        return find(hql.toString(), userId);
    }

    public Integer count(Integer userId) {
        StringBuffer sql = new StringBuffer("select COUNT(1) FROM karazam_user_shipping_address usa WHERE usa.deleted = 0  AND usa.user_id = ?0 ");
        return super.countWithSQL(sql.toString(), userId);
    }

    public void resetPreferred(Integer userId) {
        if(userId == null){
            throw new RuntimeException("参数错误");
        }
        StringBuffer sql = new StringBuffer("update karazam_user_shipping_address usa set usa.preferred = 0 WHERE usa.deleted = 0  AND usa.user_id = ?0 ");
        super.executeUpdateWithSQL(sql.toString(), userId);
    }



}
