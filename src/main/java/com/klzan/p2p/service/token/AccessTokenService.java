package com.klzan.p2p.service.token;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.ClientType;
import com.klzan.p2p.model.AccessToken;

/**
 * 令牌
 * @author
 *
 */
public interface AccessTokenService extends IBaseService<AccessToken> {

    /**
     * 令牌是否存在
     * @param token
     * @return
     */
    Boolean isExist(String token);

    /**
     * 查询令牌
     * @param token
     * @param type
     * @return
     */
    AccessToken find(Integer userId, String token, ClientType type);

    /**
     * 查询令牌
     * @param token
     * @param userId
     * @return
     */
    AccessToken findAppToken(Integer userId, String token);

}
