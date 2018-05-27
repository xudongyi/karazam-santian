/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.uc;


import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.BorrowingApplyProgress;
import com.klzan.p2p.enums.BorrowingApplyType;
import com.klzan.p2p.enums.CaptchaType;
import com.klzan.p2p.enums.GenderType;
import com.klzan.p2p.model.BorrowingApply;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.borrowing.BorrowingApplyService;
import com.klzan.p2p.service.captcha.CaptchaService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.borrowing.BorrowingApplyVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户中心借款申请列表
 * zhutao Date: 2017-05-04
 */
@Controller("ucBorrowingApplyController")
@RequestMapping("/uc/borrowingApply")
public class BorrowingApplyController extends BaseController{

    @Resource
    private BorrowingApplyService borrowingApplyService;

    @RequestMapping
    public String index() {
        return "uc/borrowing_apply/index";
    }

    @RequestMapping("data/{state}")
    @ResponseBody
    public PageResult<BorrowingApplyVo> data(@PathVariable String state, @CurrentUser User user, PageCriteria criteria,HttpServletRequest request){

        BorrowingApplyVo vo = new BorrowingApplyVo();
        vo.setMobile(user.getMobile());
        PageResult<BorrowingApplyVo> borrowingApplyByPage = borrowingApplyService.findBorrowingApplyByPage(buildQueryCriteria(criteria,request), vo);

        return borrowingApplyByPage;
    }
}
