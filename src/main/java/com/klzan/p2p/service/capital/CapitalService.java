/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.capital;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.CapitalMethod;
import com.klzan.p2p.enums.CapitalType;
import com.klzan.p2p.model.Capital;
import com.klzan.p2p.model.User;
import com.klzan.p2p.vo.capital.CapitalVo;

import java.util.List;

/**
 * 资金
 * @author: zhutao  Date: 2017/04/06 Time: 17:07
 */
public interface CapitalService extends IBaseService<Capital> {

    /**
     * 资金列表
     * @param pageCriteria 分页信息
     * @return
     */
    PageResult<Capital> findList(PageCriteria pageCriteria, CapitalType type, CapitalMethod method);

    /**
     * 查询资金分页
     * @param criteria
     * @return
     */
    PageResult<CapitalVo> findPage(PageCriteria criteria,String createDateBegin, String createDateEnd);

    /**
     * 用户资金记录
     * @param criteria
     * @param currentUser
     * @return
     */
    PageResult<CapitalVo> findPage(PageCriteria criteria, User currentUser);

    void addUserCapital(Capital capital);

    Capital findByOrderNo(String orderNo);

    /**
     * 用户资金列表
     * @return
     */
    List<CapitalVo> findAllCapital(String mobile, String type, String method, String createDateBegin, String createDateEnd);
}
