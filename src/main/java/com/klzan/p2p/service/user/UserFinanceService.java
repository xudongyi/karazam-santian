/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.UserFinance;
import com.klzan.p2p.vo.capital.UserFinanceVo;

import java.util.List;
import java.util.Map;

public interface UserFinanceService extends IBaseService<UserFinance> {
    /**
     * 根据用户Id查询用户的财务信息
     * @param userId
     * @return
     */
    UserFinanceVo findUserFinanceByUserId(Integer userId);

    /**
     * 查询用户财务信息分页
     * @param criteria
     * @return
     */
    PageResult<UserFinanceVo> findUserFinancePage(PageCriteria criteria);

    /**
     * 用户财务信息
     * @param userId 用户ID
     * @return
     */
    UserFinance findByUserId(Integer userId);

    /**
     * 根据用户获取
     * @param userId
     * @return
     */
    Map<String, Object> getAssetsByUser(Integer userId);

    /**
     * 查询所有用户账户
     * @param mobile
     * @param startCreateDate
     * @param endCreateDate
     * @return
     */
    List<UserFinanceVo> findAllUserFund(String mobile, String startCreateDate, String endCreateDate);

    /**
     * 根据用户查询
     * @param userId
     * @return
     */
    UserFinance getByUserId(Integer userId);
}
