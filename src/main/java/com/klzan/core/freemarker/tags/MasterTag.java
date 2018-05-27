package com.klzan.core.freemarker.tags;

import com.klzan.core.RequestContextHolder;
import com.klzan.core.freemarker.templateloader.MasterTemplateLoader;
import freemarker.core.Environment;
import freemarker.template.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

public class MasterTag extends TagSupport implements TemplateDirectiveModel {

	@Inject
	private MasterTemplateLoader templateLoader;

	@Inject
	private RequestContextHolder requestContextHolder;

	@Override
	@SuppressWarnings("all")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		assertHasBody(body);
		String template = getRequiredStringParam(params, "template");
		Map<String, Object> model = requestContextHolder.getModel();
		model.putAll(params);
		Template masterTemplate = templateLoader.loadTemplate(template);
		model.put(TagName.TAG_BODY.getTagName(), new BodyTag(body));
		registerDefaultWebHelpers(model);
		masterTemplate.process(model, env.getOut());
	}

	private void registerDefaultWebHelpers(Map<String, Object> model) {
		if (model.get(TagName.STYLESHEET.getTagName()) == null) {
			model.put(TagName.STYLESHEET.getTagName(), new StyleSheetTag(""));
		}
		if (model.get(TagName.SCRIPT.getTagName()) == null) {
			model.put(TagName.SCRIPT.getTagName(), new ScriptTag(""));
		}
	}
}
