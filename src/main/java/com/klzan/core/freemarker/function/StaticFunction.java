package com.klzan.core.freemarker.function;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.util.Assert;

import java.util.List;

public class StaticFunction extends FunctionSupport implements TemplateMethodModelEx {

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		Assert.notEmpty(arguments, String.format("the parameter %s when using static function must not be empty.", "path"));
		String path = (String)getRealObject(arguments, 0);
		String resPath = path;
		if(!path.startsWith("/")) {
        	resPath = "/static/" + path;
        }
		return super.constructCDNURL(resPath);
	}
}
