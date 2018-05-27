[@nestedscript]
    [@js src="admin/js/message/push.js" /]
[/@nestedscript]
 [@insert template="admin/layout/default_layout" title="推送消息列表"]
    <div id="tb" style="padding:5px;height:auto">
        <div>
            [@shiro.hasPermission name="message:push"]
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
                   onclick="pushMessage('${type}','${type.displayName}')">推送</a>
            [/@shiro.hasPermission]
        </div>
    </div>
    <table id="messageDg"></table>
[/@insert]