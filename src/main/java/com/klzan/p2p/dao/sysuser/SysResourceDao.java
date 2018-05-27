package com.klzan.p2p.dao.sysuser;

import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.model.sys.SysResource;
import com.klzan.p2p.vo.sysuser.SysResourceVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysResourceDao extends DaoSupport<SysResource> {

    public void delete(Integer resourceId) {
        SysResource resource = findOne(resourceId);
        super.deleteById(resourceId);
        String deleteChildrenSql = "delete from karazam_sys_resource where parent_ids like ?0 ";
        super.executeUpdateWithSQL(deleteChildrenSql, resource.makeSelfAsParentIds() + "%");
    }

    public SysResource findOne(Integer resourceId) {
        return super.get(resourceId);
    }

    public List<SysResource> findAll() {
        return super.find("FROM SysResource order by sort ASC ");
    }

    public List<SysResource> findChildren(Integer resourceId) {
        String hql = "FROM SysResource WHERE parentId = ?0 order by sort ASC ";
        return super.find(hql, resourceId);
    }

    public List<Integer> findResourceIds(Integer roleId) {
        return super.findBySQL("select resource_id from karazam_sys_role_resource where role_id=?0 ", roleId);
    }

    public List<SysResourceVo> findMenus() {
        StringBuffer sql = new StringBuffer();
        sql.append("select r.id id,r.parent_id parentId,r.`name` name, r.url url,ifnull(r.icon, 'fa fa-file-archive-o') icon,r.sort sort,r.description description from karazam_sys_resource r ");
        sql.append("where r.type='menu' order by r.sort");
        List<SysResourceVo> resourceVos = getResourceVos(sql);
        for (SysResourceVo resourceVo : resourceVos) {
            List<SysResource> children = findChildren(resourceVo.getId());
            if (!children.isEmpty() && children.size() > 0) {
                resourceVo.setHasChildren(true);
            } else {
                resourceVo.setHasChildren(false);
            }
        }
        return resourceVos;
    }

    public List<SysResourceVo> findNotRootList() {
        StringBuffer sql = new StringBuffer();
        sql.append("select r.id id,r.parent_id parentId,r.`name` name, r.url url,ifnull(r.icon, 'fa fa-file-archive-o') icon,r.sort sort,r.description description from karazam_sys_resource r ");
        sql.append("where r.id != 1 order by r.sort");
        List<SysResourceVo> resourceVos = getResourceVos(sql);
        for (SysResourceVo resourceVo : resourceVos) {
            List<SysResource> children = findChildren(resourceVo.getId());
            if (!children.isEmpty() && children.size() > 0) {
                resourceVo.setHasChildren(true);
            } else {
                resourceVo.setHasChildren(false);
            }
        }
        return resourceVos;
    }

    public List<SysResourceVo> getMenuOperation(Integer parentId) {
        StringBuffer sql = new StringBuffer();
        sql.append("select r.id id,r.`name` name, r.permission permission, r.url url,r.sort sort,r.description description from karazam_sys_resource r ");
        sql.append("where r.type='button' and r.parent_id=?0 order by r.sort");
        return super.findBySQL(sql.toString(), new ScalarAliasCallback<SysResourceVo>() {
            @Override
            protected Class<SysResourceVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("permission", StandardBasicTypes.STRING);
                query.addScalar("url", StandardBasicTypes.STRING);
                query.addScalar("sort", StandardBasicTypes.INTEGER);
                query.addScalar("description", StandardBasicTypes.STRING);
                return SysResourceVo.class;
            }
        }, parentId);
    }

    private List<SysResourceVo> getResourceVos(StringBuffer sql) {
        return super.findBySQL(sql.toString(), new ScalarAliasCallback<SysResourceVo>() {
            @Override
            protected Class<SysResourceVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("parentId", StandardBasicTypes.INTEGER);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("url", StandardBasicTypes.STRING);
                query.addScalar("icon", StandardBasicTypes.STRING);
                query.addScalar("sort", StandardBasicTypes.INTEGER);
                query.addScalar("description", StandardBasicTypes.STRING);
                return SysResourceVo.class;
            }
        });
    }
}
