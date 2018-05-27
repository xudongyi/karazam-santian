package com.klzan.core.freemarker.tags;

import com.klzan.core.RequestContextHolder;
import com.klzan.core.util.FreemarkerUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

public class NestedStylesheetTag extends TagSupport implements TemplateDirectiveModel {

	@Inject
	private RequestContextHolder requestContextHolder;

	@Override
	@SuppressWarnings("all")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		assertHasBody(body);
		String nestedContent = FreemarkerUtils.parseWriteBodyToString(body);
		requestContextHolder.getModel().put(TagName.STYLESHEET.getTagName(), new ScriptTag(nestedContent));
	}
}
