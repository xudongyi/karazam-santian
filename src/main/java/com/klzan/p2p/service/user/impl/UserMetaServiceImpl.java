package com.klzan.p2p.service.user.impl;

import com.klzan.core.util.ObjectUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.user.UserMetaDao;
import com.klzan.p2p.enums.IEnumMeta;
import com.klzan.p2p.mapper.UserMetaMapper;
import com.klzan.p2p.model.UserMeta;
import com.klzan.p2p.service.user.UserMetaService;
import com.klzan.p2p.util.UserMetaUtils;
import com.klzan.p2p.vo.user.AbstractUserMeta;
import com.klzan.p2p.vo.user.UserMetaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by suhao Date: 2017/5/26 Time: 12:54
 *
 * @version: 1.0
 */
@Service
public class UserMetaServiceImpl extends BaseService<UserMeta> implements UserMetaService {

    @Autowired
    private UserMetaDao userMetaDao;

    @Autowired
    private UserMetaMapper userMetaMapper;

    @Override
    public void addMetasByType(IEnumMeta meta, AbstractUserMeta userMeta) {
        Integer userId = userMeta.getUserId();
        List<UserMetaVo> metas = findMetasByType(meta, userId);
        for (UserMetaVo userMetaVo : metas) {
            userMetaMapper.deleteByPrimaryKey(userMetaVo.getId());
        }

        List<UserMeta> userMetas = UserMetaUtils.convertBeanToMeta(userId, meta.getType(), userMeta);
        for (UserMeta pUserMeta : userMetas) {
            userMetaDao.persist(pUserMeta);
        }
    }

    @Override
    public List<UserMetaVo> findMetasByType(IEnumMeta meta, Integer userId) {
        UserMetaVo metaPara = new UserMetaVo();
        metaPara.setUserId(userId);
        metaPara.setMetaType(meta.getType());
        return userMetaMapper.findMetasByType(metaPara);
    }

    @Override
    public <M> M convertToBean(IEnumMeta meta, Integer userId, Class clazz) {
        List<UserMetaVo> metas = findMetasByType(meta, userId);
        if (metas.isEmpty()) {
            return ObjectUtils.newInstance(clazz.getPackage().getName() + "." +clazz.getSimpleName());
        }
        Object obj = UserMetaUtils.convert(metas, clazz);
        return (M) obj;
    }

    @Override
    public void deleteMetas(IEnumMeta meta, Integer userId) {
        List<UserMetaVo> metas = findMetasByType(meta, userId);
        for (UserMetaVo userMetaVo : metas) {
            userMetaMapper.deleteByPrimaryKey(userMetaVo.getId());
        }
    }
}
