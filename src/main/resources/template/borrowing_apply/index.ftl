[@nestedstyle]
    [@css href="css/cubeportfolio.min.css" /]
    [@css href="css/borrowing_apply/index.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/borrowing_apply/index.js" /]
[/@nestedscript]
[@insert template="layout/invest_layout" title="我要借款" nav="borrowing" user=kuser]
<div id="preBorrow">
    <div id="preBorrow-project">
        <div id="preBorrow-project-head" class="detail-banner0" style="display: block;"></div>
        [#--<div id="preBorrow-project-nav"--]
             [#--style="filter: progid:DXImageTransform.Microsoft.Gradient(startColorstr=#11ffffff,endColorstr=#11ffffff);">--]
            [#--<div id="preBorrow-project-nav-cont" class="layout">--]
                [#--<ul style="padding-left: 0">--]
                    [#--<li class=""><a href="#project=0&amp;type=0">车抵贷<span></span></a></li>--]
                    [#--<li class=""><a href="#project=1&amp;type=2">秒速贷<span></span></a></li>--]
                    [#--<li class=""><a href="#project=2&amp;type=3">拍易融<span></span></a></li>--]
                    [#--<li class=""><a href="#project=3&amp;type=1">房拖贷<span></span></a></li>--]
                    [#--<li class=""><a href="#project=2&amp;type=2">出行派<span></span></a></li>--]
                    [#--<li class=""><a href="#project=3&amp;type=3">车分期<span></span></a></li>--]
                [#--</ul>--]
            [#--</div>--]
        [#--</div>--]
        <div id="preBorrow-project-content">
            <div class="layout">
                <div id="preBorrow-project-content-info" class="detail-info0" style="display: block;"></div>
                <div id="preBorrow-project-content-form">
                    <div class="carWrite">
                        <div class="errorTip clearfix" style="display: none;"><i class="fa fa-times"></i><span>请填写正确信息</span></div>
                        <input type="hidden" name="project" value="0">
                        <ol>
                            <li class="clearfix">
                                [#if user??]
                                    <i class="iconfont fl fa fa-money"></i>
                                    <input class="fl" name="amount" type="text" id="amount" placeholder="期望贷款金额">元
                                    <input class="fl" type="hidden" id="userId" name="userId" value="${user.id}">
                                [#else ]
                                    <i class="iconfont fl fa fa-user-o"></i>
                                    <input class="fl" name="name" type="text" id="carName" placeholder="您的姓名">
                                [/#if]
                            </li>
                            <li class="clearfix">
                                [#if user??]
                                    <i class="iconfont fl fa fa-line-chart"></i>
                                    <input class="fl" name="deadline" type="text" id="deadline"  mobile="${user.mobile}" placeholder="期望期限">天
                                [#else ]
                                    <i class="iconfont fl fa fa-phone"></i>
                                    <input class="fl" name="phone" type="text" id="carTel" placeholder="联系电话">
                                [/#if]
                            </li>
                            <li class="clearfix">
                                [#if user??]
                                    <i class="iconfont fl fa fa-percent"></i>
                                    <input class="fl" name="rate" type="text" id="rate" placeholder="期望利率">%
                                [#else ]
                                    <i class="iconfont fl fa fa-intersex"></i>
                                    <span class="fl form-sex">性别：</span>
                                    <div class="checkboxGroup">
                                        <div class="checkbox">
                                            <div><i></i></div>
                                            <span>先生</span></div>
                                        <div class="checkbox">
                                            <div><i></i></div>
                                            <span>女士</span></div>
                                    </div>
                                    <input type="hidden" id="carSex" name="sex">
                                [/#if]
                            </li>
                            <li class="clearfix">
                                <i class="iconfont fl fa fa-navicon"></i>
                                <input class="fl" type="text" name="code"
                                       id="carCode"
                                       placeholder="验证码">
                                <img class="fl"
                                     src=""
                                     id="formImgCode"
                                     alt="">
                            </li>
                            <li class="clearfix">
                                <i class="iconfont fl fa fa-building-o"></i>
                                <input class="fl" type="text" name="messageCode"
                                       id="messageCode"
                                       placeholder="短信验证码">
                                <a class="fl sendMessageCode" id="sendMessageCode" href="javascript:volid(0);" onclick="sendMsg()" style="vertical-align: top">获取验证码</a>
                            </li>
                        </ol>
                        <button class="confirm">极速申请</button>
                        <p id="preBorrow-project-content-ifQue">如有问题请电话咨询拍拖贷客服</p>
                        <p id="preBorrow-project-content-hotLine">${setting.basic.servicePhone}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
[/@insert]