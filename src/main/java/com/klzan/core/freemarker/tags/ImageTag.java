package com.klzan.core.freemarker.tags;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@SuppressWarnings("all")
public class ImageTag extends CDNTagSupports implements TemplateDirectiveModel {
	
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    	assertNoBody(body);
        String imageUrl = buildImageUrl(params);
        Writer output = env.getOut();
        output.write(imageUrl);
    }

	private String buildImageUrl(Map params) throws TemplateModelException {
    	return buildResourceUrl(getRequiredStringParam(params, "src"), "/static");
	}
}
