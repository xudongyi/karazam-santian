/**
 * Created by zhutao on 2017/06/01.
 */
var dg;
var dialog;
dg = $('#dg').datagrid({
    method: "get",
    url: ctx + '/admin/userCoupon/list.do',
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
        {field: 'couponStateStr', title: '优惠券状态',width: 120},
        {field: 'availableByCategory', title: '失效规则',width: 100,
            formatter:function (val,row,index) {
                if(row.availableByCategory){
                    return "优惠券类型";
                }else{
                    return "用户优惠券";
                }

            }
        },
        {field: 'userInvalidDate', title: '用户优惠券失效日期',width: 150},
        {field: 'couponInvalidDate', title: '该类优惠券失效日期',width: 150},
        {field: 'userRule', title: '用户优惠券规则',width: 110},
        {field: 'couponRule', title: '该类优惠券规则',width: 110},
        {field: 'amount', title: '优惠券面额',width: 100},
        {field: 'mobile', title: '手机号',width: 100},
        {field: 'realName', title: '真实姓名',width: 100},
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
