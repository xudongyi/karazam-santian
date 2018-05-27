$(function () {

});
var messageDg;
var messageDialog;
$(function () {
    messageDg = $('#messageDg').datagrid({
        method: "get",
        url: ctx + '/mng/messagepush/json',
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
            {field: 'notificationTitle', title: '通知标题', width: 100},
            {field: 'typeStr', title: '类型', width: 50},
            {field: 'title', title: '标题', width: 100},
            {field: 'content', title: '内容', width: 300},
            {field: 'createDate', title: '创建时间', width: 100}
        ]],
        toolbar: '#tb'
    });
});

//弹窗增加
function pushMessage() {
    messageDialog = Keasy.dialog({
        dialogId: "createPushMessage",
        title: '推送消息',
        width: 600,
        height: 480,
        href: ctx + '/mng/messagepush/push',
        maximizable: true,
        modal: true
    });
}
function doConfirmDialog(formData) {
    Keasy.post("/mng/messagepush/push", formData, function (data) {
        successTip(data, messageDg, messageDialog);
    })
}