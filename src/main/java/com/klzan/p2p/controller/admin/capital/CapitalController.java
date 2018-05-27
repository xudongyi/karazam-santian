/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.capital;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.CapitalMethod;
import com.klzan.p2p.enums.CapitalType;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.capital.PlatformCapitalService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserIpsInfoService;
import com.klzan.p2p.util.ExcelView;
import com.klzan.p2p.vo.capital.CapitalVo;
import com.klzan.p2p.vo.capital.PlatformCapitalVo;
import com.klzan.p2p.vo.capital.UserFinanceVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 资金管理
 * Created by zhutao Date: 2017/04/06
 *
 * @version: 1.0
 */
@Controller("adminCapitalController")
@RequestMapping("admin/capital")
public class CapitalController extends BaseAdminController {

    @Inject
    private UserFinanceService userFinanceService;

    @Inject
    private CapitalService capitalService;
    @Resource
    private PlatformCapitalService platformCapitalService;
    @Resource
    private UserIpsInfoService userIpsInfoService;

    @RequiresPermissions("capital:user:view")
    @RequestMapping(method = RequestMethod.GET)
    public String allCapitalsIndex(@RequestParam(defaultValue = "false") Boolean toshow, Model model, HttpServletRequest request) {
        model.addAttribute("types", CapitalType.values());
        model.addAttribute("methods", CapitalMethod.values());
        model.addAttribute("toshow", toshow);
        return "admin/capital/index";
    }

    @RequiresPermissions("capital:platform:view")
    @RequestMapping(value = "/platform", method = RequestMethod.GET)
    public String platformCapitalsIndex(Model model, HttpServletRequest request) {
        model.addAttribute("types",CapitalType.values());
        model.addAttribute("methods", CapitalMethod.values());
        return "admin/capital/platform";
    }

    @RequiresPermissions("capital:user:view")
    @RequestMapping(value="/json",method = RequestMethod.GET)
    @ResponseBody
    public Object getCapitals(@RequestParam(defaultValue = "false") Boolean toshow, PageCriteria criteria,HttpServletRequest request) {
        criteria.setSort("c.id");
        criteria.setOrder("desc");
        String createDateBegin = request.getParameter("createDateBegin");
        String createDateEnd = request.getParameter("createDateEnd");
        PageResult<CapitalVo> result = capitalService.findPage(buildQueryCriteria(criteria, request),createDateBegin,createDateEnd);
        if (!toshow) {
            for (CapitalVo capitalVo : result.getRows()) {
                capitalVo.setMobile("");
                capitalVo.setOperator("");
            }
        }
        return result;
    }

    @RequiresPermissions("capital:user:view")
    @RequestMapping(value="/platFormCapitals",method = RequestMethod.GET)
    @ResponseBody
    public PageResult getPlatFormCapitals(PageCriteria criteria, HttpServletRequest request) {
        PageResult<PlatformCapitalVo> result = platformCapitalService.findHuaShanCapitalList(criteria,request.getParameter("operator"),request.getParameter("type"),
                request.getParameter("method"),request.getParameter("createDateBegin"),request.getParameter("createDateEnd"));
        return result;
    }

    /**
     * 用户资金总揽列表页
     * @param model
     * @param request
     * @return
     */
    @RequiresPermissions("capital:userfund:view")
    @RequestMapping(value = "/userFund",method = RequestMethod.GET)
    public String userFundIndex(@RequestParam(defaultValue = "false") Boolean toshow, Model model, HttpServletRequest request) {
        model.addAttribute("toshow", toshow);
        return "admin/capital/user_finance_index";
    }

    /**
     * 用户资金总揽列表数据
     * @param criteria
     * @param request
     * @return
     */
    @RequiresPermissions("capital:userfund:view")
    @RequestMapping(value="/userFund/json",method = RequestMethod.GET)
    @ResponseBody
    public PageResult getUserFunds(@RequestParam(defaultValue = "false") Boolean toshow, PageCriteria criteria, HttpServletRequest request) {
        PageResult<UserFinanceVo> result = userFinanceService.findUserFinancePage(buildQueryCriteria(criteria,request));
        if (!toshow) {
            for (UserFinanceVo userFinanceVo : result.getRows()) {
                userFinanceVo.setUserName("");
                userFinanceVo.setRealName("");
                userFinanceVo.setMobile("");
            }
        }
        return result;
    }

    /**
     * 查询环迅资金信息
     * @param ids
     * @return
     */
    @RequestMapping(value="/ips/{ids}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result getUserIpsInfo(@PathVariable Integer[] ids) {
//        PageResult<UserFinanceVo> result = userFinanceService.findUserFinancePage(buildQueryCriteria(criteria,request));
        userIpsInfoService.updateIpsInfo(ids,"03");
        return Result.success();
    }

    /**------------统计----------------**/


    @RequestMapping(value = "findAllCapital", method = RequestMethod.POST)
    @ResponseBody
    public Result findAllCapital(HttpServletRequest request) {
        List result = capitalService.findAllCapital(request.getParameter("mobile"),request.getParameter("type"),request.getParameter("method"),
                request.getParameter("createDateBegin"),request.getParameter("createDateEnd"));
        return Result.success("成功",result);
    }

    @RequestMapping(value = "findAllUserFund", method = RequestMethod.POST)
    @ResponseBody
    public Result findAllUserFund(HttpServletRequest request) {
        List result = userFinanceService.findAllUserFund(request.getParameter("mobile"), request.getParameter("startCreateDate"), request.getParameter("endCreateDate"));
        return Result.success("成功",result);
    }
    @RequestMapping(value = "findAllPlatFormAmt", method = RequestMethod.POST)
    @ResponseBody
    public Result findAllPlatFormAmt(HttpServletRequest request) {
        List result = platformCapitalService.findAllPlatFormAmt(request.getParameter("operator"),request.getParameter("type"),
                request.getParameter("method"),request.getParameter("createDateBegin"),request.getParameter("createDateEnd"));
        return Result.success("成功",result);
    }

    /**
     * 描述：导出Excel
     * @param request
     * @throws Exception
     */
    @RequestMapping(value="exportExcelUserFund")
    public ModelAndView exportExcelUserFund(ModelMap model, HttpServletRequest request){

        List<UserFinanceVo> result = userFinanceService.findAllUserFund(request.getParameter("filter_LIKES_mobile"), request.getParameter("filter_GTD_u.create_date"), request.getParameter("filter_LTD_u.create_date"));
        String[] properties = { "id", "userName", "realName", "idNo", "mobile", "balance", "frozen",
                "borrowingAmts", "investmentAmts", "credit", "debit","experience","createDate"};
        String[] titles = { "记录编号", "登录名", "姓名", "身份证号", "手机号", "余额(元)", "冻结(元)",
                "借款总额", "投资总额", "待收金额", "待还金额", "体验金","注册日期"};
        String filename = "userFinance_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
        BigDecimal balance = new BigDecimal(0);
        BigDecimal frozen = new BigDecimal(0);
        BigDecimal borrowingAmts = new BigDecimal(0);
        BigDecimal investmentAmts = new BigDecimal(0);
        BigDecimal credit = new BigDecimal(0);
        BigDecimal debit = new BigDecimal(0);
        BigDecimal experience = new BigDecimal(0);
        for (UserFinanceVo vo:result){
            balance = balance.add(vo.getBalance());
            frozen = frozen.add(vo.getFrozen());
            borrowingAmts = borrowingAmts.add(vo.getBorrowingAmts());
            investmentAmts = investmentAmts.add(vo.getInvestmentAmts());
            credit = credit.add(vo.getCredit());
            debit = debit.add(vo.getDebit());
            experience = experience.add(vo.getExperience());
        }
        String[] contents = {"余额共计:"+balance+"元","冻结共计:"+frozen+"元","借款总额共计:"+borrowingAmts+"元",
                "投资总额共计:"+investmentAmts+"元","待收金额共计:"+credit+"元","待还金额共计:"+debit+"元","体验金共计:"+experience+"元",
                "导出条数：" + result.size(), "操作日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) };
        return new ModelAndView(new ExcelView(filename, null, properties, titles, null, null, result,
                contents), model);
    }

    /**
     * 描述：导出Excel
     * @param request
     * @throws Exception
     */
    @RequestMapping(value="exportCapitalRecord")
    public ModelAndView exportCapitalRecord(ModelMap model, HttpServletRequest request){

        List<CapitalVo> result = capitalService.findAllCapital(request.getParameter("filter_LIKES_mobile"),request.getParameter("filter_EQS_c.type"),
                request.getParameter("filter_EQS_method"), request.getParameter("createDateBegin"),
                request.getParameter("createDateEnd"));
        String[] properties = { "id", "mobile", "typeStr", "methodStr", "credit", "debit", "frozen",
                "unfrozen",/* "credits",  "debits",*/"balance","operator","ip","memo","strCreateDate"};
        String[] titles = { "记录编号", "手机号", "类型", "方式", "收入(元)", "支出(元)", "冻结(元)",
                "解冻(元)", "余额(元)","操作员","ip","备注","创建时间"};
        String filename = "capitalRecord_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
        BigDecimal balance = new BigDecimal(0);
        BigDecimal credit = new BigDecimal(0);
        BigDecimal debit = new BigDecimal(0);
        BigDecimal frozen = new BigDecimal(0);
        BigDecimal unfrozen = new BigDecimal(0);
        for (CapitalVo vo:result){
            balance = balance.add(vo.getBalance());
            credit = credit.add(vo.getCredit());
            debit = debit.add(vo.getDebit());
            frozen = frozen.add(vo.getFrozen());
            unfrozen = unfrozen.add(vo.getUnfrozen());
            vo.setStrCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vo.getCreateDate()));
        }
        String[] contents = {"余额共计:"+balance+"元",
                "收入共计:"+credit+"元","支出共计:"+debit+"元","冻结共计:"+frozen+"元","解冻共计:"+unfrozen+"元",
                "导出条数：" + result.size(), "操作日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) };
        return new ModelAndView(new ExcelView(filename, null, properties, titles, null, null, result,
                contents), model);
    }
    /**
     * 描述：导出Excel
     * @param request
     * @throws Exception
     */
    @RequestMapping(value="exportExcelPlatForm")
    public ModelAndView exportExcelPlatForm(ModelMap model, HttpServletRequest request){

        List<PlatformCapitalVo> result = platformCapitalService.findAllPlatFormAmt(request.getParameter("operator"),request.getParameter("type"),
                request.getParameter("method"),request.getParameter("createDateBegin"),request.getParameter("createDateEnd"));
        String[] properties = { "id","typeStr", "methodStr", "credit", "debit", "userLoginName",
                "operator", "ip","memo","createDate"};
        String[] titles = { "记录ID", "类型", "方式", "收入(元)", "支出(元)", "关联记录账户",
                "操作员","ip","备注","创建时间"};
        String filename = "platFormCapital_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
        BigDecimal credit = new BigDecimal(0);
        BigDecimal debit = new BigDecimal(0);
        for (PlatformCapitalVo vo:result){
            credit = credit.add(vo.getCredit());
            debit = debit.add(vo.getDebit());
        }
        String[] contents = {"收入共计:"+credit+"元","支出共计:"+debit+"元",
                "导出条数：" + result.size(), "操作日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) };
        return new ModelAndView(new ExcelView(filename, null, properties, titles, null, null, result,
                contents), model);
    }
}
