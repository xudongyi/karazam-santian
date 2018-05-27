package com.klzan.p2p.controller;

import com.klzan.core.Result;
import com.klzan.p2p.enums.CaptchaType;
import com.klzan.p2p.service.captcha.CaptchaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * 图形验证码
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @Inject
    private CaptchaService captchaService;

    /**
     * 图形验证码
     */
    @RequestMapping(method = RequestMethod.GET)
    public void image(@RequestParam CaptchaType type, HttpSession session, HttpServletResponse response) throws Exception {
        response.addHeader("Powered-By", "klzan.com");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setDateHeader("Expires", 0L);
        response.setContentType("image/jpeg");

        BufferedImage bufferedImage = captchaService.buildImage(type, session);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", servletOutputStream);
    }

    /**
     * 验证图形验证码
     */
    @RequestMapping(value = "/verify")
    @ResponseBody
    public Result image(@RequestParam CaptchaType type, @RequestParam String imageCaptcha, HttpServletRequest request) throws Exception {

        // 验证验证码
        if (!captchaService.verify(type, imageCaptcha, request.getSession())) {
            return Result.error("图形验证码错误");
        }
        return Result.success();
    }

}