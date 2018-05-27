<div>
    <form id="mainform" action="${ctx}/admin/resource/${action}" method="post">
        <table class="tbform">
            <tr>
                <td style="width: 150px;">菜单名称：</td>
                <td>
                    <input type="hidden" name="id" value="${resource.id }"/>
                    <input type="hidden" name="type" value="MENU"/>
                    <input id="name" name="name" type="text" value="${resource.name }" class="easyui-textbox" data-options="required:'required',validType:'length[2,20]'"/>
                </td>
            </tr>
            <tr>
                <td>菜单路径：</td>
                <td>
                    <input id="url" name="url" type="text" value="${resource.url }" class="easyui-textbox" class="easyui-validatebox"/>
                </td>
            </tr>
            <tr>
                <td>权限编码：</td>
                <td>
                    <input id="permCode" name="permission" type="text" class="easyui-textbox" data-options="required:'required'" value="${resource.permission }"/>
                </td>
            </tr>
            <tr>
                <td>菜单图标：</td>
                <td>
                    <input id="icon" name="icon" type="text" class="easyui-textbox" data-options="required:'required'" value="${resource.icon }"/>
                </td>
            </tr>
            <tr>
                <td>上级菜单：</td>
                <td>
                    <input id="pid" name="parentId" class="easyui-combobox" value="${resource.parentId }"/>
                </td>
            </tr>
            <tr>
                <td>排序：</td>
                <td>
                    <input id="sort" type="text" class="easyui-textbox" name="sort" value="${resource.sort }" class="easyui-numberbox" />
                </td>
            </tr>
            <tr>
                <td>描述：</td>
                <td>
                    <textarea class="easyui-textbox" data-options="width:280,height:50" rows="3" cols="41" name="description">${resource.description}</textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    //父级权限
    var action = "${action}";
    if (action == 'create') {
        $('#pid').val(parentPermId);
    } else if (action == 'update') {
        $('#pid').val(parentPermId);
    }

    //上级菜单
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
            if (successTip(data, menuDg, resourceDag))
                menuDg.treegrid('reload');
        }
    });


</script>