[@nestedscript]
    [@js src="js/admin/borrowing_apply/list.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="前台借款申请"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'前台借款申请'" style="width: 500px">
        <div id="tb" style="padding:5px;height:auto">
            <form id="searchForm" style="margin-bottom: 5px;">
                <div>
                    <select name="borrowingApplyTypeEnum" value="申请借款类型" class="easyui-combobox" data-options="width:160,required:'required'">
                        <option value="">--请选择--</option>
                        [#list borrowTypes as borrowType]
                            <option value="${borrowType}">${borrowType.displayName}</option>
                        [/#list]
                    </select>
                    <select name="borrowingApplyProgressEnum" value="申请借款进度" class="easyui-combobox" data-options="width:160,required:'required'">
                        <option value="">--请选择--</option>
                        [#list borrowingApplyProgresses as borrowingApplyProgress]
                            <option value="${borrowingApplyProgress}">${borrowingApplyProgress.displayName}</option>
                        [/#list]
                    </select>
                    <input type="text" name="mobile" class="easyui-textbox"
                           data-options="width:150,prompt: '手机号'"/>
                    <input type="text" name="userName" class="easyui-textbox"
                           data-options="width:150,prompt: '姓名'"/>
                    <span class="toolbar-item dialog-tool-separator"></span>
                    <a href="javascript:query();" class="easyui-linkbutton" iconCls="fa fa-search">查询</a>
                </div>
            </form>
            <div>
                [@shiro.hasPermission name="borrowing:apply:connected"]
                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="fa fa-handshake-o" onclick="setToConnected()">设为已联系</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="borrowing:apply:reject"]
                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="fa fa-remove" onclick="applyReject()">驳回</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="borrowing:apply:success"]
                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="fa fa-check" onclick="applySuccess()">已通过</a>
                [/@shiro.hasPermission]
            </div>
        </div>
        <table id="dg"></table>
        <div id="dialog"></div>
    </div>
</div>
[/@insert]