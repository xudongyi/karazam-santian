package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.util.SnUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.GenderType;
import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.enums.UserLogType;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserInfo;
import com.klzan.p2p.model.UserLog;
import com.klzan.p2p.service.sms.SmsService;
import com.klzan.p2p.service.user.UserInfoService;
import com.klzan.p2p.service.user.UserLogService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.user.UserIdentityVo;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;

/**
 * 安全中心
 * Created by suhao Date: 2016/12/2 Time: 14:46
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("uc/security")
public class SecurityController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RSAService rsaService;
    @Resource
    private SmsService smsService;
    @Resource
    private UserLogService userLogService;
    @Resource
    private PayUtils payUtils;

    /**
     * 索引重定向URL
     */
    private static final String INDEX_REDIRECT_URL = "redirect:/uc/security";

    @RequestMapping
    public String index(@CurrentUser User currentUser, @RequestParam(defaultValue = "false") Boolean isAuth, Model model, HttpServletRequest request) {
        // 生成密钥
        RSAPublicKey publicKey = rsaService.generateKey(request);
        model.addAttribute("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        model.addAttribute("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
        model.addAttribute("type", currentUser.getType().toString());
        model.addAttribute("isAuth", isAuth);
        model.addAttribute("payAccountInfo", payUtils.getCpcnPayAccountInfo(currentUser.getId()));
        try {
            UserInfoRequest userInfoRequest = new UserInfoRequest();
            userInfoRequest.setUserId(currentUser.getId());
            PayModule payModule = PayPortal.bankcard_bind_query.getModuleInstance();
            payModule.setRequest(userInfoRequest);
            payModule.invoking().getResponse();
        } catch (Exception e) {
            logger.error("绑卡查询错误");
        }
        model.addAttribute("cards", payUtils.getUserBankCards(currentUser.getId()));
        return "uc/security/index";
    }

    @RequestMapping(value = "identity", method = RequestMethod.POST)
    public String identity(@CurrentUser User user, UserIdentityVo userIdentityVo,
                           RedirectAttributes redirectAttributes, HttpServletRequest request) {

        userIdentityVo.setUserId(user.getId());
        String idNo = userIdentityVo.getIdNo();
        String realName = userIdentityVo.getRealName();
        if (StringUtils.isBlank(realName) || StringUtils.isBlank(idNo)) {
            redirectAttributes.addFlashAttribute("flashMessage", "参数错误");
            return INDEX_REDIRECT_URL;
        }
        if (userIdentityVo.getType() == UserType.ENTERPRISE) {
            if (StringUtils.isBlank(userIdentityVo.getCorpName()) || StringUtils.isBlank(userIdentityVo.getCorpLicenseNo())) {
                redirectAttributes.addFlashAttribute("flashMessage", "参数错误");
                return INDEX_REDIRECT_URL;
            }
        }
        UserInfo userInfo = userInfoService.getUserInfo(user.getId());
        // 验证是否已绑定身份证号码
        if (userInfo.hasIdentity()) {
            redirectAttributes.addFlashAttribute("flashMessage", "身份已认证");
            return INDEX_REDIRECT_URL;
        }
        // 验证身份证号码是否唯一
        if (user.getType().equals("GENERAL")){
            List userInfoList = userInfoService.findByIdNo(user.getType(), idNo);

            if (userInfoList.size() >= 1) {
                redirectAttributes.addFlashAttribute("flashMessage", "身份证号码已被认证");
                return INDEX_REDIRECT_URL;
            }
        }

        try {
            userService.addIdentityInfo(userIdentityVo);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("flashMessage", "身份认证错误");
            return INDEX_REDIRECT_URL;
        }

        return INDEX_REDIRECT_URL+"?isAuth=true";
    }

    /**
     * 检查
     */
    @RequestMapping(value = "checkIdNo", method = RequestMethod.POST)
    @ResponseBody
    public boolean check(@CurrentUser User user, String idNo) {
        if (StringUtils.isBlank(idNo)) {
            return false;
        }
        // 验证身份证号码是否唯一
        if(user.getType().equals("GENERAL")){
            if (userInfoService.findByIdNo(user.getType(), idNo).size() == 0) {
                return true;
            } else {
                return false;
            }
        }else {
            return true;
        }

    }

    /**
     * 检查
     */
    @RequestMapping(value = "/checkOldPayPassword", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkOldPayPassword(User user, String currentPassword) {
        if (StringUtils.isBlank(currentPassword)) {
            return false;
        }
        // 验证身份证号码是否唯一
        if (StringUtils.equals(user.getPayPassword(), DigestUtils.md5Hex(currentPassword))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 短信
     */
    @RequestMapping(value = "send_texting", method = RequestMethod.POST)
    @ResponseBody
    public Result sendText(User user, ModelMap mode) {
        // 发送找回密码短信
        if (user.getMobile() == null) {
            return Result.error("未绑定手机号");
        }
        mode.addAttribute("mobile", user.getMobile());
        try {
            smsService.sendValidate(user.getMobile(), mode, SmsType.USER_FIND_PAY_PASSWORD_CODE);
            return Result.success("短信发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("短信发送失败");
        }
    }

    /**
     * 安全中心修改登录密码
     */
    @RequestMapping(value = "login_password", method = RequestMethod.POST)
    public String modifyLoginPasswrod(@CurrentUser User user, ModelMap modle, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        // RSA解密并获取当前密码
        String currentPassword = rsaService.decryptParameter("currentPassword", request);
        // RSA解密并获取密码
        String password = rsaService.decryptParameter("password", request);
        // // 移除公共密匙
        // rsaService.removePrivateKey(request);

        if (StringUtils.isBlank(currentPassword) || StringUtils.isBlank(password)) {
            redirectAttributes.addFlashAttribute("flashMessage", "参数错误");
            return "redirect:/uc/security";
        }

        // 验证新密码长度
        if (password.length() < 4 || password.length() > 20) {
            redirectAttributes.addFlashAttribute("flashMessage", "参数错误");
            return "redirect:/uc/security";
        }

        // 验证新密码
        if (StringUtils.equals(currentPassword, password)) {
            redirectAttributes.addFlashAttribute("flashMessage", "新密码不能与旧密码相同");
            return "redirect:/uc/security";
        }

        // 验证当前密码
        //数据库中当前用户的密码
        String currentPasswordInTable = user.getPassword();
        //用户输入的原密码
        String currentPasswordInput = PasswordHelper.encryptPassword(currentPassword, user.getSalt());

        if (!currentPasswordInTable.equals(currentPasswordInput)) {
            redirectAttributes.addFlashAttribute("flashMessage", "原密码输入错误");
            return "redirect:/uc/security";
        }

        try {
            // 更新密码
            PasswordHelper.encryptPassword(user, password);
            userService.update(user);
            // 发送修改登录密码短信提醒
            if (user.getMobile() != null && !"".equals(user.getMobile())) {
                modle.addAttribute("mobile", user.getMobile());
                smsService.send(user.getMobile(), modle, SmsType.MODIFY_LOGIN_PASSWORD_NOTICE);
            }
            redirectAttributes.addFlashAttribute("flashMessage", "登录密码设置成功");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flashMessage", "登录密码设置失败");
        }
        UserLog userLog = new UserLog(UserLogType.MODIFY, new String("PC端用户中心修改登陆密码"), user.getLoginName(), WebUtils.getRemoteIp(request), user.getId());
        userLogService.persist(userLog);
        return "redirect:/uc/security";
    }

    @RequestMapping(value = "authentication", method = RequestMethod.POST)
    public String authentication(@CurrentUser User user, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //获取借口必要参数
        String bankId = request.getParameter("bankCode");
        String accountName = request.getParameter("name");
        String accountNumber = request.getParameter("bankCardNumber");
        String identificationNumber = request.getParameter("idNo");
        String orderNumber = SnUtils.getOrderNo();

        // 验证身份证号码是否唯一
        List userInfoList = userInfoService.findByIdNo(user.getType(), identificationNumber);
        if (userInfoList.size() >= 1) {
            //addFlashMessage(redirectAttributes, Message.error("身份证号码已存在"));
            redirectAttributes.addAttribute("flashMessage", "认真失败，身份证号码已存在");
            return INDEX_REDIRECT_URL;
        }
        if (false) {//businessResult.isSuccess()
            UserVo userVo = userService.getUserById(user.getId());

            try {
                Date birth = null;
                GenderType gender = null;

                // 18位身份证号码
                if (StringUtils.length(identificationNumber) == 18) {
                    // 获取出生日期
                    birth = DateUtils.parseDate(StringUtils.substring(identificationNumber, 6, 14), "yyyyMMdd");
                    // 获取性别
                    gender = Integer.parseInt(StringUtils.left(StringUtils.right(identificationNumber, 2), 1)) % 2 == 0 ? GenderType.FEMALE
                            : GenderType.MALE;
                }
                // 15位身份证号码
                else if (StringUtils.length(identificationNumber) == 15) {
                    // 获取出生日期
                    birth = DateUtils.parseDate("19" + StringUtils.substring(identificationNumber, 6, 12), "yyyyMMdd");
                    // 获取性别
                    gender = Integer.parseInt(StringUtils.right(identificationNumber, 1)) % 2 == 0 ? GenderType.FEMALE : GenderType.MALE;
                }
                // 更新user_info表身份信息,
                userVo.setRealName(accountName);
                userVo.setId(user.getId());
                userVo.setIdNo(StringUtils.upperCase(identificationNumber));
                // 更新user表性别、出生日期,
                userVo.setGender(gender);
                userVo.setBirthday(birth);
            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addAttribute("flashMessage", "认证失败，请确认输入信息是否正确");
            }
            //更新User表
            userService.updateUser(userVo);
            //实名认证
            userInfoService.addRealInfoIdentify(userVo);
        } else {
            redirectAttributes.addAttribute("flashMessage", "认证失败，请确认输入信息是否正确");
        }
        return INDEX_REDIRECT_URL;
    }

}
