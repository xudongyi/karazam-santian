package com.klzan.p2p.service.user.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.model.UserLog;
import com.klzan.p2p.service.user.UserLogService;
import com.klzan.p2p.vo.user.UserLogVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhutao on 2017/2/14.
 */
@Service
public class UserLogServiceImpl extends BaseService<UserLog> implements UserLogService {

    @Override
    public PageResult<UserLogVo> findUserLogListByPage(PageCriteria pageCriteria, String loginName, String mobile) {
        Map map = new HashMap();
        map.put("loginName",loginName);
        map.put("mobile",mobile);
        return myDaoSupport.findPage("userlog.findList",map,pageCriteria);
    }
}
