var datagrid;
var dialog;
datagrid = $('#datagrid').datagrid({
    toolbar: '#tb222',

    method: "get",
    url: ctx+'/admin/borrowing/contacts/list.json',
    fit: true,
    fitColumns: true,
    border: true,
    striped: true,
    pagination: true,
    rownumbers: true,
    pageNumber: 1,
    pageSize: 20,
    pageList: [10, 20, 30, 40, 50],
    singleSelect: true,
    columns: [[
        {field: 'id', title: 'ID',width: 50},
        {field: 'typeDes', title: '类型',width: 60},
        {field: 'name', title: '姓名',width: 60},
        {field: 'mobile', title: '手机号码',width: 70},
        {field: 'telephone', title: '座机号码',width: 70},
        {field: 'memo', title: '备注',width: 70}
    ]],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false
});

//查询
function query() {
    var obj = $("#searchFrom").serializeObject();
    datagrid.datagrid('load', obj);
}

//弹窗 查看
function view() {
    var row = datagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    dialog = $("#dialog").dialog({
        title: '查看',
        width: 600,
        height: 400,
        href: ctx + '/admin/borrowing/contacts/view/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '取消',
            handler: function () {
                dialog.panel('close');
            }
        }]
    });
}

//弹窗 新增
function create() {
    dialog = $("#dialog").dialog({
        title: '新增',
        width: 600,
        height: 400,
        href: ctx + '/admin/borrowing/contacts/create',
        maximizable: true,
        modal: true,
        buttons: [{
            text: '确认',
            handler: function () {
                $("#borrowingForm").submit();
            }
        }, {
            text: '取消',
            handler: function () {
                dialog.panel('close');
            }
        }]
    });
}

//弹窗 修改
function update() {
    var row = datagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    dialog = $("#dialog").dialog({
        title: '修改',
        width: 600,
        height: 400,
        href: ctx + '/admin/borrowing/contacts/update/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '修改',
            handler: function () {
                $('#borrowingForm').submit();
            }
        }, {
            text: '取消',
            handler: function () {
                dialog.panel('close');
            }
        }]
    });
}

//删除
function deleteRecord() {
    var row = datagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            Keasy.delete(ctx + "/admin/borrowing/contacts/delete/" + row.id, {}, function (result) {
                successTip(result, datagrid);
            });
        }
    });
}