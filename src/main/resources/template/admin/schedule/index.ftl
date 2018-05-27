[@nestedscript]
    [@js src="js/admin/schedule/schedule.job.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="定时任务"]
<div id="scheduleToolBar" style="padding:5px;height:auto">
    <div>
        [@shiro.hasPermission name="admin:schedule:job:update"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-edit'" onclick="updateSchedule();">修改</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="admin:schedule:job:delete"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-remove'"  onclick="deleteSchedule()">删除</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="admin:schedule:job:view"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-eye'"  onclick="viewSchedule()">查看</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="admin:schedule:job:run"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-eye'"  onclick="runSchedule()">执行</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="admin:schedule:job:pause"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-eye'"  onclick="pauseSchedule()">暂停</a>
        [/@shiro.hasPermission]
        [@shiro.hasPermission name="admin:schedule:job:resume"]
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'fa fa-eye'"  onclick="resumeSchedule()">恢复</a>
        [/@shiro.hasPermission]
    </div>
</div>
<table id="scheduleDatagrid"></table>
<div id="scheduleDialog"></div>
[/@insert]