[@nestedstyle]
    [@css href="css/datePickerRange.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/util/riqiSearch.js" /]
    [@js src="js/util/DatePickerRange.js" /]
    [@js src="js/user/user.recovery.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心 - 回款明细" memberContentId="shoukuan-daishou" module="recovery" nav="uc" currentUser=kuser user=kuser]
<div class="chongzhiTop">
    <h3>收款明细</h3>
</div>
<div class="chongzhiNav clearfix">
    <ul>
        <li class="recoveryView cur" state="recovering"><a href="javascript:void(0);">待收明细</a></li>
        <li class="recoveryView" state="recoveried"><a href="javascript:void(0);">已收明细</a></li>
    </ul>
</div>
<p class="weishouDetail recoveringDetail">
    <span>未收本金：<em class="capitals">0.00</em>元</span>
    <span>未收利息：<em class="interests">0.00</em>元</span>
</p>
<p class="weishouDetail recoveriedDetail">
    <span>已收本金：<em class="capitals">0.00</em>元</span>
    <span>已收利息：<em class="interests">0.00</em>元</span>
</p>
<div class="tiaojian clearfix" id="dateRange">
    <ul>
        <li class="cur">全部</li>
        [#--[#if true]--]
            <li class="futureButton">未来1个月</li>
            <li class="futureButton">未来3个月</li>
            <li class="futureButton">未来1年</li>
        [#--[/#if]--]
    </ul>
    <div class="riqi" id="allStatus">
        日期：
        <input type="text" id="startDate">
        <label>—</label>
        <input type="text" id="endDate">
        <button type="button" class="button button-sm button-special" id="riqiSearch">查询</button>
        <div class="datapicker touziRiqi" id="datapicker" style="display: none;">
        </div>
        <div class="datapicker touziRiqi" id="datapicker1" style="left: 155px; display: none;"></div>
        [#--<button type="button" class="button button-sm button-special" id="excel">导出excel</button>--]
    </div>
</div>
<div class="answer" style="display: none;">
    <ul class="title">
        <li class="tal" style="width:11%;box-sizing:border-box;padding-left:2%;+width:10%;">期数</li>
        <li class="tal" style="width:17%;">项目名称</li>
        <li id="recoveringPayDate">待收日期</li>
        <li id="recoveriedPayDate" style="display: none;">已收日期</li>
        <li class="tar" id="recoveringCapitalInterest">待收本息(元)</li>
        <li class="tar" id="recoveriedCapitalInterest" style="display: none;">已收本息(元)</li>
        <li class="tar" id="recoveringCapital">待收本金(元)</li>
        <li class="tar" id="recoveriedCapital" style="display: none;">已收本金(元)</li>
        <li class="tar" id="recoveringInterest">待收利息(元)</li>
        <li class="tar" id="recoveriedInterest" style="display: none;">已收利息(元)</li>
        <li style="width:15%;">状态</li>
    </ul>
    <div id="recoveryCont"></div>
</div>
<div class="pageList" id="pagination" style="display: none;"></div>
<div class="empty" id="recoveriesEmpty">
    <img src="${ctx}/static/images/safe/empty1.png" alt="">
    <p class="text">您距离躺着赚钱，只剩动动手指投资啦！</p>
    <p class="btn"><a href="${ctx}/investment">
        <button class="center_btn_a">立即投资</button>
    </a></p>
</div>

<script id="recoveryTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
        <ul class="content">
            <li class="tal" style="width:11%;box-sizing:border-box;padding-left:2%;+width:9%;">
                {{ item.period }} / {{ item.projectPeriod }}
            </li>
            <li class="tal" style="width:17%;">
                <a href="${ctx}/investment/{{item.borrowing}}">{{ item.projectName }}</a>
            </li>
            <li>
                {{#if (item.state=='REPAYING') { }}
                    {{ dateFormatter(item.payDate) }}
                {{# } else { }}
                    {{ dateFormatter(item.paidDate) }}
                {{# } }}
            </li>
            <li class="tar">
                {{#if (item.state=='REPAYING') { }}
                    {{ currency(item.capitalInterest) }}
                {{# } else { }}
                    {{ currency(item.paidCapital + item.paidInterest) }}
                {{# } }}
            </li>
            <li class="tar">
                {{#if (item.state=='REPAYING') { }}
                    {{ currency(item.capital) }}
                {{# } else { }}
                    {{ currency(item.paidCapital) }}
                {{# } }}
            </li>
            <li class="tar">
                {{#if (item.state=='REPAYING') { }}
                    {{ currency(item.interest) }}
                {{# } else { }}
                    {{ currency(item.paidAmount-item.paidCapital) }}
                {{# } }}
            </li>
            <li style="width:15%;">{{ item.stateDes }}</li>
        </ul>
    {{#  }); }}

</script>
[/@insert]