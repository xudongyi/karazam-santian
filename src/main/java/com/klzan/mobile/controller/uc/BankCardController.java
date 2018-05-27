/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.BankCardStatus;
import com.klzan.p2p.model.CpcnBankCard;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.user.CpcnBankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 银行卡管理
 */
@Controller("mobileBankCardController")
@RequestMapping("/mobile/uc/bank_card")
public class BankCardController extends BaseController {

    @Autowired
    private CpcnBankCardService cpcnBankCardService;

    @RequestMapping("list.json")
    @ResponseBody
    public Result list(@CurrentUser User currentUser) {
        try {
            List<CpcnBankCard> bankCards = cpcnBankCardService.findByUserId(currentUser.getId(), BankCardStatus.BINDED);
            return Result.success("", bankCards);
        } catch (Exception e) {
            return Result.error();
        }
    }

}
