package com.klzan.p2p.controller.admin.user;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.ReferralFeeState;
import com.klzan.p2p.service.user.ReferralFeeService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.user.ReferralFeeVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhu on 2016/11/15.
 */
@Controller
@RequestMapping("admin/referralfee")
public class ReferralFeeController extends BaseAdminController {

    @Autowired
    private ReferralFeeService referralFeeService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("user:referralfee:manage")
    public String list(PageCriteria PageCriteria, ModelMap model) {

        model.addAttribute("states", ReferralFeeState.values());
        return template("/user/referral_fee");
    }

    @RequestMapping(value = "list.json", method = RequestMethod.GET)
    @RequiresPermissions("user:referralfee:manage")
    @ResponseBody
    public PageResult<ReferralFeeVo> listData(PageCriteria pageCriteria, HttpServletRequest request) {
        ParamsFilter.buildFromHttpRequest(request, pageCriteria);
        PageResult<ReferralFeeVo> page = referralFeeService.findReferralFeeList(pageCriteria, null);
        for (ReferralFeeVo vo : page.getRows()) {
            vo.setUserNickName("");
            vo.setReUserNickName("");
            vo.setUserMobile("");
            vo.setReUserMobile("");
        }
        return page;
    }

    @RequestMapping(value = "applyAuditing/{ids}", method = RequestMethod.DELETE)
    @RequiresPermissions("user:referralfee:applyAuditing")
    @ResponseBody
    public Result applyAuditing(@PathVariable("ids") Integer[] ids, Model model) {
        try {
            referralFeeService.applyAuditing(ids);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @RequestMapping(value = "offlinepaid/{ids}", method = RequestMethod.DELETE)
    @RequiresPermissions("user:referralfee:offlinepaid")
    @ResponseBody
    public Result offlinepaid(@PathVariable("ids") Integer[] ids, Model model) {
        try {
            referralFeeService.offlinepaid(ids);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @RequestMapping(value = "suggestion/{referralFeeId}")
    public String suggestion(@PathVariable("referralFeeId") Integer referralFeeId, Model model) {
        model.addAttribute("referralFeeId", referralFeeId);
        return template("/user/referral_fee_suggestion");
    }

    @RequestMapping(value = "auditing", method = RequestMethod.POST)
    @RequiresPermissions("user:referralfee:auditing")
    @ResponseBody
    public Result referralFeeSettle(Integer referralFeeId, String suggestion) {
        try {
            referralFeeService.auditing(referralFeeId, suggestion);
            return Result.success("结算成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
