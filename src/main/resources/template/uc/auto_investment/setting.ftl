[@nestedstyle]
    [@css href="css/auto_invest.css" /]
[/@nestedstyle]
[@nestedscript]
    [#--[@js src="js/user/avatar.min.js" /]--]
    [#--[@js src="js/util/format.js" /]--]
    [#--[@js src="js/util/riqiSearch.js" /]--]
    [#--[@js src="js/util/DatePickerRange.js" /]--]
    [@js src="js/user/user.auto.settingt.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心 - 自动投标" module="autoInvestment" nav="autoInvestment" currentUser=kuser user=kuser]
<div class="auto-sale clearfix">
    <div class="statues">
        <h2>
            预约自动投标
            <span class="grayDarkColor">
                预约自动投标当前排名
                <i id="userRank" class="blackColor">0</i>/<i id="effectiveSign1" class="blackColor">0</i>
            </span>
            <span class="grayDarkColor">
                预约自动投标当前有效人数
                <i id="effectiveSign2" class="blackColor">0</i>人
                <em class="helpTipsIcon fa fa-question-circle">
                    <div class="helpTipsText">自动投资优选项目，让资金不再站岗！</div>
                    <i class="helpTipsI"></i>
                </em>
            </span>
        </h2>
        <p class="grayDarkColor">还在为抢不到标烦恼?自动投资优质短期稳定标的,傻瓜式理财,坐享收益!让资金不再闲置!</p>
    </div>
</div>
<form action="" id="autoForm">
    <ul class="seting-form">
        <li class="clearfix">
            <label for="" class="name">设置有效期：</label>
            <input type="text" id="validity" name="validity" value="${rank.validity!1}" placeholder="签约有效期" class="input-select short-input" style="width:100px;margin-right:0">
            <span>&nbsp;天</span>
        </li>
        <li class="clearfix">
            <label for="" class="name">投标期限：</label>
            <input type="text" id="projectMinCyc" name="projectMinCyc" value="${rank.projectMinCyc!1}" placeholder="投标期限最小值" class="input-select short-input"
                   style="width:100px;margin-right:0"
                   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
            <div class="text-p"></div>
            <input type="text" id="projectMaxCyc" name="projectMaxCyc" value="${rank.projectMaxCyc!10}" placeholder="投标期限最大值" class="input-select short-input"
                   style="width:100px;margin-right:0"
                   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
            <span>&nbsp;天</span>
        </li>
        <li class="clearfix">
            <label for="" class="name">投标区间：</label>
            <input type="text" id="investMinAmount" name="investMinAmount" value="${rank.investMinAmount!100}" placeholder="投标区间最小值" class="input-select short-input"
                   style="width:100px;margin-right:0"
                   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
            <div class="text-p"></div>
            <input type="text" id="investMaxAmount" name="investMaxAmount" value="${rank.investMaxAmount!10000}" placeholder="投标区间最大值" class="input-select short-input"
                   style="width:100px;margin-right:0"
                   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
            <span>&nbsp;元</span>
        </li>
        <li class="clearfix">
            <label for="" class="name">利率区间：</label>
            <input type="text" id="interestRateMinRate" name="interestRateMinRate" value="${rank.interestRateMinRate!1}" placeholder="不限" class="input-select short-input" style="width:100px;margin-right:0">
            <div class="text-p"></div>
            <input type="text" id="interestRateMaxRate" name="interestRateMaxRate" value="${rank.interestRateMaxRate!48}" placeholder="不限" class="input-select short-input" style="width:100px;margin-right:0">
            <span>&nbsp;%</span>
        </li>
    </ul>
</form>
<div class="save save-short" style="border: none;">
    <button id="toSettingSign" type="button" name="button" class="button button-save radius-ssm button-normal pay-btn autobid-btn" unvalid="yes">保存设置并开启</button>
</div>
<div class="order-intro-ul" style="padding-top: 20px;">
    <h6>预约自动投标说明：</h6>
    <ul>
        <li>一.自动投标排队规则</li>
        <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;先抓取符合当前自动标投资条件的用户，然后在适合的用户中按照排名先后再次进行排序进行投标，因此没有了跳过标的的概念，也较难预估投标时间，投资人有意向进行自动投标轮流的，可提前进行充值。</li>
        <li>以下几种情况将导致重新排队：</li>
        <li>a)更改期限设置；</li>
        <li>b)更改下限，上限设置；</li>
        <li>c)自动投标关闭后重新开启；</li>
        <li>d)成功投标之后（除部分中标）；</li>
        <li>e)轮到时，账户余额小于500 ；</li>
        <li>&nbsp;</li>
        <li>二．部分中标情况介绍</li>
        <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;部分中标系指投资人最近一次中标的额度是符合投资人的投标下限设置，但由于投资人是最后一个中标的，可能标剩余额度小于500元，这时投资人也会中标，不过不会重新排队，投资人账户的资金会继续投中下一个符合投资人投标设置的标。部分中标时，请勿修改任何设置，避免重新排队。</li>
    </ul>
</div>

<div class="modal fade" id="autoInvestmentSignModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" id="signCloseBtn" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">开启预约自动标确认</h4>
            </div>
            <form class="form-horizontal" id="autoInvestmentSignForm">
                <input type="hidden" name="signType" id="signType" value="AUTO_INVESTMENT_SIGN" />
                <div class="modal-body">
                    <div class="content">
                        <ul class="item-ul" style="padding:25px 0;padding-left:147px;">
                            <li class="clearfix tal arrLi1">
                                设置有效期： <span id="validityShow">1天</span>
                            </li>
                            <li class="clearfix tal arrLi1">
                                投标期限： <span id="projectMinCycShow">不限</span>
                                ----
                                <span id="projectMaxCycShow">不限</span>
                            </li>
                            <li class="clearfix tal arrLi2">
                                投标区间： <span id="investMinAmountShow">100元</span>
                                ----
                                <span id="investMaxAmountShow">不限</span>
                            </li>
                            </li>
                            <li class="clearfix tal arrLi1">
                                利率区间： <span id="interestRateMinRateShow">不限</span>
                                ----
                                <span id="interestRateMaxRateShow">不限</span>
                            </li>
                        </ul>
                        <div class="save save-short" style="border: none;">
                            <button id="toSettingSign" type="submit" class="button button-save radius-ssm button-normal pay-btn autobid-btn" unvalid="yes"> 确 定 </button>
                            <p class="alert-submit" id="mb40"><input type="checkbox" name="agreement" checked="checked">
                                我已阅读并同意 <a href="${ctx}/uc/autoInvestment/agreement" class="blue" target="_blank">《自动投标授权协议》</a>
                            </p>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

[/@insert]