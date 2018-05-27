package com.klzan.mobile.controller.cpcn;

import com.klzan.core.Result;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.DeviceType;
import com.klzan.p2p.enums.PaymentOrderMethod;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.enums.TransferLoanState;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.model.Transfer;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.util.AccountantUtils;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.InvestmentRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * 项目支付
 */
@Controller("mobilePayInvestController")
@RequestMapping("/mobile/pay")
public class PayInvestController {

    protected static final String ERROR_VIEW = "/error";

    @Autowired
    private TransferService transferService;

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private UserService userService;

    @Autowired
    private RepaymentService repaymentService;

    @RequestMapping(value = "invest")
    @ResponseBody
    public Result index(Integer projectId, BigDecimal amount, DeviceType deviceType) {
        // 会员验证
        User currentUser = UserUtils.getCurrentUser();
        if (currentUser == null) {
            return Result.error("请先登陆");
        }

        currentUser = userService.get(currentUser.getId());
        // 验证项目
        Borrowing pProject = borrowingService.get(projectId);
        if (pProject == null) {
            return Result.error("项目不存在");
        }
        if (!pProject.verifyInvest()) {
            return Result.error("项目不可投");
        }
        // 验证是否为自己的项目
        if (currentUser.getId().equals(pProject.getBorrower())) {
            return Result.error("不可投资自己的项目");
        }
        // 验证是否为此项目担保人
        if (currentUser.equals(pProject.getGuaranteeCorp())) {
            return Result.error("不可投资自己担保的项目");
        }

        /** 投资验证 */
        BigDecimal investmentMinimum = pProject.getInvestmentMinimum();  // 最小可投
        BigDecimal surplusInvestmentAmount = pProject.getSurplusInvestmentAmount();  // 剩余可投
        BigDecimal investmentMaximum = pProject.getInvestmentMaximum(); // 最高可投
        switch (pProject.getInvestmentMethod()) {
            case MULTIPLE_OF_MINIMUM: { /** 最低投资倍数 */
                if (amount == null) {
                    return Result.error("请输入投资金额");
                }
                if ((amount.subtract(investmentMinimum)).intValue() % investmentMinimum.intValue() != 0) {
                    return Result.error("加价幅度需为" + investmentMinimum.intValue() + "的倍数");
                }
                // 验证可投余额
                if (surplusInvestmentAmount.compareTo(investmentMinimum) == -1) {
                    if (!(amount.compareTo(surplusInvestmentAmount) == 0)) {
                        return Result.error("请输入正确的投资金额");
                    }
                } else {
                    if (amount.compareTo(investmentMinimum) == -1) {
                        return Result.error("请输入正确的投资金额");
                    }
                }
                if (investmentMaximum != null && investmentMaximum.compareTo(amount) < 0) {
                    return Result.error("最大投资金额（￥" + investmentMaximum + "）");
                }
                if (!pProject.verifyInvest(amount)) {
                    return Result.error("投资金额有误");
                }
                break;
            }
            default: {
                return Result.error("系统错误");
            }
        }

        PayModule payModule = PayPortal.invest.getModuleInstance();
        InvestmentRequest req = new InvestmentRequest(true, UserUtils.getCurrentUser().getId(), projectId,
                amount, false, 0, 0, PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY, deviceType);
        payModule.setRequest(req);
        PageRequest param = payModule.invoking().getPageRequest();
        return Result.success(param);
    }

    @RequestMapping(value = "transfer")
    @ResponseBody
    public Result transfer(Integer transferId, Integer parts, DeviceType deviceType) {
        // 验证转让
        User currentUser = UserUtils.getCurrentUser();
        if (currentUser == null) {
            return Result.error("请先登录");
        }
        Transfer transfer = transferService.get(transferId);
        if (transfer == null) {
            return Result.error("转让不存在");
        }
        if (transfer.getIsFull()) {
            return Result.error("已满额");
        }

        // 验证项目
        Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
        if (borrowing == null) {
            return Result.error("投资项目不存在");
        }

        currentUser = userService.get(currentUser.getId());

        if (transfer.getTransfer().intValue() == currentUser.getId().intValue()) {
            return Result.error("不能购买自己的债权");
        }

        if (borrowing.getBorrower().intValue() == currentUser.getId().intValue()) {
            return Result.error("受让人不能为借款人");
        }

        //逾期判断
        List<Repayment> repayments = repaymentService.findList(borrowing.getId());
        for (Repayment repayment : repayments) {
            if (repayment.getState().equals(RepaymentState.REPAID)) {
                continue;
            }
            if (repayment.getIsOverdue()) {
                return Result.error("借款逾期，购买失败");
            }
        }

        if (transfer.getState().equals(TransferLoanState.CANCEL)) {
            return Result.error("转让已撤销");
        }

        Integer projectId = transfer.getBorrowing();
        BigDecimal transferCapital = new BigDecimal(parts * 100);
        BigDecimal inTransferFee = AccountantUtils.calFee(transferCapital, borrowing.getInTransferFeeRate()); // 转入服务费
        transferCapital = transferCapital.add(inTransferFee);
        PayModule payModule = PayPortal.invest.getModuleInstance();
        InvestmentRequest req = new InvestmentRequest(true, UserUtils.getCurrentUser().getId(), projectId,
                transferCapital, true, transfer.getId(), parts, PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY, deviceType);
        payModule.setRequest(req);
        PageRequest param = payModule.invoking().getPageRequest();
        return Result.success(param);
    }

}
