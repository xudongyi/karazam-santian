package com.klzan.core.freemarker;

import com.klzan.core.RequestContextHolder;
import com.klzan.core.exception.SystemException;
import com.klzan.core.freemarker.templateloader.MasterTemplateLoader;
import com.klzan.core.freemarker.templateloader.TemplateLoader;
import com.klzan.core.util.WebUtils;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModelException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class DefaultFreemarkerView extends FreeMarkerView implements TemplateLoader {
	private static final String CONTEXT_PATH = "base";

	private MasterTemplateLoader templateLoader;

	private RequestContextHolder requestContextHolder;

	public DefaultFreemarkerView() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		ApplicationContext applicationContext = getApplicationContext();
		templateLoader = applicationContext.getBean(MasterTemplateLoader.class);
		templateLoader.setView(this);
		requestContextHolder = applicationContext.getBean(RequestContextHolder.class);
	}

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model,
											 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			requestContextHolder.bindModel(model);
			registeHttpInfos(model, request);
			exposeHelpers(model, request);
			doRender(model, request, response);
		} finally {
			requestContextHolder.releaseModel();
		}
	}

	/**
	 * add http headers into model
	 *
	 * @param request
	 */
	private void registeHttpInfos(Map<String, Object> model,
								  HttpServletRequest request) {
		model.put("HttpHeader", WebUtils.getHttpHeaders(request));
		model.put("ctx", request.getContextPath());
	}

	/**
	 * override the implementation of achieving session attributes
	 */
	@Override
	protected SimpleHash buildTemplateModel(Map<String, Object> model,
											HttpServletRequest request, HttpServletResponse response) {
		SimpleHash retVal = super.buildTemplateModel(model, request, response);
		Map<String, Object> bindedModel = requestContextHolder.getModel();
		//retVal.put(FreemarkerServlet.KEY_SESSION, buildSessionModel(request, response));
		try {
			bindedModel.put(FreemarkerServlet.KEY_SESSION, retVal.get(FreemarkerServlet.KEY_SESSION));
			bindedModel.put(FreemarkerServlet.KEY_JSP_TAGLIBS, retVal.get(FreemarkerServlet.KEY_JSP_TAGLIBS));
			bindedModel.put(FreemarkerServlet.KEY_APPLICATION, retVal.get(FreemarkerServlet.KEY_APPLICATION));
		} catch (TemplateModelException e) {
			throw new SystemException(e.getMessage(), e);
		}
		bindedModel.put(FreemarkerServlet.KEY_REQUEST, new HttpRequestHashModel(request, response, getObjectWrapper()));
		bindedModel.put(FreemarkerServlet.KEY_REQUEST_PARAMETERS, new HttpRequestParametersHashModel(request));
		return retVal;
	}

	@Override
	protected void exposeHelpers(Map<String, Object> model,
								 HttpServletRequest request) throws Exception {
		super.exposeHelpers(model, request);
	}

	@Override
	protected boolean isUrlRequired() {
		return false;
	}

	public Template loadTemplate(String fullTemplatePath)
			throws IOException {
		return getTemplate(fullTemplatePath, RequestContextUtils.getLocale(WebUtils.getHttpRequest()));
	}
}