[@nestedstyle]
    [@css href="css/auto_invest.css" /]
[/@nestedstyle]
[@nestedscript]
    [#--[@js src="js/user/avatar.min.js" /]--]
    [#--[@js src="js/util/format.js" /]--]
    [#--[@js src="js/util/riqiSearch.js" /]--]
    [#--[@js src="js/util/DatePickerRange.js" /]--]
    <script>
        var openStatus = ${rank.openStatus?string('true', 'false')};
    </script>
    [@js src="js/user/user.auto.investment.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心 - 自动投标" module="autoInvestment" nav="autoInvestment" currentUser=kuser user=kuser]
<div class="auto-sale clearfix">
    <div class="statues">
        <h2>
            自动投标 <span>当前开通自动投标人数<i id="hasSign1">0</i>人</span>
        </h2>
        <p class="grayDarkColor">
            还在为抢不到标烦恼?自动投资优质短期稳定标的,傻瓜式理财,坐享收益!让资金不再闲置!
        </p>
    </div>
    <a href="${ctx}/uc/autoInvestment/record" class="blueColor fr">自动投标记录</a>
</div>
<ul class="auto-chose clearfix">
    <li class="fl">
        <dl>
            <dt class="clearfix"><i class="fa fa-clock-o"></i>
                预约自动投标
            [#if setting.autoInvestmentSetting.autoInvestmentSign]
                <div class="zdBtn fr [#if rank.openStatus && rank.expire.getTime()>setting.now.getTime()]zdBntOk[/#if]" id="autoBtn">
                    <span>开</span>
                    <span>关</span>
                    <div class="bntW"></div>
                </div>
            [/#if]
            </dt>
            <dd>
                自动预约标，比普通标利率高，但需要排队购买，不定时发放， 资金可能会有站岗情况。
            </dd>
        </dl>
        <div class="clearfix"></div>
        <ul class="auto-intro clearfix">
            <li>设置有效期：<span>${rank.validity}天</span></li>
            <li>投标期限：<span><span>${rank.projectMinCyc}天</span> ---- <span>${rank.projectMaxCyc}天</span></span></li>
            <li>投标区间：<span>${rank.investMinAmount}元</span> ---- <span>${rank.investMaxAmount}元</span></li>
            <li>利率区间：<span><span>${rank.interestRateMinRate}%</span> ---- <span>${rank.interestRateMaxRate}%</span></span></li>
            <li>&nbsp;</li>
        </ul>
        <div class="fr chosed chosed-add blue">
        [#if setting.autoInvestmentSetting.autoInvestmentSign]
            <a href="${ctx}/uc/autoInvestment/setting" class="blue">修改设置</a>
        [/#if]
        </div>
        <div class="people-number clearfix">
            <div class="fl not-open">预约自动投标开启总人数<em class="blue not-open"><span id="hasSign2">0</span></em>人</div>
            <div class="fr">
                当前有效人数<span id="effectiveSign">0</span>人
                <i class="hoverQuestion fa fa-question-circle">
                    <div class="hoverText" style="display: none;">有效人数意为开启自动投标且账户余额大于0的用户数</div>
                </i>
            </div>
        </div>
    </li>
</ul>

<div class="modal fade" id="autoInvestmentSignModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close autoInvestStatusClose" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">开启预约自动标确认</h4>
            </div>
            <form class="form-horizontal" id="autoInvestOpenForm" action="${ctx}/uc/autoInvestment/setting/true" method="post">
                <input type="hidden" name="signType" value="AUTO_REPAYMENT_SIGN" />
                <div class="modal-body">
                    <div class="content">
                        <ul class="item-ul" style="padding:25px 0;padding-left:147px;">
                            <li class="clearfix tal arrLi1">
                                设置有效期：<span>${rank.validity}天</span>
                            </li>
                            <li class="clearfix tal arrLi1">
                                投标期限：<span>${rank.projectMinCyc}天</span> ---- <span>${rank.projectMaxCyc}天</span>
                            </li>
                            <li class="clearfix tal arrLi2">
                                投标区间： <span>${rank.investMinAmount}元</span> ---- <span>${rank.investMaxAmount}元</span>
                            </li>
                            <li class="clearfix tal arrLi1">
                                利率区间：<span>${rank.interestRateMinRate}%</span> ---- ${rank.interestRateMaxRate}%</span>
                            </li>
                        </ul>
                        <div class="save save-short" style="border: none;">
                            <button id="toSettingSign" type="submit" name="button" class="button button-save radius-ssm button-normal pay-btn autobid-btn" unvalid="yes">确定</button>
                            <p class="alert-submit" id="mb40">
                                <input type="checkbox" name="agreement" checked="checked">
                                我已阅读并同意 <a href="${ctx}/uc/autoInvestment/agreement" class="blue" target="_blank">《自动投标授权协议》</a>
                            </p>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="autoInvestmentCloseModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close autoInvestStatusClose" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">您确定关闭预约自动投标吗？</h4>
            </div>
            <form class="form-horizontal" id="autoInvestCloseForm" action="${ctx}/uc/autoInvestment/setting/false" method="post">
                <div class="modal-body">
                    <div class="content">
                        <div class="save save-short" style="border: none;margin: 0 auto; text-align: center;padding: 0;">
                            <button type="button" class="btn btn-default autoInvestStatusClose" data-dismiss="modal" style="margin-right: 50px;">取消</button>
                            <button type="submit" class="btn btn-primary autoInvestClose">确定</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
[/@insert]