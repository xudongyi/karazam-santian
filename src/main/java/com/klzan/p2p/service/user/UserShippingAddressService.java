/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.PointType;
import com.klzan.p2p.model.PointRecord;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserShippingAddress;
import com.klzan.p2p.vo.user.UserShippingAddressVo;

import java.util.List;

/**
 * 收货地址
 * @author: chenxinglin
 */
public interface UserShippingAddressService extends IBaseService<UserShippingAddress> {

    /**
     * 收货地址
     * @return
     */
    PageResult<UserShippingAddress> findPage(PageCriteria pageCriteria, Integer userId);

    /**
     * 收货地址
     * @param userId 用户ID
     * @return
     */
    List<UserShippingAddress> findListByUserId(Integer userId);

    /**
     * 新增
     * @param vo
     * @return
     */
    UserShippingAddress add(User currentUser, UserShippingAddressVo vo);

    /**
     * 修改
     * @param vo
     * @return
     */
    UserShippingAddress update(User currentUser, UserShippingAddressVo vo);

    Integer count(Integer userId);

}
