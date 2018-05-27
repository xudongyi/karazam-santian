/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingContactsDao;
import com.klzan.p2p.enums.BorrowingContactsType;
import com.klzan.p2p.model.BorrowingContacts;
import com.klzan.p2p.service.borrowing.BorrowingContactsService;
import com.klzan.p2p.vo.borrowing.BorrowingContactsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 借款
 *
 * @author: chenxinglin  Date: 2016/11/15
 */
@Service("borrowingContactsService")
public class BorrowingContactsServiceImpl extends BaseService<BorrowingContacts> implements BorrowingContactsService {

    @Autowired
    private BorrowingContactsDao borrowingContactsDao;

    @Override
    public PageResult<BorrowingContacts> findList(PageCriteria pageCriteria, BorrowingContactsType filter_type,
                                                  String filter_LIKES_name, String filter_LIKES_mobile, String filter_LIKES_telephone, Date filter_createDateStart, Date filter_createDateEnd) {
        return borrowingContactsDao.findList(pageCriteria, filter_type,
                filter_LIKES_name, filter_LIKES_mobile, filter_LIKES_telephone, filter_createDateStart, filter_createDateEnd);
    }

    @Override
    public void create(BorrowingContactsVo vo) {
        BorrowingContacts bc = new BorrowingContacts();
        bc.setBorrowing(vo.getBorrowing());
        bc.setType(vo.getType());
        bc.setName(vo.getName());
        bc.setMobile(vo.getMobile() == null ? "" : vo.getMobile());
        bc.setTelephone(vo.getTelephone() == null ? "" : vo.getTelephone());
        bc.setMemo(vo.getMemo() == null ? "" : vo.getMemo());
        borrowingContactsDao.persist(bc);
    }

    @Override
    public void update(BorrowingContactsVo vo) {
        BorrowingContacts bc = borrowingContactsDao.get(vo.getId());
        bc.setBorrowing(vo.getBorrowing());
        bc.setType(vo.getType());
        bc.setName(vo.getName());
        bc.setMobile(vo.getMobile() == null ? "" : vo.getMobile());
        bc.setTelephone(vo.getTelephone() == null ? "" : vo.getTelephone());
        bc.setMemo(vo.getMemo() == null ? "" : vo.getMemo());
        borrowingContactsDao.update(bc);
    }

    @Override
    public List<BorrowingContacts> findList(Integer borrowingID) {
        return borrowingContactsDao.findList(borrowingID);
    }
}
