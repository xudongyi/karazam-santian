package com.klzan.p2p.controller.admin.options;

import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.OptionsDataType;
import com.klzan.p2p.enums.OptionsDataUnit;
import com.klzan.p2p.enums.OptionsType;
import com.klzan.p2p.model.Options;
import com.klzan.p2p.service.options.OptionsService;
import com.klzan.p2p.setting.WithdrawSetting;
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
@RequestMapping("admin/withdrawSetting")
public class WithdrawSettingController extends BaseAdminController {

    @Inject
    private OptionsService optionsService;

    @RequiresPermissions("withdraw:setting:update")
    @RequestMapping
    public String index(Model model, WithdrawSetting withdrawSetting, String action, HttpServletRequest request) {
        String json = JsonUtils.toJson(withdrawSetting);
        List<OptionsVo> optionsVos = optionsService.findOptionsByType(OptionsType.WITHDRAW_SETTING);
        if (StringUtils.isNotBlank(action)) {
            if (optionsVos.isEmpty()) {
                Options options = new Options(OptionsType.WITHDRAW_SETTING, OptionsDataType.STRING, OptionsDataUnit.NOUNIT, "提现设置", "withdrawSetting", json);
                optionsService.persist(options);
            } else {
                OptionsVo optionsVo = optionsVos.get(0);
                Options options = optionsService.get(optionsVo.getId());
                options.update(OptionsType.WITHDRAW_SETTING, OptionsDataType.STRING, OptionsDataUnit.NOUNIT, "提现设置", "withdrawSetting", json);
                optionsService.update(options);
            }
        }
        optionsVos = optionsService.findOptionsByType(OptionsType.WITHDRAW_SETTING);
        if (!optionsVos.isEmpty()) {
            OptionsVo optionsVo = optionsVos.get(0);
            withdrawSetting = JsonUtils.toObject(optionsVo.getKeyValue(), WithdrawSetting.class);
            model.addAttribute("withdrawSetting", withdrawSetting);
        }
        return template("withdraw_setting/index");
    }
}
