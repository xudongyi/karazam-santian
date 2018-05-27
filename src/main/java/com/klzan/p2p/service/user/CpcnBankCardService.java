package com.klzan.p2p.service.user;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.BankCardStatus;
import com.klzan.p2p.model.CpcnBankCard;

import java.util.List;

public interface CpcnBankCardService extends IBaseService<CpcnBankCard> {

    CpcnBankCard find(Integer userId, String orderNo, String bindingSystemNo);

    List<CpcnBankCard> findByUserId(Integer userId);

    List<CpcnBankCard> findByUserId(Integer userId, BankCardStatus... status);
}
