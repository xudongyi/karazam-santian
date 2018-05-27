//package com.klzan.plugin.pay.cpcn.controller;
//
//import com.klzan.core.Result;
//import com.klzan.core.action.BaseController;
//import com.klzan.model.user.User;
//import com.klzan.oauth.core.util.UserUtils;
//import com.klzan.p2p.plugin.china_clearing.Request;
//import com.klzan.p2p.plugin.china_clearing.service.ChinaClearingService;
//import com.klzan.p2p.plugin.china_clearing.service.PayService;
//import com.klzan.p2p.service.capital.UserFinanceService;
//import com.klzan.p2p.service.customer.HuaShanUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 开户
// */
//@Controller
//@RequestMapping("/cpcn")
//public class CpcnOpenAccountController extends BaseController {
//
//    @Autowired
//    private ChinaClearingService chinaClearingService;
//    @Autowired
//    private HuaShanUserService huaShanUserService;
//    @Autowired
//    private UserFinanceService userFinanceService;
//    @Autowired
//    private PayService payService;
//
////    @RequestMapping(value = "test")
////    @ResponseBody
////    public Object test(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
////        try {
//////            Result result = payService.openAccountQuery();
//////            return result;
////            return Result.success();
////        } catch (Exception e) {
////            e.printStackTrace();
////            return null;
////        }
////    }
//
//    @RequestMapping(value = "open-account")
//    public String index(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception {
//        try {
//
//            User user = UserUtils.getCurrentUser();
//            if (user==null) {
//                redirectAttributes.addFlashAttribute("flashMessage", "未登录");
//                return "redirect:/uc";
//            }
//
//            Request result = payService.openAccount();
//            model.addAttribute("requestUrl", result.getRequestUrl());
//            model.addAttribute("parameterMap", result.getParameterMap());
//            return "/payment/submit";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ERROR_VIEW;
//        }
//    }
//
//    @RequestMapping(value = "open-account/query")
//    @ResponseBody
//    public Result openAccountQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        try {
//
//            Result result = payService.openAccountQuery(null);
//
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error(e.getMessage());
//        }
//    }
//
//
//}
