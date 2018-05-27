package com.klzan.p2p.service.message;

import com.klzan.core.exception.SystemException;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.message.MessagePushDao;
import com.klzan.p2p.dao.message.MessagePushUserDao;
import com.klzan.p2p.dao.userdevice.UserDeviceDao;
import com.klzan.p2p.enums.MessagePushType;
import com.klzan.p2p.enums.MessageReciveType;
import com.klzan.p2p.event.MsgPushEvent;
import com.klzan.p2p.event.MsgPushToPersonEvent;
import com.klzan.p2p.event.message.MsgPushMessage;
import com.klzan.p2p.event.message.MsgPushToPersonMessage;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.BasicSetting;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.message.MessagePushVo;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by suhao Date: 2017/1/10 Time: 16:44
 *
 * @version: 1.0
 */
@Service
public class MessagePushService extends BaseService<MessagePush> {

    @Inject
    private MessagePushDao messagePushDao;
    @Inject
    private MessagePushUserDao messagePushUserDao;
    @Inject
    private UserDeviceDao userDeviceDao;
    @Inject
    private UserService userService;
    @Inject
    private SettingUtils setting;
    @Inject
    private ApplicationContext applicationContext;

    public void pushMessageToPerson(MessagePushType type, String registrationId, String notificationTitle, String msgTitle, String msgContent, String extrasparam) {
        UserDevice userDevice = userDeviceDao.findByRegistrationId(registrationId);
        if (null == userDevice) {
            throw new SystemException(String.format("[%s]消息推送失败，用户registrationId[%s]", type.getDisplayName(), registrationId));
        }
        MessagePush messagePush = new MessagePush(msgTitle, msgContent, type, notificationTitle, extrasparam, MessageReciveType.user);
        messagePushDao.persist(messagePush);

        MessagePushUser messagePushUser = new MessagePushUser();
        messagePushUser.setUserId(userDevice.getUserId());
        messagePushUser.setMessageId(messagePush.getId());
        messagePushUserDao.persist(messagePushUser);

        applicationContext.publishEvent(new MsgPushToPersonEvent(applicationContext, new MsgPushToPersonMessage(type, registrationId, notificationTitle, msgTitle, msgContent, extrasparam)));
    }

    public void pushMessageToAllAndroid(MessagePushType type, String notificationTitle, String msgTitle, String msgContent, String extrasparam) {
        List<Integer> userIds = userDeviceDao.findUserByOsType(UserDevice.ANDROID);
        if (userIds.isEmpty()) {
            logger.error(String.format("[%s]消息批量推送失败，推送目标all-[%s]", type.getDisplayName(), UserDevice.ANDROID));
            return;
        }
        MessagePush messagePush = new MessagePush(msgTitle, msgContent, type, notificationTitle, extrasparam, MessageReciveType.android);
        messagePushDao.persist(messagePush);
        applicationContext.publishEvent(new MsgPushEvent(applicationContext, new MsgPushMessage(MessageReciveType.android, notificationTitle, msgTitle, msgContent, extrasparam)));
    }

    public void pushMessageToAll(MessagePushType type, String notificationTitle, String msgTitle, String msgContent, String extrasparam) {
        List<Integer> userIds = userDeviceDao.findAllUser();
        if (userIds.isEmpty()) {
            logger.error("消息批量推送失败，推送目标all");
            return;
        }
        MessagePush messagePush = new MessagePush(msgTitle, msgContent, type, notificationTitle, extrasparam, MessageReciveType.all);
        messagePushDao.persist(messagePush);
        applicationContext.publishEvent(new MsgPushEvent(applicationContext, new MsgPushMessage(MessageReciveType.all, notificationTitle, msgTitle, msgContent, extrasparam)));
    }

    /**
     * 新标通知
     * @param borrowing
     */
    public void newBorrowingSuccPush(Borrowing borrowing) {
        BasicSetting basic = setting.getBasic();
        String msg = String.format("【%s】投资人您好，%s新标上线，金额%s，年化收益%s，期限%s，%s元起投。", basic.getSiteName(),
                DateUtils.format(borrowing.getInvestmentStartDate(), DateUtils.DATE_PATTERN_yyyyMMddHHmm),
                borrowing.getAmount().toString(),
                borrowing.getRealInterestRate().toString(),
                borrowing.getPeriod()+borrowing.getPeriodUnit().getDisplayName(),
                borrowing.getInvestmentMinimum());
        pushMessageToAll (MessagePushType.new_project, "新标上线", borrowing.getTitle()+"新标上线", msg, "");
    }

    /**
     * 满标出借
     * @param borrowing
     */
    public void lendSuccPush(Integer userId, Borrowing borrowing) {
        BasicSetting basic = setting.getBasic();
        UserDevice userDevice = userDeviceDao.findByUserId(userId);
        if (null == userDevice) {
            return;
        }
        String msg = String.format("【%s】投资人您好，<<%s>>项目已满标，%s起计息，%s。", basic.getSiteName(),
                borrowing.getTitle(),
                DateUtils.format(new Date(), DateUtils.DATE_PATTERN_yyyyMMddHHmm),
                borrowing.getRepaymentMethod().getDisplayName());
        pushMessageToPerson(MessagePushType.userown, userDevice.getRegistrationId(), msg, "满标出借", msg, "");
    }

    /**
     * 投资成功
     * @param userId
     * @param amount
     */
    public void investmentSuccPush(Integer userId, BigDecimal amount, Borrowing borrowing) {
        BasicSetting basic = setting.getBasic();
        UserDevice userDevice = userDeviceDao.findByUserId(userId);
        if (null == userDevice) {
            return;
        }
        User user = userService.get(userId);
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        String msg = String.format("【%s】%s，您投资的%s已成功，金额%s元", basic.getSiteName(),
                user.getName(),
                borrowing.getTitle(),
                decimalFormat.format(amount));
        pushMessageToPerson(MessagePushType.userown, userDevice.getRegistrationId(), msg, "投资成功", msg, "");
    }

    /**
     * 投资退款
     * @param userId
     * @param amount
     */
    public void investmentRefundSuccPush(Integer userId, BigDecimal amount) {
        UserDevice userDevice = userDeviceDao.findByUserId(userId);
        if (null == userDevice) {
            return;
        }
        String msg = "您于%s退款%s元（项目终止）,请您继续关注智链金融";
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        Date now = new Date();
        msg = String.format(msg, DateUtils.format(now, DateUtils.YYYY_MM_DD_HH_MM_SS), decimalFormat.format(amount));
        pushMessageToPerson(MessagePushType.userown, userDevice.getRegistrationId(), msg, "退款", msg, "");
    }

    /**
     * 回款成功
     * @param userId
     */
    public void recoverySuccPush(Integer userId, BigDecimal amount, String title, Integer period, Date paidDate) {
        BasicSetting basic = setting.getBasic();
        UserDevice userDevice = userDeviceDao.findByUserId(userId);
        if (null == userDevice) {
            return;
        }
        String msg;
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        if (null == period) {
            msg = "【%s】您投资的%s项目还款于%s回款到您在智链金融的余额账户，回款金额为%s请及时登录账户查看。";
            msg = String.format(msg, basic.getSiteName(),
                    title,
                    DateUtils.format(paidDate, DateUtils.YYYY_MM_DD_HH_MM_SS),
                    decimalFormat.format(amount));
        } else {
            msg = "【%s】您投资的%s项目第%s期还款于%s回款到您在智链金融的余额账户，回款金额为%s请及时登录账户查看。";
            msg = String.format(msg, basic.getSiteName(),
                    title,
                    period.toString(),
                    DateUtils.format(paidDate, DateUtils.YYYY_MM_DD_HH_MM_SS),
                    decimalFormat.format(amount));
        }
        pushMessageToPerson(MessagePushType.userown, userDevice.getRegistrationId(), msg, "回款通知", msg, "");
    }

    /**
     * 回款成功
     * @param userId
     */
    public void recoverySuccPush(Integer userId, BigDecimal amount, BigDecimal fee, String title, Integer period, Date paidDate) {
        BasicSetting basic = setting.getBasic();
        UserDevice userDevice = userDeviceDao.findByUserId(userId);
        if (null == userDevice) {
            return;
        }
        String msg;
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        if (null == period) {
            msg = "【%s】您投资的%s项目还款于%s回款到您在智链金融的余额账户，回款金额为%s服务费为%s请及时登录账户查看。";
            msg = String.format(msg, basic.getSiteName(),
                    title,
                    DateUtils.format(paidDate, DateUtils.YYYY_MM_DD_HH_MM_SS),
                    decimalFormat.format(amount),
                    decimalFormat.format(fee));
        } else {
            msg = "【%s】您投资的%s项目第%s期还款于%s回款到您在智链金融的余额账户，回款金额为%s服务费为%s请及时登录账户查看。";
            msg = String.format(msg, basic.getSiteName(),
                    title,
                    period.toString(),
                    DateUtils.format(paidDate, DateUtils.YYYY_MM_DD_HH_MM_SS),
                    decimalFormat.format(amount),
                    decimalFormat.format(fee));
        }
        pushMessageToPerson(MessagePushType.userown, userDevice.getRegistrationId(), msg, "回款通知", msg, "");
    }

    /**
     * 提现申请
     * @param userId
     * @param amount
     */
    public void withdrawApplyPush(Integer userId, BigDecimal amount) {
        BasicSetting basic = setting.getBasic();
        UserDevice userDevice = userDeviceDao.findByUserId(userId);
        if (null == userDevice) {
            return;
        }
        User user = userService.get(userId);
        String msg = "【%s】%s，您的提现正在审核，金额%s元。";
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        msg = String.format(msg, basic.getSiteName(), user.getName(), decimalFormat.format(amount));
        pushMessageToPerson(MessagePushType.userown, userDevice.getRegistrationId(), msg, "提现通知", msg, "");
    }

    /**
     * 提现审核通过
     * @param userId
     * @param amount
     */
    public void withdrawAuditSuccPush(Integer userId, BigDecimal amount) {
        BasicSetting basic = setting.getBasic();
        UserDevice userDevice = userDeviceDao.findByUserId(userId);
        if (null == userDevice) {
            return;
        }
        User user = userService.get(userId);
        String msg = "【%s】%s，您的提现申请审核已通过，金额%s元，预计到账时间次日12：00左右，如遇节假日顺延，请注意查收银行卡信息。";
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        msg = String.format(msg, basic.getSiteName(), user.getName(), decimalFormat.format(amount));
        pushMessageToPerson(MessagePushType.userown, userDevice.getRegistrationId(), msg, "提现通知", msg, "");
    }

    /**
     * 提现审核不通过
     * @param userId
     * @param amount
     */
    public void withdrawAuditFailPush(Integer userId, BigDecimal amount) {
        BasicSetting basic = setting.getBasic();
        UserDevice userDevice = userDeviceDao.findByUserId(userId);
        if (null == userDevice) {
            return;
        }
        User user = userService.get(userId);
        String msg = "【%s】%s，您的提现申请审核未通过，金额%s元，详询0971-8011979。";
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        msg = String.format(msg, basic.getSiteName(), user.getName(), decimalFormat.format(amount));
        pushMessageToPerson(MessagePushType.userown, userDevice.getRegistrationId(), msg, "提现通知", msg, "");
    }

    /**
     * 提现成功
     * @param userId
     * @param amount
     */
    public void withdrawSuccPush(Integer userId, BigDecimal amount) {
        UserDevice userDevice = userDeviceDao.findByUserId(userId);
        if (null == userDevice) {
            return;
        }
        String msg = "您有一笔提现于%s提交处理提现%s元,请您继续关注拍拖贷";
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        Date now = new Date();
        msg = String.format(msg, DateUtils.format(now, DateUtils.YYYY_MM_DD_HH_MM_SS), decimalFormat.format(amount));
        pushMessageToPerson(MessagePushType.userown, userDevice.getRegistrationId(), msg, "提现通知", msg, "");
    }

    /**
     * 充值成功
     * @param userId
     * @param amount
     */
    public void rechargeSuccPush(Integer userId, BigDecimal amount) {
        BasicSetting basic = setting.getBasic();
        UserDevice userDevice = userDeviceDao.findByUserId(userId);
        if (null == userDevice) {
            return;
        }
        User user = userService.get(userId);
        String msg = "【%s】%s，您的充值已成功，金额%s元。";
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        msg = String.format(msg, basic.getSiteName(), user.getName(), decimalFormat.format(amount));
        pushMessageToPerson(MessagePushType.userown, userDevice.getRegistrationId(), msg, "充值成功", msg, "");
    }

    /**
     * 推荐费结算
     * @param userId
     * @param borrowing
     */
    public void reffralSettleSuccPush(Integer userId, Borrowing borrowing) {
        BasicSetting basic = setting.getBasic();
        UserDevice userDevice = userDeviceDao.findByUserId(userId);
        if (null == userDevice) {
            return;
        }
        String msg = "【%s】投资人您好，%s项目已满标，推荐奖励已结算至您的智链金融余额账户。";
        msg = String.format(msg, basic.getSiteName(),
                borrowing.getTitle());
        pushMessageToPerson(MessagePushType.userown, userDevice.getRegistrationId(), msg, "推荐结算", msg, "");
    }

    public MessagePush findLatestNoticeByType(MessagePushType type) {
        return messagePushDao.findByType(type);
    }

    public MessagePush findLatestNoticeByType(MessagePushType type, Integer userId) {
        return messagePushDao.findByType(type, userId);
    }

    public PageResult<MessagePush> findListByType(PageCriteria criteria, MessagePushType type) {
        return messagePushDao.findPageByType(criteria, type);
    }

    public PageResult<MessagePush> findListByType(PageCriteria criteria,Integer userId,MessagePushType... types) {
        return messagePushDao.findPageByType(criteria, userId, types);
    }
    public PageResult<MessagePushVo> findPushMessagePage(PageCriteria criteria) {
        return myDaoSupport.findPage("messagepush.findPage", null, criteria);
    }
}
