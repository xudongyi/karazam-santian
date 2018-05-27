package com.klzan.p2p.controller;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PostsContentStatus;
import com.klzan.p2p.model.PostsContent;
import com.klzan.p2p.model.PostsTaxonomy;
import com.klzan.p2p.service.posts.PostsContentService;
import com.klzan.p2p.service.posts.PostsTaxonomyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sue on 2017/5/29.
 */
@Controller
@RequestMapping("${web.url.content}")
public class PostsContentController extends BaseController {

    @Autowired
    private PostsContentService postsContentService;

    @Autowired
    private PostsTaxonomyService postsTaxonomyService;

    @Override
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, null);
    }

    @RequestMapping("{slug}")
    public String content(@PathVariable String slug, Model model) {
        PostsContent content = postsContentService.findBySlug(slug);
        if (null == content) {
            return "posts/error";
        }
        if (!StringUtils.equals(content.getStatus(), PostsContentStatus.PUBLISH.name())) {
            return "posts/error";
        }
        PostsTaxonomy taxonomy = postsTaxonomyService.get(content.getCategoryId());
        postsContentService.updateViewCount(content.getId());
        model.addAttribute("taxonomy", taxonomy);
        model.addAttribute("content", content);
        return "posts/content/" + content.getTemplate();
    }

    @RequestMapping("{slug}/json")
    @ResponseBody
    public Result contentJson(@PathVariable String slug) {
        PostsContent content = postsContentService.findBySlug(slug);
        if (null == content) {
            return Result.error();
        }
        postsContentService.updateViewCount(content.getId());
       return Result.success(SUCCESS_MSG, content);
    }

    @RequestMapping("{contentId}.json")
    @ResponseBody
    public Result contentJson(@PathVariable Integer contentId) {
        PostsContent content = postsContentService.get(contentId);
        if (null == content) {
            return Result.error();
        }
        postsContentService.updateViewCount(content.getId());
        return Result.success(SUCCESS_MSG, content);
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Result json(PageCriteria criteria) {
        PageResult<PostsContent> page = postsContentService.findPage(criteria);
        return Result.success(SUCCESS_MSG, page);
    }

    @RequestMapping(value = "addViewCount", method = RequestMethod.GET)
    @ResponseBody
    public Result addViewCount(Integer contentId) {
        postsContentService.updateViewCount(contentId);
        return Result.success(SUCCESS_MSG);
    }
}
