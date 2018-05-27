package com.klzan.p2p.controller.admin.investment;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.vo.investment.InvestmentRecordVo;
import com.klzan.p2p.vo.investment.InvestmentVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhu on 2016/11/15.
 */
@Controller("adminInvestmentController")
@RequestMapping("admin/investment")
public class InvestmentController extends BaseController {

    @Autowired
    private InvestmentService investmentService;
    @Autowired
    private InvestmentRecordService investmentRecordService;

    /**
     * 投资总记录
     * @param PageCriteria
     * @param model
     * @return
     */
    @RequestMapping(value = "/totalrecordlist", method = RequestMethod.GET)
    @RequiresPermissions("investment:totalrecord:list")
    public String totalRecordList(@RequestParam(defaultValue = "false") Boolean toshow, PageCriteria PageCriteria, ModelMap model) {
        model.addAttribute("toshow", toshow);
        return "/admin/investment/totalrecord";
    }

    /**
     * 投资总记录
     * @param pageCriteria
     * @return
     */
    @RequestMapping(value="/totalrecordlist.json",method = RequestMethod.GET)
    @RequiresPermissions("investment:totalrecord:list")
    @ResponseBody
    public Map<String, Object> totalRecordListData(@RequestParam(defaultValue = "false") Boolean toshow, PageCriteria pageCriteria, Date startDate, Date endDate, HttpServletRequest request){
        ParamsFilter.buildFromHttpRequest(request,pageCriteria);
        PageResult<InvestmentVo > pageResult= investmentService.findInvestmentList(pageCriteria, null, startDate, endDate);
        if (!toshow) {
            for (InvestmentVo investmentVo : pageResult.getRows()) {
                investmentVo.setRealName("");
                investmentVo.setMobile("");
            }
        }
        return (Map<String, Object>) getPageResult(pageResult);
    }
    /**
     * 投资总记录统计
     * @return
     */
    @RequestMapping(value="/investmentSum",method = RequestMethod.POST)
    @RequiresPermissions("investment:totalrecord:list")
    @ResponseBody
    public Result investmentSum(String realName, String mobile, Date startDate, Date endDate, HttpServletRequest request){
        Map<String, Object> map = new HashedMap();
        map.put("amountTotal", 0);
        map.put("borrowingAmountTotal", 0);
        try {
            List sums = investmentService.countInvest(realName, mobile, startDate, endDate);
            if(sums!=null && sums.size()>0) {
                map.put("amountTotal", ((Object[]) sums.get(0))[0]);
                map.put("borrowingAmountTotal", ((Object[]) sums.get(0))[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success("成功", map);
    }

    /**
     * 投资子记录
     * @param PageCriteria
     * @param model
     * @return
     */
    @RequestMapping(value = "/detailrecordlist", method = RequestMethod.GET)
    @RequiresPermissions("investment:detailrecord:list")
    public String detailRecordList(@RequestParam(defaultValue = "false") Boolean toshow, PageCriteria PageCriteria, ModelMap model) {
        model.addAttribute("toshow", toshow);
        return "/admin/investment/detailrecord";
    }

    /**
     * 投资子记录
     * @param pageCriteria
     * @return
     */
    @RequestMapping(value="/detailrecordlist.json",method = RequestMethod.GET)
    @RequiresPermissions("investment:detailrecord:list")
    @ResponseBody
    public Map<String, Object> detailRecordListData(@RequestParam(defaultValue = "false") Boolean toshow, PageCriteria pageCriteria, Date startDate, Date endDate, HttpServletRequest request){
        ParamsFilter.buildFromHttpRequest(request,pageCriteria);
        PageResult<InvestmentRecordVo> pageResult = investmentRecordService.findInvestmentRecordList(pageCriteria, startDate, endDate);
        if (!toshow) {
            for (InvestmentRecordVo investmentRecordVo : pageResult.getRows()) {
                investmentRecordVo.setRealName("");
                investmentRecordVo.setMobile("");
            }
        }
        return (Map<String, Object>) getPageResult(pageResult);
    }

    /**
     * 投资子记录统计
     * @return
     */
    @RequestMapping(value="/investmentRecordSum",method = RequestMethod.POST)
    @RequiresPermissions("investment:totalrecord:list")
    @ResponseBody
    public Result investmentRecordSum(String realName, String mobile, Date startDate, Date endDate, HttpServletRequest request){
        Map<String, Object> map = new HashedMap();
        map.put("amountTotal", 0);
        map.put("borrowingAmountTotal", 0);
        try {
            List sums = investmentRecordService.countInvest(realName, mobile, startDate, endDate);
            if(sums!=null && sums.size()>0) {
                map.put("amountTotal", ((Object[]) sums.get(0))[0]);
                map.put("borrowingAmountTotal", ((Object[]) sums.get(0))[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success("成功", map);
    }
}
