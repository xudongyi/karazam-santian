package com.klzan.core.persist.mybatis.pagehelper.dialect.helper;

import com.klzan.core.persist.mybatis.pagehelper.Page;
import com.klzan.core.persist.mybatis.pagehelper.dialect.AbstractHelperDialect;
import org.apache.ibatis.cache.CacheKey;

public class MySqlDialect extends AbstractHelperDialect {

    @Override
    public String getPageSql(String sql, Page page, CacheKey pageKey) {
        StringBuilder sqlBuilder = new StringBuilder(sql.length() + 14);
        sqlBuilder.append(sql);
        if (page.getStartRow() == 0) {
            sqlBuilder.append(" LIMIT ");
            sqlBuilder.append(page.getPageSize());
        } else {
            sqlBuilder.append(" LIMIT ");
            sqlBuilder.append(page.getStartRow());
            sqlBuilder.append(",");
            sqlBuilder.append(page.getPageSize());
            pageKey.update(page.getStartRow());
        }
        pageKey.update(page.getPageSize());
        return sqlBuilder.toString();
    }

}
