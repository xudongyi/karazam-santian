/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingOpinionDao;
import com.klzan.p2p.model.BorrowingOpinion;
import com.klzan.p2p.service.borrowing.BorrowingOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 借款
 * @author: chenxinglin  Date: 2016/11/10
 */
@Service("borrowingOpinionService")
public class BorrowingOpinionServiceImpl extends BaseService<BorrowingOpinion> implements BorrowingOpinionService {

    @Autowired
    private BorrowingOpinionDao opinionDao;

    @Override
    public BorrowingOpinion create(BorrowingOpinion opinion) {
        return opinionDao.persist(opinion);
    }

    @Override
    public List<BorrowingOpinion> findList(Integer borrowingID) {
        return opinionDao.findList(borrowingID);
    }


}
