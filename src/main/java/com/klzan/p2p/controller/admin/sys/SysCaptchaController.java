package com.klzan.p2p.controller.admin.sys;

import com.klzan.p2p.enums.CaptchaType;
import com.klzan.p2p.service.captcha.CaptchaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @class : CaptchaController
 * @description: 验证码
 */
@Controller
@RequestMapping("/admin/captcha")
public class SysCaptchaController {

    @Inject
    private CaptchaService captchaService;

    /**
     * 验证码
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

}