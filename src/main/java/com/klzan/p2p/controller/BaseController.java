package com.klzan.p2p.controller;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.core.web.StringEscapeEditor;
import com.klzan.p2p.security.user.UserPrincipal;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description：基础 controller
 */
public abstract class BaseController {
    protected static Logger logger = LoggerFactory.getLogger(IndexController.class);

    public static final String SUCCESS_MSG = "操作成功";
    public static final String ERROR_MSG = "操作失败";
    protected static final String HOMEPAGE_REDIRECT_URL = "redirect:/";
    protected static final String ERROR_VIEW = "/error";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor());
    }

    /**
     * 获取当前登录用户对象
     * @return
     */
    public UserPrincipal getShiroUser() {
        return (UserPrincipal) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前登录用户id
     * @return
     */
    public Integer getUserId() {
        return this.getShiroUser().getId();
    }

    /**
     * 获取当前登录用户名
     * @return
     */
    public String getUserName() {
        return this.getShiroUser().getName();
    }

    /**
     * 获取easyui分页数据
     *
     * @param page
     * @return map对象
     */
    public Object getPageResult(PageResult page) {
        Map<String, Object> map = new HashMap<>();
        map.put("rows", page.getRows());
        map.put("total", page.getTotal());
        return map;
    }

    public PageCriteria buildQueryCriteria(PageCriteria criteria, HttpServletRequest request) {
        ParamsFilter.buildFromHttpRequest(request, criteria);
        return criteria;
    }

}
