/**
 * Created by zhutao on 2017/4/05.
 */
var dg;
var dialog;
dg = $('#dg').datagrid({
    method: "get",
    url: ctx + '/admin/user/list.json',
    fit: true,
    // fitColumns: true,
    queryParams: {
        type: $("#userType").val()
    },
    border: true,
    idField: 'id',
    striped: true,
    pagination: true,
    pageNumber: 1,
    pageSize: 20,
    pageList: [10, 20, 30, 40, 50],
    singleSelect: true,
    columns: [[
        // {field: 'id', title: '用户ID', width: 60},
        {field: 'loginName', title: '登录名',width: 80,"rowspan":2,"colspan":1},
        // {field: 'name', title: '昵称',width: 120},
        {field: 'mobile', title: '手机号',width: 80,"rowspan":2,"colspan":1},
        {field: 'realName', title: '真实姓名',width: 60,"rowspan":2,"colspan":1},
        {field: 'birthday', title: '出生日期',width: 80,"rowspan":2,"colspan":1,
            formatter:function (val,row,index) {
                if (val!=null && val!=""){
                    var birth = new Date(val);
                    return birth.format("yyyy-MM-dd");
                }else {
                    return val;
                }
            }
        },
        {field: 'idNo', title: '身份证号码',width: 150,"rowspan":2,"colspan":1},
        {field: 'typeStr', title: '用户类型',width: 50,"rowspan":2,"colspan":1},
        {"title":"托管信息","colspan":4},
        {field: 'createDate', title: '注册日期',width: 120,"rowspan":2,"colspan":1},
        {field: 'lastVisit', title: '最后一次登陆日期',width: 120,"rowspan":2,"colspan":1}
        ],[{field: 'hasPayAccount', title: '是否开通托管',width: 80,
        formatter:function (val,row,index) {
            if(row.payAccountNo == ''){
                return "否";
            }else{
                return "是";
            }
        }
    },
        {field: 'payAccountNo',title:'托管账户号',width:100},
        {field: 'acctStatus',title:'账户开通状态',width:80,
            formatter:function (val,row,index) {
                if(val == 10){
                    return "未开通";
                }else if(val == 15){
                    return "待认证";
                }else if(val == 20){
                    return "审核中";
                }else if(val == 30){
                    return "已开通";
                }else if(val == 40){
                    return "审核驳回";
                }else if(val == 60){
                    return "正在开通电子账户";
                }else {
                    return "";
                }
            }
        },
        /*{field: 'userCardStatus',title:'身份证审核状态',width:80,
            formatter:function (val,row,index) {
                if(val == 0){
                    return "未上传身份证";
                }else if(val == 1){
                    return "审核成功";
                }else if(val == 2){
                    return "审核拒绝";
                }else if(val == 3){
                    return "审核中";
                }else if(val == 4){
                    return "未推送审核";
                }else {
                    return "";
                }
            }
        },
        {field: '绑定银行卡信息', title: '绑定银行卡信息',width: 130,
            formatter:function (val,row,index) {
                if (row.bankName!=null && row.bankCard!=null){
                    return row.bankName+"(尾号:" + row.bankCard + ")";
                }else {
                    return "";
                }
            }
        },*/
        {field: 'chargeAgreementNo', title: '还款签约号',width: 70},
        /*{field: 'queryDate', title: '接口查询时间',width: 120},*/
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

//新增用户
function create() {
    dialog = Keasy.dialog({
        title: '新增用户',
        width: 700,
        height: 400,
        href: ctx+'/admin/user/create',
        maximizable: true,
        modal: true
    });
}

//修改用户
function update() {
    var row = dg.datagrid('getSelected');
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    dialog = Keasy.dialog({
        title: '修改用户',
        width: 600,
        height: 500,
        href: ctx+'/admin/user/update/'+row.id,
        maximizable: true,
        modal: true
    });
}

function exportUser() {
    var url = ctx + "/admin/user/exportUser";
    $("#searchForm").attr("action", url);
    $("#searchForm").submit();
}

function doConfirmDialog(formData) {

    console.log(formData);
    if(formData.action=='add'){
        Keasy.post("/admin/user/create", formData, function (data) {
            successTip(data, dg, dialog);
        })
    }
    if(formData.action=='update'){
        Keasy.post("/admin/user/update/"+formData.pk, formData, function (data) {
            console.log(data);
            successTip(data, dg, dialog);
        })
    }
}