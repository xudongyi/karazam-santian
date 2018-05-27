package com.klzan.core.persist.mybatis.pagehelper.dialect.helper;

import com.klzan.core.persist.mybatis.pagehelper.Page;
import com.klzan.core.persist.mybatis.pagehelper.dialect.AbstractHelperDialect;
import org.apache.ibatis.cache.CacheKey;

public class OracleDialect extends AbstractHelperDialect {

    @Override
    public String getPageSql(String sql, Page page, CacheKey pageKey) {
        StringBuilder sqlBuilder = new StringBuilder(sql.length() + 120);
        if (page.getStartRow() > 0) {
            sqlBuilder.append("SELECT * FROM ( ");
        }
        if (page.getEndRow() > 0) {
            sqlBuilder.append(" SELECT TMP_PAGE.*, ROWNUM ROW_ID FROM ( ");
        }
        sqlBuilder.append(sql);
        if (page.getEndRow() > 0) {
            sqlBuilder.append(" ) TMP_PAGE WHERE ROWNUM <= ");
            sqlBuilder.append(page.getEndRow());
            pageKey.update(page.getEndRow());
        }
        if (page.getStartRow() > 0) {
            sqlBuilder.append(" ) WHERE ROW_ID > ");
            sqlBuilder.append(page.getStartRow());
            pageKey.update(page.getStartRow());
        }
        return sqlBuilder.toString();
    }

}
