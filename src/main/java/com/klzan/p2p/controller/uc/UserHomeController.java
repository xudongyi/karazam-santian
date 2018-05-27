package com.klzan.p2p.controller.uc;

import com.jcraft.jsch.SftpException;
import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.*;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.point.PointRecordService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.user.*;
import com.klzan.p2p.vo.investment.InvestmentVo;
import com.klzan.p2p.vo.investment.WaitingProfitInvestmentVo;
import com.klzan.p2p.vo.user.UserMonthDetailProfitVo;
import com.klzan.p2p.vo.user.UserMonthProfitVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 用户中心
 * Created by suhao Date: 2017/3/30 Time: 14:33
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("uc")
public class UserHomeController extends BaseController {

    @Inject
    private UserService userService;

    @Inject
    private UserFinanceService userFinanceService;

    @Inject
    private InvestmentService investmentService;

    @Inject
    private BorrowingService borrowingService;

    @Inject
    private RepaymentPlanService repaymentPlanService;

    @Inject
    private ReferralFeeService referralFeeService;

    @Inject
    private UserCommonService userCommonService;

    @Inject
    private UserPointService userPointService;

    @Inject
    private PointRecordService pointRecordService;

    @RequestMapping(value = {"", "/", "index"})
    public String index(Model model, @CurrentUser User user) {
        Integer userId = user.getId();
        List<WaitingProfitInvestmentVo> waitingProfitInvestmentVos = investmentService.findWaitingProfitInvestByUserId(userId, 5);
        BigDecimal alreadyProfits = BigDecimal.ZERO;
        List<RepaymentPlan> alreadyEepaymentPlans = repaymentPlanService.alreadyProfit(userId);
        for (RepaymentPlan repaymentPlan : alreadyEepaymentPlans) {
            alreadyProfits = alreadyProfits.add(repaymentPlan.getRepaymentRecord().getInterest()).add(repaymentPlan.getOverdueInterest()).add(repaymentPlan.getSeriousOverdueInterest());
        }
        BigDecimal alreadyReferralFees = BigDecimal.ZERO;
        List<ReferralFee> referralFees = referralFeeService.alreadySettlement(userId);
        for (ReferralFee referralFee : referralFees) {
            if(referralFee.getState().equals(ReferralFeeState.PAID) || referralFee.getState().equals(ReferralFeeState.OFFLINE_PAID)){
                alreadyReferralFees = alreadyReferralFees.add(referralFee.getReferralFee());
            }
        }
        model.addAttribute("assets", userCommonService.getUserAssets(user.getId()));
        model.addAttribute("waitingProfitInvestments", waitingProfitInvestmentVos);
        model.addAttribute("alreadyProfits", alreadyProfits.add(alreadyReferralFees));
        PageCriteria criteria = new PageCriteria(1, 3);
        model.addAttribute("hotProjects", borrowingService.findList(criteria, BorrowingProgress.INVESTING, null, null, null, Boolean.TRUE).getRows());
        return "uc/index";
    }

    @RequestMapping("investments")
    @ResponseBody
    public PageResult<InvestmentVo> investments(@CurrentUser User currentUser, PageCriteria criteria) {
        return investmentService.findByUserId(currentUser.getId(), criteria);
    }

    @RequestMapping("pandect")
    public String pandect(@CurrentUser User currentUser, Model model) {
        Integer userId = currentUser.getId();
        UserFinance userFinance = userFinanceService.findByUserId(userId);
        model.addAttribute("userFinance", userFinance);
//        UserPoint userPoint = userPointService.findByUserId(userId);
//        model.addAttribute("userPoint", userPoint);
        Integer investPoint = 0;
        List<PointRecord> pointRecords = pointRecordService.findList(userId, PointMethod.invest);
        for(PointRecord pointRecord : pointRecords){
            investPoint = investPoint + pointRecord.getPoint();
        }
        model.addAttribute("investPoint", investPoint);
        return "uc/pandect";
    }

    @RequestMapping(value = "profit/json", method = RequestMethod.POST)
    @ResponseBody
    public Result profit(@CurrentUser User currentUser, Integer year, Integer month) {
        UserMonthProfitVo monthProfit = new UserMonthProfitVo();
        try {
            StringBuffer yearMonth = new StringBuffer(year+"").append("-").append(month < 10 ? "0" + month : month + "");
            List<RepaymentPlan> recoveries = repaymentPlanService.findRecoveries(currentUser.getId(), yearMonth.toString());
            Map<String, UserMonthDetailProfitVo> detailProfitMap = new HashMap<>();
            for (RepaymentPlan recovery : recoveries) {
                String day = null;
                if(recovery.getState().equals(RepaymentState.REPAID)){
                    day = DateUtils.format(recovery.getPaidDate(), "d");
                }else {
                    day = DateUtils.format(recovery.getRepaymentRecord().getPayDate(), "d");
                }
                int dayInt = Integer.parseInt(day);

                BigDecimal recoverAmount = BigDecimal.ZERO;
                BigDecimal recoveredAmount = BigDecimal.ZERO;
                BigDecimal todayRecoveredAmount = BigDecimal.ZERO;
                BigDecimal todayAdvanceAmount = BigDecimal.ZERO;
                BigDecimal expireRecoverAmount = BigDecimal.ZERO;
                BigDecimal historyRecoveredAmount = BigDecimal.ZERO;

                recoverAmount = recovery.getRecoveryAmount();
                expireRecoverAmount = recovery.getRecoveryAmount();
                if (recovery.getState() == RepaymentState.REPAID) {
                    recoveredAmount = recovery.getRecoveryAmount();
                    todayRecoveredAmount = recovery.getRecoveryAmount();
                }

                UserMonthDetailProfitVo detailProfitVo;
                if (detailProfitMap.containsKey(day)) {
                    detailProfitVo = detailProfitMap.get(day);
                    detailProfitVo.updateDetail(recoverAmount,
                            recoveredAmount,
                            todayRecoveredAmount,
                            todayAdvanceAmount,
                            expireRecoverAmount,
                            historyRecoveredAmount);
                } else {
                    detailProfitVo = new UserMonthDetailProfitVo(dayInt,
                            recoverAmount,
                            recoveredAmount,
                            todayRecoveredAmount,
                            todayAdvanceAmount,
                            expireRecoverAmount,
                            historyRecoveredAmount
                    );
                }
                detailProfitMap.put(day, detailProfitVo);
                monthProfit.addRecoverAmount(recovery.getRecoveryAmount());
                if (recovery.getState() == RepaymentState.REPAYING) {
                    monthProfit.addUnRecoverAmount(recovery.getRecoveryAmount());
                }

            }
            for (Map.Entry<String, UserMonthDetailProfitVo> entry : detailProfitMap.entrySet()) {
                monthProfit.addDetailProfits(entry.getValue());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return Result.success("成功", monthProfit);
    }

    @RequestMapping(value = "pandect/invest/json", method = RequestMethod.POST)
    @ResponseBody
    public Result pandectJson(@CurrentUser User currentUser, Model model) {
        Integer userId = currentUser.getId();
        List<Investment> investments = investmentService.findListByUserId(userId);
        List<String> investProduct = new ArrayList<>();
        Map<BorrowingType, Integer> investProductRatio = new LinkedMap();
        List<InvestRatio> investRatios = new ArrayList<>();
        for (BorrowingType borrowingType : BorrowingType.values()) {
            investProduct.add(borrowingType.getAlias());
            investProductRatio.put(borrowingType, 0);
        }
        for (Investment investment : investments) {
            Borrowing borrowing = borrowingService.get(investment.getBorrowing());
            BorrowingType borrowingType = borrowing.getType();
            investProductRatio.put(borrowingType, investProductRatio.get(borrowingType) + 1);
        }
        investProduct = new ArrayList<>();
        for (Map.Entry<BorrowingType, Integer> entry : investProductRatio.entrySet()) {
            Integer value = entry.getValue();
            if (value != 0) {
                String alias = entry.getKey().getAlias();
                investProduct.add(alias);
                InvestRatio ratio = new InvestRatio(alias, value);
                investRatios.add(ratio);
            }
        }
        model.addAttribute("investProduct", investProduct);
        model.addAttribute("investProductRatio", investProductRatio);
        Map<String, Object> investData = new HashedMap();
        investData.put("products", investProduct);
        investData.put("investRatios", investRatios);
        return Result.success("操作成功", investData);
    }

    @RequestMapping(value = "pandect/waitingProfit/json", method = RequestMethod.POST)
    @ResponseBody
    public Result pandectWaitingProfitJson(@CurrentUser User currentUser, Model model) {
        List<RepaymentPlan> repaymentPlans = repaymentPlanService.waitingProfit(currentUser.getId(), 14);
        List<String> waitingProfitDate = new ArrayList<>();
        Map<String, BigDecimal> waitingProfit = new LinkedMap();
        Date nowDate = new Date();
        for (int i = 0; i < 14; i++) {
            String key = DateUtils.format(DateUtils.addDays(nowDate, i), DateUtils.YYYY_MM_DD);
            waitingProfit.put(key, BigDecimal.ZERO);
            waitingProfitDate.add(key);
        }
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            Date payDate = repaymentPlan.getRepaymentRecord().getPayDate();
            String payDateStr = DateUtils.format(payDate, DateUtils.YYYY_MM_DD);
            if (waitingProfit.containsKey(payDateStr)) {
                waitingProfit.put(payDateStr, waitingProfit.get(payDateStr).add(repaymentPlan.getRepaymentRecord().getInterest()).add(repaymentPlan.getOverdueInterest()).add(repaymentPlan.getSeriousOverdueInterest()));
            }
        }
        List<InvestRatio> profits = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : waitingProfit.entrySet()) {
            String key = entry.getKey();
            BigDecimal value = entry.getValue();
            InvestRatio profit = new InvestRatio(key, value);
            profits.add(profit);
        }
        Map<String, Object> waitingProfitData = new HashedMap();
        waitingProfitData.put("days", waitingProfitDate);
        waitingProfitData.put("profits", profits);
        return Result.success("操作成功", waitingProfitData);
    }

    @RequestMapping(value = "assets", method = RequestMethod.POST)
    @ResponseBody
    public Result assets(@CurrentUser User user) {
        if (null == user) {
            return Result.error("获取资产信息失败");
        }
        return Result.success("获取资产信息成功", userCommonService.getUserAssets(user.getId()));
    }

    @RequestMapping(value = "avatar/upload", method = RequestMethod.POST)
    public String avatarUpload(HttpServletRequest request, @CurrentUser User currentUser,
                               @RequestParam(value = "x") String x, @RequestParam(value = "y") String y,
                               @RequestParam(value = "h") String h, @RequestParam(value = "w") String w,
                               @RequestParam(value = "imgFile") MultipartFile imageFile) throws Exception{
        List<String> ALLOW_TYPES = Arrays.asList(
                "image/jpg","image/jpeg","image/png","image/gif"
        );
        User user = userService.get(currentUser.getId());
        if (imageFile != null) {
            if (ALLOW_TYPES.contains(imageFile.getContentType())) {
                String originalFilename = imageFile.getOriginalFilename();
                String extension = FilenameUtils.getExtension(originalFilename);
                boolean isPng = StringUtils.equalsIgnoreCase("png", extension) ? true : false;
                long time = DateUtils.getTime();
                String fileName = user.getId() + "_" + time + "_src." + extension;
                SftpUtils sftp = new SftpUtils();
                try {
                    String avatarDir = "user";
                    sftp.connect().upload(avatarDir, fileName, imageFile.getInputStream()).disconnect();
                } catch (SftpException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    sftp.disconnect();
                }

                int imageX = (int)Double.parseDouble(x);
                int imageY = (int)Double.parseDouble(y);
                int imageH = (int)Double.parseDouble(h);
                int imageW = (int)Double.parseDouble(w);
                //这里开始截取操作
                OutputStream fs = new FileOutputStream(IOUtils.getTempFolderPath() + "/" + fileName);
                ImageUtils.crop(imageFile.getInputStream(), fs, imageX, imageY, imageW, imageH, isPng);

                String newFileName = user.getId() + "_" + time + "." + extension;
                sftp = new SftpUtils();
                try {
                    String avatarDir = "user";
                    File file = FileUtils.multipartToFile(imageFile);

                    ImageUtils.imgCut(file, imageX, imageY, imageW, imageH);
                    sftp.connect().upload(avatarDir, newFileName, new FileInputStream(file)).disconnect();
                    user.setAvatar(avatarDir + "/" + newFileName);
                    userService.merge(user);
                } catch (SftpException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    sftp.disconnect();
                }
            }
        }
        return "uc/index";
    }

    class InvestRatio {
        private String name;
        private Object value;

        public InvestRatio(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
