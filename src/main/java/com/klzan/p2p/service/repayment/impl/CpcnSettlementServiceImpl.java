/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.repayment.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.postloan.CpcnSettlementDao;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnSettlement;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 还款
 *
 * @author: chenxinglin
 */
@Service
public class CpcnSettlementServiceImpl extends BaseService<CpcnSettlement> implements CpcnSettlementService {

    @Autowired
    protected CpcnSettlementDao cpcnSettlementDao;

    @Override
    public CpcnSettlement findByOrderNoRepayment(String rOrderNo) {
        return cpcnSettlementDao.findByOrderNoRepayment(rOrderNo);
    }

    @Override
    public CpcnSettlement findByOrderNoSettlement(String sOrderNo) {
        return cpcnSettlementDao.findByOrderNoSettlement(sOrderNo);
    }

    @Override
    public CpcnSettlement find(PaymentOrderType type, Integer borrowing, Integer repayment) {
        return cpcnSettlementDao.find(type, borrowing, repayment);
    }

    @Override
    public CpcnSettlement findSettlement(PaymentOrderType type, Integer borrowing) {
        return cpcnSettlementDao.findSettlement(type, borrowing);
    }

    @Override
    public List<CpcnSettlement> findUnSettlement() {
        return cpcnSettlementDao.findUnSettlement();
    }
}
