package com.klzan.p2p.service.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.ReferralFee;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.vo.business.Request;
import com.klzan.p2p.vo.user.ReferralFeeVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 推荐费
 *
 * @author zhu
 */
public interface ReferralFeeService extends IBaseService<ReferralFee> {

    /**
     * 查询推荐费列表
     *
     * @param pageCriteria 分页信息
     * @return
     */
    PageResult<ReferralFeeVo> findReferralFeeList(PageCriteria pageCriteria, Integer id);

    /**
     * 推荐关系推荐费
     * */
    PageResult<ReferralFeeVo> findReferralFeeAndReferralList(PageCriteria pageCriteria, Integer id);

    /**
     * 通过ID批量申请审核
     *
     * @param ids 推荐费唯一标示
     */
    void applyAuditing(Integer[] ids);

    /**
     * 通过ID审核推荐费
     *
     * @param referralFeeId
     * @param suggestion
     */
    void auditing(Integer referralFeeId, String suggestion);

    void transfer(Integer referralFeeId);

    /**
     * 获取指定推荐关系待结算的推荐费
     *
     * @param list
     * @return
     */
    BigDecimal getWillSettleReferralFee(List<Integer> list);

    List<ReferralFee> alreadySettlement(Integer userId);

    void offlinepaid(Integer[] ids);

    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     */
    ReferralFee findByOrderNo(String orderNo);

    List<ReferralFee> findByBatchOrderNo(String batchOrderNo);
}
