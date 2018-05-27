package com.klzan.core.freemarker.tags;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class CSSTag extends CDNTagSupports implements TemplateDirectiveModel {
	
    public static final String TEMPLATE_CSS_TAG = "<link type=\"text/css\" rel=\"stylesheet\" href=\"%s\"/>";
    
    @Override
    @SuppressWarnings("all")
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    	assertNoBody(body);
        String tags = buildCSSTags(params);
        Writer output = env.getOut();
        output.write(tags);
    }
    
    @SuppressWarnings("all")
	private String buildCSSTags(Map params) throws TemplateModelException, IOException {
        return buildMultipleResourceTags("href", "/static", TEMPLATE_CSS_TAG, params);
	}

}
