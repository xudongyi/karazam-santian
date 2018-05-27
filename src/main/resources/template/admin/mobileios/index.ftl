[@nestedscript]
    [@js src="js/admin/mobileapp/ios.js" /]
    [@js src="lib/jquery/jquery.ajaxfileupload.js" /]
[/@nestedscript]
[@insert template="admin/layout/default_layout" title="IOS"]
    <div id="tb" style="padding:5px;height:auto">
        <div>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
                    onclick="createApp()">新增</a>
                <span class="toolbar-item dialog-tool-separator"></span>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
                   onclick="updateApp()">修改</a>
        </div>
    </div>
    <table id="appDg"></table>
    <table id="appDialog"></table>
<script>
    var dfsFileAccessBasePath = "${dfsUrl}";
</script>
[/@insert]