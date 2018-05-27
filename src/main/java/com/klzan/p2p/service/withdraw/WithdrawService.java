package com.klzan.p2p.service.withdraw;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.WithdrawRecord;
import com.klzan.p2p.vo.withdraw.WithdrawalFeeVo;
import com.klzan.p2p.vo.withdraw.WithdrawalVo;
import com.klzan.plugin.pay.ips.withdrawrefticket.vo.IpsPayWithdrawRefundTicketResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by suhao on 2016/12/15.
 */
public interface WithdrawService extends IBaseService<WithdrawRecord> {
    /**
     * 添加提现记录
     * @param withdrawalVo
     */
    WithdrawRecord addRecord(WithdrawalVo withdrawalVo);

    /**
     * 查询用户提现中的记录
     * @param userId
     * @return
     */
    List<WithdrawRecord> findWithdrawing(Integer userId);
/*
* mobile  查询提现(申请和申请中)
*
* */
    List<WithdrawRecord> findWithdrawingMobile(Integer userId);
    /**
     * 提现记录
     * @param status
     * @return
     */
    List<WithdrawRecord> findList(RecordStatus status);

    /**
     * 查询用户已提现的记录
     * @param userId
     * @param success
     * @return
     */
    List<WithdrawRecord> findByUser(Integer userId, RecordStatus success);

    /**
     * 根据用户查询列表
     * @param userId
     * @return
     */
    List<WithdrawRecord> findByUser(Integer userId);

    /**
     * 查找提现记录
     * @param orderNo
     * @return
     */
    WithdrawRecord findByOrderNo(String orderNo);

    /**
     * 提现成功
     * @param orderNo
     */
    void withdrawSuccess(String orderNo);

    /**
     * 提现失败
     * @param orderNo
     */
    void withdrawFailure(String orderNo);

    /**
     * 提现退票
     * @param orderNo
     * @param refund
     */
    void withdrawRefund(String orderNo, IpsPayWithdrawRefundTicketResponse refund);

    /**
     * 手续费计算
     * @param amount
     * @return
     */
    WithdrawalFeeVo calculateFee(Integer userId, BigDecimal amount);

    /**
     * 根据用户查询分页数据
     * @param userId
     * @param criteria
     * @param status
     * @return
     */
    PageResult<WithdrawRecord> findPageByUser(Integer userId, PageCriteria criteria, RecordStatus status);

}
