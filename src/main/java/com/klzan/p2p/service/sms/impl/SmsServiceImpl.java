/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.sms.impl;

import com.klzan.core.SpringObjectFactory;
import com.klzan.core.freemarker.DefaultFreeMarkerConfigurer;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.FreemarkerUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.dao.sms.SmsLogDao;
import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.model.SmsLog;
import com.klzan.p2p.model.SmsToken;
import com.klzan.p2p.model.TemplateContent;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.sms.SmsService;
import com.klzan.p2p.service.sms.SmsTokenService;
import com.klzan.p2p.service.template.TemplateContentService;
import com.klzan.p2p.setting.BasicSetting;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.plugin.message.sms.SmsPlugin;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {

    @Resource(name = "cloudC253SmsPlugin")
    private SmsPlugin smsPlugin;

    /**
     * 短信验证码参数
     */
    private static final String VALIDATE_CODE = "validateCode";

    @Inject
    private SmsLogDao smsLogDao;

    @Inject
    private SpringObjectFactory springObjectFactory;

    @Inject
    private TemplateContentService templateContentService;

    @Inject
    private SmsTokenService tokenService;

    @Inject
    private SettingUtils settingUtils;

    @Inject
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    @Transactional
    public void send(String mobile, Map<String, Object> model, SmsType type) throws Exception {
        TemplateContent templateContent = templateContentService.findByType(type.getTemplateContentType(), type.getTemplatePurpose());
        if (mobile != null && templateContent != null) {
            try {
                String cont = FreemarkerUtils.process(templateContent.getContent(), model);
                this.send(mobile, cont, type);
            } catch (TemplateException var8) {
                var8.printStackTrace();
            } catch (IOException var9) {
                var9.printStackTrace();
            }
        } else {
            throw new Exception("参数错误");
        }
    }

    @Override
    @Transactional
    public void send(String mobile, String content, SmsType type) throws Exception {
        SpringObjectFactory.Profile profile = springObjectFactory.getActiveProfile();
        boolean isProd = profile == SpringObjectFactory.Profile.PROD;
        boolean isTest = profile == SpringObjectFactory.Profile.TEST;
        if (isProd || isTest) {
            // 发送短信时，一段时间内做次数限制
//            this.timeRestrict();
            // 发送短信
            this.send(mobile, content);
        }
        //短信日志
        smsLogDao.persist(new SmsLog(mobile, content, type, profile.getProfileName() + "短信日志"));
    }

    @Override
    @Transactional
    public void sendValidate(String mobile, Map<String, Object> model, SmsType type) throws Exception {
        TemplateContent templateContent = templateContentService.findByType(type.getTemplateContentType(), type.getTemplatePurpose());
        if (mobile != null && templateContent != null && mobile.split(",").length == 1) {
            try {
                SmsToken token = tokenService.build(type, mobile);
                model.put(VALIDATE_CODE, token.getCode());
                String cont = FreemarkerUtils.process(templateContent.getContent(), model);
                mobile = mobile.trim();
                //发送短信
                this.send(mobile, cont, type);
            } catch (TemplateException var8) {
                var8.printStackTrace();
            } catch (IOException var9) {
                var9.printStackTrace();
            }
        } else {
            throw new Exception("参数错误");
        }
    }

    @Override
    public Boolean validate(String mobile, String validateCode, SmsType type) throws Exception {
        return tokenService.verify(type, mobile, validateCode);
    }

    @Override
    @Transactional
    public void sendToWithdrawMng(String userMobile, BigDecimal amount) {
        try {
            BasicSetting basic = settingUtils.getBasic();
            String siteWithdrawNotice = basic.getSiteWithdrawNotice();
            if (StringUtils.isBlank(siteWithdrawNotice)) {
                return;
            }
            DecimalFormat format = new DecimalFormat(",###.##");
            String content = "%s，用户（%s）有一笔：%s元的提现申请，请及时处理。【智链金融】";
            String date = DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS);
            content = String.format(content, date, userMobile, format.format(amount));
            send(siteWithdrawNotice, content, SmsType.OTHER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送短信时，一段时间内做次数限制
     */
    public void timeRestrict() {
        Session session = SecurityUtils.getSubject().getSession();
        User currentUser = null != session ? (User) session.getAttribute("user") : null;
        if (currentUser != null) {
            String countKey = "countKey" + currentUser.getId().toString() + currentUser.getLoginName();  //用户当前短信次数key
            String count = "0"; //短信次数
//            String count = redisClientTemplate.get(countKey); //短信次数
            if (count == null) { //首次/过期
//                redisClientTemplate.set(countKey, "1");
//                redisClientTemplate.expire(countKey, 600);
            } else { //有效时间内
                if (Integer.valueOf(count) >= 10) {
                    LoggerFactory.getLogger(SmsServiceImpl.class).error("用户发送短信超过次数限制,userID:" + currentUser.getId().toString());
                    throw new RuntimeException("超过次数限制，请稍后再试");
                }
//                redisClientTemplate.set(countKey, String.valueOf(Integer.valueOf(count) + 1));
            }
        } else {
            LoggerFactory.getLogger(SmsServiceImpl.class).error("系统发送短信，非用户操作");
        }
    }

    private String send(String mobile, String templatePath, Map<String, Object> model) throws Exception {
        if (mobile == null || templatePath == null) {
            throw new Exception("参数错误");
        }

        try {
            Configuration configuration = springObjectFactory.getBean(DefaultFreeMarkerConfigurer.class).getConfiguration();
            Template template = configuration.getTemplate(templatePath);
            String cont = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            String result = send(mobile, cont);
            return result;
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String send(String mobile, String content) throws Exception {
        if (mobile == null || content == null) {
            throw new Exception("参数错误");
        }
        //发送短信
        String result = smsPlugin.send(mobile, content);

        return result;
    }

}
