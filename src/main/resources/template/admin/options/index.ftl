[@nestedscript]
    [@js src="js/admin/options/options.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="基本设置"]
    <input type="hidden" id="optionsType" value="${type}" />
    <div id="tb" style="padding:5px;height:auto">
        <div>
            [#list ["options:basic:update", "options:referral:update"] as permission]
                [@shiro.hasPermission name=permission]
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="fa fa-edit"
                        onclick="updateOptions('${type}','${type.displayName}')">修改</a>
                    [#break /]
                [/@shiro.hasPermission]
            [/#list]
        </div>
    </div>
    <table id="basicDatagrid"></table>
[/@insert]