[@nestedscript]
    [@js src="js/admin/role.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="角色管理"]
<div class="easyui-layout" data-options="fit: true">
    <div data-options="region:'west',split:true,collapsible:false,border:false,title:'角色列表'" style="width: 400px;">
        <div id="roleTb" style="padding:5px;height:auto;">
            <div>
                [@shiro.hasPermission name="role:create"]
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square" onclick="addRole();">添加</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="role:update"]
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit" onclick="updRole()">修改</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="role:delete"]
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove" onclick="delRole()">删除</a>
                [/@shiro.hasPermission]
            </div>
        </div>
        <table id="roleDg"></table>
    </div>
    <div data-options="region:'center',split:true,border:false,title:'权限列表'" style="width: 300px;">
        <div id="permissionsTb" style="padding:5px;height:auto">
            <div>
                [@shiro.hasRole name="admin"]
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square" onclick="save();">保存授权</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-refresh" onclick="back()">恢复</a>
                [/@shiro.hasRole]
            </div>
        </div>

        <table id="permissionDg"></table>
    </div>
    <div id="roleDag"></div>
</div>
[/@insert]
