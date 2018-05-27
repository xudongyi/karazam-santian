package com.klzan.p2p.service.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.UserAutoInvestmentRank;
import com.klzan.p2p.vo.user.UserAutoInvestVo;
import com.klzan.p2p.vo.user.UserVo;

import java.util.List;
import java.util.Map;

/**
 * Created by suhao on 2017/5/26.
 */
public interface UserAutoInvestmentRankService extends IBaseService<UserAutoInvestmentRank> {

    void addOrMergeRank(UserAutoInvestVo autoInvestVo);

    List<UserAutoInvestmentRank> findEffectiveList(Integer borrowingId);

    UserAutoInvestmentRank findByUserId(Integer userId);

    void updateOpenStatus(Integer userId, boolean openStatus);

    Integer findHasSign();

    Integer findEffectiveSign();

    Integer getUserRank(Integer userId);

    PageResult<UserAutoInvestmentRank> findPage(PageCriteria criteria, Map map);
}
