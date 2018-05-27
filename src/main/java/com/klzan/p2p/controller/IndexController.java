package com.klzan.p2p.controller;

import com.klzan.core.Result;
import com.klzan.core.SpringObjectFactory;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.ParamsFilter;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.BorrowingType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.content.AdService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.links.LinksService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.ReferralFeeService;
import com.klzan.p2p.service.user.UserCommonService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.user.UserVo;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 首页
 * Created by suhao Date: 2017/3/1 Time: 16:43
 *
 * @version: 1.0
 */
@Controller
public class IndexController {
    protected static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Inject
    private RSAService rsaService;

    @Inject
    private LinksService linksService;

    @Inject
    private InvestmentService investmentService;

    @Inject
    private RepaymentPlanService repaymentPlanService;

    @Inject
    private BorrowingService borrowingService;

    @Inject
    private SpringObjectFactory springObjectFactory;

    @Inject
    private AdService adService;

    @Inject
    private SettingUtils setting;
    @Inject
    private RepaymentService repaymentService;
    @Inject
    private TransferService transferService;
    @Inject
    private UserService userService;
    @Resource
    private ReferralFeeService referralFeeService;
    @Inject
    private UserCommonService userCommonService;

    /**
     * 模板路径
     */
    private static final String TEMPLATE_PATH = "/mobile";

    @RequestMapping(value = {"", "/", "/index"})
    public String index(HttpServletRequest request, Model model) throws ParseException {

//        PayModule oaPayModule = (PayOpenAccountModule) PayType.open_account.getModuleInstance();
////        oaPayModule.setUserId(1);
//        OpenAccountResponse response = (OpenAccountResponse)oaPayModule.invoking().getResponse();
//        response.getTest();
////
//        PayModule oaqPayModule = PayType.open_account_query.getModuleInstance();
////        Result result = oaqPayModule.invoking();

        // 密钥
        RSAPublicKey publicKey = rsaService.generateKey(request);
        String siteOnlineDay = setting.getBasic().getSiteOnlineDay() + " 00:00:00";
        Date onlineDate = DateUtils.parse(siteOnlineDay, DateUtils.YYYY_MM_DD);
        Date today = DateUtils.getMinDateOfDay(new Date());
        model.addAttribute("onlineCount",DateUtils.getDaysOfTwoDate(today, onlineDate));
        model.addAttribute("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        model.addAttribute("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
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
        model.addAttribute("interest", interest.add(interest));
        //产融贷条数
        model.addAttribute("guarantee", borrowingService.findList(BorrowingType.GUARANTEE));
        //优企宝条数
        model.addAttribute("mortgage", borrowingService.findList(BorrowingType.MORTGAGE));
        //投标贷条数
        model.addAttribute("credit", borrowingService.findList(BorrowingType.CREDIT));
        //首页显示标
        PageCriteria pageCriteria = new PageCriteria(1, 4);
        List<Transfer>  zrProjectList= new ArrayList<>();
        List<Object>  zrProject= new ArrayList<>();
        zrProjectList=transferService.findPage(pageCriteria).getRows();
        for (Transfer transfer : zrProjectList) {
            Map<String,Object> map=new HashMap<>();
            String timeName=borrowingService.get(transfer.getBorrowing()).getPeriodUnit().getDisplayName();

            transfer.setSurplusPeriod(transfer.getSurplusPeriod().substring(0,transfer.getSurplusPeriod().indexOf("/"))) ;
            map.put("timeName",timeName);
            map.put("transfer",transfer);
            zrProject.add(map);
        }
        model.addAttribute("zrProject",zrProject);
        model.addAttribute("hotProject", borrowingService.findList(pageCriteria, null, null, null, null, null).getRows());

//        model.addAttribute("zrProject",transferService.findPage(pageCriteria));
        //新标上线、还款提醒、出借提醒
        SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = formater1.format(new Date()) + " 00:00:00";
        String end = formater1.format(new Date()) + " 23:59:59";
        PageCriteria pageCriteria3 = new PageCriteria(1, 4);
        pageCriteria3.getParams().add(new ParamsFilter("GED_lendingDate_filterindex_100", start));
        pageCriteria3.getParams().add(new ParamsFilter("LED_lendingDate_filterindex_101", end));
        //出借提醒
        model.addAttribute("lendProject", borrowingService.findList(pageCriteria3, BorrowingProgress.REPAYING, null, null, null, null).getRows());
        //新标上线
        PageCriteria pageCriteria4 = new PageCriteria(1, 4);
        pageCriteria4.getParams().add(new ParamsFilter("GTD_publishDate_filterindex_0", start));
        pageCriteria4.getParams().add(new ParamsFilter("LTD_publishDate_filterindex_1", end));
        model.addAttribute("newProject", borrowingService.findList(pageCriteria4, BorrowingProgress.INVESTING, null, null, null, null).getRows());
        //还款提醒
        List<Repayment> repayments = repaymentService.findList(formater2.parse(start), formater2.parse(end));
        List<Borrowing> borrowing = new ArrayList<>();
        for (Repayment repayment : repayments) {
            Borrowing borrowing1 = borrowingService.get(repayment.getBorrowing());
            borrowing.add(borrowing1);
        }
        model.addAttribute("repaymentProject", repayments);
        model.addAttribute("repaymentBorrowingProject", borrowing);

        model.addAttribute("banners", adService.findAdByIdent("PC_BANNER"));
        User user = UserUtils.getCurrentUser();
        BigDecimal yesterDayInAmount = BigDecimal.ZERO;
        String assets = "";
        Date lastPayDate = null;
        if (user!=null){
            BigDecimal income = repaymentPlanService.calyesterdayIncome(user.getId());
            yesterDayInAmount = income==null ?BigDecimal.ZERO:income;
            List<ReferralFee> referralFees= referralFeeService.alreadySettlement(user.getId());
            //昨日推荐奖励
            Date startDate=DateUtils.addDays(DateUtils.getMinDateOfDay(new Date()),-1);
            Date endDate=DateUtils.addDays(DateUtils.getMaxDateOfDay(new Date()),-1);
            for (ReferralFee referralFee : referralFees) {
                if(referralFee.getPaymentDate().before(endDate)&&referralFee.getPaymentDate().after(startDate)){
                    yesterDayInAmount.add(referralFee.getReferralFee());
                }
            }
            assets = userCommonService.getUserAssets(user.getId()).get("allCapitalSum").toString();
            List<RepaymentPlan> repaymentPlen = repaymentPlanService.findList(null, user.getId(), null);
            if (repaymentPlen!=null && repaymentPlen.size()>0){
                lastPayDate = repaymentPlen.get(0).getRepaymentRecord().getPayDate();
            }
        }
        model.addAttribute("assets", StringUtils.isBlank(assets)?"0":assets);
        model.addAttribute("yesterDayInAmount", yesterDayInAmount.toString());
        model.addAttribute("lastPayDate", lastPayDate==null?"--":DateUtils.format(lastPayDate,"yyyy-MM-dd"));
        return "index";
    }

    @RequestMapping("getAssets")
    @ResponseBody
    public Result getAssets(HttpServletRequest request, Model model) throws ParseException {
        User user = UserUtils.getCurrentUser();
        String assets = "--";
        String avatar = "";
        float level = 0;
        Map map = new HashMap();
        if (user!=null){
            assets = userCommonService.getUserAssets(user.getId()).get("available").toString();
            if (user.getAvatar()!=null){
                avatar = setting.getDfsUrl() + user.getAvatar();
            }
            UserVo userVo = userService.getUserById(user.getId());
            level = userVo.getSecurityLevel();
        }
        map.put("assets",assets);
        map.put("avatar",avatar);
        map.put("level",level);
        return Result.success("",map);
    }

    @GetMapping("appDown")
    public String appDown(HttpServletRequest request, Model model) throws ParseException {
        return TEMPLATE_PATH + "/app_down";
    }
}
