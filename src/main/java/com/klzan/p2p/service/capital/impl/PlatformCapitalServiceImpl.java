/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.capital.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.capital.PlatformCapitalDao;
import com.klzan.p2p.model.PlatformCapital;
import com.klzan.p2p.service.capital.PlatformCapitalService;
import com.klzan.p2p.vo.capital.PlatformCapitalVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台资金
 * @author: zhutao  Date: 2017/02/07 Time: 14:15
 */
@Service
public class PlatformCapitalServiceImpl extends BaseService<PlatformCapital> implements PlatformCapitalService {

    @Resource
    private PlatformCapitalDao platformCapitalDao;

    @Override
    public PageResult<PlatformCapitalVo> findHuaShanCapitalList(PageCriteria pageCriteria, String operator, String type, String method, String createDateBegin, String createDateEnd) {

        Map map = new HashMap();
        if(StringUtils.isNotBlank(operator)){
            map.put("operator",operator);
        }
        if(StringUtils.isNotBlank(type)){
            map.put("type",type);
        }
        if(StringUtils.isNotBlank(method)){
            map.put("method",method);
        }
        if(StringUtils.isNotBlank(createDateBegin)){
            createDateBegin = createDateBegin + " 00:00:00";
            map.put("createDateBegin",createDateBegin);
        }
        if(StringUtils.isNotBlank(createDateEnd)){
            createDateEnd = createDateEnd + " 23:59:59";
            map.put("createDateEnd",createDateEnd);
        }

        PageResult<PlatformCapitalVo> huashanCapitalPageResult = myDaoSupport.findPage("com.klzan.p2p.mapper.PlatformCapitalMapper.findPlatFormAmount",map,pageCriteria);
        return huashanCapitalPageResult;
    }

    @Override
    public List<PlatformCapitalVo> findAllPlatFormAmt(String operator, String type, String method, String createDateBegin, String createDateEnd){
        Map map = new HashMap();
        if(StringUtils.isNotBlank(operator)){
            map.put("operator",operator);
        }
        if(StringUtils.isNotBlank(type)){
            map.put("type",type);
        }
        if(StringUtils.isNotBlank(method)){
            map.put("method",method);
        }
        if(StringUtils.isNotBlank(createDateBegin)){
            createDateBegin = createDateBegin + " 00:00:00";
            map.put("createDateBegin",createDateBegin);
        }
        if(StringUtils.isNotBlank(createDateEnd)){
            createDateEnd = createDateEnd + " 23:59:59";
            map.put("createDateEnd",createDateEnd);
        }
        return myDaoSupport.findList("com.klzan.p2p.mapper.PlatformCapitalMapper.findPlatFormAmount",map);
    }
}
