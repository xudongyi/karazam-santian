package com.klzan.p2p.controller;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.enums.PostsContentStatus;
import com.klzan.p2p.enums.PostsTaxonomyType;
import com.klzan.p2p.model.PostsContent;
import com.klzan.p2p.model.PostsTaxonomy;
import com.klzan.p2p.service.posts.PostsContentService;
import com.klzan.p2p.service.posts.PostsTaxonomyService;
import com.klzan.p2p.vo.posts.PostsContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sue on 2017/5/29.
 */
@Controller
@RequestMapping("${web.url.taxonomy}")
public class PostsTaxonomyController extends BaseController {
    @Autowired
    private PostsTaxonomyService postsTaxonomyService;

    @Autowired
    private PostsContentService postsContentService;

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

    @RequestMapping("{type}/{slug}")
    public String taxonomy(@PathVariable String type, @PathVariable String slug, HttpServletRequest request, Model model) {
        Map<String, String> types = PostsTaxonomyType.getTypes();
        if (!types.containsKey(type)) {
            return "posts/error";
        }
        PostsTaxonomy taxonomy = postsTaxonomyService.find(type, slug);
        if (null == taxonomy) {
            return "posts/error";
        }
        if (!taxonomy.getDisplay()) {
            return "posts/error";
        }
        Map<String, Object> params = WebUtils.getRequestParamMap(request);
        Integer parentId = taxonomy.getParentId();
        if (null != parentId) {
            PostsTaxonomy taxonomyParent = postsTaxonomyService.get(parentId);
            model.addAttribute("taxonomyParent", taxonomyParent);
        }
        model.addAttribute("taxonomy", taxonomy);
        model.addAttribute("whois", "children");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            model.addAttribute(entry.getKey(), entry.getValue());
        }
        return "posts/taxonomy/" + type + "/" + taxonomy.getTemplate();
    }

    @RequestMapping("{type}/{slug}/json")
    @ResponseBody
    public Result taxonomy(@PathVariable String type, @PathVariable String slug, PageCriteria criteria) {
        Map<String, String> types = PostsTaxonomyType.getTypes();
        if (!types.containsKey(type)) {
            return Result.error();
        }
        PostsTaxonomy taxonomy = postsTaxonomyService.find(type, slug);
        if (null == taxonomy) {
            return Result.error();
        }

        List<PostsTaxonomy> children = postsTaxonomyService.findChildren(type, slug);
        Map<String, Object> map = new HashMap();
        map.put("taxonomy", taxonomy);
        map.put("children", children);
        return Result.success(SUCCESS_MSG, map);
    }

    /**
     * 当前分类的内容
     * @param type
     * @param slug
     * @param criteria
     * @return
     */
    @RequestMapping("{type}/{slug}/contents.json")
    @ResponseBody
    public Result contents(@PathVariable String type, @PathVariable String slug, PageCriteria criteria) {
        Map<String, String> types = PostsTaxonomyType.getTypes();
        if (!types.containsKey(type)) {
            return Result.error();
        }
        PostsTaxonomy taxonomy = postsTaxonomyService.find(type, slug);
        if (null == taxonomy) {
            return Result.error();
        }
        PostsContentVo postsContentVo = new PostsContentVo();
        postsContentVo.setTaxonomyId(taxonomy.getId());
        postsContentVo.setTaxonomyType(taxonomy.getType());
        postsContentVo.setStatus(PostsContentStatus.PUBLISH.name());
        postsContentVo.setExpire(new Date());
        PageResult<PostsContent> page = postsContentService.findPageByTaxonomy(criteria, postsContentVo);
        Map<String, Object> map = new HashMap();
        map.put("taxonomy", taxonomy);
        map.put("contents", page);
        return Result.success(SUCCESS_MSG, map);
    }

    /**
     * 当前分类及子类的内容
     * @param type
     * @param slug
     * @param criteria
     * @return
     */
    @RequestMapping("{type}/{slug}/conts.json")
    @ResponseBody
    public Result conts(@PathVariable String type, @PathVariable String slug, PageCriteria criteria) {
        Map<String, String> types = PostsTaxonomyType.getTypes();
        if (!types.containsKey(type)) {
            return Result.error();
        }
        PostsTaxonomy taxonomy = postsTaxonomyService.find(type, slug);
        if (null == taxonomy) {
            return Result.error();
        }
        PostsContentVo postsContentVo = new PostsContentVo();
        postsContentVo.setTaxonomyId(taxonomy.getId());
        postsContentVo.setListSize(null);
        postsContentVo.setTaxonomyType(taxonomy.getType());
        postsContentVo.setTaxonomyDisplay(true);
        postsContentVo.setStatus(PostsContentStatus.PUBLISH.name());
        PageResult<PostsContent> page = postsContentService.findPageUnderTaxonomy(criteria, postsContentVo);
        Map<String, Object> map = new HashMap();
        map.put("taxonomy", taxonomy);
        map.put("contents", page);
        return Result.success(SUCCESS_MSG, map);
    }

}
