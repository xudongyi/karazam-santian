/**
 * Created by suhao on 2017/4/18.
 */
var scheduleDatagrid;
var scheduleDialog;
$(function () {
    scheduleDatagrid = $('#scheduleDatagrid').datagrid({
        method: "get",
        url: ctx + '/admin/schedule/list',
        fit: true,
        fitColumns: true,
        border: false,
        idField: 'id',
        striped: true,
        pagination: true,
        rownumbers: false,
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 40, 50],
        singleSelect: true,
        columns: [[
            {field: 'id', title: '任务ID', width: 50},
            {field: 'beanName', title: 'bean名称', width: 100},
            {field: 'methodName', title: '方法名称', width: 150},
            {field: 'params', title: '参数', width: 150},
            {field: 'cronExpression', title: 'cron表达式', width: 100},
            {field: 'remark', title: '备注', width: 100},
            {
                field: 'status', title: '状态', width: 100,
                formatter: function(val,row,index){
                    return val === 0 ?
                        '<span class="label label-success">正常</span>' :
                        '<span class="label label-danger">暂停</span>';
                }
            }
        ]],
        toolbar: '#scheduleToolBar'
    });
});

//弹窗增加
function createSchedule() {
    scheduleDialog = Keasy.dialog({
        dialogId: "createUser",
        title: '添加任务',
        width: 500,
        height: 250,
        href: ctx + '/admin/schedule/create',
        maximizable: true,
        modal: true
    });
}

//弹窗修改
function updateSchedule() {
    var row = scheduleDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    scheduleDialog = Keasy.dialog({
        dialogId: "updateSchedule",
        title: '修改定时任务',
        width: 500,
        height: 250,
        href: ctx + '/admin/schedule/update/' + row.id,
        maximizable: true,
        modal: true
    });
}

//查看
function viewSchedule() {
    var row = scheduleDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    scheduleDialog = Keasy.dialog({
        title: '查看定时任务',
        width: 500,
        height: 250,
        href: ctx + '/admin/schedule/view/' + row.id,
        maximizable: true,
        modal: true,
        buttons:[]
    });
}

//删除
function deleteSchedule() {
    var row = scheduleDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    var jobId = row.id;
    parent.$.messager.confirm('提示', '确认要删除任务？', function (data) {
        if (data) {
            var jobIdList = [];
            var data = scheduleDatagrid.datagrid('getSelections');
            for (var i = 0, j = data.length; i < j; i++) {
                var items = data[i].id;
                if (items == null) {
                    continue;
                }
                jobIdList.push(items);
            }

            if (jobId == null) {
                parent.$.messager.show({title: "提示", msg: "请选择任务！", position: "bottomRight"});
                return;
            }
            $.ajax({
                async: false,
                type: 'POST',
                data: JSON.stringify(jobIdList),
                contentType: 'application/json;charset=utf-8',
                url: ctx + "/admin/schedule/delete",
                success: function (result) {
                    result = JSON.parse(result);
                    successTip(result, scheduleDatagrid);
                }
            });
        }
    });
}

function runSchedule() {
    var row = scheduleDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    var jobId = row.id;
    parent.$.messager.confirm('提示', '确认要执行任务？', function (data) {
        if (data) {
            var jobIdList = [];
            var data = scheduleDatagrid.datagrid('getSelections');
            for (var i = 0, j = data.length; i < j; i++) {
                var items = data[i].id;
                if (items == null) {
                    continue;
                }
                jobIdList.push(items);
            }

            if (jobId == null) {
                parent.$.messager.show({title: "提示", msg: "请选择任务！", position: "bottomRight"});
                return;
            }
            $.ajax({
                async: false,
                type: 'POST',
                data: JSON.stringify(jobIdList),
                contentType: 'application/json;charset=utf-8',
                url: ctx + "/admin/schedule/run",
                success: function (result) {
                    result = JSON.parse(result);
                    successTip(result, scheduleDatagrid);
                }
            });
        }
    });
}

function pauseSchedule() {
    var row = scheduleDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    var jobId = row.id;
    parent.$.messager.confirm('提示', '确认要暂停任务？', function (data) {
        if (data) {
            var jobIdList = [];
            var data = scheduleDatagrid.datagrid('getSelections');
            for (var i = 0, j = data.length; i < j; i++) {
                var items = data[i].id;
                if (items == null) {
                    continue;
                }
                jobIdList.push(items);
            }

            if (jobId == null) {
                parent.$.messager.show({title: "提示", msg: "请选择任务！", position: "bottomRight"});
                return;
            }
            $.ajax({
                async: false,
                type: 'POST',
                data: JSON.stringify(jobIdList),
                contentType: 'application/json;charset=utf-8',
                url: ctx + "/admin/schedule/pause",
                success: function (result) {
                    result = JSON.parse(result);
                    successTip(result, scheduleDatagrid);
                }
            });
        }
    });
}

function resumeSchedule() {
    var row = scheduleDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    var jobId = row.id;
    parent.$.messager.confirm('提示', '确认要恢复任务？', function (data) {
        if (data) {
            var jobIdList = [];
            var data = scheduleDatagrid.datagrid('getSelections');
            for (var i = 0, j = data.length; i < j; i++) {
                var items = data[i].id;
                if (items == null) {
                    continue;
                }
                jobIdList.push(items);
            }

            if (jobId == null) {
                parent.$.messager.show({title: "提示", msg: "请选择任务！", position: "bottomRight"});
                return;
            }
            $.ajax({
                async: false,
                type: 'POST',
                data: JSON.stringify(jobIdList),
                contentType: 'application/json;charset=utf-8',
                url: ctx + "/admin/schedule/resume",
                success: function (result) {
                    result = JSON.parse(result);
                    successTip(result, scheduleDatagrid);
                }
            });
        }
    });
}

//创建查询对象并查询
function querySchedule() {
    var obj = $("#searchFrom").serializeObject();
    scheduleDatagrid.datagrid('load', obj);
}

function doConfirmDialog(formData) {
    var action = formData.action;
    if ('create' == action) {
        Keasy.post("/admin/schedule/create", formData, function (data) {
            successTip(data, scheduleDatagrid, scheduleDialog);
        })
    } else if ('update' == action) {
        Keasy.put("/admin/schedule/update", formData, function (data) {
            successTip(data, scheduleDatagrid, scheduleDialog);
        })
    }
}