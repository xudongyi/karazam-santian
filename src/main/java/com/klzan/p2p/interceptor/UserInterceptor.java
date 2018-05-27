package com.klzan.p2p.interceptor;

import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.model.User;
import com.klzan.p2p.security.user.UserPrincipal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * Interceptor - 会员
 * @version 1.0
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

    /** "重定向URL"参数名称 */
    private static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";
    /** 默认登录URL */
    private static final String DEFAULT_LOGIN_URL = "/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // 判断是否当前身份已登录
        UserPrincipal principal = (UserPrincipal) session.getAttribute(User.PRINCIPAL_ATTR_NAME);
        if (principal != null) {
            return true;
        } else {
            // 注销Cookie
            WebUtils.removeCookie(request, response, User.USERNAME_COOKIE_NAME, null, null);
            // 判断是否为AJAX请求（请求方式：AJAX异步请求）
            String requestType = request.getHeader("X-Requested-With");
            if (StringUtils.equalsIgnoreCase(requestType, "XMLHttpRequest")) {
                // 限制访问
                response.addHeader("loginStatus", "Access Denied");
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                // 判断是否为GET方式请求
                String requestMethod = request.getMethod();
                if (StringUtils.equalsIgnoreCase(requestMethod, "GET")) {
                    // 设置重定向URL
                    String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?"
                            + request.getQueryString() : request.getRequestURI();
                    response.sendRedirect(request.getContextPath() + DEFAULT_LOGIN_URL + "?"
                            + REDIRECT_URL_PARAMETER_NAME + "=" + URLEncoder.encode(redirectUrl, "UTF-8"));
                } else {
                    response.sendRedirect(request.getContextPath() + DEFAULT_LOGIN_URL);
                }
            }
            return false;
        }
    }

}