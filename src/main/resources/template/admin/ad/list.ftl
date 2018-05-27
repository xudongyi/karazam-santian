[@nestedscript]
    [@js src="js/admin/content/ad/list.js" /]
    [@js src="js/admin/content/ad/add.js" /]
    [@js src="lib/jquery/jquery.ajaxfileupload.js" /]
    [@js src="js/admin/content/ad/edit.js" /]
    [@js src="ueditor/ueditor.config.js" /]
    [@js src="ueditor/ueditor.all.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="链接管理"]
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" action="">
            标题:<input type="text" name="filter_LIKES_title" class="easyui-textbox"
                      data-options="width:150,prompt: '请输入标题'"/>
            [#--文章别名:<input type="text" name="filter_LIKES_alias" class="easyui-validatebox"--]
                      [#--data-options="width:150,prompt: '请输入别名'"/>--]
            <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search-minus"  onclick="query()">查询</a>
        </form>
        [@shiro.hasPermission name="content:ad:add"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square" onclick="create()">新增</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:ad:update"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit"  onclick="update()">修改</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:ad:delete"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove"  onclick="deleteAd()">删除</a>
        [/@shiro.hasPermission]
    </div>
</div>
<table id="datagrid"></table>
<div id="dialog"></div>
[/@insert]