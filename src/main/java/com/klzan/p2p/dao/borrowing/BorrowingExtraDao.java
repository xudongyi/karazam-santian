/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.borrowing;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.BorrowingExtra;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 借款项目附加信息
 */
@Repository
public class BorrowingExtraDao extends DaoSupport<BorrowingExtra> {

    public List<BorrowingExtra> findByBorrowing(Integer borrowingId) {
        StringBuffer hql = new StringBuffer("FROM BorrowingExtra WHERE deleted=false AND borrowing=?0 ");
        return this.find(hql.toString(), borrowingId);
    }
}