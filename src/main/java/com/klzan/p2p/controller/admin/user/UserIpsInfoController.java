package com.klzan.p2p.controller.admin.user;

import com.klzan.core.Result;
import com.klzan.p2p.service.user.UserIpsInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhutao on 2017/5/11
 */
@Controller
@RequestMapping("admin/ips")
public class UserIpsInfoController {

    @Resource
    private UserIpsInfoService userIpsInfoService;

    @RequestMapping(value = "update/{ids}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result updateIpsInfo(@PathVariable Integer[] ids){

        List list = userIpsInfoService.updateIpsInfo(ids,"01");

        StringBuffer info = new StringBuffer();

        if (list!=null && list.size()>0){
            info.append(";用户:");
            for (int i=0;i<list.size();i++){
                info.append(list.get(i));
                info.append(" ");
            }
            info.append(" 访问环迅接口异常");
        }
        return Result.success("操作成功"+info.toString());

    }
}
