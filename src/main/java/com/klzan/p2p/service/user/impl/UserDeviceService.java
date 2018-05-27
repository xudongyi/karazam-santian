package com.klzan.p2p.service.user.impl;


import com.klzan.core.util.StringUtils;

import com.klzan.mobile.vo.UserRegistrationVo;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.userdevice.UserDeviceDao;
import com.klzan.p2p.model.UserDevice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by suhao Date: 2017/1/9 Time: 15:25
 *
 * @version: 1.0
 */
@Service
public class UserDeviceService extends BaseService<UserDevice> {
    @Inject
    private UserDeviceDao userDeviceDao;

    public void addUserRegistrationId(UserRegistrationVo registrationVo) {
        List<UserDevice> list = userDeviceDao.findListByRegistrationId(registrationVo.getRegistrationId());
        if (!list.isEmpty()) {
            for (UserDevice userDevice : list) {
                if (!registrationVo.getUserId().equals(userDevice.getUserId()) && StringUtils.equals(userDevice.getRegistrationId(), registrationVo.getRegistrationId())) {
                    userDeviceDao.logicDeleteById(userDevice.getId());
                }
            }
        }

        UserDevice userDevice = userDeviceDao.findByUserId(registrationVo.getUserId());
        if (null == userDevice) {
            userDevice = new UserDevice();
        }
        userDevice.setUserId(registrationVo.getUserId());
        userDevice.setRegistrationId(registrationVo.getRegistrationId());
        userDevice.setOsType(registrationVo.getOsType());
        userDeviceDao.merge(userDevice);
    }
}
