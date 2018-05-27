/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.user.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.user.UserThirdPlatFormDao;
import com.klzan.p2p.enums.OtherPlatform;
import com.klzan.p2p.model.UserThirdAccount;
import com.klzan.p2p.service.user.UserThirdPlatfromService;
import com.klzan.p2p.vo.user.UserThirdAccountVo;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * 第三方平台
 * Created by suhao Date: 2016/11/3 Time: 15:45
 *
 * @version: 1.0
 */
@Service
public class UserThirdPlatfromServiceImpl extends BaseService<UserThirdAccount> implements UserThirdPlatfromService {
    @Inject
    private UserThirdPlatFormDao userThirdPlatFormDao;

    @Override
    public void create(UserThirdAccountVo thirdAccountVo) {
        OtherPlatform platform = thirdAccountVo.getPlatform();
        String openid = thirdAccountVo.getOpenid();
        if (isExist(openid, platform)) {
            return;
        }
        UserThirdAccount thirdAccount = new UserThirdAccount(thirdAccountVo.getNickname(),
                thirdAccountVo.getAvatar(),
                thirdAccountVo.getSex(),
                thirdAccountVo.getCountry(),
                thirdAccountVo.getProvince(),
                thirdAccountVo.getCity(),
                thirdAccountVo.getUnionid(),
                openid,
                thirdAccountVo.getAccessToken(),
                platform);
        userThirdPlatFormDao.persist(thirdAccount);
    }

    @Override
    public Boolean isExist(String openid, OtherPlatform platform) {
        UserThirdAccount thirdAccount = userThirdPlatFormDao.findByOpenIdAndPlatform(openid, platform);
        return null == thirdAccount ? false : true;
    }

    @Override
    public UserThirdAccount findByOpenIdAndPlatform(String openid, OtherPlatform platform) {
        return userThirdPlatFormDao.findByOpenIdAndPlatform(openid, platform);
    }

    @Override
    public Boolean isBindUser(String openid, OtherPlatform platform) {
        UserThirdAccount thirdAccount = findByOpenIdAndPlatform(openid, platform);
        return null == thirdAccount.getUserId() ? false : true;
    }

    @Override
    public void bindThirdAccToUser(String openid, OtherPlatform platform, Integer userId) {
        UserThirdAccount thirdAccount = findByOpenIdAndPlatform(openid, platform);
        thirdAccount.updateUserId(userId);
        userThirdPlatFormDao.merge(thirdAccount);
    }
}
