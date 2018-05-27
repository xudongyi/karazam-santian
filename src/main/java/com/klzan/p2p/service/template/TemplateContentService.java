package com.klzan.p2p.service.template;

import com.klzan.p2p.enums.TemplateContentType;
import com.klzan.p2p.enums.TemplatePurpose;
import com.klzan.p2p.model.TemplateContent;

/**
 * Created by suhao on 2016/12/5.
 */
public interface TemplateContentService {
    /**
     * 根据类型查询模板
     * @param templateContentType
     * @param templatePurpose
     * @return
     */
    TemplateContent findByType(TemplateContentType templateContentType, TemplatePurpose templatePurpose);
}
