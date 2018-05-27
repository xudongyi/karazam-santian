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

public class SQLUtils {

    /**
     * @param sql
     * @param criteria
     * @return
     */
    public static String buildSortStmt(String sql, PageCriteria criteria) {
        if (criteria == null) {
            return sql;
        }

//        String sortName = StringUtils.isBlank(criteria.getSortName()) ? criteria.getSort() : criteria.getSortName();
//        if (StringUtils.isBlank(sortName)) {
//            return new StringBuilder(sql).toString();
//        }
//        String order = StringUtils.isBlank(criteria.getOrder()) ? "asc" : criteria.getOrder();
//        return new StringBuilder(sql).append(" ORDER BY ")
//                .append(StringUtils.camelhumpToUnderline(sortName))
//                .append(" ")
//                .append(order)
//                .toString();

        String sortName = StringUtils.isBlank(criteria.getSortName()) ? criteria.getSort() : criteria.getSortName();
        if (StringUtils.isBlank(sortName) && (criteria.getSorts()==null || criteria.getSorts().size()==0)) {
            return new StringBuilder(sql).toString();
        }

        StringBuilder newSql = new StringBuilder(sql).append(" ORDER BY ");
        if (!StringUtils.isBlank(sortName)){
            String order = StringUtils.isBlank(criteria.getOrder()) ? "asc" : criteria.getOrder();
            newSql.append(sortName)
                    .append(" ")
                    .append(order);
        }

        StringBuilder sortSql = new StringBuilder("");
        if(!(criteria.getSorts()==null || criteria.getSorts().size()==0)){
            for(Sort sort : criteria.getSorts()){
                sortSql.append(",")
                        .append(sort.getSortName())
                        .append(" ");
                if(StringUtils.isBlank(sort.getOrder())){
                    sortSql.append("asc");
                }else {
                    sortSql.append(sort.getOrder());
                }
            }
            if(StringUtils.isBlank(sortName)){
                sortSql.substring(1,sortSql.length());
            }
        }

        return newSql.append(sortSql).toString();
    }

    public static String buildPageFiltersStmt(String sql, PageCriteria criteria, List<ParamsFilter> params) {
        if (criteria == null) {
            return sql;
        }

        // 构建条件查询
        StringBuilder paramsFilterSB = new StringBuilder();
        if (!StringUtils.containsIgnoreCase(sql, " WHERE ")) {
            paramsFilterSB.append(" WHERE 1=1 ");
        }
        for (ParamsFilter filter : params) {
            buildFilters(paramsFilterSB, filter);
        }

//        String sortName = StringUtils.isBlank(criteria.getSortName()) ? criteria.getSort() : criteria.getSortName();
//        if (StringUtils.isBlank(sortName)) {
//            return new StringBuilder(sql).append(paramsFilterSB).toString();
//        }
//        String order = StringUtils.isBlank(criteria.getOrder()) ? "asc" : criteria.getOrder();
//        return new StringBuilder(sql).append(paramsFilterSB).append(" ORDER BY ")
//                .append(StringUtils.camelhumpToUnderline(sortName))
//                .append(" ")
//                .append(order)
//                .toString();

        String sortName = StringUtils.isBlank(criteria.getSortName()) ? criteria.getSort() : criteria.getSortName();
        if (StringUtils.isBlank(sortName) && (criteria.getSorts()==null || criteria.getSorts().size()==0)) {
            return new StringBuilder(sql).append(paramsFilterSB).toString();
        }

        StringBuilder newSql = new StringBuilder(sql).append(paramsFilterSB).append(" ORDER BY ");
        if (!StringUtils.isBlank(sortName)){
            String order = StringUtils.isBlank(criteria.getOrder()) ? "asc" : criteria.getOrder();
            newSql.append(sortName)
                    .append(" ")
                    .append(order);
        }

        StringBuilder sortSql = new StringBuilder("");
        if(!(criteria.getSorts()==null || criteria.getSorts().size()==0)){
            for(Sort sort : criteria.getSorts()){
                sortSql.append(",")
                        .append(sort.getSortName())
                        .append(" ");
                if(StringUtils.isBlank(sort.getOrder())){
                    sortSql.append("asc");
                }else {
                    sortSql.append(sort.getOrder());
                }
            }
            if(StringUtils.isBlank(sortName)){
                sortSql.substring(1,sortSql.length());
            }
        }

        return newSql.append(sortSql).toString();
    }

    public static String buildListFiltersStmt(String sql, List<ParamsFilter> params) {
        // 构建条件查询
        StringBuilder paramsFilterSB = new StringBuilder();
        if (!StringUtils.containsIgnoreCase(sql, " WHERE ")) {
            paramsFilterSB.append(" WHERE 1=1 ");
        }
        for (ParamsFilter filter : params) {
            buildFilters(paramsFilterSB, filter);
        }

        return new StringBuilder(sql).append(paramsFilterSB).toString();
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
            sql.append(" AND ( ");
            int i = 0;
            for (String paramName : filter.getParamsNames()) {
                buildFiltersSql(sql, paramName + i, matchType, true);
                i++;
            }
            sql.append(" ) ").replace(7, 10, "");
        } else {
            String paramName = filter.getParamsName();
            buildFiltersSql(sql, paramName, matchType, false);
        }

        return sql;
    }

    private static void buildFiltersSql(StringBuilder sql, String paramName, MatchType matchType, boolean isOR) {
        Assert.hasText(paramName, "paramName不能为空");
        String tParamName = StringUtils.substring(paramName, 0, StringUtils.indexOf(paramName, "_filterindex_"));
        tParamName = StringUtils.camelhumpToUnderline(tParamName);
        String criteria = isOR ? " OR " : " AND ";
        // 根据MatchType构造SQL或HQL
        sql.append(criteria).append(tParamName);
        switch (matchType) {
            case EQ:
                sql.append(" = :");
                break;
            case NE:
                sql.append(" <> :");
                break;
            case LIKE:
                sql.append(" like :");
                break;
            case LE:
                sql.append(" <= :");
                break;
            case LT:
                sql.append(" < :");
                break;
            case GE:
                sql.append(" >= :");
                break;
            case GT:
                sql.append(" > :");
                break;
            default:
                throw new SystemException("sql matchType is error");
        }
        sql.append(paramName).append(" ");
    }

    public static String buildCountStmt(String sql) {
//        Matcher matcher = Pattern.compile("from", Pattern.CASE_INSENSITIVE).matcher(sql);
//        int end = 0;
//        if (matcher.find()) {
//            end = matcher.end();
//        }
//        return "SELECT COUNT(*) FROM " + sql.substring(end, sql.length());
        return "SELECT COUNT(*) FROM (" + sql + ") aliasCount";
    }

    public static String buildNamedParameters(String alais,
                                              Map<String, Object> queryParamsMap) {
        if (queryParamsMap == null || queryParamsMap.size() == 0) return "";
        StringBuilder sqlBuilder = new StringBuilder(" ");
        String entityAlais = StringUtils.isBlank(alais) ? "" : alais + ".";
        for (Map.Entry<String, Object> entry : queryParamsMap.entrySet()) {
            String key = entry.getKey();
            sqlBuilder.append(" AND ").append(entityAlais).append(key).append("=:").append(key);
        }
        return sqlBuilder.toString();
    }

    public static String buildEnclosedCountStmt(String sql) {
        return new StringBuilder("SELECT COUNT(*) FROM (")
                .append(sql)
                .append(") o").toString();
    }

}
