package com.klzan.core.freemarker;

import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

public class DefaultFreemarkerViewResolver extends FreeMarkerViewResolver {

    public String buildFullTemplatePath(String template) {
        return String.format("%s%s%s", getPrefix(), template, getSuffix());
    }

}
