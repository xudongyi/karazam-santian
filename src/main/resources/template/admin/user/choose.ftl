[@insert template="admin/layout/default_layout" title="会员选择"]
<form id="userformsearch" style="margin: 5px;">
    <input id="mobileuser" type="text" name="mobile" class="easyui-textbox"
           data-options="width:150,prompt: '手机号'"/>
    <a href="javascript:querymobile();" class="easyui-linkbutton" iconCls="fa fa-search">查询</a>
</form>
<table id="chooseUserDatagrid"></table>
[/@insert]
