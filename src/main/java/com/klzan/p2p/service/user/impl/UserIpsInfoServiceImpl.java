/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.user.impl;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.user.UserIpsInfoDao;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserIpsInfo;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.user.UserIpsInfoService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.comquery.vo.BalanceResponse;
import com.klzan.plugin.pay.ips.comquery.vo.IpsPayCommonQueryResponse;
import com.klzan.plugin.pay.ips.comquery.vo.UserInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserIpsInfoServiceImpl extends BaseService<UserIpsInfo> implements UserIpsInfoService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BusinessService businessService;
    @Resource
    private UserService userService;
    @Resource
    private UserIpsInfoDao userIpsInfoDao;

    @Override
    @Transactional
    public List updateIpsInfo(Integer[] ids,String type) {
        logger.debug("merge userIpsInfo method start");
        List<Integer> list = new ArrayList();
        for (Integer id:ids){
            try{
                User user = userService.get(id);
                if (user!=null && StringUtils.isNotBlank(user.getPayAccountNo())){
                    IDetailResponse response = null;
                    try {
                        response = businessService.query(type, user.getPayAccountNo());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (response!=null){

                        IpsPayCommonQueryResponse commonResponse = (IpsPayCommonQueryResponse)response;
                        UserIpsInfo userIpsInfo = userIpsInfoDao.queryByUserId(id);
                        if (userIpsInfo==null){
                            userIpsInfo = new UserIpsInfo();
                        }
                        //1.查询账户信息
                        if ("01".equals(type)){
                            UserInfoResponse userInfo = commonResponse.getUserInfo();
                            userIpsInfo.setUserId(id);
                            userIpsInfo.setAcctStatus(userInfo.getAcctStatus());
                            userIpsInfo.setUserCardStatus(userInfo.getuCardStatus());
                            userIpsInfo.setBankName(userInfo.getBankName());
                            userIpsInfo.setBankCard(userInfo.getBankCard());
                            userIpsInfo.setRepaySignStatus(userInfo.getSignStatus());
                            userIpsInfo.setQueryDate(new Date());
                        }
                        //2.查询资金信息
                        if ("03".equals(type)){
                            BalanceResponse balance = commonResponse.getBalance();
                            userIpsInfo.setUserId(id);
                            userIpsInfo.setCurBal(balance.getCurBal());
                            userIpsInfo.setAvailBal(balance.getAvailBal());
                            userIpsInfo.setFreezeBal(balance.getFreezeBal());
                            userIpsInfo.setRepaymentBal(balance.getRepaymentBal());
                            userIpsInfo.setQueryDate(new Date());
                        }
                        userIpsInfoDao.merge(userIpsInfo);
                        logger.debug("merge user [{}] success", user.getName());
                    }else {
                        list.add(id);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                list.add(id);
                logger.debug("查询并更新 id为:[{}]的用户托管信息失败",id);
            }
        }
        logger.debug("merge userIpsInfo method end");
        return list;
    }

    public UserIpsInfo queryByUserId(Integer id){
        return userIpsInfoDao.queryByUserId(id);
    }
}
