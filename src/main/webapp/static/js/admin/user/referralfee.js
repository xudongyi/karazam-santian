var datagrid;
var dialog;
datagrid = $('#datagrid').datagrid({
    method: "get",
    url: ctx+'/admin/referralfee/list.json',
    fit: true,
    fitColumns: true,
    border: false,
    striped: true,
    pagination: true,
    pageNumber: 1,
    pageSize: 20,
    pageList: [10, 20, 30, 40, 50],
    singleSelect: false,
    columns: [[
        {field: 'id', title: '记录ID', width:80},
        {field: 'userId', title: 'userId',  hidden: true},
        {field: 'reUserId', title: 'reUserId',  hidden: true},
        // {field: 'userNickName', title: '推荐人昵称', width:150},
        // {field: 'userMobile', title: '推荐人电话号码', width:180},
        // {field: 'reUserNickName', title: '被推荐人昵称', width:150},
        // {field: 'reUserMobile', title: '被推荐人电话号码', width:180},
        {field: 'state', title: 'state',  hidden: true},
        {field: 'stateStr', title: '结算状态', width:150},
        {field: 'referralAmt', title: '推荐金额', width:150},
        {field: 'referralFeeRate', title: '推荐费率', width:150},
        {field: 'referralFee', title: '推荐费', width:150},
        {field: 'planPaymentDate', title: '计划结算日期', width:180},
        {field: 'paymentDate', title: '实际结算日期', width:180},
        {field: 'memo', title: '备注', width:150},
        {field: 'operator', title: '操作员', width:150},
        {field: 'ip', title: 'ip', width:150}
    ]],
    toolbar: '#tb'
});

//申请审核
function applyAuditing() {
    var ids = [];
    var rows = datagrid.datagrid('getSelections');
    for(var i=0; i<rows.length; i++){
        ids.push(rows[i].id);
        //如果状态不为“待申请”，则无法结算
        if (rows[i].state != "WAIT_APPLY") {
            $.messager.alert("提示","包含不为'待申请'状态的数据，无法申请");
                return;
        }
    }
    if(ids.length<1){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    $.messager.confirm('确认对话框', '共'+ ids.length +'条数据，确认申请审核这些费用？', function(r){
        if (r){
            Keasy.delete("/admin/referralfee/applyAuditing/"+ids,{},function (r) {
                successTip(r,datagrid);
            });
        }
    });
}
//审核
function auditing() {
    var ids = [];
    var rows = datagrid.datagrid('getSelections');
    if(rows.length<1){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    if(rows.length>1){
        $.messager.alert("提示","只能选择一行数据");
        return;
    }
    for(var i=0; i<rows.length; i++){
        ids = rows[i].id;
        //如果状态不为“待申请”，则无法结算
        if (rows[i].state != "APPLYING") {
            $.messager.alert("提示","包含不为'待审核'状态的数据，无法申请");
            return;
        }
    }
    $.messager.confirm('确认对话框', '共1条数据,确认审核这些费用？', function(r){
        if (r){
            dialog = Keasy.dialog({
                title: '审批意见',
                width: 450,
                height: 240,
                href: ctx+'/admin/referralfee/suggestion/'+ids,
                maximizable: true,
                modal: true
            });
            // Keasy.delete("/mng/referralfee/auditing/"+ids,{},function (r) {
            //     successTip(r,datagrid);
            // });
        }
    });
}
function offlinepaid() {
    var ids = [];
    var rows = datagrid.datagrid('getSelections');
    for(var i=0; i<rows.length; i++){
        ids.push(rows[i].id);
        //如果状态不为“待申请”，则无法结算
        if (rows[i].state == "paid") {
            $.messager.alert("提示","包含'已结算'状态的数据，请重新选择!");
            return;
        }
    }
    if(ids.length<1){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    $.messager.confirm('确认对话框', '共'+ ids.length +'条数据,确认线下结算这些费用？', function(r){
        if (r){
            Keasy.delete("/admin/referralfee/offlinepaid/"+ids,{},function (r) {
                successTip(r,datagrid);
            });
        }
    });
}
function doConfirmDialog(formData) {

    if(formData.action=='suggestion'){
        Keasy.post("/admin/referralfee/auditing", formData, function (data) {
            successTip(data, datagrid, dialog);
            if (data.success) {
                referralFeeFormSubmit(data.data.requestUrl, data.data.parameterMap);
            }
        })
    }
}
//查询
function query() {
    var obj = $("#searchFrom").serializeObject();
    datagrid.datagrid('load', obj);
}

function referralFeeFormSubmit(url, data) {
    var form = document.createElement("form");
    document.body.appendChild(form);
    form.method = 'post';
    form.action = url;
    form.target = '_blank';
    //创建隐藏表单
    $.each(data, function (key, value) {
        var element = document.createElement("input");
        element.setAttribute("name", key);
        element.setAttribute("value", value);
        element.setAttribute("type","hidden");
        form.appendChild(element);
    });

    form.submit();
}