/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.plugin.pay.cpcn.listener;

import com.klzan.core.SpringObjectFactory;
import com.klzan.core.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import payment.api.system.PaymentEnvironment;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.util.Date;

/**
 * 支付插件初始化 - 中金托管
 */
@Component
public class CpcnPaymentInitListener implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CpcnPaymentInitListener.class.getName());

    /** servletContext */
    private ServletContext servletContext;

    @Inject
    private SpringObjectFactory springObjectFactory;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            // 当spring容器初始化完成后执行
            try {
                SpringObjectFactory.Profile profile = springObjectFactory.getActiveProfile();
                String path = "/config/" + profile.getProfileName() + "/payment";
                String paymentConfigPath = new ClassPathResource(path).getFile().getAbsolutePath();

                // 初始化支付环境
                PaymentEnvironment.initialize(paymentConfigPath);
                logger.info("initial paymentconfig success {}", DateUtils.format(new Date(), DateUtils.DATE_PATTERN_DEFAULT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}