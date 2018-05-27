package com.klzan.plugin.pay.common.module;

import com.klzan.core.exception.PayException;
import com.klzan.core.util.SpringUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.ProjectSettlementBatchRequest;
import com.klzan.plugin.pay.common.dto.RepaymentRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import com.klzan.plugin.pay.cpcn.ChinaClearingConstant;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.p2p.Tx3261Request;
import payment.api.vo.RongziProjectSettlementBatItem;

import java.util.ArrayList;

/**
 * 项目批量结算
 * Created by suhao Date: 2017/11/22 Time: 14:45
 *
 * @version: 1.0
 */
public class PayProjectSettlementBatchModule extends PayModule {

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.project_settlement_batch;
    }

    @Override
    public TxBaseRequest getReqParam() {

        ProjectSettlementBatchRequest psRequest = (ProjectSettlementBatchRequest)getRequest();
        if(psRequest.getOrders().size()>1000){
            throw new PayException("");
        }
        Borrowing borrowing = payUtils.getBorrowing(psRequest.getSettlement().getBorrowing());

        String settlementBatchNo = getSn();
        long totalAmount = 0;
        int totalCount = 0;
        ArrayList<RongziProjectSettlementBatItem> itemList = new ArrayList<>();
        String remark = "批量结算";

        for (PaymentOrder order : psRequest.getOrders()) {
            RongziProjectSettlementBatItem item = new RongziProjectSettlementBatItem();
            item.setSettlementNo(order.getOrderNo());
            item.setProjectNo(borrowing.getProjectNo());
            item.setAccountType(20);
            //结算类型 10=投资人 20=融资人 30=担保人 40=P2P平台方"
            //结算用途：10=融资人融资款20=担保公司担保费30=P2P 平台服务费40=投资收益50=投资撤回退款（指 悔的主动退款申请）60=投资超募退款 70=债权转让回款
            if(order.getType().equals(PaymentOrderType.REPAYMENT_FEE) || order.getType().equals(PaymentOrderType.RECOVERY_FEE)
                    || order.getType().equals(PaymentOrderType.REPAYMENT_EARLY_FEE) || order.getType().equals(PaymentOrderType.RECOVERY_EARLY_FEE)
                    || order.getType().equals(PaymentOrderType.TRANSFER_FEE)
                    || order.getType().equals(PaymentOrderType.LENDING_FEE)){ //平台服务费
                item.setSettlementType(40);
                item.setSettlementUsage("30");
                item.setPaymentAccountName(ChinaClearingConfig.PLAT_PAYMENT_ACCOUNT_NAME); //账户名称
                item.setPaymentAccountNumber(ChinaClearingConfig.PLAT_PAYMENT_ACCOUNT_NUMBER); //账户号码
            }else if(order.getType().equals(PaymentOrderType.TRANSFER)){
                item.setSettlementType(10);
                item.setSettlementUsage("70");
                item.setPaymentAccountName(payUtils.getPaymentAccountUsername(order.getUserId()));
                item.setPaymentAccountNumber(payUtils.getPaymentAccountNumber(order.getUserId()));
                item.setPaymentNo(payUtils.getPaymentNo(borrowing.getId(), order.getUserId()));
//                item.setPaymentNo(order.getExtOrderNo());
            }else if(order.getType().equals(PaymentOrderType.RECOVERY) || order.getType().equals(PaymentOrderType.RECOVERY_EARLY)){ //投资人回款
                item.setSettlementType(10);
                item.setSettlementUsage("40");
                item.setPaymentAccountName(payUtils.getPaymentAccountUsername(order.getUserId()));
                item.setPaymentAccountNumber(payUtils.getPaymentAccountNumber(order.getUserId()));
                item.setPaymentNo(payUtils.getPaymentNo(borrowing.getId(), order.getUserId()));
            }else if(order.getType().equals(PaymentOrderType.LENDING)){
                item.setSettlementType(20);
                item.setSettlementUsage("10");
                item.setPaymentAccountName(payUtils.getPaymentAccountUsername(order.getUserId()));
                item.setPaymentAccountNumber(payUtils.getPaymentAccountNumber(order.getUserId()));
                if(StringUtils.isNotBlank(borrowing.getBankAccountNumber())){
                    item.setAccountType(Integer.valueOf(payUtils.getUserType(order.getUserId())));
                    item.setBankID(borrowing.getBankID()); //银行账户开户行
                    item.setBankAccountName(borrowing.getBankAccountName()); //银行账户开户行
                    item.setBankAccountNumber(borrowing.getBankAccountNumber()); //银行账户号码
                    item.setBankBranchName(borrowing.getBankBranchName()); //银行账户分支行
                    item.setBankProvince(borrowing.getBankProvince()); //省份
                    item.setBankCity(borrowing.getBankCity()); //城市
                }

            }else if(order.getType().equals(PaymentOrderType.REFUND)){
                item.setSettlementType(10);
                item.setSettlementUsage("50");
                item.setPaymentAccountName(payUtils.getPaymentAccountUsername(order.getUserId()));
                item.setPaymentAccountNumber(payUtils.getPaymentAccountNumber(order.getUserId()));
                item.setPaymentNo(payUtils.getPaymentNo(borrowing.getId(), order.getUserId()));
            }else {
                throw new PayException("");
            }
            item.setAmount(payUtils.convertToFen(order.getAmount().subtract(order.getFee())));
            item.setRemark("");

            itemList.add(item);
            totalAmount += item.getAmount();
            totalCount++;
        }

        // 创建交易请求对象
        Tx3261Request request = new Tx3261Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setSettlementBatchNo(settlementBatchNo);
        request.setTotalAmount(totalAmount);
        request.setTotalCount(totalCount);
        request.setRemark(remark);
        request.setItemList(itemList);

        return request;
    }

}
