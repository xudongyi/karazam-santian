[@nestedstyle]
    [@css href="css/invite.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/user/user.invite.js" /]
    [@js src="js/util/jquery.share.min.js" /]
    [@js src="lib/zeroclipboard/ZeroClipboard.min.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心" module="referral" nav="myReferrer" currentUser=kuser user=kuser]

<div class="chongzhiTop">
    <h3>邀请奖励</h3>
</div>
<div class="banner">
    <img src="${ctx}/static/images/index/inviteBanner.png">
</div>
<div class="inviteTitle">
    <i></i>邀请方式
</div>
<div class="mode clearfix">
    <h3>01、将您的推荐人专属链接发给您的好友</h3>
    <div class="modeInfo">
        <input type="text" class="fl inputTxt" id="inputTxt"
               value="${setting.basic.siteUrl}/regist?inviteCode=${inviteCode}">

        <a href="javascript:void(0);" class="fl center_btn_b" id="copyBtn" data-clipboard-target="inputTxt">复制</a>
        <div class="zclip" id="zclip-ZeroClipboardMovie_1"
             style="position: absolute; left: 1103px; top: 474px; width: 100px; height: 34px; z-index: 99;">
            <embed id="ZeroClipboardMovie_1"  loop="false"
                   menu="false"
                   quality="best" bgcolor="#ffffff" width="100" height="34" name="ZeroClipboardMovie_1" align="middle"
                   allowscriptaccess="always" allowfullscreen="false" type="application/x-shockwave-flash"
                   pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="id=1&amp;width=100&amp;height=34"
                   wmode="transparent">
        </div>
    </div>
</div>
<div class="mode clearfix">
    <h3>02、分享至</h3>
    <div class="modeInfo">
        <ul>
            <li class="row">
                <div id="share" data-weibo-url="${setting.basic.siteUrl}/regist?inviteCode=${inviteCode}"
                     data-qzone-url="${setting.basic.siteUrl}/regist?inviteCode=${inviteCode}"
                     data-url="${setting.basic.siteUrl}/regist?inviteCode=${inviteCode}"
                     data-title="送你588元新手红包！快来领取吧！${setting.basic.siteName}，靠谱理财神器！新手领588元红包，专享11.2%年化新手标！快跟我一起赚钱吧！"
                     data-image="http://www.isantian.com/static/images/ptdlogo.png"
                     data-description="送你588元新手红包！快来领取吧！${setting.basic.siteName}，靠谱理财神器！新手领588元红包，专享11.2%年化新手标！快跟我一起赚钱吧！" class="share-component social-share"></div>
            </li>

        </ul>
    </div>
</div>
<div class="inviteTitle">
    <i></i>投资奖励流程
</div>
<div class="process clearfix">
    <dl>
        <dd class="img1"></dd>
        <dt>
        <h3>注册邀请</h3>
        <p>邀请用户通过您的手机或网页上</p>
        <p>的专属链接注册成功</p>
        </dt>
    </dl>
    <i class="fa fa-angle-right"></i>
    <dl>
        <dd class="img2"></dd>
        <dt>
        <h3>好友投资</h3>
        <p>邀请用户按照规则进行投资</p>
        </dt>
    </dl>
    <i class="fa fa-angle-right"></i>
    <dl>
        <dd class="img3"></dd>
        <dt>
        <h3>邀请奖励</h3>
        <p>每位被推荐者完成任务就有奖励</p>
        </dt>
    </dl>
</div>
<div class="inviteTitle ">
    <i></i>红包攻略
</div>
<div class="inviteTitle">
    <i></i>邀请记录
</div>
<div class="inviteRecord">
    <div>
        <span>我的奖励<i class="orangeColor DinFont" id="jiangli">${referralFee}</i>元</span>
        <span class="midd">累积推荐<i class="orangeColor DinFont" id="leiji">${referralCount}</i>人</span>
        <span>有效推荐<i class="orangeColor DinFont" id="youxiao">${contEffect}</i>人</span>
    </div>
    <div class="inviteList">
        <ul class="inviteListTitle" id="inviteListTitle">
            <li class="phone fl">推荐账户</li>
            <li class="time fl">注册时间</li>
            <li class="boolean fl">是否符合奖励</li>
        </ul>
        <ul class="inviteListInfo" id="inviteListInfo">
            [#list referrals as referral]
                <li class="statusClass">
                    <span class="phone">${reUsers[referral_index].name}</span>
                    <span class="time">${referral.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                [#if referral.available ]
                    <span class="boolean">是</span>
                [#else]
                    <span class="boolean">否</span>
                </li>
                [/#if]
            [/#list]
            [#if referrals?size == 0]
                <tr>
                    <td colspan="8">没有记录！</td>
                </tr>
            [/#if]
        </ul>
        <div class="pageList"></div>
    </div>
</div>
[/@insert]
