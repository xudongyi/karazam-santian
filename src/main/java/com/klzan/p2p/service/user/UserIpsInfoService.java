/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.user;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.UserIpsInfo;

import java.util.List;

public interface UserIpsInfoService extends IBaseService<UserIpsInfo> {

    /**
     * 根据ID 查询环迅账户信息
     * @param ids
     */
    List updateIpsInfo(Integer[] ids,String type);


    /**
     * 根据用户ID查询userIpsInfo
     * @param id
     * @return
     */
    UserIpsInfo queryByUserId(Integer id);
}
