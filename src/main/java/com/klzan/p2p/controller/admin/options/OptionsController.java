/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.options;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.OptionsType;
import com.klzan.p2p.service.options.OptionsService;
import com.klzan.p2p.vo.options.OptionsVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统参数设置
 * Created by suhao Date: 2016/11/11 Time: 11:31
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("admin/options")
public class OptionsController extends BaseAdminController {

    @Autowired
    private OptionsService optionsService;

    @RequiresPermissions("options:basic:view")
    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam OptionsType type, Model model) {
        model.addAttribute("type", type);
        return template("options/index");
    }

    @RequiresPermissions("options:basic:view")
    @RequestMapping(value="json",method = RequestMethod.GET)
    @ResponseBody
    public PageResult<OptionsVo> getData(@RequestParam OptionsType type, PageCriteria pageCriteria) {
        return optionsService.findPageByType(pageCriteria, type);
    }

    @RequiresPermissions("options:basic:update")
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String updateIndex(@RequestParam OptionsType type, Model model) {
        model.addAttribute("records", optionsService.findOptionsByType(type));
        return template("options/form");
    }

    @RequiresPermissions("options:basic:update")
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public Object update(HttpServletRequest request) {
        List<OptionsVo> optionsVos = new ArrayList<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> tmp : parameterMap.entrySet()) {
            String key = tmp.getKey();
            String regex = "keyValue_(.*)";
            Pattern pat = Pattern.compile(regex);
            Matcher mat = pat.matcher(key);
            if (mat.find()) {
                OptionsVo optionsVo = new OptionsVo();
                optionsVo.setId(Integer.parseInt(mat.group(1)));
                optionsVo.setKeyValue(tmp.getValue()[0]);
                optionsVos.add(optionsVo);
            }
        }
        optionsService.updateOp(optionsVos);

        return Result.success();
    }

}
