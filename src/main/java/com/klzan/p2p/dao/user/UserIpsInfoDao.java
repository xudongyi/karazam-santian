/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.user;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.UserIpsInfo;
import org.springframework.stereotype.Repository;

@Repository
public class UserIpsInfoDao extends DaoSupport<UserIpsInfo> {

    public UserIpsInfo queryByUserId(Integer id){
        String sql = "select * from karazam_user_ips_info where deleted=0 and user_id =?0";
        return this.findUniqueBySQL(sql,UserIpsInfo.class,id);
    }
}
