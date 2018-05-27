package com.klzan.core.freemarker.tags;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

public class BodyTag extends TagSupport implements TemplateDirectiveModel {
	
    private final TemplateDirectiveBody body;

    public BodyTag(TemplateDirectiveBody body) {
        this.body = body;
    }

    @Override
    @SuppressWarnings("all")
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    	assertNoBody(body);
        this.body.render(env.getOut());
    }
}
