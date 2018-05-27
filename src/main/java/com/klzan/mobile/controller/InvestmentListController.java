package com.klzan.mobile.controller;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.mybatis.pagehelper.Page;
import com.klzan.core.util.*;
import com.klzan.mobile.vo.BorrowingInfoExtrasVo;
import com.klzan.mobile.vo.ProjectDetailVo;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.BorrowingExtraService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.material.MaterialService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.borrowing.BorrowingExtraDetailVo;
import com.klzan.p2p.vo.borrowing.BorrowingExtraVo;
import com.klzan.p2p.vo.borrowing.BorrowingSimpleVo;
import com.klzan.p2p.vo.investment.InvestmentRecordSimpleVo;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.repayalgorithm.DateLength;
import com.klzan.plugin.repayalgorithm.RepayRecords;
import com.klzan.plugin.repayalgorithm.RepayRecordsStrategyHolder;
import freemarker.template.TemplateException;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import static com.klzan.plugin.repayalgorithm.DateUnit.DAY;
import static com.klzan.plugin.repayalgorithm.DateUnit.MONTH;


/**
 * 投资列表
 */
@Controller("investmentListController")
@RequestMapping("/mobile/investment")
public class InvestmentListController extends BaseController {

    @Autowired
    private SettingUtils setting;
    @Inject
    private BorrowingService borrowingService;
    @Inject
    private MaterialService materialService;
    @Inject
    private InvestmentRecordService investmentRecordService;
    @Inject
    private RepaymentService repaymentService;
    @Resource
    private BusinessService businessService;
    @Resource
    private UserFinanceService userFinanceService;
    @Resource
    private UserService userService;
    @Inject
    private BorrowingExtraService borrowingExtraService;
    @Inject
    private AgreementService agreementService;
    @Inject
    private DataConvertService dataConvertService;
    /**
     * 投资列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result borrowingList(Integer currentPage) {
        if (null == currentPage) {
            currentPage = 1;
        }
        PageCriteria criteria = new PageCriteria(currentPage, 5);
        PageResult<BorrowingSimpleVo> result = borrowingService.findPage(criteria);
        for(BorrowingSimpleVo vo : result.getRows()){
            Borrowing borrowing = borrowingService.get(vo.getBorrowingId());
            vo.setAllowInvest(borrowing.verifyInvest());
            vo.setProgressDesReal(borrowing.getProgressDes());
            vo.setInvestmentStartDate(borrowing.getInvestmentStartDate());
            vo.setHeraldTime(borrowing.getInvestmentStartDate().getTime()-new Date().getTime());
            //-------------------------

            User user =userService.get(borrowing.getBorrower());
            if (borrowing == null) {
                return Result.error("投资项目不存在");
            }
            Map<String, Object> project = new HashedMap();
            project.put("investmentStartDate",borrowing.getInvestmentStartDate());
            project.put("labels",borrowing.getLabels());
            project.put("userType",user.getType());
            project.put("allowInvest",borrowing.verifyInvest());
            project.put("type", borrowing.getType());
            project.put("title", borrowing.getTitle());
            project.put("repaymentMethodDes", borrowing.getRepaymentMethodDes());
            project.put("interestRate", new DecimalFormat("#.##").format(borrowing.getRealInterestRate()));
            project.put("amount", borrowing.getAmount());
            project.put("residualAmount", borrowing.getSurplusInvestmentAmount());
            project.put("period", borrowing.getPeriod());
            project.put("periodUnitDes", borrowing.getPeriodUnitDes());
            project.put("progressDesReal", borrowing.getProgressMobileDes());
            project.put("investmentMinimum", new DecimalFormat("#.##").format(borrowing.getInvestmentMinimum()));
            project.put("description", StringUtils.isNotBlank(borrowing.getDescription())? borrowing.getDescription() : "");
            project.put("purpose", StringUtils.isNotBlank(borrowing.getPurpose())? borrowing.getPurpose() : "");
            project.put("repaymentInquiry", StringUtils.isNotBlank(borrowing.getRepaymentInquiry())? borrowing.getRepaymentInquiry() : "");
            project.put("percent",borrowing.getSurplusInvestmentAmount().divide(borrowing.getAmount(),2).
                    setScale(4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
            //投资数量
            List resultRo=  investmentRecordService.findList(vo.getBorrowingId(), false, InvestmentState.PAID, InvestmentState.SUCCESS);
          //  List resultRo = investmentRecordService.findListSuccess(vo.getBorrowingId());
            //investmentRecordService.findList(projectId, false, InvestmentState.PAID, InvestmentState.SUCCESS)
            project.put("investedNumber",resultRo.size()>0?resultRo.size():0);

            //车辆实拍张数
            List<String> materialImgUrls = new ArrayList<>();
            List<Material> materials = materialService.findList(vo.getBorrowingId());
            for (Material material : materials) {
//                if (material.getType()== MaterialType.CAR_PICTURE && material.getLinkType()== LinkType.BORROWING){
                    materialImgUrls.add(setting.getDfsUrl() + material.getSource());
//                }
            }
            project.put("pictureNumber", materialImgUrls.size());
            vo.setDetail(project);
        //-------------------
        }
        Map<String, Object> map = new HashedMap();
        map.put("pages", result.getPages());
        map.put("rows", result.getRows());

        return Result.success("借款列表", map);
    }


    @RequestMapping(value = "/details", method = RequestMethod.GET)
    @ResponseBody
    public Result details(Integer projectId) {
        // 验证项目
        Borrowing pProject = borrowingService.get(projectId);
        User user =userService.get(pProject.getBorrower());
        if (pProject == null) {
            return Result.error("投资项目不存在");
        }
        Map<String, Object> project = new HashedMap();
        project.put("investmentStartDate",pProject.getInvestmentStartDate());
        project.put("labels",pProject.getLabels());
        project.put("userType",user.getType());
        project.put("allowInvest",pProject.verifyInvest());
        project.put("type", pProject.getType());
        project.put("title", pProject.getTitle());
        project.put("repaymentMethodDes", pProject.getRepaymentMethodDes());
        project.put("interestRate", new DecimalFormat("#.##").format(pProject.getRealInterestRate()));
        project.put("amount", pProject.getAmount());
        project.put("residualAmount", pProject.getSurplusInvestmentAmount());
        project.put("period", pProject.getPeriod());
        project.put("periodUnitDes", pProject.getPeriodUnitDes());
        project.put("progressDesReal", pProject.getProgressMobileDes());
        project.put("investmentMinimum", new DecimalFormat("#.##").format(pProject.getInvestmentMinimum()));
        project.put("description", StringUtils.isNotBlank(pProject.getDescription())? pProject.getDescription() : "");
        project.put("purpose", StringUtils.isNotBlank(pProject.getPurpose())? pProject.getPurpose() : "");
        project.put("repaymentInquiry", StringUtils.isNotBlank(pProject.getRepaymentInquiry())? pProject.getRepaymentInquiry() : "");
        project.put("percent",pProject.getSurplusInvestmentAmount().divide(pProject.getAmount(),2).
                setScale(4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        //投资数量

       // List result = investmentRecordService.findListSuccess(projectId);
        List result=  investmentRecordService.findList(projectId, false, InvestmentState.PAID, InvestmentState.SUCCESS);
        project.put("investedNumber",result.size()>0?result.size():0);

        //车辆实拍张数
        List<String> materialImgUrls = new ArrayList<>();
        List<Material> materials = materialService.findList(projectId);
        if (materials!=null&&materials.size()!=0) {
            for (Material material : materials) {
               // if (material.getType() == MaterialType.CAR_PICTURE && material.getLinkType() == LinkType.BORROWING) {
                    materialImgUrls.add(setting.getDfsUrl() + material.getSource());
                //}
            }
            project.put("pictureNumber", materialImgUrls.size());
        }else{
            project.put("pictureNumber",0);
        }
        return Result.success(String.format("项目[%d]详情", projectId), project);
    }
    @RequestMapping(value = "/heraldTime")
    @ResponseBody
    public  Result heraldTime(Integer projectId){
        Borrowing pProject = borrowingService.get(projectId);
        long heraldTime= pProject.getInvestmentStartDate().getTime()-new Date().getTime();
        Map map=new HashMap();
        map.put("heraldTime",heraldTime);
        return Result.success("预告时间",map);
    }

    @RequestMapping(value = "/car_picture", method = RequestMethod.GET)
    @ResponseBody
    public Result carPicture(Integer projectId) {
        // 验证项目
        Borrowing pProject = borrowingService.get(projectId);
        if (pProject == null) {
            return Result.error("投资项目不存在");
        }
        Map<String, Object> map = new HashedMap();

        List<String> materialImgUrls = new ArrayList<>();
        List<Material> materials = materialService.findList(projectId);
        for (Material material : materials) {
//            if (material.getType()== MaterialType.CAR_PICTURE && material.getLinkType()== LinkType.BORROWING){
//                materialImgUrls.add(setting.getDfsUrl() + material.getSource());
//            }
            materialImgUrls.add(setting.getDfsUrl() + material.getSource());
        }
        map.put("materialImgUrls", materialImgUrls);
        return Result.success(String.format("项目[%d]详情", projectId), map);
    }

    @RequestMapping(value = "/investmentRecords", method = RequestMethod.GET)
    @ResponseBody
    public Result investmentRecords(Integer projectId, Integer currentPage,Boolean isTransfer) {
        // 验证项目
        if(!isTransfer){
            Borrowing pProject = borrowingService.get(projectId);
            if (pProject == null) {
                return Result.error("投资项目不存在");
            }
            if (null == currentPage) {
                currentPage = 1;
            }
            PageCriteria criteria = new PageCriteria(currentPage, 20);
            PageResult<InvestmentRecordSimpleVo> result = investmentRecordService.findPage(projectId,criteria,isTransfer,InvestmentState.SUCCESS,InvestmentState.PAID);
            for (InvestmentRecordSimpleVo investmentRecordSimpleVo : result.getRows()) {
                investmentRecordSimpleVo.setInvestor(SecrecyUtils.toUsername(investmentRecordSimpleVo.getInvestor(), "*"));
            }
            Map<String, Object> map = new HashedMap();
            map.put("records", result.getRows());
            map.put("pages", result.getPages());
            return Result.success(String.format("项目[%d]投资记录详情", projectId), map);
        }else{
            Map<String, Object> map = new HashedMap();

            List<InvestmentRecord> investmentRecords = investmentRecordService.findList(projectId, true, InvestmentState.PAID, InvestmentState.SUCCESS);
            List<InvestmentRecord> tranferRecords = dataConvertService.convertInvestmentRecords(investmentRecords);
            List<InvestmentRecordSimpleVo>  investmentRecordSimpleVoList= new ArrayList<>();
            for (InvestmentRecord tranferRecord : tranferRecords) {
                InvestmentRecordSimpleVo vo=new InvestmentRecordSimpleVo();
                vo.setAmount(tranferRecord.getAmount());
                vo.setBuyTime(new Date(tranferRecord.getCreateDate().getTime()));
                vo.setInvestor(SecrecyUtils.toUsername(userService.getUserById(tranferRecord.getInvestor()).getLoginName(),"*"));
                vo.setId(tranferRecord.getId());
                investmentRecordSimpleVoList.add(vo);
            }
            map.put("records", investmentRecordSimpleVoList);
            map.put("pages", 1);
            return Result.success(String.format("项目[%d]投资记录详情", projectId), map);
        }
    }
    /**
     *
     * 协议
     */
    @RequestMapping("/agreement/{projectId}")
    public String agreement(@PathVariable Integer projectId,ModelMap modelMap) {
        // 验证项目
        Borrowing pProject = borrowingService.get(projectId);
        if (pProject!=null){
            Agreement agreement = agreementService.get(pProject.getAgreementId());
            Map map=new HashMap();
            try {
                modelMap.addAttribute("content",
                        FreemarkerUtils.process(
                                agreement!=null?agreement.getContent():agreementService.findAll().get(0).getContent(),map
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }else {
            modelMap.addAttribute("content",agreementService.findAll().get(0).getContent());
        }
        return "/agreement/agreement";
    }
    /**
     *  预期收益
     */
    @RequestMapping(value = "/expectedReturn/{projectId}",method = RequestMethod.GET)
    @ResponseBody
    public Result calculator(BigDecimal amount, @PathVariable Integer projectId) {
        BigDecimal sum_invests = BigDecimal.ZERO;
        Borrowing borrowing= borrowingService.get(projectId);
       if(borrowing==null){
           Result.error("项目不存在");
       }
        DateLength dateLength = new DateLength(borrowing.getPeriod(), MONTH, InterestMethod.T_PLUS_ZERO);
        if (borrowing.getPeriodUnit().name().equals("MONTH")) {
            dateLength = new DateLength(borrowing.getPeriod(), MONTH, InterestMethod.T_PLUS_ZERO);
        } else if (borrowing.getPeriodUnit().name().equals("DAY")) {
            dateLength = new DateLength(borrowing.getPeriod(), DAY, InterestMethod.T_PLUS_ONE);
        }
        RepayRecords repayRecords = RepayRecordsStrategyHolder.instanse().generateRepayRecords(borrowing.getRepaymentMethod(), amount, borrowing.getInterestRate(), dateLength);
        List<RepaymentRecord> repaymentPlans = repayRecords.getRepaymentPlans();
        for (RepaymentRecord record : repaymentPlans) {
            sum_invests = record.getInterest().add(sum_invests);
        }
        Map map=new HashMap();
        map.put("sum_invests",sum_invests);
        return Result.success("预期收益",map);
    }
    //项目详情
    @RequestMapping(value = "/projectDetail/{projectId}",method = RequestMethod.GET)
    @ResponseBody
    public Result projectDetail(@PathVariable Integer projectId){
        Borrowing borrowing= borrowingService.get(projectId);
        if (borrowing==null)
        {
            return Result.error("项目不存在");
        }
        UserVo userVo= userService.getUserById(borrowing.getBorrower());
        ProjectDetailVo projectDetailVo=new ProjectDetailVo();
        projectDetailVo.setUserType(userService.get(borrowing.getBorrower()).getType());
        projectDetailVo.setIntro(borrowing.getIntro());
        projectDetailVo.setIntroDes(
                borrowing.getIntro().replaceAll("</?[^>]+>","")
                        .replaceAll("\\s*|\t|\r|\n","")
                        .replaceAll("<style[^>]*?>[\\\\s\\\\S]*?<\\\\/style>","")
                        .replaceAll("<script[^>]*?>[\\\\s\\\\S]*?<\\\\/script>","")
                        .replaceAll("&nbsp;","")
        );
        projectDetailVo.setAge(new Date().getYear()-userVo.getBirthday().getYear());
        projectDetailVo.setCorpLicenseNo(SecrecyUtils.toCorpLicenseNo(userVo.getCorpLicenseNo(),null));
        projectDetailVo.setCorpName(SecrecyUtils.toCorpName(userVo.getCorpName(),null));
        projectDetailVo.setGenderDisplay(userVo.getGender().getDisplayName());
        projectDetailVo.setUserRealName(SecrecyUtils.toUsername(userVo.getRealName(),null));
        projectDetailVo.setIdNo(SecrecyUtils.toIdNo(userVo.getIdNo(),null));
        List<Repayment> allRepayments = repaymentService.findByUser(borrowing.getBorrower());
        Integer repaidPeriodCount = 0;
        Integer repayingPeriodCount = 0;
        Integer overduePeriodCount = 0;
        for (Repayment repayment : allRepayments) {
            if (repayment.getIsOverdue()) {
                overduePeriodCount++;
            }
            if (repayment.getState() == RepaymentState.REPAID) {
                repaidPeriodCount++;
            }
            if (repayment.getState() == RepaymentState.REPAYING) {
                repayingPeriodCount++;
            }
        }
        List<BorrowingExtraVo> borrowingExtraVoList=borrowingExtraService.findByBorrowing(projectId);
        List<BorrowingInfoExtrasVo> extrasVos=new ArrayList<>();
       if(borrowingExtraVoList.size()!=0) {
           for (BorrowingExtraDetailVo detailVo : borrowingExtraVoList.get(0).getDetails()) {
               BorrowingInfoExtrasVo borrowingInfoExtrasVo = new BorrowingInfoExtrasVo();
               borrowingInfoExtrasVo.setExtraFieldDes(detailVo.getExtraFieldDes());
               borrowingInfoExtrasVo.setExtraFieldValue(detailVo.getExtraFieldValue());
               extrasVos.add(borrowingInfoExtrasVo);
           }
           projectDetailVo.setBorrowingExtraVoList(extrasVos);
       }
        projectDetailVo.setRepaidPeriodCount(repaidPeriodCount);
        projectDetailVo.setRepayingPeriodCount(repayingPeriodCount);
        projectDetailVo.setOverduePeriodCount(overduePeriodCount);
        return  Result.success("success",projectDetailVo);
    }

}