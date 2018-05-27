[@nestedscript]
    [@js src="admin/js/message/push.js" /]
[@nestedscript]
[@insert template="admin/layout/default_layout" title="消息推送"]
<form id="pushForm" method="post">
<table class="tbform">
    <tr>
        <td>通知标题</td>
        <td>
            <input id="notificationTitle" name="notificationTitle" class="easyui-validatebox" data-options="required:'required'"/>
        </td>
    </tr>
    <tr>
        <td>通知类型</td>
        <td>
            <select id="type" class="easyui-combobox" name="type" style="width:200px;">
                <option value="new_project">新标通知</option>
                <option value="system">系统通知</option>
            </select>

        </td>
    </tr>
    <tr>
        <td>标题</td>
        <td>
            <input id="title" name="title" class="easyui-validatebox" data-options="required:'required'"/>
        </td>
    </tr>
    <tr>
        <td>内容</td>
        <td>
            <textarea id="content" name="content" class="easyui-validatebox" data-options="required:'required'"></textarea>
        </td>
    </tr>
</table>
</form>
[/@insert]