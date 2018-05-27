package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.util.DigestUtils;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.mobile.vo.UserGesPasswordVo;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.enums.UserLogType;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserLog;
import com.klzan.p2p.service.user.UserLogService;
import com.klzan.p2p.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static com.klzan.core.util.DigestUtils.decrypt;

/**
 * Created by Administrator on 2017/5/18 0018.
 */
@Controller("mobileUCGesPasswordController")
@RequestMapping("/mobile/uc/")
public class GesPasswordController {

    @Inject
    private UserService userService;
    @Resource
    private UserLogService userLogService;
    //设置手势密码

    @RequestMapping(value = "/setGesPassword", method = RequestMethod.POST)
    @ResponseBody
    public Result setGesPassword(@CurrentUser User user,@RequestBody UserGesPasswordVo userGesPasswordVo, HttpServletRequest request) {
        if(user==null){
            Result.error("请先登录");
        }
        userGesPasswordVo.setUserId(user.getId());
        String gesPassword = userGesPasswordVo.getGesPassword();

        String decrypt = DigestUtils.decrypt(gesPassword);
        gesPassword = DigestUtils.md5(decrypt);
        user.setGesPassword(gesPassword);
        userService.update(user);
        UserLog userLog = new UserLog(UserLogType.REGIST, new String(userGesPasswordVo.getClientType().getDisplayName() + "设置手势密码"), user.getLoginName(), WebUtils.getRemoteIp(request), user.getId());
        userLogService.persist(userLog);
        Map<String, Object> map = new HashMap<>();
        map.put("gesPassword", gesPassword);
        return Result.success("手势密码设置成功", map);
    }

//    ios 没用
    @RequestMapping(value = "/checkGesPassword",method = RequestMethod.POST)
    @ResponseBody
    public Result checkGesPassword(@CurrentUser User user,@RequestParam String gespassword){
        if(user==null){
           return Result.error("用户未登录");
        }
        String decrypt= DigestUtils.decrypt(gespassword);
        gespassword=DigestUtils.md5(decrypt);
        if(user.getGesPassword().equals(gespassword)){
           return Result.success("验证成功");
        }
        return  Result.error("手势密码错误");
    }

}
