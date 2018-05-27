//package com.klzan.plugin.pay.cpcn.controller;
//
//import com.klzan.core.Result;
////import com.klzan.core.action.BaseController;
//import com.klzan.core.util.DateUtils;
////import com.klzan.p2p.plugin.china_clearing.service.PayService;
//import com.klzan.p2p.controller.BaseController;
//import com.klzan.plugin.pay.cpcn.service.PayService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
///**
// * 对账
// */
//@Controller
//@RequestMapping("/cpcn/accountChecking")
//public class CpcnAccountCheckingController extends BaseController {
//
//    @Autowired
//    private PayService payService;
//
//    /**
//     *
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping
//    @ResponseBody
//    public Result query(HttpServletRequest request, Date date, String page, String count) throws Exception {
//        try {
//            if(date == null){
//               return Result.error("参数不能为空");
//            }
//            date = DateUtils.parseToDate(DateUtils.format(date, DateUtils.DATE_PATTERN_yyyyMMdd));
//            return payService.accountChecking(date, page, count);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error();
//        }
//    }
//
//    /**
//     *
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "confirm")
//    @ResponseBody
//    public Result querypage(HttpServletRequest request, Date date) throws Exception {
//        Result result = null;
//        try {
//            date = DateUtils.parseToDate(DateUtils.format(date, DateUtils.DATE_PATTERN_yyyyMMdd));
//            return payService.accountCheckingConfirm(date);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error();
//        }
//    }
//
//}
