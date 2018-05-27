package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.DigestUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.mobile.token.TokenUtils;
import com.klzan.mobile.vo.RegistVo;
import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.ClientType;
import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.enums.UserLogType;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.AccessToken;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserLog;
import com.klzan.p2p.service.sms.SmsService;
import com.klzan.p2p.service.token.AccessTokenService;
import com.klzan.p2p.service.user.UserLogService;
import com.klzan.p2p.service.user.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 注册
 */
@Controller("mobileUCIndexController")
@RequestMapping("/mobile/uc")
public class IndexController extends BaseController {

    @RequestMapping("/test")
    public String test() {
        return "agreement/regist_agreement";
    }

    @RequestMapping("/test2")
    @ResponseBody
    public Result test2() {
        return Result.success("sss");
    }

}