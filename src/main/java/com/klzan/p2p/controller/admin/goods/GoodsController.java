package com.klzan.p2p.controller.admin.goods;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.GoodsType;
import com.klzan.p2p.model.Goods;
import com.klzan.p2p.model.GoodsCategory;
import com.klzan.p2p.service.goods.GoodsCategoryService;
import com.klzan.p2p.service.goods.GoodsService;
import com.klzan.p2p.vo.goods.GoodsCategoryVo;
import com.klzan.p2p.vo.goods.GoodsVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 商品
 * @author chenxinglin
 */
@Controller("adminGoodsController")
@RequestMapping("admin/goods")
public class GoodsController extends BaseAdminController {

    @Inject
    private GoodsCategoryService goodsCategoryService;
    @Inject
    private GoodsService goodsService;

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

    /**
     * 列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("mall:goods:list")
    public String list() {
        return "/admin/goods/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("mall:goods:list")
    @ResponseBody
    public Map<String, Object> pageList(PageCriteria criteria, HttpServletRequest request) {
        ParamsFilter.buildFromHttpRequest(request, criteria);
        PageResult<Goods> pageResult = goodsService.findPage(criteria);
        return (Map<String, Object>) getPageResult(pageResult);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("mall:goods:add")
    public String add(ModelMap model, HttpServletRequest request) {
        model.addAttribute("action", "save");
//        String parentId = request.getParameter("parentId");
//        if (parentId != null) {
//            model.addAttribute("goods", goodsService.get(new Integer(parentId)));
//        }
//        model.addAttribute("goodsCategory", goodsCategoryService.get(1));
        model.addAttribute("types", GoodsType.values());
        return "/admin/goods/add";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("mall:goods:add")
    @ResponseBody
    public Result save(GoodsVo vo) {
//        // 验证名称是否存在
//        if (goodsCategoryService.nameExists(null, goodsCategoryVo.getName())) {
//            return Result.error("名称已存在");
//        }
        Goods goods = new Goods();
        goods.setGoodsCategory(vo.getGoodsCategory());
        goods.setType(vo.getType());
        goods.setDummy(vo.getDummy());
        goods.setHot(vo.getHot());
        goods.setName(vo.getName());
        goods.setOriginalPrice(vo.getOriginalPrice());
        goods.setPrice(vo.getPrice());
        goods.setOriginalPoint(vo.getOriginalPoint());
        goods.setPoint(vo.getPoint());
        goods.setImage(vo.getImage());
        goods.setImageLarge(vo.getImageLarge());
        goods.setImageDetail(vo.getImageDetail());
        goods.setWeight(vo.getWeight());
        goods.setStock(vo.getStock());
//        goods.setAllocatedStock(vo.getAllocatedStock());
        goods.setPutaway(vo.getPutaway());
        goods.setMemo(vo.getMemo());
        goods.setSeoTitle(vo.getSeoTitle());
        goods.setSeoKeywords(vo.getSeoKeywords());
        goods.setSeoDescription(vo.getSeoDescription());
        goods.setIntroduction(vo.getIntroduction());
        goods.setGoodsAttr(vo.getGoodsAttr());
        goods.setSort(vo.getSort());

        goodsService.persist(goods);
        return Result.success("保存成功");
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @RequiresPermissions("mall:goods:update")
    public String edit(@PathVariable Integer id, ModelMap model) {
        Goods goods = goodsService.get(id);
        model.addAttribute("goods", goods);
//        if (goodsCategory.getParent() != null) {
//            GoodsCategory parentgoodsCategory = goodsService.get(goodsCategory.getParent());
//            model.addAttribute("parentgoodsCategory", parentgoodsCategory);
//        }
//        model.addAttribute("goodsCategory", goodsCategoryService.get(1));
        model.addAttribute("types", GoodsType.values());
        model.addAttribute("action", "update");
        model.addAttribute("pk", id);
        return "/admin/goods/edit";
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @RequiresPermissions("mall:goods:update")
    @ResponseBody
    public Result update(@PathVariable Integer id, GoodsVo vo) {
        Goods goods = goodsService.get(id);
        // 验证
        if (goods == null) {
            return Result.error("商品不存在");
        }
        goods.setGoodsCategory(vo.getGoodsCategory());
        goods.setType(vo.getType());
        goods.setDummy(vo.getDummy());
        goods.setHot(vo.getHot());
        goods.setName(vo.getName());
        goods.setOriginalPrice(vo.getOriginalPrice());
        goods.setPrice(vo.getPrice());
        goods.setOriginalPoint(vo.getOriginalPoint());
        goods.setPoint(vo.getPoint());
        goods.setImage(vo.getImage());
        goods.setImageLarge(vo.getImageLarge());
        goods.setImageDetail(vo.getImageDetail());
        goods.setWeight(vo.getWeight());
        goods.setStock(vo.getStock());
//        goods.setAllocatedStock(vo.getAllocatedStock());
        goods.setPutaway(vo.getPutaway());
        goods.setMemo(vo.getMemo());
        goods.setSeoTitle(vo.getSeoTitle());
        goods.setSeoKeywords(vo.getSeoKeywords());
        goods.setSeoDescription(vo.getSeoDescription());
        goods.setIntroduction(vo.getIntroduction());
        goods.setGoodsAttr(vo.getGoodsAttr());
        goods.setSort(vo.getSort());

        goodsService.update(goods);
        return Result.success("更新成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("mall:goods:delete")
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        Goods goods = goodsService.get(id);
//        List<goodsCategory> children = goodsCategoryService.findChildren(id);
//        List<Article> articles = articleService.findArticleByCategory(id);
//        if (pgoodsCategory == null) {
//            return Result.error("参数错误");
//        }
//        // 验证文章分类是否存在下级
//        if (pgoodsCategory != null
//                && (pgoodsCategory.getBuiltin() || children.size() > 0 || articles.size() > 0)) {
//            return Result.error("删除失败，文章分类“" + pgoodsCategory.getName() + "”存在下级");
//        }
        goodsService.remove(id);
        return Result.success("删除成功");
    }
}
