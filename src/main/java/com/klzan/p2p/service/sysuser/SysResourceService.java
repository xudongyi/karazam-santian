package com.klzan.p2p.service.sysuser;

import com.klzan.p2p.model.sys.SysResource;
import com.klzan.p2p.vo.sysuser.SysResourceVo;

import java.util.List;
import java.util.Set;

public interface SysResourceService {
    /**
     * 新增
     * @param resourceVo
     * @return
     */
    SysResource create(SysResourceVo resourceVo);

    /**
     * 更新
     * @param resourceVo
     * @return
     */
    SysResource update(SysResourceVo resourceVo);

    /**
     * 删除
     * @param resourceId
     */
    void delete(Integer resourceId);

    /**
     * 查询Resource
     * @param resourceId
     * @return
     */
    SysResource findOne(Integer resourceId);

    /**
     * 查询全部
     * @return
     */
    List<SysResource> findAll();

    /**
     * 得到资源对应的权限字符串
     * @param resourceIds
     * @return
     */
    Set<String> findPermissions(Set<Integer> resourceIds);

    /**
     * 根据用户权限得到菜单
     * @param permissions
     * @return
     */
    List<SysResource> findMenus(Set<String> permissions);

    /**
     * 查询菜单
     * @return
     */
    List<SysResourceVo> findMenus();

    /**
     * 查询非根节点列表
     * @return
     */
    List<SysResourceVo> findNotRootList();

    /**
     * 查询菜单资源
     * @param parentId
     * @return
     */
    List<SysResourceVo> getMenuOperation(Integer parentId);

    List<SysResource> findMenusByParent(Integer parentId);
}
