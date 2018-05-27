    //禁用form编辑
    $(document).ready(function(){
        $("#borrowingForm textarea").attr("disabled", "disabled"); //对form里面的禁用
        $("#borrowingForm select").attr("disabled", "disabled"); //对form里面的禁用
        $("#borrowingForm input").attr("disabled", "disabled"); //对form里面的禁用
    });

//借款人
$('#borrowing').combobox({
    width: 180,
    method: 'GET',
    url: ctx + '/admin/borrowing/contacts/borrowing/json',
    valueField: 'id',
    textField: 'title',
});