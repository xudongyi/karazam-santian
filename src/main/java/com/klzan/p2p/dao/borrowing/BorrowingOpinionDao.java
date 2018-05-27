/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.borrowing;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.BorrowingOpinion;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 借款
 * @author: chenxinglin
 */
@Repository
public class BorrowingOpinionDao extends DaoSupport<BorrowingOpinion> {

    public List<BorrowingOpinion> findList(Integer borrowingID) {
        StringBuffer hql = new StringBuffer("From BorrowingOpinion m WHERE deleted = 0 ");
        hql.append(" AND m.borrowing = " + borrowingID );
        return this.find(hql.toString());
    }


}