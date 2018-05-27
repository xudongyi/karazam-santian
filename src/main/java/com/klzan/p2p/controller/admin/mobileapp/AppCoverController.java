package com.klzan.p2p.controller.admin.mobileapp;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.controller.BaseController;

import com.klzan.p2p.model.AppCover;
import com.klzan.p2p.service.appcover.AppCoverService;
import com.klzan.p2p.vo.AppCover.AppCoverVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
@Controller
@RequestMapping("/admin/appCover")
public class AppCoverController extends BaseController {

    @Autowired
    private AppCoverService appCoverService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "/admin/app_cover/list";
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> list(PageCriteria pageCriteria, HttpServletRequest request) {

        ParamsFilter.buildFromHttpRequest(request,pageCriteria);
        PageResult<AppCoverVo> pageResult = appCoverService.findPageAppCover(pageCriteria);
        return (Map<String, Object>) getPageResult(pageResult);
    }
    //添加
    @RequestMapping(value = "/addEdit", method = RequestMethod.GET)
    public String add(ModelMap model, HttpServletRequest request) {
        return "/admin/app_cover/add" ;
    }
    @RequestMapping(value = "updateEdit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model, HttpServletRequest request) {
        // 验证位置
        AppCover appCover = appCoverService.get(id);
        model.addAttribute("appCover",appCover);
        return "/admin/app_cover/edit";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(AppCoverVo vo) {

        // 验证开始日期、结束日期
        if (vo.getStartDate() != null && vo.getEndDate() != null && vo.getStartDate().after(vo.getEndDate())) {
            return Result.error("日期不能为空，且结束日期必须大于开始日期");
        }
        appCoverService.save(vo);
        return Result.success("保存成功");
    }

    /**
     * 更新
     */
    @RequestMapping(value = "update/{id}")
    @ResponseBody
    public Result update(@PathVariable Integer id, AppCoverVo vo) {

        // 验证是否存在
        AppCover appCover = appCoverService.get(id);
        if (appCover == null) {
            return Result.error("验证失败,启动页图片不存在");
        }
        // 验证开始日期、结束日期
        if (vo.getStartDate() != null && vo.getEndDate() != null && vo.getStartDate().after(vo.getEndDate())) {
            return Result.error("日期不能为空，且结束日期必须大于开始日期");
        }
        appCover.setEndDate(vo.getEndDate());
        appCover.setPath(vo.getPath());
        appCover.setStartDate(vo.getStartDate()) ;
        appCover.setTitle(vo.getTitle());
        appCover.setUrl(vo.getUrl());
        appCover.setModifyDate(new Date());
        appCover.setCont(vo.getCont());
        appCoverService.update(appCover);
        return Result.success("更新成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        appCoverService.remove(id);
        return Result.success("删除成功");
    }
}
