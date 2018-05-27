package com.klzan.p2p.controller.admin;

import com.klzan.p2p.common.service.RSAService;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.RSAPublicKey;

@Controller("adminLoginController")
@RequestMapping("admin")
public class LoginController extends BaseAdminController {
    @Autowired
    private RSAService rsaService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            return "redirect:/admin/index";
        }
        // 密钥
        RSAPublicKey publicKey = rsaService.generateKey(request);
        model.addAttribute("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        model.addAttribute("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
        return "admin/login/index";
    }

    /**
     * 登出
     * @param model
     * @return
     */
    @GetMapping(value="logout")
    public String logout(Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/admin/login";
    }

}
