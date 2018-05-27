/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.freemarker.directive;

import com.klzan.p2p.model.PostsContent;
import com.klzan.p2p.service.posts.PostsContentService;
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
 * 文章指令
 */
@Component
public class PostsContentsDirective extends BaseDirective {

    /**
     * 变量名称
     */
    private static final String VARIABLE_NAME = "contents";

    @Inject
    private PostsContentService postsContentService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String taxonomyType = getParam("taxonomyType", params);
        String taxonomySlug = getParam("taxonomySlug", params);
        Integer listSize = Integer.parseInt(getParam("listSize", params));
        List<PostsContent> list = postsContentService.findList(taxonomyType, taxonomySlug, listSize);
        setLocalVariable(VARIABLE_NAME, list, env, body);
    }

}