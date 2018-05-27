[@nestedscript]
    [@js src="js/admin/content/link/list.js" /]
    [#--[@js src="lib/jquery/jquery.ajaxfileupload.js" /]--]
    [#--[@js src="js/admin/content/link/add.js" /]--]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="链接管理"]
    <div id="tb" style="padding:5px;height:auto">
        <form id="searchFrom" style="margin-bottom: 5px;">
            名称:<input type="text" name="filter_LIKES_name" class="easyui-textbox"
                      data-options="width:150,prompt: '请输入名称'"/>
            文章别名:<input type="text" name="filter_LIKES_alias" class="easyui-textbox"
                      data-options="width:150,prompt: '请输入别名'"/>
            <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search-minus" onclick="query()">查询</a>
        </form>
        [@shiro.hasPermission name="content:link:add"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square"  onclick="create()">新增</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:link:update"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit"  onclick="update()">修改</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:link:delete"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove"  onclick="deleteLink()">删除</a>
        [/@shiro.hasPermission]
    </div>
    <table id="linksDg"></table>
    <div id="linksDag"></div>
[/@insert]