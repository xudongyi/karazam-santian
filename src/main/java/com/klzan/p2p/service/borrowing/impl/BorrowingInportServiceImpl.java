/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing.impl;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.user.UserDao;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.borrowing.BorrowingInportService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.vo.borrowing.BorrowingImportVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目批量导入
 */
@Service
@Transactional
public class BorrowingInportServiceImpl extends BaseService<Borrowing> implements BorrowingInportService {

    @Inject
    private SettingUtils setting;
    @Inject
    private UserDao userDao;
    @Inject
    private BorrowingDao borrowingDao;

    @Override
    public Boolean importOne(BorrowingImportVo vo) {
        try {
            Borrowing borrowing = new Borrowing();
            borrowing.setBorrower(vo.getBorrower());
            borrowing.setType(vo.getType());
            borrowing.setTitle(vo.getTitle());
            borrowing.setIntro(vo.getIntro());
            borrowing.setAmount(vo.getAmount());
            borrowing.setPeriod(vo.getPeriod());
            borrowing.setPeriodUnit(vo.getPeriodUnit());
            borrowing.setInterestRate(vo.getInterestRate());
            borrowing.setInterestMethod(vo.getInterestMethod());
            borrowing.setRewardInterestRate(vo.getRewardInterestRate() == null ? BigDecimal.ZERO : vo.getRewardInterestRate());
            borrowing.setDescription(vo.getDescription());
            borrowing.setPurpose(vo.getPurpose());
            borrowing.setFieldInquiry(vo.getFieldInquiry());
            borrowing.setCreditInquiry(vo.getCreditInquiry());
            borrowing.setRepaymentInquiry(vo.getRepaymentInquiry());
            borrowing.setGuaranteeMethod(vo.getGuaranteeMethod());
            borrowing.setGuaranteeCorp(vo.getGuaranteeCorp());
            borrowing.setGuarantee(vo.getGuarantee());
            borrowing.setCollateral(vo.getCollateral());
            borrowing.setInvestmentMethod(vo.getInvestmentMethod());
            borrowing.setInvestmentMinimum(vo.getInvestmentMinimum());
            borrowing.setInvestmentMaximum(vo.getInvestmentMaximum());
            borrowing.setInvestmentStartDate(vo.getInvestmentStartDate());
            borrowing.setInvestmentEndDate(vo.getInvestmentEndDate());
            borrowing.setLendingTime(vo.getLendingTime());
            borrowing.setRepaymentMethod(vo.getRepaymentMethod());
            borrowing.setFeeRate(vo.getFeeRate());
            borrowing.setRepaymentFeeMethod(vo.getRepaymentFeeMethod());
            borrowing.setRepaymentFeeRate(vo.getRepaymentFeeRate());
            borrowing.setRecoveryFeeRate(vo.getRecoveryFeeRate());
            borrowing.setInTransferFeeRate(vo.getInTransferFeeRate());
            borrowing.setOutTransferFeeRate(vo.getOutTransferFeeRate());
            borrowing.setOverdueInterestRate(vo.getOverdueInterestRate());
            borrowing.setSeriousOverdueStartPeriod(vo.getSeriousOverdueStartPeriod());
            borrowing.setSeriousOverdueInterestRate(vo.getSeriousOverdueInterestRate());
            borrowing.setRiskAnalysis(vo.getRiskAnalysis());
            borrowing.setInvestedAmount(BigDecimal.ZERO);
            borrowing.setOccupyAmount(BigDecimal.ZERO);
            borrowing.setOccupyCount(0);
            borrowing.setFee(BigDecimal.ZERO);
            borrowing.setPaidFee(BigDecimal.ZERO);
            borrowing.setIp(CommonUtils.getRemoteIp());
            borrowing.setIsRecommend(vo.getIsRecommend());
            borrowing.setSubjectSituation(vo.getSubjectSituation());
            borrowing.setTradingContractNo(vo.getTradingContractNo());
            borrowing.setCommercialFactoringContractNo(vo.getCommercialFactoringContractNo());
            borrowing.setFactoringComBussinessNo(vo.getFactoringComBussinessNo());
            borrowing.setReceivables(vo.getReceivables());
            //TODO 增加协议字段
            borrowing.setAgreementId(new Integer(vo.getAgreementId()));
            borrowing.setTransferAgreementId(new Integer(vo.getTransferAgreementId()));
            borrowing.setInvestTransferAgreementId(new Integer(vo.getInvestTransferAgreementId()));
            borrowing.setSurportCoupon(vo.getSurportCoupon());
            borrowing.setAgreementPlace(vo.getAgreementPlace());
            borrowing.setLabels(vo.getLabels());
            borrowing.setAutoInvest(vo.getAutoInvest());
            if (vo.getAutoInvest()) {
                if (vo.getInvestmentMinimum().compareTo(new BigDecimal(100)) != 0) {
//                    throw new BusinessProcessException("开启自动投标最低投资金额必须是100");
                    logger.info("开启自动投标最低投资金额必须是100");
                    return false;
                }
            }
            borrowingDao.persist(borrowing);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Result importBatch(List<BorrowingImportVo> vos) {

        //数据验证
        List<Map> data = new ArrayList<>();
        Boolean pass = Boolean.TRUE;
        for(BorrowingImportVo vo : vos){
            String mobile = vo.getMobile();
            UserType userType = vo.getUserType();
            String title = vo.getTitle();
            Map map = new HashMap();
            map.put("mobile", mobile);
            map.put("userType", userType);
            map.put("title", title);
            User user = userDao.getUserByMobile(mobile, userType);
            if(user.verifyPayAccount()){
                map.put("message", "已开户");
            }else {
                map.put("message", "未开户,不能导入项目");
                pass = Boolean.FALSE;
            }
            data.add(map);
        }

        if(!pass){
            return Result.error("数据验证未通过", JsonUtils.toJson(data));
        }

        //数据导入
        List<Map> dataImport = new ArrayList<>();
        for(BorrowingImportVo vo : vos){
            String mobile = vo.getMobile();
            UserType userType = vo.getUserType();
            String title = vo.getTitle();
            Map map = new HashMap();
            map.put("mobile", mobile);
            map.put("userType", userType);
            map.put("title", title);
            Boolean success = this.importOne(vo);
            if(success){
                map.put("message", "导入成功");
            }else {
                map.put("message", "导入失败");
            }
            dataImport.add(map);
        }

        return Result.success("导入完成", JsonUtils.toJson(dataImport));
    }

}