package com.klzan.p2p.dao.links;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.model.Links;
import com.klzan.p2p.vo.links.LinksVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

/**
 * Created by zhu on 2016/12/14.
 */
@Repository
public class LinksDao extends DaoSupport<Links> {

    public PageResult<LinksVo> findPageByCategory(PageCriteria pageCriteria) {

        StringBuffer sb = new StringBuffer()
                .append("SELECT ")
                .append("b.id as id,")
                .append("b.sort as sort,")
                .append("b.create_date as createDate,")
                .append("b.name as name,")
                .append("b.type as typeStr,")
                .append("b.logo as logo,")
                .append("b.url as url,")
                .append("b.target as target,")
                .append("b.description as description,")
                .append("b.visible as visible ")
                .append("FROM karazam_links b ")
                .append("where deleted=0");
        return this.findPageBySQL(sb.toString(), pageCriteria, new ScalarAliasCallback<LinksVo>() {
            @Override
            protected Class<LinksVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("sort", StandardBasicTypes.INTEGER);
                query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("typeStr", StandardBasicTypes.STRING);
                query.addScalar("logo", StandardBasicTypes.STRING);
                query.addScalar("url", StandardBasicTypes.STRING);
                query.addScalar("target", StandardBasicTypes.STRING);
                query.addScalar("description", StandardBasicTypes.STRING);
                query.addScalar("visible", StandardBasicTypes.BOOLEAN);
                return LinksVo.class;
            }
        },pageCriteria.getParams());
    }

    public Links findLinkByName(String name){
        String sql = "select * from karazam_links where deleted=0 and name=?0";
        return this.findUniqueBySQL(sql,Links.class,name);
    }
}
