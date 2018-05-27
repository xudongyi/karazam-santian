package com.klzan.p2p.controller.admin.user;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.model.Referral;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.user.ReferralService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.user.ReferralVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhutao on 2017/4/5.
 */
@Controller("adminreferral")
@RequestMapping("admin/referral")
public class ReferralController {
    @Resource
    private ReferralService referralService;
    @Resource
    private UserService userService;

    @RequestMapping("list")
    public String list(ModelMap modelMap){
        return "admin/user/referral";
    }
    @RequestMapping(value="/list.json",method = RequestMethod.GET)
    @ResponseBody
    public Object listQuery(PageCriteria pageCriteria, HttpServletRequest request){
        ParamsFilter.buildFromHttpRequest(request,pageCriteria);
        PageResult<ReferralVo> corporation = referralService.findReferral(pageCriteria);
        for (ReferralVo referralVo : corporation.getRows()) {
//            String reRealName=userService.getUserById(referralVo.getReUserId()).getRealName();
//            String realName=userService.getUserById(referralVo.getUserId()).getRealName();
//            if(StringUtils.isBlank(realName)){
//                realName="---";
//            }
//            if(StringUtils.isBlank(reRealName)){
//                realName="---";
//            }
//
//            referralVo.setReRealName(reRealName);
//            referralVo.setRealName(realName);
            referralVo.setUserMobile("");
            referralVo.setReUserMobile("");
        }
        return corporation;
    }
    @RequestMapping("update/{id}")
    public String updata(@PathVariable Integer id,ModelMap modelMap){
        Referral referral = referralService.get(id);
        User user = userService.get(referral.getUserId());
        User reUser = userService.get(referral.getReUserId());
        ReferralVo vo = new ReferralVo();
        vo.setUserId(user.getId());
        vo.setUserNickName(user.getName());
        vo.setUserMobile(user.getMobile());
        vo.setReUserNickName(reUser.getName());
        vo.setReUserMobile(reUser.getMobile());
        vo.setReferralFeeRate(referral.getReferralFeeRate());
        vo.setAvailable(referral.getAvailable());
        modelMap.addAttribute("referral", vo);
        modelMap.addAttribute("id", id);
        return "admin/user/referral_update";
    }

    /**
     * 修改推荐人
     * @param model
     * @return
     */
    @RequestMapping(value = "/update.do/{id}", method = RequestMethod.POST)
    @RequiresPermissions("user:referral:update")
    @ResponseBody
    public Result updateDo(@PathVariable("id") Integer id, ReferralVo vo, Model model) {
        //corporationService.updateCorporation(vo,id);
        try{
            Referral referral = referralService.get(id);
            Integer reUserId = referral.getReUserId();
            if(reUserId.intValue()==vo.getUserId().intValue()){
                return Result.error("推荐人和被推荐人不能为同一人");
            }
            referralService.remove(id);
            Referral newReferral = new Referral();
            newReferral.setReUserId(reUserId);
            newReferral.setUserId(vo.getUserId());
            newReferral.setAvailable(vo.getAvailable());
            newReferral.setReferralFeeRate(vo.getReferralFeeRate());
            referralService.persist(newReferral);
            return Result.success();
        }catch (Exception e){
            return Result.error();
        }
    }
}
