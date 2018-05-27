package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.util.StringUtils;
import com.klzan.mobile.vo.UserRegistrationVo;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.service.user.impl.UserDeviceService;
import com.klzan.p2p.vo.user.UserIdentityVo;
import com.klzan.p2p.vo.user.UserVo;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/16 0016.
 * 账户信息
 */
@Controller("mobileUCAccountInfoController")
@RequestMapping(value = "/mobile/uc/accountInfo/")
public class AccountInfoController {

    @Inject
    private UserService userService;
    @Autowired
    private UserDeviceService userDeviceService;

    @RequestMapping(method= RequestMethod.GET)
    public Result indexAccountInfo(@CurrentUser User user){
        Map<String,Object> map= new HashMap<>();
        if(user==null){
            Result.error("请先登录");
        }
        UserVo userVo= userService.getUserById(user.getId());
        Boolean isCertify=false;
        if(!StringUtils.isBlank(userVo.getIdNo())){
            isCertify=true;
        }
        map.put("isCertify",isCertify);
        map.put("accountName",userVo.getName());
        return Result.success("success",map);
    }
    @RequestMapping(value = "saveUserDevice")
    @ResponseBody
    public Result saveUserDevice(@CurrentUser User user,UserRegistrationVo userRegistrationVo){
        if(user==null){
            return Result.error("请先登录");
        }
        if(StringUtils.isBlank(userRegistrationVo.getRegistrationId())){
            return Result.error("设备标识不能为空");
        }
        try {
            userDeviceService.addUserRegistrationId(userRegistrationVo);
        }catch (Throwable e){
            return Result.error(e.getMessage());
        }
        return Result.success();
    }
}
