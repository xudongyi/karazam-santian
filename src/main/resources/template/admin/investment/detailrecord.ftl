[@nestedscript]
    <script>var toshow = ${toshow?string("true", "false")}</script>
    [@js src="js/admin/investment/detailrecord.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="投资子记录"]
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" action="">
            投资人姓名:<input type="text" name="filter_LIKES_realName" class="easyui-textbox"
                         data-options="width:150,prompt: '投资人姓名'"/>
            投资人手机号:<input type="text" name="filter_LIKES_mobile" class="easyui-textbox"
                          data-options="width:150,prompt: '投资人手机号'"/>
            <input type="text" name="startDate" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                   data-options="width:150,showSeconds:true,prompt: '投资时间起'"/>
            - <input type="text" name="endDate" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                     data-options="width:150,showSeconds:true,prompt: '投资时间止'"/>
            <span class="toolbar-item dialog-tool-separator"></span>
            <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search" onclick="query()">查询</a>
        </form>
    </div>
</div>
<table id="datagrid"></table>
<div id="dialog"></div>
[/@insert]