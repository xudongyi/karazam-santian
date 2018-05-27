[@nestedscript]
    [@js src="js/admin/sysuser.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="管理员管理"]
<div id="sysUserToolBar" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" action="" style="margin-bottom: 5px;">
            <input type="text" name="filter_LIKES_name" class="easyui-textbox"
                   data-options="width:150,prompt: '昵称'"/>
            <input type="text" name="filter_LIKES_mobile" class="easyui-textbox"
                   data-options="width:150,prompt: '电话'"/>
            <input type="text" name="filter_GED_u.createDate" class="easyui-datebox"
                   data-options="width:150,prompt: '开始日期'"/>
            - <input type="text" name="filter_LED_u.createDate" class="easyui-datebox"
                     data-options="width:150,prompt: '结束日期'"/>
            <span class="toolbar-item dialog-tool-separator"></span>
            <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search" onclick="querySysUser()">查询</a>
        </form>

        [@shiro.hasPermission name="sysuser:create"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-plus-square'" onclick="createUser();">添加</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="sysuser:update"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-edit'" onclick="updateUser();">修改</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="sysuser:delete"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-remove'"  onclick="deleteUser()">删除</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="sysuser:user:role"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-user-circle-o'"  onclick="addUserRoles()">关联角色</a>
        [/@shiro.hasPermission]
    </div>
</div>
<table id="sysUserDatagrid"></table>
<div id="sysUserDialog"></div>
[/@insert]