/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.core.util;

import com.klzan.core.exception.SystemException;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.ParamsFilter;
import com.klzan.core.page.ParamsFilter.MatchType;
import com.klzan.core.page.Sort;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HQLUtils {

    /**
     * @param hql
     * @param criteria
     * @return
     */
    public static String buildSortStmt(String hql, PageCriteria criteria) {
        if (criteria == null) {
            return hql;
        }

//        String sortName = StringUtils.isBlank(criteria.getSortName()) ? criteria.getSort() : criteria.getSortName();
//        if (StringUtils.isBlank(sortName)) {
//            return new StringBuilder(hql).toString();
//        }
//        String order = StringUtils.isBlank(criteria.getOrder()) ? "asc" : criteria.getOrder();
//        return new StringBuilder(hql).append(" ORDER BY ")
//                .append(sortName)
//                .append(" ")
//                .append(order)
//                .toString();

        String sortName = StringUtils.isBlank(criteria.getSortName()) ? criteria.getSort() : criteria.getSortName();
        if (StringUtils.isBlank(sortName) && (criteria.getSorts()==null || criteria.getSorts().size()==0)) {
            return new StringBuilder(hql).toString();
        }

        StringBuilder newHql = new StringBuilder(hql).append(" ORDER BY ");
        if (!StringUtils.isBlank(sortName)){
            String order = StringUtils.isBlank(criteria.getOrder()) ? "asc" : criteria.getOrder();
            newHql.append(sortName)
                    .append(" ")
                    .append(order);
        }

        StringBuilder sortHql = new StringBuilder("");
        if(!(criteria.getSorts()==null || criteria.getSorts().size()==0)){
            for(Sort sort : criteria.getSorts()){
                sortHql.append(",")
                        .append(sort.getSortName())
                        .append(" ");
                if(StringUtils.isBlank(sort.getOrder())){
                    sortHql.append("asc");
                }else {
                    sortHql.append(sort.getOrder());
                }
            }
            if(StringUtils.isBlank(sortName)){
                sortHql.substring(1,sortHql.length());
            }
        }

        return newHql.append(sortHql).toString();
    }

    public static String buildPageFiltersStmt(String hql, PageCriteria criteria, List<ParamsFilter> params) {
        if (criteria == null) {
            return hql;
        }

        // 构建条件查询
        StringBuilder paramsFilterSB = new StringBuilder();
        if (!StringUtils.containsIgnoreCase(hql, " WHERE ")) {
            paramsFilterSB.append(" WHERE 1=1 ");
        }
        for (ParamsFilter filter : params) {
            buildFilters(paramsFilterSB, filter);
        }

//        String sortName = StringUtils.isBlank(criteria.getSortName()) ? criteria.getSort() : criteria.getSortName();
//        if (StringUtils.isBlank(sortName)) {
//            return new StringBuilder(hql).append(paramsFilterSB).toString();
//        }
//        String order = StringUtils.isBlank(criteria.getOrder()) ? "asc" : criteria.getOrder();
//        return new StringBuilder(hql).append(paramsFilterSB).append(" ORDER BY ")
//                .append(sortName)
//                .append(" ")
//                .append(order)
//                .toString();

        String sortName = StringUtils.isBlank(criteria.getSortName()) ? criteria.getSort() : criteria.getSortName();
        if (StringUtils.isBlank(sortName) && (criteria.getSorts()==null || criteria.getSorts().size()==0)) {
            return new StringBuilder(hql).append(paramsFilterSB).toString();
        }

        StringBuilder newHql = new StringBuilder(hql).append(paramsFilterSB).append(" ORDER BY ");
        if (!StringUtils.isBlank(sortName)){
            String order = StringUtils.isBlank(criteria.getOrder()) ? "asc" : criteria.getOrder();
            newHql.append(sortName)
                    .append(" ")
                    .append(order);
        }

        StringBuilder sortHql = new StringBuilder("");
        if(!(criteria.getSorts()==null || criteria.getSorts().size()==0)){
            int i = 0;
            for(Sort sort : criteria.getSorts()){
                if (i == 0 && StringUtils.isNotBlank(sortName)) {
                    sortHql.append(",");
                }
                if (i > 0) {
                    sortHql.append(",");
                }
                sortHql.append(sort.getSortName())
                        .append(" ");
                if(StringUtils.isBlank(sort.getOrder())){
                    sortHql.append("asc");
                }else {
                    sortHql.append(sort.getOrder());
                }
                i++;
            }
            if(StringUtils.isBlank(sortName)){
                sortHql.substring(1,sortHql.length());
            }
        }

        return newHql.append(sortHql).toString();
    }

    public static String buildListFiltersStmt(String hql, List<ParamsFilter> params) {
        // 构建条件查询
        StringBuilder paramsFilterSB = new StringBuilder();
        if (!StringUtils.containsIgnoreCase(hql, " WHERE ")) {
            paramsFilterSB.append(" WHERE 1=1 ");
        }
        for (ParamsFilter filter : params) {
            buildFilters(paramsFilterSB, filter);
        }

        return new StringBuilder(hql).append(paramsFilterSB).toString();
    }

    public static Map<String, Object> buildFiltersMap(List<ParamsFilter> params) {
        Map<String, Object> paraMap = new HashMap<>();
        for (ParamsFilter param : params) {
            boolean hasMultiProperties = param.hasMultiProperties();
            if (hasMultiProperties) {
                int i = 0;
                for (String paramName : param.getParamsNames()) {
                    if (param.getMatchType() == MatchType.LIKE) {
                        paraMap.put(paramName + i, "%" + param.getMatchValue() + "%");
                    } else {
                        paraMap.put(paramName + i, param.getMatchValue());
                    }
                    i++;
                }
            } else {
                if (param.getMatchType() == MatchType.LIKE) {
                    paraMap.put(param.getParamsName(), "%" + param.getMatchValue() + "%");
                } else {
                    paraMap.put(param.getParamsName(), param.getMatchValue());
                }
            }
        }
        return paraMap;
    }

    protected static StringBuilder buildFilters(StringBuilder sql, ParamsFilter filter) {
        boolean hasMultiProperties = filter.hasMultiProperties();
        MatchType matchType = filter.getMatchType();
        if (hasMultiProperties) {
            sql.append(" ( ");
            int i = 0;
            for (String paramName : filter.getParamsNames()) {
                buildFiltersHql(sql, paramName + 0, matchType, true);
                i++;
            }
            sql.append(" ) ").replace(7, 10, "");
        } else {
            String paramName = filter.getParamsName();
            buildFiltersHql(sql, paramName, matchType, false);
        }

        return sql;
    }

    protected static StringBuilder buildFiltersHql(StringBuilder hql, final String paramName, final MatchType matchType, boolean isOR) {
        Assert.hasText(paramName, "paramName不能为空");
        String tParamName = StringUtils.substring(paramName, 0, StringUtils.indexOf(paramName, "_filterindex_"));
        tParamName = StringUtils.underlineToCamelhump(tParamName);
        String criteria = isOR ? " OR " : " AND ";
        // 根据MatchType构造SQL或HQL
        hql.append(criteria).append(tParamName);
        switch (matchType) {
            case EQ:
                hql.append(" = :");
                break;
            case NE:
                hql.append(" <> :");
                break;
            case LIKE:
                hql.append(" like :");
                break;
            case LE:
                hql.append(" <= :");
                break;
            case LT:
                hql.append(" < :");
                break;
            case GE:
                hql.append(" >= :");
                break;
            case GT:
                hql.append(" > :");
                break;
            default:
                throw new SystemException("hql matchType is error");
        }
        hql.append(paramName).append(" ");
        return hql;
    }

    public static String buildCountStmt(String hql) {
//        Matcher matcher = Pattern.compile("from", Pattern.CASE_INSENSITIVE).matcher(hql);
//        int end = 0;
//        if (matcher.find()) {
//            end = matcher.end();
//        }
//        return "SELECT COUNT(*) FROM " + hql.substring(end, hql.length());
        return "SELECT COUNT(1) FROM (" + hql + ") aliasCount";
    }

    public static String buildNamedParameters(String alais, Map<String, Object> queryParamsMap) {
        if (queryParamsMap == null || queryParamsMap.size() == 0) return "";
        StringBuilder sqlBuilder = new StringBuilder(" ");
        String entityAlais = StringUtils.isBlank(alais) ? "" : alais + ".";
        for (Map.Entry<String, Object> entry : queryParamsMap.entrySet()) {
            String key = entry.getKey();
            sqlBuilder.append(" AND ").append(entityAlais).append(key).append("=:").append(key);
        }
        return sqlBuilder.toString();
    }

    public static String buildEnclosedCountStmt(String hql) {
        return new StringBuilder("SELECT COUNT(*) FROM (")
                .append(hql)
                .append(") o").toString();
    }

}
