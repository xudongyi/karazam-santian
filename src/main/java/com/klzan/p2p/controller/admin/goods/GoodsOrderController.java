package com.klzan.p2p.controller.admin.goods;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.GoodsOrderStatus;
import com.klzan.p2p.enums.GoodsShippingStatus;
import com.klzan.p2p.model.Goods;
import com.klzan.p2p.model.GoodsCategory;
import com.klzan.p2p.model.GoodsOrder;
import com.klzan.p2p.service.goods.GoodsCategoryService;
import com.klzan.p2p.service.goods.GoodsOrderService;
import com.klzan.p2p.service.goods.GoodsService;
import com.klzan.p2p.vo.goods.GoodsCategoryVo;
import com.klzan.p2p.vo.goods.GoodsVo;
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
import java.util.Map;

/**
 * 商品订单
 * @author chenxinglin
 */
@Controller("adminGoodsOrderController")
@RequestMapping("admin/goods_order")
public class GoodsOrderController extends BaseAdminController {

    @Inject
    private GoodsCategoryService goodsCategoryService;
    @Inject
    private GoodsService goodsService;
    @Inject
    private GoodsOrderService goodsOrderService;

    /**
     * 列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("mall:goodsorder:list")
    public String list() {
        return "/admin/goods_order/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("mall:goodsorder:list")
    @ResponseBody
    public Map<String, Object> pageList(PageCriteria criteria, HttpServletRequest request) {
        ParamsFilter.buildFromHttpRequest(request, criteria);
        PageResult<GoodsOrder> pageResult = goodsOrderService.findPage(criteria);
        return (Map<String, Object>) getPageResult(pageResult);
    }

    /**
     * 发货
     */
    @RequestMapping(value = "/sendView/{orderId}", method = RequestMethod.GET)
    @RequiresPermissions("mall:goodsorder:list")
    public String send(ModelMap model, @PathVariable Integer orderId, HttpServletRequest request) {
        model.addAttribute("action", "send");
        if (orderId != null) {
            model.addAttribute("goodsOrder", goodsOrderService.get(new Integer(orderId)));
        }
        return "/admin/goods_order/send";
    }
    /**
     * 发货
     */
    @RequestMapping(value = "/send/{orderId}", method = RequestMethod.POST)
    @RequiresPermissions("mall:goodsCategory:add")
    @ResponseBody
    public Result save(@PathVariable Integer orderId, String logisticsCorp, String logisticsCorpUrl, String logisticsNo, RedirectAttributes redirectAttributes) {
        if (orderId == null) {
            return Result.error("参数错误");
        }
        GoodsOrder goodsOrder = goodsOrderService.get(new Integer(orderId));
        if (goodsOrder == null) {
            return Result.error("订单不存在");
        }
        if (!goodsOrder.getLogisticsStatus().equals(GoodsShippingStatus.unshipped)
                || goodsOrder.getStatus().equals(GoodsOrderStatus.cancel)
                || goodsOrder.getStatus().equals(GoodsOrderStatus.failure)) {
            return Result.error("订单不可发货");
        }
        try {
            goodsOrderService.sendOut(goodsOrder, logisticsCorp, logisticsCorpUrl, logisticsNo);
            return Result.success("发货成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("发货失败");
        }
    }

    /**
     * 修改订单
     */



//    /**
//     * 添加
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    @RequiresPermissions("mall:goods:add")
//    public String add(ModelMap model, HttpServletRequest request) {
//        model.addAttribute("action", "save");
////        String parentId = request.getParameter("parentId");
////        if (parentId != null) {
////            model.addAttribute("goods", goodsService.get(new Integer(parentId)));
////        }
//        model.addAttribute("goodsCategory", goodsCategoryService.get(2));
//        return "/admin/goods/add";
//    }
//
//    /**
//     * 保存
//     */
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @RequiresPermissions("mall:goods:add")
//    @ResponseBody
//    public Result save(GoodsVo vo) {
////        // 验证名称是否存在
////        if (goodsCategoryService.nameExists(null, goodsCategoryVo.getName())) {
////            return Result.error("名称已存在");
////        }
//        Goods goods = new Goods();
//        goods.setName(vo.getName());
//        goods.setOriginalPrice(vo.getOriginalPrice());
//        goods.setPrice(vo.getPrice());
//        goods.setOriginalPoint(vo.getOriginalPoint());
//        goods.setPoint(vo.getPoint());
//        goods.setImage(vo.getImage());
//        goods.setWeight(vo.getWeight());
//        goods.setStock(vo.getStock());
//        goods.setAllocatedStock(vo.getAllocatedStock());
//        goods.setMarketable(vo.getMarketable());
//        goods.setMemo(vo.getMemo());
//        goods.setSeoTitle(vo.getSeoTitle());
//        goods.setSeoKeywords(vo.getSeoKeywords());
//        goods.setSeoDescription(vo.getSeoDescription());
//        goods.setIntroduction(vo.getIntroduction());
//        goods.setGoodsAttr(vo.getGoodsAttr());
//        goods.setGoodsCategory(vo.getGoodsCategory());
//        goodsService.persist(goods);
//        return Result.success("保存成功");
//    }
//
//    /**
//     * 编辑
//     */
//    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
//    @RequiresPermissions("mall:goods:update")
//    public String edit(@PathVariable Integer id, ModelMap model) {
//        Goods goods = goodsService.get(id);
//        model.addAttribute("goods", goods);
////        if (goodsCategory.getParent() != null) {
////            GoodsCategory parentgoodsCategory = goodsService.get(goodsCategory.getParent());
////            model.addAttribute("parentgoodsCategory", parentgoodsCategory);
////        }
//        model.addAttribute("goodsCategory", goodsCategoryService.get(2));
//        model.addAttribute("action", "update");
//        model.addAttribute("pk", id);
//        return "/admin/goods/edit";
//    }
//
//    /**
//     * 更新
//     */
//    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
//    @RequiresPermissions("mall:goods:update")
//    @ResponseBody
//    public Result update(@PathVariable Integer id, GoodsVo vo) {
//        Goods goods = goodsService.get(id);
//        // 验证
//        if (goods == null) {
//            return Result.error("商品不存在");
//        }
//        goods.setName(vo.getName());
//        goods.setOriginalPrice(vo.getOriginalPrice());
//        goods.setPrice(vo.getPrice());
//        goods.setOriginalPoint(vo.getOriginalPoint());
//        goods.setPoint(vo.getPoint());
//        goods.setImage(vo.getImage());
//        goods.setWeight(vo.getWeight());
//        goods.setStock(vo.getStock());
//        goods.setAllocatedStock(vo.getAllocatedStock());
//        goods.setMarketable(vo.getMarketable());
//        goods.setMemo(vo.getMemo());
//        goods.setSeoTitle(vo.getSeoTitle());
//        goods.setSeoKeywords(vo.getSeoKeywords());
//        goods.setSeoDescription(vo.getSeoDescription());
//        goods.setIntroduction(vo.getIntroduction());
//        goods.setGoodsAttr(vo.getGoodsAttr());
//        goods.setGoodsCategory(vo.getGoodsCategory());
//        goodsService.update(goods);
//        return Result.success("更新成功");
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
//    @RequiresPermissions("mall:goods:delete")
//    @ResponseBody
//    public Result delete(@PathVariable Integer id) {
//        Goods goods = goodsService.get(id);
////        List<goodsCategory> children = goodsCategoryService.findChildren(id);
////        List<Article> articles = articleService.findArticleByCategory(id);
////        if (pgoodsCategory == null) {
////            return Result.error("参数错误");
////        }
////        // 验证文章分类是否存在下级
////        if (pgoodsCategory != null
////                && (pgoodsCategory.getBuiltin() || children.size() > 0 || articles.size() > 0)) {
////            return Result.error("删除失败，文章分类“" + pgoodsCategory.getName() + "”存在下级");
////        }
//        goodsService.remove(id);
//        return Result.success("删除成功");
//    }
}
