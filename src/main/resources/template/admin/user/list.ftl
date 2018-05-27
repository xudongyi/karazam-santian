[@nestedscript]
    [@js src="js/admin/user/list.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="用户列表"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'用户列表'" style="width: 500px">
        <div id="tb" style="padding:5px;height:auto">
            <form id="searchForm" action="" style="margin-bottom: 5px;">
                <input id="userType" type="hidden" name="type" value="${userType}" >
                <div>
                   [#-- <select name="type" value="用户类型" class="easyui-combobox" data-options="width:160,required:'required',disabled:'disabled'" >
                        --][#--<option value="">--请选择--</option>--][#--
                        [#list types as type]
                            [#if userType == type]
                                <option value="${type}" selected="selected">${type.displayName}</option>
                            [/#if]
                        [/#list]
                    </select>--]
                    <input type="text" name="mobile" class="easyui-textbox"
                           data-options="width:150,prompt: '手机号'"/>
                    <input type="text" name="realName" class="easyui-textbox"
                           data-options="width:150,prompt: '真实姓名'"/>
                    <span class="toolbar-item dialog-tool-separator"></span>
                    <a href="javascript:query();" class="easyui-linkbutton" iconCls="fa fa-search">查询</a>
                    <a href="javascript:queryIpsInfo();" class="easyui-linkbutton" iconCls="fa fa-search">查询托管信息</a>
                    <a href="javascript:exportUser();" class="easyui-linkbutton" iconCls="fa fa-mail-forward">Excel导出</a>
                    [@shiro.hasPermission name="user:list:add"]
                        <a href="javascript:create();" class="easyui-linkbutton" iconCls="fa fa-plus-square" [#--onclick="create()"--]>注册</a>
                    [/@shiro.hasPermission]
                    [@shiro.hasPermission name="user:list:update"]
                        <a href="javascript:update();" class="easyui-linkbutton" iconCls="fa fa-search" >修改</a>
                    [/@shiro.hasPermission]
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