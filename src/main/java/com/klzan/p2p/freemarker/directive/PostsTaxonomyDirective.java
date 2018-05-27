/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.freemarker.directive;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.model.PostsTaxonomy;
import com.klzan.p2p.service.posts.PostsTaxonomyService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

/**
 * 文章类型指令
 */
@Component
public class PostsTaxonomyDirective extends BaseDirective {
    private static Set<String> WHOIS = new HashSet<>();

    static {
        WHOIS.add("parent");
        WHOIS.add("self");
        WHOIS.add("brothers");
        WHOIS.add("children");
    }

    /**
     * 变量名称
     */
    private static final String VARIABLE_NAME = "taxonomies";

    @Inject
    private PostsTaxonomyService postsTaxonomyService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String type = getParam("type", params);
        String slug = getParam("slug", params);
        // whois可以的值为parent, self, brothers, children
        String whois = getParam("whois", params);
        List<PostsTaxonomy> list = new ArrayList<>();
        if (WHOIS.contains(whois)) {
            if (StringUtils.equals(whois, "parent")) {
                PostsTaxonomy taxonomy = postsTaxonomyService.findParent(type, slug);
                list.add(taxonomy);
            } else if (StringUtils.equals(whois, "brothers")) {
                list = postsTaxonomyService.findBrothers(type, slug);
            } else if (StringUtils.equals(whois, "children")) {
                list = postsTaxonomyService.findChildren(type, slug);
            } else {
                PostsTaxonomy taxonomy = postsTaxonomyService.find(type, slug);
                list.add(taxonomy);
            }
        } else {
            list = postsTaxonomyService.findChildren(type, slug);
        }
        setLocalVariable(VARIABLE_NAME, list, env, body);
    }

}