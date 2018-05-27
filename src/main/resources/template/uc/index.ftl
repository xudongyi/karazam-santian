[@nestedstyle]
    [@css href="css/center_index.css" /]
    [@css href="css/datepicker.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/util/DatePicker.js" /]
    [@js src="js/user/user.home.js" /]
[/@nestedscript]
[#assign userDetail = k_userinfo(kuser.id) /]
[@insert template="layout/uc_layout" title="用户中心" memberContentId="center-index" module="index" currentUser=kuser user=kuser]
<ul class="total-data">
    <li class="li-a">
        <h3>总资产(元) <i class="fa fa-eye" id="eye"></i></h3>
        <p>
            <span class="font-orange DinFont" id="total-money">￥${assets.allCapitalSum}</span>
            <a href="${ctx}/uc/pandect" class="blue more">详情</a>
        </p>
        <input id="depositStatus" type="hidden" value="0">
        <input id="depositSwitch" type="hidden" value="1">
        <input id="isFromLogin" type="hidden" value="">
        <input type="hidden" id="civilSubjectType" value="1">
        <input type="hidden" id="certType" value="1">
        <input type="hidden" id="depositStatusVo" value="0">
    </li>
    <li class="li-b">
        <h3 class="h3-small">累计收益(元)<i></i></h3>
        <p><span id="total-interest" class="DinFont">￥${assets.alreadyProfits}</span></p>
    </li>
    <li class="li-c">
        <h3 class="h3-small">可用余额(元)<i></i></h3>
        <p><span id="left-money" class="DinFont">￥${assets.available}</span></p>
    </li>
    <li class="basic-coperate">
        <p><a href="${ctx}/uc/recharge" id="recharge" class="center_btn_c">充值</a></p>
        <p id="takeBtn"><a id="cash" href="${ctx}/uc//withdraw" class="tixianBtn">提现</a></p>
    </li>
</ul>
<div class="money-detail">
    <h3><span class="fl">收款明细</span><i class="show-detail fa fa-reorder" id="record-detail"></i></h3>
    <div class="month-data font-gray">
        <div class="fl" style="padding-left: 18px">
            本月到期应收(元)：<span class="font-black">0.00</span>&nbsp;&nbsp;本月未收(元)：<span class="font-black">0.00</span>
        </div>
        <div class="nianyue font-black">
            <div class="select_year fl">
                [#assign nowYear = (.now?string('yyyy'))?number /]
                [#assign nowMonth = (.now?string('M'))?number /]
                [#assign beginYear = nowYear-2 /]
                [#assign endYear = beginYear+5 /]
                <input type="hidden" value="${nowYear}" id="selectYearTxt" class="input_txt">
                <dl class="select">
                    <dt><a target="_self" href="javascript:void(0);" id="selectYear">${nowYear}年</a></dt>
                    <dd class="bd" style="display: none;">
                        <ul>
                            [#list beginYear..endYear as year]
                                <li [#if year==nowYear]class="selected"[/#if]><a value="${year_index+1}" data-value="${year}" href="javascript:void(0);">${year}年</a></li>
                            [/#list]
                        </ul>
                    </dd>
                </dl>
            </div>
            <div class="select_month fl">
                <input type="hidden" value="4" id="selectMonthTxt" class="input_txt">
                <dl class="select" id="scroll_bd">
                    <dt><a target="_self" href="javascript:void(0);" id="selectMonth">${nowMonth}月</a></dt>
                    <dd class="bd" style="display: none;">
                        <ul id="ul">
                            [#list 1..12 as month]
                                <li [#if month==nowMonth]class="selected"[/#if]><a value="${month}" data-value="${month}" href="javascript:void(0);">${month}月</a></li>
                            [/#list]
                        </ul>
                    </dd>
                    <div class="scroll" id="scroll" style="display: none;">
                        <p id="p"></p>
                    </div>
                </dl>
            </div>
            [#--<a class="fl blue monthBill" style="line-height: 26px;" target="_blank" href="javascript:void(0)">月度账单&gt;&gt;</a>--]
        </div>
    </div>
    <div class="day-data clearfix">
        <div class="fl">
            <p id="today"><i class="rili">今</i></p>
            <div id="dataLeft" class="tc">
                <div class="empty"><img>
                    <p>当天没有项目回款</p>
                    <p><a href="/list/showBidList" class="blueColor">快去投资</a>吧...</p></div>
            </div>
        </div>
        <div class="fr">
            <a class="preBtn" onclick="datapicker.toPrevMonth();"><i></i></a>
            <a class="nextBtn" onclick="datapicker.toNextMonth();"><i></i></a>
            <div class="datapicker" id="datapicker">
            </div>
        </div>
    </div>
    <div class="detail-content">
        <div class="answer">
            <ul class="title">
                <li class="tal">当前/总(期)</li>
                <li>项目名称</li>
                <li>待收日期</li>
                <li class="tar">待收总额(元)</li>
                <li class="tar">待收本金(元)</li>
                <li class="tar">待收利息(元)</li>
                <li>状态</li>
            </ul>
            <div id="recoveryCont"></div>
        </div>
        <p><a href="${ctx}/uc/recovery" class="blue">查看更多&gt;</a></p>
    </div>
</div>
[#if hotProjects?? && hotProjects?size>0]
    <div>
        <h3>热门推荐</h3>
        <ul class="advise-bid">
            [#list hotProjects as project]
                [#if project.progress=="INVESTING"]
                <li>
                    <a href="${ctx}/investment/${project.id}">
                        <h4 class="blackColor">${project.title} <span></span></h4>
                        <p class="bid-data clearfix DinFont blackColor">
                            <span class="font-orange fl DinFont">${project.realInterestRate}%</span>
                            <span class="fr DinFont">${project.period}<em>${project.periodUnit.displayName} </em></span>
                        </p>
                        <p class="bid-cate clearfix"><span class="fl">预期年化</span><span class="fr">投资期限</span></p>
                    </a>
                </li>
                [/#if]
            [/#list]
        </ul>
    </div>
[/#if]

<script id="recoveryTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <ul class="content">
        <li class="tal">
            {{ item.period }} / {{ item.projectPeriod }}
        </li>
        <li>
            <a href="'+viewUrl+'">{{ item.projectName }}</a>
        </li>
        <li>{{ dateFormatter(item.payDate) }}</li>
        <li class="tar">{{ currency(item.capitalInterest) }}</li>
        <li class="tar">{{ currency(item.capital) }}</li>
        <li class="tar">{{ currency(item.interest) }}</li>
        <li>{{ item.stateDes }}</li>
    </ul>
    {{#  }); }}

</script>

[/@insert]