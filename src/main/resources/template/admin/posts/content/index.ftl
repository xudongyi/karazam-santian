[@nestedscript]
    [@js src="js/admin/posts/content.list.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="文章列表"]
<div id="contentBar" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" action="" style="margin-bottom: 5px;">
            分类：<select id="categoryId" name="categoryId" class="easyui-combotree" data-options="width:200"></select>
            <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search" onclick="queryContents()">查询</a>
        </form>
    </div>
    <div>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-edit'" onclick="updateContent();">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-remove'" onclick="deleteContent()">删除</a>
        <a id="publishContent" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-arrow-up'" onclick="publishContent()" style="display: none;">发布</a>
        <a id="draftContent" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-briefcase'" onclick="draftContent()" style="display: none;">草稿</a>
        <a id="garbageContent" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-cart-arrow-down'" onclick="garbageContent()" style="display: none;">回收</a>
    </div>
</div>
<table id="contentDatagrid"></table>
[/@insert]