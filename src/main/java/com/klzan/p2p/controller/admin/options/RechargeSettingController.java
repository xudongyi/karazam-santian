package com.klzan.p2p.controller.admin.options;

import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.OptionsDataType;
import com.klzan.p2p.enums.OptionsDataUnit;
import com.klzan.p2p.enums.OptionsType;
import com.klzan.p2p.model.Options;
import com.klzan.p2p.service.options.OptionsService;
import com.klzan.p2p.setting.RechargeSetting;
import com.klzan.p2p.vo.options.OptionsVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by suhao Date: 2017/2/8 Time: 16:57
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("admin/rechargeSetting")
public class RechargeSettingController extends BaseAdminController {

    @Inject
    private OptionsService optionsService;

    @RequiresPermissions("recharge:setting:update")
    @RequestMapping
    public String index(Model model, RechargeSetting rechargeSetting, String action, HttpServletRequest request) {
        String json = JsonUtils.toJson(rechargeSetting);
        List<OptionsVo> optionsVos = optionsService.findOptionsByType(OptionsType.RECHARGE_SETTING);
        if (StringUtils.isNotBlank(action)) {
            if (optionsVos.isEmpty()) {
                Options options = new Options(OptionsType.RECHARGE_SETTING, OptionsDataType.STRING, OptionsDataUnit.NOUNIT, "充值设置", "rechargeSetting", json);
                optionsService.persist(options);
            } else {
                OptionsVo optionsVo = optionsVos.get(0);
                Options options = optionsService.get(optionsVo.getId());
                options.update(OptionsType.RECHARGE_SETTING, OptionsDataType.STRING, OptionsDataUnit.NOUNIT, "充值设置", "rechargeSetting", json);
                optionsService.update(options);
            }
        }
        optionsVos = optionsService.findOptionsByType(OptionsType.RECHARGE_SETTING);
        if (!optionsVos.isEmpty()) {
            OptionsVo optionsVo = optionsVos.get(0);
            rechargeSetting = JsonUtils.toObject(optionsVo.getKeyValue(), RechargeSetting.class);
            model.addAttribute("rechargeSetting", rechargeSetting);
        }
        return template("recharge_setting/index");
    }
}
