[@nestedscript]
    [@js src="js/admin/content/articlecategory/list_id.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="文章分类管理"]
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" action="">
            <input id="level" name="level" value="children" hidden/>
        </form>
		[@shiro.hasPermission name="content:articlecategory:add"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square"  onclick="create()">新增分类</a>
		[/@shiro.hasPermission]
		[@shiro.hasPermission name="content:articlecategory:update"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit"  onclick="update()">修改分类</a>
		[/@shiro.hasPermission]
		[@shiro.hasPermission name="content:articlecategory:delete"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove" onclick="deleteCategory()">删除分类</a>
		[/@shiro.hasPermission]
        [@shiro.hasPermission name="content:articlecategory:queryarticle"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"  onclick="queryArticle2()">查看文章</a>
        [/@shiro.hasPermission]
    </div>
</div>
<table id="datagrid"></table>
<div id="dialog"></div>
<script>
    var parentId = "${id}";
</script>
[/@insert]