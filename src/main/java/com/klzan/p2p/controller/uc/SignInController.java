/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.SecrecyUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.enums.GoodsOrderStatus;
import com.klzan.p2p.enums.PointMethod;
import com.klzan.p2p.enums.PointType;
import com.klzan.p2p.model.PointRecord;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserCoupon;
import com.klzan.p2p.model.UserPoint;
import com.klzan.p2p.service.coupon.UserCouponService;
import com.klzan.p2p.service.point.PointRecordService;
import com.klzan.p2p.service.user.UserPointService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import java.util.*;

/**
 * 用户积分
 * @author: chenxinglin
 */
@Controller("webUCSignInController")
@RequestMapping("/uc/sign_in")
public class SignInController extends BaseController {

    /**
     * 索引重定向URL
     */
    private static final String INDEX_REDIRECT_URL = "redirect:/uc/sign_in";

    /**
     * 模板路径
     */
    private static final String TEMPLATE_PATH = "/uc/sign_in";

    @Inject
    private PointRecordService pointRecordService;

    @Inject
    private UserPointService userPointService;

    @Inject
    private UserCouponService userCouponService;

    /**
     * 首页
     */
    @RequestMapping
    public String sign_in(@CurrentUser User currentUser, PointType type, GoodsOrderStatus status, ModelMap model, RedirectAttributes redirectAttributes) {
        if(currentUser == null){
            redirectAttributes.addFlashAttribute("flashMessage", "未登录");
            return INDEX_REDIRECT_URL;
        }
        UserPoint userPoint = userPointService.findByUserId(currentUser.getId());
        model.addAttribute("userPoint", userPoint);

//        List<Map> records = new ArrayList<>();
//        List<PointRecord> pointRecordsAll = pointRecordService.findList(null, PointMethod.sign_in);
//        for(PointRecord pointRecord : pointRecordsAll){
//            Map map = new HashMap();
//            map.put("username", SecrecyUtils.toUsername(pointRecord.getUsername(), null));
//            map.put("createDate", DateUtils.format(pointRecord.getCreateDate()));
//            map.put("point", pointRecord.getPoint() + "积分");
//            records.add(map);
//        }
//        model.addAttribute("records", records); //中奖纪录

        List<Map> records = new ArrayList<>();
        List<UserCoupon> userCoupons = userCouponService.findUserCoupon(null, CouponSource.SIGN_IN);
        for(UserCoupon userCoupon : userCoupons){
            Map map = new HashMap();
            map.put("username", SecrecyUtils.toUsername(userCoupon.getUsername(), null));
            map.put("createDate", DateUtils.format(userCoupon.getCreateDate()));
            map.put("title", userCoupon.getTitle());
            records.add(map);
        }
        model.addAttribute("records", records); //中奖纪录 userCouponService

        List<Map> myRecords = new ArrayList<>();
        List<UserCoupon> myUserCoupons = userCouponService.findUserCoupon(currentUser.getId(), CouponSource.SIGN_IN);
        for(UserCoupon userCoupon : myUserCoupons){
            Map map = new HashMap();
            map.put("createDate", userCoupon.getCreateDate());
            map.put("point", userCoupon.getTitle());
            myRecords.add(map);
        }
        List<PointRecord> pointRecords = pointRecordService.findList(currentUser.getId(), PointMethod.sign_in);
        for(PointRecord pointRecord : pointRecords){
            Map map = new HashMap();
            map.put("createDate", pointRecord.getCreateDate());
            map.put("point", pointRecord.getPoint() + "积分");
            myRecords.add(map);
        }

        model.addAttribute("recordsList", calendarRecords(currentUser.getId())); //我的奖励
        model.addAttribute("myRecords", myRecords); //我的奖励
        return TEMPLATE_PATH + "/index";
    }

//    public Map<String, String> sortMapByValue(Map<String, String> oriMap) {
//        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
//        if (oriMap != null && !oriMap.isEmpty()) {
//            List<Map.Entry<String, String>> entryList = new ArrayList<Map.Entry<String, String>>(oriMap.entrySet());
//            Collections.sort(entryList,
//                    new Comparator<Map.Entry<String, String>>() {
//                        public int compare(Map.Entry<String, String> entry1,
//                                           Map.Entry<String, String> entry2) {
//                            int value1 = 0, value2 = 0;
//                            try {
//                                value1 = getInt(entry1.getValue());
//                                value2 = getInt(entry2.getValue());
//                            } catch (NumberFormatException e) {
//                                value1 = 0;
//                                value2 = 0;
//                            }
//                            return value2 - value1;
//                        }
//                    });
//            Iterator<Map.Entry<String, String>> iter = entryList.iterator();
//            Map.Entry<String, String> tmpEntry = null;
//            while (iter.hasNext()) {
//                tmpEntry = iter.next();
//                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
//            }
//        }
//        return sortedMap;
//    }

    /**
     * 签到
     * @param currentUser
     * @return
     */
    @RequestMapping("sign_in")
    @ResponseBody
    public Result sign_in(@CurrentUser User currentUser) {
        if(currentUser == null){
            return Result.error("未登录");
        }
        UserPoint userPoint = userPointService.findByUserId(currentUser.getId());
        if(userPoint == null){
            return Result.error("系统错误");
        }
        if(userPoint.getLastSignIn()!=null && DateUtils.getZeroDate(new Date()).compareTo(DateUtils.getZeroDate(userPoint.getLastSignIn()))==0){
            return Result.error("您今天已签到，请明日再战");
        }
        try {
            Object[] obj = userPointService.signIn(currentUser.getId());
            Map map = new HashedMap();
            map.put("point", obj[0]);
            map.put("coupon", obj[1]);
            map.put("conSignInCount",obj[2]);
            return Result.success("签到成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("签到失败");
        }
    }

    /**
     * 签到记录
     */
    public List calendarRecords(Integer userId) {

        List result = new ArrayList();

        try {
            Date date = DateUtils.getCurrentDate(); //当天
            Date firstDayOfMonth = DateUtils.getDateByFirstDayOfMonth(date); //当月第一天
            Date firstDayOfNextMonth = DateUtils.getDateByFirstDayOfMonth(DateUtils.addMonths(date, 1)); //下月第一天

            int currentDays = DateUtils.getDaysOfMonth(date); //当月天数
            int currentVernier = 1; //当月天数游标

            int currentDayOfWeek = DateUtils.getDayOfWeek(firstDayOfMonth); //当月第一天 星期第几天
            int beforeDays = currentDayOfWeek; //上月天数

            int afterDays = 42 - currentDays - beforeDays; //下月天数
            int afterVernier = 1; //下月天数游标

            Date dateVernier = null;
            for(int i=0; i<6; i++){
                List list = new ArrayList();
                int j=0;
                for(j=0; j<7; j++){
                    String classStr = "";
                    int valueInt = -1;
                    if(beforeDays>0){
                        classStr = "color-gray";
                        dateVernier = DateUtils.addDays(firstDayOfMonth, -beforeDays);
                        beforeDays--;
                    }else if(currentVernier <= currentDays){
                        if(currentVernier == DateUtils.getDayOfMonth(date)){
                            classStr = "color-red today";
                        }
                        dateVernier = DateUtils.addDays(firstDayOfMonth, currentVernier-1);
                        currentVernier++;
                    }else if(afterVernier <= afterDays){
                        classStr = "color-gray";
                        dateVernier = DateUtils.addDays(firstDayOfNextMonth, afterVernier-1);
                        afterVernier++;
                    }else {
                        System.out.println("---异常---");
                    }
                    Map map = new HashMap();
                    map.put("classStr", classStr); //color-red color-gray
                    List<PointRecord> pointRecords = pointRecordService.findList(userId , PointMethod.sign_in, DateUtils.getZeroDate(dateVernier), DateUtils.getZeroDate(DateUtils.addDays(dateVernier, 1)));
                    if(pointRecords!=null && pointRecords.size()>0){
                        map.put("value", "√"); // value V(0)
                    }else {
                        map.put("value", DateUtils.getDayOfMonth(dateVernier));
                    }
                    list.add(map);
//                    System.out.println(classStr);
//                    System.out.println(dateVernier);
                }
//                System.out.println("---");
                result.add(list);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}





