package com.klzan.p2p.controller.admin.goods;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.model.Goods;
import com.klzan.p2p.model.GoodsCategory;
import com.klzan.p2p.service.goods.GoodsCategoryService;
import com.klzan.p2p.service.goods.GoodsService;
import com.klzan.p2p.vo.goods.GoodsCategoryVo;
import com.klzan.p2p.vo.user.UserVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 商品分类
 * @author chenxinglin
 */
@Controller("adminGoodsCategoryController")
@RequestMapping("admin/goods_category")
public class GoodsCategoryController extends BaseAdminController {

    @Inject
    private GoodsCategoryService goodsCategoryService;
    @Inject
    private GoodsService goodsService;

    /**
     * 列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("mall:goodscategory:list")
    public String list() {
        return "/admin/goods_category/list";
    }

    /**
     * 列表数据
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("mall:goodscategory:list")
    @ResponseBody
    public Map<String, Object> pageList(PageCriteria criteria, HttpServletRequest request) {
        ParamsFilter.buildFromHttpRequest(request, criteria);
        PageResult<GoodsCategory> pageResult = goodsCategoryService.findPage(criteria);
        return (Map<String, Object>) getPageResult(pageResult);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("mall:goodsCategory:add")
    public String add(ModelMap model, HttpServletRequest request) {
        model.addAttribute("action", "save");
        String parentId = request.getParameter("parentId");
        if (parentId != null) {
            model.addAttribute("goodsCategory", goodsCategoryService.get(new Integer(parentId)));
        }
        return "/admin/goods_category/add";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("mall:goodsCategory:add")
    @ResponseBody
    public Result save(GoodsCategoryVo vo, RedirectAttributes redirectAttributes) {
//        // 验证名称是否存在
//        if (goodsCategoryService.nameExists(null, goodsCategoryVo.getName())) {
//            return Result.error("名称已存在");
//        }
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setName(vo.getName());
        goodsCategory.setSeoTitle(vo.getSeoTitle());
        goodsCategory.setSeoKeywords(vo.getSeoKeywords());
        goodsCategory.setSeoDescription(vo.getSeoDescription());
        goodsCategory.setSort(vo.getSort());
        if(vo.getParent()==null){
            goodsCategory.setGrade(0);
        }else {
            GoodsCategory parent = goodsCategoryService.get(vo.getParent());
            goodsCategory.setParent(vo.getParent());
            goodsCategory.setGrade(parent.getGrade()+1);
        }
        goodsCategoryService.persist(goodsCategory);
        return Result.success("保存成功");
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @RequiresPermissions("mall:goodsCategory:update")
    public String edit(@PathVariable Integer id, ModelMap model) {
        GoodsCategory goodsCategory = goodsCategoryService.get(id);
        model.addAttribute("goodsCategory", goodsCategory);
        if (goodsCategory.getParent() != null) {
            GoodsCategory parentgoodsCategory = goodsCategoryService.get(goodsCategory.getParent());
            model.addAttribute("parentgoodsCategory", parentgoodsCategory);
        }
        model.addAttribute("action", "update");
        model.addAttribute("pk", id);
        return "/admin/goods_category/edit";
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @RequiresPermissions("mall:goodsCategory:update")
    @ResponseBody
    public Result update(@PathVariable Integer id, GoodsCategoryVo vo) {
        GoodsCategory goodsCategory = goodsCategoryService.get(id);
//        // 验证文章分类是否存在
//        if (pgoodsCategory == null || pgoodsCategory.getBuiltin()) {
//            return Result.error("文章分类不存在");
//        }
        goodsCategory.setName(vo.getName());
        goodsCategory.setSeoTitle(vo.getSeoTitle());
        goodsCategory.setSeoKeywords(vo.getSeoKeywords());
        goodsCategory.setSeoDescription(vo.getSeoDescription());
        goodsCategory.setSort(vo.getSort());
        goodsCategoryService.update(goodsCategory);
        return Result.success("更新成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("mall:goodsCategory:delete")
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        GoodsCategory pgoodsCategory = goodsCategoryService.get(id);
        if (pgoodsCategory == null) {
            return Result.error("参数错误");
        }
        List<Goods> goodsList = goodsService.findAllList(pgoodsCategory.getId());
        if (goodsList != null && goodsList.size()>0) {
            return Result.error("存在商品，不能删除");
        }
        goodsCategoryService.remove(id);
        return Result.success("删除成功");
    }

    /**
     * 借款人集合(JSON)
     */
    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public List<GoodsCategory> borrowerAsJson() {
        return goodsCategoryService.findValidAll();
    }
    

}
