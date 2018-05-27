[@nestedscript]
    <script>var toshow = ${toshow?string("true", "false")}</script>
    [@js src="js/admin/capital/capital.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="资金列表"]
<div id="capitalToolbar" style="padding:5px;height:auto">
    <form id="capitalSearchForm" style="margin-bottom: 5px;">
        手机号:<input type="text" name="filter_LIKES_mobile" class="easyui-textbox" data-options="prompt: '手机号'"/>&nbsp;
        类型:<select class="easyui-combobox" name="filter_EQS_c.type" style="width:200px;">
            <option value="">请选择</option>
            [#list types as type]
                <option value="${type.name()}">${type.displayName}</option>
            [/#list]
        </select>&nbsp;
       方式:<select id="method" class="easyui-combobox" name="filter_EQS_method" style="width:200px;">
            <option value="">请选择</option>
            [#list methods as method]
                <option value="${method.name()}">${method.displayName}</option>
            [/#list]
        </select>&nbsp;
        创建时间:<input type="text" name="createDateBegin" class="easyui-datebox"
               data-options="width:150,prompt: '开始日期'"/>
        - <input type="text" name="createDateEnd" class="easyui-datebox"
                 data-options="width:150,prompt: '结束日期'"/>
        <span class="toolbar-item dialog-tool-separator"></span>&nbsp;
    </form>
    <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search" onclick="capitalQuery()">查询</a>
    <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-file-excel-o" onclick="exportCapitalRecord()">Excel导出</a>
</div>
[/@insert]