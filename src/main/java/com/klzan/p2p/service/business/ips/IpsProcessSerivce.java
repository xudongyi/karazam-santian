package com.klzan.p2p.service.business.ips;

import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;

/**
 * Created by suhao on 2017/4/7.
 */
public interface IpsProcessSerivce {

    <T> T process(String orderNo, IDetailResponse response);

    Boolean support(BusinessType businessType, PaymentOrderType type);
}
