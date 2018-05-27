[@nestedstyle]
    <link rel="stylesheet" href="${ctx}/static/mall/base-51daa35356.css"/>
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css"/>
    <link rel="stylesheet" href="${ctx}/static/mall/signIn.css"/>
[/@nestedstyle]
[@nestedscript]
    [@js src= "lib/layui/lay/dest/layui.all.js" /]
    [@js src="${ctx}/static/mall/js/signin.js" /]
    [#--<script src="${ctx}/static/mall/signIn.js" type="text/javascript"></script>--]
[/@nestedscript]
[@insert template="layout/sign_in_layout" title="用户中心-我的积分" memberContentId="zichan-all" module="signIn" nav = "signIn" currentUser=kuser user=kuser]

<div class="wrapper ff-layout">
    <input type="hidden" id="serverTime" value="1497179184354">
    <div id="signIn">
        <div class="signInAlert">

            <div id="signInBox" class="sign-in" style="display: block;">
                <div class="shining-yellow"></div>
                <div class="sign-get prize" style="display: block;">
                    <div class="close"></div>
                    <div class="title"></div>
                    <div class="prize-img">
                        <img src="${ctx}/static/mall/images/signin/prize0.png" alt="">
                        <p class="prize-name" id="signInRes">
                            [#--<span class="value">3</span><span class="type">积分</span>+<span class="value">3</span><span class="type">元优惠券</span>--]
                        </p>
                    </div>
                    <div class="explain">本周周末投资即可使用;<br>详细使用规则可在"<a target="_blank" href="${ctx}/uc/">个人中心</a>"-"<a target="_blank" href="${ctx}/uc/userCoupon">我的卡券</a>"中查看</div>
                    <div class="signProgress">
                        <div class="bar"></div>
                        <ul class="dot">

                        </ul>
                    </div>
                    <a href="${ctx}/uc/point" target="_blank" class="button"> </a>
                    <div class="share">
                        <img src="${ctx}/static/mall/images/signin/icon_wechat.png" alt="">
                        <p> 通过移动端分享可获得5%加息券哦~ <a href="javascript:;">立即分享
                            <span class="ewm"><img src="" alt="">
                                    <span>进入微信，"扫一扫"左侧二维码，即刻分享到朋友圈或朋友，可再获得一张5%加息券，惊喜多多，赚足收益！</span>
                                </span>
                        </a></p>
                    </div>
                </div>
                <div class="no-prize prize">
                    <div class="close"></div>
                    <div class="title"></div>
                    <div class="prize-img" style="top: 140px;">
                        <img src="${ctx}/static/mall/images/signin/prize_no.png" alt="">
                    </div>
                    <div style="color: #fff;font-size: 18px;position: absolute;top: 330px;left: 110px;">
                        别灰心,还有机会哦~
                    </div>
                    <div class="button">
                    </div>
                </div>
            </div>

        </div>
        <div class="bg-head1">
            <div class="title">
                <h1>每日签到</h1>
                <p>签到赢取即时加息、积分、红包等奖品</p>
                <p>连续7天签到更享神秘惊喜</p>
            </div>
            <div class="shining"></div>
            <div class="btn-group" id="todaySignIn">
                [#if userPoint.todaySignIn]
                    <a href="javascript:;" class="signBtn1"><img src="${ctx}/static/mall/images/signin/btn2.png" alt=""></a>
                [#else ]
                    <a href="javascript:;" id="signInBtn" class="signBtn1"><img src="${ctx}/static/mall/images/signin/btn3.png" alt=""></a>
                [/#if]
            </div>
            <div class="tree"></div>
        </div>
        <div class="bg1 bg-body"></div>
        <div class="bg2 bg-body">
            <div class="calendar">
                <div class="title">
                    已连续签到
                    <span class="color-red">${userPoint.conSignInCountCal}</span>天，再连续签到
                    <span class="color-red">${7 - (userPoint.conSignInCountCal%7)?int}</span>天可获得1棵钻石树抽奖机会
                </div>
                <div class="content">
                    <div class="content-left">
                        <table class="calendar-body">
                            <thead>
                            <tr>
                                <th>星期日</th>
                                <th>星期一</th>
                                <th>星期二</th>
                                <th>星期三</th>
                                <th>星期四</th>
                                <th>星期五</th>
                                <th>星期六</th>
                            </tr>
                            </thead>
                            <tbody>
                                [#list recordsList as records]
                                    <tr>
                                        [#list records as record]
                                            <td><span class="${record.classStr}">${record.value}[#--[#if record.value==0]${record.value}[#else]${record.value}[/#if]--]</span></td>
                                        [/#list]
                                    </tr>
                                [/#list]
                            </tbody>
                        </table>
                    </div>
                    <div class="content-right">
                        <ul class="tab">
                            <li data-type="l0" class="active">中奖纪录</li>
                            <li data-type="l1">我的奖励</li>
                        </ul>
                        <table class="list l0" style="display: block">
                            <thead>
                            <tr>
                                <th>用户</th>
                                <th>时间</th>
                                <th>奖励</th>
                            </tr>
                            </thead>
                            <tbody>
                                [#list records as record]
                                <tr>
                                    <td>${record.username}</td>
                                    <td>${record.createDate}</td>
                                    <td class="color-red">${record.title}</td>
                                </tr>
                                [/#list]
                            </tbody>
                        </table>
                        <div [#--style="overflow: scroll"--]>
                            <table class="list l1" style="display: none;">
                                <thead>
                                    <tr>
                                        <th>时间</th>
                                        <th>奖励</th>
                                    </tr>
                                </thead>
                                <tbody style="overflow: scroll;">
                                    [#list myRecords?sort_by("createDate")?reverse as record]
                                        <tr>
                                            <td>${record.createDate}</td>
                                            <td class="color-red">${record.point}</td>
                                        </tr>
                                    [/#list]
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="bg3 bg-body">
            <div class="rule">
                <p>1.用户每日进入活动页面通过点击“签到”按钮完成签到，并抽奖一次；</p>
                <p>2.每日“签到”后第一次抽奖，若未抽中奖品，可参与理财知识竞答，答题正确即可获得一次额外抽奖机会；</p>
                <p>3.“签到”成功即可参与当日抽奖，将有机会获得最高达0.6%年化收益的即时加息，或1-10不等的积分奖励；</p>
                <p>4.周末参与“签到”抽奖，将有机会获得5元周末签到红包，详细使用规则可进入"我的卡券"查看；</p>
                <p>5.“签到”中的即时加息奖励可为历史中最近一笔定期投资（不含债权转让及网贷产品）进行加息，当日奖励收益将在2个工作日内返现至用户账户，若历史最近一笔定期投资金额低于400元则无法获得返现奖励；</p>
                <p>6.连续7天“签到”并分享活动至微信好友或朋友圈，将获赠1张5%加息券，加息期限10天，有效期3天，下次投资可使用；</p>
                <p>7.如有任何疑问，欢迎致电客服中心*****（工作时间9:00-21:00）。</p>
                <p></p>
            </div>
        </div>

    </div>
</div>


[/@insert]

