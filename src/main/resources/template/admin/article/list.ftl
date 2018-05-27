[@nestedscript]
    [@js src="js/admin/content/article/list.js" /]
    [@js src="lib/jquery/jquery.ajaxfileupload.js" /]
    [@js src="js/admin/content/article/add.js" /]
    [@js src="ueditor/ueditor.config.js" /]
    [@js src="ueditor/ueditor.all.js" /]
    [@js src="js/admin/content/article/edit.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="文章管理"]
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" action="">
            文章标题:<input type="text" name="filter_LIKES_title" class="easyui-textbox"
                      data-options="width:150,prompt: '请输入标题'"/>
            文章别名:<input type="text" name="filter_LIKES_alias" class="easyui-textbox"
                      data-options="width:150,prompt: '请输入别名'"/>
            <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search-minus" onclick="query()">查询</a>
        </form>
        [@shiro.hasPermission name="content:article:add"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square"  onclick="create()">新增文章</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:article:update"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit" onclick="update()">修改文章</a>
            <span class="toolbar-item dialog-tool-separator"></span>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:article:delete"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove"  onclick="deleteArticle()">删除文章</a>
        [/@shiro.hasPermission]
    </div>
</div>
<table id="datagrid"></table>
<div id="dialog"></div>
[/@insert]