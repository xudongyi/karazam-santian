package com.klzan.core.freemarker.function;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.util.Assert;

import java.util.List;

public class UrlFunction extends FunctionSupport implements TemplateMethodModelEx {

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		Assert.notEmpty(arguments, String.format("the parameter %s when using url function must not be empty.", "path"));
		String url = (String)getRealObject(arguments, 0);
		String scheme = null;
		if(arguments.size() > 1) {
			scheme = (String)getRealObject(arguments, 1);
		}
		return constructURL(url, scheme);
	}
}
