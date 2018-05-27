package com.klzan.p2p.service.sysuser;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.sys.SysUser;
import com.klzan.p2p.vo.sysuser.SysUserVo;

public interface SysUserService extends IBaseService<SysUser> {

    /**
     * 创建用户
     * @param userVo
     */
    SysUser createUser(SysUserVo userVo);

    /**
     * 更新
     * @param userVo
     * @return
     */
    SysUser updateUser(SysUserVo userVo);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    void changePassword(Integer userId, String newPassword);

    /**
     * 根据用户名查找用户
     * @param loginName
     * @return
     */
    SysUser findByLoginName(String loginName);

    /**
     * 查询用户分页
     * @param pageCriteria
     * @return
     */
    PageResult<SysUserVo> findUsers(PageCriteria pageCriteria);

    /**
     * 修改用户登录
     *
     * @param user
     */
    void updateLoginUser(SysUser user);

}
