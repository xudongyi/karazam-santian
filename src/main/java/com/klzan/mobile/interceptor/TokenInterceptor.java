package com.klzan.mobile.interceptor;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.ClientType;
import com.klzan.p2p.model.AccessToken;
import com.klzan.p2p.service.token.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Interceptor - 移动端令牌拦截
 * @version 1.0
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            String sid = request.getHeader("sid");
            System.out.println("====================================================");
            System.out.println("TokenInterceptor：" + sid + "---" + new Date());
            if (StringUtils.isBlank(sid)) {
                sid = request.getParameter("sid");
            }
            String servletPath = request.getServletPath();
            System.out.println("servletPath: "+servletPath);
            System.out.println("====================================================");
            if(servletPath.contains("checkLoginStatus")){
                return true;
            }
            if(!StringUtils.isBlank(sid)){
                AccessToken token = accessTokenService.findAppToken(null, sid);
//                if(token!=null){
//                    token.setLastActiveDate(new Date());
//                    token = accessTokenService.merge(token);
//                    accessTokenService.refresh(token);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}