package com.klzan.mobile.controller;

import com.klzan.core.Result;
import com.klzan.core.ResultCode;
import com.klzan.core.exception.SystemException;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.DigestUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.mobile.token.TokenUtils;
import com.klzan.mobile.vo.LoginVo;
import com.klzan.mobile.vo.UserBaseVo;
import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.model.AccessToken;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.token.AccessTokenService;
import com.klzan.p2p.service.user.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 */
@Controller("mobileLoginController")
@RequestMapping("/mobile")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody LoginVo vo, HttpServletRequest request) {
        if (StringUtils.isBlank(vo.getLoginName())) {
            throw new SystemException("用户名不能为空");
        }
        if (StringUtils.isBlank(vo.getPassword())) {
            throw new SystemException("密码不能为空");
        }
        if (vo.getType()==null || "".equals(vo.getType())) {
            throw new SystemException("类型不能为空");
        }
        User user = userService.getUserByLoginName(vo.getLoginName(), vo.getType());
        if (null == user) {
            return Result.error("用户名或密码错误");
        }
        if (!PasswordHelper.verifyPassword(user, DigestUtils.decrypt(vo.getPassword()))) {
            return Result.error("用户名或密码错误");
        }

        AccessToken token = null;
        try {
            token = accessTokenService.findAppToken(user.getId(), null);
            if(token == null){
                token = new AccessToken(user.getId(), vo.getClientType(), tokenUtils.generateToken(request));
                token = accessTokenService.persist(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success("登录失败");
        }

        Map map = new HashMap();
        map.put("userId", user.getId());
        map.put("userKey", TokenUtils.generateUserKey(user));
        map.put("sid", token.getToken());
        map.put("userType",user.getType());
        return Result.success("登录成功", map);
    }

    /**
     * 注销
     */
    @RequestMapping(value = "uc/logout", method = RequestMethod.POST)
    @ResponseBody
    public Result logout(/*@RequestBody LoginVo vo, */HttpServletRequest request) {
//        if (vo.getClientType()==null || "".equals(vo.getClientType())) {
//            throw new SystemException("类型不能为空");
//        }
        String sid = request.getHeader("sid");
        AccessToken token = accessTokenService.findAppToken(null, sid);
        if(token == null){
            return Result.error("未登录");
        }
        accessTokenService.remove(token.getId());
        return Result.success("成功");
    }

    /**
     * 检查登录状态
     * @param userBaseVo
     * @param request
     * @return
     */
    @RequestMapping(value = "uc/checkLoginStatus", method = RequestMethod.POST)
    @ResponseBody
    public Result checkLoginStatus(@RequestBody UserBaseVo userBaseVo, HttpServletRequest request) {
        Integer userId = userBaseVo.getUserId();
        String userKey = userBaseVo.getUserKey();
        String sid = request.getHeader("sid");

        // 用户校验
        User user = userService.get(userId);
        if (null == user || StringUtils.isBlank(sid)) {
            return Result.error("非法操作");
        }

        // userKey校验
        String sUserKey = TokenUtils.generateUserKey(user);
        if (!StringUtils.equals(userKey, sUserKey)) {
            return Result.error("非法操作");
        }

        AccessToken token = null;
        try {
            token = accessTokenService.findAppToken(null, sid);
            if(token == null){
                token = new AccessToken(user.getId(), userBaseVo.getClientType(), tokenUtils.generateToken(request));
                token = accessTokenService.persist(token);
            }
        } catch (UnknownSessionException e) {
            logger.warn(String.format("UserId[%d]-userKey[%s]-SessionId[%s]不存在", userId, userKey, sid));
        }

        Map<String, Object> map = new HashedMap();
        map.put("userId", user.getId());
        map.put("userKey", sUserKey);
        map.put("sid", token.getToken());
        map.put("loginTimeFlag", DateUtils.getTime());
        map.put("userType",user.getType());
        return Result.success("登录成功", map);
    }

    /**
     * 未登录
     */
    @RequestMapping(value = "/not_login", method = RequestMethod.GET)
    @ResponseBody
    public Result not_login() {
        return Result.error(ResultCode.APP_NOT_LOGIN);
    }

}
