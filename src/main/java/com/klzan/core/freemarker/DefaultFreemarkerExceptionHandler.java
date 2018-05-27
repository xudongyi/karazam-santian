package com.klzan.core.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Writer;

public class DefaultFreemarkerExceptionHandler implements TemplateExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(DefaultFreemarkerExceptionHandler.class);

	@Override
	public void handleTemplateException(TemplateException te, Environment env,
										Writer out) throws TemplateException {
		logger.error(te.getMessage(), te);
	}

}
