var linksDg;
var linksDag;
$(function () {
    linksDg = $('#linksDg').datagrid({
        method: "get",
        url: ctx + '/admin/link/list',
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
            {field: 'name', title: '名称', width: 100},
            {field: 'typeStr', title: '类型', width: 100},
            {field: 'logo', title: 'LOGO', width: 100},
            {field: 'url', title: '链接地址', width: 100,
                formatter:function (val,row,index) {
                    return  "<a target='_blank' href='"+ val +"'>" + val + "</a>" ;
                }
            },
            {field: 'target', title: '链接打开方式', width: 100,
                formatter:function (val,row,index) {
                    if(val=='_blank'){
                        return "新窗口中打开";
                    }else{
                        return "相同的框架中打开";
                    }
                }
            },
            {field: 'description', title: '链接描述', width: 100},
            {
                field: 'visible', title: '是否可见', width: 100,
                formatter:function (val,row,index) {
                    if(val){
                        return "是";
                    }else{
                        return "否";
                    }
                }
            },
            {field: 'sort', title: '排序', width: 100},
            {field: 'createDate', title: '创建时间', width: 100}
        ]],
        toolbar: '#tb'
    });
});

//弹窗输入新增链接信息
function create() {
    linksDag= Keasy.dialog({
        title: '新增链接',
        width: 540,
        height: 550,
        href: ctx+'/admin/link/addEdit',
        maximizable: true,
        modal: true
    });

}
//弹窗输入修改链接
function update() {
    var row = linksDg.datagrid('getSelected');

    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    linksDag=  Keasy.dialog({
        title: '修改分类',
        width: 540,
        height: 550,
        href: ctx+'/admin/link/updateEdit/'+row.id,
        maximizable: true,
        modal: true
    });
}
function deleteLink() {
    var row = linksDg.datagrid('getSelected');
    //var rows = { "userId": row.userId, "userInfoId": row.userInfoId };
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    $.messager.confirm('确认对话框', '您想要删除该链接信息吗？', function(r){
        if (r){
            Keasy.delete("/admin/link/delete/"+row.id,{},function (data) {
                successTip(data, linksDg);
            })
        }
    });
}

function nextCategory(id){
    alert(id);
}
//查询
function query() {
    var obj = $("#searchFrom").serializeObject();
    linksDg.datagrid('load', obj);
}

function doConfirmDialog(formData) {
    if(formData.action=='add'){
        Keasy.post("/admin/link/save", formData, function (data) {
            successTip(data, linksDg, linksDag);
        })
    }
    if(formData.action=='update'){
        Keasy.put("/admin/link/update/"+formData.id, formData, function (data) {
            successTip(data, linksDg, linksDag);
        })
    }
}