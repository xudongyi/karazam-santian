package com.klzan.p2p.controller.admin;

import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.BorrowingApplyProgress;
import com.klzan.p2p.enums.BorrowingState;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.model.RechargeRecord;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.sys.SysUser;
import com.klzan.p2p.service.borrowing.BorrowingApplyService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.recharge.RechargeService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.borrowing.BorrowingApplyVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 后台管理首页
 * Created by suhao Date: 2017/3/15 Time: 14:14
 *
 * @version: 1.0
 */
@Controller("adminIndexController")
@RequestMapping("admin")
public class IndexController extends BaseAdminController {

    @Resource
    private BorrowingApplyService borrowingApplyService;
    @Resource
    private InvestmentService investmentService;
    @Resource
    private RepaymentPlanService repaymentPlanService;
    @Resource
    private UserService userService;
    @Resource
    private RechargeService rechargeService;
    @Resource
    private BorrowingService borrowingService;
    @Resource
    private InvestmentRecordService investmentRecordService;

    @RequestMapping(value = {"", "/", "index"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("currentUser", UserUtils.getCurrentSysUser());
        return "admin/index";
    }

    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String welcome(HttpServletRequest request, Model model) {
        boolean connected = SecurityUtils.getSubject().isPermitted("borrowing:apply:connected");
        boolean reject = SecurityUtils.getSubject().isPermitted("borrowing:apply:reject");
        boolean success = SecurityUtils.getSubject().isPermitted("borrowing:apply:success");

        int size1 = 0;
        int size2 = 0;
        if (connected) {
            size1 = borrowingApplyService.findListByProgress(BorrowingApplyProgress.APPROVAL).size();
        }
        if (reject || success) {
            size2 = borrowingApplyService.findListByProgress(BorrowingApplyProgress.CONTACTED).size();
        }
        if (size1 + size2 > 0) {
            model.addAttribute("size", size1 + size2);
        }

        //累计撮合金额
        BigDecimal dealAmount = investmentService.countInvest();
        dealAmount = dealAmount == null ? BigDecimal.ZERO : dealAmount;
        model.addAttribute("dealAmount", dealAmount);
        //为投资者带来收益
        BigDecimal earnInterest = repaymentPlanService.countProfit();
        earnInterest = earnInterest == null ? BigDecimal.ZERO : earnInterest;
        model.addAttribute("earnInterest", earnInterest);
        //即将为投资者带来收益
        BigDecimal interest = repaymentPlanService.countWillProfit();
        interest = interest == null ? BigDecimal.ZERO : interest;
        model.addAttribute("interest", interest);
        //用户总量
        List<User> all = userService.findAll();
        model.addAttribute("userNum", all != null ? all.size() : 0);
        //今日注册
        Integer todayUserNum = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String nowStr = sdf.format(new Date());
        for (User user : all) {
            String createStr = sdf.format(user.getCreateDate());
            if (nowStr.equals(createStr)) {
                todayUserNum++;
            }
        }
        model.addAttribute("todayUserNum", todayUserNum);
        //今日充值
        List<RechargeRecord> records = rechargeService.findAll();
        BigDecimal todayRechargeNum = BigDecimal.ZERO;
        for (RechargeRecord rechargeRecord : records) {
            String rechageCreateStr = sdf.format(rechargeRecord.getCreateDate());
            if (nowStr.equals(rechageCreateStr) && rechargeRecord.getStatus() == RecordStatus.SUCCESS) {
                todayRechargeNum = todayRechargeNum.add(rechargeRecord.getAmount());
            }
        }
        model.addAttribute("todayRechargeNum", todayRechargeNum);
        //今日标的
        List<Borrowing> borrowings = borrowingService.findAll();
        Integer todayBorrowingsNum = 0;
        for (Borrowing borrowing : borrowings) {
            if (nowStr.equals(sdf.format(borrowing.getCreateDate()))) {
                if (borrowing.getState() != BorrowingState.FAILURE && borrowing.getState() != BorrowingState.EXPIRY) {
                    ++todayBorrowingsNum;
                }
            }
        }
        model.addAttribute("todayBorrowingsNum", todayBorrowingsNum);
        //今日投资
        List<InvestmentRecord> investmentRecords = investmentRecordService.findAll();
        BigDecimal todayInvestmentNum = BigDecimal.ZERO;
        for (InvestmentRecord investmentRecord : investmentRecords) {
            if (nowStr.equals(sdf.format(investmentRecord.getCreateDate())) && investmentRecord.getState() == InvestmentState.SUCCESS) {
                todayInvestmentNum = todayInvestmentNum.add(investmentRecord.getAmount());
            }
        }
        model.addAttribute("todayInvestmentNum", todayInvestmentNum);
        return "admin/welcome";
    }
}
