package com.klzan.p2p.controller.admin.posts;

import com.klzan.core.Result;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.PinyinUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.PostsTaxonomyType;
import com.klzan.p2p.enums.PostsTemplateType;
import com.klzan.p2p.enums.TreeNodeState;
import com.klzan.p2p.model.PostsContent;
import com.klzan.p2p.model.PostsTaxonomy;
import com.klzan.p2p.service.posts.PostsContentService;
import com.klzan.p2p.service.posts.PostsTaxonomyService;
import com.klzan.p2p.util.PostsTemplateUtils;
import com.klzan.p2p.vo.posts.PostsTaxonomyVo;
import com.klzan.p2p.vo.posts.TaxonomyTree;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sue on 2017/5/29.
 */
@Controller("adminPostsTaxonomyController")
@RequestMapping("admin/posts/taxonomy")
public class PostsTaxonomyController extends BaseAdminController {
    private static Map<String, String> types = PostsTaxonomyType.getTypes();

    @Autowired
    private PostsTaxonomyService postsTaxonomyService;

    @Autowired
    private PostsContentService postsContentService;

    @RequestMapping(value = "{taxonomy}", method = RequestMethod.GET)
    public String index(@PathVariable String taxonomy, Model model) {
        if (!types.containsKey(taxonomy)) {
            throw new BusinessProcessException("参数错误");
        }
        model.addAttribute("templates", PostsTemplateUtils.getTemplate(PostsTemplateType.valueOf(taxonomy.toUpperCase())));
        return template("/posts/taxonomy/" + taxonomy);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(PostsTaxonomyVo taxonomy) {
        Boolean existSlug = postsTaxonomyService.isExistSlug(taxonomy.getType(), taxonomy.getSlug(), null);
        if (existSlug) {
            return Result.error();
        }
        postsTaxonomyService.createTaxonomy(taxonomy);
        return Result.success();
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    @ResponseBody
    public Result update(@RequestParam Integer taxonomyId) {
        if (null == taxonomyId) {
            return Result.error();
        }
        return Result.success(SUCCESS_MSG, postsTaxonomyService.get(taxonomyId));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(PostsTaxonomyVo taxonomy) {
        try {
            postsTaxonomyService.updateTaxonomy(taxonomy);
            return Result.success();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return Result.error(e.getMessage());
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result delete(@PathVariable("id") Integer id) {
        try {
            PostsTaxonomy taxonomy = postsTaxonomyService.get(id);
            List<PostsTaxonomy> children = postsTaxonomyService.findChildren(id);
            if (!children.isEmpty()) {
                return Result.error("[" + taxonomy.getTitle() + "]包含有下级分类不能删除");
            }
            List<PostsContent> contents = postsContentService.findList(id);
            if (!contents.isEmpty()) {
                return Result.error("[" + taxonomy.getTitle() + "]包含有文章不能删除");
            }
            postsTaxonomyService.remove(id);
            return Result.success();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return Result.error(e.getMessage());
        }
    }

    @RequestMapping(value = "setting", method = RequestMethod.GET)
    public String setting(@RequestParam Integer taxonomyId, Model model) {
        model.addAttribute("taxonomy", postsTaxonomyService.get(taxonomyId));
        return template("posts/taxonomy/setting");
    }

    @RequestMapping(value = "setting", method = RequestMethod.POST)
    @ResponseBody
    public Result setting(PostsTaxonomyVo taxonomy) {
        try {
            postsTaxonomyService.updateTaxonomySetting(taxonomy);
            return Result.success();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return Result.error(e.getMessage());
        }
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public PageResult<TaxonomyTree> json(PostsTaxonomyVo taxonomy, PageCriteria criteria) {
        PageResult<PostsTaxonomy> page = postsTaxonomyService.findPage(criteria, taxonomy);
        PageResult<TaxonomyTree> newPage = new PageResult<>();
        newPage.setTotal(page.getTotal());
        List rows = new ArrayList();
        for (PostsTaxonomy pTaxonomy : page.getRows()) {
            TaxonomyTree taxonomyTree = new TaxonomyTree();
            taxonomyTree.setId(pTaxonomy.getId());
            taxonomyTree.setParentId(pTaxonomy.getParentId());
            taxonomyTree.setTitle(pTaxonomy.getTitle());
            taxonomyTree.setText(pTaxonomy.getText());
            taxonomyTree.setContentCount(pTaxonomy.getContentCount());
            taxonomyTree.setSlug(pTaxonomy.getSlug());
            taxonomyTree.setState(pTaxonomy.getState());

            if (TreeNodeState.hasChildren(pTaxonomy.getState())) {
                recursiveTree(taxonomyTree);
            }
            rows.add(taxonomyTree);
        }
        newPage.setRows(rows);
        return newPage;
    }

    @RequestMapping(value = "select.json", method = RequestMethod.GET)
    @ResponseBody
    public List<TaxonomyTree> selectJson(PostsTaxonomyVo taxonomy) {
        List<PostsTaxonomy> taxonomyList = postsTaxonomyService.findListByType(taxonomy);
        List<TaxonomyTree> taxonomyTrees = new ArrayList<>();
        for (PostsTaxonomy pTaxonomy : taxonomyList) {
            TaxonomyTree taxonomyTree = new TaxonomyTree();
            taxonomyTree.setId(pTaxonomy.getId());
            taxonomyTree.setParentId(pTaxonomy.getParentId());
            taxonomyTree.setTitle(pTaxonomy.getTitle());
            taxonomyTree.setText(pTaxonomy.getText());
            taxonomyTree.setContentCount(pTaxonomy.getContentCount());
            taxonomyTree.setSlug(pTaxonomy.getSlug());
            taxonomyTree.setState(pTaxonomy.getState());

            if (TreeNodeState.hasChildren(pTaxonomy.getState())) {
                recursiveTree(taxonomyTree);
            }
            taxonomyTrees.add(taxonomyTree);
        }
        return taxonomyTrees;
    }

    @RequestMapping(value = "translate")
    @ResponseBody
    public Result translate(@RequestParam String type, String title, Integer taxonomyId) {
        String slug = PinyinUtils.hanziToPinyin(title, "_");
        slug = StringUtils.replaceAll(slug, "(,|，|\\s){1,}", "");
        Boolean exist = postsTaxonomyService.isExistSlug(type, slug, taxonomyId);
        int flag = 0;
        while (exist) {
            slug = PinyinUtils.hanziToPinyin(title, "_") + "_" + flag;
            exist = postsTaxonomyService.isExistSlug(type, slug, taxonomyId);
            flag++;
        }
        return Result.success(SUCCESS_MSG, slug);
    }

    @RequestMapping(value = "isExistSlug")
    @ResponseBody
    public Result isExistSlug(@RequestParam String type, String slug, Integer taxonomyId) {
        slug = StringUtils.replaceAll(slug, "(,|，|\\s){1,}", "");
        Boolean exist = postsTaxonomyService.isExistSlug(type, slug, taxonomyId);
        String tmpSlug = slug;
        int flag = 0;
        while (exist) {
            slug = tmpSlug + "_" + flag;
            exist = postsTaxonomyService.isExistSlug(type, slug, taxonomyId);
            flag++;
        }
        return Result.success(SUCCESS_MSG, slug);
    }

    private void recursiveTree(TaxonomyTree taxonomyTree) {
        List<PostsTaxonomy> children = postsTaxonomyService.findListByParent(taxonomyTree.getId());
        for (PostsTaxonomy child : children) {
            TaxonomyTree childTree = new TaxonomyTree();
            childTree.setId(child.getId());
            childTree.setParentId(child.getParentId());
            childTree.setTitle(child.getTitle());
            childTree.setSlug(child.getSlug());
            childTree.setText(child.getText());
            childTree.setContentCount(child.getContentCount());
            childTree.setState(child.getState());
            taxonomyTree.getChildren().add(childTree);
            recursiveTree(childTree);
        }

    }
}
