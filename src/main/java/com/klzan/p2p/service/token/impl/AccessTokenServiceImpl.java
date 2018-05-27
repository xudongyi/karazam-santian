package com.klzan.p2p.service.token.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.token.AccessTokenDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.token.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 令牌
 */
@Service
public class AccessTokenServiceImpl extends BaseService<AccessToken> implements AccessTokenService {

	@Autowired
	private AccessTokenDao accessTokenDao;

	@Override
	public Boolean isExist(String token) {
		AccessToken accessToken = accessTokenDao.find(token);
		return accessToken!=null;
	}

	@Override
	public AccessToken find(Integer userId, String token, ClientType type) {
		return accessTokenDao.find(userId, token, type);
	}

	@Override
	public AccessToken findAppToken(Integer userId, String token) {
		return accessTokenDao.findAppToken(userId, token);
	}
}
