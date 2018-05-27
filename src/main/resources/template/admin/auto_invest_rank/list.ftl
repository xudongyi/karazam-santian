[@nestedscript]
    [@js src="js/admin/autoinvest/rank.list.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="用户列表"]
<div id="tb" style="padding:5px;height:auto">
    <form id="searchForm" style="margin-bottom: 5px;">
        <div>
            <input type="text" name="mobile" class="easyui-textbox"
                   data-options="width:150,prompt: '手机号'"/>
            <input type="text" name="realName" class="easyui-textbox"
                   data-options="width:150,prompt: '真实姓名'"/>
            <span class="toolbar-item dialog-tool-separator"></span>
            <a href="javascript:query();" class="easyui-linkbutton" iconCls="fa fa-search">查询</a>
        </div>
    </form>
</div>
<table id="dg"></table>
<div id="dialog"></div>
[/@insert]