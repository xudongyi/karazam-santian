/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.freemarker.method;

import com.klzan.p2p.enums.IEnumMeta;
import com.klzan.p2p.service.user.UserMetaService;
import com.klzan.p2p.util.UserMetaTypeUtils;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * 模板方法 - 获取用户信息
 */
@Component("userMetaMethod")
public class UserMetaMethod implements TemplateMethodModelEx {

    @Inject
    private UserMetaService userMetaService;

    @SuppressWarnings("rawtypes")
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null && arguments.get(1) != null
                && StringUtils.isNotBlank(arguments.get(0).toString()) && StringUtils.isNotBlank(arguments.get(1).toString())) {
            String metaType = arguments.get(0).toString();
            int userId = Integer.parseInt(arguments.get(1).toString());
            IEnumMeta enumMeta = UserMetaTypeUtils.getMetaType(metaType);
            return userMetaService.convertToBean(enumMeta, userId, enumMeta.getInfoBean());
        }
        return null;
    }

}