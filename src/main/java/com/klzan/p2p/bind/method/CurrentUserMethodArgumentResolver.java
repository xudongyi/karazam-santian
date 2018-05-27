/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.bind.method;

import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.model.User;
import com.klzan.p2p.security.user.UserPrincipal;
import com.klzan.p2p.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于绑定@FormModel的方法参数解析器
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    public CurrentUserMethodArgumentResolver() {
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        //这里暂时把User对象放在session中
        UserPrincipal user = (UserPrincipal) request.getSession().getAttribute(User.PRINCIPAL_ATTR_NAME);
        User currentUser = userService.get(user.getId());
        request.getSession().setAttribute("user", currentUser);
        return currentUser;
    }
}
