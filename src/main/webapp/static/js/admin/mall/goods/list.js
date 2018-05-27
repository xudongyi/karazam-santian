var datagrid;
var dialog;
datagrid = $('#datagrid').datagrid({
    method: "get",
    url: ctx+'/admin/goods/list',
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
        {field: 'id', title: 'id', hidden: true, width:120},
        {field: 'name', title: '名称', sortable: true, width:120},
        {field: 'seoTitle', title: '页面标题', sortable: true, width:120},
        {field: 'seoKeywords', title: '页面关键词', sortable: true, width:120},
        {field: 'seoDescription', title: '页面描述', sortable: true, width:120},
        {field: 'price', title: '价格', sortable: true, width:120},
        {field: 'point', title: '积分', sortable: true, width:120},
        {field: 'stock', title: '库存', sortable: true, width:120},
        {field: 'allocatedStock', title: '已分配库存', sortable: true, width:120},
        {field: 'putaway', title: '是否上架', sortable: true, width:120},
        {field: 'sort', title: '排序', sortable: true, width:120},
        {field: 'createDate', title: '创建时间', sortable: true, width:120}
    ]],
    enableHeaderClickMenu: true,
    enableHeaderContextMenu: true,
    enableRowContextMenu: false,
    toolbar: '#tb'
});

//弹窗输入新增文章分类
function create() {
    dialog=Keasy.dialog({
        title: '新增',
        width: 1200,
        height: 800,
        href: ctx+'/admin/goods/add',
        maximizable: true,
        modal: true
    });
}
//弹窗输入新增用户信息
function update() {
    var row = datagrid.datagrid('getSelected');

    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    dialog=Keasy.dialog({
        title: '修改',
        width: 1200,
        height: 800,
        href: ctx+'/admin/goods/edit/'+row.id,
        maximizable: true,
        modal: true
    });
}
function deleteCategory() {
    var row = datagrid.datagrid('getSelected');
    //var rows = { "userId": row.userId, "userInfoId": row.userInfoId };
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    $.messager.confirm('确认对话框', '您想要删除该商品吗？', function(r){
        if (r){
            Keasy.delete("/admin/goods/delete/"+row.id,{},function (data) {
                successTip(data, datagrid);
            })
        }
    });
}


function uploadLogo(uploadDom) {
    var uploadDg = Keasy.dialog({
        dialogId: "uploadLogoDag",
        title: '上传LOGO',
        width: 500,
        height: 200,
        href: ctx + '/admin/upload',
        maximizable: true,
        modal: true,
        buttons: [
            {
                id: "dialog_confirm_btn",
                iconCls: 'icon-ok',
                text: '确定',
                handler: function () {
                    var form = $("#uploadLogoDag").find("form");
                    var confirmBtn = $("#uploadLogoDag").find("#dialog_confirm_btn");
                    $(confirmBtn).linkbutton('disable');
                    if (!$(form).form('validate')) {
                        $(confirmBtn).linkbutton('enable');
                        return;
                    }
                    submitPic();
                }
            }, {
                iconCls: 'icon-cancel',
                text: '取消',
                handler: function () {
                    uploadDg.dialog('destroy');
                }
            }
        ]
    });

    function submitPic() {
        if (!$("#uploadForm").form('validate')) {
            return false;
        }
        var attachFile = $("#attachFile").val();
        if (attachFile == null || attachFile == "") {
            $("#picTip").html("<span style='color:Red'>错误提示:上传文件不能为空,请重新选择文件</span>");
            return false;
        } else {
            var extname = attachFile.substring(attachFile.lastIndexOf(".") + 1, attachFile.length);
            extname = extname.toLowerCase();//处理了大小写
            if (extname != "jpeg" && extname != "jpg" && extname != "gif" && extname != "png") {
                $("#picTip").html("<span style='color:Red'>错误提示:格式不正确,支持的图片格式为：JPEG、GIF、PNG！</span>");
                return false;
            }
        }
        var file = document.getElementById("attachFile").files;
        var size = file[0].size;
        if (size > 2097152) {
            $("#picTip").html("<span style='color:Red'>错误提示:所选择的图片太大，图片大小最多支持2M!</span>");
            return false;
        }
        ajaxFileUploadPic();
    }

    function ajaxFileUploadPic() {
        $.ajaxFileUpload({
            url: ctx + '/admin/upload',
            secureuri: false,
            fileElementId: 'attachFile',
            type: 'post',
            dataType: 'json',
            success: function (data, status) {
                data = JSON.parse(data);
                if (status == 'success') {
                    parent.$.messager.show({title: "提示", msg: data.message, position: "bottomRight"});
                    $('#' + uploadDom).textbox('setValue',data.data.attachUrl);
                    uploadDg.dialog('destroy');
                }
            },
            error: function (data, status, e) {
                $.messager.alert(data.message);
            }
        });
        return false;
    }
}

//
// function nextCategory(row){
//     parent.addTab({
//         title : row.name,
//         border : false,
//         closable : true,
//         fit : true,
//         content : '<iframe src="' + ctx + "/admin/goods/pageListByIdDispatcher/"+row.id + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>'
//     });
// }
//查询
function query() {
    var obj = $("#searchFrom").serializeObject();
    datagrid.datagrid('load', obj);
}

function doConfirmDialog(formData) {
    if(formData.action=='add'){
        Keasy.post("/admin/goods/save", formData, function (data) {
            successTip(data, datagrid,dialog);
        })
    }
    if(formData.action=='update'){
        Keasy.post("/admin/goods/update/"+formData.pk, formData, function (data) {
            console.log(data);
            successTip(data, datagrid,dialog);
        })
    }
}
//
// function querygoods() {
//     var row = datagrid.datagrid('getSelected');
//     //var rows = { "userId": row.userId, "userInfoId": row.userInfoId };
//     if(rowIsNull(row)){
//         $.messager.alert("提示","请选择一行数据");
//         return;
//     }
//     parent.window.mainpage.mainTabs.addModule(row.name+"相关文章", ctx + "/admin/goods/query_goods/"+row.id, "icon-hamburg-home", true, true);
// }
//

function querygoods() {
    var row = datagrid.datagrid('getSelected');
    //var rows = { "userId": row.userId, "userInfoId": row.userInfoId };
    if(rowIsNull(row)){
        $.messager.alert("提示","请选择一行数据");
        return;
    }
    parent.window.mainpage.mainTabs.addModule(row.name+"相关文章", ctx + "/admin/goods/query_goods/"+row.id, "icon-hamburg-home", true, true);
}