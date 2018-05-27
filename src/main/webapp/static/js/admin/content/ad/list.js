var datagrid;
var dialog;
datagrid = $('#datagrid').datagrid({
    method: "get",
    url: ctx+'/admin/ad/list',
    fit: true,
    fitColumns: true,
    border: false,
    idField: 'id',
    striped: true,
    pagination: true,
    rownumbers: true,
    pageNumber: 1,
    pageSize: 20,
    pageList: [10, 20, 30, 40, 50],
    singleSelect: true,
    columns: [[
        {field: 'id', title: 'id', hidden: true},
        {field: 'title', title: '标题', sortable: true, width:120},
        {field: 'positionName', title: '广告位', sortable: true, width:120},
        {field: 'typeStr', title: '广告类型', sortable: true, width:120},
        {field: 'startDate', title: '开始时间', sortable: true, width:120},
        {field: 'endDate', title: '结束时间', sortable: true, width:120},
        {field: 'sort', title: '排序', sortable: true, width:120},
        {field: 'createDate', title: '创建时间', sortable: true, width:120}
    ]],
    toolbar: '#tb'
});

//弹窗输入新增链接信息
function create() {
    dialog= Keasy.dialog({
        title: '新增广告',
        width: 540,
        height: 550,
        href: ctx+'/admin/ad/addEdit',
        maximizable: true,
        modal: true
    });
}
//弹窗输入修改链接
function update() {
    var row = datagrid.datagrid('getSelected');

    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    dialog= Keasy.dialog({
        title: '修改广告',
        width: 540,
        height: 550,
        href: ctx+'/admin/ad/updateEdit/'+row.id,
        maximizable: true,
        modal: true
    });
}
function deleteAd() {
    var row = datagrid.datagrid('getSelected');
    //var rows = { "userId": row.userId, "userInfoId": row.userInfoId };
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    $.messager.confirm('确认对话框', '您想要删除该广告信息吗？', function(r){
        if (r){
            Keasy.delete("/admin/ad/delete/"+row.id,{},function (data) {
                successTip(data, datagrid);
            })
        }
    });
}

//查询
function query() {
    var obj = $("#searchFrom").serializeObject();
    datagrid.datagrid('load', obj);
}

function doConfirmDialog(formData) {
    if(formData.action=='add'){
        if (formData.startDate != '') {
            formData.startDate = formData.startDate + " 00:00:00";
        }
        if(formData.endDate!=''){
            formData.endDate=formData.endDate+" 24:59:59";
        }
        Keasy.post("/admin/ad/save", formData, function (data) {
            successTip(data, datagrid,dialog);
        })
    }
    if(formData.action=='update'){
        if (formData.startDate != '') {
            formData.startDate = formData.startDate + " 00:00:00";
        }
        if(formData.endDate!=''){
            formData.endDate=formData.endDate+" 24:59:59";
        }
        Keasy.put("/admin/ad/update/"+formData.pk, formData, function (data) {
            successTip(data, datagrid,dialog);
        })
    }
}