var datagrid;
var dialog;
datagrid = $('#datagrid').datagrid({
    method: "get",
    url: ctx+'/admin/ad_position/list',
    fit: true,
    fitColumns: true,
    border: false,
    idField: 'id',
    striped: true,
    pagination: true,
    rownumbers: true,
    pageNumber: 1,
    pageSize: 20,
    pageList: [10, 20, 30, 40, 50],
    singleSelect: true,
    columns: [[
        {field: 'id', title: 'id', hidden: true},
        {field: 'name', title: '名称', width:120,
            formatter:function (val,row,index) {
                return  "<a href='#' onclick='nextCategory("+JSON.stringify(row)+")'>" + val + "</a>" ;
            }
        },
        {field: 'ident', title: '标示', width:120},
        {field: 'description', title: '描述', width:120},
        {field: 'builtin', title: '是否内置', width:120,
            formatter:function (val,row,index) {
                if(val){
                    return "是";
                }else{
                    return "否";
                }
            }
        }
    ]],
    toolbar: '#tb'
});

//弹窗输入新增链接信息
function create() {
    dialog= Keasy.dialog({
        title: '新增广告位',
        width: 540,
        height: 550,
        href: ctx+'/admin/ad_position/addEdit',
        maximizable: true,
        modal: true
    });
}
//弹窗输入修改链接
function update() {
    var row = datagrid.datagrid('getSelected');

    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    dialog= Keasy.dialog({
        title: '修改广告位',
        width: 540,
        height: 550,
        href: ctx+'/admin/ad_position/updateEdit/'+row.id,
        maximizable: true,
        modal: true
    });
}
function deleteAdPosition() {
    var row = datagrid.datagrid('getSelected');
    //var rows = { "userId": row.userId, "userInfoId": row.userInfoId };
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    $.messager.confirm('确认对话框', '您想要删除该广告位吗？', function(r){
        if (r){
            Keasy.delete("/admin/ad_position/delete/"+row.id,{},function (data) {
                successTip(data, datagrid);
            })
        }
    });
}

function nextCategory(row){
    parent.addTab({
        title : row.name,
        border : false,
        closable : true,
        fit : true,
        content : '<iframe src="' + ctx + "/admin/ad_position/pageListByIdDispatcher/"+row.id + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>'
    });
}
//查询
function query() {
    var obj = $("#searchFrom").serializeObject();
    datagrid.datagrid('load', obj);
}

function doConfirmDialog(formData) {

    console.log(formData);
    if(formData.action=='add'){
        Keasy.post("/admin/ad_position/save", formData, function (data) {
            successTip(data, datagrid,dialog);
        })
    }
    if(formData.action=='update'){
        Keasy.post("/admin/ad_position/update/"+formData.pk, formData, function (data) {
            console.log(data);
            successTip(data, datagrid,dialog);
        })
    }
}