[@nestedscript]
        [@js src="js/admin/content/articlecategory/list.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="文章分类管理"]
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" style="margin-bottom: 5px;">
            名称:<input type="text" name="filter_LIKES_name" class="easyui-textbox"
                       data-options="width:150,prompt: '请输入名称'"/>
            别名:<input type="text" name="filter_LIKES_alias" class="easyui-textbox"
                       data-options="width:150,prompt: '请输入别名'"/>
            <span class="toolbar-item dialog-tool-separator"></span>
            <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search-minus" onclick="query()">查询</a>
        </form>
		[@shiro.hasPermission name="content:articlecategory:add"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square"  onclick="create()">新增分类</a>
		[/@shiro.hasPermission]
		[@shiro.hasPermission name="content:articlecategory:update"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit" onclick="update()">修改分类</a>
		[/@shiro.hasPermission]
		[@shiro.hasPermission name="content:articlecategory:delete"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove" onclick="deleteCategory()">删除分类</a>
		[/@shiro.hasPermission]
        [@shiro.hasPermission name="content:articlecategory:queryarticle"]
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-eye"  onclick="queryArticle()">查看文章</a>
        [/@shiro.hasPermission]

    </div>
</div>
<table id="datagrid"></table>
<div id="dialog"></div>
[/@insert]