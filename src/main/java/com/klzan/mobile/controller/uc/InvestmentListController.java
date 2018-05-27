package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.SecrecyUtils;
import com.klzan.core.util.SnUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.material.MaterialService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.borrowing.BorrowingSimpleVo;
import com.klzan.p2p.vo.business.Request;
import com.klzan.p2p.vo.investment.InvestVo;
import com.klzan.p2p.vo.investment.InvestmentRecordSimpleVo;
import com.klzan.plugin.repayalgorithm.DateLength;
import com.klzan.plugin.repayalgorithm.RepayRecords;
import com.klzan.plugin.repayalgorithm.RepayRecordsStrategyHolder;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import static com.klzan.plugin.repayalgorithm.DateUnit.DAY;
import static com.klzan.plugin.repayalgorithm.DateUnit.MONTH;


/**
 * 投资列表
 */
@Controller("ucInvestmentListController")
@RequestMapping("/mobile/uc/investment")
public class InvestmentListController extends BaseController {

    @Inject
    private BorrowingService borrowingService;
    @Inject
    private MaterialService materialService;
    @Inject
    private InvestmentRecordService investmentRecordService;
    @Resource
    private BusinessService businessService;
    @Resource
    private UserFinanceService userFinanceService;
    @Resource
    private UserService userService;


    @RequestMapping(value = "/{projectId}/invest", method = RequestMethod.GET)
    @ResponseBody
    public synchronized Result invest(@CurrentUser User currentUser,@PathVariable Integer projectId,
                                      InvestVo investVo, HttpServletRequest request) {
        if (currentUser == null) {
            return Result.error("请先登陆");
        }
        // 会员资金
        UserFinance investorFinance = userFinanceService.findByUserId(currentUser.getId());
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
        if (currentUser.equals(pProject.getBorrower())) {
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
        investVo.setDeviceType(DeviceType.ANDROID);
        switch (pProject.getInvestmentMethod()) {
            case MULTIPLE_OF_MINIMUM: { /** 最低投资倍数 */
                if (investVo.getAmount() == null) {
                    return Result.error("请输入投资金额");
                }
                if ((investVo.getAmount().subtract(investmentMinimum)).intValue() % investmentMinimum.intValue() != 0) {
                    return Result.error("加价幅度需为" + investmentMinimum.intValue() + "的倍数");
                }
                // 验证可投余额
                if (surplusInvestmentAmount.compareTo(investmentMinimum) == -1) {
                    if (!(investVo.getAmount().compareTo(surplusInvestmentAmount) == 0)) {
                        return Result.error("请输入正确的投资金额");
                    }
                } else {
                    if (investVo.getAmount().compareTo(investmentMinimum) == -1) {
                        return Result.error("请输入正确的投资金额");
                    }
                }
                if (investmentMaximum != null && investmentMaximum.compareTo(investVo.getAmount()) < 0) {
                    return Result.error("最大投资金额（￥" + investmentMaximum + "）");
                }
                if (!pProject.verifyInvest(investVo.getAmount())) {
                    return Result.error("投资金额有误");
                }
                break;
            }
            default: {
                return Result.error("系统错误");
            }
        }

        try {
            // 投资人余额校验
            if (investVo.getAmount().compareTo(investorFinance.getBalance().subtract(investorFinance.getFrozen())) > 0) {
                return Result.error("账户余额不足");
            }
            String sn = SnUtils.getOrderNo();
            // 完善业务参数
            investVo.setInvestor(currentUser.getId());
            investVo.setOperationMethod(OperationMethod.MANUAL);
            investVo.setPaymentMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
            investVo.setSn(sn);
            //1.接口
            Request backRequest = businessService.investmentFrozen(request, investVo, currentUser, pProject, PaymentOrderType.INVESTMENT);
//            model.addAttribute("parameterMap", backRequest.getParameterMap());
            return Result.success("成功",backRequest.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success("投资失败");
        }
    }

//    /**
//     * 协议
//     */
//    @RequestMapping("/agreement/{projectId}")
//    public String agreement(@PathVariable Integer projectId) {
//        // 验证项目
//        Borrowing pProject = borrowingService.get(projectId);
//        if (pProject == null) {
//            return "/agreement/invest/credit_agreement";
//        }
//        return "/agreement/invest/" + pProject.getType().name().toLowerCase() + "_agreement";
//    }
}