/**
 * Created by zhutao on 2017/4/05.
 */
var dg;
var dialog;
dg = $('#dg').datagrid({
    method: "get",
    url: ctx + '/admin/borrowing/borrowingApplyFromWebSite.json',
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
        {field: 'id', title: 'ID', width: 30},
        {field: 'userName', title: '姓名',width: 50},
        {field: 'mobile', title: '联系电话',width: 60},
        {field: 'genderType', title: '性别',width: 40},
        {field: 'borrowType', title: '申请借款类型',width: 50},
        {field: 'borrowingApplyProgress', title: '申请借款进度',width: 50},
        {field: 'isPlatFormUser', title: '是否平台客户',width: 50,
            formatter:function (val,row,index) {
                if(val){
                    return "是";
                }else{
                    return "否";
                }
            }
        },
        {field: 'amount', title: '期望金额(元)',width: 60},
        {field: 'deadline', title: '期望期限(天)',width: 60},
        {field: 'rate', title: '期望利率(%)',width: 60},
        {field: 'suggestion', title: '审批意见',width: 200},
        {field: 'remark', title: '备注',width: 200},
        {field: 'createDate', title: '申请时间',width: 90}
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

//设为已联系
function setToConnected() {
    var row = dg.datagrid('getSelected');
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    if(row.borrowingApplyProgress!='申请中'){
        $.messager.alert("提示","只有进度为申请中的数据可被设为已联系");
        return;
    }
    $.messager.confirm('确认对话框', '你是否已联系该客户？', function(r){
        if (r){
            dialog = Keasy.dialog({
                title: '信息录入',
                width: 450,
                height: 240,
                href: ctx+'/admin/borrowing/suggestion/'+row.id+'?type=connected',
                maximizable: true,
                modal: true
            });
        }
    });
}
//设为已驳回
function applyReject() {
    var row = dg.datagrid('getSelected');
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    if(row.borrowingApplyProgress!='已联系'){
        $.messager.alert("提示","只有进度为已联系的数据可被驳回");
        return;
    }
    $.messager.confirm('确认对话框', '你是否驳回该借款申请？', function(r){
        console.log(1111);
        if (r){
            dialog = Keasy.dialog({
                title: '信息录入',
                width: 450,
                height: 240,
                href: ctx+'/admin/borrowing/suggestion/'+row.id+'?type=reject',
                maximizable: true,
                modal: true
            });
        }
    });
}

//设为已通过
function applySuccess() {
    var row = dg.datagrid('getSelected');
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    if(row.borrowingApplyProgress!='已联系'){
        $.messager.alert("提示","只有进度为已联系的数据可被通过");
        return;
    }
    $.messager.confirm('确认对话框', '你是否通过该借款申请？', function(r){
        if (r){
            dialog = Keasy.dialog({
                title: '信息录入',
                width: 450,
                height: 240,
                href: ctx+'/admin/borrowing/suggestion/'+row.id+'?type=success',
                maximizable: true,
                modal: true
            });
        }
    });
}

function doConfirmDialog(formData) {

    if(formData.action=='suggestion'){
        console.log(formData.id);
        Keasy.post("/admin/borrowing/doSet", formData, function (data) {
            console.log(dialog);
            successTip(data,dg,dialog);
        })
    }
}