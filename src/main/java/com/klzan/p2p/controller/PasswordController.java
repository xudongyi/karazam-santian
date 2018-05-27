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
import org.apache.commons.collections.map.HashedMap;
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
public class PasswordController extends BaseController {

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

    @RequestMapping("/forgetPassword")
    public String register(HttpServletRequest request, Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "forgetPassword/forgetPassword";
        }
        // 密钥
        RSAPublicKey publicKey = rsaService.generateKey(request);
        model.addAttribute("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        model.addAttribute("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));

        return "forgetPassword/forgetPassword";
    }

    /**
     * 检查手机号码
     */
    @RequestMapping(value = "/forgetPassword/check_mobile", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkMobile(String mobile, UserType type) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        return userService.isExistMobile(mobile, type);
    }

    /**
     * 验证图形验证码
     */
    @RequestMapping(value = "/verifyCaptcha")
    @ResponseBody
    public Result image(@RequestParam CaptchaType type, @RequestParam String mobile, @RequestParam String imageCaptcha, HttpServletRequest request) throws Exception {

        // 验证验证码
        if (!captchaService.verify(type, imageCaptcha, request.getSession())) {
            return Result.error("图形验证码错误");
        }
        Map<String, Object> map = new HashedMap();
        map.put("mobile", mobile);
        return Result.success("图形验证码正确", map);
    }

    /**
     * 发送验证短信
     */
    @RequestMapping(value = "/forgetPassword/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public Result textingApply(ModelMap mode, String mobile, UserType type) {

        if (mobile == null) {
            return Result.error("手机号不能为空");
        }
        // 验证手机号码是否唯一
        if (!userService.isExistMobile(mobile, type)) {
            return Result.error("手机号码未注册");
        }
        try {
            // 发送注册短信
            mode.addAttribute("mobile", mobile);
            smsService.sendValidate(mobile, mode, SmsType.USER_FIND_PASSWORD_CODE);
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
     * 验证短信验证码
     */
    @RequestMapping(value = "/forgetPassword/verifyMessage", method = RequestMethod.POST)
    @ResponseBody
    public Result verifyMessage(@RequestParam String mobile, @RequestParam String validateCode) throws Exception {

        if (mobile == null) {
            return Result.error("参数错误");
        }
        // 验证验证码
        if (mobile != null) {
            // 验证短信令牌
            try {
                if (!smsService.validate(mobile, validateCode, SmsType.USER_FIND_PASSWORD_CODE)) {
                    return Result.error("短信验证码错误");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("验证短信出错");
            }
        } else {
            return Result.error("手机号为空");
        }
        return Result.success("验证成功");
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/forgetPassword/do", method = RequestMethod.POST)
    @ResponseBody
    public Result resetLoginPassword(RegistVo vo, HttpServletRequest request, HttpServletResponse response, UserType type) {

        final int MAX_LENGTH = 18;
        final int MIX_LENGTH = 6;
        // 获取密码
        vo.setPassword(rsaService.decryptParameter("password", request));
        // 验证密码长度
        if (vo.getPassword().length() > MAX_LENGTH || vo.getPassword().length() < MIX_LENGTH) {
            return Result.error("密码需为6-18位字符");
        }
        // 验证手机号是否在
        if (!userService.isExistMobile(vo.getMobile(), type)) {
            return Result.error("手机号未注册");
        }
        String mobile = vo.getMobile();
        String password = vo.getPassword();
        if (vo.getMobile() == null) {
            return Result.error("手机号不能为空");
        }

        // 验证验证码
        if(mobile != null){
            // 验证短信令牌
            try{
                if(!smsService.validate(mobile,vo.getCaptcha(),SmsType.USER_FIND_PASSWORD_CODE)){
                    return Result.error("短信验证码错误");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("验证短信出错");
            }
        }else {
            return Result.error("手机号为空");
        }

        //重置用户密码
        User user = userService.getUserByMobile(mobile, type);
        PasswordHelper.encryptPassword(user, password);
        try {
            user = userService.merge(user);
        } catch (Exception e) {
            logger.error("重置密码失败");
            e.printStackTrace();
            return Result.error("重置密码失败");
        }

        WebUtils.refactorSession(request);
        HttpSession session = request.getSession();

        Map map = new HashMap();
        map.put("sid", session.getId());
        map.put("userId", user.getId());
        map.put("loginTimeFlag", DateUtils.getTime());

        // 保存会员日志
        if (user != null) {
            UserLog userLog = new UserLog(UserLogType.MODIFY, new String("官网修改密码"), user.getLoginName(), WebUtils.getRemoteIp(request), user.getId());
            userLogService.persist(userLog);
        }
        return Result.success("登陆密码重置成功", map);
    }
}