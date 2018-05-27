package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.plugin.pay.common.module.PayModule;
import payment.api.notice.NoticeRequest;
import payment.api.tx.TxBaseRequest;

import java.util.Map;

/**
 * 支付业务接口
 */
public interface BusinessService {

    Result before(PayModule module);

    Result after(PayModule module, Result result);

    Result notice(String sn, NoticeRequest notice);

}
