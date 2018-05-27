[@nestedscript]
    [@js src="js/admin/schedule/schedule.log.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="定时任务日志"]
<div id="scheduleLogToolBar" style="padding:5px;height:auto">
    <div>
        [@shiro.hasPermission name="admin:schedule:log:view"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-eye'"  onclick="viewScheduleLog()">查看</a>
        [/@shiro.hasPermission]
    </div>
</div>
<table id="scheduleLogDatagrid"></table>
<div id="scheduleLogDialog"></div>
[/@insert]