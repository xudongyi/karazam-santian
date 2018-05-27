<div>
    <form id="mainform">
        <input type="hidden" name="id" value="${role.id}"/>
        <input type="hidden" name="action" value="${action}"/>
        <table class="tbform">
            <tr>
                <td>角色名：</td>
                <td>
                    <input id="name" name="name" type="text" value="${role.name }" class="easyui-textbox"
                           data-options="width: 150,required:'required'"/>
                </td>
            </tr>
            <tr>
                <td>角色编码：</td>
                <td>
                    <input id="role" name="roleCode" type="text" value="${role.roleCode }" class="easyui-textbox" data-options="width: 150,required:'required'" validType="length[0,30]"/>
                </td>
            </tr>
            <tr>
                <td>排序：</td>
                <td>
                    <input id="sort" name="sort" type="text" value="${role.sort}" class="easyui-numberbox" data-options="width: 150"/>
                </td>
            </tr>
            <tr>
                <td>描述：</td>
                <td>
                    <textarea rows="3" cols="30" name="description" style="font-size: 12px;font-family: '微软雅黑'" class="easyui-textbox">${role.description}</textarea>
                </td>
            </tr>
        </table>
    </form>
</div>

<script type="text/javascript">
    var action = "${action}";
    if (action == 'update') {
        $('#roleCode').attr("readonly", true);
        $("#roleCode").css("background", "#eee");
    }

    $('#mainform').form({
        onSubmit: function () {
            var isValid = $(this).form('validate');
            return isValid;	// 返回false终止表单提交
        },
        success: function (data) {
            data = JSON.parse(data);
            successTip(data, dg, d);
        }
    });

</script>