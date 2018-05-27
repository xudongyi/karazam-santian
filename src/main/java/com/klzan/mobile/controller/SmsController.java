package com.klzan.mobile.controller;

import com.klzan.core.Result;
import com.klzan.core.util.DateUtils;
import com.klzan.mobile.vo.SendSmsVo;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.service.sms.SmsService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

/**
 * 短信
 * @version: 1.0
 */
@RestController
@RequestMapping("/mobile/sms")
public class SmsController extends BaseController {

    @Autowired
    private SmsService smsService;

    /**
     * 发送短信
     * @param sendSmsVo
     * @return
     */
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @ResponseBody
    public Result sendSms(@RequestBody SendSmsVo sendSmsVo) {
        String mobile = sendSmsVo.getMobile();
        SmsType smsType = sendSmsVo.getSmsType();
        if (StringUtils.isBlank(mobile)) {
            return Result.error("手机号不能为空");
        }
//        String smsTypeStr = sendSmsVo.getSmsType();
//        try {
//            smsType = SmsType.valueOf(smsTypeStr);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            return Result.success("短信类型有误");
//        }
        try {
            Map<String, Object> model = new HashedMap();
            Date expire = DateUtils.addSeconds(new Date(), 600);
            model.put("expire", expire);
            smsService.sendValidate(mobile, model, smsType);
            return Result.success("短信发送成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error("短信发送失败");
        }
    }

    /**
     * 验证短信
     * @param sendSmsVo
     * @return
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public Result verify(@RequestBody SendSmsVo sendSmsVo) {
        String mobile = sendSmsVo.getMobile();
        SmsType smsType = sendSmsVo.getSmsType();
        if (StringUtils.isBlank(mobile)) {
            return Result.error("手机号不能为空");
        }
        if (StringUtils.isBlank(sendSmsVo.getSmsCode())) {
            return Result.error("验证码不能为空");
        }
//        String smsTypeStr = sendSmsVo.getSmsType();
//        try {
//            smsType = SmsType.valueOf(smsTypeStr);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            return Result.success("短信类型有误");
//        }
        try {
            Boolean validate = smsService.validate(sendSmsVo.getMobile(), sendSmsVo.getSmsCode(), smsType);
            return validate ? Result.success("验证成功") : Result.error("验证失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("验证失败");
        }
    }
}
