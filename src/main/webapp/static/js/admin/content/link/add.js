$(function () {

    //文本链接，不显示LOGO输入框
    if($("#typeStr").val()=='TEXT'){
        $(".logoTr").hide();
    }
    else{
        $("#logoTr").html("<td>LOGO：</td><td><div class='input-group'><input id='logo' name='logo' class='easyui-textbox' data-options=\"width:180,required:\'required\'\"/><label class='input-group-addon' onclick=\"uploadBanner('logo')\";>上传</label></div></td>");
        $.parser.parse('#logoTr');
    }
    //链接类型onchange事件
    $("#typeStr").combobox({
        onChange:function (newVal,oldVal) {
            if(newVal=='TEXT'){
                $("#logoTr").html("");
            }else{
                $("#logoTr").html("<td>LOGO：</td><td><div class='input-group'><input id='logo' name='logo' class='easyui-textbox' data-options=\"width:180,required:\'required\'\"/><label class='input-group-addon' onclick=\"uploadBanner('logo')\";>上传</label></div></td>");
                $.parser.parse('#logoTr');
        }
        }
    })
})

function uploadBanner(uploadDom) {
    var uploadDg = Keasy.dialog({
        dialogId: "uploadBannerDag",
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

                    var form = $("#uploadBannerDag").find("form");
                    var confirmBtn = $("#uploadBannerDag").find("#dialog_confirm_btn");
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
                if (data.status == 'success') {
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