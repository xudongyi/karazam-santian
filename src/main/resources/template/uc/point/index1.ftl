[@nestedscript]
    [@js src="js/user/user.point.js" /]
[/@nestedscript]
[@insert template="layout/uc_layout" title="用户中心-积分管理" memberContentId="zichan-all" module="point" nav = "point" currentUser=kuser user=kuser]
<div class="chongzhiTop">
    <h3>积分管理</h3>
</div>
<div class="chongzhiNav clearfix">
    <ul>
        <li class="typepointRecords cur" searchType="pointRecords">
            <a href="javascript:void(0);">积分记录</a>
        </li>
        <li class="typepointRecords" searchType="goodsOrder">
            <a href="javascript:void(0);">积分订单</a>
        </li>
    </ul>
</div>

<div class="tiaojian clearfix" id="dateRange">
    <div class="riqi pointRecordsType">
        类型：
        <select class="form-control selector pointRecords-form-control" style="font-size: 12px;height:30px;vertical-align: middle">
            <option class="selector-title" value="">全部</option>
            [#list pointTypes as _type]
                <option value="${_type}"[#if _type == type] selected="selected"[/#if]>${_type.displayName}</option>
            [/#list]
        </select>
    </div>
    <div class="riqi goodsOrderType" style="display: none;">
        类型：
        <select class="form-control selector goodsOrder-form-control" style="font-size: 12px;height:30px;vertical-align: middle">
            <option class="selector-title" value="">全部</option>
            [#list goodsOrderStatus as _status]
                <option value="${_status}"[#if _status == status] selected="selected"[/#if]>${_status.displayName}</option>
            [/#list]
        </select>
    </div>
</div>

<div class="answer">
    <ul class="title" id="pointRecordsTitle">
        <li class="tal" style="box-sizing:border-box;padding-left:2%;+width:20%;">方式</li>
        <li class="tar" style="width:20%;">类型</li>
        <li class="tar" style="width:20%;">积分</li>
        <li class="tar" style="width:20%;">备注</li>
        <li class="tar" style="width:20%;">创建时间</li>
    </ul>
    <ul class="title" id="goodsOrderTitle" style="display: none;">
        <li class="tal" style="width:15%;">订单编号</li>
        <li class="tar" style="width:10%;">订单状态</li>
        [#--<li class="tar" style="width:10%;">物流状态</li>--]
        <li class="tar" style="width:5%;">商品</li>
        <li class="tar" style="width:5%;">数量</li>
        <li class="tar" style="width:5%;">积分</li>
        <li class="tar" style="width:10%;">备注</li>
        <li class="tal" style="width:30%;box-sizing:border-box;padding-left:2%;">创建时间</li>
        <li class="tar" style="width:10%;">操作</li>
    </ul>
    <div id="pointRecordss"></div>
</div>
<div class="pageList" id="pagination"   style="text-align: right; margin-top: 5px;"></div>
<div class="empty" style="">
    <img src="${ctx}/static/images/safe/empty.png" alt="">
    <p class="text">暂时没有您的记录</p>
</div>


[#-- 积分记录 --]
<script id="pointRecordsTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
        <ul class="content">
            <li class="tar" style="width:20%;">{{ item.methodDes }}</li>
            <li class="tar" style="width:20%;">{{ item.typeDes }}</li>
            <li class="tar" style="width:20%;">{{ item.point }}</li>
            <li class="tar" style="width:20%;">{{ item.memo }}</li>
            <li class="tal" style="box-sizing:border-box;padding-left:2%;+width:20%;">{{ item.createDate }}</li>
        </ul>
    {{#  }); }}
</script>

[#-- 积分订单 --]
<script id="goodsOrderTpl" type="text/html">
    {{#  $.each(d.rows, function(index, item){ }}
        <ul class="content">
            <li class="tar" style="width:15%;">{{ item.orderNo }}</li>
            <li class="tar" style="width:10%;box-sizing:border-box;padding-right:1%;">{{ item.statusDes }}</li>
            [#--<li class="tar" style="width:10%;box-sizing:border-box;padding-right:1%;">{{ item.logisticsStatusDes }}</li>--]
            <li class="tar" style="width:5%;">{{ item.goodsName }}</li>
            <li class="tar" style="width:5%;">{{ item.quantity }}</li>
            <li class="tar" style="width:5%;">{{ item.point }}</li>
            <li class="tar" style="width:10%;">{{ item.memo }}</li>
            <li class="tal" style="width:30%;box-sizing:border-box;padding-left:2%;">{{ item.createDate }}</li>
            <li class="tal" style="width:10%;box-sizing:border-box;padding-left:2%;">
                {{# if(item.status=='paid' ){ }}
                    <a class="detail-title blue cancelOrder" data-orderId="{{ item.id }}">撤销</a>
                {{# } }}
                {{# if(item.status=='send' ){ }}
                    <a class="detail-title blue confirmReceipt" data-orderId="{{ item.id }}">确认收货</a>
                {{# } }}
                <a href="/uc/goods_order/detail/{{ item.id }}" class="detail-title blue">详情</a>
            </li>
        </ul>
    {{#  }); }}
</script>

[/@insert]

