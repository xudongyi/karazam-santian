/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.token;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.ClientType;
import com.klzan.p2p.model.AccessToken;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 令牌
 * @author: chenxinglin
 */
@Repository
public class AccessTokenDao extends DaoSupport<AccessToken> {

    /**
     * 令牌
     * @param token
     * @return
     */
    public AccessToken find(String token) {
        StringBuffer hql = new StringBuffer("From AccessToken t WHERE 1=1 ");
        Map<String, Object> params = new HashMap();
        if(token != null){
            hql.append(" AND t.token = :token ");
            params.put("token", token);
        }
        return this.findUnique(hql.toString(), params);
    }

    /**
     * 令牌
     * @param token
     * @return
     */
    public AccessToken find(Integer userId, String token, ClientType type) {/*t.deleted = 0*/
        StringBuffer hql = new StringBuffer("From AccessToken t WHERE t.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(userId != null){
            hql.append(" AND t.userId = :userId ");
            params.put("userId", userId);
        }
        if(token != null){
            hql.append(" AND t.token = :token ");
            params.put("token", token);
        }
        if(type != null){
            hql.append(" AND t.type = :type ");
            params.put("type", type);
        }
        return this.findUnique(hql.toString(), params);
    }

    /**
     * 令牌
     * @param token
     * @return
     */
    public AccessToken findAppToken(Integer userId, String token) {
        StringBuffer hql = new StringBuffer("From AccessToken t WHERE t.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(userId != null){
            hql.append(" AND t.userId = :userId ");
            params.put("userId", userId);
        }
        if(token != null){
            hql.append(" AND t.token = :token ");
            params.put("token", token);
        }
        hql.append(" AND ( t.type = :type1 OR t.type = :type2 ) ");
        params.put("type1", ClientType.ANDROID);
        params.put("type2", ClientType.IOS);
        return this.findUnique(hql.toString(), params);
    }

}