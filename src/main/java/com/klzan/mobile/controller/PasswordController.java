package com.klzan.mobile.controller;

import com.klzan.core.Result;
import com.klzan.core.util.DigestUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.mobile.vo.LoginVo;
import com.klzan.mobile.vo.PasswordFindVo;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.enums.UserLogType;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserLog;
import com.klzan.p2p.service.captcha.CaptchaService;
import com.klzan.p2p.service.sms.SmsService;
import com.klzan.p2p.service.user.UserLogService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.user.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 找回密码
 */
@Controller("mobilePasswordController")
@RequestMapping("/mobile")
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

    @RequestMapping(value = "/forgetPassword")
    @ResponseBody
    @Transactional
    public Result findPWdByMobile(@RequestBody PasswordFindVo passwordFindVo, HttpServletRequest request) {
        String mobile = passwordFindVo.getMobile();
        String smsCode = passwordFindVo.getSmsCode();
        String password = passwordFindVo.getPassword();
        UserType type = passwordFindVo.getType();
        if (org.apache.commons.lang3.StringUtils.isBlank(mobile)) {
            return Result.error("手机号不能为空");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(smsCode)) {
            return Result.error("验证码不能为空");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(password)) {
            return Result.error("密码不能为空");
        }
        if (type == null) {
            return Result.error("用户类型不能为空");
        }
        User user = userService.getUserByMobile(mobile, type);
        if (null == user) {
            return Result.error("用户不存在");
        }
        try {
            Boolean validate = smsService.validate(mobile, smsCode, SmsType.USER_FIND_PASSWORD_CODE);
            if (validate) {
                password = DigestUtils.decrypt(password);
                PasswordHelper.encryptPassword(user, password);
                userService.update(user);
                UserLog userLog = new UserLog(UserLogType.MODIFY,new String("移动端忘记登陆密码"),user.getLoginName(), WebUtils.getRemoteIp(request),user.getId());
                userLogService.persist(userLog);
                return Result.success("修改成功");
            }
            return Result.error("短信验证失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(String.format("%s修改密码失败", mobile), e);
            return Result.error();
        }
    }

    /**
     * 检查手机号码
     */
    @RequestMapping(value = "/checkMobile", method = RequestMethod.POST)
    @ResponseBody
    public Result checkMobile(String mobile,UserType type) {
        if (org.apache.commons.lang3.StringUtils.isBlank(mobile)) {
            return Result.error("手机号不能为空");
        }
        // 验证手机号码是否唯一
        if (userService.isExistMobile(mobile, type)) {
            return Result.success("成功");
        } else {
            return Result.error("手机号不存在");
        }
    }
    /*验证登录密码*/
    @RequestMapping(value = "/uc/verifyPassword",method = RequestMethod.POST)
    @ResponseBody
     public Result verifyPassword(@CurrentUser User user,@RequestBody UserVo userVo){

        if(StringUtils.isBlank(userVo.getPassword())){
           return Result.error("参数错误");
        }
        if(PasswordHelper.verifyPassword(user, DigestUtils.decrypt(userVo.getPassword())))
        {
            return Result.success("验证成功");
        }
        else{
            return Result.error("验证不通过");
        }
    }

}