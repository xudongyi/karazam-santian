package com.klzan.p2p.dao.sysuser;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.model.sys.SysUser;
import com.klzan.p2p.vo.sysuser.SysUserVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysUserDao extends DaoSupport<SysUser> {

    public List<SysUser> findAll() {
        return super.find("FROM SysUser ");
    }

    public SysUser findByLoginName(String loginName) {
        return super.findUnique("FROM SysUser WHERE loginName=?0 ", loginName);
    }

    public PageResult<SysUserVo> findUsers(PageCriteria pageCriteria) {
        StringBuffer sb = new StringBuffer()
                .append("SELECT ")
                .append("     u.id              as id, ")
                .append("     u.login_name      as loginName, ")
                .append("     u.name            as name, ")
                .append("     u.gender          as genderDisplay, ")
                .append("     u.email           as email, ")
                .append("     u.mobile          as mobile, ")
                .append("     u.status          as statusStr, ")
                .append("     u.login_count     as loginCount, ")
                .append("     u.previous_visit  as previousVisit, ")
                .append("     u.create_date     as createDate ")
                .append("FROM karazam_sys_user u ")
                .append("WHERE u.deleted = false ");
        return findPageBySQL(sb.toString(), pageCriteria, new ScalarAliasCallback<SysUserVo>() {
            @Override
            protected Class<SysUserVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("loginName", StandardBasicTypes.STRING);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("genderDisplay", StandardBasicTypes.STRING);
                query.addScalar("email", StandardBasicTypes.STRING);
                query.addScalar("mobile", StandardBasicTypes.STRING);
                query.addScalar("statusStr", StandardBasicTypes.STRING);
                query.addScalar("loginCount", StandardBasicTypes.INTEGER);
                query.addScalar("previousVisit", StandardBasicTypes.TIMESTAMP);
                query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
                return SysUserVo.class;
            }
        }, pageCriteria.getParams());
    }

}
