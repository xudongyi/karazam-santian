package com.klzan.mobile.controller;

import com.klzan.core.Result;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.DigestUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.mobile.vo.RegistVo;
import com.klzan.mobile.token.TokenUtils;
import com.klzan.mobile.vo.UserRegistrationVo;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.common.service.RSAService;
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
import com.klzan.p2p.service.user.impl.UserDeviceService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 注册
 */
@Controller("mobileRegistController")
@RequestMapping("/mobile/regist")
public class RegistController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserLogService userLogService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private TokenUtils tokenUtils;
    @Resource
    private RSAService rsaService;

    /**
     * 注册数据提交
     * @param vo
     * @param request
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Result register(@RequestBody RegistVo vo, HttpServletRequest request) {

        if(vo == null){
            return Result.error("参数错误");
        }

        String mobile = vo.getMobile();
        String password = vo.getPassword();
        String referrer = vo.getReferrer();
//        String captcha = vo.getCaptcha();
        String smsCode = vo.getSmsCode();
        UserType type = vo.getType();
        ClientType clientType = vo.getClientType();

        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password) || type==null || clientType==null) {
            return Result.error("参数错误");
        }

        //检查手机号码
        if(userService.isExistMobile(mobile, type)){
            return Result.error("手机号已存在");
        }

        //检查推荐人
        if(referrer!=null && !referrer.equals("") && !userService.isExistMobile(referrer, null)){
            return Result.error("推荐人不存在");
        }

        if (StringUtils.isNotBlank(referrer)){
            User u = userService.getUserByMobile(referrer, UserType.GENERAL);
            if (u == null || u.getDeleted()) {
                logger.error("推荐人不存在或非个人用户");
                return Result.error("推荐人不存在或非个人用户");
            }
        }

        try {
            String sid = "";
            Boolean validate = smsService.validate(mobile, smsCode, SmsType.USER_REGIST_CODE);
            if (validate) {
                password = DigestUtils.decrypt(vo.getPassword());
                User user = new User(mobile, password, vo.getType());
                user.setMobile(mobile);
                PasswordHelper.encryptPassword(user, password);
                try {
                    user = userService.createUser(user, null, vo.getReferrer());

                    UserLog userLog = new UserLog(UserLogType.REGIST, new String(vo.getClientType().getDisplayName() + "注册"), user.getLoginName(), WebUtils.getRemoteIp(request), user.getId());
                    userLogService.persist(userLog);

                    sid = tokenUtils.generateToken(request);
                    AccessToken token = accessTokenService.findAppToken(user.getId(), sid);
                    if(token == null){
                        token = new AccessToken(user.getId(), clientType, sid);
                        accessTokenService.persist(token);
                    }else {
                        throw new RuntimeException("系统错误");
                    }
                } catch (Exception e) {
                    logger.error("新增用户失败");
                    e.printStackTrace();
                    return Result.error("新增用户失败");
                }

                Map<String, Object> map = new HashedMap();
                map.put("userId", user.getId());
                map.put("userKey", TokenUtils.generateUserKey(user));
                map.put("sid", sid);
                //todo 添加消息推荐用户

                return Result.success("注册成功", map);
            } else {
                return Result.error("短信验证码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("注册失败");
        }
    }

    /**
     * 注册发送短信
     */
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @ResponseBody
    public Result textingApply(@RequestParam String mobile, ModelMap mode) {
        if(mobile == null){
            return Result.error("参数错误");
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
     * 检查手机号码
     */
    @RequestMapping(value = "/checkMobile", method = RequestMethod.POST)
    @ResponseBody
    public Result checkMobile(@RequestParam String mobile, @RequestParam UserType type) {
        if (org.apache.commons.lang3.StringUtils.isBlank(mobile)) {
            return Result.error("手机号不能为空");
        }
        // 验证手机号码是否唯一
        if (userService.isExistMobile(mobile, type)) {
            return Result.error("手机号已被注册");
        } else {
            return Result.success("手机号可用");
        }
    }

    /**
     * 检查推荐人是否存在
     */
    @RequestMapping(value = "/checkReferrer", method = RequestMethod.POST)
    @ResponseBody
    public Result checkReferrer(@RequestParam String referrer) {
        if (org.apache.commons.lang3.StringUtils.isBlank(referrer)) {
            return Result.error("推荐人为空");
        }
        // 验证手机号码是否唯一
        if (!userService.isExistMobile(referrer, null)) {
            return Result.error("推荐人不存在");
        } else {
            return Result.success("推荐人可用");
        }
    }

    /**
     * 注册协议
     * @return
     */
    @RequestMapping("/agreement")
    public String agreement() {
        return "agreement/regist_agreement";
    }
    //扫码注册
    @RequestMapping(value = "/scanSubmit", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Result scanSubmit(RegistVo vo, HttpServletRequest request) {

        if(vo == null){
            return Result.error("参数错误");
        }

        String mobile = vo.getMobile();
        String password = vo.getPassword();
        String referrer = vo.getReferrer();
        String captcha = vo.getCaptcha();
        String smsCode = vo.getSmsCode();
        UserType type = vo.getType();
        ClientType clientType =ClientType.MOBILEWEB ;
        vo.setClientType(ClientType.MOBILEWEB);

        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password) || type==null) {
            return Result.error("参数错误");
        }

        //检查手机号码
        if(userService.isExistMobile(mobile, type)){
            return Result.error("手机号已存在");
        }

        //检查推荐人
        if(referrer!=null && !referrer.equals("") && !userService.isExistMobile(referrer, null)){
            return Result.error("推荐人不存在");
        }

        try {
            String sid = "";
            Boolean validate = smsService.validate(mobile, captcha, SmsType.USER_REGIST_CODE);
            if (validate) {

                password = rsaService.decryptParameter("password",request);
                User user = new User(mobile, password, vo.getType());
                user.setMobile(mobile);
                PasswordHelper.encryptPassword(user, password);
                try {
                    user = userService.createUser(user, null, vo.getReferrer());

                    UserLog userLog = new UserLog(UserLogType.REGIST, new String(vo.getClientType().getDisplayName() + "注册"), user.getLoginName(), WebUtils.getRemoteIp(request), user.getId());
                    userLogService.persist(userLog);

                    sid = tokenUtils.generateToken(request);
                    AccessToken token = accessTokenService.findAppToken(user.getId(), sid);
                    if(token == null){
                        token = new AccessToken(user.getId(), clientType, sid);
                        accessTokenService.persist(token);
                    }else {
                        throw new RuntimeException("系统错误");
                    }
                } catch (Exception e) {
                    logger.error("新增用户失败");
                    e.printStackTrace();
                    return Result.error("新增用户失败");
                }

                Map<String, Object> map = new HashedMap();
                map.put("userId", user.getId());
                map.put("userKey", TokenUtils.generateUserKey(user));
                map.put("sid", sid);
                //todo 添加消息推荐用户

                return Result.success("注册成功", map);
            } else {
                return Result.error("短信验证码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("注册失败");
        }
    }


}