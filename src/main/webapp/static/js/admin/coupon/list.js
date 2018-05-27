/**
 * Created by zhutao on 2017/06/01.
 */
var dg;
var dialog;
dg = $('#dg').datagrid({
    method: "get",
    url: ctx + '/admin/coupon/list.do',
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
        {field: 'couponSourceStr', title: '优惠券来源',width: 120},
        {field: 'couponTypeStr', title: '优惠券类型',width: 120},
        {field: 'amount', title: '优惠券面额',width: 120},
        {field: 'isRandomAmount', title: '金额是否随机',width: 100,
            formatter:function (val,row,index) {
                if(row.isRandomAmount == 1){
                    return "是";
                }else{
                    return "否";
                }
            }
        },
        {field: 'rule', title: '规则',width: 100},
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

//弹窗输入信息
function createCoupon() {
    dialog = Keasy.dialog({
        title: '新增优惠券',
        width: 700,
        height: 500,
        href: ctx+'/admin/coupon/create',
        maximizable: true,
        modal: true
    });
}

//弹窗输入修改信息
function updateCoupon() {
    var row = dg.datagrid('getSelected');
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    dialog = Keasy.dialog({
        title: '修改优惠券',
        width: 600,
        height: 500,
        href: ctx+'/admin/coupon/update/'+row.id,
        maximizable: true,
        modal: true
    });
}
function deleteCoupon() {
    var row = dg.datagrid('getSelected');
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    $.messager.confirm('确认对话框', '您想要删除数据？', function(r){
        if (r){
            Keasy.delete("/admin/coupon/delete.do/"+row.id,{},function (data) {
                successTip(data, dg,dialog);
            })
        }
    });
}

function doConfirmDialog(formData) {
    console.log(formData);
    if(formData.action=='create'){
        console.log('create');
        Keasy.post("/admin/coupon/create.do", formData, function (data) {
            console.log(data);
            successTip(data, dg,dialog);
        })
    }
    if(formData.action=='update'){
        console.log('update');
        Keasy.post("/admin/coupon/update.do/"+formData.pk, formData, function (data) {
            console.log(data);
            successTip(data, dg,dialog);
        })
    }
}