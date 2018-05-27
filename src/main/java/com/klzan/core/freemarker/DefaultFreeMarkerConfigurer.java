package com.klzan.core.freemarker;

import com.klzan.core.freemarker.shirotags.ShiroTags;
import com.klzan.core.freemarker.templateloader.HTMLEscapeTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

public class DefaultFreeMarkerConfigurer extends FreeMarkerConfigurer {

    @Override
    protected TemplateLoader getTemplateLoaderForPath(String templateLoaderPath) {
        TemplateLoader loader = super.getTemplateLoaderForPath(templateLoaderPath);
        return new HTMLEscapeTemplateLoader(loader);
    }

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();
        this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }
}
