/**
 * Created by suhao on 2017/4/18.
 */
var scheduleLogDatagrid;
var scheduleLogDialog;
$(function () {
    scheduleLogDatagrid = $('#scheduleLogDatagrid').datagrid({
        method: "get",
        url: ctx + '/admin/scheduleLog/list',
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
            {field: 'id', title: '日志ID', width: 50},
            {field: 'jobId', title: '任务ID', width: 50},
            {field: 'beanName', title: 'bean名称', width: 50},
            {field: 'params', title: '参数', width: 50},
            {
                field: 'status', title: '状态', width: 100,
                formatter: function(val,row,index){
                    return val === 0 ?
                        '<span class="label label-success">正常</span>' :
                        '<span class="label label-danger">暂停</span>';
                }
            },
            {field: 'times', title: '耗时(单位：毫秒)', width: 50},
            {field: 'createDate', title: '执行时间', width: 50}
        ]],
        toolbar: '#scheduleLogToolBar'
    });
});


//查看
function viewScheduleLog() {
    var row = scheduleLogDatagrid.datagrid('getSelected');
    if (rowIsNull(row)) return;
    scheduleLogDialog = Keasy.dialog({
        title: '查看定时任务日志',
        width: 600,
        height: 400,
        href: ctx + '/admin/scheduleLog/view/' + row.id,
        maximizable: true,
        modal: true,
        buttons:[]
    });
}