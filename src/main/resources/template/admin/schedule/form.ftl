<div>
    <form id="mainform">
        <input type="hidden" name="id" value="${job.id}"/>
        <input type="hidden" name="action" value="${action}"/>
        <table class="tbform">
            <tr>
                <td>bean名称：</td>
                <td>
                    <input type="text" name="beanName" value="${job.beanName}" class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>方法名称：</td>
                <td>
                    <input type="text" name="methodName" value="${job.methodName}" class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>参数：</td>
                <td>
                    <input type="text" name="params" value="${job.params}" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>cron表达式：</td>
                <td>
                    <input type="text" name="cronExpression" value="${job.cronExpression}" class="easyui-textbox" data-options="required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>备注：</td>
                <td>
                    <input type="text" name="remark" value="${job.remark}" class="easyui-textbox" />
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var action = "${action}";
    if (action == 'view') {
        $("input").attr('readonly', 'readonly');
        $("input").css('background', '#eee');
    }

</script>