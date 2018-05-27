package com.klzan.p2p.controller.admin.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.UserAutoInvestmentRank;
import com.klzan.p2p.service.user.UserAutoInvestmentRankService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动投标用户
 * Created by suhao Date: 2017/6/12 Time: 19:58
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("admin/user/autoInvestRank")
public class UserAutoInvestRankController extends BaseAdminController {
    @Autowired
    private UserAutoInvestmentRankService userAutoInvestmentRankService;

    @RequiresPermissions("user:autoinvestrank:view")
    @RequestMapping(value = {"", "index"})
    public String list(ModelMap modelMap){
        modelMap.addAttribute("types", UserType.values());
        return "admin/auto_invest_rank/list";
    }

    @RequiresPermissions("user:autoinvestrank:view")
    @RequestMapping(value="list.json",method = RequestMethod.GET)
    @ResponseBody
    public PageResult<UserAutoInvestmentRank> listQuery(PageCriteria criteria, HttpServletRequest request){
        Map map = new HashMap();
        map.put("mobile", request.getParameter("mobile"));
        map.put("realName", request.getParameter("realName"));
        PageResult<UserAutoInvestmentRank> page = userAutoInvestmentRankService.findPage(criteria, map);
        for (UserAutoInvestmentRank rank : page.getRows()) {
            if (!rank.getOpenStatus() || rank.getExpire().before(new Date())) {
                continue;
            }
            Integer userRank = userAutoInvestmentRankService.getUserRank(rank.getUserId());
            Integer effectiveSign = userAutoInvestmentRankService.findEffectiveSign();
            rank.setRank(userRank);
            rank.setEffectiveSign(effectiveSign);
            rank.setRankDes(userRank + "/" + effectiveSign);
        }
        return page;
    }

}
