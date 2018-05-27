package com.klzan.p2p.controller.admin.ad;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.model.AdPosition;
import com.klzan.p2p.service.content.AdPositionService;
import com.klzan.p2p.service.content.AdService;
import com.klzan.p2p.vo.content.AdPositionVo;
import com.klzan.p2p.vo.content.AdVo;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Map;

/**
 * Controller - 广告位
 *
 * @author Karazam Team
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/ad_position")
public class AdPositionController extends BaseAdminController {

    /** 模板路径 */
    private static final String TEMPLATE_PATH = "/ad_position";

    @Resource
    private AdPositionService adPositionService;
    @Resource
    private AdService adService;

    /**
     * 列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("content:ad_position:list")
    public String listDispater() {
        return template("ad_position/list") ;
    }
    /**
     * 列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions("content:ad_position:list")
    @ResponseBody
    public Map<String, Object> list(PageCriteria pageCriteria, HttpServletRequest request) {
        ParamsFilter.buildFromHttpRequest(request,pageCriteria);
        PageResult<AdPositionVo> pageResult = adPositionService.findPageByCategory(pageCriteria);
        return (Map<String, Object>) getPageResult(pageResult);
    }
    /**
     * 指定分类的列表
     */
    @RequestMapping(value = "/pageListByIdDispatcher/{position}",method = RequestMethod.GET)
    public String pageListByIdDispatcher(@PathVariable Integer position,ModelMap model) {
        model.addAttribute("position",position);
        return template("ad_position/list_id");
    }

    @RequestMapping(value = "/pageListById/{position}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> pageListById(@PathVariable Integer position, PageCriteria pageCriteria, HttpServletRequest request){
        ParamsFilter.buildFromHttpRequest(request,pageCriteria);
        PageResult<AdVo> pageResult = adService.findPageByCategory(position, pageCriteria);
        return (Map<String, Object>) getPageResult(pageResult);
    }
    /**
     * 添加
     */
    @RequestMapping(value = "/addEdit", method = RequestMethod.GET)
    @RequiresPermissions("content:ad_position:add")
    public String add() {
        return template("ad_position/add");
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/updateEdit/{id}", method = RequestMethod.GET)
    @RequiresPermissions("content:ad_position:update")
    public String edit(@PathVariable Integer id, ModelMap model,
                       RedirectAttributes redirectAttributes) {
        model.addAttribute("adPosition", adPositionService.get(id));
        return template("ad_position/edit");
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("content:ad_position:add")
    @ResponseBody
    public Result save(AdPositionVo vo) {
        // 验证名称是否存在
        if (adPositionService.nameExists(vo.getName())) {
            return Result.error("名称已存在");
        }
        // 验证标识是否存在
        if (adPositionService.identExists(vo.getIdent())) {
            return Result.error("标识已存在");
        }
        AdPosition adPosition = new AdPosition();
        setObj(vo,adPosition);
        adPositionService.persist(adPosition);
        return Result.success("保存成功");
    }

    /**
     * 更新
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    @RequiresPermissions("content:ad_position:update")
    @ResponseBody
    public Result update(@PathVariable Integer id, AdPositionVo vo) {

        AdPosition adPosition = adPositionService.get(id);
        if (adPosition==null) {
            return Result.error("参数错误,广告位不存在");
        }
        // 验证名称是否唯一
        if(!adPosition.getName().equals(vo.getName())){
            if (adPositionService.nameExists(vo.getName())) {
                return Result.error("参数错误,名称已存在");
            }
        }
        // 验证标识是否唯一
        if(!adPosition.getIdent().equals(vo.getIdent())){
            if(adPositionService.identExists(vo.getIdent())){
                return Result.error("参数错误,标识已存在");
            }
        }
        setObj(vo,adPosition);
        adPositionService.update(adPosition);
        return Result.success("更新成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("content:ad_position:delete")
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        AdPosition adPosition = adPositionService.get(id);
        if (adPosition == null) {
            return Result.error("参数错误");
        }
        // 验证广告位是否存在广告
        if (adPosition != null && (adService.findAdByPosition(adPosition.getId()).size()>0)) {
            return Result.error("删除失败，广告位“" + adPosition.getName() + "”存在广告");
        }
        adPositionService.remove(id);
        return Result.success("删除成功");
    }

    /**
     * 检查名称
     */
    @RequestMapping(value = "/check_name", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkName(String name, String previousName) {
//        if (StringUtils.isBlank(name)) {
//            return false;
//        }
//        // 验证名称是否唯一
//        if (adPositionService.nameUnique(previousName, name)) {
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }

    /**
     * 检查标识
     */
    @RequestMapping(value = "/check_ident", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkIdent(String ident, String previousIdent) {
        if (StringUtils.isBlank(ident)) {
            return false;
        }
//        // 验证标识是否唯一
//        if (adPositionService.identUnique(previousIdent, ident)) {
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }

    private void setObj(AdPositionVo vo, AdPosition adPosition){
        adPosition.setBuiltin(false);
        adPosition.setDescription(vo.getDescription());
        adPosition.setName(vo.getName());
        adPosition.setIdent(StringUtils.upperCase(vo.getIdent()));
    }
}