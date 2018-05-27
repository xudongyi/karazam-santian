package com.klzan.mobile.controller;/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.util.StringUtils;
import com.klzan.mobile.vo.IndexPageVo;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.enums.LinkType;
import com.klzan.p2p.enums.MaterialType;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.Material;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserFinance;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.content.AdService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.material.MaterialService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.borrowing.BorrowingSimpleVo;
import com.klzan.p2p.vo.content.AdVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 首页
 * Created by suhao Date: 2016/10/20 Time: 13:28
 *
 * @version: 1.0
 */
@RestController("mobileIndexController")
@RequestMapping("/mobile/index")
public class IndexController extends BaseController {

    @Inject
    private RepaymentPlanService repaymentPlanService;
    @Inject
    private BorrowingService borrowingService;
    @Inject
    private AdService adService;
    @Inject
    private SettingUtils setting;
    @Inject
    private UserFinanceService userFinanceService;
    @Inject
    private UserService userService;
    @Inject
    private InvestmentRecordService investmentRecordService;
    @Inject
    private MaterialService materialService;

    @RequestMapping(value ="/data",method = RequestMethod.GET)
    @ResponseBody
    public Result index(HttpServletRequest request) {

        //首页显示标
        PageCriteria pageCriteria = new PageCriteria(1, 4);
       List<Borrowing> Projects= borrowingService.findList(pageCriteria, null, null, null, null, null).getRows();
       // borrowingService.findHotProjects()
       List<BorrowingSimpleVo> hotProjects=new ArrayList<>();
        for (Borrowing Project : Projects) {
            BorrowingSimpleVo  borrowingSimpleVo= new BorrowingSimpleVo();
            setObj(Project,borrowingSimpleVo);

           //-------------
            User user =userService.get(Project.getBorrower());
            if (Project == null) {
                return Result.error("投资项目不存在");
            }
            Map<String, Object> project = new HashedMap();
            project.put("investmentStartDate",Project.getInvestmentStartDate());
            project.put("labels",Project.getLabels());
            project.put("userType",user.getType());
            project.put("allowInvest",Project.verifyInvest());
            project.put("type", Project.getType());
            project.put("title", Project.getTitle());
            project.put("repaymentMethodDes", Project.getRepaymentMethodDes());
            project.put("interestRate", new DecimalFormat("#.##").format(Project.getRealInterestRate()));
            project.put("amount", Project.getAmount());
            project.put("residualAmount", Project.getSurplusInvestmentAmount());
            project.put("period", Project.getPeriod());
            project.put("periodUnitDes", Project.getPeriodUnitDes());
            project.put("progressDesReal", Project.getProgressMobileDes());
            project.put("investmentMinimum", new DecimalFormat("#.##").format(Project.getInvestmentMinimum()));
            project.put("description", StringUtils.isNotBlank(Project.getDescription())? Project.getDescription() : "");
            project.put("purpose", StringUtils.isNotBlank(Project.getPurpose())? Project.getPurpose() : "");
            project.put("repaymentInquiry", StringUtils.isNotBlank(Project.getRepaymentInquiry())? Project.getRepaymentInquiry() : "");
            project.put("percent",Project.getSurplusInvestmentAmount().divide(Project.getAmount(),2).
                    setScale(4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
            //投资数量investmentRecordService.findList(vo.getBorrowingId(), false, InvestmentState.PAID, InvestmentState.SUCCESS)

            List resultRo = investmentRecordService.findList(Project.getId(), false, InvestmentState.PAID, InvestmentState.SUCCESS);
            project.put("investedNumber",resultRo.size()>0?resultRo.size():0);

            //车辆实拍张数
            List<String> materialImgUrls = new ArrayList<>();
            List<Material> materials = materialService.findList(Project.getId());
            for (Material material : materials) {
                    materialImgUrls.add(setting.getDfsUrl() + material.getSource());
            }
            project.put("pictureNumber", materialImgUrls.size());
            borrowingSimpleVo.setDetail(project);
            hotProjects.add(borrowingSimpleVo);
            //----------

        }
        //banner
        List<Map<String, String>> bannerUrls = new ArrayList<>();
        List<AdVo> banners = adService.findAdByIdent("APP_BANNER");
        for (AdVo banner : banners) {
            Map<String, String> map = new HashedMap();
            map.put("burl", setting.getDfsUrl() + banner.getPath());
            map.put("blink", banner.getUrl());
            bannerUrls.add(map);
        }
        User user=UserUtils.getCurrentUser();
        UserFinance userFinance=null;
        if(user!=null){
            userFinance= userFinanceService.getByUserId(user.getId());
        }
        IndexPageVo indexPage = new IndexPageVo(hotProjects,bannerUrls,user,userFinance);
        return Result.success("首页数据", indexPage);
    }
    private void setObj(Borrowing borrowing, BorrowingSimpleVo borrowingSimpleVo) {
        borrowingSimpleVo.setInvestmentStartDate(borrowing.getInvestmentStartDate());
        borrowingSimpleVo.setBorrowingId(borrowing.getId());
        borrowingSimpleVo.setTitle(borrowing.getTitle());
        borrowingSimpleVo.setInterestRate(borrowing.getInterestRate().add(borrowing.getRewardInterestRate()));
        if(borrowing.getRewardInterestRate().compareTo(BigDecimal.ZERO)==0||borrowing.getRewardInterestRate()==null){
            borrowingSimpleVo.setInterestRateStr(borrowing.getInterestRate().toString());
        }else{
            borrowingSimpleVo.setInterestRateStr(borrowing.getInterestRate().toString()+'+'+borrowing.getRewardInterestRate());
        }

        borrowingSimpleVo.setResidualAmount(borrowing.getAmount().subtract(borrowing.getInvestedAmount()));
        borrowingSimpleVo.setResidualAmountStr(borrowing.getAmount().subtract(borrowing.getInvestedAmount()).divide(new BigDecimal(10000),2).toString());
        borrowingSimpleVo.setPeriod(borrowing.getPeriod());
        borrowingSimpleVo.setPeriodUnit(borrowing.getPeriodUnit().name());
        borrowingSimpleVo.setProgressDesReal(borrowing.getProgressDes());
        borrowingSimpleVo.setBorrowingType(borrowing.getType());
        borrowingSimpleVo.setRepaymentMethodStr(borrowing.getRepaymentMethodDes());
        borrowingSimpleVo.setRepaymentMethod(borrowing.getRepaymentMethod());
        borrowingSimpleVo.setAmount(borrowing.getAmount());
        borrowingSimpleVo.setPercent(borrowing.getAmount().subtract(borrowing.getInvestedAmount()).divide(borrowing.getAmount(),2));
        borrowingSimpleVo.setAllowInvest(borrowing.verifyInvest());
        borrowingSimpleVo.setProgress(borrowing.getProgress().name());
        borrowingSimpleVo.setProgressDes(borrowing.getProgressDes());
        borrowingSimpleVo.setHeraldTime(borrowing.getInvestmentStartDate().getTime()-new Date().getTime());
    }
}

