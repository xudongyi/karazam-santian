package com.klzan.p2p.service.sysuser;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.sys.SysRole;
import com.klzan.p2p.vo.sysuser.SysRoleVo;

import java.util.List;
import java.util.Set;

public interface SysRoleService extends IBaseService<SysRole> {
    /**
     * 新增
     * @param roleVo
     * @return
     */
    SysRole createRole(SysRoleVo roleVo);

    /**
     * 更新
     * @param roleVo
     * @return
     */
    SysRole updateRole(SysRoleVo roleVo);

    /**
     * 删除
     * @param roleId
     */
    void deleteRole(Integer roleId);

    /**
     * 根据角色编号得到权限字符串列表
     * @param roleIds
     * @return
     */
    Set<String> findPermissions(Integer... roleIds);

    /**
     * 查询分页
     * @param pageCriteria
     * @return
     */
    PageResult<SysRoleVo> findPageRoles(PageCriteria pageCriteria);

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    List<Integer> getPermissionIds(Integer roleId);

    /**
     * 更新角色权限
     * @param roleId
     * @param newResourcesIdList
     */
    void updateRolePermission(Integer roleId, List<Integer> newResourcesIdList);

    /**
     * 根据角色编号得到角色标识符列表
     * @param roleIds
     * @return
     */
    Set<String> findRoles(Integer... roleIds);
}
