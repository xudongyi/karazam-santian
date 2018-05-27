[@nestedstyle]
    [@css href="css/datePickerRange.css" /]
    [@css href="css/auto_invest.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/user/avatar.min.js" /]
    [@js src="js/util/format.js" /]
    [@js src="js/util/riqiSearch.js" /]
    [@js src="js/util/DatePickerRange.js" /]
    [@js src="js/user/user.auto.investment.record.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心-自动投标记录" module="autoInvestment" nav="autoInvestment" currentUser=kuser user=kuser]
[#--列表--]
<div class="chongzhiTop">
    <h3>自动投标</h3>
</div>
<div class="tiaojian clearfix" id="dateRange">
    <ul>
        <li class="cur">全部</li>
        <li >近1个月</li>
        <li>近3个月</li>
        <li>近1年</li>
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
        <li class="tal" style="+width:31%;padding-left:2%;">项目名称</li>
        <li style="width: 15%">自动投标时间</li>
        <li class="tar" style="width: 10%">投资金额(元)</li>
        <li class="tar" style="width: 15%">预期年化</li>
        <li style="width: 15%">期限</li>
        <li class="tal" style="padding-left:2%;+width:14%;">还款方式</li>
    </ul>
    <div id="dataDiv"></div>
</div>
<div class="pageList" id="pagination" style="display: none; margin: 10px 5px 0 auto;"></div>
<div class="empty" id="noDataTip" style="">
    <img src="${ctx}/static/images/safe/empty.png" alt="">
    <p class="text">您距离躺着赚钱，只剩动动手指投资啦！</p>
    <p class="btn">
        <a href="${ctx}/investment">
            <button class="center_btn_a">立即投资</button>
        </a>
    </p>
</div>

<script id="dataTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <ul class="content">
        <li class="tal" style="+width:31%;padding-left:2%;">
            <div class="titleName">
                {{#  if(item.title.length>8){ }}
                    <a href="${ctx}/investment/{{ item.borrowing }}" target="_blank" title="{{ item.title }}">{{ item.title.substring(0,8) }}..</a>
                {{#  }else{ }}
                    <a href="${ctx}/investment/{{ item.borrowing }}" target="_blank" title="{{ item.title }}">{{ item.title}}</a>
                {{#  } }}
            </div>
        </li>
        [#--<li>{{dateFormatter(item.createDate, 'yyyy-MM-dd hh:mm:ss') }}</li>--]
        <li style="width: 15%">{{ item.createDate }}</li>
        <li class="tar" style="width: 10%"> {{ currency(item.amount,'￥') }}</li>
        <li class="tar" style="width: 15%"> {{ item.borrowingInterestRate }}%</li>
        <li style="width: 15%"> {{ item.borrowingPeriod }}{{ item.borrowingPeriodUnitDes }}</li>
        <li class="tal" style="padding-left:2%;+width:14%;"> {{ item.borrowingRepaymentMethodDes }}</li>
    </ul>
    {{#  }); }}
</script>

[/@insert]

