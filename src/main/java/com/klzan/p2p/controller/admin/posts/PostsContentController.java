package com.klzan.p2p.controller.admin.posts;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.PinyinUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.PostsTaxonomyType;
import com.klzan.p2p.enums.PostsTemplateType;
import com.klzan.p2p.model.PostsContent;
import com.klzan.p2p.model.PostsTaxonomy;
import com.klzan.p2p.service.posts.PostsContentService;
import com.klzan.p2p.service.posts.PostsTaxonomyService;
import com.klzan.p2p.util.PostsTemplateUtils;
import com.klzan.p2p.vo.posts.PostsContentVo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sue on 2017/5/29.
 */
@Controller("adminPostsContentController")
@RequestMapping("admin/posts/content")
public class PostsContentController extends BaseAdminController {

    @Autowired
    private PostsContentService postsContentService;

    @Autowired
    private PostsTaxonomyService postsTaxonomyService;

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("templates", PostsTemplateUtils.getTemplate(PostsTemplateType.CONTENT));
        return template("/posts/content/form");
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(PostsContentVo contentVo, String tagIdsStr) {
        if (StringUtils.isNotBlank(tagIdsStr)) {
            String[] tags = StringUtils.splitString(tagIdsStr, ",");
            List<Integer> tagIds = new ArrayList<>();
            for (String tag : tags) {
                tagIds.add(Integer.parseInt(tag));
            }
            contentVo.setTagIds(tagIds);
        }
        contentVo.setText(HtmlUtils.htmlUnescape(contentVo.getText()));
        Boolean existSlug = postsContentService.isExistSlug(contentVo.getSlug(), null);
        if (existSlug) {
            return Result.error();
        }
        postsContentService.createContent(contentVo);

        return Result.success();
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(@RequestParam Integer contentId, Model model) {
        model.addAttribute("templates", PostsTemplateUtils.getTemplate(PostsTemplateType.CONTENT));
        model.addAttribute("content", postsContentService.get(contentId));
        return template("/posts/content/form");
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(PostsContentVo contentVo, String tagIdsStr) {
        if (StringUtils.isNotBlank(tagIdsStr)) {
            String[] tags = StringUtils.splitString(tagIdsStr, ",");
            List<Integer> tagIds = new ArrayList<>();
            for (String tag : tags) {
                tagIds.add(Integer.parseInt(tag));
            }
            contentVo.setTagIds(tagIds);
        }
        contentVo.setText(HtmlUtils.htmlUnescape(contentVo.getText()));
        Boolean existSlug = postsContentService.isExistSlug(contentVo.getSlug(), contentVo.getId());
        if (existSlug) {
            return Result.error();
        }
        postsContentService.updateContent(contentVo);
        return Result.success();
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        return template("/posts/content/index");
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public PageResult<PostsContent> json(PageCriteria criteria, HttpServletRequest request) {
        String category = request.getParameter("categoryId");
        if (StringUtils.isNotBlank(category)) {
            PostsContentVo postsContentVo = new PostsContentVo();
            postsContentVo.setTaxonomyId(Integer.parseInt(category));
            return postsContentService.findPageByTaxonomy(criteria, postsContentVo);
        }
        return postsContentService.findPage(criteria);
    }

    @RequestMapping(value = "translate")
    @ResponseBody
    public Result translate(String title, Integer contentId) {
        String slug = PinyinUtils.hanziToPinyin(title, "_");
        slug = StringUtils.replaceAll(slug, "(,|，|\\s){1,}", "");
        Boolean exist = postsContentService.isExistSlug(slug, contentId);
        int flag = 0;
        while (exist) {
            slug = PinyinUtils.hanziToPinyin(title, "_") + "_" + flag;
            exist = postsContentService.isExistSlug(slug, contentId);
            flag++;
        }
        return Result.success(SUCCESS_MSG, slug);
    }

    @RequestMapping(value = "isExistSlug")
    @ResponseBody
    public Result isExistSlug(String slug, Integer contentId) {
        slug = StringUtils.replaceAll(slug, "(,|，|\\s){1,}", "");
        Boolean exist = postsContentService.isExistSlug(slug, contentId);
        String tmpSlug = slug;
        int flag = 0;
        while (exist) {
            slug = tmpSlug + "_" + flag;
            exist = postsContentService.isExistSlug(slug, contentId);
            flag++;
        }
        return Result.success(SUCCESS_MSG, slug);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        try {
            postsContentService.remove(id);
            return Result.success();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return Result.error(e.getMessage());
        }
    }

    @RequestMapping(value = "status/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateStatus(@PathVariable Integer id, String status) {
        try {
            postsContentService.updateStatus(id, status);
            return Result.success();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return Result.error(e.getMessage());
        }
    }

    @RequestMapping(value = "taxonomy.json", method = RequestMethod.GET)
    @ResponseBody
    public Result getTaxonomyJson(Integer contentId) {
        List<PostsTaxonomy> categoryTaxonomies = postsTaxonomyService.findContentTaxonomy(PostsTaxonomyType.CATEGORY, contentId);
        List<PostsTaxonomy> featureTaxonomies = postsTaxonomyService.findContentTaxonomy(PostsTaxonomyType.FEATURE, contentId);
        List<PostsTaxonomy> tagTaxonomies = postsTaxonomyService.findContentTaxonomy(PostsTaxonomyType.TAG, contentId);
        Map map = new HashMap();
        map.put("categories", categoryTaxonomies);
        map.put("features", featureTaxonomies);
        map.put("tags", tagTaxonomies);
        return Result.success(SUCCESS_MSG, map);
    }
}
