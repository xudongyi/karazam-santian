package com.klzan.p2p.dao.notice;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.model.Notice;
import com.klzan.p2p.vo.notice.NoticeVo;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by zhu on 2016/12/7.
 */

@Repository
public class NoticeDao extends DaoSupport<Notice> {

    public PageResult<NoticeVo> findPageListPage(Integer id, PageCriteria pageCriteria) {

        StringBuffer sb = new StringBuffer()
                .append("SELECT ")
                .append("b.id as id,")              //主键
                .append("b.cont as cont,")          //内容
                .append("b.publisher as publisher,")//发布者
                .append("b.ip as ip,")              //ip
                .append("b.create_date as createDate,")//创建时间
                .append("b.receivers as receivers ")//接受者
                .append("FROM karazam_notice b ")
                .append("where deleted=0 and receivers =:receivers");
        Map<String, Object> params = new HashedMap();
        params.put("receivers",id);
        return this.findPageBySQL(sb.toString(), pageCriteria, new ScalarAliasCallback<NoticeVo>() {
            @Override
            protected Class<NoticeVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("cont", StandardBasicTypes.STRING);
                query.addScalar("publisher", StandardBasicTypes.STRING);
                query.addScalar("ip", StandardBasicTypes.STRING);
                query.addScalar("createDate", StandardBasicTypes.DATE);
                query.addScalar("receivers", StandardBasicTypes.INTEGER);
                return NoticeVo.class;
            }
        },params);
    }

    public Notice getNoticeDetail(Integer id){
        return this.get(id);
    }
}
