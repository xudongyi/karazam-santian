/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.user;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.OtherPlatform;
import com.klzan.p2p.model.UserThirdAccount;
import com.klzan.p2p.vo.user.UserThirdAccountVo;

/**
 * Created by suhao on 2016/11/3.
 */
public interface UserThirdPlatfromService extends IBaseService<UserThirdAccount> {
    /**
     * 保存第三方用户信息
     * @param thirdAccountVo
     */
    void create(UserThirdAccountVo thirdAccountVo);

    /**
     * 是否存在第三方账号
     * @param openid
     * @param platform
     * @return
     */
    Boolean isExist(String openid, OtherPlatform platform);

    /**
     * 查找第三方账号
     * @param openid
     * @param platform
     * @return
     */
    UserThirdAccount findByOpenIdAndPlatform(String openid, OtherPlatform platform);

    /**
     * 是否绑定平台账户
     * @param openid
     * @param platform
     * @return
     */
    Boolean isBindUser(String openid, OtherPlatform platform);

    /**
     * 绑定第三方账户到平台账户
     * @param openid
     * @param platform
     * @param userId
     */
    void bindThirdAccToUser(String openid, OtherPlatform platform, Integer userId);
}
