/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.freemarker.directive;

import com.klzan.p2p.enums.FriendLinkType;
import com.klzan.p2p.service.links.LinksService;
import com.klzan.p2p.vo.links.LinksVo;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 模板指令 - 友情链接
 */
@Component
public class LinksDirective extends BaseDirective {

    /**
     * 变量名称
     */
    private static final String VARIABLE_NAME = "friendLinks";

    @Inject
    private LinksService linksService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String ident = getParam("type", params);
        List<LinksVo> list = linksService.findList(FriendLinkType.valueOf(ident));
        setLocalVariable(VARIABLE_NAME, list, env, body);
    }

}