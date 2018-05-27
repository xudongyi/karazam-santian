//package com.klzan.plugin.pay.cpcn.controller;
//
//import com.klzan.core.Result;
//import com.klzan.core.util.JsonUtils;
//import com.klzan.plugin.pay.common.FormatUtil;
//import com.klzan.plugin.pay.cpcn.service.CpcnPayQueryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 富民订单查询
// */
//@Controller
//@RequestMapping("/cpcn")
//public class CpcnQueryController /*extends BaseController*/ {
//
//    @Autowired
//    private CpcnPayQueryService payQueryService;
//
//    /**
//     *
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "query")
//    @ResponseBody
//    public Result query(HttpServletRequest request) throws Exception {
//        try {
//            String sn = request.getParameter("sn");
//            return payQueryService.query(sn);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error();
//        }
//    }
//
//    /**
//     *
//     * @param request
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "query-page")
//    public String querypage(HttpServletRequest request, Model model) throws Exception {
//        Result result = null;
//        try {
//            String sn = request.getParameter("sn");
//            model.addAttribute("orderNo", sn);
//            result = payQueryService.query(sn);
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = Result.error();
//        }
//        model.addAttribute("result", FormatUtil.formatJson(JsonUtils.toJson(result)));
//        return "orderquerycpcn/index";
//    }
//
//}
