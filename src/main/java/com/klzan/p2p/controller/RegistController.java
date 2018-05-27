package com.klzan.p2p.controller;

import com.klzan.core.Result;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.enums.CaptchaType;
import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.enums.UserLogType;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserLog;
import com.klzan.p2p.security.user.UserPrincipal;
import com.klzan.p2p.service.captcha.CaptchaService;
import com.klzan.p2p.service.sms.SmsService;
import com.klzan.p2p.service.user.UserLogService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.regist.RegistVo;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegistController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private RSAService rsaService;
    @Resource
    private UserLogService userLogService;
    @Resource
    private SmsService smsService;

    @RequestMapping("/regist")
    public String register(@RequestParam(required = false) String inviteCode, HttpServletRequest request, Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/";
        }
        // 密钥
        RSAPublicKey publicKey = rsaService.generateKey(request);
        model.addAttribute("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        model.addAttribute("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));

        if (StringUtils.isNotBlank(inviteCode)) {
            User inviteUser = userService.getUserByInviteCode(inviteCode);
            if (inviteUser != null) {
                model.addAttribute("referrer", inviteUser.getMobile());
                model.addAttribute("inviteCode", inviteCode);
            } else {
                model.addAttribute("flashMessage", "邀请码不存在");
            }
        }

        return "regist/regist";
    }

    /**
     * 注册协议
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/regist/agreement")
    public String agreement(HttpServletRequest request, Model model) {

        return "regist/agreement";
    }

    /**
     * 检查用户名
     */
    @RequestMapping(value = "/regist/check_username", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkUsername(String username, UserType type) {
        if (StringUtils.isBlank(username)) {
            return false;
        }
        // 判断用户名是否存，true表示用户存在，反之不存在
        return userService.isExistLoginName(username, type);
    }
    /**
     * 检查手机号码
     */
    @RequestMapping(value = "/regist/check_mobile", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkMobile(String mobile, UserType type, HttpServletRequest request) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        return !(userService.isExistMobile(mobile, type));
    }

    /**
     * 检查推荐人
     */
    @RequestMapping(value = "/regist/check_referrer", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkReferrer(String referrer, HttpServletRequest request) {
        if (StringUtils.isBlank(referrer)) {
            return true;
        }

        // 判断推荐人是否存在
        return userService.isExistMobile(referrer, UserType.GENERAL);
    }

    /**
     * 发送短信
     */
    @RequestMapping(value = "/regist/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public Result textingApply(ModelMap mode, String mobile, UserType type, HttpServletRequest request) {

        if(mobile == null ) {
            return Result.error("手机号不能为空");
        }

        // 验证手机号码是否唯一
        if (userService.isExistMobile(mobile, type)) {
            return Result.error("手机号码已被注册");
        }
        try {
            // 发送注册短信
            mode.addAttribute("mobile",mobile);
            smsService.sendValidate(mobile, mode, SmsType.USER_REGIST_CODE);
            return Result.success("验证码发送成功");
        } catch (BusinessProcessException e) {
            e.printStackTrace();
            return Result.error("超出当日有效发送次数");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("验证码发送失败");
        }
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/regist/do", method = RequestMethod.POST)
    @ResponseBody
    public Result textingRegist(RegistVo vo, HttpServletRequest request, HttpServletResponse response) {

        final int MAX_LENGTH = 18;
        final int MIX_LENGTH = 6;

        // 获取密码
        vo.setPassword(rsaService.decryptParameter("password", request));

        // 验证密码长度
        if (vo.getPassword().length() > MAX_LENGTH || vo.getPassword().length() < MIX_LENGTH) {
            return Result.error("密码需为6-18位字符");
        }
        // 验证手机号是否在
        if (userService.isExistMobile(vo.getMobile(), vo.getType())) {
            return Result.error("手机号已被注册");
        }
        request.getSession().setAttribute("modulus", request.getParameter("modulus"));
        request.getSession().setAttribute("exponent", request.getParameter("exponent"));

        String mobile = vo.getMobile();
        String password = vo.getPassword();
        String referrer = vo.getReferrer();
        if (StringUtils.isBlank(mobile)) {
            return Result.error("参数错误");
        }
        if (mobile.equals(referrer)){
            return Result.error("推荐人不能为自己");
        }

        if (StringUtils.isNotBlank(referrer)){
            User u = userService.getUserByMobile(referrer, UserType.GENERAL);
            if (u == null || u.getDeleted()) {
                logger.error("推荐人不存在或非个人用户");
                return Result.error("推荐人不存在或非个人用户");
            }
        }

        // 验证验证码
        if(mobile != null){
            // 验证短信令牌
            try{
                if(!smsService.validate(mobile,vo.getCaptcha(),SmsType.USER_REGIST_CODE)){
                    return Result.error("短信验证码错误");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("验证短信出错");
            }
        }else {
            return Result.error("手机号为空");
        }

        //新增用户
        User user = new User(mobile, password, vo.getType());
        PasswordHelper.encryptPassword(user, password);
        User user2 = null;
        try {
            user2 = userService.createUser(user, null, referrer);
        } catch (Exception e) {
            logger.error("新增用户失败");
            e.printStackTrace();
            return Result.error("新增用户失败");
        }

        WebUtils.refactorSession(request);
        HttpSession session = request.getSession();
        session.setAttribute(User.PRINCIPAL_ATTR_NAME, new UserPrincipal(user.getId(), user.getLoginName(), user.getType(), user.getName(), user.getMobile()));
        WebUtils.addCookie(request, response, User.USERNAME_COOKIE_NAME, user.getLoginName());

        Map map = new HashMap();
        map.put("sid", session.getId());
        map.put("userId", user.getId());
        map.put("loginTimeFlag", DateUtils.getTime());

        // 保存会员日志
        if(user2!=null){
            UserLog userLog = new UserLog(UserLogType.REGIST,new String("官网注册"),user2.getLoginName(), WebUtils.getRemoteIp(request),user2.getId());
            userLogService.persist(userLog);
        }
        return Result.success("注册成功", map);
    }

    @RequestMapping(value = "/regist/succeed", method = RequestMethod.GET)
    public String regist_succeed(ModelMap map, HttpServletRequest request) {
        WebUtils.refactorSession(request);
        HttpSession session = request.getSession();
        UserPrincipal principal = (UserPrincipal) session.getAttribute(User.PRINCIPAL_ATTR_NAME);
        if (null == principal) {
            return "redirect:/login";
        }
        map.addAttribute("currentUser", principal);
        return "regist/succeed";
    }
}