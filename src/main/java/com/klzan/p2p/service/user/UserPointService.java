/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.user;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.*;


/**
 * 用户积分
 * @author: chenxinglin
 */
public interface UserPointService extends IBaseService<UserPoint> {

    /**
     * 用户积分
     * @param userId 用户ID
     * @return
     */
    UserPoint findByUserId(Integer userId);

    /**
     * 注册积分
     * @param userId 用户ID
     * @return
     */
    void regist(Integer userId);

    /**
     * 推荐积分
     * @param referralId 被推荐人ID
     * @param referrerId 推荐人ID
     * @return
     */
    void referral(Integer referralId, Integer referrerId);

    /**
     * 签到积分
     * @param userId 用户ID
     * @return Object[积分，红包]
     */
    Object[] signIn(Integer userId);

    /**
     * 投资积分
     * @param investment 投资
     * @return
     */
    void invest(Investment investment);

    /**
     * 回款积分
     * @param repaymentPlan 回款计划
     * @return
     */
    void repayment(RepaymentPlan repaymentPlan);

    /**
     * 商品兑换
     * @param userId 用户ID
     * @param count 积分
     * @return
     */
    void exchange(Integer userId, Integer count);

    /**
     * 商品订单撤销
     * @param goodsOrder 订单
     * @return
     */
    void exchangeCancel(GoodsOrder goodsOrder);

}
