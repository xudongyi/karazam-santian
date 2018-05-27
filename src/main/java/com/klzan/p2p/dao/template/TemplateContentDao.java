package com.klzan.p2p.dao.template;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.TemplateContentType;
import com.klzan.p2p.enums.TemplatePurpose;
import com.klzan.p2p.model.TemplateContent;
import org.springframework.stereotype.Repository;

/**
 * Created by suhao Date: 2016/12/5 Time: 15:30
 *
 * @version: 1.0
 */
@Repository
public class TemplateContentDao extends DaoSupport<TemplateContent> {
    public TemplateContent findByType(TemplateContentType templateContentType, TemplatePurpose templatePurpose) {
        StringBuilder sb = new StringBuilder("FROM TemplateContent WHERE enable=true AND templateContentType=?0 AND templatePurpose=?1 ");
        return findUnique(sb.toString(), templateContentType, templatePurpose);
    }
}
