package com.klzan.core.freemarker.tags;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

public class StyleSheetTag extends TagSupport implements TemplateDirectiveModel {
	
    private final String nestedContent;
    
    public StyleSheetTag(String nestedContent) {
        this.nestedContent = nestedContent;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    	assertNoBody(body);
    	env.getOut().write(nestedContent);
    }
}
