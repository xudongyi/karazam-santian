package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.util.DigestUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.mobile.vo.modifyPasswordVo;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserInfo;
import com.klzan.p2p.model.UserLog;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.user.CorporationLegalService;
import com.klzan.p2p.service.user.UserInfoService;
import com.klzan.p2p.service.user.UserLogService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.business.Request;
import com.klzan.p2p.vo.user.UserIdentityVo;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.comquery.vo.IpsPayCommonQueryResponse;
import com.klzan.plugin.pay.ips.comquery.vo.UserInfoResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 安全中心
 * Created by suhao Date: 2016/12/2 Time: 14:46
 *
 * @version: 1.0
 */
@Controller("mobileUCSecurityController")
@RequestMapping("/mobile/uc/security")
public class SecurityController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RSAService rsaService;
    @Resource
    private CorporationLegalService corporationLegalService;
    @Resource
    private UserLogService userLogService;
    @Resource
    private BusinessService businessService;
/*
* 实名认证
* */
    @RequestMapping(value = "/identity", method = RequestMethod.POST)
    @ResponseBody
    public Result identity(@CurrentUser User user, @RequestBody UserIdentityVo userIdentityVo) {
        userIdentityVo.setUserId(user.getId());
        String idNo = userIdentityVo.getIdNo();
        String realName = userIdentityVo.getRealName();
        if (StringUtils.isBlank(realName) || StringUtils.isBlank(idNo)) {
            return Result.error("参数错误");
        }
        UserInfo userInfo = userInfoService.getUserInfo(user.getId());
        // 验证是否已绑定身份证号码
        if (userInfo.hasIdentity()) {
            return Result.error("身份已认证");
        }
        // 验证身份证号码是否唯一
        if (user.getType().equals("GENERAL")){
            List userInfoList = userInfoService.findByIdNo(user.getType(), idNo);
            if (userInfoList.size() >= 1) {
                return Result.error("身份证号码已被认证");
            }
        }

        try {
            userService.addIdentityInfo(userIdentityVo);
        } catch (Exception e) {
            return Result.error("身份认证错误");
        }

        return Result.success("success");
    }

/*
*  环迅开户
* */
@RequestMapping(value = "/openAccount",method = RequestMethod.POST)
@ResponseBody
public Result check(@CurrentUser User user,DeviceType deviceType){
    UserVo userVo = userService.getUserById(user.getId());
    if(StringUtils.isBlank(userVo.getIdNo())){
        return Result.error("请先实名认知");
    }
    Request request = businessService.openAccount(user);
    Map map = request.getParameterMap();
    return Result.success("success",map);
}
/*
*托管账号登录
 */
@RequestMapping(value = "tgLogin", method = RequestMethod.GET)
@ResponseBody
public Result login(@CurrentUser User user) {
    Request request;
    try {
        request = businessService.login(user);
    } catch (Exception e) {
        e.printStackTrace();
        return Result.error("托管账号登录失败");
    }
    return Result.success("托管账号登录成功",request);
}

    /**
     * 安全中心修改登录密码
     */
    @RequestMapping(value = "login_password", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyLoginPasswrod(@RequestBody modifyPasswordVo vo,HttpServletRequest request) {
        String token = request.getHeader("sid");
        User user = null;
        if(!StringUtils.isBlank(token)){
            user = userService.getCurrentUserOfApp(null,token);
        }
        if (user == null){
            return Result.error("未登录");
        }
        //解密

        String newPwd=DigestUtils.decrypt(vo.getNewPassword());
        String oldPwd=DigestUtils.decrypt(vo.getOldPassword());

        if(!PasswordHelper.verifyPassword(user,oldPwd)){
            return Result.error("输入密码错误");
        }

        // 验证新密码长度
        if (newPwd.length() < 4 || newPwd.length() > 20) {
            return Result.error("参数错误");
        }

        // 验证新密码
        if (StringUtils.equals(newPwd, oldPwd)) {
           return Result.error("新密码不能与旧密码相同");
        }

        try {
            // 更新密码
            PasswordHelper.encryptPassword(user, newPwd);
            userService.update(user);
            UserLog userLog = new UserLog(UserLogType.MODIFY, new String("移动端修改登陆密码"), user.getLoginName(), WebUtils.getRemoteIp(request), user.getId());
            userLogService.persist(userLog);
            return Result.success("success");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("登录密码设置失败");
        }
    }
    @RequestMapping(value = "/isOpenAccountAndTiedCard",method = RequestMethod.GET)
    @ResponseBody
    public Result isOpenAccountAndTiedCard(@CurrentUser User user) {
        boolean isOpenAccoun = true;
        boolean isTiedCard = true;
        boolean isIdentity=true;
        Map map=new HashMap();
        try {
            IDetailResponse query = businessService.query("01", user.getPayAccountNo());
            IpsPayCommonQueryResponse commonResponse = (IpsPayCommonQueryResponse) query;
            UserInfoResponse userInfo = commonResponse.getUserInfo();
            if (com.klzan.core.util.StringUtils.isBlank(userInfo.getBankCard())) {
                isTiedCard=false;
            }
            if(userInfo.getuCardStatus().equals("0")){
                isIdentity=false;
            }
        }catch (Exception e) {
            isTiedCard=false;
        }
        isOpenAccoun= StringUtils.isNotBlank(user.getPayAccountNo());
        map.put("isIdentity",isIdentity);
        map.put("isTiedCard",isTiedCard);
        map.put("isOpenAccoun",isOpenAccoun);
        return Result.success("success",map);
    }

    /**
     * 检查身份证唯一
     */
    @RequestMapping(value = "/checkIdNo", method = RequestMethod.POST)
    @ResponseBody
    public Result check(@CurrentUser User user, String idNo) throws ParseException {

        Scanner s=new Scanner(idNo);
        String IdCard=new String(s.next());
        IdCard = IdCard.toUpperCase();
        String msg= IDCardValidate(IdCard);
        boolean isEnable=msg.equals("该身份证有效！");
        if(user.getType().equals("GENERAL")){
            if (userInfoService.findByIdNo(user.getType(), idNo).size() != 0) {
                return Result.error("身份证号已认证");
            }
        }

        if(isEnable){
            return Result.success(msg);
        }
        return Result.error(msg);
    }
    public static String IDCardValidate(String IDStr) throws ParseException {
        String tipInfo = "该身份证有效！";// 记录错误信息
        String Ai = "";
        // 判断号码的长度 15位或18位
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            tipInfo = "身份证号码长度应该为15位或18位。";
            return tipInfo;
        }

        // 18位身份证前17位位数字，如果是15位的身份证则所有号码都为数字
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            tipInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return tipInfo;
        }

        // 判断出生年月是否有效
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 日期
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            tipInfo = "身份证出生日期无效。";
            return tipInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                tipInfo = "身份证生日不在有效范围。";
                return tipInfo;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            tipInfo = "身份证月份无效";
            return tipInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            tipInfo = "身份证日期无效";
            return tipInfo;
        }

        // 判断地区码是否有效
        Hashtable areacode = GetAreaCode();
        //如果身份证前两位的地区码不在Hashtable，则地区码有误
        if (areacode.get(Ai.substring(0, 2)) == null) {
            tipInfo = "身份证地区编码错误。";
            return tipInfo;
        }

        if(isVarifyCode(Ai,IDStr)==false){
            tipInfo = "身份证校验码无效，不是合法的身份证号码";
            return tipInfo;
        }

        return tipInfo;
    }
    /*
     * 判断第18位校验码是否正确
    * 第18位校验码的计算方式：
       　　1. 对前17位数字本体码加权求和
       　　公式为：S = Sum(Ai * Wi), i = 0, ... , 16
       　　其中Ai表示第i个位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
       　　2. 用11对计算结果取模
       　　Y = mod(S, 11)
       　　3. 根据模的值得到对应的校验码
       　　对应关系为：
       　　 Y值：     0  1  2  3  4  5  6  7  8  9  10
       　　校验码： 1  0  X  9  8  7  6  5  4  3   2
    */
    private static boolean isVarifyCode(String Ai,String IDStr) {
        String[] VarifyCode = { "1", "0", "X", "9", "8", "7", "6", "5", "4","3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7","9", "10", "5", "8", "4", "2" };
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum = sum + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = sum % 11;
        String strVerifyCode = VarifyCode[modValue];
        Ai = Ai + strVerifyCode;
        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                return false;

            }
        }
        return true;
    }

    /**
     * 将所有地址编码保存在一个Hashtable中
     * @return Hashtable 对象
     */

    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 判断字符串是否为数字,0-9重复0次或者多次
     * @param strnum
     * @return
     */
    private static boolean isNumeric(String strnum) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(strnum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串出生日期是否符合正则表达式：包括年月日，闰年、平年和每月31天、30天和闰月的28天或者29天
     *
     * @param
     * @return
     */
    public static boolean isDate(String strDate) {

        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
}

