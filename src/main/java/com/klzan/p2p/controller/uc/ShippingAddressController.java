/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.PointType;
import com.klzan.p2p.model.Area;
import com.klzan.p2p.model.PointRecord;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserShippingAddress;
import com.klzan.p2p.service.content.AreaService;
import com.klzan.p2p.service.user.UserShippingAddressService;
import com.klzan.p2p.vo.user.UserShippingAddressVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收货地址
 * @author: chenxinglin
 */
@Controller("webUCShippingAddressController")
@RequestMapping("/uc/shipping_address")
public class ShippingAddressController extends BaseController {

    /**
     * 索引重定向URL
     */
    private static final String INDEX_REDIRECT_URL = "redirect:/uc/shipping_address";

    /**
     * 模板路径
     */
    private static final String TEMPLATE_PATH = "/uc/shipping_address";

    @Inject
    private UserShippingAddressService userShippingAddressService;

    /**
     * 列表
     */
    @RequestMapping
    public String investments(@CurrentUser User currentUser, ModelMap model, RedirectAttributes redirectAttributes) {
        if(currentUser == null){
            redirectAttributes.addFlashAttribute("flashMessage", "用户不存在");
            return INDEX_REDIRECT_URL;
        }
        List<UserShippingAddress> addresses = userShippingAddressService.findListByUserId(currentUser.getId());
        model.addAttribute("addresses", addresses);
        return TEMPLATE_PATH + "/index";
    }

    /**
     * 新增
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@CurrentUser User currentUser, UserShippingAddressVo vo) {
        if(currentUser == null){
            return Result.error("未登录");
        }
        if(vo == null){
            return Result.error("参数错误");
        }
        if(userShippingAddressService.count(currentUser.getId())>=10){
            return Result.error("最多新增10条地址");
        }
        try {
            userShippingAddressService.add(currentUser, vo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("新增失败");
        }
        return Result.success("新增成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@CurrentUser User currentUser, UserShippingAddressVo vo) {
        if(currentUser == null){
            return Result.error("未登录");
        }
        if(vo == null){
            return Result.error("参数错误");
        }
        try {
            userShippingAddressService.update(currentUser, vo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
//    @ResponseBody
    public String delete(@CurrentUser User currentUser, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if(currentUser == null){
//            return Result.error("未登录");
            redirectAttributes.addFlashAttribute("flashMessage", "未登录");
            return INDEX_REDIRECT_URL;
        }
        if(id == null){
//            return Result.error("参数错误");
            redirectAttributes.addFlashAttribute("flashMessage", "参数错误");
            return INDEX_REDIRECT_URL;
        }
        try {
            UserShippingAddress userShippingAddress = userShippingAddressService.get(id);
            if(userShippingAddress == null){
//                return Result.error("收货地址不存在");
                redirectAttributes.addFlashAttribute("flashMessage", "收货地址不存在");
                return INDEX_REDIRECT_URL;
            }
            if(!currentUser.getId().equals(userShippingAddress.getUserId())){
//                return Result.error("拒绝操作");
                redirectAttributes.addFlashAttribute("flashMessage", "拒绝操作");
                return INDEX_REDIRECT_URL;
            }
            userShippingAddressService.remove(userShippingAddress.getId());
        } catch (Exception e) {
            e.printStackTrace();
//            return Result.error("删除失败");
            redirectAttributes.addFlashAttribute("flashMessage", "删除失败");
            return INDEX_REDIRECT_URL;
        }
//        return Result.success("删除成功");
        redirectAttributes.addFlashAttribute("flashMessage", "删除成功");
        return INDEX_REDIRECT_URL;
    }

//    /**
//     *地区
//     */
//    @RequestMapping(value = "area/jsons", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<Integer, String> jsons(Integer parentId) {
//        List<Area> areas = new ArrayList<Area>();
//        if (parentId != null) {
//            areas = areaService.findChildren(parentId);
//        } else {
//            areas = areaService.findRoots();
//        }
//        Map<Integer, String> options = new HashMap<Integer, String>();
//        for (Area area : areas) {
//            options.put(area.getId(), area.getName());
//        }
//        return options;
//    }

}





