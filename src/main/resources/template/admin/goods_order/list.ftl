[@nestedscript]
        [@js src="js/admin/mall/goodsorder/list.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="商品订单管理"]
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" style="margin-bottom: 5px;">
            订单编号:<input type="text" name="filter_LIKES_orderNo" class="easyui-textbox"
                       data-options="width:150,prompt: '请输入订单编号'"/>
            <span class="toolbar-item dialog-tool-separator"></span>
            <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search-minus" onclick="query()">查询</a>
        </form>
		[@shiro.hasPermission name="mall:goodsorder:list"]
            <a href="javascript:sendOut()" class="easyui-linkbutton" iconCls="fa fa-plus-square"  [#--onclick="sendOut()"--]>发货</a>
		[/@shiro.hasPermission]
		[#--[@shiro.hasPermission name="mall:goodsorder:list"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit" onclick="update()">修改</a>
		[/@shiro.hasPermission]--]
    </div>
</div>
<table id="datagrid"></table>
<div id="dialog"></div>
[/@insert]