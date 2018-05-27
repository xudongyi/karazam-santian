/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

var optionsDatagrid;
var optionsDag;
$(function () {
    var optionsType = $("#optionsType").val();
    optionsDatagrid = $('#basicDatagrid').datagrid({
        method: "get",
        url: ctx + '/admin/options/json?type=' + optionsType,
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
            {field: 'keyName', title: 'key', width: 100, hidden: true},
            {field: 'typeStr', title: '类型', width: 100, hidden: true},
            {field: 'name', title: '名称', width: 100},
            {field: 'keyValue', title: '内容', width: 200,
                formatter: function(value,row,index){
                    if (row.dataType == 'BOOLEAN'){
                        if (value == 'true') {
                            return "是";
                        } else {
                            return "否";
                        }
                    } else {
                        return value;
                    }
                }

            }
        ]],
        enableHeaderClickMenu: false,
        enableHeaderContextMenu: false,
        enableRowContextMenu: false,
        toolbar: '#tb'
    });
});

//弹窗修改
function updateOptions(type, typeName) {
    optionsDag = Keasy.dialog({
        dialogId: "updateOptions",
        title: '修改' + typeName,
        width: 500,
        height: 400,
        href: ctx + '/admin/options/update?type=' + type,
        maximizable: true,
        modal: true
    });
}

function doConfirmDialog(formData) {
    console.log(formData);
    Keasy.put("/admin/options/update", formData, function (data) {
        successTip(data, optionsDatagrid, optionsDag);
    })
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