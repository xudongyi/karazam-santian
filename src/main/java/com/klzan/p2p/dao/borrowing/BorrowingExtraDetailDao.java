/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.borrowing;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.BorrowingExtraDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 借款项目附加详细信息
 */
@Repository
public class BorrowingExtraDetailDao extends DaoSupport<BorrowingExtraDetail> {

    public List<BorrowingExtraDetail> findByExtra(Integer extraId) {
        StringBuffer hql = new StringBuffer("FROM BorrowingExtraDetail WHERE deleted=false AND extraId=?0 ");
        return this.find(hql.toString(), extraId);
    }
}