[@nestedscript]
    [@js src="js/admin/content/ad_position/list.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="广告位管理"]
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" action="">
            名称:<input type="text" name="filter_LIKES_name" class="easyui-textbox"
                      data-options="width:150,prompt: '请输入名称'"/>
            [#--文章别名:<input type="text" name="filter_LIKES_alias" class="easyui-validatebox"--]
                      [#--data-options="width:150,prompt: '请输入别名'"/>--]
            [#--<span class="toolbar-item dialog-tool-separator"></span>--]
            <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search-minus" onclick="query()">查询</a>
        </form>
        [@shiro.hasPermission name="content:ad_position:add"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square" plain="true" onclick="create()">新增</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:ad_position:update"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit" plain="true" onclick="update()">修改</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:ad_position:delete"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove" plain="true" onclick="deleteAdPosition()">删除</a>
        [/@shiro.hasPermission]
    </div>
</div>
<table id="datagrid"></table>
<div id="dialog"></div>
[/@insert]
