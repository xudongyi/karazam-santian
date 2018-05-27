/**
 * Created by zhutao on 2017/5/16.
 */
var dg;
var dialog;
dg = $('#dg').datagrid({
    method: "get",
    url: ctx + '/admin/transfer/list.json',
    fit: true,
    fitColumns: true,
    border: true,
    idField: 'id',
    striped: true,
    pagination: true,
    pageNumber: 1,
    pageSize: 20,
    pageList: [10, 20, 30, 40, 50],
    columns: [[
        {field: 'id', title: 'ID', width: 50},
        {field: 'transferMobile', title: '转让人手机号',width: 80},
        {field: 'transferRealName', title: '转让人真实姓名',width: 90},
        {field: 'borrowingId', title: '标的ID',width: 50},
        {field: 'typeStr', title: '标的类型',width: 70},
        {field: 'repaymentMethodStr', title: '标的还款方式',width: 100},
        {field: 'enumStateStr', title: '状态',width: 80},
        {field: 'capital', title: '转让本金',width: 80},
        {field: 'worth', title: '总价值',width: 80},
        {field: 'surplusWorth', title: '剩余价值',width: 80},
        {field: 'transferedCapital', title: '已转让本金',width: 80},
        {field: 'inFee', title: '转入手续费',width: 80},
        {field: 'outFee', title: '转出手续费',width: 80},
        {field: 'lastDate', title: '上次转出时间',width: 100},
        {field: 'fullDate', title: '全部转出时间',width: 100},
        {field: 'orderNo', title: '订单号',width: 100},
        {field: 'createDate', title: '创建时间',width: 110}
    ]],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar: '#tb'
});

//查询
function query() {
    var obj = $("#searchForm").serializeObject();
    dg.datagrid('load', obj);
}

//弹窗 查看
function view() {
    var row = dg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    dialog = $("#dialog").dialog({
        title: '查看',
        width: 1000,
        height: 600,
        href: ctx + '/admin/transfer/view/' + row.id,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '取消',
            handler: function () {
                dialog.panel('close');
            }
        }]
    });
    dg.datagrid('selectRecord', row.id);
}