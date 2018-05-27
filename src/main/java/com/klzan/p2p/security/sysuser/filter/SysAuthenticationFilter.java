package com.klzan.p2p.security.sysuser.filter;

import com.klzan.core.Result;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.security.sysuser.ShiroSysUser;
import com.klzan.p2p.security.sysuser.realm.SysUserCaptchaToken;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SysAuthenticationFilter extends FormAuthenticationFilter {
    private static final String DEFAULT_PASSWORD_PARAM = "password";
    private static final String DEFAULT_CAPTCHA_PARAM = "captcha";
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    @Autowired
    private RSAService rsaService;

    public String getCaptchaParam() {
        return captchaParam;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        HttpSession session = getSession(request);
        return new SysUserCaptchaToken(username, password.toCharArray(), rememberMe, host, captcha, session);
    }

    protected HttpSession getSession(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        return request.getSession();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestType = request.getHeader("X-Requested-With");

        // 判断是否为登录请求
        if (isLoginRequest(request, response)) {
            // 判断是否包含登录参数
            if (isLoginSubmission(request, response)) {
                // 判断是否为AJAX请求（请求方式：AJAX异步请求）
                if (StringUtils.equalsIgnoreCase(requestType, "XMLHttpRequest")) {
                    return executeLogin(request, response);
                }
                // 限制访问
                response.addHeader("loginStatus", "Access Denied");
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            return true;
        }
        // 保存请求、重定向到登录
        // 判断是否为AJAX请求（请求方式：AJAX异步请求）
        if (StringUtils.equalsIgnoreCase(requestType, "XMLHttpRequest")) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = httpServletResponse.getWriter();
            response.setHeader("sessionstatus", "timeout");
            out.flush();
            out.close();
            return executeLogin(request, response);
        }
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    /**
     * 当登录成功
     * @param token
     * @param subject
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 防止Session Fixation攻击
        Session session = subject.getSession();
        // 销毁Session
        Map<Object, Object> attributes = new HashMap<>();
        Collection<Object> keys = session.getAttributeKeys();
        for (Object key : keys) {
            attributes.put(key, session.getAttribute(key));
        }
        session.stop();
        // 重构Session
        session = subject.getSession();
        for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {// 不是ajax请求
            issueSuccessRedirect(servletRequest, servletResponse);
        } else {
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = httpServletResponse.getWriter();
            ShiroSysUser sysUser = UserUtils.getCurrentShiroSysUser();
            Map map = new HashMap();
            map.put("sid", session.getId());
            map.put("userId", sysUser.getId());
            map.put("loginTimeFlag", DateUtils.getTime());
            map.put("successUrl", getSuccessUrl());
            Result result = Result.success("登录成功", map);
            out.println(JsonUtils.toJson(result));
            out.flush();
            out.close();
        }
        return false;
    }

    /**
     * 当登录失败
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request)
                .getHeader("X-Requested-With"))) {// 不是ajax请求
            setFailureAttribute(request, e);
            return true;
        }
        try {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Content-type", "text/html;charset=UTF-8");
            res.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String message = e.getClass().getSimpleName();
            Result result = Result.error("未知错误");
            RSAPublicKey publicKey = rsaService.generateKey((HttpServletRequest) request);
            Map<String, String> modules = new HashedMap();
            modules.put("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
            modules.put("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
            if (StringUtils.equals(message, "UnsupportedTokenException")) {
                result = Result.error("验证码错误", modules);
            } else if (StringUtils.equals(message, "UnknownAccountException")) {
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

            out.println(JsonUtils.toJson(result));
            out.flush();
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * 获取密码
     *
     * @param servletRequest 应用请求
     * @return 密码
     */
    @Override
    protected String getPassword(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String password = rsaService.decryptParameter(DEFAULT_PASSWORD_PARAM, request);
        rsaService.removePrivateKey(request);
        if (StringUtils.isBlank(password)) {
            password = WebUtils.getCleanParam(request, DEFAULT_PASSWORD_PARAM);
        }
        return password;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

}
