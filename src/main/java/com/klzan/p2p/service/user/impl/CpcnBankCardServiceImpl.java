package com.klzan.p2p.service.user.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.bank.BankDao;
import com.klzan.p2p.dao.user.CpcnAccDao;
import com.klzan.p2p.dao.user.CpcnBankCardDao;
import com.klzan.p2p.enums.BankCardStatus;
import com.klzan.p2p.model.Bank;
import com.klzan.p2p.model.CpcnBankCard;
import com.klzan.p2p.service.user.CpcnBankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CpcnBankCardServiceImpl extends BaseService<CpcnBankCard> implements CpcnBankCardService {

    @Autowired
    private CpcnBankCardDao cpcnBankCardDao;

    @Autowired
    private BankDao bankDao;

    @Override
    public CpcnBankCard find(Integer userId, String orderNo, String bindingSystemNo) {
        return cpcnBankCardDao.find(userId, orderNo, bindingSystemNo);
    }

    @Override
    public List<CpcnBankCard> findByUserId(Integer userId) {
        List<CpcnBankCard> cards = cpcnBankCardDao.findByUserId(userId);
        for (CpcnBankCard card : cards) {
            Bank bank = bankDao.findByCode(card.getBankID());
            if (null != bank) {
                card.setBankName(bank.getName());
                card.setBankLogo(bank.getLogo());
            }
        }
        return cards;
    }

    @Override
    public List<CpcnBankCard> findByUserId(Integer userId, BankCardStatus... status) {
        List<CpcnBankCard> cards = cpcnBankCardDao.findByUserId(userId, status);
        for (CpcnBankCard card : cards) {
            Bank bank = bankDao.findByCode(card.getBankID());
            if (null != bank) {
                card.setBankName(bank.getName());
                card.setBankLogo(bank.getLogo());
            }
        }
        return cards;
    }
}
