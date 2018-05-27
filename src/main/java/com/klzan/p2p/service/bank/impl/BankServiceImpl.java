package com.klzan.p2p.service.bank.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.bank.BankDao;
import com.klzan.p2p.model.Bank;
import com.klzan.p2p.service.bank.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by suhao Date: 2017/4/26 Time: 13:19
 *
 * @version: 1.0
 */
@Service
public class BankServiceImpl extends BaseService<Bank> implements BankService {
    @Autowired
    private BankDao bankDao;

    @Override
    public List<Bank> findList() {
        return bankDao.findList();
    }

    @Override
    public Bank findByCodeAndBizType(String bankCode, Integer bizType) {
        return bankDao.findByCodeAndBizType(bankCode, bizType);
    }
}
