package com.klzan.p2p.service.sms.impl;

import com.klzan.core.SpringObjectFactory;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.sms.SmsTokenDao;
import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.model.SmsToken;
import com.klzan.p2p.service.sms.SmsTokenService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service - 令牌
 */
@Service
public class SmsTokenServiceImpl extends BaseService<SmsToken> implements SmsTokenService {

    @Autowired
    private SmsTokenDao tokenDao;

    @Autowired
    private SpringObjectFactory springObjectFactory;

    @Override
    @Transactional
    public SmsToken build(SmsType type, String addr) throws Exception {

        // 参数验证
        if (type == null || StringUtils.isBlank(addr)) {
            throw new RuntimeException();
        }

        // TODO 重发令牌
        SmsToken pToken = tokenDao.findByAddr(addr);
        //测试
        SpringObjectFactory.Profile profile = springObjectFactory.getActiveProfile();
        boolean isProd = profile == SpringObjectFactory.Profile.PROD;
        String randomNumeric = !isProd ? "000000" : RandomStringUtils.randomNumeric(6);
        if (pToken != null) {
            if (!pToken.getRetry().before(new Date())) {
                throw new BusinessProcessException("短信重发时间未到");
            }
            if (!pToken.verifyRetry(type)) {
                throw new BusinessProcessException("短信类型不正确");
            }
            pToken.setType(type);
            pToken.setAddr(addr);
            pToken.setCode(randomNumeric);
            pToken.setExpiry(DateUtils.addMinutes(new Date(), 30));
            pToken.setRetry(DateUtils.addMinutes(new Date(), 1));
            pToken.setCount(pToken.getCount() + 1);
            if (pToken.getCount() == SmsToken.COUNT) {
                pToken.setRetry(DateUtils.getZeroDate(DateUtils.addMinutes(new Date(), 1)));
                pToken.setCount(0);
            }
            tokenDao.merge(pToken);
            return pToken;
        }

        // TODO 新发令牌
        SmsToken token = new SmsToken();
        token.setCount(0);
        token.setType(type);
        token.setAddr(addr);
        token.setCode(randomNumeric);
        token.setExpiry(DateUtils.addMinutes(new Date(), 30));
        token.setRetry(DateUtils.addMinutes(new Date(), 1));
        tokenDao.persist(token);
        return token;
    }

    @Override
    @Transactional
    public boolean verify(SmsType type, String addr, String code, boolean isDelete) {
        SmsToken pToken = tokenDao.findByAddr(addr);
        if (pToken != null && pToken.verify(type, addr, code)) {
            if (isDelete) {
                tokenDao.delete(pToken);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean verify(SmsType type, String addr, String code) {
        SmsToken pToken = tokenDao.findByAddr(addr);
        return pToken != null && pToken.verify(type, addr, code);
    }

    @Override
    public PageResult<SmsToken> findAllSmsTokenPage(PageCriteria criteria, HttpServletRequest request) {
        Map map = new HashMap();
        map.put("type",request.getParameter("type"));
        return myDaoSupport.findPage("com.klzan.p2p.mapper.SmsTokenMapper.findSmsTokens", map, criteria);
    }
}