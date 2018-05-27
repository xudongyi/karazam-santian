[@nestedscript]
    [@js src="js/admin/sms_token/list.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="手机短信验证码列表"]
<div id="userManage" class="easyui-layout" data-options="fit: true">
    <div data-options="region:'center',split:true,border:false,title:'手机短信验证码列表'" style="width: 500px">
        <div id="tb" style="padding:5px;height:auto">
        </div>
        <table id="dg"></table>
        <div id="dialog"></div>
    </div>
</div>
[/@insert]