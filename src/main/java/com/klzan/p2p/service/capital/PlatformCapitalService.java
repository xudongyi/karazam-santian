/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.capital;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.PlatformCapital;
import com.klzan.p2p.vo.capital.PlatformCapitalVo;

import java.util.List;

/**
 * 平台资金
 * @author: zhutao  Date: 2017/02/07 Time: 14:15
 */
public interface PlatformCapitalService extends IBaseService<PlatformCapital> {


    /**
     * 分页查询平台资金
     * @param pageCriteria
     * @return
     */
    PageResult<PlatformCapitalVo> findHuaShanCapitalList(PageCriteria pageCriteria, String operator, String type,
                                                         String method, String createDateBegin, String createDateEnd);

    List<PlatformCapitalVo> findAllPlatFormAmt(String operator, String type, String method, String createDateBegin, String createDateEnd);
}
