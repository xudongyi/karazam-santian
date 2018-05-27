package com.klzan.core.freemarker.tags;

import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * Created by suhao on 2015/9/11.
 */
public class ShiroTag extends FreeMarkerConfigurer {

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();
//        this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }

}