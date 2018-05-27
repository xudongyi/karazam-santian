var datagrid;
var dialog;
datagrid = $('#datagrid').datagrid({
    method: "get",
    url: ctx+'/admin/ad_position/pageListById/'+position,
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
        {field: 'title', title: '标题', sortable: true},
        {field: 'positionName', title: '广告位', sortable: true},
        {field: 'typeStr', title: '广告类型', sortable: true},
        {field: 'startDate', title: '开始时间', sortable: true},
        {field: 'endDate', title: '结束时间', sortable: true},
        {field: 'sort', title: '排序', sortable: true},
        {field: 'createDate', title: '创建时间', sortable: true}
    ]],
    headerContextMenu: [
        {
            text: "冻结该列", disabled: function (e, field) {
            return datagrid.datagrid("getColumnFields", true).contains(field);
        },
            handler: function (e, field) {
                datagrid.datagrid("freezeColumn", field);
            }
        },
        {
            text: "取消冻结该列", disabled: function (e, field) {
            return datagrid.datagrid("getColumnFields", false).contains(field);
        },
            handler: function (e, field) {
                datagrid.datagrid("unfreezeColumn", field);
            }
        }
    ],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar: '#tb'
});

//弹窗输入新增链接信息
function create() {
    Keasy.dialog({
        title: '新增广告',
        width: 540,
        height: 550,
        href: ctx+'/admin/ad/addEdit?position='+ position,
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
    Keasy.dialog({
        title: '修改广告',
        width: 540,
        height: 550,
        href: ctx+'/mng/ad/updateEdit/'+row.id+'?position='+position,
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

    console.log(formData);
    if(formData.action=='add'){
        Keasy.post("/admin/ad/save", formData, function (data) {
            successTip(data, datagrid);
        })
    }
    if(formData.action=='update'){
        Keasy.post("/admin/ad/update/"+formData.pk, formData, function (data) {
            console.log(data);
            successTip(data, datagrid);
        })
    }
}