package com.klzan.p2p.service.user;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.IEnumMeta;
import com.klzan.p2p.model.UserMeta;
import com.klzan.p2p.vo.user.AbstractUserMeta;
import com.klzan.p2p.vo.user.UserMetaVo;

import java.util.List;

/**
 * Created by suhao on 2017/5/26.
 */
public interface UserMetaService extends IBaseService<UserMeta> {

    void addMetasByType(IEnumMeta meta, AbstractUserMeta userMeta);

    List<UserMetaVo> findMetasByType(IEnumMeta meta, Integer userId);

    <M> M convertToBean(IEnumMeta meta, Integer userId, Class clazz);

    void deleteMetas(IEnumMeta meta, Integer userId);
}
