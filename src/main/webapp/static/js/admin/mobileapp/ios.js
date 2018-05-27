var appDg;
var appDialog;
$(function () {
    appDg = $('#appDg').datagrid({
        method: "get",
        url: ctx + '/admin/mobileios/json',
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
            {field: 'appType', title: '类型', width: 100},
            {field: 'versionName', title: '版本名称', width: 100},
            {field: 'versionNo', title: '版本号', width: 100},
            {field: 'packageName', title: '包名', width: 100},
            /*{field: 'appUrl', title: '下载地址', width: 100,
                formatter: function(value, row, index){
                return dfsFileAccessBasePath + value;
            }},*/
            {field: 'appUrl', title: '下载地址', width: 100},
            {field: 'changeLog', title: '更新日志', width: 300}
        ]],
        enableHeaderClickMenu: false,
        enableHeaderContextMenu: false,
        enableRowContextMenu: false,
        toolbar: '#tb'
    });
});

//弹窗增加
function createApp() {
    appDialog = Keasy.dialog({
        dialogId: "createUser",
        title: '添加应用',
        width: 600,
        height: 480,
        href: ctx + '/admin/mobileios/create',
        maximizable: true,
        modal: true
    });
}

//弹窗修改
function updateApp() {
    var row = appDg.datagrid('getSelected');
    if (rowIsNull(row)) return;
    appDialog = Keasy.dialog({
        dialogId: "updateUser",
        title: '修改应用',
        width: 600,
        height: 480,
        href: ctx + '/admin/mobileios/update/' + row.id,
        maximizable: true,
        modal: true
    });
}

function doConfirmDialog(formData) {
    var action = formData.action;
    if ('create' == action) {
        Keasy.post("/admin/mobileios/create", formData, function (data) {
            successTip(data, appDg, appDialog);
        })
    } else if ('update' == action) {
        Keasy.put("/admin/mobileios/update", formData, function (data) {
            successTip(data, appDg, appDialog);
        })
    }
}

function uploadApp(uploadDom) {
    var uploadDg = Keasy.dialog({
        dialogId: "uploadAppag",
        title: '上传应用',
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
                    var form = $("#uploadAppag").find("form");
                    var confirmBtn = $("#uploadAppag").find("#dialog_confirm_btn");
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

    // function submitPic() {
    //     if (!$("#uploadForm").form('validate')) {
    //         return false;
    //     }
    //     var attachFile = $("#attachFile").val();
    //     if (attachFile == null || attachFile == "") {
    //         $("#picTip").html("<span style='color:Red'>错误提示:上传应用不能为空,请重新选择应用</span>");
    //         return false;
    //     } else {
    //         var extname = attachFile.substring(attachFile.lastIndexOf(".") + 1, attachFile.length);
    //         extname = extname.toLowerCase();//处理了大小写
    //         if (extname != "apk") {
    //             $("#picTip").html("<span style='color:Red'>错误提示:格式不正确,支持的应用格式为：apk！</span>");
    //             return false;
    //         }
    //     }
    //     var file = document.getElementById("attachFile").files;
    //     var size = file[0].size;
    //     if (size > 52428800) {
    //         $("#picTip").html("<span style='color:Red'>错误提示:所选择的应用太大，应用大小最多支持50M!</span>");
    //         return false;
    //     }
    //     ajaxFileUploadPic();
    // }

    // function ajaxFileUploadPic() {
    //     $.ajaxFileUpload({
    //         url: ctx + '/admin/upload',
    //         secureuri: false,
    //         fileElementId: 'attachFile',
    //         type: 'post',
    //         dataType: 'json',
    //         success: function (data, status) {
    //             if (data.status == 'success') {
    //                 parent.$.messager.show({title: "提示", msg: data.message, position: "bottomRight"});
    //                 $('#' + uploadDom).val(data.data.attachUrl);
    //                 uploadDg.dialog('destroy');
    //             }
    //         },
    //         error: function (data, status, e) {
    //             $.messager.alert(data.message);
    //         }
    //     });
    //     return false;
    // }
}