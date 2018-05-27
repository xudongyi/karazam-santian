/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller;


import com.klzan.core.Result;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.BorrowingApply;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.borrowing.BorrowingApplyService;
import com.klzan.p2p.service.captcha.CaptchaService;
import com.klzan.p2p.service.sms.SmsService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.borrowing.BorrowingApplyVo;
import com.klzan.p2p.vo.user.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 首页借款
 * zhutao Date: 2017-05-02
 */
@Controller("indexBorrowingApplyController")
@RequestMapping("/borrowingApply")
public class BorrowingApplyController extends BaseController{

    @Resource
    private UserService userService;
    @Resource
    private BorrowingApplyService borrowingApplyService;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private SmsService smsService;

    @RequestMapping
    public String index(ModelMap modelMap) {
        User currentUser = UserUtils.getCurrentUser();
        if (currentUser!=null){
            modelMap.addAttribute("user",currentUser);
        }
        return "borrowing_apply/index";
    }

    @RequestMapping("/apply")
    @ResponseBody
    public Result apply(BorrowingApplyVo vo, HttpServletRequest request){
        BorrowingApply borrowingApply = new BorrowingApply();
        String mobile = vo.getMobile();

        // 验证验证码
        if (!captchaService.verify(CaptchaType.BORROWING_APPLY, vo.getImgCode(), request.getSession())) {
            return Result.error("您输入的验证码有误");
        }
        // 验证验证码
        if(mobile != null){
            // 验证短信令牌
            try{
                if(!smsService.validate(mobile,vo.getCaptcha(),SmsType.BORROWING_APPLY_CODE)){
                    return Result.error("短信验证码错误");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("验证短信出错");
            }
        }else {
            return Result.error("手机号为空");
        }
        //1.登陆状态下申请借款
        if(StringUtils.isNotBlank(vo.getUserId())){
            UserVo user = userService.getUserById(new Integer(vo.getUserId()));
            borrowingApply.setGenderType(user.getGender()==null?GenderType.UNKNOWN:user.getGender());
            borrowingApply.setMobile(user.getMobile());
            borrowingApply.setUserName(user.getRealName());
            borrowingApply.setAmount(new BigDecimal(vo.getAmount()));
            borrowingApply.setDeadline(vo.getDeadline());
            borrowingApply.setRate(vo.getRate());
            borrowingApply.setIsPlatFormUser(true);
        }else {
        //2.未登陆状态下申请借款
            borrowingApply.setGenderType(vo.getGenderTypeEnum());
            borrowingApply.setUserName(vo.getUserName());
            if (StringUtils.isBlank(vo.getMobile())){
                return Result.error("手机号码为空");
            }else {
                borrowingApply.setMobile(vo.getMobile());
                if (userService.isExistMobile(vo.getMobile(),null)){
                    borrowingApply.setIsPlatFormUser(true);
                }else {
                    borrowingApply.setIsPlatFormUser(false);
                }
            }
        }
        borrowingApply.setBorrowingApplyType(getBorrowingType(vo.getType()));
        borrowingApply.setBorrowingApplyProgress(BorrowingApplyProgress.APPROVAL);
        borrowingApplyService.persist(borrowingApply);
        String sex = "";
        if (borrowingApply.getGenderType()==GenderType.FEMALE){
            sex = "女士";
        }else if (borrowingApply.getGenderType()==GenderType.MALE){
            sex = "先生";
        }else {
        }
        return Result.success(borrowingApply.getUserName()+sex+":您的申请已提交，已为您分配专属客户经理，我们将在两个小时内通过您提交的手机号:"+borrowingApply.getMobile()+"联系您，请保持电话畅通，谢谢合作！");
    }

    private BorrowingApplyType getBorrowingType(String type){
        switch (type){
            case "0":
                return BorrowingApplyType.CAR_PLEDGE;
            case "1":
                return BorrowingApplyType.HOUSE_BORROW;
            case "2":
                return BorrowingApplyType.FAST_BORROW;
            case "3":
                return BorrowingApplyType.SALE_BORROW;
            case "4":
                return BorrowingApplyType.CAR_BY_STAGES;
            case "5":
                return BorrowingApplyType.TRAVEL_BORROW;
            default:
                return BorrowingApplyType.CAR_PLEDGE;
        }
    }

    /**
     * 发送短信
     */
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public Result borrowingApply(ModelMap mode, String mobile) {

        if(mobile == null && mobile=="" ) {
            return Result.error("手机号不能为空");
        }
        try {
            // 发送注册短信
            mode.addAttribute("mobile",mobile);
            smsService.sendValidate(mobile, mode, SmsType.BORROWING_APPLY_CODE);
            return Result.success("验证码发送成功");
        } catch (BusinessProcessException e) {
            e.printStackTrace();
            return Result.error("超出当日有效发送次数");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("验证码发送失败");
        }
    }
}
