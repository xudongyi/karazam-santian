/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.user;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.UserPoint;
import org.springframework.stereotype.Repository;

/**
 * 用户积分
 * @author: chenxinglin
 */
@Repository
public class UserPointDao extends DaoSupport<UserPoint> {

    public UserPoint findByUserId(Integer userId) {
        StringBuffer hql = new StringBuffer("From UserPoint up WHERE up.deleted = 0  AND up.userId = ?0 ");
        return findUnique(hql.toString(), userId);
    }

}
