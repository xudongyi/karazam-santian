package com.klzan.core.freemarker.templateloader;

import com.klzan.core.freemarker.DefaultFreemarkerViewResolver;
import freemarker.template.Template;

import javax.inject.Inject;
import java.io.IOException;

public class MasterTemplateLoader implements TemplateLoader {

    @Inject
    private DefaultFreemarkerViewResolver viewResolver;

    private TemplateLoader templateLoader;

    public Template loadTemplate(String template) throws IOException {
        String fullTemplatePath = viewResolver.buildFullTemplatePath(template);
        return templateLoader.loadTemplate(fullTemplatePath);
    }

    public void setView(TemplateLoader templateLoader) {
        this.templateLoader = templateLoader;
    }
}