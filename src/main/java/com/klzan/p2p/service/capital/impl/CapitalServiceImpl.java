/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.capital.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.capital.CapitalDao;
import com.klzan.p2p.enums.CapitalMethod;
import com.klzan.p2p.enums.CapitalType;
import com.klzan.p2p.model.Capital;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.capital.CapitalVo;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhutao  Date: 2016/04/06
 */
@Service("capitalService")
public class CapitalServiceImpl extends BaseService<Capital> implements CapitalService {

    @Inject
    private CapitalDao capitalDao;

    @Inject
    private UserService userService;

    @Override
    public PageResult<Capital> findList(PageCriteria pageCriteria, CapitalType type, CapitalMethod method){
        return capitalDao.findList(pageCriteria, type, method);
    }

    @Override
    public PageResult<CapitalVo> findPage(PageCriteria criteria,String createDateBegin, String createDateEnd) {
        PageResult<CapitalVo> page = capitalDao.findPage(criteria,createDateBegin,createDateEnd);
        for (CapitalVo capitalVo : page.getRows()) {
            User user = userService.get(capitalVo.getUserId());
            capitalVo.setName(user.getName());
            capitalVo.setUserType(user.getType());
            capitalVo.setMobile(user.getMobile());
        }
        return page;
    }

    @Override
    public PageResult<CapitalVo> findPage(PageCriteria criteria, User currentUser) {
        PageResult<CapitalVo> page = capitalDao.findPage(criteria, currentUser.getId());
        for (CapitalVo capitalVo : page.getRows()) {
            capitalVo.setName(currentUser.getName());
        }
        return page;
    }

    @Override
    public List<CapitalVo> findAllCapital(String mobile, String type, String method, String createDateBegin, String createDateEnd) {
        Map map = new HashMap();
        if(StringUtils.isNotBlank(mobile)){
            map.put("mobile",mobile);
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

        return myDaoSupport.findList("com.klzan.p2p.mapper.CapitalMapper.findCapital",map);
    }


    @Override
    public void addUserCapital(Capital capital) {
        capitalDao.persist(capital);
    }

    @Override
    public Capital findByOrderNo(String orderNo) {
        return capitalDao.findByOrderNo(orderNo);
    }
}
