package com.klzan.p2p.mapper;

import com.klzan.core.persist.mybatis.MyMapper;
import com.klzan.p2p.model.User;
import com.klzan.p2p.vo.user.UserVo;

import java.util.List;
import java.util.Map;

/**
 * Created by suhao Date: 2017/4/10 Time: 16:13
 *
 * @version: 1.0
 */
public interface UserMapper extends MyMapper<User> {
    List<UserVo> findUserDetailPage(Map map);
}
