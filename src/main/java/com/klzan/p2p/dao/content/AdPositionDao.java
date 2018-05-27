package com.klzan.p2p.dao.content;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.model.AdPosition;
import com.klzan.p2p.vo.content.AdPositionVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhu on 2016/12/14.
 */
@Repository
public class AdPositionDao extends DaoSupport<AdPositionVo> {

    public PageResult<AdPositionVo> findPageByCategory(PageCriteria pageCriteria) {

        StringBuffer sb = new StringBuffer()
                .append("SELECT ")
                .append("b.id as id,")
                .append("b.create_date as createDate,")
                .append("b.name as name,")
                .append("b.ident as ident,")
                .append("b.builtin as builtin,")
                .append("b.description as description ")
                .append("FROM karazam_ad_position b ")
                .append("where deleted=0");
        return this.findPageBySQL(sb.toString(), pageCriteria, new ScalarAliasCallback<AdPositionVo>() {
            @Override
            protected Class<AdPositionVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("createDate", StandardBasicTypes.DATE);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("ident", StandardBasicTypes.STRING);
                query.addScalar("builtin", StandardBasicTypes.BOOLEAN);
                query.addScalar("description", StandardBasicTypes.STRING);
                return AdPositionVo.class;
            }
        }, pageCriteria.getParams());
    }

    public List<AdPosition> findByName(String name) {
        String sql = "select * from karazam_ad_position where deleted=0 and name=?0 ";
        return findBySQL(sql, AdPosition.class, name);
    }

    public List<AdPosition> findByIdent(String ident) {
        String sql = "select * from karazam_ad_position where deleted=0 and ident=?0 ";
        return findBySQL(sql, AdPosition.class, ident);
    }
}
