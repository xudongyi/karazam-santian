package com.klzan.p2p.service.user.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.user.UserShippingAddressDao;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.user.UserShippingAddressService;
import com.klzan.p2p.vo.user.UserShippingAddressVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * 用户收货地址
 * @author: chenxinglin
 */
@Service
@Transactional
public class UserShippingAddressServiceImpl extends BaseService<UserShippingAddress> implements UserShippingAddressService {

    @Inject
    private UserShippingAddressDao userShippingAddressDao;

    @Override
    public PageResult<UserShippingAddress> findPage(PageCriteria pageCriteria, Integer userId) {
        return userShippingAddressDao.findPage(pageCriteria, userId);
    }

    @Override
    public List<UserShippingAddress> findListByUserId(Integer userId) {
        return userShippingAddressDao.findListByUserId(userId);
    }

    @Override
    public UserShippingAddress add(User currentUser, UserShippingAddressVo vo) {

        if(vo.getPreferred()!=null && vo.getPreferred()){
            userShippingAddressDao.resetPreferred(currentUser.getId());
        }

        UserShippingAddress address = new UserShippingAddress();
        address.setUserId(currentUser.getId());
        address.setConsignee(vo.getConsignee());
        address.setArea(vo.getArea());
        address.setAddress(vo.getAddress());
//        address.setZipCode(vo.getZipCode());
        address.setMobile(vo.getMobile());
//        address.setTelephone(vo.getTelephone());
//        address.setMemo(vo.getMemo());
        address.setPreferred(vo.getPreferred());
        return userShippingAddressDao.persist(address);
    }

    @Override
    public UserShippingAddress update(User currentUser, UserShippingAddressVo vo) {

        if(vo.getPreferred()!=null && vo.getPreferred()){
            userShippingAddressDao.resetPreferred(currentUser.getId());
        }

        UserShippingAddress address = userShippingAddressDao.get(vo.getId());
        address.setUserId(currentUser.getId());
        address.setConsignee(vo.getConsignee());
        address.setArea(vo.getArea());
        address.setAddress(vo.getAddress());
//        address.setZipCode(vo.getZipCode());
        address.setMobile(vo.getMobile());
//        address.setTelephone(vo.getTelephone());
//        address.setMemo(vo.getMemo());
        address.setPreferred(vo.getPreferred());
        return userShippingAddressDao.merge(address);
    }

    @Override
    public Integer count(Integer userId) {
        return userShippingAddressDao.count(userId);
    }

}
