/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.repayment;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnSettlement;

import java.util.List;

/**
 * 还款
 * @author: chenxinglin
 */
public interface CpcnSettlementService extends IBaseService<CpcnSettlement> {

    CpcnSettlement findByOrderNoRepayment(String rOrderNo);

    CpcnSettlement findByOrderNoSettlement(String sOrderNo);

    CpcnSettlement find(PaymentOrderType type, Integer borrowing, Integer repayment);

    CpcnSettlement findSettlement(PaymentOrderType type, Integer borrowing);

    List<CpcnSettlement> findUnSettlement();


}
