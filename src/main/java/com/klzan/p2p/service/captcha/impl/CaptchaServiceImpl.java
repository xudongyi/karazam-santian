package com.klzan.p2p.service.captcha.impl;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.klzan.p2p.enums.CaptchaType;
import com.klzan.p2p.service.captcha.CaptchaService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * @author suhao
 * @version 1.0
 * @description: 验证码
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Inject
    private Producer captchaProducer;

    @Override
    public BufferedImage buildImage(HttpSession session) {
        String captcha = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, captcha);
        session.setAttribute(Constants.KAPTCHA_SESSION_DATE, new Date());
        return captchaProducer.createImage(captcha);
    }

    @Override
    public BufferedImage buildImage(CaptchaType captchaType, HttpSession session) {
        String captcha = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY + captchaType.name(), captcha);
        session.setAttribute(Constants.KAPTCHA_SESSION_DATE, new Date());
        return captchaProducer.createImage(captcha);
    }

    @Override
    public boolean verify(CaptchaType captchaType, String captcha, HttpSession session) {
        // 验证验证码范围
        if (captchaType != null && ArrayUtils.contains(CaptchaType.values(), captchaType)) {
            return StringUtils.isNotBlank(captcha) && session != null
                    && StringUtils.equalsIgnoreCase(captcha, (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY + captchaType.name()));
        } else {
            return true;
        }
    }

    @Override
    public boolean verify(String captcha, HttpSession session) {
        // 验证验证码
        return StringUtils.isNotBlank(captcha) && session != null
                && StringUtils.equalsIgnoreCase(captcha, (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY));
    }

}
