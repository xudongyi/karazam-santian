<div>
    <form id="mainform">
        <input type="hidden" name="id" value="${jobLog.id}"/>
        <input type="hidden" name="action" value="${action}"/>
        <table class="tbform">
            <tr>
                <td>任务ID：</td>
                <td>
                    <input type="text" name="jobId" value="${jobLog.jobId}" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>bean名称：</td>
                <td>
                    <input type="text" name="beanName" value="${jobLog.beanName}" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>方法名称：</td>
                <td>
                    <input type="text" name="methodName" value="${jobLog.methodName}" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>参数：</td>
                <td>
                    <input type="text" name="params" value="${jobLog.params}" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>cron表达式：</td>
                <td>
                    <input type="text" name="cronExpression" value="${jobLog.cronExpression}" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>任务状态：</td>
                <td>
                    <input type="status" name="cronExpression" value="${jobLog.status}" class="easyui-textbox" />
                </td>
            </tr>
            <tr>
                <td>耗时(单位：毫秒)：</td>
                <td>
                    <input type="text" name="times" value="${jobLog.times}" class="easyui-textbox" />
                </td>
            </tr>
            [#if jobLog.error??]
                <tr>
                    <td>耗时(单位：毫秒)：</td>
                    <td>
                        <textarea type="text" name="error" readonly>${jobLog.error}</textarea>
                    </td>
                </tr>
            [/#if]
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