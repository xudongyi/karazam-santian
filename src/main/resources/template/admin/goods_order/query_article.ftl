[#--
[[@nestedscript]
    [@js src="js/admin/content/articlecategory/query_article.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="文章管理"]
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" action="">
        </form>
        [@shiro.hasPermission name="content:article:add"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square" onclick="create()">新增文章</a>
            <span class="toolbar-item dialog-tool-separator"></span>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:article:update"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit" onclick="update()">修改文章</a>
            <span class="toolbar-item dialog-tool-separator"></span>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="content:article:delete"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove"  onclick="deleteArticle()">删除文章</a>
            <span class="toolbar-item dialog-tool-separator"></span>
        [/@shiro.hasPermission]
    </div>
</div>
<table id="datagrid"></table>
<div id="dialog"></div>
<script>
    var parentId = "${id}";
</script>
[/@insert]--]
