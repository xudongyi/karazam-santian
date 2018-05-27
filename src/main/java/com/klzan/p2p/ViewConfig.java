package com.klzan.p2p;

import com.klzan.core.freemarker.DefaultFreeMarkerConfigurer;
import com.klzan.core.freemarker.DefaultFreemarkerView;
import com.klzan.core.freemarker.DefaultFreemarkerViewResolver;
import com.klzan.core.freemarker.function.FunctionName;
import com.klzan.core.freemarker.function.StaticFunction;
import com.klzan.core.freemarker.function.UrlFunction;
import com.klzan.core.freemarker.tags.*;
import com.klzan.core.freemarker.templateloader.MasterTemplateLoader;
import com.klzan.core.setting.FreemarkerSetting;
import com.klzan.core.util.PropertiesUtils;
import com.klzan.p2p.freemarker.directive.LinksDirective;
import com.klzan.p2p.freemarker.directive.PostsContentsDirective;
import com.klzan.p2p.freemarker.directive.PostsTaxonomyDirective;
import com.klzan.p2p.freemarker.method.*;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.plugin.pay.common.PayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by suhao on 2017/3/15.
 */
@Configuration
public class ViewConfig {
    private static final Logger logger = LoggerFactory.getLogger(ViewConfig.class);
    @Value("#{servletContext.contextPath}")
    protected String ctx;

    @Autowired
    private FreemarkerSetting freemarkerSetting;

    @Autowired
    private LinksDirective linksDirective;

    @Autowired
    private PostsTaxonomyDirective postsTaxonomyDirective;

    @Autowired
    private PostsContentsDirective postsContentsDirective;

    @Autowired
    private UserInfoMethod userInfoMethod;

    @Autowired
    private SecrecyMethod secrecyMethod;

    @Autowired
    private AbbreviateMethod abbreviateMethod;

    @Autowired
    private NumToRMBMethod numToRMBMethod;

    @Autowired
    private UserMetaMethod userMetaMethod;

    @Autowired
    private SettingUtils settingUtils;

    @Autowired
    private PayUtils payUtils;

    @Bean
    public MasterTemplateLoader masterTemplateLoader() {
        return new MasterTemplateLoader();
    }

    @Bean
    public DefaultFreemarkerViewResolver freeMarkerViewResolver() {
        DefaultFreemarkerViewResolver freeMarkerViewResolver = new DefaultFreemarkerViewResolver();
        freeMarkerViewResolver.setCache(Boolean.TRUE);
        freeMarkerViewResolver.setPrefix(freemarkerSetting.getPrefix());
        freeMarkerViewResolver.setSuffix(freemarkerSetting.getSuffix());
        freeMarkerViewResolver.setViewClass(DefaultFreemarkerView.class);
        freeMarkerViewResolver.setContentType("text/html; charset=UTF-8");
        freeMarkerViewResolver.setExposeSpringMacroHelpers(true);
        return freeMarkerViewResolver;
    }

    @Bean
    public DefaultFreeMarkerConfigurer freeMarkerConfigurer() {
        DefaultFreeMarkerConfigurer freeMarkerConfigurer = new DefaultFreeMarkerConfigurer();
        String[] templateLoaderPaths = freemarkerSetting.getLoaderPaths().split(",");
        freeMarkerConfigurer.setTemplateLoaderPaths(templateLoaderPaths);
        Properties props = new Properties();
        props.put("default_encoding", freemarkerSetting.getDefaultEncoding());
        props.put("url_escaping_charset", freemarkerSetting.getUrlEscapingCharset());
        props.put("locale", freemarkerSetting.getLocale());
        props.put("template_update_delay", freemarkerSetting.getTemplateUpdateDelay());
        props.put("tag_syntax", freemarkerSetting.getTagSyntax());
        props.put("whitespace_stripping", freemarkerSetting.getWhitespaceStripping());
        props.put("classic_compatible", freemarkerSetting.getClassicCompatible());
        props.put("number_format", freemarkerSetting.getNumberFormat());
        props.put("boolean_format", freemarkerSetting.getBooleanFormat());
        props.put("datetime_format", freemarkerSetting.getDatetimeFormat());
        props.put("date_format", freemarkerSetting.getDateFormat());
        props.put("time_format", freemarkerSetting.getTimeFormat());
        props.put("object_wrapper", freemarkerSetting.getObjectWrapper());
        props.put("output_encoding", freemarkerSetting.getOutputEncoding());
        props.put("template_exception_handler", freemarkerSetting.getTemplateExceptionHandler());
        freeMarkerConfigurer.setFreemarkerSettings(props);
        Map<String, Object> helpers = new HashMap<>();
        registerHelpers(helpers);
        freeMarkerConfigurer.setFreemarkerVariables(helpers);
        return freeMarkerConfigurer;
    }

    protected void registerHelpers(Map<String, Object> helpers) {
        helpers.put(TagName.TAG_JS.getTagName(), jsTag());
        helpers.put(TagName.TAG_CSS.getTagName(), cssTag());
        helpers.put(TagName.TAG_IMAGE.getTagName(), imageTag());
        helpers.put(TagName.TAG_MASTER.getTagName(), masterTag());
        helpers.put(TagName.NESTED_STYLESHEET.getTagName(), nestedStylesheetTag());
        helpers.put(TagName.NESTED_SCRIPT.getTagName(), nestedScriptTag());
        helpers.put(FunctionName.URL_FUNCTION.getFunctionName(), urlFunction());
        helpers.put(FunctionName.STATIC_FUNCTION.getFunctionName(), staticFunction());
        helpers.put("ctx", ctx);
        helpers.put("dfsUrl", PropertiesUtils.getString("url.file_server"));
        helpers.put("setting", settingUtils);
        helpers.put("payUtil", payUtils);
        helpers.put("k_links", linksDirective);
        helpers.put("k_taxonomy", postsTaxonomyDirective);
        helpers.put("k_contents", postsContentsDirective);
        helpers.put("k_userinfo", userInfoMethod);
        helpers.put("secrecy", secrecyMethod);
        helpers.put("abbreviate", abbreviateMethod);
        helpers.put("userMeta", userMetaMethod);
        helpers.put("numToRMB", numToRMBMethod);
    }

    @Bean
    public NestedStylesheetTag nestedStylesheetTag() {
        return new NestedStylesheetTag();
    }

    @Bean
    public NestedScriptTag nestedScriptTag() {
        return new NestedScriptTag();
    }

    @Bean
    public StaticFunction staticFunction() {
        return new StaticFunction();
    }

    @Bean
    public UrlFunction urlFunction() {
        return new UrlFunction();
    }

    @Bean
    public JSTag jsTag() {
        return new JSTag();
    }

    @Bean
    public CSSTag cssTag() {
        return new CSSTag();
    }

    @Bean
    public ImageTag imageTag() {
        return new ImageTag();
    }

    @Bean
    public MasterTag masterTag() {
        return new MasterTag();
    }

}
