/**
 * Created by suhao on 2017/6/12.
 */
/**
 * Created by zhutao on 2017/4/05.
 */
var dg;
var dialog;
dg = $('#dg').datagrid({
    method: "get",
    url: ctx + '/admin/user/autoInvestRank/list.json',
    fit: true,
    border: true,
    idField: 'id',
    striped: true,
    pagination: true,
    pageNumber: 1,
    pageSize: 20,
    pageList: [10, 20, 30, 40, 50],
    singleSelect: false,
    columns: [[
        {field: 'id', title: 'ID', width: 60, hidden: true},
        {field: 'mobile', title: '手机号',width: 120},
        {field: 'realName', title: '真实姓名',width: 100},
        {field: 'createDate', title: '签约日期',width: 150},
        {
            field: 'openStatus', title: '开启状态',width: 50,
            formatter: function (value, row, index) {
                return value ? '是' : '否';
            }
        },
        {field: 'rankDes', title: '自动投标排名',width: 150},
        {field: 'investNo', title: '投资序号',width: 150},
        {field: 'expire', title: '过期时间',width: 150},
        {field: 'investMinAmount', title: '投标限额最小值(元)',width: 120},
        {field: 'investMaxAmount', title: '投标限额最大值(元)',width: 120},
        {field: 'projectMinCyc', title: '标的周期最小值(天)',width: 120},
        {field: 'projectMaxCyc', title: '标的周期最大值(天)',width: 120},
        {field: 'interestRateMinRate', title: '利率最小值(%)',width: 120},
        {field: 'interestRateMaxRate', title: '利率最大值(%)',width: 120},
        {field: 'prevInvestDate', title: '上次投资时间',width: 150}
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

//查询并更新Ips信息
function queryIpsInfo() {
    var ids = [];
    var rows = dg.datagrid('getSelections');
    for(var i=0; i<rows.length; i++){
        ids.push(rows[i].id);
        if (rows[i].state == "paid") {
            $.messager.alert("提示","");
            return;
        }
    }
    if(ids.length<1){
        $.messager.alert("提示","请至少选择一行数据");
        return;
    }
    $.messager.confirm('确认对话框', '共'+ ids.length +'条数据,确认查询？', function(r){
        if (r){
            Keasy.delete(ctx + "/admin/ips/update/"+ids,{},function (r) {
                successTip(r,dg);
            });
        }
    });
}

//弹窗 查看
function view() {
    var row = dg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    dialog = $("#dialog").dialog({
        title: '查看',
        width: 1000,
        height: 600,
        href: ctx + '/admin/user/view/' + row.id,
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