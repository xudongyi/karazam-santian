[@nestedscript]
    [@js src="js/admin/content/ad_position/list_id.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="链接管理"]
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" action="">
        </form>
        [@shiro.hasPermission name="content:ad:add"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square"  onclick="create()">新增</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:ad:update"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit" onclick="update()">修改</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:ad:delete"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove"  onclick="deleteAd()">删除</a>
        [/@shiro.hasPermission]
    </div>
</div>
<table id="datagrid"></table>
<div id="dialog"></div>
<script>
    var position = "${position}";
</script>
[/@insert]