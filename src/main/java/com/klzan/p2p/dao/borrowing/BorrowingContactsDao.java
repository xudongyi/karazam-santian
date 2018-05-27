/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.borrowing;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.BorrowingContactsType;
import com.klzan.p2p.model.BorrowingContacts;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借款
 * @author: chenxinglin
 */
@Repository
public class BorrowingContactsDao extends DaoSupport<BorrowingContacts> {

    /**
     * 列表
     * @param pageCriteria
     * @param filter_type
     * @param filter_LIKES_name
     * @param filter_LIKES_mobile
     * @param filter_LIKES_telephone
     * @param filter_createDateStart
     * @param filter_createDateEnd
     * @return
     */
    public PageResult<BorrowingContacts> findList(PageCriteria pageCriteria, BorrowingContactsType filter_type, String filter_LIKES_name,
                                                  String filter_LIKES_mobile, String filter_LIKES_telephone, Date filter_createDateStart, Date filter_createDateEnd) {

        StringBuffer hql = new StringBuffer("From BorrowingContacts b WHERE deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(filter_type!=null){
            hql.append(" AND b.type = " + filter_type.toString());
        }
        if(filter_LIKES_name!=null){
            hql.append(" AND b.name LIKE '%" + filter_LIKES_name + "%'");
        }
        if(filter_LIKES_mobile!=null){
            hql.append(" AND b.mobile LIKE '%" + filter_LIKES_mobile + "%'");
        }
        if(filter_LIKES_telephone!=null){
            hql.append(" AND b.telephone LIKE '%" + filter_LIKES_telephone + "%'");
        }
        if(filter_createDateStart!=null){
            hql.append(" AND b.createDate >= '" + DateUtils.format(filter_createDateStart,"yyyy-MM-dd HH:mm:ss") +"'");
        }
        if(filter_createDateEnd!=null){
            hql.append(" AND b.createDate <= '" + DateUtils.format(filter_createDateEnd,"yyyy-MM-dd HH:mm:ss") +"'");
        }
        return this.findPage(hql.toString(), pageCriteria, params);

    }


    public List<BorrowingContacts> findList(Integer borrowingID) {
        StringBuffer hql = new StringBuffer("From BorrowingContacts m WHERE deleted = 0 ");
        hql.append(" AND m.borrowing = " + borrowingID );
        return this.find(hql.toString());
    }



}