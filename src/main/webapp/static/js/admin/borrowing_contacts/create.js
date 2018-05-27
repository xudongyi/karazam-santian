    //表单提交
    $('#borrowingForm').form({
        onSubmit: function () {
            var isValid = $(this).form('validate');
            return isValid;	// 返回false终止表单提交
        },
        success: function (data) {
            data = JSON.parse(data);
            successTip(data, datagrid, dialog);
        }
    });

//借款人
$('#borrowing').combobox({
    width: 180,
    method: 'GET',
    url: ctx + '/admin/borrowing/contacts/borrowing/json',
    valueField: 'id',
    textField: 'title'
});
