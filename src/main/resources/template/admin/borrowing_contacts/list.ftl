[@nestedscript]
    [@js src="js/admin/borrowing_contacts/list.js" /]
    [@js src="js/admin/borrowing_contacts/create.js" /]
    [@js src="js/admin/borrowing_contacts/update.js" /]
    [@js src="js/admin/borrowing_contacts/view.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="借款联系人列表"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'借款联系人列表'" style="width: 500px">
        <div id="tb" style="padding:5px;height:auto">
            <form id="searchFrom" style="margin: 5px;">
                <div>
                    <select name="progress" class="easyui-combobox" data-options="width:160,required:'required'">
                            <option value="">--请选择--</option>
                        [#list contactsTypes as contactsType]
                            <option value="${contactsType}">${contactsType.displayName}</option>
                        [/#list]
                    </select>
                    <input type="text" name="filter_LIKES_name" class="easyui-textbox"
                           data-options="width:150,prompt: '姓名'"/>
                    <input type="text" name="filter_LIKES_mobile" class="easyui-textbox"
                           data-options="width:150,prompt: '手机号码'"/>
                    <input type="text" name="filter_LIKES_telephone" class="easyui-textbox"
                           data-options="width:150,prompt: '座机号码'"/>
                    <input type="text" name="filter_createDateStart" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                           data-options="width:150,showSeconds:true,prompt: '创建日期起'"/>
                    - <input type="text" name="filter_createDateEnd" class="easyui-datetimebox" datefmt="yyyy-MM-dd"
                             data-options="width:150,showSeconds:true,prompt: '创建日期止'"/>
                    <span class="toolbar-item dialog-tool-separator"></span>
                    <a href="javascript:(0)" class="easyui-linkbutton" iconCls="fa fa-search"  onclick="query()">查询</a>
                </div>
            </form>
            <div>
                [@shiro.hasPermission name="contacts:view"]
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-eye'"  onclick="view()">查看</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="contacts:create"]
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-plus-square'"  onclick="create()">新增</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="contacts:update"]
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-edit'"  onclick="update()">修改</a>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="contacts:delete"]
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-remove'"  onclick="deleteRecord()">删除</a>
                [/@shiro.hasPermission]
            </div>
        </div>

        <table id="datagrid"></table>
        <div id="dialog"></div>
    </div>
</div>
[/@insert]