[@nestedscript]
    [@js src="js/admin/transfer/list.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="转让列表"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'转让列表'" style="width: 500px">
        <div id="tb" style="padding:5px;height:auto">
            <form id="searchForm" style="margin-bottom: 5px;">
                <div>
                    <input type="text" name="mobile" class="easyui-textbox"
                           data-options="width:150,prompt: '转让人手机号'"/>
                    <span class="toolbar-item dialog-tool-separator"></span>
                    <a href="javascript:query();" class="easyui-linkbutton" iconCls="fa fa-search">查询</a>
                </div>
            </form>
        [#--<div>--]
        [#--[@shiro.hasPermission name="borrowing:view"]--]
        [#--<a href="javascript:view();" class="easyui-linkbutton" iconCls="fa fa-eye" --][#--onclick="view()"--][#-->查看</a>--]
        [#--[/@shiro.hasPermission]--]
        [#--</div>--]
        </div>
        <table id="dg"></table>
        <div id="dialog"></div>
    </div>
</div>
[/@insert]