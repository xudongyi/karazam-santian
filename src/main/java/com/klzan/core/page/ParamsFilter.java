package com.klzan.core.page;

import com.klzan.core.util.ConvertUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by suhao on 2016/11/19.
 */
public class ParamsFilter {
    /**
     * 多个属性间OR关系的分隔符.
     */
    public static final String OR_SEPARATOR = "_OR_";

    /**
     * 属性比较类型.
     */
    public enum MatchType {
        ///** 等于 */
        EQ,
        ///** 不等于 */
        NE,
        ///** 相似 */
        LIKE,
        ///** 小于 */
        LT,
        ///** 大于 */
        GT,
        ///** 小于等于 */
        LE,
        ///** 大于等于 */
        GE
    }

    /**
     * 属性数据类型.
     */
    public enum ParamsType {
        S(String.class), I(Integer.class), L(Long.class), M(BigDecimal.class), N(Double.class), D(Date.class), B(Boolean.class);

        private Class<?> clazz;

        ParamsType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getValue() {
            return clazz;
        }
    }

    private MatchType matchType = null;
    private Object matchValue = null;

    private Class<?> paramsClass = null;
    private String[] paramsNames = null;

    /**
     * @param filterName 比较属性字符串,含待比较的比较类型、属性值类型及属性列表.
     *                   eg. LIKES_NAME_OR_LOGIN_NAME
     * @param value      待比较的值.
     */
    public ParamsFilter(final String filterName, final String value) {

        String firstPart = StringUtils.substringBefore(filterName, "_");
        String matchTypeCode = StringUtils.substring(firstPart, 0, firstPart.length() - 1);
        String propertyTypeCode = StringUtils.substring(firstPart, firstPart.length() - 1, firstPart.length());

        try {
            matchType = Enum.valueOf(MatchType.class, matchTypeCode);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
        }

        try {
            paramsClass = Enum.valueOf(ParamsType.class, propertyTypeCode).getValue();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
        }

        String propertyNameStr = StringUtils.substringAfter(filterName, "_");
        Assert.isTrue(StringUtils.isNotBlank(propertyNameStr), "filter名称" + filterName + "没有按规则编写,无法得到属性名称.");
        paramsNames = StringUtils.splitByWholeSeparator(propertyNameStr, OR_SEPARATOR);

        this.matchValue = ConvertUtils.convertStringToObject(value, paramsClass);
    }

    public static ParamsFilter addFilter(final String paramName, final MatchType matchType, final Object value) {
        Class<?> valueClass = value.getClass();
        String filterName = new StringBuilder(matchType.name()).append(getValueClassType(valueClass)).append("_").append(paramName).toString();
        return new ParamsFilter(filterName + "_filterindex_" + 0, String.valueOf(value));
    }

    public static String getValueClassType(Class<?> clazz) {
        ParamsType[] paramsTypes = ParamsType.values();
        for (ParamsType paramsType : paramsTypes) {
            if (paramsType.getValue() == clazz) {
                return paramsType.name();
            }
        }
        throw new IllegalArgumentException("参数类型" + clazz + "没有按规则编写,无法得到参数类型.");
    }

    public static void buildFromHttpRequest(final HttpServletRequest request, PageCriteria criteria) {
        criteria.setParams(buildFromHttpRequest(request, "filter"));
    }

    /**
     * 从HttpRequest中创建PropertyFilter列表, 默认Filter属性名前缀为filter.
     *
     * @see #buildFromHttpRequest(HttpServletRequest, String)
     */
    public static List<ParamsFilter> buildFromHttpRequest(final HttpServletRequest request) {
        return buildFromHttpRequest(request, "filter");
    }

    /**
     * 从HttpRequest中创建PropertyFilter列表
     * PropertyFilter命名规则为Filter属性前缀_比较类型属性类型_属性名.
     *
     * eg.
     * filter_EQS_name
     * filter_LIKES_name_OR_email
     */
    public static List<ParamsFilter> buildFromHttpRequest(final HttpServletRequest request, final String filterPrefix) {
        List<ParamsFilter> filterList = new ArrayList<ParamsFilter>();

        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, filterPrefix + "_");

        //分析参数Map,ParamsFilter
        int i = 0;
        for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
            String filterName = entry.getKey();
            String value = (String) entry.getValue();
            //如果value值为空,则忽略此filter.
            if (StringUtils.isNotBlank(value)) {
                ParamsFilter filter = new ParamsFilter(filterName + "_filterindex_" + i, value);
                filterList.add(filter);
            }
            i++;
        }

        return filterList;
    }

    /**
     * 获取比较值的类型.
     */
    public Class<?> getParamsClass() {
        return paramsClass;
    }

    /**
     * 获取比较方式.
     */
    public MatchType getMatchType() {
        return matchType;
    }

    /**
     * 获取比较值.
     */
    public Object getMatchValue() {
        return matchValue;
    }

    /**
     * 获取比较属性名称列表.
     */
    public String[] getParamsNames() {
        return paramsNames;
    }

    /**
     * 获取唯一的比较属性名称.
     */
    public String getParamsName() {
        Assert.isTrue(paramsNames.length == 1, "There are not only one property in this filter.");
        return paramsNames[0];
    }

    /**
     * 是否比较多个属性.
     */
    public boolean hasMultiProperties() {
        return (paramsNames.length > 1);
    }
}
