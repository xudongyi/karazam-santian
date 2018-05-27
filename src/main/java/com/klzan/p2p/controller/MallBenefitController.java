///*
// * Copyright $2016-2020 www.klzan.com. All rights reserved.
// * Support: http://www.klzan.com
// */
//package com.klzan.p2p.controller;
//
//import com.klzan.core.page.PageCriteria;
//import com.klzan.core.page.PageResult;
//import com.klzan.p2p.enums.BorrowingProgress;
//import com.klzan.p2p.enums.GoodsType;
//import com.klzan.p2p.enums.InterestRateScope;
//import com.klzan.p2p.enums.PeriodScope;
//import com.klzan.p2p.model.Goods;
//import com.klzan.p2p.service.goods.GoodsCategoryService;
//import com.klzan.p2p.service.goods.GoodsService;
//import org.apache.http.HttpRequest;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
///**
// * 商城
// * @author: chenxinglin
// */
//@Controller("webMallBenefitController")
//@RequestMapping("/benefit")
//public class MallBenefitController extends BaseController {
//
//    /**
//     * 索引重定向URL
//     */
//    private static final String INDEX_REDIRECT_URL = "redirect:/benefit";
//
//    /**
//     * 模板路径
//     */
//    private static final String TEMPLATE_PATH = "/mall";
//
//    /**
//     * 模板路径
//     */
//    private static final Integer PAGE_ROWS = 12;
//
//    @Inject
//    private GoodsCategoryService goodsCategoryService;
//
//    @Inject
//    private GoodsService goodsService;
//
//    /**
//     * 列表
//     */
//    @RequestMapping
//    public String list(ModelMap model) {
//        List<Goods> hots = goodsService.findList(null, null, 4, true);
//        model.addAttribute("hots", hots);
//        return TEMPLATE_PATH + "/benefit";
//    }
//
//    @RequestMapping("data")
//    @ResponseBody
//    public PageResult<Goods> data(PageCriteria criteria, HttpServletRequest request) {
//        criteria.setRows(PAGE_ROWS);
//        PageResult<Goods> page = goodsService.findPage(buildQueryCriteria(criteria, request), null, GoodsType.benefit);
//        return page;
//    }
//
//}
//
//
//
//
//
