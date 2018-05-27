package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.StringUtils;
import com.klzan.mobile.vo.UserRegistrationVo;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.enums.MessagePushType;
import com.klzan.p2p.enums.PostsTaxonomyType;
import com.klzan.p2p.model.MessagePush;
import com.klzan.p2p.model.PostsContent;
import com.klzan.p2p.model.PostsTaxonomy;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.message.MessagePushService;
import com.klzan.p2p.service.posts.PostsContentService;
import com.klzan.p2p.service.posts.PostsTaxonomyService;
import com.klzan.p2p.service.posts.impl.PostsTaxonomyServiceImpl;
import com.klzan.p2p.service.user.impl.UserDeviceService;
import com.klzan.p2p.vo.posts.PostsContentVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.klzan.p2p.controller.BaseController.SUCCESS_MSG;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
@Controller("mobileUCMessageController")
@RequestMapping("/mobile/uc/message")
public class MessageController {

    @Inject
    private MessagePushService messagePushService;

    @Inject
    private UserDeviceService userDeviceService;
    @Inject
    private PostsTaxonomyService postsTaxonomyService;
    @Inject
    private PostsContentService postsContentService;

    @RequestMapping
    @ResponseBody
    public Result messagePush(@CurrentUser User user,String type,PageCriteria criteria){

        if(user==null){
            return  Result.error("请先登录");
        }
        PageResult<MessagePush> messagePushPageResult=new PageResult<>();
        Map map= new HashMap();
        List pageList=new ArrayList();
        switch (type){
            case "USEROWN":
                messagePushPageResult = messagePushService.findListByType(criteria,user.getId(),MessagePushType.new_project,MessagePushType.repayment,MessagePushType.userown);
                if (messagePushPageResult==null){
                    return Result.error("暂无内容");
                }
                for (MessagePush messagePush : messagePushPageResult.getRows()) {
                    Map contMap= new HashMap();
                    contMap.put("text",messagePush.getContent());
                    contMap.put("textDes",messagePush.getContent());
                    contMap.put("title",messagePush.getTitle());
                    contMap.put("time",messagePush.getModifyDate());
                    pageList.add(contMap);
                }
                map.put("pages",messagePushPageResult.getPages());
                map.put("messagelist",pageList);
                break;
            case "NOTICE":
                PostsTaxonomy taxonomy = postsTaxonomyService.find(PostsTaxonomyType.CATEGORY.name(), "gonggao_mobile");
                if (taxonomy==null){
                    return Result.error("暂无内容");
                }
                PostsContentVo postsContentVo = new PostsContentVo();
                postsContentVo.setTaxonomyId(taxonomy.getId());
                postsContentVo.setListSize(null);
                postsContentVo.setTaxonomyType(taxonomy.getType());
                postsContentVo.setTaxonomyDisplay(true);
                PageResult<PostsContent> page = postsContentService.findPageUnderTaxonomy(criteria, postsContentVo);
                if (page.getRows()==null){
                    return Result.error("暂无内容");
                }
                for (PostsContent content : page.getRows()) {
                    Map contMap= new HashMap();
                    contMap.put("text",content.getText());
                    contMap.put("textDes",content.getText().replaceAll("</?[^>]+>","")
                            .replaceAll("\\s*|\t|\r|\n","")
                            .replaceAll("<style[^>]*?>[\\\\s\\\\S]*?<\\\\/style>","")
                            .replaceAll("<script[^>]*?>[\\\\s\\\\S]*?<\\\\/script>",""));
                    contMap.put("title",content.getTitle());
                    contMap.put("time",content.getModifyDate());
                    pageList.add(contMap);
                }

                map.put("messagelist", pageList);
                map.put("pages", page.getPages());
                break;
            default:
                return Result.error("参数错误");
        }
        return Result.success(SUCCESS_MSG, map);
    }
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public Result registration(@CurrentUser User currentUser, @RequestBody UserRegistrationVo userRegistrationVo) {
        // 会员验证
        if (currentUser == null) {
            return Result.error("请先登录");
        }
        userRegistrationVo.setUserId(currentUser.getId());
        if (StringUtils.isBlank(userRegistrationVo.getRegistrationId())) {
            return Result.error("设备标识不能为空");
        }
        try {
            userDeviceService.addUserRegistrationId(userRegistrationVo);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
        return Result.success("操作成功");
    }
}
