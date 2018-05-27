[@nestedscript]
    [@js src="js/user/user.address.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心-积分管理" memberContentId="zichan-all" module="address" nav = "address" currentUser=kuser user=kuser]
<div class="chongzhiTop">
    <h3>收货地址管理</h3>
</div>

<p><a href="#" class="center_btn_c">新增</a></p>

<div class="tiaojian clearfix" id="dateRange">
</div>

<div class="answer">
    <ul class="title" id="shippingAddrTitle">
        <li class="tal" style="box-sizing:border-box;padding-left:2%;+width:10%;">收货人</li>
        <li class="tar" style="width:10%;">地区</li>
        <li class="tar" style="width:15%;">详细地址</li>
        <li class="tar" style="width:10%;">邮编</li>
        <li class="tar" style="width:10%;">手机</li>
        <li class="tar" style="width:10%;">电话</li>
        <li class="tar" style="width:10%;">备注</li>
        [#--<li class="tar" style="width:15%;">创建时间</li>--]
        <li class="tar" style="width:15%;">操作</li>
    </ul>
    <div id="shippingAddrs"></div>
</div>
<div class="pageList" id="pagination"   style="text-align: right; margin-top: 5px;"></div>
<div class="empty" style="">
    <img src="${ctx}/static/images/safe/empty.png" alt="">
    <p class="text">暂时没有您的记录</p>
</div>


[#-- 积分记录 --]
<script id="shippingAddrTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
        <ul class="content">
            <li class="tar" style="width:10%;">{{ item.consignee }}</li>
            <li class="tar" style="width:10%;">{{ item.area }}</li>
            <li class="tar" style="width:15%;">{{ item.address }}</li>
            <li class="tar" style="width:10%;">{{ item.zipCode }}</li>
            <li class="tar" style="width:10%;">{{ item.mobile }}</li>
            <li class="tar" style="width:10%;">{{ item.telephone }}</li>
            <li class="tar" style="width:10%;">{{ item.memo }}</li>
            [#--<li class="tal" style="box-sizing:border-box;padding-left:2%;+width:15%;">{{ item.createDate }}</li>--]
            <li class="tal" style="box-sizing:border-box;padding-left:2%;+width:15%;">
                <a class="detail-title blue">修改</a>
                <a class="detail-title blue">删除</a>
            </li>
        </ul>
    {{#  }); }}
</script>

[/@insert]

