package com.klzan.p2p.controller.admin.user;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.GenderType;
import com.klzan.p2p.enums.UserLogType;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.user.*;
import com.klzan.p2p.util.ExcelView;
import com.klzan.p2p.vo.regist.RegistVo;
import com.klzan.p2p.vo.user.UserIdentityVo;
import com.klzan.p2p.vo.user.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhutao on 2017/4/5.
 */
@Controller
@RequestMapping("admin/user")
public class UserController extends BaseAdminController {

    @Inject
    private UserService userService;
    @Inject
    private UserInfoService userInfoService;
    @Inject
    private UserFinanceService userFinanceService;
    @Inject
    private UserLogService userLogService;
    @Inject
    private CorporationLegalService corporationLegalService;
    @Inject
    private CorporationService corporationService;

    @RequestMapping("list")
    public String list(ModelMap modelMap, UserType userType) {
        modelMap.addAttribute("types", UserType.values());
        modelMap.addAttribute("userType", userType);
        return "admin/user/list";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
    @ResponseBody
    public Object listQuery(PageCriteria pageCriteria, HttpServletRequest request) {
        Map map = new HashMap();
        map.put("type", request.getParameter("type"));
        map.put("mobile", request.getParameter("mobile"));
        map.put("realName", request.getParameter("realName"));
        PageResult<UserVo> userList = userService.findAllUserByPage(pageCriteria, map);
        return userList;
    }

    @RequestMapping(value = "exportUser")
    public ModelAndView exportUser(ModelMap model, String type, HttpServletRequest request) {
        String loginName = request.getParameter("loginName");
        String mobile = request.getParameter("mobile");
        String realName = request.getParameter("realName");
        UserType userType = null;
        if (StringUtils.isNotBlank(type)) {
            userType = UserType.valueOf(type);
        }
        List<UserVo> result = userService.findAllUser(loginName, mobile, userType, realName);
        String[] properties = {"id", "loginName", "name", "mobile", "realName", "birthday", "idNo",
                "typeStr", "statusStr", "lastVisit", "modifyDate", "createDate"};
        String[] titles = {"用户ID", "登录名", "昵称", "手机号", "实名名称", "出生日期", "身份证号码",
                "用户类型", "状态", "最后一次登陆日期", "最后一次修改日期", "注册日期"};
        String filename = "user" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
        String[] contents = {"导出条数：" + result.size(),
                "操作日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())};
        return new ModelAndView(new ExcelView(filename, null, properties, titles, null, null, result,
                contents), model);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user:huashanuser:manage")
    public String listQuery(@PathVariable Integer id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.getUserById(id));
        return "admin/user/view";
    }

    @RequestMapping(value = "/choose", method = RequestMethod.GET)
    public String chooseUserIndex(PageCriteria PageCriteria, ModelMap model) {
        return "admin/user/choose";
    }

    @RequestMapping(value = "/choose/json/{userType}", method = RequestMethod.GET)
    @ResponseBody
    public PageResult chooseUserData(@PathVariable String userType, PageCriteria pageCriteria, HttpServletRequest request, String mobile) {
        Map map = new HashMap();
        map.put("type", "");
        map.put("mobile", request.getParameter("mobile"));
        map.put("realName", "");
        PageResult<UserVo> userList = userService.findAllUserByPage(pageCriteria, map);
        return userList;
    }

    /**
     * 新增用户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @RequiresPermissions("user:list:add")
    public String createDispater(Model model) {
        return "admin/user/userForm";
    }

    /**
     * 新增用户
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @RequiresPermissions("user:list:add")
    @ResponseBody
    public Result createDo(RegistVo vo, Boolean flag, HttpServletRequest request) {

        //新增用户
        String mobile = vo.getMobile();
        String password = vo.getPassword();

//            User user = new User(mobile, password, vo.getType());
        User user = new User(vo.getUserType(), vo.getLoginName(), vo.getName(), vo.getPassword(), vo.getBirthday(), vo.getGender(), vo.getMobile(), vo.getCont());
//            if(vo.getType().equals(UserType.ENTERPRISE)){
        user.setLegalMobile(vo.getIpsMobile());
//            }
        PasswordHelper.encryptPassword(user, password);
        User user2 = null;
        try {
            user2 = userService.createUser(user, null, null);
            if (flag != null && flag) {
                //1.如果是个人用户实名认证
                if (vo.getUserType().equals(UserType.GENERAL)) {
                    UserIdentityVo uIVo = new UserIdentityVo();
                    uIVo.setUserId(user.getId());
                    uIVo.setType(vo.getUserType());
                    uIVo.setRealName(vo.getRealName());
                    uIVo.setIdNo(vo.getIdNo());
                    uIVo.setCorpName("");
                    uIVo.setCorpLicenseNo("");
                    uIVo.setIpsMobile(vo.getIpsMobile());
                    userService.addIdentityInfo(uIVo);
                } else {//2.如果是企业用户实名认证

                    String idNo = vo.getIdNo();
                    String realName = vo.getRealName();
                    Date birth = null;
                    GenderType gender = null;
                    // 18位身份证号码
                    if (com.klzan.core.util.StringUtils.length(idNo) == 18) {
                        //获取出生日期
                        birth = com.klzan.core.util.DateUtils.parseDate(com.klzan.core.util.StringUtils.substring(idNo, 6, 14), "yyyyMMdd");
                        // 获取性别
                        gender = Integer.parseInt(com.klzan.core.util.StringUtils.left(com.klzan.core.util.StringUtils.right(idNo, 2), 1)) % 2 == 0 ? GenderType.FEMALE : GenderType.MALE;
                    } else if (com.klzan.core.util.StringUtils.length(idNo) == 15) { // 15位身份证号码
                        // 获取出生日期
                        birth = com.klzan.core.util.DateUtils.parseDate("19" + com.klzan.core.util.StringUtils.substring(idNo, 6, 12), "yyyyMMdd");
                        // 获取性别
                        gender = Integer.parseInt(com.klzan.core.util.StringUtils.right(idNo, 1)) % 2 == 0 ? GenderType.FEMALE : GenderType.MALE;
                    }
                    user.setBirthday(birth);
                    user.setGender(gender);
                    user = userService.merge(user);

                    CorporationLegal corporationLegal = new CorporationLegal();
                    corporationLegal.setUserId(user.getId());
                    corporationLegal.setCorporationIdCard(vo.getIdNo());
                    corporationLegal.setCorporationName(vo.getRealName());
                    corporationLegal.setCorporationMobile(vo.getMobile());
                    corporationLegal = corporationLegalService.createCorporationLegal(corporationLegal);

                    Corporation corporation = new Corporation();
                    corporation.setLegalId(corporationLegal.getId());
                    corporationService.persist(corporation);
                }
            }
        } catch (Exception e) {
            logger.error("新增用户失败");
            e.printStackTrace();
            return Result.error("新增用户失败");
        }
        return Result.success();
    }

    /**
     * 修改用户跳转
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user:list:update")
    public String updateDispater(@PathVariable("id") Integer id, Model model) {
//        HuaShanUserVo vo = userService.findUserByIdWithMyBatis(id);
        User user = userService.get(id);
        model.addAttribute("user", user);
        if (user.getType().equals(UserType.GENERAL)) {
            UserInfo userInfo = userInfoService.getUserInfo(user.getId());
            if (userInfo != null) {
                model.addAttribute("userInfo", userInfo);
                model.addAttribute("realName", userInfo.getRealName());
                model.addAttribute("idNo", userInfo.getIdNo());
            }
        } else {
            CorporationLegal corporationLegal = corporationLegalService.findCorporationLegalByUserId(user.getId());
            if (corporationLegal != null) {
                model.addAttribute("corporationLegal", corporationLegal);
                model.addAttribute("realName", corporationLegal.getCorporationName());
                model.addAttribute("idNo", corporationLegal.getCorporationIdCard());
            }
        }

        model.addAttribute("action", "update");
        model.addAttribute("pk", id);
        return "admin/user/updateUserForm";
    }

    /**
     * 修改用户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @RequiresPermissions("user:list:update")
    @ResponseBody
    public Result updateDo(HttpServletRequest request, @PathVariable("id") Integer id, RegistVo vo, Model model) {

        try {
            User user = userService.get(id);
            user.setName(vo.getName());
            user.setMobile(vo.getMobile());
            user.setLegalMobile(vo.getIpsMobile());
            if (StringUtils.isNotBlank(vo.getPassword())) {
                PasswordHelper.encryptPassword(user, vo.getPassword());
            }
            user = userService.merge(user);

            UserInfo userInfo = userInfoService.getUserInfo(user.getId());
            if (!userInfo.hasIdentity() && StringUtils.isNotBlank(vo.getRealName()) && StringUtils.isNotBlank(vo.getIdNo())) {
                if (user.getType().equals(UserType.GENERAL)) { //1.如果是个人用户实名认证
                    UserIdentityVo uIVo = new UserIdentityVo();
                    uIVo.setUserId(user.getId());
                    uIVo.setType(user.getType());
                    uIVo.setRealName(vo.getRealName());
                    uIVo.setIdNo(vo.getIdNo());
                    uIVo.setCorpName("");
                    uIVo.setCorpLicenseNo("");
                    uIVo.setIpsMobile(user.getLegalMobile());
                    userService.addIdentityInfo(uIVo);
                } else { //2.如果是企业用户实名认证

                    String idNo = vo.getIdNo();
                    String realName = vo.getRealName();
                    Date birth = null;
                    GenderType gender = null;
                    // 18位身份证号码
                    if (com.klzan.core.util.StringUtils.length(idNo) == 18) {
                        //获取出生日期
                        birth = com.klzan.core.util.DateUtils.parseDate(com.klzan.core.util.StringUtils.substring(idNo, 6, 14), "yyyyMMdd");
                        // 获取性别
                        gender = Integer.parseInt(com.klzan.core.util.StringUtils.left(com.klzan.core.util.StringUtils.right(idNo, 2), 1)) % 2 == 0 ? GenderType.FEMALE : GenderType.MALE;
                    } else if (com.klzan.core.util.StringUtils.length(idNo) == 15) { // 15位身份证号码
                        // 获取出生日期
                        birth = com.klzan.core.util.DateUtils.parseDate("19" + com.klzan.core.util.StringUtils.substring(idNo, 6, 12), "yyyyMMdd");
                        // 获取性别
                        gender = Integer.parseInt(com.klzan.core.util.StringUtils.right(idNo, 1)) % 2 == 0 ? GenderType.FEMALE : GenderType.MALE;
                    }
                    user.setBirthday(birth);
                    user.setGender(gender);
                    user = userService.merge(user);

                    CorporationLegal corporationLegal = new CorporationLegal();
                    corporationLegal.setUserId(user.getId());
                    corporationLegal.setCorporationIdCard(vo.getIdNo());
                    corporationLegal.setCorporationName(vo.getRealName());
                    corporationLegal.setCorporationMobile(user.getMobile());
                    corporationLegalService.createCorporationLegal(corporationLegal);
                }
            }

            UserLog userLog = new UserLog(UserLogType.MODIFY, vo.getCont(), "", WebUtils.getRemoteIp(request), id);
            userLogService.persist(userLog);
        } catch (Exception e) {
            logger.error("修改用户失败");
            e.printStackTrace();
            return Result.error("修改用户失败");
        }


//        try {
////            userService.updateUserByIdWithMyBatis(operator.getLoginName(),vo,id);
////            //记录操作日志
//
//            model.addAttribute("action", "update");
//        }catch (Exception e){
//            return Result.error();
//        }
        return Result.success();
    }

    @RequestMapping("validataLoginName/{userType}/{id}")
    @ResponseBody
    public Boolean validataLoginName(String loginName, @PathVariable UserType userType, @PathVariable Integer id) {
//        UserType type = UserType.GENERAL;
//        if(userType!=null && userType){
//            type = UserType.ENTERPRISE;
//        }
        User user = userService.get(id);
        if (user.getType().equals(userType) && user.getLoginName().equals(loginName)) {
            return true;
        }
        return !(userService.isExistLoginName(loginName, userType));
    }

    @RequestMapping("validataMobile/{userType}/{id}")
    @ResponseBody
    public Boolean validataMobile(String mobile, @PathVariable UserType userType, @PathVariable Integer id) {
        User user = userService.get(id);
        if (user.getType().equals(userType) && user.getMobile().equals(mobile)) {
            return true;
        }
        return !(userService.isExistMobile(mobile, userType));
    }

    @RequestMapping("validataLoginName/{userType}")
    @ResponseBody
    public Boolean validataLoginName(String loginName, @PathVariable UserType userType) {
//        UserType type = UserType.GENERAL;
//        if(userType!=null && userType){
//            type = UserType.ENTERPRISE;
//        }
        return !(userService.isExistLoginName(loginName, userType));
    }

    @RequestMapping("validataMobile/{userType}")
    @ResponseBody
    public Boolean validataMobile(String mobile, @PathVariable UserType userType) {
//        UserType type = UserType.GENERAL;
//        if(userType!=null && userType){
//            type = UserType.ENTERPRISE;
//        }
        return !(userService.isExistMobile(mobile, userType));
    }

    @RequestMapping(value = "/checkIdNo/{userType}")
    @ResponseBody
    public Boolean checkIdNo(String idNo, @PathVariable UserType userType) {

        if (UserType.GENERAL.equals(userType)) {
            if (userInfoService.findByIdNo(userType.GENERAL, idNo).size() == 0) {
                return true;
            } else {
                return false;
            }
        }
        else {
//            CorporationLegal corporationLegal = corporationLegalService.findCorporationLegalByCardId(idNo);
//            if (corporationLegal == null) {
//                return true;
//            } else {
//                return false;
//            }
            return true;
        }

    }

}
