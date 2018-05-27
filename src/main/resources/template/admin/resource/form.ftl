<div>
    <form id="mainform" action="${ctx}/admin/resource/${action}" method="post">
        <table class="tbform">
            <tr>
                <td>权限名称：</td>
                <td>
                    <input type="hidden" name="id" value="${resource.id }"/>
                    <input type="hidden" name="type" value="BUTTON" />
                    <input type="hidden" name="parentId" value="${resource.parentId }" />
                    <input id="name" name="name" type="text" value="${resource.name }" class="easyui-textbox" data-options="width: 180,required:'required',validType:'length[2,20]'"/>
                </td>
            </tr>
            <tr>
                <td>权限编码：</td>
                <td>
                    <input id="permCode" name="permission" type="text" class="easyui-textbox" data-options="width: 180,required:'required'" value="${resource.permission }"/>
                </td>
            </tr>
            <tr>
                <td>排序：</td>
                <td>
                    <input id="sort" type="text" name="sort" value="${resource.sort }" class="easyui-numberbox" data-options="width: 180"/>
                </td>
            </tr>
            <tr>
                <td>描述：</td>
                <td><textarea rows="3" cols="41" name="description">${resource.description}</textarea></td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    //父级权限
    $('input[name=parentId]').val(parentPermId);

    //菜单类型
    $('#type').combobox({
        width: 180,
        panelHeight: 50
    });

    //上级权限
    $('#pid').combotree({
        width: 180,
        method: 'GET',
        url: '${ctx}/admin/resource/menu/json',
        idField: 'id',
        textFiled: 'name',
        parentField: 'parentId',
        iconCls: 'icon',
        animate: true
    });

    $('#mainform').form({
        onSubmit: function () {
            var isValid = $(this).form('validate');
            return isValid;	// 返回false终止表单提交
        },
        success: function (data) {
            data = JSON.parse(data);
            successTip(data, permissionDg, resourceDag);
        }
    });
</script>