[@nestedstyle]
    [@css href="lib/flexslider/flexslider.css" /]
    [@css href="css/build.css" /]
[/@nestedstyle]
[@nestedscript]
    <script>
        var modules = "${modules}", exponent = "${exponent}";
        var nowTime = ${setting.now.getTime() / 1000};
    </script>
    [@js src="lib/flexslider/jquery.flexslider-min.js" /]
    [@js src="lib/flexslider/flexslider.config.js" /]
    [@js src="lib/swiper/swiper.min.js" /]
    [@js src="lib/swiper/swiperpage.js" /]
    <script>
        var cont=0;
        $("#viewPassWord").click(function () {

            cont++;
            console.log(cont);
            if(cont==1){
                $("#viewPassWord").removeClass("login-eye").addClass("login-eye-close");
                $("#password").attr("type","");
            }else{
                $("#viewPassWord").removeClass("login-eye-close").addClass("login-eye");
                $("#password").attr("type","password");
                cont=0;
            }

        })
        $("#show_total_amount").click(function(){
            if ($("#show_total_amount").hasClass("no")){
                $(this).removeClass('no');
                $("#show_total_amount_span").html('￥'+${assets!'0'});
                $("#show_two").html('&nbsp;'+ ${yesterDayInAmount} +'元');
                $("#show_three").next('span').show();
            }else {
                $(this).toggleClass('no');
                $("#show_total_amount_span").html("&nbsp;&nbsp;***");
                $("#show_two,#show_three").html('&nbsp;&nbsp;***');
                $("#show_three").next('span').hide();
            }

        })
    </script>
[/@nestedscript]
[@insert template="layout/index_layout" title="${setting.basic.siteName}" user=kuser]
<div class="banner">
    <div class="switchAlpha">
        <ul class="list">
            [#list banners as banner]
                <li class="pic00  current "
                    style="display: list-item; z-index: 1; opacity: 0; background-image: url(${dfsUrl}/${banner.path}); background-color: rgb(0, 132, 255);"
                    bgimg="url(${dfsUrl}/${banner.path})">
                    <a href="${banner.url}" target="_blank">
                        <div class="txtbg inside"></div>
                        <div class="txt"></div>
                    </a>
                </li>
            [/#list]
        </ul>

        <div class="dot">
            <div class="dot-l">
                [#list banners as banner]
                    <a class="" id="first_dot"></a>
                [/#list]
            </div>
        </div>
    </div>

    <div class="guide" style="z-index:2">
        <div class="welcome">
            <div class="bg"></div>
            <div class="info" style="padding:0;">
                [#if user??]
                    <input hidden id="userId" value="${user.id}">
                [#else ]
                    <input hidden id="userId" value="">
                [/#if]
                [#if user??]
                    <!--登录后-->
                    <div class="login_box" id="logined_box" style="">
                        <h2>${setting.basic.siteName}欢迎您, <a style="color:#fff" href="${ctx}/uc/order">${secrecy("mobile", user.name)}</a>
                        </h2>
                        <p class="total">
                            总资产<i id="show_total_amount"></i><br><span id="show_total_amount_span">￥${assets}</span></p>
                        <a href="${ctx}/uc/recovery">
                            <div class="zs"
                                 style=" position:absolute; margin-left:20px; height:70px; padding:10px 0px;"></div>
                            <p style="margin-left:40px; text-align:left; line-height:30px; margin-top:5px;">昨日收益<span
                                    style="color:#e54; display:inline" id="show_two">&nbsp;${yesterDayInAmount}元</span></p>
                            <p style="margin-left:40px; text-align:left;  height:32px; line-height:30px;">下次回款时间
                                <span style="margin: 0;font-size: 12px;line-height: 12px;padding: 0 0 10px 0px; display:inline-block">&nbsp;&nbsp;${lastPayDate}</span>
                            </p>

                        </a>

                        <a style="margin: 20px auto;" class="btn1 register" href="${ctx}/uc/index">进入账户中心</a>
                    </div>
                    <!--登录后end-->
                [#else]
                    <!--登录前-->
                    <div class="login_box" id="login_box">
                        <div class="login_switch">
                            <a href="javascript:void(0);" class="">注册</a>
                            <a href="javascript:void(0);" class="cur">登录</a>
                        </div>
                        <!--注册-->
                        <div class="reg_index login_m" style="display: none;">
                            <h2>信任就是财富</h2>
                            <p class="publicity">最高<span>8<em style="font-size: 36px;">+2</em><em
                                    style="font-size: 18px;">%</em></span></p>
                            <p style="opacity: 0.4;">预期年化收益</p>
                            <a class="btn1 register" href="${ctx}/regist" target="_blank">立即注册</a>
                        </div>
                        <!--登录-->
                        <div class="login_index login_m" style="display: block;">
                            <form action=" " method="post" class="loginForm" autocomplete="off">
                                <input type="hidden" name="use_verify_code" id="use_verify_code" value="1">
                                <input type="hidden" name="token" value="4a85d6e61bb9c5c31e710b16c01eba5a">
                                <table width="220" border="0">
                                    <tbody>
                                    <tr align="center">
                                        <td></td>
                                        <td height="20" class="pl20" colspan="2" align="left">
                                            <p id="mt" class="warmin"></p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <div class="radio" style="color: #ffffff">
                                            <div class="radio radio-inline">
                                                <input type="radio"  name="radio1" id="radio1" value="GENERAL" checked="" >
                                                <label for="radio1">
                                                    个人用户
                                                </label>
                                            </div>
                                            <div class="radio radio-inline">
                                                <input type="radio"  name="radio1" id="radio2" value="ENTERPRISE">
                                                <label for="radio2">
                                                    企业用户
                                                </label>
                                            </div>
                                        </div>
                                    </tr>
                                    <tr>
                                        <td class="pl20" colspan="3">
                                            <i class=" icon user"></i>
                                            <input name="username" type="text" id="username" onkeyup="checkMobile(this.value)"
                                                    placeholder="手机号码">
                                        </td>
                                    </tr>
                                    <tr align="center">
                                        <td></td>
                                        <td height="20" colspan="2" class="pl20" align="left"><p id="pt" class=""></p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" class="pl20">
                                            <i class=" icon pwd"></i>
                                            <input name="password" type="password" id="password"
                                                   onkeyup="checkPasswd(this.value);" placeholder="密&nbsp;&nbsp;码"
                                                   autocomplete="off">
                                            <span id="viewPassWord" class="login-eye" style="display: block; width: 30px; height: 20px;margin-top: 3px; margin-left: -20px; background-size: 70%,70%;"></span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="30"></td>
                                        <td align="left" class="pl20" id="choose">
                                            <i class="focus"></i>
                                            <input type="checkbox" name="remember" id="rem" checked
                                                   style="width:20px; height:20px; border:none; vertical-align: middle; display:none;">记住登录名
                                        </td>
                                        <td align="right">
                                            <a href="${ctx}/forgetPassword" style=" color:#7eaee6; " target="_blank">
                                                <span>忘记密码？</span>
                                            </a>
                                        </td>
                                    </tr>
                                    <tr height="40">
                                        <td colspan="3" align="center">
                                            <input type="button" name="loginnow" id="loginnow" value="立即登录" onclick="login()"
                                                   class="btn1  register">
                                        </td>
                                    </tr>
                                    <tr height="20">
                                        <td align="center" colspan="3">
                                            <span span="" class="jm" style="color:#ebe7e7;margin:0;">您的信息已通过256位加密保护</span>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                    </div>
                [/#if]
            </div>
        </div>
    </div>
</div>

<!-- 平台数据 start -->
<section class="dataInfo">
    <div class="layout bgWhite">
        <div class="dataContent clearfix">
            <span>平台投资总额(元)<em class="data">${dealAmount?string(",###.##")}</em></span>
            [#--<span>安全运营(天)<em class="data">${onlineCount}</em></span>--]
            <span class="middle2">累计为用户赚取(元)<em class="data">${earnInterest?string(",###.##")}</em></span>
            <div class="last">
                <i class="fa fa-bullhorn" style="float: left;line-height: 50px;margin-right: 5px"></i>
                <div class="noticeList">
                    <div class="swiper-container">
                        <div class="swiper-wrapper">
                            [@k_contents taxonomyType="category" taxonomySlug="gonggao" listSize=10]
                                [#list contents as content]
                                    <div class="swiper-slide ">
                                        <a href="${ctx}/c/${content.slug}"
                                           target="_blank">${content.title}</a>
                                        <span class="time fc666">${(content.createDate?date)?string("yyyy-MM-dd")}</span>
                                    </div>
                                [/#list]
                                [#if contents?size == 0]
                                    <div class="swiper-slide">
                                        <a>暂无公告</a>
                                        <span class="time fc666"></span>
                                    </div>
                                [/#if]
                            [/@k_contents]
                        </div>
                    </div>
                </div>
                <a class="more link999" href="${ctx}/t/category/gonggao">更多<img style="width: 8px;margin:0 0 3px 5px;" src="${ctx}/static/images/arrow-right.png"></a>
            </div>
        </div>
    </div>
</section>
<div class="container project-wrap">
    <div class="recommend-bar bar">
        <h4>最新优质项目</h4>
        <span>资金不放假，理财有规划</span>
        <a class="more" href="${ctx}/investment">更多项目<img
                src="${ctx}/static/images/arrow-right.png"></a>
    </div>
    <div class="project-items-wrap">
        [#list hotProject as project]
            [#--预告标/投资中--]
            [#if project.showInvesting]
                <a class="invest-item [#if project_index==0]invest-item1[#elseif project_index==1]invest-item-2[#elseif project_index==2]invest-item-3[#else]invest-item-4[/#if]"
                   href="${ctx}/investment/${project.id}" target="_blank">
                    <div>
                        [#if project.investmentStartDate.getTime()>setting.now.getTime()]
                            <div class="invest-item-type-wrap">
                            <span class="invest-item-type" style="display: none">
                                起投时间： <span class="countdown"
                                            startTime="${project.investmentStartDate.getTime()?number_to_datetime}">${project.investmentStartDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                            </span>
                            </div>
                        [/#if]
                        <div class="invest-item-title">
                            [#--${project.type.alias}--]
                            ${abbreviate(project.title, 20, "...")}</div>
                        <div class="invest-item-subtitle">${project.repaymentMethod.displayName}</div>
                        <div class="progress project-progress">
                            <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20.00"
                                 aria-valuemin="0" aria-valuemax="100"
                                 style="width: ${project.investedAmount/project.amount*100}%">
                            </div>
                        </div>
                        <p class="project-info">
                            <span class="percent">${(project.investedAmount/project.amount*100)?string("#.##")}%</span>
                            <span class="decimal-wrap">
                                <span class="decimal">${(project.investedAmount/10000)?string(",###.##")}万</span>
                                <span>/</span>
                                <span class="decimal">${(project.amount/10000)?string(",###.##")}</span>万
                            </span>
                        </p>
                        <div class="invest-item-features">
                            <div>
                                <div class="invest-item-feature">
                                    <div>
                                        <span class="invest-item-profit invest-item-profit">
                                            [#if project.rewardInterestRate > 0]${project.interestRate+project.rewardInterestRate}[#else]${project.interestRate}[/#if]
                                            <em></em>
                                        </span>
                                    </div>
                                    <div>年化利率(%)</div>
                                </div>
                                <div class="split"></div>
                                <div class="invest-item-feature">
                                    <div>
                                        <span class="invest-item-profit">${project.period}</span>
                                    </div>
                                    <div>项目期限(${project.periodUnit.displayName})</div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                </a>
            [/#if]
            [#--还款中--]
            [#if project.progress=="REPAYING"]
                <a class="project-status-finished invest-item [#if project_index==0]invest-item1[#elseif project_index==1]invest-item-2[#elseif project_index==2]invest-item-3[#else]invest-item-4[/#if]"
                   href="${ctx}/investment/${project.id}" target="_blank">
                    <div>
                        <div class="invest-item-title">
                            [#--${project.type.alias}--]
                            ${abbreviate(project.title, 20, "...")}</div>
                        <div class="invest-item-subtitle">${project.repaymentMethod.displayName}</div>
                        <div class="progress project-progress">
                            <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20.00"
                                 aria-valuemin="0" aria-valuemax="100"
                                 style="width: ${project.investedAmount/project.amount*100}%">
                            </div>
                        </div>
                        <p class="project-info">
                            融资完成，金额<span
                                class="decimal">${(project.investedAmount/10000)?string(",###.##")}</span>万
                        </p>
                        <div class="invest-item-features">
                            <div>
                                <div class="invest-item-feature">
                                    <div>
                                        <span class="invest-item-profit invest-item-profit">
                                            [#if project.rewardInterestRate > 0]${project.interestRate+project.rewardInterestRate}[#else]${project.interestRate}[/#if]
                                            <em></em>
                                        </span>
                                    </div>
                                    <div>年化利率(%)</div>
                                </div>
                                <div class="split"></div>
                                <div class="invest-item-feature">
                                    <div>
                                        <span class="invest-item-profit">${project.period}</span>
                                    </div>
                                    <div>项目期限(${project.periodUnit.displayName})</div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                    <div class="project-finished-text">还款中<span>查看详情</span></div>
                    <div class="project-finished-mask"></div>
                </a>
            [/#if]
            [#--已完成--]
            [#if project.progress=="COMPLETED"]
                <a class="project-status-finished invest-item [#if project_index==0]invest-item1[#elseif project_index==1]invest-item-2[#elseif project_index==2]invest-item-3[#else]invest-item-4[/#if]"
                   href="${ctx}/investment/${project.id}" target="_blank">
                    <div>
                        <div class="invest-item-title">
                            ${abbreviate(project.title, 20, "...")}</div>
                        <div class="invest-item-subtitle">${project.repaymentMethod.displayName}</div>
                        <div class="progress project-progress">
                            <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20.00"
                                 aria-valuemin="0" aria-valuemax="100"
                                 style="width: ${project.investedAmount/project.amount*100}%">
                            </div>
                        </div>
                        <p class="project-info">
                            融资完成，金额<span
                                class="decimal">${(project.investedAmount/10000)?string(",###.##")}</span>万
                        </p>
                        <div class="invest-item-features">
                            <div>
                                <div class="invest-item-feature">
                                    <div>
                                        <span class="invest-item-profit invest-item-profit">
                                            [#if project.rewardInterestRate > 0]${project.interestRate+project.rewardInterestRate}[#else]${project.interestRate}[/#if]
                                            <em></em>
                                        </span>
                                    </div>
                                    <div>年化利率(%)</div>
                                </div>
                                <div class="split"></div>
                                <div class="invest-item-feature">
                                    <div>
                                        <span class="invest-item-profit">${project.period}</span>
                                    </div>
                                    <div>项目期限(${project.periodUnit.displayName})</div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                    <div class="project-finished-text">已完成<span>查看详情</span></div>
                    <div class="project-finished-mask"></div>
                </a>
            [/#if]
            [#--已满额--]
            [#if project.progress=="LENDING"]
                <a class="project-status-finished invest-item [#if project_index==0]invest-item1[#elseif project_index==1]invest-item-2[#elseif project_index==2]invest-item-3[#else]invest-item-4[/#if]"
                   href="${ctx}/investment/${project.id}" target="_blank">
                    <div>
                        <div class="invest-item-title">
                            ${abbreviate(project.title, 20, "...")}</div>
                        <div class="invest-item-subtitle">${project.repaymentMethod.displayName}</div>
                        <div class="progress project-progress">
                            <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20.00"
                                 aria-valuemin="0" aria-valuemax="100"
                                 style="width: ${project.investedAmount/project.amount*100}%">
                            </div>
                        </div>
                        <p class="project-info">
                            融资完成，金额<span
                                class="decimal">${(project.investedAmount/10000)?string(",###.##")}</span>万
                        </p>
                        <div class="invest-item-features">
                            <div>
                                <div class="invest-item-feature">
                                    <div>
                                        <span class="invest-item-profit invest-item-profit">
                                            [#if project.rewardInterestRate > 0]${project.interestRate+project.rewardInterestRate}[#else]${project.interestRate}[/#if]
                                            <em></em>
                                        </span>
                                    </div>
                                    <div>年化利率(%)</div>
                                </div>
                                <div class="split"></div>
                                <div class="invest-item-feature">
                                    <div>
                                        <span class="invest-item-profit">${project.period}</span>
                                    </div>
                                    <div>项目期限(${project.periodUnit.displayName})</div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                    <div class="project-finished-text">已满额<span>查看详情</span></div>
                    <div class="project-finished-mask"></div>
                </a>
            [/#if]
        [/#list]
    </div>
</div>

[#if zrProject?size>0]
<div class="container project-wrap">
    <div class="recommend-bar bar">
        <h4>最新转让项目</h4>
        <span>急需资金，快速回笼</span>
        <a class="more" href="${ctx}/investment">更多项目<img
                src="${ctx}/static/images/arrow-right.png"></a>
    </div>
    <div class="project-items-wrap">
        [#if zrProject?size>0]
            [#list zrProject as project]
                [#if project.transfer.state=='TRANSFERING'||project.transfer.state=='TRANSFERPART']
                    <a class="invest-item [#if project_index==0]invest-item1[#elseif project_index==1]invest-item-2[#elseif project_index==2]invest-item-3[#else]invest-item-4[/#if]"
                       href="${ctx}/transfer/${project.transfer.id}" target="_blank">
                        <div>
                            <div class="invest-item-title">
                                [#--[#if project.transfer.type=="CREDIT"]--]
                                [#--车商贷---]
                            [#--[#elseif project.transfer.type=="GUARANTEE"]--]
                                [#--质押贷---]
                            [#--[#elseif project.transfer.type=="MORTGAGE"]--]
                                [#--抵押贷---]
                            [#--[/#if]--]
                                [#--转让标---]
                            ${project.transfer.title}
                            </div>
                            <div class="invest-item-subtitle">${project.transfer.repaymentMethod.displayName}</div>
                            <div class="progress project-progress">
                                <div class="progress-bar progress-bar-info"
                                     role="progressbar"
                                     aria-valuenow="2.00" aria-valuemin="0"
                                     aria-valuemax="100"
                                     style="width: ${project.transfer.transferedCapital/project.transfer.capital*100}%">

                                </div>
                            </div>
                            <p class="project-info">
                                <span class="percent">
                                    [#if (project.transfer.transferedCapital/project.transfer.capital)<=0]
                                        0%
                                    [#else]
                                    ${(project.transfer.transferedCapital/project.transfer.capital*100)?string("#.##")}
                                        %
                                    [/#if]
                                </span>
                                <span class="decimal-wrap">
                                    <span class="decimal">${(project.transfer.transferedCapital/10000)?string(",###.##")}
                                        万</span>
                                    <span>/</span>
                                    <span class="decimal">${(project.transfer.capital/10000)?string(",###.##")}</span>万
                                </span>
                            </p>
                            <div class="invest-item-features">
                                <div>
                                    <div class="invest-item-feature">
                                        <div>
                                            <span class="invest-item-profit DinFont">${project.transfer.interestRate}
                                                <em></em>
                                            </span>
                                        </div>
                                        <div>年化利率(%)</div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="invest-item-feature">
                                        <div>
                                            <span class="invest-item-profit DinFont">${project.transfer.residualPeriod}</span>
                                        </div>
                                        <div>剩余期限(${project.transfer.residualUnit})</div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                    </a>
                [/#if]
                [#if project.transfer.state=="TRANSFERED"]
                    <a class="project-status-finished invest-item [#if project_index==0]invest-item1[#elseif project_index==1]invest-item-2[#elseif project_index==2]invest-item-3[#else]invest-item-4[/#if]"
                       href="${ctx}/transfer/${project.transfer.id}" target="_blank">
                        <div>
                            <div class="invest-item-title">
                                [#--[#if project.transfer.type=="CREDIT"]--]
                                [#--车商贷---]
                            [#--[#elseif project.transfer.type=="GUARANTEE"]--]
                                [#--质押贷---]
                            [#--[#elseif project.transfer.type=="MORTGAGE"]--]
                                [#--抵押贷---]
                            [#--[/#if]--]
                                [#--转让标---]
                            ${project.transfer.title}
                            </div>
                            <div class="invest-item-subtitle">${project.transfer.repaymentMethod.displayName}</div>
                            <div class="progress project-progress">
                                <div class="progress-bar progress-bar-info"
                                     role="progressbar"
                                     aria-valuenow="2.00" aria-valuemin="0"
                                     aria-valuemax="100"
                                     style="width: ${project.transfer.transferedCapital/project.transfer.capital*100}%">
                                </div>
                            </div>
                            <p class="project-info">
                                <span class="percent">
                                    [#if (project.transfer.transferedCapital/project.transfer.capital)<=0]
                                        0%
                                    [#else]
                                    ${(project.transfer.transferedCapital/project.transfer.capital*100)?string("#.##")}
                                        %
                                    [/#if]
                                </span>
                                <span class="decimal-wrap">
                                    <span class="decimal">${(project.transfer.transferedCapital/10000)?string(",###.##")}
                                        万</span>
                                    <span>/</span>
                                    <span class="decimal">${(project.transfer.capital/10000)?string(",###.##")}</span>万
                                </span>
                            </p>
                            <div class="invest-item-features">
                                <div>
                                    <div class="invest-item-feature">
                                        <div>
                                            <span class="invest-item-profit DinFont">${project.transfer.interestRate}
                                                <em></em>
                                            </span>
                                        </div>
                                        <div>年化利率(%)</div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="invest-item-feature">
                                        <div>
                                            <span class="invest-item-profit DinFont">${project.transfer.surplusPeriod}</span>
                                        </div>
                                        <div>剩余期限(${project.timeName})</div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                        <div class="project-finished-text">已转让<span>查看详情</span></div>
                        <div class="project-finished-mask"></div>
                    </a>
                [/#if]
            [/#list]
        [/#if]
    </div>
</div>
[/#if]
<div class="content inside">
    <div class="news" style="margin-top:25px;">
        <div class="media">
            <h2>
                <span onclick="hrefs('${ctx}/t/category/xue_yuan?whois=brothers')" style="cursor:pointer;">${setting.basic.siteName}学院</span>
                <a class="btn3" href="${ctx}/t/category/xue_yuan?whois=brothers" target="_blank">更多 &gt;&gt;</a>
            </h2>
            <ul>
                [@k_contents taxonomyType="category" taxonomySlug="xue_yuan" listSize=4]
                    [#list contents as content]
                        <li>
                            <a href="${ctx}/c/${content.slug}"
                               title="${abbreviate(content.title, 60, "...")}"
                               target="_blank">${abbreviate(content.title, 60, "...")}
                            </a>
                        </li>
                    [/#list]
                    [#if contents?size == 0]
                        <li>
                            <a>暂无公告</a>
                        </li>
                    [/#if]
                [/@k_contents]
            </ul>
        </div>
        <div class="notice">
            <h2>
                <span onclick="hrefs('${ctx}/t/category/gonggao')" style="cursor:pointer;">${setting.basic.siteName}公告</span>
                <a class="btn3" href="${ctx}/t/category/gonggao" target="_blank">更多&gt;&gt;</a>
            </h2>
            <ul>
                [@k_contents taxonomyType="category" taxonomySlug="gonggao" listSize=8]
                    [#list contents as content]
                        <li>
                            <a href="${ctx}/c/${content.slug}"
                               title="${abbreviate(content.title, 60, "...")}"
                               target="_blank">${abbreviate(content.title, 60, "...")}
                                <span>${(content.createDate?date)?string("yyyy-MM-dd HH:mm:ss")}</span>
                            </a>
                        </li>
                    [/#list]
                    [#if contents?size == 0]
                        <li>
                            <a>暂无公告</a>
                        </li>
                    [/#if]
                [/@k_contents]
            </ul>
        </div>
    </div>
</div>
<div class="footer">
<div class="f_link inside">
    <h4>合作伙伴</h4>
    [@k_links type="IMAGE"]
        [#list friendLinks as friendLink]
            [#if friendLink.visible]
                <a href="${friendLink.url}" target="_blank" class="link0">
                    <img alt="${dfsUrl}${friendLink.title}" src="${dfsUrl}${friendLink.logo}" width="110px" height="40px">
                </a>
            [/#if]
        [/#list]
    [/@k_links]
</div>
</div>
[/@insert]