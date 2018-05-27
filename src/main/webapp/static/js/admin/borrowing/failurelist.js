
var datagrid;
var dialog;
datagrid = $('#datagrid').datagrid({
    toolbar: '#tb222',

    method: "get",
    url: '/admin/borrowing/failureList.json',
    fit: true,
    fitColumns: true,
    border: true,
    idField: 'id',
    striped: true,
    pagination: true,
    rownumbers: true,
    pageNumber: 1,
    pageSize: 20,
    pageList: [10, 20, 30, 40, 50],
    singleSelect: true,
    columns: [[
        {field: 'id', title: '借款编号', sortable: true},
        {field: 'title', title: '标题', sortable: true, width: 100},
        {field: 'progressDes', title: '进度', sortable: false, width: 100},
        {field: 'stateDes', title: '状态', sortable: false, width: 100},
        {field: 'typeDes', title: '类型', sortable: false, width: 100},
        {field: 'amount', title: '金额(元)', sortable: true, width: 100},
        /*{field: 'period', title: '期限', sortable: true, width: 100},*/
        {field: 'period', title: '期限', width: 100,
            formatter: function(value,row,index){
                return row.period + row.periodUnitDes;
            }
        },
        {field: 'interestRate', title: '利率(%)',width:100, sortable: true},
        {field: 'rewardInterestRate', title: '奖励利率(%)',width:100, sortable: true},
        {field: 'createDate', title: '创建日期',width:200, sortable: true}
    ]],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar: '#tb'
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
        width: 1000,
        height: 600,
        href: '/admin/borrowing/view/' + row.id,
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
