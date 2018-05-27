package com.klzan.core.freemarker.tags;

import com.klzan.core.setting.WebSetting;
import com.klzan.core.util.URLBuilder;
import com.klzan.core.util.WebUtils;
import freemarker.template.TemplateModelException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

public class CDNTagSupports extends TagSupport {

	@Inject
	protected WebSetting websetting;

	public CDNTagSupports(WebSetting websetting) {
		this.websetting = websetting;
	}

	public CDNTagSupports() {
	}

	protected String buildResourceUrl(String url, String resourceDir) {
		Assert.hasText(url, String.format("%s can not be empty", "url"));
		if (!url.startsWith("/")) {
			url = String.format("%s/%s", resourceDir, url);
		}
		return constructCDNURL(constructVersionUrl(url));
	}

	String buildMultipleResourceTags(String srcKey, String resourceDir,
									 String tagTemplate, Map<String, Object> params) throws IOException,
			TemplateModelException {
		StringBuilder builder = new StringBuilder();
		String srcValue = getRequiredStringParam(params, srcKey);
		String[] srcItems = srcValue.split(",");
		for (int i = 0; i < srcItems.length; i++) {
			String src = srcItems[i].trim();
			if (!src.startsWith("/")) {
				src = String.format("%s/%s", resourceDir, src);
			}
			src = constructCDNURL(constructVersionUrl(src));
			String code = String.format(tagTemplate, src);
			builder.append(code).append("\n");
		}
		return builder.toString();
	}

	private String constructVersionUrl(String src) {
		String version = WebUtils.getWebSetting().getVersion();
		char connector = src.indexOf('?') < 0 ? '?' : '&';
		StringBuilder builder = new StringBuilder(src);
		builder.append(connector).append("version=").append(version);
		return builder.toString();
	}

	protected String constructCDNURL(String url) {
		return constructLocalURL(url);
	}

	private String constructLocalURL(String url) {
		return URLBuilder.build().setContext(WebUtils.getHttpRequest().getContextPath()).constructRelativeURL(url);
	}

	/**
	 * @param url
	 * @param scheme
	 * @return
	 */
	public String constructURL(String url, String scheme) {
		if(!StringUtils.hasText(scheme)) {
			return constructLocalURL(url);
		}
		return constructLocalURL(url);
	}

}
