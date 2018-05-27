/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.user;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.UserInfo;
import com.klzan.p2p.vo.user.UserVo;

import java.util.List;

public interface UserInfoService extends IBaseService<UserInfo> {

    List<UserInfo> findByIdNo(String idNo);

    List<UserInfo> findByIdNo(UserType type, String idNo);

    UserInfo getUserInfo(Integer userId);

    void addRealInfoIdentify(UserVo userVo);

    boolean hasCertification(Integer userId);
}
