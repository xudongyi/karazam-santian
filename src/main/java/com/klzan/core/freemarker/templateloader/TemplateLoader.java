package com.klzan.core.freemarker.templateloader;

import freemarker.template.Template;

import java.io.IOException;

public interface TemplateLoader {
	
	Template loadTemplate(String templatePath) throws IOException;
}
