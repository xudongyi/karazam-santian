[@nestedscript]
    [@js src="js/admin/capital/platform.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="平台资金记录"]
<div id="capitalToolbar" style="padding:5px;height:auto">
        <form id="searchFrom" style="margin-bottom: 5px;">
            操作员:<input type="text" name="operator" class="easyui-textbox"
                       data-options="width:150,prompt: '请输入操作员'"/>
            类型:<select class="easyui-combobox" name="type" style="width:200px;">
                    <option value="">请选择</option>
                    [#list types as type]
                        <option value="${type.name()}">${type.displayName}</option>
                    [/#list]
                </select>
            方式:<select class="easyui-combobox" name="method" style="width:200px;">
                    <option value="">请选择</option>
                    [#list methods as method]
                        <option value="${method.name()}">${method.displayName}</option>
                    [/#list]
                </select>
            起止时间:<input type="text" id="createDateBegin" name="createDateBegin" class="easyui-datebox"
                        data-options="width:150,prompt: '请输入开始时间'"/>
            &nbsp;&nbsp;至&nbsp;&nbsp;:<input type="text" id="createDateEnd" name="createDateEnd" class="easyui-datebox"
                                             data-options="width:150,prompt: '请输入结束时间'"/>
            <span class="toolbar-item dialog-tool-separator"></span><br>
        </form>
    <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search" onclick="query()">查询</a>
    <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-file-excel-o" onclick="exportExcelPlatForm()">Excel导出</a>
</div>
[/@insert]