[@nestedstyle]
    [@css href="css/datePickerRange.css" /]
[/@nestedstyle]
[@nestedscript]
    [@js src="js/user/avatar.min.js" /]
    [@js src="js/util/format.js" /]
    [@js src="js/user/user.coupon.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心 - 我的优惠券" module="coupon" nav = "coupon" currentUser=kuser user=kuser]

[#--列表--]
<div class="chongzhiTop">
    <h3>我的优惠券</h3>
</div>
<div class="chongzhiNav clearfix">
    <ul>
        <li class="recoveryView cur" state="UNUSED"><a href="javascript:void(0);">未使用</a></li>
        <li class="recoveryView" state="USED"><a href="javascript:void(0);">已使用</a></li>
        <li class="recoveryView" state="OVERDUE"><a href="javascript:void(0);">已失效</a></li>
    </ul>
</div>
<div class="answer" style="display: none;">
    <ul class="title">
        [#--<li  style="width:9%;box-sizing:border-box;padding-left:1%;+width:9%;">申请人</li>--]
        <li  style="width:13%">名称</li>
        <li  style="width:8%">金额(元)</li>
        <li id="createDate" style="width:14%">生效日期</li>
        <li id="usedDate" style="width:14%">使用日期</li>
        <li  style="width:14%">到期日期</li>
        <li  style="width:12%">红包类型</li>
        <li  style="width:15%">使用条件</li>
        [#--<li  style="width:15%">备注</li>--]
    </ul>
    <div id="data"></div>
</div>
<div class="pageList" id="pagination" style="display: none; margin: 10px 5px 0 auto;"></div>
<div class="empty" id="noDataTip" style="">
    <img src="${ctx}/static/images/safe/empty.png" alt="">
    <p class="text">暂无优惠券！</p>
    <p class="btn">
[#--        <a href="${ctx}/borrowingApply">
            <button class="center_btn_a">立即借款</button>
        </a>--]
    </p>
</div>
<script id="dataTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
    <ul class="content">
        [#--<li  style="width:9%;box-sizing:border-box;padding-left:1%;+width:9%;">{{ item.mobile }}</li>--]
        <li  style="width:13%;">{{ item.title }}</li>
        <li  style="width:8%;">{{ item.amount }}</li>
        {{# if(item.couponState == 'USED'){ }}
            <li  style="width:14%;">{{ item.usedDate }}</li>
        {{# }else{ }}
            <li  style="width:14%;">{{ item.createDate }}</li>
        {{# } }}
        <li  style="width:14%;">{{ item.userInvalidDate}}</li>
        <li  style="width:12%;">{{ item.couponTypeStr }}</li>
        <li  style="width:15%;">投资金额不少于{{ item.rule.beginAmount }}元</li>
        [#--<li  style="width:15%;">投资金额不少于{{ item.remark }}元</li>--]
    </ul>
    {{#  }); }}
</script>
[/@insert]

