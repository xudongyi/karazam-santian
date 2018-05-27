[@insert template="admin/layout/default_layout" title="app"]
<div>
    <form id="mobileappForm" method="post">
        <input type="hidden" name="id" value="${app.id}"/>
        <input type="hidden" name="action" value="${action}"/>
        <table class="tbform" style="margin-top: 20px">
            <tr>
                <td>版本名称: </td>
                <td>
                    <input id="versionName" name="versionName" class="easyui-textbox" data-options="required:'required'" value="${app.versionName}"/>
                </td>
            </tr>
            <tr>
                <td>包名: </td>
                <td>
                    <input id="packageName" name="packageName" class="easyui-textbox" data-options="required:'required'" value="${app.packageName}"/>
                </td>
            </tr>
            <tr>
                <td>应用URL: </td>
                <td>
                    <input id="appUrl" name="appUrl" class="easyui-textbox" value="${app.appUrl}"/>
                </td>
            </tr>
            <tr>
                <td>更新日志: </td>
                <td>
                    <textarea id="changeLog" name="changeLog" class="easyui-validatebox" data-options="required:'required'">${app.changeLog}</textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
[/@insert]