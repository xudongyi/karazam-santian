package com.klzan.core.freemarker.tags;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class JSTag extends CDNTagSupports implements TemplateDirectiveModel {
	
    public static final String TEMPLATE_JS_TAG = "<script charset=\"UTF-8\" type=\"text/javascript\" src=\"%s\"></script>";
    
    @Override
    @SuppressWarnings("all")
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    	assertNoBody(body);
        String tags = buildJSTags(params);
        Writer output = env.getOut();
        output.write(tags);
    }

    String buildJSTags(Map<String, Object> params) throws IOException, TemplateModelException {
    	return buildMultipleResourceTags("src", "/static", TEMPLATE_JS_TAG, params);
    }
}
