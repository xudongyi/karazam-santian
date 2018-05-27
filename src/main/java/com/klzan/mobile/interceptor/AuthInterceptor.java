package com.klzan.mobile.interceptor;

import com.klzan.p2p.model.AccessToken;
import com.klzan.p2p.model.User;
import com.klzan.p2p.security.user.UserPrincipal;
import com.klzan.p2p.service.token.AccessTokenService;
import com.klzan.p2p.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Interceptor - 移动端UC权限拦截
 * @version 1.0
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    /** 重定向URL */
    private static final String REDIRECT_URL = "/mobile/not_login";

    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
//            ClientType clientType = ClientType.valueOf(request.getHeader("clientType"));

            HttpSession session = request.getSession();
            String sid = request.getHeader("sid");
            if (StringUtils.isBlank(sid)) {
                sid = request.getParameter("sid");
            }
            if(StringUtils.isBlank(sid)){
                response.sendRedirect(request.getContextPath() + REDIRECT_URL);
            }else {
                AccessToken token = accessTokenService.findAppToken(null, sid.trim());
                if(token==null){
                    response.sendRedirect(request.getContextPath() + REDIRECT_URL);
                } else {
                    User user = userService.get(token.getUserId());
                    session.setAttribute(User.PRINCIPAL_ATTR_NAME, new UserPrincipal(user.getId(), user.getLoginName(), user.getType(), user.getName(), user.getMobile()));
                    session.setAttribute("user", user);
                }
            }
//        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }

}