/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing.impl;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingFieldRemarkDao;
import com.klzan.p2p.model.BorrowingFieldRemark;
import com.klzan.p2p.service.borrowing.BorrowingFieldRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借款字段备注
 */
@Service
public class BorrowingFieldRemarkServiceImpl extends BaseService<BorrowingFieldRemark> implements BorrowingFieldRemarkService {

    @Autowired
    private BorrowingFieldRemarkDao borrowingFieldRemarkDao;

    @Override
    public void createOrUpdateRemarks(Integer borrowingId, Map<String, ?> remarks) {
        List<BorrowingFieldRemark> remarkList = borrowingFieldRemarkDao.findByBorrowing(borrowingId);
        for (BorrowingFieldRemark fieldRemark : remarkList) {
            borrowingFieldRemarkDao.logicDeleteById(fieldRemark.getId());
        }
        remarkList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : remarks.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            if (!StringUtils.containsIgnoreCase(key, "-remark") || StringUtils.isBlank(value)) {
                continue;
            }
            remarkList.add(new BorrowingFieldRemark(borrowingId, key, value));
        }
        borrowingFieldRemarkDao.batchMerge(remarkList);
    }

    @Override
    public Map<String, String> findReamrks(Integer borrowingId) {
        List<BorrowingFieldRemark> remarkList = borrowingFieldRemarkDao.findByBorrowing(borrowingId);
        Map<String, String> map = new HashMap<>();
        for (BorrowingFieldRemark remark : remarkList) {
            map.put(remark.getFieldName(), remark.getFieldRemark());
        }
        return map;
    }
}