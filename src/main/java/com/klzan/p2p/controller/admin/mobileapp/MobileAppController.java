package com.klzan.p2p.controller.admin.mobileapp;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.mobile.vo.MobileAppVo;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.DeviceType;
import com.klzan.p2p.model.MobileApp;
import com.klzan.p2p.service.mobileapp.MobileAppService;
import com.klzan.p2p.setting.SettingUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * Created by suhao Date: 2017/1/18 Time: 11:48
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin/mobileapp")
public class MobileAppController extends BaseController {

    @Inject
    private MobileAppService mobileAppService;

    @Inject
    private SettingUtils setting;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "/admin/mobileapp/index";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object json(PageCriteria criteria) {
        PageResult<MobileAppVo> page = mobileAppService.findByPage(criteria, DeviceType.ANDROID);

        return getPageResult(page);
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createIndex(Model model) {
        model.addAttribute("app", new MobileApp());
        model.addAttribute("action", "create");
        return "/admin/mobileapp/form";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(MobileAppVo vo) {
        vo.setAppType(DeviceType.ANDROID.name());
        mobileAppService.save(vo);
        return Result.success();
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateIndex(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("app", mobileAppService.findById(id));
        model.addAttribute("action", "update");
        return "/admin/mobileapp/form";
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public Result update(MobileAppVo vo) {
        mobileAppService.update(vo);
        return Result.success();
    }
}
