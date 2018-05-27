package com.klzan.core.web;

import com.klzan.core.PlatformConstant;
import com.klzan.core.setting.WebSetting;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class DefaultDispatcherServlet extends DispatcherServlet {

	private String version;

	private static final long serialVersionUID = -8164183713379587672L;

	@Override
	protected void initFrameworkServlet() throws ServletException {
		super.initFrameworkServlet();
		ServletContext servletContext = getServletContext();
		WebApplicationContext applicationContext = getWebApplicationContext();
		WebSetting setting = applicationContext.getBean(WebSetting.class);
		setting.setVersion(version);
		servletContext.setAttribute(PlatformConstant.WEB_SETTING, setting);
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
