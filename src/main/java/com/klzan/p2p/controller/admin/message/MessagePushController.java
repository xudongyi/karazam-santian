package com.klzan.p2p.controller.admin.message;
import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.service.message.MessagePushService;
import com.klzan.p2p.vo.message.MessagePushVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * Created by suhao Date: 2017/1/16 Time: 14:31
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin/messagepush")
public class MessagePushController extends BaseController {
    @Inject
    private MessagePushService messagePushService;

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("message:pushpage")
    public String index() {
        return "message/index";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @RequiresPermissions("message:pushpage")
    @ResponseBody
    public Object json(PageCriteria criteria) {
        PageResult<MessagePushVo> page = messagePushService.findPushMessagePage(criteria);
        return getPageResult(page);
    }

    @RequestMapping(value = "push", method = RequestMethod.GET)
    @RequiresPermissions("message:push")
    public String pushIndex() {
        return "message/push";
    }

    @RequestMapping(value = "/push", method = RequestMethod.POST)
    @RequiresPermissions("message:push")
    @ResponseBody
    public Result save(MessagePushVo vo) {
        messagePushService.pushMessageToAll(vo.getType(), vo.getNotificationTitle(), vo.getTitle(), vo.getContent(), "");
        return Result.success();
    }
}
