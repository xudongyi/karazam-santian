package com.klzan.p2p.mapper;

import com.klzan.core.persist.mybatis.MyMapper;
import com.klzan.p2p.model.UserMeta;
import com.klzan.p2p.vo.user.UserMetaVo;

import java.util.List;

/**
 * Created by suhao Date: 2017/4/10 Time: 16:13
 *
 * @version: 1.0
 */
public interface UserMetaMapper extends MyMapper<UserMeta> {
    List<UserMetaVo> findMetasByType(UserMetaVo para);
}
