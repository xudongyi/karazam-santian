package com.klzan.p2p.controller;

import com.klzan.core.Result;
import com.klzan.core.exception.SystemException;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.enums.CaptchaType;
import com.klzan.p2p.enums.UserStatus;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.User;
import com.klzan.p2p.security.user.UserPrincipal;
import com.klzan.p2p.service.captcha.CaptchaService;
import com.klzan.p2p.service.user.UserService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPublicKey;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController extends BaseController {
    @Autowired
    private RSAService rsaService;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "login")
    public String index(HttpServletRequest request, Model model) {
        WebUtils.refactorSession(request);
        HttpSession session = request.getSession();
        UserPrincipal principal = (UserPrincipal) session.getAttribute(User.PRINCIPAL_ATTR_NAME);
        if (null != principal) {
            return "redirect:/uc";
        }
        // 密钥
        RSAPublicKey publicKey = rsaService.generateKey(request);
        String redirectUrl = request.getParameter("redirectUrl");
        if (StringUtils.isNotBlank(redirectUrl)) {
            model.addAttribute("redirectUrl", redirectUrl);
        }
        model.addAttribute("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        model.addAttribute("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
        return "login/index";
    }

    @PostMapping("login")
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response, String username, String password, UserType type) {
        try {
            // 重构Session
            WebUtils.refactorSession(request);
            HttpSession session = request.getSession();
            // 改为全部抛出异常，避免ajax csrf token被刷新
            if (StringUtils.isBlank(username)) {
                throw new SystemException("用户名不能为空");
            }
            if (StringUtils.isBlank(password)) {
                throw new SystemException("密码不能为空");
            }
            password = rsaService.decryptParameter("password", request);
            rsaService.removePrivateKey(request);
            User user = userService.getUserByLoginName(username, type);

            if (null == user) {
                // 异常：账户不存在
                throw new UnknownAccountException();
            }
            if (user.getType()!=type){
                // 异常：账户不存在
                throw new UnknownAccountException();
            }
            if (!PasswordHelper.verifyPassword(user, password)) {
                // 异常：认证错误
                throw new AuthenticationException();
            }
            if (user.getStatus() == UserStatus.DISABLE) {
                // 异常：账户禁用
                throw new DisabledAccountException();
            }
            if (user.getStatus() == UserStatus.LOCKED) {
                // 异常：账户锁定
                throw new LockedAccountException();
            }

            user.setLoginCount(user.getLoginCount()==null?1:user.getLoginCount());
            user.setPreviousVisit(user.getLastVisit());
            user.setLastVisit(new Timestamp(new Date().getTime()));
            userService.merge(user);

            session.setAttribute(User.PRINCIPAL_ATTR_NAME, new UserPrincipal(user.getId(), user.getLoginName(), user.getType(), user.getName(), user.getMobile()));
            session.setAttribute("user", user);
            WebUtils.addCookie(request, response, User.USERNAME_COOKIE_NAME, user.getLoginName());
            String redirectUrl = request.getParameter("redirectUrl");
            Map map = new HashMap();
            map.put("sid", session.getId());
            map.put("userId", user.getId());
            map.put("loginTimeFlag", DateUtils.getTime());
            if (StringUtils.isNotBlank(redirectUrl)) {
                map.put("redirectUrl", redirectUrl);
            }
            return Result.success("登录成功", map);
        } catch (Exception e) {
            String message = e.getClass().getSimpleName();
            Result result = Result.error("未知错误");
            RSAPublicKey publicKey = rsaService.generateKey((HttpServletRequest) request);
            Map<String, String> modules = new HashedMap();
            modules.put("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
            modules.put("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
            if (StringUtils.equals(message, "UnknownAccountException")) {
                result = Result.error("账户或密码错误", modules);
            } else if (StringUtils.equals(message, "DisabledAccountException")) {
                result = Result.error("账户已被禁用", modules);
            } else if (StringUtils.equals(message, "LockedAccountException")) {
                result = Result.error("账户已被锁定", modules);
            } else if (StringUtils.equals(message, "IncorrectCredentialsException")) {
                result = Result.error("账户或密码错误", modules);
            } else if (StringUtils.equals(message, "AuthenticationException")) {
                result = Result.error("账户或密码错误", modules);
            }
            return result;
        } catch (Throwable e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    /**
     * 注销
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        session.removeAttribute(User.PRINCIPAL_ATTR_NAME);
        session.removeAttribute("user");
        WebUtils.removeCookie(request, response, User.USERNAME_COOKIE_NAME, null, null);
        return "redirect:/";
    }

}
