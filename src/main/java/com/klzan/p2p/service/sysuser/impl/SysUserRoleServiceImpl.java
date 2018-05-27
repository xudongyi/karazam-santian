package com.klzan.p2p.service.sysuser.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.sysuser.SysUserRoleDao;
import com.klzan.p2p.model.sys.SysUserRole;
import com.klzan.p2p.service.sysuser.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by suhao Date: 2017/3/17 Time: 17:38
 *
 * @version: 1.0
 */
@Service
public class SysUserRoleServiceImpl extends BaseService<SysUserRole> implements SysUserRoleService {
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public List<SysUserRole> findUserRoles(Integer userId) {
        StringBuilder hql = new StringBuilder("FROM SysUserRole WHERE userId=?0 ");
        return this.find(hql.toString(), userId);
    }

    @Override
    public List<Integer> findRoleIdList(Integer userId) {
        StringBuilder hql= new StringBuilder("select ur.role_id from karazam_sys_user_role ur where ur.user_id=?0");
        return this.findBySQL(hql.toString(), userId);
    }

    @Override
    public void updateUserRole(Integer userId, List<Integer> newUserRoleList) {
        List<Integer> roleIdList = findRoleIdList(userId);
        // 删除
        for (Integer roleId : roleIdList) {
            if (!newUserRoleList.contains(roleId)) {
                SysUserRole sysUserRole = sysUserRoleDao.findByUserIdAndRoleId(userId, roleId);
                sysUserRoleDao.delete(sysUserRole);
            }
        }

        // 添加
        for (Integer roleId : newUserRoleList) {
            if (!roleIdList.contains(roleId)) {
                sysUserRoleDao.persist(new SysUserRole(userId, roleId));
            }
        }
    }
}
