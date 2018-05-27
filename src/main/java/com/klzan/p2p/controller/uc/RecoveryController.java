package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.vo.repayment.RepaymentPlanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 回收
 * Created by suhao Date: 2017/4/23 Time: 15:38
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("uc/recovery")
public class RecoveryController extends BaseController {
    @Autowired
    private RepaymentPlanService repaymentPlanService;

    @RequestMapping(value = {"", "/", "index"})
    public String index() {
        return "uc/recovery/index";
    }

    @RequestMapping(value = "{recoveryState}", method = RequestMethod.GET)
    @ResponseBody
    public PageResult<RepaymentPlanVo> data(@PathVariable String recoveryState, @CurrentUser User user,
                                            PageCriteria criteria, HttpServletRequest request) {
        RepaymentState state;
        switch (recoveryState) {
            case "recovering" :
                state = RepaymentState.REPAYING;
                break;
            case "recoveried" :
                state = RepaymentState.REPAID;
                break;
            default:
                state = RepaymentState.REPAYING;
        }
        return repaymentPlanService.findPage(user.getId(), state, buildQueryCriteria(criteria, request));
    }

    @RequestMapping(value = "count/{recoveryState}", method = RequestMethod.GET)
    @ResponseBody
    public Result count(@PathVariable String recoveryState, @CurrentUser User user,
                        PageCriteria criteria, HttpServletRequest request) {
        RepaymentState state;
        switch (recoveryState) {
            case "recovering" :
                state = RepaymentState.REPAYING;
                break;
            case "recoveried" :
                state = RepaymentState.REPAID;
                break;
            default:
                state = RepaymentState.REPAYING;
        }
        Map map = new HashMap();
        BigDecimal capitals = BigDecimal.ZERO;
        BigDecimal interests = BigDecimal.ZERO;

        List<RepaymentPlanVo> list = repaymentPlanService.findList(user.getId(), state, buildQueryCriteria(criteria, request));
        for (RepaymentPlanVo planVo : list) {
            capitals = capitals.add(planVo.getCapital());
            interests = interests.add(planVo.getInterest());
        }
        map.put("capitals", capitals);
        map.put("interests", interests);
        return Result.success("成功", map);
    }
}
