/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.freemarker.method;

import com.klzan.core.util.WebUtils;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 模板方法 - 获取Request里面的值
 */
@Component("getRequestAttributeMethod")
public class GetRequestAttributeMethod implements TemplateMethodModelEx {

    @SuppressWarnings("rawtypes")
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null
                && StringUtils.isNotBlank(arguments.get(0).toString())) {
            Object attribute = WebUtils.getHttpRequest().getAttribute(arguments.get(0).toString());
            return new SimpleScalar(String.valueOf(attribute));
        }
        return null;
    }

}