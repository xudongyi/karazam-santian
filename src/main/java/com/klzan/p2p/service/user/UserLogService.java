package com.klzan.p2p.service.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.UserLog;
import com.klzan.p2p.vo.user.UserLogVo;

/**
 * Created by zhutao on 2017/2/14.
 */
public interface UserLogService extends IBaseService<UserLog> {

    /**
     * 分页查询用户操作日志
     * @param pageCriteria
     * @param loginName
     * @param mobiel
     * @return
     */
    PageResult<UserLogVo> findUserLogListByPage(PageCriteria pageCriteria, String loginName, String mobiel);
}
