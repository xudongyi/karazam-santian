package com.klzan.core.util;

import java.util.HashMap;
import java.util.Map;

public class URLBuilder {

	String contextPath;
	String serverName;
	String url;
	int httpsPort;
	int httpPort;
	Map<String, String> parameters;

	private URLBuilder() {
	}

	public static URLBuilder build() {
		return new URLBuilder();
	}

	public static URLBuilder build(String host, String contextPath, int httpPort, int httpsPort) {
		return new URLBuilder(host, contextPath, httpPort, httpsPort);
	}

	public static URLBuilder build(String host, String contextPath) {
		return new URLBuilder(host, contextPath, 80, 443);
	}

	public static URLBuilder build(String host) {
		return new URLBuilder(host, "/", 80, 443);
	}

	private URLBuilder(String host, String contextPath, int httpPort, int httpsPort) {
		this.serverName = host;
		this.httpPort = httpPort;
		this.httpsPort = httpsPort;
		this.contextPath = contextPath;
	}

	/**
	 * building relative url according to the relative URL
	 * @param relativeURL
	 * @return
	 */
	public String constructRelativeURL(String relativeURL) {
		StringBuilder builder = new StringBuilder();
		buildRelativeURL(builder, relativeURL);
		return builder.toString();
	}

	/**
	 * building absolute url according to the absolute URL
	 * @param scheme
	 * @param url
	 * @return
	 */
	public String constructAbsoluteURL(String scheme, String url) {
		if(url == null) url = "/";
		StringBuilder builder = new StringBuilder();
		if(!url.startsWith("http")) {
			if (!url.startsWith("/"))
				throw new IllegalArgumentException(
						"relative url must start with '/' to construct absolute url");
			buildURLPrefix(builder, scheme);
			buildRelativeURL(builder, url);
		} else {
			builder.append(url);
		}
		buildURLParameters(builder);
		return builder.toString();
	}

	public String constructAbsoluteURL(String scheme) {
		return constructAbsoluteURL(scheme, url);
	}

	public String constructAbsoluteURL() {
		return constructAbsoluteURL("http", url);
	}

	/**
	 * building relative url according to the logicalURL
	 * @return
	 */
	public String buildRelativeURL() {
		StringBuilder builder = new StringBuilder();
		buildRelativeURL(builder, url);
		buildURLParameters(builder);
		return builder.toString();
	}

	void buildRelativeURL(StringBuilder builder, String url) {
		if (url.startsWith("/") && contextPath != null) {
			String context = getAbsoluteContext(contextPath);
			builder.append(context).append(url);
		} else {
			builder.append(url);
		}
	}

	private String getAbsoluteContext(String servletContextPath) {
		if ("/".equals(servletContextPath))
			return "";
		return servletContextPath;
	}

	private void buildURLPrefix(StringBuilder builder, String scheme) {
		String schemaInLowerCase = scheme.toLowerCase();
		builder.append(schemaInLowerCase).append("://").append(serverName);

		if ("http".equals(schemaInLowerCase)
				&& httpPort != 80) {
			builder.append(":").append(httpPort);
		} else if ("https".equals(schemaInLowerCase)
				&& httpsPort != 443) {
			builder.append(":").append(httpsPort);
		}
	}

	/**
	 * building url parameters according to the parameters
	 * @param builder
	 */
	private void buildURLParameters(StringBuilder builder) {
		if(parameters == null) return;
		for(String parameterName : parameters.keySet()) {
			String val = parameters.get(parameterName);
			if(StringUtils.isNotBlank(val)) {
				if(builder.lastIndexOf("?") > 0) {
					builder.append("&").append(parameterName).append("=").append(val);
				} else {
					builder.append("?").append(parameterName).append("=").append(val);
				}
			}
		}
	}

	public URLBuilder setContext(String contextPath) {
		this.contextPath = contextPath;
		return this;
	}

	public URLBuilder setServerInfo(String serverName, int httpPort, int httpsPort) {
		this.serverName = serverName;
		this.httpPort = httpPort;
		this.httpsPort = httpsPort;
		return this;
	}

	public URLBuilder setURL(String url) {
		this.url = url;
		return this;
	}

	public URLBuilder setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
		return this;
	}

	/**
	 * add url parameter
	 * @param name
	 * @param value
	 */
	public URLBuilder addParameter(String name, String value) {
		if(parameters == null) parameters = new HashMap<>();
		parameters.put(name, value);
		return this;
	}

	@Override
	public String toString() {
		return this.constructAbsoluteURL();
	}
	
}