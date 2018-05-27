package com.klzan.p2p.controller.admin.user;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.core.util.BeanUtils;
import com.klzan.p2p.model.Corporation;
import com.klzan.p2p.model.CorporationLegal;
import com.klzan.p2p.service.user.CorporationLegalService;
import com.klzan.p2p.service.user.CorporationService;
import com.klzan.p2p.vo.user.CorporationVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhutao on 2017/4/5.
 */
@Controller
@RequestMapping("admin/corporation")
public class CorporationController {
    @Resource
    private CorporationService corporationService;
    @Resource
    private CorporationLegalService corporationLegalService;

    @RequestMapping("list")
    public String list(ModelMap modelMap) {
        return "admin/user/corporate";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
    @ResponseBody
    public Object listQuery(PageCriteria pageCriteria, HttpServletRequest request) {
        ParamsFilter.buildFromHttpRequest(request, pageCriteria);
        PageResult<CorporationVo> corporation = corporationService.findCorporation(pageCriteria);
        return corporation;
    }

    @RequestMapping("update/{id}")
    public String upDateCorpotation(@PathVariable Integer id,ModelMap model) {
        Corporation temp = corporationService.findCorporationById(id);
        CorporationLegal user= corporationLegalService.findCorporationlegalBylegalId(temp.getLegalId());

        CorporationVo corporation = new CorporationVo();
        try {
            BeanUtils.copyBean2Bean(corporation, temp);//将temp属性值copy到corporation
            corporation.setCorporationMobile(user.getCorporationMobile());
            corporation.setCorporationName(user.getCorporationName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("action","update");
        model.addAttribute("corporation",corporation);
        return "admin/user/corporateForm";
    }

    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result save(CorporationVo vo,@PathVariable Integer id) {
        if(id<=0)
        {
            return Result.error("系统错误");
        }
        Corporation corporation = corporationService.findCorporationById(id);
        Integer legalId=corporation.getLegalId();
        CorporationLegal user= corporationLegalService.findCorporationlegalBylegalId(legalId);
        corporation.setCorpName(vo.getCorpName());
        user.setCorporationName(vo.getCorporationName());
//        user.setCorporationMobile(vo.getCorporationMobile());
        corporation.setCorpType(vo.getCorpType());
        corporation.setCorpDomain(vo.getCorpDomain());
        corporation.setCorpScale(vo.getCorpScale());
        corporation.setCorpCertification(vo.getCorpCertification());
        corporation.setCorpWithGuarantee(vo.getCorpWithGuarantee());
        corporation.setEnterpriseBorrowingAbility(vo.getEnterpriseBorrowingAbility());
        corporation.setCorpIntro(vo.getCorpIntro());
        corporation.setCorpAssetSize(vo.getCorpAssetSize());
        corporation.setCorpPrevYearOperatedRevenue(vo.getCorpPrevYearOperatedRevenue());
        corporation.setCorpRegisteredCapital(vo.getCorpRegisteredCapital());
        corporation.setCorpLocality(vo.getCorpLocality());
        corporation.setCorpAddr(vo.getCorpAddr());
        corporation.setCorpZipcode(vo.getCorpZipcode());
        corporation.setCorpLicenseNo(vo.getCorpLicenseNo());
        corporation.setCorpLicenseIssueDate(vo.getCorpLicenseIssueDate());
        corporation.setCorpNationalTaxNo(vo.getCorpNationalTaxNo());
        corporation.setCorpLandTaxNo(vo.getCorpLandTaxNo());
        corporationLegalService.update(user);
        corporationService.update(corporation);

        return Result.success("保存成功");
    }
}