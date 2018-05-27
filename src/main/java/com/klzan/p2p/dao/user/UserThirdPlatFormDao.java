/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.user;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.OtherPlatform;
import com.klzan.p2p.model.UserThirdAccount;
import org.springframework.stereotype.Repository;

/**
 * Created by suhao Date: 2016/11/3 Time: 15:47
 *
 * @version:
 */
@Repository
public class UserThirdPlatFormDao extends DaoSupport<UserThirdAccount> {
    public UserThirdAccount findByOpenIdAndPlatform(String openid, OtherPlatform platform) {
        String hql = "FROM UserThirdAccount WHERE openid=?0 AND platform=?1 ";
        return super.findUnique(hql, openid, platform);
    }
}
