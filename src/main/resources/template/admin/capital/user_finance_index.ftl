[@nestedscript]
    <script>var toshow = ${toshow?string("true", "false")}</script>
    [@js src="js/admin/capital/user.finance.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="用户资金列表"]
<div id="tb" style="padding:5px;height:auto">
    <form id="searchFrom" style="margin-bottom: 5px;">
        手机号:<input type="text" name="filter_LIKES_mobile" class="easyui-textbox"
                   data-options="width:150,prompt: '请输入手机号'"/>
        <input type="text" name="filter_GTD_u.create_date" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
               data-options="width:150,showSeconds:true,prompt: '注册日期起'"/>
        - <input type="text" name="filter_LTD_u.create_date" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                 data-options="width:150,showSeconds:true,prompt: '注册日期止'"/>
        <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search" onclick="query()">查询</a>
        [#--<a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search" onclick="queryIpsInfo()">查询托管资金信息</a>--]
    </form>
    <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-file-excel-o" onclick="exportExcelUserFund()">Excel导出</a>
</div>
[/@insert]