package com.klzan.p2p.service.recharge;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.RechargeRecord;
import com.klzan.p2p.vo.recharge.RechargeVo;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.ips.appRecharge.vo.IpsPayAppRechargeResponse;
import com.klzan.plugin.pay.ips.recharge.vo.IpsPayRechargeResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by suhao Date: 2016/12/8 Time: 10:35
 *
 * @version: 1.0
 */
public interface RechargeService extends IBaseService<RechargeRecord> {
    /**
     * 添加充值记录
     * @param rechargeVo
     */
    RechargeRecord addRecord(RechargeVo rechargeVo);

    /**
     * 充值成功
     * @param orderNo
     * @param rechargeResponse
     */
    void rechargeSuccess(String orderNo, IpsPayRechargeResponse rechargeResponse);

    /**
     * 充值成功
     * @param orderNo
     */
    void rechargeSuccess(String orderNo);

    /**
     * 投资成功充值
     * @param orderNo
     */
    void rechargeFromInvestSuccess(String orderNo);

    /**
     * App充值成功
     * @param orderNo
     * @param rechargeResponse
     */
    void appRechargeSuccess(String orderNo, IpsPayAppRechargeResponse rechargeResponse);
    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     */
    RechargeRecord findByOrderNo(String orderNo);

    /**
     * 根据用户查询分页数据
     * @param userId
     * @param criteria
     * @param status
     * @return
     */
    PageResult<RechargeRecord> findPageByUser(Integer userId, PageCriteria criteria, RecordStatus status);

    /**
     * 用户充值列表
     * @param userId
     * @return
     */
    List<RechargeRecord> findByUser(Integer userId);
}
