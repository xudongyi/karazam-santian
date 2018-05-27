[@nestedstyle]
    [@css href="css/datePickerRange.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/user/avatar.min.js" /]
    [@js src="js/util/format.js" /]
    [#--[@js src="js/util/riqiSearch.js" /]--]
    [#--[@js src="js/util/DatePickerRange.js" /]--]
    [@js src="js/user/user.borrowing.apply.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心 - 我的借款" module="borrowing_apply" nav = "borrowing_apply" currentUser=kuser user=kuser]

[#--列表--]
<div class="chongzhiTop">
    <h3>我的借款申请</h3>
</div>
<div class="chongzhiNav clearfix">
</div>
[#--<div class="tiaojian clearfix" id="dateRange">--]
    [#--<ul>--]
        [#--<li class="cur">全部</li>--]
        [#--<li>近1个月</li>--]
        [#--<li>近3个月</li>--]
        [#--<li>近1年</li>--]
    [#--</ul>--]
    [#--<div class="riqi" id="allStatus">--]
        [#--日期：--]
        [#--<input type="text" id="startDate">--]
        [#--<label>—</label>--]
        [#--<input type="text" id="endDate">--]
        [#--<button type="button" class="button button-sm button-special" id="riqiSearch">查询</button>--]
        [#--<div class="datapicker touziRiqi" id="datapicker" style="display: none;">--]
        [#--</div>--]
        [#--<div class="datapicker touziRiqi" id="datapicker1" style="left: 155px; display: none;"></div>--]
        [#--<button type="button" class="button button-sm button-special" id="excel">导出excel</button>--]
    [#--</div>--]
[#--</div>--]
<div class="answer" style="display: none;">
    <ul class="title">
        <li  style="width:9%;box-sizing:border-box;padding-left:1%;+width:9%;">申请人</li>
        <li  style="width:10%">申请类型</li>
        <li  style="width:10%">申请进度</li>
        <li  style="width:7%">申请金额</li>
        <li  style="width:7%">期望期限</li>
        <li  style="width:7%">期望利率</li>
        <li  style="width:35%">审批意见</li>
        <li  style="width:15%">申请时间</li>
    </ul>
    <div id="data"></div>
</div>
<div class="pageList" id="pagination" style="display: none; margin: 10px 5px 0 auto;"></div>
<div class="empty" id="noDataTip" style="">
    <img src="${ctx}/static/images/safe/empty.png" alt="">
    <p class="text">需要资金周转，来借款吧！</p>
    <p class="btn">
[#--        <a href="${ctx}/borrowingApply">
            <button class="center_btn_a">立即申请借款</button>
        </a>--]
    </p>
</div>

<script id="dataTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <ul class="content">
        <li  style="width:9%;box-sizing:border-box;padding-left:1%;+width:9%;">{{ item.mobile }}</li>
        <li  style="width:10%;">{{ item.borrowType }}</li>
        <li  style="width:10%;">{{ item.borrowingApplyProgress }}</li>
        <li  style="width:7%;">{{ item.amount }}</li>
        <li  style="width:7%;">{{ item.deadline }}</li>
        <li  style="width:7%;">{{ item.rate }}</li>
        {{# if(item.suggestion==null){ }}
        <li  style="width:35%;">--</li>
        {{# }else{ }}
        <li  style="width:35%;">{{ item.suggestion }}</li>
        {{# } }}
        <li  style="width:15%;">{{ item.createDate }}</li>
    </ul>
    {{#  }); }}
</script>
[/@insert]

