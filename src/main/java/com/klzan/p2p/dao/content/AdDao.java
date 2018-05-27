package com.klzan.p2p.dao.content;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.model.Ad;
import com.klzan.p2p.vo.content.AdVo;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhu on 2016/12/14.
 */
@Repository
public class AdDao extends DaoSupport<AdVo> {

    public PageResult<AdVo> findPageByCategory(PageCriteria pageCriteria) {

        StringBuffer sb = new StringBuffer()
                .append("SELECT ")
                .append("b.id as id,")
                .append("b.sort as sort,")
                .append("b.create_date as createDate,")
                .append("b.title as title,")
                .append("b.type as typeStr,")
                .append("b.path as path,")
                .append("b.cont as cont,")
                .append("b.start_date as startDate,")
                .append("b.end_date as endDate,")
                .append("b.url as url,")
                .append("p.name as positionName,")
                .append("b.position as position ")
                .append("FROM karazam_ad b left join karazam_ad_position p on b.position=p.id ")
                .append("where b.deleted=0");
        return this.findPageBySQL(sb.toString(), pageCriteria, new ScalarAliasCallback<AdVo>() {
            @Override
            protected Class<AdVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("sort", StandardBasicTypes.INTEGER);
                query.addScalar("createDate", StandardBasicTypes.DATE);
                query.addScalar("title", StandardBasicTypes.STRING);
                query.addScalar("typeStr", StandardBasicTypes.STRING);
                query.addScalar("path", StandardBasicTypes.STRING);
                query.addScalar("cont", StandardBasicTypes.STRING);
                query.addScalar("startDate", StandardBasicTypes.DATE);
                query.addScalar("endDate", StandardBasicTypes.DATE);
                query.addScalar("url", StandardBasicTypes.STRING);
                query.addScalar("positionName", StandardBasicTypes.STRING);
                query.addScalar("position", StandardBasicTypes.INTEGER);
                return AdVo.class;
            }
        }, pageCriteria.getParams());
    }

    public PageResult<AdVo> findPageByCategory(Integer position, PageCriteria pageCriteria) {

        StringBuffer sb = new StringBuffer()
                .append("SELECT ")
                .append("b.id as id,")
                .append("b.sort as sort,")
                .append("b.create_date as createDate,")
                .append("b.title as title,")
                .append("b.type as typeStr,")
                .append("b.path as path,")
                .append("b.cont as cont,")
                .append("b.start_date as startDate,")
                .append("b.end_date as endDate,")
                .append("b.url as url,")
                .append("p.name as positionName,")
                .append("b.position as position ")
                .append("FROM ad b left join karazam_ad_position p on b.position=p.id ")
                .append("where b.deleted=0 and position=:position");
        Map<String, Object> params = new HashedMap();
        params.put("position", position);
        return this.findPageBySQL(sb.toString(), pageCriteria, new ScalarAliasCallback<AdVo>() {
            @Override
            protected Class<AdVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("sort", StandardBasicTypes.INTEGER);
                query.addScalar("createDate", StandardBasicTypes.DATE);
                query.addScalar("title", StandardBasicTypes.STRING);
                query.addScalar("typeStr", StandardBasicTypes.STRING);
                query.addScalar("path", StandardBasicTypes.STRING);
                query.addScalar("cont", StandardBasicTypes.STRING);
                query.addScalar("startDate", StandardBasicTypes.DATE);
                query.addScalar("endDate", StandardBasicTypes.DATE);
                query.addScalar("url", StandardBasicTypes.STRING);
                query.addScalar("positionName", StandardBasicTypes.STRING);
                query.addScalar("position", StandardBasicTypes.INTEGER);
                return AdVo.class;
            }
        }, params);
    }

    public List<Ad> findAdByPosition(Integer position) {
        String sql = "select * from karazam_ad where deleted=0 and position=?0";
        return findBySQL(sql, Ad.class, position);
    }
}
