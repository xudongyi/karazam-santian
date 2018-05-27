[@nestedscript]
    [@js src="ueditor/ueditor.config.js" /]
    [@js src="ueditor/ueditor.all.js" /]
    [@js src="js/admin/borrowing/borrowing.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="借款列表"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'借款列表'" style="width: 500px">
        <div id="tb" style="padding:5px;height:auto">
            <form id="searchForm" style="margin-bottom: 5px;">
                <div>
                    <select name="progress" value="${borrowing.type}" class="easyui-combobox" data-options="width:160,required:'required'">
                        <option value="">全部</option>
                        [#list progresses as progress]
                            <option value="${progress}">${progress.displayName}</option>
                        [/#list]
                    </select>
                    <input type="text" name="filter_LIKES_title" class="easyui-textbox"
                           data-options="width:150,prompt: '标题'"/>
                    <input type="text" name="filter_GTM_amount" class="easyui-numberbox"
                           data-options="width:150,min:0,precision:2,prefix:'￥',prompt: '借款金额起'"/>
                    - <input type="text" name="filter_LTM_amount" class="easyui-numberbox"
                             data-options="width:150,min:0,precision:2,prefix:'￥',prompt: '借款金额止'"/>
                    <input type="text" name="filter_GTD_createDate" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                           data-options="width:150,showSeconds:true,prompt: '创建日期起'"/>
                    - <input type="text" name="filter_LTD_createDate" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                             data-options="width:150,showSeconds:true,prompt: '创建日期止'"/>
                    <span class="toolbar-item dialog-tool-separator"></span>
                    <a href="javascript:query();" class="easyui-linkbutton" iconCls="fa fa-search">查询</a>
                </div>
            </form>
            <div>
                [@shiro.hasPermission name="borrowing:view"]
                    <a href="javascript:view();" class="easyui-linkbutton" iconCls="fa fa-eye" [#--onclick="view()"--]>查看</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="borrowing:view"]
                    <a href="javascript:copy();" class="easyui-linkbutton" iconCls="fa fa-copy" [#--onclick="copy()"--]>复制</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="borrowing:apply"]
                    <a href="javascript:apply();" class="easyui-linkbutton" iconCls="fa fa-plus" [#--onclick="apply()"--]>申请</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="borrowing:update"]
                    <a href="javascript:update();" class="easyui-linkbutton update-button" iconCls="fa fa-edit" [#--onclick="update()"--]>修改</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="borrowing:inquiry"]
                    <a href="javascript:inquiry();" class="easyui-linkbutton inquiry-button" iconCls="fa fa-binoculars" [#--onclick="inquiry()"--]>调查</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="borrowing:approval"]
                    <a href="javascript:approval();" class="easyui-linkbutton approval-button" iconCls="fa fa-crosshairs" [#--onclick="approval()"--]>审批</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="borrowing:lending"]
                    <a href="javascript:lending();" class="easyui-linkbutton lending-button" iconCls="fa fa-sign-out" [#--onclick="lending()"--]>出借</a>
                [/@shiro.hasPermission]
            </div>
        </div>
        <table id="dg"></table>
        <div id="dialog"></div>
    </div>
</div>
[/@insert]