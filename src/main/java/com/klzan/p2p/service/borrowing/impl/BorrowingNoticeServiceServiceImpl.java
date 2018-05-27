/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing.impl;

import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.controller.IndexController;
import com.klzan.p2p.enums.BorrowingContactsType;
import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingContactsService;
import com.klzan.p2p.service.borrowing.BorrowingNoticeService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.sms.SmsService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.BasicSetting;
import com.klzan.p2p.setting.RepaymentNoticeSetting;
import com.klzan.p2p.setting.SettingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 借款通知
 */
@Service
public class BorrowingNoticeServiceServiceImpl implements BorrowingNoticeService {

    protected static Logger logger = LoggerFactory.getLogger(BorrowingNoticeService.class);

    @Autowired
    private SmsService smsService;
    @Autowired
    private SettingUtils settingUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private BorrowingContactsService borrowingContactsService;
    @Autowired
    private BorrowingService borrowingService;

    @Override
    public void full(Borrowing borrowing) {
        try {
            //标的负责人（必须）
            List<BorrowingContacts> contacts = borrowingContactsService.findList(borrowing.getId());
            for(BorrowingContacts contact : contacts){
                if(contact.getType().equals(BorrowingContactsType.PRINCIPAL)){
                    smsService.send(contact.getMobile(), "您好，项目-[" + borrowing.getTitle() + "]已满标,id["+borrowing.getId()+"]【智链金融】", SmsType.FULL_NOTICE);
                }
            }
        }catch (Exception e){
            logger.info("满标通知失败:borrowing["+borrowing+"]");
            e.printStackTrace();
        }
    }

    @Override
    public void lendingToBorrower(Borrowing borrowing, Map map) {
        try {
            //借款人（可选）
//            User user = userService.get(borrowing.getBorrower());
            Integer borrower = (Integer)map.get("borrower");
            BigDecimal amount = (BigDecimal)map.get("amount");
            String mobile = getMobile(borrower);
            if (StringUtils.isNotBlank(mobile)){
                smsService.send(mobile, "您好，项目-[" + borrowing.getTitle() + "]已放款,借款金额["+amount+"]【智链金融】", SmsType.LENDING_NOTICE);
            }else {
                logger.info("出借/放款通知失败:userId["+borrower+"]");
            }
        }catch (Exception e){
            logger.info("出借/放款通知失败:borrowing["+borrowing+"]"+JsonUtils.toJson(map));
            e.printStackTrace();
        }
    }

    @Override
    public void lendingToInvestors(Borrowing borrowing, List<Map> investors) {
        try {
            //投资人（可选）
            for(Map map : investors){
                Integer investor = (Integer)map.get("investor");
                BigDecimal amount = (BigDecimal)map.get("amount");
//                User user = userService.get(investor);
//                smsService.send("mobile", "您好，项目-" + borrowing.getTitle() + ",已出借【智链金融】", SmsType.LENDING_NOTICE);
                String mobile = getMobile(investor);
                if (StringUtils.isNotBlank(mobile)){
                    smsService.send(mobile, "您好，项目-[" + borrowing.getTitle() + "]已放款,投资金额["+amount+"]【智链金融】", SmsType.LENDING_NOTICE);
                }else {
                    logger.info("出借/放款通知失败:userId["+investor+"]");
                }
            }
        }catch (Exception e){
            logger.info("出借/放款通知失败:borrowing["+borrowing+"]"+ JsonUtils.toJson(investors));
            e.printStackTrace();
        }
    }

    @Override
    public void repayment(Integer borrowingId, Integer repaymentId, Integer count, List<Map> investors) {
        try {

            Borrowing borrowing = borrowingService.get(borrowingId);

            //标的负责人（配置）、投资人（配置）
            RepaymentNoticeSetting setting = settingUtils.getRepaymentNoticeSetting();
            if(setting.getNoticePrincipal()){ //标的负责人
                List<BorrowingContacts> contacts = borrowingContactsService.findList(borrowingId);
                for(BorrowingContacts contact : contacts){
                    if(contact.getType().equals(BorrowingContactsType.PRINCIPAL)){
//                        smsService.send("mobile", "您好，有一笔" + "" + "【智链金融】", SmsType.REPAYMENT_NOTICE);
                        String mobile = contact.getMobile();
                        if (StringUtils.isNotBlank(mobile)){
                            smsService.send(mobile, "您好，项目-[" + borrowing.getTitle() + "],borrowingId["+borrowing.getId()+"]第["+repaymentId+"]期,已还款,总笔数["+count+"],本次成功笔数["+investors.size()+"]【智链金融】", SmsType.LENDING_NOTICE);
                        }else {
                            logger.info("还款通知失败:标的负责人mobile["+mobile+"]");
                        }
                    }
                }
            }
            if(setting.getNoticeInvestor()){ //投资人
                for(Map map : investors){
                    Integer investor = (Integer)map.get("investor");
                    BigDecimal amount = (BigDecimal)map.get("amount");
//                    User user = userService.get(investor);
//                    smsService.send("mobile", "您好，有一笔" + "" + "【智链金融】", SmsType.REPAYMENT_NOTICE);
                    String mobile = getMobile(investor);
                    if (StringUtils.isNotBlank(mobile)){
                        smsService.send(mobile, "您好，项目-[" + borrowing.getTitle() + "],borrowingId["+borrowing.getId()+"]第["+repaymentId+"]期,已还款,回款金额["+amount+"]【智链金融】", SmsType.LENDING_NOTICE);
                    }else {
                        logger.info("还款通知失败:投资人mobile["+mobile+"]");
                    }
                }
            }
        }catch (Exception e){
            logger.info("还款通知失败:borrowing["+borrowingId+"]" + JsonUtils.toJson(investors));
            e.printStackTrace();
        }
    }

    @Override
    public void repaymentAdvance(Integer borrowingId, Integer count, List<Map> investors) {
        try {

            Borrowing borrowing = borrowingService.get(borrowingId);

            //标的负责人（配置）、投资人（配置）
            RepaymentNoticeSetting setting = settingUtils.getRepaymentNoticeSetting();
            if(setting.getNoticePrincipal()){ //标的负责人
                List<BorrowingContacts> contacts = borrowingContactsService.findList(borrowingId);
                for(BorrowingContacts contact : contacts){
                    if(contact.getType().equals(BorrowingContactsType.PRINCIPAL)){
//                        smsService.send("mobile", "您好，有一笔" + "" + "【智链金融】", SmsType.REPAYMENT_NOTICE);
                        String mobile = contact.getMobile();
                        if (StringUtils.isNotBlank(mobile)){
                            smsService.send(mobile, "您好，项目-[" + borrowing.getTitle() + "],borrowingId["+borrowing.getId()+"],已提前还款,总笔数["+count+"],本次成功笔数["+investors.size()+"]【智链金融】", SmsType.LENDING_NOTICE);
                        }else {
                            logger.info("提前还款通知失败:标的负责人mobile["+mobile+"]");
                        }
                    }
                }
            }
            if(setting.getNoticeInvestor()){ //投资人
                for(Map map : investors){
                    Integer investor = (Integer)map.get("investor");
                    BigDecimal amount = (BigDecimal)map.get("amount");
//                    User user = userService.get(investor);
//                    smsService.send("mobile", "您好，有一笔" + "" + "【智链金融】", SmsType.REPAYMENT_NOTICE);
                    String mobile = getMobile(investor);
                    if (StringUtils.isNotBlank(mobile)){
                        smsService.send(mobile, "您好，项目-[" + borrowing.getTitle() + "],borrowingId["+borrowing.getId()+"],已提前还款,提前回款金额["+amount+"]【智链金融】", SmsType.LENDING_NOTICE);
                    }else {
                        logger.info("提前还款通知失败:投资人mobile["+mobile+"]");
                    }
                }
            }
        }catch (Exception e){
            logger.info("提前还款通知失败:borrowing["+borrowingId+"]" + JsonUtils.toJson(investors));
            e.printStackTrace();
        }
    }

    public String getMobile(Integer userId) {
        if(userId!=null){
            User user = userService.get(userId);
            if (user!=null) {
                return user.getMobile();
            }
        }
        return "";
    }

}