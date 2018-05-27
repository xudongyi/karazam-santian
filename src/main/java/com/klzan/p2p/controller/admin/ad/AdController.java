package com.klzan.p2p.controller.admin.ad;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.AdType;
import com.klzan.p2p.model.Ad;
import com.klzan.p2p.model.AdPosition;
import com.klzan.p2p.service.content.AdPositionService;
import com.klzan.p2p.service.content.AdService;
import com.klzan.p2p.vo.content.AdVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller - 广告
 * 
 * @author Karazam Team
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/ad")
public class AdController extends BaseAdminController {

    /** 索引重定向URL */
    private static final String INDEX_REDIRECT_URL = "redirect:/admin/ad";

    /** 模板路径 */
    private static final String TEMPLATE_PATH = "/ad";

    @Resource
    private AdService adService;

    @Resource
    private AdPositionService adPositionService;

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("content:ad:list")
    public String listDispater(){
        return  template("ad/list");
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions("content:ad:list")
    @ResponseBody
    public Map<String, Object> list(PageCriteria pageCriteria, HttpServletRequest request) {

        ParamsFilter.buildFromHttpRequest(request,pageCriteria);
        PageResult<AdVo> pageResult = adService.findPageByCategory(pageCriteria);
        return (Map<String, Object>) getPageResult(pageResult);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/addEdit", method = RequestMethod.GET)
    @RequiresPermissions("content:ad:add")
    public String add(ModelMap model,HttpServletRequest request) {

        model.addAttribute("types", AdType.values());
        List<AdPosition> list = adPositionService.findAll();
        List<AdPosition> list2 = new ArrayList<>();
        for (AdPosition adPosition:list){
            if (!adPosition.getDeleted()) {
                list2.add(adPosition);
            }
        }
        model.addAttribute("adPositions", list2);
        String position = request.getParameter("position");
        if(position!=null&&!"".equals(position)){
            model.addAttribute("adPosition",adPositionService.get(new Integer(position)));
        }
        return template("ad/add")  ;
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "updateEdit/{id}", method = RequestMethod.GET)
    @RequiresPermissions("content:ad:update")
    public String edit(@PathVariable Integer id, ModelMap model,HttpServletRequest request,
                       RedirectAttributes redirectAttributes) {
        // 验证位置
        Ad ad = adService.get(id);
        if (ad == null || adPositionService.get(ad.getPosition())==null) {
            redirectAttributes.addAttribute("","验证失败");
            return INDEX_REDIRECT_URL;
        }

        model.addAttribute("ad", ad);
        model.addAttribute("types", AdType.values());
        List<AdPosition> list = adPositionService.findAll();
        List<AdPosition> list2 = new ArrayList<>();
        for (AdPosition adPosition:list){
            if (!adPosition.getDeleted()) {
                list2.add(adPosition);
            }
        }
        model.addAttribute("adPositions", list2);
        String position = request.getParameter("position");
        if(position!=null&&!"".equals(position)){
            model.addAttribute("adPosition",adPositionService.get(new Integer(position)));
        }
        return template("ad/edit");
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("content:ad:add")
    @ResponseBody
    public Result save(AdVo vo) {

        // 验证位置
        if (vo.getPosition() != null && adPositionService.get(vo.getPosition()) == null) {
            return Result.error("验证失败,广告位不存在");
        }

        // 验证开始日期、结束日期
        if (vo.getStartDate() != null && vo.getEndDate() != null && vo.getStartDate().after(vo.getEndDate())) {
            return Result.error("日期不能为空，且结束日期必须大于开始日期");
        }
        Ad ad = new Ad();
        setObj(vo,ad);
        adService.persist(ad);

        return Result.success("保存成功");
    }

    /**
     * 更新
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @RequiresPermissions("content:ad:update")
    @ResponseBody
    public Result update(@PathVariable Integer id, AdVo vo) {

        // 验证位置
        if (vo.getPosition() != null && adPositionService.get(vo.getPosition()) == null) {
            return Result.error("验证失败,广告位不存在");
        }

        // 验证广告是否存在
        Ad ad = adService.get(id);
        if (ad == null) {
            return Result.error("验证失败,广告不存在");
        }
        // 验证开始日期、结束日期
        if (vo.getStartDate() != null && vo.getEndDate() != null && vo.getStartDate().after(vo.getEndDate())) {
            return Result.error("日期不能为空，且结束日期必须大于开始日期");
        }
        setObj(vo,ad);
        adService.update(ad);
        return Result.success("更新成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("content:ad:delete")
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        adService.remove(id);
        return Result.success("删除成功");
    }

    private void setObj(AdVo vo, Ad ad){
        ad.setTitle(vo.getTitle());
        ad.setCont(vo.getCont());
        ad.setUrl(vo.getUrl());
        ad.setEndDate(vo.getEndDate());
        ad.setPosition(vo.getPosition());
        ad.setStartDate(vo.getStartDate());
        ad.setSort(vo.getSort());
        ad.setPath(vo.getPath());
    }
}