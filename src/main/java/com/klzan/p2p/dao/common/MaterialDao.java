/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.common;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.Material;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 材料
 * @author: chenxinglin
 */
@Repository
public class MaterialDao extends DaoSupport<Material> {

    public List<Material> findList(Integer borrowingID) {
        StringBuffer hql = new StringBuffer("From Material m WHERE deleted = 0 ");
        hql.append(" AND m.borrowing = " + borrowingID );
        return this.find(hql.toString());
    }

    public void deleteList(Integer borrowingID) {
        StringBuffer hql = new StringBuffer("update Material m set deleted = 1 WHERE deleted = 0 ");
        hql.append(" AND m.borrowing = " + borrowingID );
        this.executeUpdate(hql.toString());
    }

}