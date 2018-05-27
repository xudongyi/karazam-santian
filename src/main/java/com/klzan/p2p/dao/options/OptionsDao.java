/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.options;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.enums.OptionsType;
import com.klzan.p2p.model.Options;
import com.klzan.p2p.vo.options.OptionsVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by suhao Date: 2016/11/11 Time: 10:04
 *
 * @version: 1.0
 */
@Repository
public class OptionsDao extends DaoSupport<Options> {
    public OptionsVo findById(Integer optionsId) {
        StringBuilder sql = getBasicSql();
        sql.append("and o.id =?0 ");
        return this.findUniqueBySQL(sql.toString(), OptionsVo.class, optionsId);
    }

    public PageResult<OptionsVo> findPageByType(PageCriteria criteria, OptionsType type) {
        StringBuilder sql = getBasicSql();
        sql.append("and o.type =?0 ");
        sql.append("order by o.sort ASC ");
        return this.findPageBySQL(sql.toString(), criteria, new ScalarAliasCallback<OptionsVo>() {
            @Override
            protected Class<OptionsVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("typeStr", StandardBasicTypes.STRING);
                query.addScalar("dataTypeStr", StandardBasicTypes.STRING);
                query.addScalar("dataUnitStr", StandardBasicTypes.STRING);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("keyName", StandardBasicTypes.STRING);
                query.addScalar("keyValue", StandardBasicTypes.STRING);
                return OptionsVo.class;
            }
        }, type.name());
    }

    public List<OptionsVo> findListByType(OptionsType type) {
        StringBuilder sql = getBasicSql();
        sql.append("and o.type =?0 ");
        sql.append("order by o.sort ASC ");
        return this.findBySQL(sql.toString(), new ScalarAliasCallback<OptionsVo>() {
            @Override
            protected Class<OptionsVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("typeStr", StandardBasicTypes.STRING);
                query.addScalar("dataTypeStr", StandardBasicTypes.STRING);
                query.addScalar("dataUnitStr", StandardBasicTypes.STRING);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("keyName", StandardBasicTypes.STRING);
                query.addScalar("keyValue", StandardBasicTypes.STRING);
                return OptionsVo.class;
            }
        }, type.name());
    }

    private StringBuilder getBasicSql() {
        StringBuilder sql = new StringBuilder("select ");
        sql.append("o.id, ");
        sql.append("o.type typeStr, ");
        sql.append("o.data_type dataTypeStr, ");
        sql.append("o.data_unit dataUnitStr, ");
        sql.append("o.name, ");
        sql.append("o.key_name keyName, ");
        sql.append("o.key_value keyValue ");
        sql.append("from karazam_options o ");
        sql.append("where o.deleted=false ");
        return sql;
    }

}
