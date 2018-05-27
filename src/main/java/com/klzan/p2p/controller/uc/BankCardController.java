/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.BankCardStatus;
import com.klzan.p2p.model.CpcnBankCard;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.user.CpcnBankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 银行卡管理
 */
@Controller
@RequestMapping("uc/bank_card")
public class BankCardController extends BaseController {

    @Autowired
    private CpcnBankCardService cpcnBankCardService;

    @RequestMapping
    public String index(@CurrentUser User currentUser, Model model, HttpServletRequest request) {
        model.addAttribute("bankCards", cpcnBankCardService.findByUserId(currentUser.getId(), BankCardStatus.BINDED, BankCardStatus.VERIFYING));//银行信息
        model.addAttribute("currentUser", currentUser);//当前用户
        return "uc/bank_card/index";
    }

    @RequestMapping("list.json")
    @ResponseBody
    public Result list(@CurrentUser User currentUser, Model model, HttpServletRequest request) {
        try {
            List<CpcnBankCard> bankCards = cpcnBankCardService.findByUserId(currentUser.getId(), BankCardStatus.BINDED, BankCardStatus.VERIFYING);
            return Result.success("", bankCards);
        } catch (Exception e) {
            return Result.error();
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@PathVariable Integer id, @CurrentUser User currentUser) {
        if (currentUser == null) {
            return Result.error("未登录");
        }
        try {
            return Result.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage() == null ? "删除失败" : e.getMessage());
        }

    }

}
