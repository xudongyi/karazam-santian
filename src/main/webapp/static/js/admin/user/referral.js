/**
 * Created by zhutao on 2017/4/05.
 */
var dg;
var updateDg;
dg = $('#dg').datagrid({
    method: "get",
    url: ctx + '/admin/referral/list.json',
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
        {field: 'id', title: '推荐ID',width:60},
        {field: 'userId', title: 'userId', hidden: true,width:50},
        {field: 'reUserID', title: 'reUserID', hidden: true,width:50},
        // {field: 'realName', title: '推荐人',width:100},
        // {field: 'userMobile', title: '推荐人手机号',width:100},
        // {field: 'reRealName', title: '被推荐人',width:100},
        // {field: 'reUserMobile', title: '被推荐人手机号',width:100},
        {field: 'referralFeeRate', title: '推荐费率%',width:80},
        {field: 'createDate', title: '创建时间',width:150},
        {field: 'available', title: '是否有效',width:80,
            formatter:function (value,row,index) {
                if (value==false){
                    return "否";
                } else {
                    return "是";
                }
            }
        }
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

//弹窗输入
function update() {
    var row = dg.datagrid('getSelected');
    if(rowIsNull(row)){
        return;
    }
    updateDg = Keasy.dialog({
        title: '修改',
        width: 600,
        height: 500,
        href: ctx+'/admin/referral/update/'+row.id,
        maximizable: true,
        modal: true
    });
}

function doConfirmDialog(formData) {

    if(formData.action=='update'){
        Keasy.post("/admin/referral/update.do/"+formData.pk, formData, function (data) {
            console.log(data);
            successTip(data, dg,updateDg);
        })
    }
}