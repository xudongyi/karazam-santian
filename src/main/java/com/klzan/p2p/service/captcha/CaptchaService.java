package com.klzan.p2p.service.captcha;

import com.klzan.p2p.enums.CaptchaType;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * Created by Administrator on 2015/9/1.
 */
public interface CaptchaService {
    /**
     * 生成验证码图片
     *
     * @param session 会话
     * @return 验证码图片
     */
    BufferedImage buildImage(HttpSession session);

    /**
     * 生成验证码图片
     * @param captchaType
     * @param session
     * @return
     */
    BufferedImage buildImage(CaptchaType captchaType, HttpSession session);

    /**
     * 验证码验证
     *
     * @param captchaType 验证码类型
     * @param captcha     验证码（忽略大小写）
     * @param session     会话
     * @return 验证是否通过
     */
    boolean verify(CaptchaType captchaType, String captcha, HttpSession session);

    /**
     * 验证码验证
     *
     * @param captcha 验证码（忽略大小写）
     * @param session 会话
     * @return 验证是否通过
     */
    boolean verify(String captcha, HttpSession session);

}
