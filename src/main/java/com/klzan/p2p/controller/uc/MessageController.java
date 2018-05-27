/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.uc;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.notice.NoticeService;
import com.klzan.p2p.vo.notice.NoticeVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * 消息管理
 * Created by zhutao Date: 2016/12/207 Time: 11:22
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("uc")
public class MessageController {

    @Resource
    private NoticeService noticeService;

    @RequestMapping("/notice")
    public String index(@CurrentUser User currentUser, Model model, HttpServletRequest request) {
        return "uc/message/notice";
    }

    @RequestMapping("/getnotice")
    @ResponseBody
    public PageResult<NoticeVo> findNoticeByPag(@CurrentUser User currentUser, PageCriteria criteria) {
        return noticeService.findNoticePageByUserId(currentUser.getId(),criteria);
    }

    @RequestMapping("/getNoticeDetail/{id}")
    public String findNoticeDetail(@PathVariable("id") Integer id, ModelMap model) {
        model.put("notice",noticeService.getNoticeDetail(id));
        return "uc/message/notice_detail";
    }
}
