/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.freemarker.method;

import com.klzan.p2p.service.user.UserService;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * 模板方法 - 获取用户信息
 */
@Component("userInfoMethod")
public class UserInfoMethod implements TemplateMethodModelEx {

    @Inject
    private UserService userService;

    @SuppressWarnings("rawtypes")
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null
                && StringUtils.isNotBlank(arguments.get(0).toString())) {
            Integer user = Integer.parseInt(arguments.get(0).toString());
            return userService.getUserById(user);
        }
        return null;
    }

}