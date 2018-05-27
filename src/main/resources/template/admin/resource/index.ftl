[@nestedscript]
    [@js src="js/admin/resource.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="资源管理"]
<div class="easyui-layout" data-options="fit: true">
    <div data-options="region:'west',split:true,border:false,title:'菜单列表'" style="width: 300px">
        <div id="menuTb" style="padding:5px;height:auto">
            [@shiro.hasPermission name="resource:create"]
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square" onclick="addMenu()">添加</a>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="resource:update"]
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit" onclick="updMenu()">修改</a>
            [/@shiro.hasPermission]
            [@shiro.hasPermission name="resource:delete"]
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove" onclick="delMenu()">删除</a>
            [/@shiro.hasPermission]
        </div>
        <table id="menuDg"></table>
    </div>
    <div data-options="region:'center',split:true,border:false,title:'权限列表'">
        [@shiro.hasRole name="admin"]
            <div id="permissionTb" style="padding:5px;height:auto">
                [@shiro.hasPermission name="resource:create"]
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square" onclick="addPermission()">添加</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="resource:update"]
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit" onclick="updPermission()">修改</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="resource:delete"]
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove" onclick="delPermission()">删除</a>
                [/@shiro.hasPermission]
            </div>
        [/@shiro.hasRole]
        <table id="permissionDg"></table>
    </div>
    <div id="resourceDag"></div>
    <div id="icon_dlg"></div>
</div>
[/@insert]
