/**
 * Created by zhutao on 2017/06/01.
 */
var dg;
var dialog;
dg = $('#dg').datagrid({
    method: "get",
    url: ctx + '/admin/token/list.do',
    fit: true,
    fitColumns: true,
    border: true,
    idField: 'id',
    striped: true,
    pagination: true,
    pageNumber: 1,
    pageSize: 20,
    pageList: [10, 20, 30, 40, 50],
    singleSelect: true,
    columns: [[
        {field: 'id', title: 'ID', width: 60},
        {field: 'addr', title: '手机号',width: 120},
        {field: 'code', title: '验证码',width: 120},
        {field: 'count', title: '发送次数',width: 120},
        {field: 'expiry', title: '到期时间',width: 100},
        {field: 'retry', title: '重发时间',width: 110}
    ]],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar: '#tb'
});