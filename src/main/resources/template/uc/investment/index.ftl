[@nestedstyle]
    [@css href="css/datePickerRange.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/user/avatar.min.js" /]
    [@js src="js/util/format.js" /]
    [@js src="js/util/riqiSearch.js" /]
    [@js src="js/util/DatePickerRange.js" /]
    [@js src="js/util/touzi-all.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心 - 我的投资" module="investment" nav="investment" currentUser=kuser user=kuser]
[#--列表--]
<div class="chongzhiTop">
    <h3>投资记录</h3>
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
        <li class="tal" style="width:15%;box-sizing:border-box;padding-left:2%;+width:14%;">项目名称</li>
        <li class="tac" style="width:11%;">交易时间</li>
        <li class="tac" style="width:11%;">计息时间</li>
        <li class="tar" style="width:9%;">投资本金(元)</li>
        <li class="tar" style="width:9%">预期收益(元)</li>
        <li class="tac">预期年化</li>
        <li class="tac" style="width:7%;">期限</li>
        <li class="tac">状态</li>
        <li style="width:14%;">操作</li>
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
        <li class="tal" style="width:15%;box-sizing:border-box;padding-left:2%;+width:14%;">
            <div class="titleName">
                <a href=${ctx}"/investment/{{item.borrowing}} ">{{ item.title}}</a>
            </div>
        </li>
        <li class="tac" style="width:11%;">{{dateFormatter(item.createDate) }}</li>
        {{# if(item.interestBeginDate!=''){ }}
        <li class="tac" style="width:11%;"> {{ dateFormatter(item.interestBeginDate) }}</li>
        {{# }else{ }}
        <li class="tac" style="width:11%;"> - </li>
        {{# } }}
        <li class="tar" style="width:9%;"> {{ currency(item.amount,'￥') }}</li>
        <li class="tar" style="width:9%;"> {{ currency(item.expectedReturn,'￥') }}</li>
        <li class="tac"> {{item.realInterestRate}}</li>
        <li class="tac" style="width:7%;"> {{item.borrowingPeriod}} {{item.borrowingPeriodUnitStr}}</li>
        <li class="status" style="width:11%;">{{item.stateStr}}</li>
        <li class="search clearfix" style="width:14%;margin-top: 0px">
            <div class="search-a">
            {{#  if(item.borrowingProgressStr != '' && (item.borrowingProgressStr == '还款中' || item.borrowingProgressStr == '已完成')){ }}
                <div class="search-a-title" onclick="loadInvestRefund(this,{{item.id}})">回款计划</div>
            {{#  }else{ }}
                <div style="color:#9e9e9e;">回款计划</div>
            {{#  } }}
                <div class="search-a-content have-more"><i></i>
                    <div class="have-more-div">
                        <ul class="search-a-content-title">
                            <li>回款期数</li>
                            <li>回款日期</li>
                            <li class="tar">待收本息(元)</li>
                            <li class="tar">待收本金(元)</li>
                            <li class="tar">待收利息(元)</li>
                            <li>回款状态</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="search-b">
                <div class="search-b-title">详情</div>
                <div class="son-nav">
                 {{# if (item.state == 'SUCCESS') { }}
                    <span><a target="_blank" href="${ctx}/uc/investment/agreement/{{item.id}}">查看协议</a> </span>
                    <span><a target="_blank" href="${ctx}/uc/investment/agreement/{{item.id}}/download">下载协议</a></span>
                 {{# } else{ }}
                    <span><a style="color:#9e9e9e;" href="javascript:void(0);">查看协议</a></span>
                    <span><a style="color:#9e9e9e;" href="javascript:void(0);">下载协议</a></span>
                 {{# } }}
                </div>
            </div>
        </li>
    </ul>
    {{#  }); }}
</script>

[/@insert]

