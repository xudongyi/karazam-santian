package com.klzan.core.setting;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by suhao on 2015/9/25.
 */
public class FreemarkerSetting {
    @Value("${template.number_format}")
    private String numberFormat;
    @Value("${template.boolean_format}")
    private String booleanFormat;
    @Value("${template.datetime_format}")
    private String datetimeFormat;
    @Value("${template.date_format}")
    private String dateFormat;
    @Value("${template.time_format}")
    private String timeFormat;
    @Value("${template.loader_path}")
    private String loaderPaths;
    @Value("${template.prefix}")
    private String prefix;
    @Value("${template.suffix}")
    private String suffix;
    @Value("${template.default_encoding}")
    private String defaultEncoding;
    @Value("${template.url_escaping_charset}")
    private String urlEscapingCharset;
    @Value("${template.locale}")
    private String locale;
    @Value("${template.template_update_delay}")
    private String templateUpdateDelay;
    @Value("${template.tag_syntax}")
    private String tagSyntax;
    @Value("${template.whitespace_stripping}")
    private String whitespaceStripping;
    @Value("${template.classic_compatible}")
    private String classicCompatible;
    @Value("${template.object_wrapper}")
    private String objectWrapper;
    @Value("${template.output_encoding}")
    private String outputEncoding;
    @Value("${template.template_exception_handler}")
    private String templateExceptionHandler;

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    public String getBooleanFormat() {
        return booleanFormat;
    }

    public void setBooleanFormat(String booleanFormat) {
        this.booleanFormat = booleanFormat;
    }

    public String getDatetimeFormat() {
        return datetimeFormat;
    }

    public void setDatetimeFormat(String datetimeFormat) {
        this.datetimeFormat = datetimeFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getLoaderPaths() {
        return loaderPaths;
    }

    public void setLoaderPaths(String loaderPaths) {
        this.loaderPaths = loaderPaths;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public String getUrlEscapingCharset() {
        return urlEscapingCharset;
    }

    public void setUrlEscapingCharset(String urlEscapingCharset) {
        this.urlEscapingCharset = urlEscapingCharset;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTemplateUpdateDelay() {
        return templateUpdateDelay;
    }

    public void setTemplateUpdateDelay(String templateUpdateDelay) {
        this.templateUpdateDelay = templateUpdateDelay;
    }

    public String getTagSyntax() {
        return tagSyntax;
    }

    public void setTagSyntax(String tagSyntax) {
        this.tagSyntax = tagSyntax;
    }

    public String getWhitespaceStripping() {
        return whitespaceStripping;
    }

    public void setWhitespaceStripping(String whitespaceStripping) {
        this.whitespaceStripping = whitespaceStripping;
    }

    public String getClassicCompatible() {
        return classicCompatible;
    }

    public void setClassicCompatible(String classicCompatible) {
        this.classicCompatible = classicCompatible;
    }

    public String getObjectWrapper() {
        return objectWrapper;
    }

    public void setObjectWrapper(String objectWrapper) {
        this.objectWrapper = objectWrapper;
    }

    public String getOutputEncoding() {
        return outputEncoding;
    }

    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }

    public String getTemplateExceptionHandler() {
        return templateExceptionHandler;
    }

    public void setTemplateExceptionHandler(String templateExceptionHandler) {
        this.templateExceptionHandler = templateExceptionHandler;
    }
}
