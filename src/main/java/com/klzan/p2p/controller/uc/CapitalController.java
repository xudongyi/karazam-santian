/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.uc;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.CapitalMethod;
import com.klzan.p2p.enums.CapitalType;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.Capital;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.investment.InvestmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * 资金记录
 * Created by chenxinglin Date: 2016-12-12
 */
@Controller
@RequestMapping("/uc/capital")
public class CapitalController extends BaseController {

    @Inject
    private CapitalService capitalService;

    @Inject
    private InvestmentService investmentService;

    @RequestMapping
    public String index(@CurrentUser User currentUser, CapitalType type, CapitalMethod method, Model model, HttpServletRequest request) {
        model.addAttribute("types", CapitalType.values());
        model.addAttribute("type", type);
        model.addAttribute("methods", CapitalMethod.values());
        model.addAttribute("method", method);
        model.addAttribute("recordStatus", RecordStatus.values());
        return "uc/capital/index";
    }

    @RequestMapping("data")
    @ResponseBody
    public PageResult<Capital> data(@CurrentUser User currentUser, PageCriteria criteria, CapitalMethod method, HttpServletRequest request) {
        PageCriteria pageCriteria = buildQueryCriteria(criteria, request);
        pageCriteria.getParams().add(ParamsFilter.addFilter("userId", ParamsFilter.MatchType.EQ, currentUser.getId()));
        return capitalService.findList(pageCriteria, null, method);
    }
}
