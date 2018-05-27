/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.point;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.PointMethod;
import com.klzan.p2p.enums.PointType;
import com.klzan.p2p.model.Order;
import com.klzan.p2p.model.PointRecord;

import java.util.Date;
import java.util.List;


/**
 * 积分记录
 * @author: chenxinglin
 */
public interface PointRecordService extends IBaseService<PointRecord> {

    /**
     * 积分记录
     * @return
     */
    PageResult<PointRecord> findPage(PageCriteria pageCriteria, Integer userId, PointType type, Integer month);

    /**
     * 积分记录
     * @param userId 用户ID
     * @return
     */
    List<PointRecord> findListByUserId(Integer userId);

    /**
     * 积分记录
     * @return
     */
    List<PointRecord> findList(Integer userId, PointMethod method);

    /**
     * 积分记录
     * @return
     */
    List<PointRecord> findList(Integer userId, PointMethod method, Date startDate, Date endDate);

//    /**
//     * 积分记录
//     * @param goodsId 用户ID
//     * @return
//     */
//    List<PointRecord> findListByGoods(Integer goodsId);


}
