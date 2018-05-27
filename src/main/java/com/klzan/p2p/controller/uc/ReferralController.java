/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller.uc;

import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.model.Referral;
import com.klzan.p2p.model.ReferralFee;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.user.ReferralFeeService;
import com.klzan.p2p.service.user.ReferralService;
import com.klzan.p2p.service.user.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 推荐管理
 * Created by zhutao Date: 2016/12/207 Time: 11:22
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("uc")
public class ReferralController {

    @Resource
    private ReferralService referralService;
    @Resource
    private ReferralFeeService referralFeeService;
    @Inject
    private UserService userService;

    @RequestMapping("/referral")
    public String index(@CurrentUser User currentUser, Model model) {
        //推荐关系
        List<Referral> lists = referralService.findListById(currentUser.getId());

        //推荐人数
        model.addAttribute("referralCount", lists.size());
        //有效推荐人数、我的奖励
        int referralCountY = 0;
        List<User> listusers = new ArrayList<>();
        BigDecimal totalReferralFee = new BigDecimal(0);
        for (Referral list : lists) {
            listusers.add(userService.get(list.getReUserId()));
            if (list.getAvailable()) {
                referralCountY++;
            }
        }

        //已结算推荐费
        for (ReferralFee fee : referralFeeService.alreadySettlement(currentUser.getId())) {
            totalReferralFee = totalReferralFee.add(fee.getReferralFee());
        }
        //待结算推荐费
        //totalReferralFee.add(referralFeeService.getWillSettleReferralFee(listId));

        model.addAttribute("contEffect", referralCountY);
        //我的奖励
        model.addAttribute("referralFee", totalReferralFee);
        User user = userService.get(currentUser.getId());
        if (StringUtils.isBlank(user.getInviteCode())) {
            user.setInviteCode(DigestUtils.md5Hex(DateUtils.getTime() + "" + UUID.randomUUID()));
            userService.update(user);
        }
        model.addAttribute("inviteCode", user.getInviteCode());
        model.addAttribute("referrals", lists);
        model.addAttribute("reUsers",listusers);
        return "uc/referral/referral";
    }
}
