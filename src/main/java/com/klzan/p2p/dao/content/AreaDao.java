/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.content;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by suhao Date: 2016/11/17 Time: 19:07
 *
 * @version: 1.0
 */
@Repository
public class AreaDao extends DaoSupport<Area> {

    public List<Area> getAreaList() {
        String sql = "select * from karazam_area where deleted = 0 and ISNULL(parent)  ORDER BY id,sort";
        return this.findBySQL(sql, Area.class);
    }

    public List<Area> findChildren(Integer parentId) {
        String sql = "select * from karazam_area where deleted = 0 and parent = ?0 ORDER BY id,sort";
        return this.findBySQL(sql, Area.class, parentId);
    }

    public List<Area> findRoots() {
        String sql = "select * from karazam_area where deleted = 0 and parent is null ";
        return this.findBySQL(sql, Area.class);
    }

    public Area findByCode(String code) {
        String sql = "select * from karazam_area where 1=1 AND code = ?0 ";
        return this.findUniqueBySQL(sql, Area.class, code);
    }

}
