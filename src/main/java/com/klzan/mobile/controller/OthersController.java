package com.klzan.mobile.controller;

import com.klzan.core.Result;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.DeviceType;
import com.klzan.p2p.model.AppCover;
import com.klzan.p2p.model.MobileApp;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.appcover.AppCoverService;
import com.klzan.p2p.service.mobileapp.MobileAppService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐好友
 *
 * @version: 1.0
 */
@Controller("mobileOthersController")
@RequestMapping("/mobile/others")
public class OthersController extends BaseController {

    @Inject
    private AppCoverService appCoverService;
    @Inject
    private MobileAppService mobileAppService;

    @Inject
    private SettingUtils setting;
    @Resource
    private UserService userService;
    @Resource
    private RSAService rsaService;


    @RequestMapping(value = "/aboutUs")
    public String toAboutUs(HttpServletRequest request, ModelMap model) {
        return "others/aboutus";
    }

    @RequestMapping(value = "/appInfo/json", method = RequestMethod.GET)
    @ResponseBody
    public Result aboutUsData(HttpServletRequest httpServletRequest, DeviceType deviceType) {
        MobileApp latestApp = mobileAppService.findLatestApp(deviceType);
        String dfsFileAccessBasePath = setting.getDfsUrl();
        Map map = new HashMap();
        map.put("version", 1);
        map.put("versionName", "1.0.0");
        map.put("packageName", "");
        map.put("changeLog", "");
        map.put("appUrl", "");
        if (null != latestApp) {
            map.put("version", latestApp.getVersionNo());
            map.put("versionName", latestApp.getVersionName());
            map.put("packageName", latestApp.getPackageName());
            map.put("changeLog", latestApp.getChangeLog());

            if (deviceType == DeviceType.IOS) {
                map.put("appUrl", latestApp.getAppUrl());
            } else {
                map.put("appUrl", dfsFileAccessBasePath + latestApp.getAppUrl());
            }

        }

        return Result.success("应用信息", map);
    }

    @RequestMapping(value = "/helpCenter")
    public String toHelpCenter(HttpServletRequest request, ModelMap model) {
        return "others/help_center";
    }

    @RequestMapping(value = "/appDownLoad")
    public String toAppDownLoad(HttpServletRequest request, ModelMap model) {
        try {
            MobileApp latestApp = mobileAppService.findLatestApp(DeviceType.ANDROID);
            model.addAttribute("dfsUrl", setting.getDfsUrl());
            model.addAttribute("appUrl", latestApp != null ? latestApp.getAppUrl() : latestApp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "mobile/app_down_weixin";
    }

    @RequestMapping(value = "/appDownLoad/{appType}")
    public void appDownLoad(@PathVariable DeviceType appType, HttpServletResponse response) {
        try {
            MobileApp latestApp = mobileAppService.findLatestApp(appType.ANDROID);
            if (latestApp == null) {
                return;
            }
            URL url = new URL(setting.getDfsUrl() + latestApp.getAppUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取子节数组
            byte[] bytes = readInputStream(inputStream);
            String fileName = URLEncoder.encode("santian" + latestApp.getVersionName() + ".apk", "UTF-8");
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.addHeader("Content-Length", "" + bytes.length);
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "appDownCount/{appType}", method = RequestMethod.POST)
    @ResponseBody
    public Result appDownCount(@PathVariable DeviceType appType, HttpServletRequest request) {
        if (appType == DeviceType.ANDROID) {
            mobileAppService.addDownCount(appType);
        }
        if (appType == DeviceType.IOS) {
            mobileAppService.addDownCount(appType);
        }
        return Result.success();
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 启动页
     */
    @RequestMapping(value = "/appCover", method = RequestMethod.GET)
    @ResponseBody
    public Result AppCover() {
        //启动页
        AppCover appCover = appCoverService.findLatestAppCover();
        //引导页
        List<AppCover> appWelcome = appCoverService.findLatestAppWelcomeCover();
        if (appCover != null) {
            String path = setting.getDfsUrl().toString() + appCover.getPath().toString();
            appCover.setPath(path);
        }

        if (appWelcome != null) {
            for (AppCover cover : appWelcome) {
                cover.setPath(setting.getDfsUrl().toString() + cover.getPath().toString());
            }
        }
        Map map = new HashMap();
        map.put("appCover", appCover);
        map.put("appWelcome", appWelcome);

        return Result.success("启动页面", map);
    }

    //关注微信
    @RequestMapping(value = "/weixin")
    public String weixin() {
        return "mobile/weixin";
    }

    //邀请好活动规则
    @RequestMapping(value = "/inviteFriends", method = RequestMethod.GET)
    public String inviteFriends() {
        return "mobile/inviteFriends";
    }
    //关注微信跳转到注册页

    @RequestMapping(value = "/weixinRegist")
    //-----------
    public String weixinRegist(@RequestParam(required = false) String inviteCode, HttpServletRequest request, Model
            model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/";
        }
        // 密钥
        RSAPublicKey publicKey = rsaService.generateKey(request);
        model.addAttribute("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        model.addAttribute("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));

        if (StringUtils.isNotBlank(inviteCode)) {
            User inviteUser = userService.getUserByInviteCode(inviteCode);
            if (inviteUser != null) {
                model.addAttribute("referrer", inviteUser.getMobile());
                model.addAttribute("inviteCode", inviteCode);
                model.addAttribute("inviteMobile", inviteUser.getMobile());
            } else {
                model.addAttribute("flashMessage", "邀请码不存在");
            }
        }
        return "mobile/regist";
    }

}
