package com.klzan.p2p.service.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.Referral;
import com.klzan.p2p.vo.user.ReferralVo;

import java.util.List;

/**
 * 推荐关系
 *
 * @author zhu
 */
public interface ReferralService extends IBaseService<Referral> {

    /**
     * 查询推荐关系列表
     *
     * @param pageCriteria 分页信息
     * @return
     */
    PageResult<ReferralVo> findReferral(PageCriteria pageCriteria);

    /**
     * 新增推荐关系
     *
     * @param referral
     */
    void createReferral(Referral referral);

    /**
     * 获取指定推荐人的所有推荐关系
     *
     * @param id
     * @return
     */
    List<Referral> findListById(int id);
}
