<div class="header-s">
    <div class="topbar" name="topd" id="topd">
        <div class="p-top"></div>

        <div class="inside">
            <a href="javascript:void(0)" class="weixin tel">客服热线：<em>${setting.basic.servicePhone}</em></a> |
            <a href="javascript:void(0)" class="weixin"> 关注我们
                <div class="r_box_b" style="left:-25px;">
                    <p class="spider" style="left:65px;"><i class="i1"></i><i class="i2"></i><i class="i3"></i><i
                            class="i4"></i><i class="i5"></i><i class="i6"></i></p>
                    <div class="boxinfo" style="width:120px; font-size:14px;">扫描关注微信<img
                            src="${ctx}/static/images/index/ewm.jpg" width="100"
                            height="100">
                    </div>
                </div>
            </a>

            <div class="bar_r">
                <div style="display:inline-block" class="appt">
                    <a href="${ctx}/appDown" target="_blank" class="weixin app">移动客户端</a>
                    <div class="r_box_b apps" style="left: 10px; display: none;">
                        <p class="spider">
                            <i class="i1"></i>
                            <i class="i2"></i>
                            <i class="i3"></i>
                            <i class="i4"></i>
                            <i class="i5"></i>
                            <i class="i6"></i>
                        </p>
                        <div class="boxinfo" style=" width:127px; height:125px;">
                            <img style="float:left;" width="106" height="106"
                                 src="${ctx}/static/images/appdown/appdown_ewm.png">
                        </div>
                    </div>
                </div>
                <a href="${ctx}/t/category/help" target="_blank" class="btn2" style="text-decoration: none;">帮助中心</a>|
                <a class="btn2" href="${ctx}/t/category/about" target="_blank" style="text-decoration: none;">关于我们</a>
            </div>
        </div>
    </div>
    <div class="header-t"></div>
    <!--header-->
    <div class="header">
        <div class="inside"><h1 class="logo"><a href="${setting.basic.siteUrl}">${setting.basic.siteName}</a></h1>
            <ul class="nav">

                [#if nav=="mall" || nav=='gift' || nav=='benefit' || nav=='signIn']
                    <li class="[#if nav=='mall']cur[/#if]"><a href="${ctx}/mall" >商城首页</a></li>
                    <li class="[#if nav=='gift']cur[/#if]"><a href="${ctx}/mall/gift" style="width: auto">礼品店</a></li>
                    <li class="[#if nav=='benefit']cur[/#if]"><a href="${ctx}/mall/benefit" >购实惠</a></li>
                    [#--<li class="[#if nav=='borrowing']cur[/#if]"><a href="#" >帮助中心</a></li>--]
                    [#--<li class="[#if nav=='borrowing']cur[/#if]"><a href="#" >赚积分</a></li>--]
                    <li class="[#if nav=='']cur[/#if]"><a href="${ctx}/uc/point" style="width: auto">我的积分[#if userPoint??]：${userPoint.point}[/#if]</a></li>
                [#else]
                    <li class="[#if nav=='index']cur[/#if]"><a href="${ctx}/">首页</a></li>
                    <li class="[#if nav=='investment']cur[/#if]">
                        <a href="${ctx}/investment" onclick="window.location.href='${ctx}/investment'">投资理财</a>
                        [#--<div class="r_box_b">--]
                            [#--<p class="spider"><i class="i1"></i><i class="i2"></i>--]
                                [#--<i class="i3"></i>--]
                                [#--<i class="i4"></i>--]
                                [#--<i class="i5"></i>--]
                                [#--<i class="i6"></i>--]
                            [#--</p>--]
                            [#--<div class="boxinfo" style="padding:10px 0px">--]
                                [#--<a href="${ctx}/investment?type=MORTGAGE">抵押贷</a>--]
                                [#--<a href="${ctx}/investment?type=GUARANTEE">质押贷</a>--]
                                [#--<a href="${ctx}/investment?type=CREDIT">车商贷</a>--]
                                [#--<a href="${ctx}/investment?type=TRANSFER">债权转让</a>--]
                            [#--</div>--]
                        [#--</div>--]
                    </li>
                    [#--<li class="[#if nav=='borrowing']cur[/#if]"><a href="${ctx}/borrowingApply">我要借款</a></li>--]
                    [#--<li class="[#if nav=='help']cur[/#if]"><a class="hasicon nav_icon2" href="${ctx}/article/help">新手指引</a></li>--]
                    [#--<li class="[#if nav=='mall']cur[/#if]"><a href="${ctx}/mall">积分商城</a></li>--]
                [/#if]

                <li style="width:130px;margin-left:30px;">
                [#if user??]

                    <div class="user-main">
                        <a class="my-granary" href="${ctx}/uc/index" style="width: auto">账户中心</a>
                        <div class="user-board">
                            <div class="user-info">
                                <div class="user-info-userimg">
                                    <img id="myAvatar" onerror="this.src=''"
                                         src="/static/images/safe/user-logo.png"
                                         art="user-Head">
                                    <div class="userimg-mask"></div>
                                </div>
                                <div class="user-info-ext">
                                    <h5>尊敬的${secrecy("mobile", user.name)}</h5>
                                    <p>欢迎回到${setting.basic.siteName}</p>
                                </div>
                            </div>
                            <div class="user-list-box">
                                <ul>
                                    <li>
                                        <a href="${ctx}/uc/index">
                                            <span class="list-item-left">可用余额</span>
                                            <span class="list-item-right" style="color: #e54;" id="myAmount">￥${assets}</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/uc/investment">
                                            <span class="list-item-left">我的投资</span>
                                            <span class="list-item-right  "></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/uc/security">
                                            <span class="list-item-left">账户安全</span>
                                            <span class="list-item-right invest-security" id="securityLevel">低</span>
                                        </a>
                                    </li>
                                    [#--<li>--]
                                        [#--<a href="">--]
                                            [#--<span class="list-item-left">消息中心</span>--]
                                            [#--<span class="list-item-right" style="padding-right: 0;"></span>--]
                                        [#--</a>--]
                                    [#--</li>--]
                                    <li>
                                        <a class="exit-login" href="${ctx}/logout">
                                            <span class="list-item-left">退出登录</span>
                                            <span class="list-item-right"></span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <i class="user-mask"></i>
                    </div>

                [#else]
                    <em class="login-box">
                        <a class="login" href="${ctx}/login">登录</a> |
                        <a class="register" href="${ctx}/regist" target="_blank">注册</a>
                    </em>
                [/#if]
                </li>
            </ul>
        </div>
    </div>
</div>