package com.klzan.p2p.mapper.handler;

import com.klzan.p2p.model.elem.SEOElem;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by suhao Date: 2017/4/11 Time: 14:00
 *
 * @version:　1.0
 */
public class SeoEleHandler extends BaseTypeHandler<SEOElem> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    SEOElem parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.valueOf(parameter));

    }

    @Override
    public SEOElem getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return new SEOElem();
    }

    @Override
    public SEOElem getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return new SEOElem();
    }

    @Override
    public SEOElem getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return new SEOElem();
    }

}