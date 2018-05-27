package com.klzan.p2p.mapper;

import com.klzan.core.persist.mybatis.MyMapper;
import com.klzan.p2p.model.UserAutoInvestmentRank;

import java.util.List;
import java.util.Map;

/**
 * Created by suhao Date: 2017/4/10 Time: 16:13
 *
 * @version: 1.0
 */
public interface UserAutoInvestmentRankMapper extends MyMapper<UserAutoInvestmentRank> {
    /**
     * 查询有效自动投标排行列表
     * @param params
     * @return
     */
    List<UserAutoInvestmentRank> findEffectiveList(Map params);
}