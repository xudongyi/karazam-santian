[@nestedscript]
    [@js src="js/admin/user/corporate.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="企业列表"]
<script>

</script>
<div id="corporationManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'企业列表'" style="width: 500px">
        <div id="tb" style="padding:5px;height:auto">
            <form id="searchForm" style="margin-bottom: 5px;">
                <input type="text" name="filter_LIKES_corpName" class="easyui-textbox"
                       data-options="width:150,prompt: '公司名称'"/>
                <input type="text" name="filter_LIKES_corpType" class="easyui-textbox"
                       data-options="width:150,prompt: '公司类别'"/>
                <input type="text" name="filter_LIKES_corpDomain" class="easyui-textbox"
                       data-options="width:150,prompt: '公司行业'"/>
                <span class="toolbar-item dialog-tool-separator"></span>
                <a href="javascript:query();" class="easyui-linkbutton" iconCls="fa fa-search">查询</a>
                 <a href="javascript:updateCorporate();"class="easyui-linkbutton modifyCorporate" iconCls="fa fa-search">修改</a>
                <a href="javascript:viewCorporate();"class="easyui-linkbutton modifyCorporate" iconCls="fa fa-search">查看</a>
            </form>
        </div>
        <table id="dg"></table>
        <div id="dialog"></div>
    </div>
</div>
[/@insert]