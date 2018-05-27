[@nestedscript]
    [@js src="js/admin/user/referralfee.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="推荐费"]
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" style="margin-bottom: 5px;">
            推荐人手机号:<input type="text" name="filter_LIKES_c.mobile" class="easyui-textbox"
                   data-options="width:150,prompt: '请输入推荐人手机号'"/>
            被推荐人手机号:<input type="text" name="filter_LIKES_d.mobile" class="easyui-textbox"
                   data-options="width:150,prompt: '请输入被推荐人手机号'"/>
            结算状态:<select name="filter_LIKES_a.state" class="easyui-combobox" >
                    <option value="">&nbsp;</option>
                    [#list states as type]
                        <option value="${type}">${type.displayName}</option>
                    [/#list]
            </select>
            <span class="toolbar-item dialog-tool-separator"></span>
            <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search" onclick="query()">查询</a>
        </form>
        [@shiro.hasPermission name="user:referralfee:applyAuditing"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-plus-square'" onclick="applyAuditing()">申请审核</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="user:referralfee:auditing"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-check-square'" onclick="auditing()">审核</a>
        [/@shiro.hasPermission]
    </div>
</div>
<table id="datagrid"></table>
<div id="dialog"></div>
[/@insert]