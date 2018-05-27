package com.klzan.p2p.service.sysuser.impl;

import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.SnUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.sysuser.SysUserDao;
import com.klzan.p2p.model.sys.SysUser;
import com.klzan.p2p.service.sysuser.SysUserService;
import com.klzan.p2p.vo.sysuser.SysUserVo;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;

@Service
public class SysUserServiceImpl extends BaseService<SysUser> implements SysUserService {

    @Inject
    private SysUserDao userDao;

    @Inject
    private DistributeLock distributeLock;

    /**
     * 创建用户
     *
     * @param userVo
     */
    @Override
    public SysUser createUser(SysUserVo userVo) {
        SysUser user = new SysUser(userVo.getLoginName(), userVo.getName(), userVo.getPassword(), userVo.getBirthdayDate(), userVo.getGender(), userVo.getEmail(), userVo.getMobile(), userVo.getStatus(), userVo.getDescription());
        //加密密码
        PasswordHelper.encryptPassword(user, userVo.getPassword());
        SysUser userCreated = userDao.persist(user);
        userCreated.updateUserNo(SnUtils.getUserNo(user.getId(), 10, 10));
        userDao.update(user);
        return userCreated;
    }

    @Override
    public SysUser updateUser(SysUserVo userVo) {
        SysUser user = get(userVo.getId());
        if (StringUtils.isNotBlank(userVo.getPassword())) {
            //加密密码
            PasswordHelper.encryptPassword(user, userVo.getPassword());
        }
        userVo.setPassword(user.getPassword());
        user.update(userVo.getLoginName(), userVo.getName(), userVo.getPassword(), userVo.getBirthdayDate(), userVo.getGender(), userVo.getEmail(), userVo.getMobile(), userVo.getStatus(), userVo.getDescription());
        if (StringUtils.isBlank(user.getUserNo())) {
            user.updateUserNo(SnUtils.getUserNo(user.getId(), 10, 10));
        }
        return userDao.update(user);
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     */
    public void changePassword(Integer userId, String newPassword) {
        SysUser user = userDao.get(userId);
        PasswordHelper.encryptPassword(user, newPassword);
        userDao.update(user);
    }

    /**
     * 根据用户名查找用户
     *
     * @param loginName
     * @return
     */
    @Override
    public SysUser findByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }

    @Override
    public PageResult<SysUserVo> findUsers(PageCriteria pageCriteria) {
        return userDao.findUsers(pageCriteria);
    }

    @Override
    public void updateLoginUser(SysUser user) {
        try {
            distributeLock.lock(LockStack.USER_LOCK, "updateSysUserLoginInfo" + user.getId());
            Integer loginCount = (user.getLoginCount() == null ? 0 : user.getLoginCount()) + 1;
            Timestamp previousVisit = user.getLastVisit();
            Timestamp lastVisit = DateUtils.getTimestamp();
            user.updateLoginInfo(loginCount, previousVisit, lastVisit);
            userDao.merge(user);
        } finally {
            distributeLock.unLock(LockStack.USER_LOCK, "updateSysUserLoginInfo" + user.getId());
        }
    }

}
