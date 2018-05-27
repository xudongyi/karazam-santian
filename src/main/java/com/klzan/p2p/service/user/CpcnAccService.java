package com.klzan.p2p.service.user;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.CpcnPayAccountInfo;

/**
 * Created by suhao on 2017/11/23.
 */
public interface CpcnAccService extends IBaseService<CpcnPayAccountInfo> {

    /**
     * 查询账户信息
     * @param payAccount
     * @return
     */
    CpcnPayAccountInfo findByPayAcc(String payAccount);

    CpcnPayAccountInfo findByUserId(Integer userId);

    void orgTransferSuccess(String orderNo);
}
