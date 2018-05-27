package com.klzan.p2p.dao.sysuser;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.model.sys.SysRole;
import com.klzan.p2p.vo.sysuser.SysRoleVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysRoleDao extends DaoSupport<SysRole> {
    @Autowired
    private SysRoleResourceDao sysRoleResourceDao;

    public SysRole update(SysRoleVo roleVo) {
        SysRole role = super.get(roleVo.getId());
        role.update(roleVo.getName(), roleVo.getRoleCode(), roleVo.getDescription(), roleVo.getSort());
        super.merge(role);
        return role;
    }

    public void delete(Integer roleId) {
        super.deleteById(roleId);
    }

    public PageResult<SysRoleVo> findPageRoles(PageCriteria pageCriteria) {
        final String sql = "select id, name, role_code roleCode, description, sort, available from karazam_sys_role";
        return super.findPageBySQL(sql, pageCriteria, new ScalarAliasCallback<SysRoleVo>() {
            @Override
            protected Class<SysRoleVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("roleCode", StandardBasicTypes.STRING);
                query.addScalar("description", StandardBasicTypes.STRING);
                query.addScalar("available", StandardBasicTypes.BOOLEAN);
                query.addScalar("sort", StandardBasicTypes.INTEGER);
                return SysRoleVo.class;
            }
        });
    }

    public List<Integer> findPermissionIds(Integer roleId) {
        final String sql = "select resource_id from karazam_sys_role_resource where role_id=?0 ";
        return super.findBySQL(sql, roleId);
    }

}
