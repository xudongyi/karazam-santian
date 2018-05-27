/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.token;

import com.klzan.core.page.PageCriteria;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.service.sms.SmsTokenService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 短信验证码
 * Created by limate Date: 2017/06/20
 *
 * @version: 1.0
 */
@Controller("adminTokenController")
@RequestMapping("admin/token")
public class smsTokenController extends BaseAdminController {

    @Resource
    private SmsTokenService smsTokenService;

    @RequestMapping("list")
    @RequiresPermissions("admin:smstoken:list")
    public String list(ModelMap modelMap){
        return "admin/sms_token/list";
    }

    @RequestMapping("list.do")
    @RequiresPermissions("admin:smstoken:list")
    @ResponseBody
    public Object listDo(PageCriteria pageCriteria,HttpServletRequest request){
        return smsTokenService.findAllSmsTokenPage(pageCriteria,request);
    }
}
