package com.klzan.p2p.service.template.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.template.TemplateContentDao;
import com.klzan.p2p.enums.TemplateContentType;
import com.klzan.p2p.enums.TemplatePurpose;
import com.klzan.p2p.model.TemplateContent;
import com.klzan.p2p.service.template.TemplateContentService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by suhao Date: 2016/12/5 Time: 15:36
 *
 * @version: 1.0
 */
@Service
public class TemplateContentServiceImpl extends BaseService<TemplateContent> implements TemplateContentService {
    @Inject
    private TemplateContentDao templateContentDao;
    @Override
    public TemplateContent findByType(TemplateContentType templateContentType, TemplatePurpose templatePurpose) {
        return templateContentDao.findByType(templateContentType, templatePurpose);
    }
}
