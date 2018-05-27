/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.borrowing;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.BorrowingFieldRemark;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 借款备注
 */
@Repository
public class BorrowingFieldRemarkDao extends DaoSupport<BorrowingFieldRemark> {

    public List<BorrowingFieldRemark> findByBorrowing(Integer borrowingId) {
        String hql = "FROM BorrowingFieldRemark WHERE deleted=false AND borrowing=?0 ";
        return find(hql, borrowingId);
    }
}