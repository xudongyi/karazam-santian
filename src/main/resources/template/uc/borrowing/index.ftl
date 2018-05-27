[@nestedstyle]
    [@css href="css/datePickerRange.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/user/avatar.min.js" /]
    [@js src="js/util/format.js" /]
    [@js src="js/util/riqiSearch.js" /]
    [@js src="js/util/DatePickerRange.js" /]
    [@js src="js/user/user.borrowing.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心 - 我的借款" module="borrowing" nav = "borrowing" currentUser=kuser user=kuser]

[#--列表--]
<div class="chongzhiTop">
    <h3>我的借款</h3>
</div>
<div class="chongzhiNav clearfix">
    <ul>

        [#--<li class="recoveryView cur" state="APPLY"><a href="javascript:void(0);">申请中</a></li>--]
        [#--<li class="recoveryView" state="auditing"><a href="javascript:void(0);">审核中</a></li>--]
        <li class="recoveryView cur" state="REPAYING"><a href="javascript:void(0);">还款中</a></li>
            <li class="recoveryView " state="NOLEND"><a href="javascript:void(0);">未出借</a></li>
        <li class="recoveryView" state="COMPLETED"><a href="javascript:void(0);">已完成</a></li>
    </ul>
</div>
<div class="tiaojian clearfix" id="dateRange">
    <ul>
        <li class="cur">全部</li>
        <li>近1个月</li>
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
    <ul class="title" id="borrowing">
        <li style="width:10%;">编号</li>
        <li class="tac" style="width:10%;">名称</li>
        <li class="tac" style="width:15%;">金额</li>
        <li class="tac" style="width:10%;">年利率</li>
        <li class="tac" style="width:10%">期限</li>
        <li class="tac" style="width:10%;">进度</li>
        <li style="width:17%;">协议</li>
        <li style="width:18%;">操作</li>
    </ul>
    <ul class="title" id="borrowing-apply">
        [#--<li  style="width:10%;box-sizing:border-box;padding-left:1%;+width:9%;">申请人</li>--]
        <li  style="width:10%">申请类型</li>
        <li  style="width:10%">申请进度</li>
        <li  style="width:10%">申请金额</li>
        <li  style="width:10%">期望期限</li>
        <li  style="width:10%">期望利率</li>
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
            <button class="center_btn_a">立即借款</button>
        </a>--]
    </p>
</div>

<script id="dataTpl-borrowing" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <ul class="content">
        <li style="width:10%;">
            <div class="titleName">
                <a href="">{{ item.id }}</a>
            </div>
        </li>
        {{#  if(item.title.length>8){ }}
            <li class="tac" style="width:10%;" title="{{ item.title }}">{{ item.title.substring(0,8) }}..</li>
        {{#  }else{ }}
            <li class="tac" style="width:10%;" title="{{ item.title }}">{{ item.title }}</li>
        {{#  } }}
        <li class="tac" style="width:15%;">{{ currency(item.amount, '￥') }}</li>
        <li class="tac" style="width:10%;">{{ item.interestRateByYear }}</li>
        <li class="tac" style="width:10%;">{{ item.period }}{{ item.periodUnitDes }}</li>
        <li class="tac" style="width:10%;">{{ item.progressDes }}</li>
        <li class="search clearfix" style="width:17%;margin-top: 0">
            {{#  if(item.progress == 'REPAYING' || item.progress == 'COMPLETED'){ }}
                <a target="_blank" class="search-a-title" style="color:#36adf7;" href="${ctx}/uc/borrowing/agreement/{{ item.id }}" >查看</a>
                <a target="_blank" class="search-a-title" style="color:#36adf7;margin-left: 5px" href="${ctx}/uc/borrowing/agreement/{{ item.id }}/download" >下载</a>
            {{#  }else{ }}
                [#--<a href="javascript:void(0);" style="color:#9e9e9e;">查看</a>--]
                [#--<a href="javascript:void(0);" style="color:#9e9e9e;">下载</a>--]
                <li style="width:17%;margin-top: 0">-</li>
            {{#  } }}
        </li>
        <li class="search clearfix" style="width:18%;margin-top: 0">
            {{#  if(item.progress == 'INVESTING' || item.progress == 'LENDING'){ }}
                <a target="_blank" class="search-a-title" href="${ctx}/investment/{{ item.id }}" style="color:#36adf7;">详情</a>
                [#--<a href="javascript:void(0);" style="color:#9e9e9e;">还款计划</a>--]
            {{#  }else if(item.progress == 'REPAYING' || item.progress == 'COMPLETED'){ }}
                <a target="_blank" class="search-a-title" href="${ctx}/investment/{{ item.id }}" style="color:#36adf7;">详情</a>
                <a class="search-a-title" href="${ctx}/uc/repayment/plan/{{ item.id }}" style="color:#36adf7;margin-left: 5px">还款计划</a>
            {{#  }else{ }}
                [#--<a href="javascript:void(0);" style="color:#9e9e9e;">详情</a>--]
                [#--<a href="javascript:void(0);" style="color:#9e9e9e;">还款计划</a>--]
            {{#  } }}
        </li>
    </ul>
    {{#  }); }}
</script>
<script id="dataTpl-borrowing-apply" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <ul class="content">
        [#--<li  style="width:9%;box-sizing:border-box;padding-left:1%;+width:9%;">{{ item.mobile }}</li>--]
        <li  style="width:10%;">{{ item.borrowType }}</li>
        <li  style="width:10%;">{{ item.borrowingApplyProgress }}</li>
        <li  style="width:10%;">{{ item.amount }}</li>
        <li  style="width:10%;">{{ item.deadline }}</li>
        <li  style="width:10%;">{{ item.rate }}</li>
        {{# if(item.suggestion==null){ }}
        <li  style="width:35%;">-</li>
        {{# }else{ }}
        <li  style="width:35%;">{{ item.suggestion }}</li>
        {{# } }}
        <li  style="width:15%;">{{ item.createDate }}</li>
    </ul>
    {{#  }); }}
</script>
[/@insert]

