package com.klzan.p2p.service.bank;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.Bank;

import java.util.List;

/**
 * Created by suhao on 2017/4/26.
 */
public interface BankService extends IBaseService<Bank> {
    List<Bank> findList();

    Bank findByCodeAndBizType(String bankCode, Integer bizType);
}
