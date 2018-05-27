/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.sms;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.SmsToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 短信令牌dao
 */
@Repository
public class SmsTokenDao extends DaoSupport<SmsToken> {
    public SmsToken findByAddr(String addr) {
        if (StringUtils.isBlank(addr)) {
            return null;
        }
        String hql = "select tokens from SmsToken tokens where lower(tokens.addr) = lower(?0)";
        return findUnique(hql, addr);
    }
}