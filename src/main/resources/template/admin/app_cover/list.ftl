[@nestedscript]
    [@js src="js/admin/app_cover/list.js" /]
    [@js src="js/admin/app_cover/add.js" /]
    [@js src="lib/jquery/jquery.ajaxfileupload.js" /]
    [@js src="js/admin/app_cover/edit.js" /]
    [@js src="ueditor/ueditor.config.js" /]
    [@js src="ueditor/ueditor.all.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="APP启动页"]
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" action="">
            [#--标题:<input type="text" name="filter_LIKES_title" class="easyui-textbox"--]
                      [#--data-options="width:150,prompt: '请输入标题'"/>--]

            [#--<a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search-minus"  onclick="query()">查询</a>--]
        </form>

            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-plus-square" onclick="create()">新增</a>

            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit"  onclick="update()">修改</a>


            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-remove"  onclick="deleteAppCover()">删除</a>

    </div>
</div>
<table id="datagrid"></table>
<div id="dialog"></div>
[/@insert]